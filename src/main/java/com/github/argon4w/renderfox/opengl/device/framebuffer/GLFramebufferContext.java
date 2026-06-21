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

package com.github.argon4w.renderfox.opengl.device.framebuffer;

import com.github.argon4w.renderfox.data.coordinate.IOffset3D;
import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferStateManager;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.function.*;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag.GLFramebufferWriteBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag.GLFramebufferWriteBufferBit;
import com.github.argon4w.renderfox.opengl.framebuffer.object.GLFrameBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.object.GLFramebufferCreateInfo;
import com.github.argon4w.renderfox.opengl.framebuffer.object.raw.GLRawFramebuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.object.raw.IGLRawFramebuffer;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTextureBase;

public class GLFramebufferContext {

	private final	OpenGLDevice					device;
	private final	GLFramebufferStateManager		framebufferStateManager;
	private final	IGLFramebufferFunctions			framebufferFunctions;
	private final	GLFramebufferFunctionsHelper	framebufferHelper;

	private			IGLRawFramebuffer				globalReadFramebuffer;
	private			IGLRawFramebuffer				globalDrawFramebuffer;
	private			boolean							globalFramebufferInitialized;

	public GLFramebufferContext(OpenGLDevice device) {
		this.device						= device;
		this.framebufferStateManager	= new GLFramebufferStateManager	();
		this.framebufferFunctions		= createFramebufferFunctions	();
		this.framebufferHelper			= createFramebufferHelper		();

		this.globalReadFramebuffer			= null;
		this.globalDrawFramebuffer			= null;
		this.globalFramebufferInitialized	= false;
	}

	public void initialize() {
		framebufferStateManager	.initialize(device);
		framebufferFunctions	.initialize(device);
	}

	public void copyTextureToTexture(
			IGLRawTextureBase	readTexture,
			IGLRawTextureBase	drawTexture,
			IOffset3D			copyOffsetRead,
			IOffset3D			copyOffsetDraw,
			IExtent3D			copyExtent,
			int					copyMipLevelRead,
			int					copyMipLevelDraw
	) {
		if (copyOffsetRead == null) {
			throw new IllegalArgumentException("CopyOffsetRead cannot be null.");
		}

		if (copyOffsetDraw == null) {
			throw new IllegalArgumentException("CopyOffsetDraw cannot be null.");
		}

		if (copyExtent == null) {
			throw new IllegalArgumentException("CopyExtent cannot be null.");
		}

		if (!globalFramebufferInitialized) {
			globalFramebufferInitialized = true;

			globalReadFramebuffer = createRawFramebuffer();
			globalDrawFramebuffer = createRawFramebuffer();

			globalReadFramebuffer.setReadAttachment(GLFramebufferAttachment.COLOR_ATTACHMENT_0);
			globalDrawFramebuffer.setDrawAttachment(GLFramebufferAttachment.COLOR_ATTACHMENT_0);
		}

		var internalFormatRead = readTexture.getInternalFormat(copyMipLevelRead);
		var internalFormatDraw = drawTexture.getInternalFormat(copyMipLevelDraw);

		if (internalFormatRead != internalFormatDraw) {
			throw new IllegalArgumentException("The internal format of the readTexture and drawTexture must be equal.");
		}

		var writeBuffer = new GLFramebufferWriteBuffer();

		if (internalFormatRead.hasColor()) {
			writeBuffer.add(GLFramebufferWriteBufferBit.COLOR_BUFFER_BIT);

			globalReadFramebuffer.attachTexture(GLFramebufferAttachment.COLOR_ATTACHMENT_0, readTexture, copyMipLevelRead);
			globalDrawFramebuffer.attachTexture(GLFramebufferAttachment.COLOR_ATTACHMENT_0, drawTexture, copyMipLevelDraw);
		}

		if (internalFormatRead.hasDepth()) {
			writeBuffer.add(GLFramebufferWriteBufferBit.DEPTH_BUFFER_BIT);

			globalReadFramebuffer.attachTexture(GLFramebufferAttachment.DEPTH, readTexture, copyMipLevelRead);
			globalDrawFramebuffer.attachTexture(GLFramebufferAttachment.DEPTH, drawTexture, copyMipLevelDraw);
		}

		if (internalFormatRead.hasStencil()) {
			writeBuffer.add(GLFramebufferWriteBufferBit.STENCIL_BUFFER_BIT);

			globalReadFramebuffer.attachTexture(GLFramebufferAttachment.STENCIL, readTexture, copyMipLevelRead);
			globalDrawFramebuffer.attachTexture(GLFramebufferAttachment.STENCIL, drawTexture, copyMipLevelDraw);
		}

		globalReadFramebuffer.blitDataTo(
				globalDrawFramebuffer,
				(int) copyOffsetRead.getOffsetX(),
				(int) copyOffsetRead.getOffsetY(),
				(int) copyOffsetRead.getOffsetX() + (int) copyExtent.getWidth	(),
				(int) copyOffsetRead.getOffsetY() + (int) copyExtent.getHeight	(),
				(int) copyOffsetDraw.getOffsetX(),
				(int) copyOffsetDraw.getOffsetY(),
				(int) copyOffsetDraw.getOffsetX() + (int) copyExtent.getWidth	(),
				(int) copyOffsetDraw.getOffsetY() + (int) copyExtent.getHeight	(),
				writeBuffer,
				GLFilterMode.NEAREST
		);
	}

	public IGLFramebufferFunctions createFramebufferFunctions() {
		var framebufferFunctions = GLFramebufferFunctionsDirect.of();

		framebufferFunctions = device.getCreateInfo().useCacheStates() ? new GLFramebufferFunctionsCached		(framebufferFunctions) : framebufferFunctions;
		framebufferFunctions = device.getCreateInfo().useErrorCheck	() ? new GLFramebufferFunctionsErrorCheck	(framebufferFunctions) : framebufferFunctions;
		framebufferFunctions = device.getCreateInfo().useValidation	() ? new GLFramebufferFunctionsValidation	(framebufferFunctions) : framebufferFunctions;

		return framebufferFunctions;
	}

	public GLFrameBuffer createFramebuffer(GLFramebufferCreateInfo createInfo) {
		var rawFramebuffer				= createRawFramebuffer						();
		var readAttachment				= createInfo.getReadAttachment				();
		var drawAttachments				= createInfo.getDrawAttachments				();
		var colorAttachments			= createInfo.getColorAttachments			();
		var depthAttachment				= createInfo.getDepthAttachment				();
		var stencilAttachment			= createInfo.getStencilAttachment			();
		var drawAttachmentCount			= createInfo.getDrawAttachmentCount			();
		var defaultWidth				= createInfo.getDefaultWidth				();
		var defaultHeight				= createInfo.getDefaultHeight				();
		var defaultLayers				= createInfo.getDefaultLayers				();
		var defaultSamples				= createInfo.getDefaultSamples				();
		var defaultFixedSampleLocations	= createInfo.isDefaultFixedSampleLocations	();

		for (var index = 0; index < drawAttachmentCount; index ++) {
			colorAttachments[index].attach(rawFramebuffer, GLFramebufferAttachment.color(index));
		}

		depthAttachment		.attach(rawFramebuffer, GLFramebufferAttachment.DEPTH);
		stencilAttachment	.attach(rawFramebuffer, GLFramebufferAttachment.STENCIL);

		rawFramebuffer.setDrawAttachments			(drawAttachmentCount, drawAttachments.address());
		rawFramebuffer.setReadAttachment			(readAttachment);
		rawFramebuffer.setDefaultWidth				(defaultWidth);
		rawFramebuffer.setDefaultHeight				(defaultHeight);
		rawFramebuffer.setDefaultLayers				(defaultLayers);
		rawFramebuffer.setDefaultSamples			(defaultSamples);
		rawFramebuffer.setDefaultFixedSampleLocation(defaultFixedSampleLocations);

		return new GLFrameBuffer(rawFramebuffer);
	}

	public int createFramebufferHandle() {
		return framebufferFunctions.createFramebuffer();
	}

	public GLFramebufferFunctionsHelper createFramebufferHelper() {
		return new GLFramebufferFunctionsHelper(framebufferFunctions);
	}

	public GLFramebufferFunctionsHelper createFramebufferHelper(int framebufferHandle) {
		return new GLFramebufferFunctionsHelper(framebufferFunctions).setFramebuffer(framebufferHandle);
	}

	public IGLRawFramebuffer createRawFramebuffer() {
		return device.getCreateInfo().useCacheProperties() ? new GLRawFramebuffer(this) : createFramebufferHelper(createFramebufferHandle());
	}

	public GLFramebufferStateManager getFramebufferStateManager() {
		return framebufferStateManager;
	}

	public IGLFramebufferFunctions getFramebufferFunctions() {
		return framebufferFunctions;
	}

	public GLFramebufferFunctionsHelper getGlobalFramebufferHelper() {
		return framebufferHelper;
	}

	public OpenGLDevice getDevice() {
		return device;
	}
}
