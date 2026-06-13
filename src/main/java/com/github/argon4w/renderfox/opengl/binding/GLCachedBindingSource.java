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

package com.github.argon4w.renderfox.opengl.binding;

import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelParameter;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GLCachedBindingSource implements IGLBindingSource {

	private final	Int2IntMap														boundSamplers;
	private final	Int2ReferenceMap		<Reference2IntMap<GLTextureType>>		boundTextures;
	private final	Reference2IntMap		<GLPixelParameter>						pixelStorageModes;
	private final	Reference2IntMap		<GLFramebufferType>						boundFramebuffers;
	private final	Reference2IntMap		<GLBufferType>							boundBuffers;
	private final	Reference2ReferenceMap	<GLBufferBlockType, BufferBlockBinding>	boundBufferBlocks;
	private			int																activeTexture;

	public GLCachedBindingSource() {
		this.boundSamplers		= new Int2IntOpenHashMap				();
		this.boundTextures		= new Int2ReferenceOpenHashMap		<>	();
		this.pixelStorageModes	= new Reference2IntOpenHashMap		<>	();
		this.boundBuffers		= new Reference2IntOpenHashMap		<>	();
		this.boundFramebuffers	= new Reference2IntOpenHashMap		<>	();
		this.boundBufferBlocks	= new Reference2ReferenceOpenHashMap<>	();
		this.activeTexture		= GL13.GL_TEXTURE0;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		var helper = device.getGlobalContext().getGlobalHelper();

		for (var blockType : GLBufferBlockType.values()) {
			this.boundBufferBlocks.put(blockType, new BufferBlockBinding(helper.getBufferMaxBindings(blockType)));
		}

		for (var textureUnit = 0; textureUnit < helper.getMaxTextureUnits(); textureUnit ++) {
			this.boundTextures.put(textureUnit, new Reference2IntOpenHashMap<>());
		}
	}

	@Override
	public int getPixelStorageMode(GLPixelParameter parameter) {
		return pixelStorageModes.getOrDefault(parameter, 0);
	}

	@Override
	public boolean getPackSwapBytes() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_SWAP_BYTES, 0) == GL11.GL_TRUE;
	}

	@Override
	public boolean getPackLSBFirst() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_LSB_FIRST, 0) == GL11.GL_TRUE;
	}

	@Override
	public int getPackRowLength() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_ROW_LENGTH, 0);
	}

	@Override
	public int getPackImageHeight() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_IMAGE_HEIGHT, 0);
	}

	@Override
	public int getPackSkipRows() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_SKIP_ROWS, 0);
	}

	@Override
	public int getPackSkipPixels() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_SKIP_PIXELS, 0);
	}

	@Override
	public int getPackSkipImages() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_SKIP_IMAGES, 0);
	}

	@Override
	public int getPackAlignment() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.PACK_ALIGNMENT, 4);
	}

	@Override
	public boolean getUnpackSwapBytes() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_SWAP_BYTES, 0) == GL11.GL_TRUE;
	}

	@Override
	public boolean getUnpackLSBFirst() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_LSB_FIRST, 0) == GL11.GL_TRUE;
	}

	@Override
	public int getUnpackRowLength() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_ROW_LENGTH, 0);
	}

	@Override
	public int getUnpackImageHeight() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_IMAGE_HEIGHT, 0);
	}

	@Override
	public int getUnpackSkipRows() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_SKIP_ROWS, 0);
	}

	@Override
	public int getUnpackSkipPixels() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_SKIP_PIXELS, 0);
	}

	@Override
	public int getUnpackSkipImages() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_SKIP_IMAGES, 0);
	}

	@Override
	public int getUnpackAlignment() {
		return pixelStorageModes.getOrDefault(GLPixelParameter.UNPACK_ALIGNMENT, 4);
	}

	@Override
	public void pixelStore(int pixelParameter, int value) {
		pixelStorageModes.put(GLPixelParameter.fromConstant(pixelParameter), value);
	}

	@Override
	public int getActiveTexture() {
		return activeTexture;
	}

	@Override
	public int getBoundTexture(GLTextureType textureType) {
		return boundTextures.get(activeTexture - GL13.GL_TEXTURE0).getOrDefault(textureType, 0);
	}

	@Override
	public int getBoundSampler(int textureUnit) {
		return boundSamplers.getOrDefault(textureUnit, 0);
	}

	@Override
	public int getBoundFramebuffer(GLFramebufferType framebufferType) {
		return boundFramebuffers.getOrDefault(framebufferType, 0);
	}

	@Override
	public int getBoundBuffer(GLBufferType bufferType) {
		return boundBuffers.getOrDefault(bufferType, 0);
	}

	@Override
	public int getBoundBuffer(GLBufferBlockType bufferBlockType, int index) {
		return boundBufferBlocks.get(bufferBlockType).getBoundBuffer(index);
	}

	@Override
	public long getBoundBufferOffset(GLBufferBlockType bufferBlockType, int index) {
		return boundBufferBlocks.get(bufferBlockType).getBoundOffset(index);
	}

	@Override
	public long getBoundBufferLength(GLBufferBlockType bufferBlockType, int index) {
		return boundBufferBlocks.get(bufferBlockType).getBoundLength(index);
	}

	@Override
	public void activeTexture(int textureUnit) {
		activeTexture = textureUnit;
	}

	@Override
	public void bindTexture(int textureTarget, int textureHandle) {
		boundTextures.get(activeTexture - GL13.GL_TEXTURE0).put(GLTextureType.fromConstant(textureTarget), textureHandle);
	}

	@Override
	public void bindSampler(int textureUnit, int samplerHandle) {
		boundSamplers.put(textureUnit, samplerHandle);
	}

	@Override
	public void bindFramebuffer(int framebufferTarget, int framebufferHandle) {
		boundFramebuffers.put(GLFramebufferType.fromBindingConstant(framebufferTarget), framebufferHandle);
	}

	@Override
	public void bindBuffer(int bufferTarget, int bufferHandle) {
		boundBuffers.put(GLBufferType.fromTypeConstant(bufferTarget), bufferHandle);
	}

	@Override
	public void bindBufferBase(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			GLBufferType	bufferType
	) {
		boundBufferBlocks.get(GLBufferBlockType.fromTypeConstant(bufferTarget)).bind(
				bufferTargetIndex,
				bufferHandle,
				0L,
				0L
		);
	}

	@Override
	public void bindBufferRange(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			long			bufferBindOffset,
			long			bufferBindSize,
			GLBufferType	bufferType
	) {
		boundBufferBlocks.get(GLBufferBlockType.fromTypeConstant(bufferTarget)).bind(
				bufferTargetIndex,
				bufferHandle,
				bufferBindOffset,
				bufferBindSize
		);
	}

	@Override
	public void bindBuffersBase(
			int				bufferTarget,
			int				bufferTargetFirstIndex,
			int				bufferCounts,
			long			bufferHandlesAddress,
			GLBufferType	bufferType
	) {
		try (var bufferHandlesView = StackDataView.asDataView(bufferHandlesAddress, (long) bufferCounts * Integer.BYTES)) {
			for (var index = 0; index < bufferCounts; index ++) {
				bindBufferBase(
						bufferTarget,
						bufferTargetFirstIndex + index,
						bufferHandlesView.getInt(index),
						bufferType
				);
			}
		}
	}

	@Override
	public void bindBuffersRange(
			int				bufferTarget,
			int				bufferTargetFirstIndex,
			int				bufferCounts,
			long			bufferHandlesAddress,
			long			bufferOffsetsAddress,
			long			bufferLengthsAddress,
			GLBufferType	bufferType
	) {
		try (var bufferHandlesView = StackDataView.asDataView(bufferHandlesAddress, (long) bufferCounts * Integer	.BYTES);
		     var bufferOffsetsView = StackDataView.asDataView(bufferOffsetsAddress, (long) bufferCounts * Long		.BYTES);
		     var bufferLengthsView = StackDataView.asDataView(bufferLengthsAddress, (long) bufferCounts * Long		.BYTES)
		) {
			for (var index = 0; index < bufferCounts; index ++) {
				bindBufferRange(
						bufferTarget,
						bufferTargetFirstIndex + index,
						bufferHandlesView.getInt	(index),
						bufferOffsetsView.getLong	(index),
						bufferLengthsView.getLong	(index),
						bufferType
				);
			}
		}
	}

	private static class BufferBlockBinding {

		public final int		maxBindings;
		public final int	[]	boundBuffers;
		public final long	[]	boundOffsets;
		public final long	[]	boundLengths;

		public BufferBlockBinding(int maxBindings) {
			this.maxBindings	= maxBindings;
			this.boundBuffers	= new int	[maxBindings];
			this.boundOffsets	= new long	[maxBindings];
			this.boundLengths	= new long	[maxBindings];
		}

		public void bind(
				int		index,
				int		bufferHandle,
				long	bindOffset,
				long	bindLength
		) {
			if (index < maxBindings) {
				boundBuffers[index] = bufferHandle;
				boundOffsets[index] = bindOffset;
				boundLengths[index] = bindLength;
			}
		}

		public int getBoundBuffer(int index) {
			return index >= maxBindings ? 0 : boundBuffers[index];
		}

		public long getBoundOffset(int index) {
			return index >= maxBindings ? 0 : boundOffsets[index];
		}

		public long getBoundLength(int index) {
			return index >= maxBindings ? 0 : boundLengths[index];
		}
	}
}
