/*
 * Copyright (C) 2026  Argon4W
 *
 * This file is part of RenderFox.
 *
 * RenderFox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RenderFox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RenderFox.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.argon4w.renderfox;

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferAccessBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageBit;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBufferCreateInfo;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped.GLMappedBufferCreateInfo;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.device.OpenGLDeviceCreateInfo;
import com.github.argon4w.renderfox.opengl.texture.object.GLTextureCreateInfo;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.lifecycle.ClientStartedEvent;
import org.slf4j.Logger;

@Mod(
		value	= RenderFox	.MOD_ID,
		dist	= Dist		.CLIENT
)
@EventBusSubscriber(
		modid	= RenderFox	.MOD_ID,
		value	= Dist		.CLIENT
)
public class RenderFox {

	public static final String MOD_ID = "render_fox";
	public static final Logger LOGGER = LogUtils.getLogger();

    public RenderFox(IEventBus modEventBus, ModContainer container) {

    }

	@SubscribeEvent
	public static void clientSetup(ClientStartedEvent event) {
		// Create our OpenGL Device for later use.
		OpenGLDevice device = OpenGLDeviceCreateInfo
				.builder				()
				.useDirectStateAccess	(false)
				.useBufferStorage		(false)
				.useTextureStorage		(false)
				.useValidation			(true)
				.useErrorCheck			(true)
				.useCacheStates			(false)
				.build					()
				.buildDevice			();

		// Define the properties of our staging buffer which is a "bridge" between host (CPU) and device (GPU).
		// Staging buffers are usually on host side but "visible" to device side through PCI-E access.
		// It needs to be mapped persistently to reach maximum efficiency.
		var stagingInfo = GLMappedBufferCreateInfo
				.builder		()
				.withBufferSize	(2048)
				.withBufferType	(GLBufferType		.VERTEX_BUFFER)
				.addStorageBit	(GLBufferStorageBit	.CLIENT_STORAGE)
				.addStorageBit	(GLBufferStorageBit	.DYNAMIC_STORAGE)
				.addStorageBit	(GLBufferStorageBit	.MAP_PERSISTENT)
				.addStorageBit	(GLBufferStorageBit	.MAP_COHERENT)
				.addStorageBit	(GLBufferStorageBit	.MAP_WRITE)
				.addAccessBit	(GLBufferAccessBit	.MAP_FLUSH_EXPLICIT)
				.addAccessBit	(GLBufferAccessBit	.MAP_PERSISTENT)
				.addAccessBit	(GLBufferAccessBit	.MAP_COHERENT)
				.addAccessBit	(GLBufferAccessBit	.MAP_WRITE)
				.build			();

		// Define the properties of our device-side pixel buffer for uploading the data to our texture.
		// PIXEL_UNPACK_BUFFER means the buffer is optimized for uploading data to texture.
		var pboInfo = GLBufferCreateInfo
				.builder		()
				.withBufferSize	(2048)
				.withBufferType	(GLBufferType.PIXEL_UNPACK_BUFFER)
				.build			();

		// Define the properties of our 16 * 16 texture
		var textureInfo = GLTextureCreateInfo
				.builder				()
				.withTextureMipLevels	(3)
				.withTextureWidth		(16)
				.withTextureHeight		(16)
				.withTextureDepth		(1)
				.build					();

		// Create the staging buffer.
		var stagingBuffer = device
				.getBufferContext	()
				.getBufferCreator	()
				.createMappedBuffer	(stagingInfo);

		// Create the pixel buffer.
		var pixelBuffer = device
				.getBufferContext	()
				.getBufferCreator	()
				.createBuffer		(pboInfo);

		// Create the texture.
		var texture = device
				.getTextureContext	()
				.getTextureCreator	()
				.createTexture		(textureInfo);

		// Create a transfer info for uploading data to the texture.
		// We want our uploaded data to be 14 * 14 pixels and pasted (uploaded) it to not the origin but (1, 1).
		var transfer = texture
				.transfer			(0)
				.withDeviceOffsetX	(1)
				.withDeviceOffsetY	(1)
				.withWidth			(14)
				.withHeight			(14)
				.build				();

		// Allocate a slice of memory in our staging buffer and decorate it with our transfer info
		// to make writing pixels easier through ImageDataView.
		var pixels = stagingBuffer.reserve(1024).as(transfer);

		// Now we can put actual 14 * 14 image data into the memory.
		for		(var pixelX = 0; pixelX < 14; pixelX ++) {
			for	(var pixelY = 0; pixelY < 14; pixelY ++) {

				var fx = pixelX / 14.0f;
				var fy = pixelY / 14.0f;

				pixels.putTexelRGBA8(
						pixelX,
						pixelY,
						(int) (fx * 255.0f),
						(int) (fy * 255.0f),
						0,
						255
				);
			}
		}

		pixels.putTexelRGBA8(
				0,
				0,
				255,
				255,
				255,
				255
		);

		// Flush the data to make it visible to GPU for following operations.
		// Also, we now have the range of what we actually wrote to.
		var pixelRange = pixels.flush();

		// Copy the host-side data in staging buffer to the start of device-side pixel buffer
		// from the range we wrote the staging buffer.
		stagingBuffer.copyRangeDataTo(
				pixelBuffer,
				pixelRange,
				pixelRange.withOffset(0L)
		);

		// Free all allocated space from staging buffer because we have done using it.
		stagingBuffer.clear();

		// Clear the texture to fully opaque black.
		texture.clearFullImage(0, new ColorFloat());

		// Upload the image data in the device-side pixel buffer to the texture
		// using the length from data allocated in staging buffer and offset from
		// what we copy the data to pixel buffer which is zero.
		texture.uploadRangeImageFromBuffer(
				transfer,
				pixelBuffer,
				pixelRange.withOffset(0L)
		);

		// Generate the mipmap for the texture.
		texture.generateMipmap();

		// Bind the texture to texture unit 9 for observation.
		texture.bindTextureUnit(9);
	}
}
