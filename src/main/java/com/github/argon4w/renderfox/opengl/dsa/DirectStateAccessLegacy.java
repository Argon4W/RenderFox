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

package com.github.argon4w.renderfox.opengl.dsa;

import com.github.argon4w.renderfox.opengl.buffer.GLBufferStateManager;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureStateManager;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureLevelParameter;
import com.github.argon4w.renderfox.opengl.texture.sampler.GLSamplerStateManager;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.List;
import java.util.Map;

public class DirectStateAccessLegacy implements IDirectStateAccess {

	private GLBufferStateManager	bufferStateManager;
	private GLTextureStateManager	textureStateManager;
	private GLSamplerStateManager	samplerStateManager;

	public DirectStateAccessLegacy() {
		this.bufferStateManager		= null;
		this.textureStateManager	= null;
		this.samplerStateManager	= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		this.bufferStateManager		= device.getBufferContext	().getBufferStateManager	();
		this.textureStateManager	= device.getTextureContext	().getTextureStateManager	();
		this.samplerStateManager	= device.getTextureContext	().getSamplerStateManager	();
	}

	@Override
	public int createBufferHandle(GLBufferType bufferType) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		int bufferHandle = GL15.glGenBuffers();

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> {});

		return bufferHandle;
	}

	@Override
	public void bufferStorage(
			int				bufferHandle,
			long			bufferSize,
			long			bufferDataAddress,
			int				storageFlags,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> ARBBufferStorage.nglBufferStorage(
				bufferType.getConstant(),
				bufferSize,
				bufferDataAddress,
				storageFlags
		));
	}

	@Override
	public void bufferData(
			int				bufferHandle,
			long			bufferDataSize,
			long			bufferDataAddress,
			int				bufferUsage,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.nglBufferData(
				bufferType.getConstant(),
				bufferDataSize,
				bufferDataAddress,
				bufferUsage
		));
	}

	@Override
	public void bufferSubData(
			int				bufferHandle,
			long			bufferDataOffset,
			long			bufferDataSize,
			long			bufferDataAddress,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.nglBufferSubData(
				bufferType.getConstant(),
				bufferDataOffset,
				bufferDataSize,
				bufferDataAddress
		));
	}

	@Override
	public void copyBufferSubData(
			int		bufferHandleRead,
			int		bufferHandleWrite,
			long	bufferCopyOffsetRead,
			long	bufferCopyOffsetWrite,
			long	bufferCopySize
	) {
		bufferStateManager.runScoped(
				Map.of(
						GLBufferType.COPY_READ_BUFFER,	bufferHandleRead,
						GLBufferType.COPY_WRITE_BUFFER,	bufferHandleWrite
				),
				() -> GL31.glCopyBufferSubData(
						GL31.GL_COPY_READ_BUFFER,
						GL31.GL_COPY_WRITE_BUFFER,
						bufferCopyOffsetRead,
						bufferCopyOffsetWrite,
						bufferCopySize
				)
		);
	}

	@Override
	public void clearBufferData(
			int				bufferHandle,
			int				bufferClearFormat,
			int				clearDataFormat,
			int				clearDataType,
			long			clearDataAddress,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> ARBClearBufferObject.nglClearBufferData(
				bufferType.getConstant(),
				bufferClearFormat,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		));
	}

	@Override
	public void clearBufferSubData(
			int				bufferHandle,
			int				bufferClearFormat,
			long			bufferClearOffset,
			long			bufferClearSize,
			int				clearDataFormat,
			int				clearDataType,
			long			clearDataAddress,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> ARBClearBufferObject.nglClearBufferSubData(
				bufferType.getConstant(),
				bufferClearFormat,
				bufferClearOffset,
				bufferClearSize,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		));
	}

	@Override
	public long mapBuffer(
			int				bufferHandle,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		return bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.nglMapBuffer(bufferType.getConstant(), mapAccess));
	}

	@Override
	public long mapBufferRange(
			int				bufferHandle,
			long			mapOffset,
			long			mapLength,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		return bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL30.nglMapBufferRange(
				bufferType.getConstant(),
				mapOffset,
				mapLength,
				mapAccess
		));
	}

	@Override
	public void unmapBuffer(int bufferHandle, GLBufferType bufferType) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.glUnmapBuffer(bufferType.getConstant()));
	}

	@Override
	public void flushMappedBufferRange(
			int				bufferHandle,
			long			flushOffset,
			long			flushLength,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL30.glFlushMappedBufferRange(
				bufferType.getConstant(),
				flushOffset,
				flushLength
		));
	}

	@Override
	public int getBufferParameteri(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		return bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.glGetBufferParameteri(bufferType.getConstant(), bufferParameter));
	}

	@Override
	public void getBufferParameteriv(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.nglGetBufferParameteriv(
				bufferType.getConstant(),
				bufferParameter,
				outDataAddress
		));
	}

	@Override
	public long getBufferParameteri64(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		return bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL32.glGetBufferParameteri64(bufferType.getConstant(), bufferParameter));
	}

	@Override
	public void getBufferParameteri64v(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL32.nglGetBufferParameteri64v(
				bufferType.getConstant(),
				bufferParameter,
				outDataAddress
		));
	}

	@Override
	public long getBufferPointer(
			int				bufferHandle,
			int				bufferPointer,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		return bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.glGetBufferPointer(bufferType.getConstant(), bufferPointer));
	}

	@Override
	public void getBufferPointerv(
			int				bufferHandle,
			int				bufferPointer,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.nglGetBufferPointerv(
				bufferType.getConstant(),
				bufferPointer,
				outDataAddress
		));
	}

	@Override
	public void getBufferSubData(
			int				bufferHandle,
			long			outOffset,
			long			outSize,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("nvalid bufferType.");
		}

		bufferStateManager.runScoped(Map.of(bufferType, bufferHandle), () -> GL15.nglGetBufferSubData(
				bufferType.getConstant(),
				outOffset,
				outSize,
				outDataAddress
		));
	}

	@Override
	public int createFramebuffer() {
		return ARBDirectStateAccess.glCreateFramebuffers();
	}

	@Override
	public void framebufferParameteri(
			int framebufferHandle,
			int framebufferParameter,
			int framebufferParameterValue
	) {
		ARBDirectStateAccess.glNamedFramebufferParameteri(
				framebufferHandle,
				framebufferParameter,
				framebufferParameterValue
		);
	}

	@Override
	public void framebufferTexture(
			int framebufferHandle,
			int framebufferAttachment,
			int textureHandle,
			int textureMipLevel
	) {
		ARBDirectStateAccess.glNamedFramebufferTexture(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureMipLevel
		);
	}

	@Override
	public void framebufferTextureLayer(
			int framebufferHandle,
			int framebufferAttachment,
			int textureHandle,
			int textureMipLevel,
			int framebufferLayer
	) {
		ARBDirectStateAccess.glNamedFramebufferTextureLayer(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureMipLevel,
				framebufferLayer
		);
	}

	@Override
	public void framebufferDrawBuffer(int framebufferHandle, int framebufferDrawBuffer) {
		ARBDirectStateAccess.glNamedFramebufferDrawBuffer(framebufferHandle, framebufferDrawBuffer);
	}

	@Override
	public void framebufferDrawBuffers(
			int		framebufferHandle,
			int		framebufferDrawBufferCount,
			long	framebufferDrawBufferDataAddress
	) {
		ARBDirectStateAccess.nglNamedFramebufferDrawBuffers(
				framebufferHandle,
				framebufferDrawBufferCount,
				framebufferDrawBufferDataAddress
		);
	}

	@Override
	public void framebufferReadBuffer(int framebufferHandle, int framebufferReadBuffer) {
		ARBDirectStateAccess.glNamedFramebufferReadBuffer(framebufferHandle, framebufferReadBuffer);
	}

	@Override
	public void invalidateFramebufferData(
			int		framebufferHandle,
			int		framebufferAttachmentCount,
			long	framebufferAttachmentDataAddress
	) {
		ARBDirectStateAccess.nglInvalidateNamedFramebufferData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress
		);
	}

	@Override
	public void invalidateFramebufferSubData(
			int		framebufferHandle,
			int		framebufferAttachmentCount,
			long	framebufferAttachmentDataAddress,
			int		invalidatePositionX,
			int		invalidatePositionY,
			int		invalidateWidth,
			int		invalidateHeight
	) {
		ARBDirectStateAccess.nglInvalidateNamedFramebufferSubData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress,
				invalidatePositionX,
				invalidatePositionY,
				invalidateWidth,
				invalidateHeight
		);
	}

	@Override
	public void clearFramebufferiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		ARBDirectStateAccess.nglClearNamedFramebufferiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferuiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		ARBDirectStateAccess.nglClearNamedFramebufferuiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferfv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		ARBDirectStateAccess.nglClearNamedFramebufferfv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferfi(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			float	clearDepth,
			int		clearStencil
	) {
		ARBDirectStateAccess.glClearNamedFramebufferfi(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDepth,
				clearStencil
		);
	}

	@Override
	public void blitFramebuffer(
			int framebufferHandleRead,
			int framebufferHandleWrite,
			int blitReadPositionXStart,
			int blitReadPositionYStart,
			int blitReadPositionXEnd,
			int blitReadPositionYEnd,
			int blitWritePositionXStart,
			int blitWritePositionYStart,
			int blitWritePositionXEnd,
			int blitWritePositionYEnd,
			int blitWriteBufferMask,
			int blitWriteFilter
	) {
		ARBDirectStateAccess.glBlitNamedFramebuffer(
				framebufferHandleRead,
				framebufferHandleWrite,
				blitReadPositionXStart,
				blitReadPositionYStart,
				blitReadPositionXEnd,
				blitReadPositionYEnd,
				blitWritePositionXStart,
				blitWritePositionYStart,
				blitWritePositionXEnd,
				blitWritePositionYEnd,
				blitWriteBufferMask,
				blitWriteFilter
		);
	}

	@Override
	public int checkFramebufferStatus(int framebufferHandle, int framebufferTarget) {
		return ARBDirectStateAccess.glCheckNamedFramebufferStatus(framebufferHandle, framebufferTarget);
	}

	@Override
	public void getFramebufferParameteriv(
			int		framebufferHandle,
			int		framebufferParameter,
			long	outDataAddress
	) {
		ARBDirectStateAccess.nglGetNamedFramebufferParameteriv(
				framebufferHandle,
				framebufferParameter,
				outDataAddress
		);
	}

	@Override
	public int getFramebufferParameteri(int framebufferHandle, int framebufferParameter) {
		return ARBDirectStateAccess.glGetNamedFramebufferParameteri(framebufferParameter, framebufferHandle);
	}

	@Override
	public void getFramebufferAttachmentParameteriv(
			int		framebufferHandle,
			int		framebufferAttachment,
			int		attachmentParameter,
			long	outDataAddress
	) {
		ARBDirectStateAccess.nglGetNamedFramebufferAttachmentParameteriv(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter,
				outDataAddress
		);
	}

	@Override
	public int getFramebufferAttachmentParameteri(
			int framebufferHandle,
			int framebufferAttachment,
			int attachmentParameter
	) {
		return ARBDirectStateAccess.glGetNamedFramebufferAttachmentParameteri(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter
		);
	}

	@Override
	public int reserveTexture() {
		return GL11.glGenTextures();
	}

	@Override
	public int createTexture(int textureTarget, GLTextureType textureType) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		var textureHandle = GL11.glGenTextures();

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> {});

		return textureHandle;
	}

	@Override
	public void textureImage1D(
			int				textureHandle,
			int				textureMipLevel,
			int				textureInternalFormat,
			int				textureWidth,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglTexImage1D(
				textureType.getConstant(),
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				0,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		));
	}

	@Override
	public void textureImage2D(
			int				textureHandle,
			int				textureMipLevel,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	) {
		var format	= GLFormat	.fromConstant(imageDataFormat);
		var type	= GLDataType.fromConstant(imageDataType);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid format.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid type.");
		}

		if (textureType == GLTextureType.TEXTURE_CUBE_MAP) {
			for (var i = 0; i < GLTextureType.CUBE_MAP_FACES.length; i ++) {
				var offset = textureWidth * textureHeight * format.getComponentCount() * type.getSize();
				var target = GLTextureType.CUBE_MAP_FACES[i];

				textureStateManager.runTextureScoped(Map.of(target, textureHandle), () -> GL11.nglTexImage2D(
						target.getConstant(),
						textureMipLevel,
						textureInternalFormat,
						textureWidth,
						textureHeight,
						0,
						imageDataFormat,
						imageDataType,
						imageDataAddress + offset
				));
			}

			return;
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglTexImage2D(
				textureType.getConstant(),
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				0,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		));
	}

	@Override
	public void textureImage3D(
			int				textureHandle,
			int				textureMipLevel,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				textureDepth,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL12.nglTexImage3D(
				textureType.getConstant(),
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				0,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		));
	}

	public void textureImage2DMultisample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL32.glTexImage2DMultisample(
				textureType.getConstant(),
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureUseFixedSampleLocations
		));
	}

	public void textureImage3DMultisample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				textureDepth,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL32.glTexImage3DMultisample(
				textureType.getConstant(),
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations
		));
	}

	@Override
	public void textureStorage1D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> ARBTextureStorage.glTexStorage1D(
				textureType.getConstant(),
				textureMipLevels,
				textureInternalFormat,
				textureWidth
		));
	}

	@Override
	public void textureStorage2D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> ARBTextureStorage.glTexStorage2D(
				textureType.getConstant(),
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight
		));
	}

	@Override
	public void textureStorage3D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				textureDepth,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> ARBTextureStorage.glTexStorage3D(
				textureType.getConstant(),
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth
		));
	}

	@Override
	public void textureStorage2DMultiSample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> ARBTextureStorageMultisample.glTexStorage2DMultisample(
				textureType.getConstant(),
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureUseFixedSampleLocations
		));
	}

	@Override
	public void textureStorage3DMultiSample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				textureDepth,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> ARBTextureStorageMultisample.glTexStorage3DMultisample(
				textureType.getConstant(),
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations
		));
	}

	@Override
	public void textureSubImage1D(
			int				textureHandle,
			int				textureMipLevel,
			int				imageWritePositionX,
			int				imageWriteWidth,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.glTexSubImage1D(
				textureType.getConstant(),
				textureMipLevel,
				imageWritePositionX,
				imageWriteWidth,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		));
	}

	@Override
	public void textureSubImage2D(
			int				textureHandle,
			int				textureMipLevel,
			int				imageWritePositionX,
			int				imageWritePositionY,
			int				imageWriteWidth,
			int				imageWriteHeight,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.glTexSubImage2D(
				textureType.getConstant(),
				textureMipLevel,
				imageWritePositionX,
				imageWritePositionY,
				imageWriteWidth,
				imageWriteHeight,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		));
	}

	@Override
	public void textureSubImage3D(
			int				textureHandle,
			int				textureMipLevel,
			int				imageWritePositionX,
			int				imageWritePositionY,
			int				imageWritePositionZ,
			int				imageWriteWidth,
			int				imageWriteHeight,
			int				imageWriteDepth,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		if (textureType == GLTextureType.TEXTURE_CUBE_MAP) {
			if (imageWritePositionZ < 0) {
				throw new IllegalArgumentException("The effective target is GL_TEXTURE_CUBE_MAP and imageWritePositionZ is negative.");
			}

			if (imageWritePositionZ > GLTextureType.CUBE_MAP_FACES.length) {
				throw new IllegalArgumentException("The effective target is GL_TEXTURE_CUBE_MAP and imageWritePositionZ is greater than 6.");
			}

			var target = GLTextureType.CUBE_MAP_FACES[imageWritePositionZ];

			textureStateManager.runTextureScoped(Map.of(target, textureHandle), () -> GL12.glTexSubImage2D(
					target.getConstant(),
					textureMipLevel,
					imageWritePositionX,
					imageWritePositionY,
					imageWriteWidth,
					imageWriteHeight,
					imageDataFormat,
					imageDataType,
					imageDataAddress
			));

			return;
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL12.glTexSubImage3D(
				textureType.getConstant(),
				textureMipLevel,
				imageWritePositionX,
				imageWritePositionY,
				imageWritePositionZ,
				imageWriteWidth,
				imageWriteHeight,
				imageWriteDepth,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		));
	}

	@Override
	public void textureParameterf(
			int				textureHandle,
			int				textureParameter,
			float			textureParameterValue,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.glTexParameterf(
				textureType.getConstant(),
				textureParameter,
				textureParameterValue
		));
	}

	@Override
	public void textureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglTexParameterfv(
				textureType.getConstant(),
				textureParameter,
				textureParameterDataAddress
		));
	}

	@Override
	public void textureParameteri(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.glTexParameteri(
				textureType.getConstant(),
				textureParameter,
				textureParameterValue
		));
	}

	@Override
	public void textureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglTexParameteriv(
				textureType.getConstant(),
				textureParameter,
				textureParameterDataAddress
		));
	}

	@Override
	public void textureParameterIi(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.glTexParameterIi(
				textureType.getConstant(),
				textureParameter,
				textureParameterValue
		));
	}

	@Override
	public void textureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.nglTexParameterIiv(
				textureType.getConstant(),
				textureParameter,
				textureParameterDataAddress
		));
	}

	@Override
	public void textureParameterIui(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.glTexParameterIui(
				textureType.getConstant(),
				textureParameter,
				textureParameterValue
		));
	}

	@Override
	public void textureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.nglTexParameterIuiv(
				textureType.getConstant(),
				textureParameter,
				textureParameterDataAddress
		));
	}

	@Override
	public void generateTextureMipmap(int textureHandle, GLTextureType textureType) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.glGenerateMipmap(textureType.getConstant()));
	}

	@Override
	public void bindTextureUnit(
			int				textureUnit,
			int				textureHandle,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runUnitScoped(textureUnit, () -> GL11.glBindTexture(textureType.getConstant(), textureHandle));
	}

	@Override
	public void getTextureImage(
			int				textureHandle,
			int				textureMipLevel,
			int				outDataFormat,
			int				outDataType,
			long			outDataSize,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		var format	= GLFormat	.fromConstant(outDataFormat);
		var type	= GLDataType.fromConstant(outDataType);
		
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}
		
		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid format.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid type.");
		}

		if (textureType == GLTextureType.TEXTURE_CUBE_MAP) {
			var width	= getTextureLevelParameteri(textureHandle, textureMipLevel, GLTextureLevelParameter.TEXTURE_WIDTH	.getConstant(), textureType);
			var height	= getTextureLevelParameteri(textureHandle, textureMipLevel, GLTextureLevelParameter.TEXTURE_HEIGHT	.getConstant(), textureType);

			for (var i = 0; i < GLTextureType.CUBE_MAP_FACES.length; i ++) {
				var offset = width * height * format.getComponentCount() * type.getSize() * i;
				var target = GLTextureType.CUBE_MAP_FACES[i];

				textureStateManager.runTextureScoped(Map.of(target, textureHandle), () -> GL11.nglGetTexImage(
						target.getConstant(),
						textureMipLevel,
						outDataFormat,
						outDataType,
						outDataAddress + offset
				));
			}

			return;
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglGetTexImage(
				textureType.getConstant(),
				textureMipLevel,
				outDataFormat,
				outDataType,
				outDataAddress
		));
	}

	@Override
	public float getTextureLevelParameterf(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		return textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.glGetTexLevelParameterf(
				textureType.getConstant(),
				textureMipLevel,
				textureLevelParameter
		));
	}

	@Override
	public void getTextureLevelParameterfv(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglGetTexLevelParameterfv(
				textureType.getConstant(),
				textureMipLevel,
				textureLevelParameter,
				outDataAddress
		));
	}

	@Override
	public int getTextureLevelParameteri(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		return textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.glGetTexLevelParameteri(
				textureType.getConstant(),
				textureMipLevel,
				textureLevelParameter
		));
	}

	@Override
	public void getTextureLevelParameteriv(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglGetTexLevelParameteriv(
				textureType.getConstant(),
				textureMipLevel,
				textureLevelParameter,
				outDataAddress
		));
	}

	@Override
	public float getTextureParameterf(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		return textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.glGetTexParameterf(textureType.getConstant(), textureParameter));
	}

	@Override
	public void getTextureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.nglGetTexParameterfv(
				textureType.getConstant(),
				textureParameter,
				outDataAddress
		));
	}

	@Override
	public int getTextureParameteri(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		return textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.glGetTexParameteri(textureType.getConstant(), textureParameter));
	}

	@Override
	public void getTextureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL11.nglGetTexParameteriv(
				textureType.getConstant(),
				textureParameter,
				outDataAddress
		));
	}

	@Override
	public int getTextureParameterIi(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		return textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.glGetTexParameterIi(textureType.getConstant(), textureParameter));
	}

	@Override
	public void getTextureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.nglGetTexParameterIiv(
				textureType.getConstant(),
				textureParameter,
				outDataAddress
		));
	}

	@Override
	public int getTextureParameterIui(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		return textureStateManager.runTextureScoped(Map.of(textureType, textureHandle), () -> GL30.glGetTexParameterIui(textureType.getConstant(), textureParameter));
	}

	@Override
	public void getTextureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		textureStateManager.runTextureScoped(List.of(textureType), () -> GL30.nglGetTexParameterIuiv(
				textureType.getConstant(),
				textureParameter,
				outDataAddress
		));
	}

	@Override
	public int createVertexArray() {
		return ARBDirectStateAccess.glCreateVertexArrays();
	}

	@Override
	public void disableVertexArrayAttrib(int vertexArrayHandle, int index) {
		ARBDirectStateAccess.glDisableVertexArrayAttrib(vertexArrayHandle, index);
	}

	@Override
	public void enableVertexArrayAttrib(int vertexArrayHandle, int index) {
		ARBDirectStateAccess.glEnableVertexArrayAttrib(vertexArrayHandle, index);
	}

	@Override
	public void vertexArrayElementBuffer(int vertexArrayHandle, int elementBufferHandle) {
		ARBDirectStateAccess.glVertexArrayElementBuffer(vertexArrayHandle, elementBufferHandle);
	}

	@Override
	public void vertexArrayVertexBuffer(
			int vertexArrayHandle,
			int vertexBufferBindingIndex,
			int vertexBufferHandle,
			int vertexBufferOffset,
			int vertexBufferStride
	) {
		ARBDirectStateAccess.glVertexArrayVertexBuffer(
				vertexArrayHandle,
				vertexBufferBindingIndex,
				vertexBufferHandle,
				vertexBufferOffset,
				vertexBufferStride
		);
	}

	@Override
	public void vertexArrayVertexBuffers(
			int				vertexArrayHandle,
			int				vertexArrayFirstBindingIndex,
			IntBuffer		vertexBufferHandleData,
			PointerBuffer	vertexBufferOffsetData,
			IntBuffer		vertexBufferStrideData
	) {
		ARBDirectStateAccess.glVertexArrayVertexBuffers(
				vertexArrayHandle,
				vertexArrayFirstBindingIndex,
				vertexBufferHandleData,
				vertexBufferOffsetData,
				vertexBufferStrideData
		);
	}

	@Override
	public void vertexArrayVertexBuffers(
			int		vertexArrayHandle,
			int		vertexArrayFirstBindingIndex,
			int		vertexArrayBindingIndexCount,
			long	vertexBufferHandleDataAddress,
			long	vertexBufferOffsetDataAddress,
			long	vertexBufferStrideDataAddress
	) {
		ARBDirectStateAccess.nglVertexArrayVertexBuffers(
				vertexArrayHandle,
				vertexArrayFirstBindingIndex,
				vertexArrayBindingIndexCount,
				vertexBufferHandleDataAddress,
				vertexBufferOffsetDataAddress,
				vertexBufferStrideDataAddress
		);
	}

	@Override
	public void vertexArrayAttribFormat(
			int		vertexArrayHandle,
			int		vertexAttributeIndex,
			int		vertexAttributeSize,
			int		vertexAttributeInputType,
			boolean	vertexAttributeNormalized,
			int		vertexAttributeOffset
	) {
		ARBDirectStateAccess.glVertexArrayAttribFormat(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeSize,
				vertexAttributeInputType,
				vertexAttributeNormalized,
				vertexAttributeOffset
		);
	}

	@Override
	public void vertexArrayAttribIFormat(
			int vertexArrayHandle,
			int vertexAttributeIndex,
			int vertexAttributeSize,
			int vertexAttributeInputType,
			int vertexAttributeOffset
	) {
		ARBDirectStateAccess.glVertexArrayAttribIFormat(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeSize,
				vertexAttributeInputType,
				vertexAttributeOffset
		);
	}

	@Override
	public void vertexArrayAttribLFormat(
			int vertexArrayHandle,
			int vertexAttributeIndex,
			int vertexAttributeSize,
			int vertexAttributeInputType,
			int vertexAttributeOffset
	) {
		ARBDirectStateAccess.glVertexArrayAttribLFormat(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeSize,
				vertexAttributeInputType,
				vertexAttributeOffset
		);
	}

	@Override
	public void vertexArrayAttribBinding(
			int vertexArrayHandle,
			int vertexAttributeIndex,
			int vertexBufferBindingIndex
	) {
		ARBDirectStateAccess.glVertexArrayAttribBinding(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexBufferBindingIndex
		);
	}

	@Override
	public void vertexArrayBindingDivisor(
			int vertexArrayHandle,
			int vertexBufferBindingIndex,
			int vertexBufferDivisor
	) {
		ARBDirectStateAccess.glVertexArrayBindingDivisor(
				vertexArrayHandle,
				vertexBufferBindingIndex,
				vertexBufferDivisor
		);
	}

	@Override
	public void getVertexArrayiv(
			int			vertexArrayHandle,
			int			vertexArrayParameter,
			IntBuffer	outData
	) {
		ARBDirectStateAccess.glGetVertexArrayiv(
				vertexArrayHandle,
				vertexArrayParameter,
				outData
		);
	}

	@Override
	public void getVertexArrayiv(
			int		vertexArrayHandle,
			int		vertexArrayParameter,
			long	outDataAddress
	) {
		ARBDirectStateAccess.nglGetVertexArrayiv(
				vertexArrayHandle,
				vertexArrayParameter,
				outDataAddress
		);
	}

	@Override
	public int getVertexArrayi(int vertexArrayHandle, int vertexArrayParameter) {
		return ARBDirectStateAccess.glGetVertexArrayi(vertexArrayHandle, vertexArrayParameter);
	}

	@Override
	public void getVertexArrayIndexediv(
			int			vertexArrayHandle,
			int			vertexAttributeIndex,
			int			vertexAttributeParameter,
			IntBuffer	outData
	) {
		ARBDirectStateAccess.glGetVertexArrayIndexediv(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeParameter,
				outData
		);
	}

	@Override
	public void getVertexArrayIndexediv(
			int		vertexArrayHandle,
			int		vertexAttributeIndex,
			int		vertexAttributeParameter,
			long	outDataAddress
	) {
		ARBDirectStateAccess.nglGetVertexArrayIndexediv(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeParameter,
				outDataAddress
		);
	}

	@Override
	public int getVertexArrayIndexedi(
			int vertexArrayHandle,
			int vertexAttributeIndex,
			int vertexAttributeParameter
	) {
		return ARBDirectStateAccess.glGetVertexArrayIndexedi(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeParameter
		);
	}

	@Override
	public void getVertexArrayIndexed64iv(
			int			vertexArrayHandle,
			int			vertexAttributeIndex,
			int			vertexAttributeParameter,
			LongBuffer	outData
	) {
		ARBDirectStateAccess.glGetVertexArrayIndexed64iv(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeParameter,
				outData
		);
	}

	@Override
	public void getVertexArrayIndexed64iv(
			int		vertexArrayHandle,
			int		vertexAttributeIndex,
			int		vertexAttributeParameter,
			long	outDataAddress
	) {
		ARBDirectStateAccess.nglGetVertexArrayIndexed64iv(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeParameter,
				outDataAddress
		);
	}

	@Override
	public long getVertexArrayIndexed64i(
			int vertexArrayHandle,
			int vertexAttributeIndex,
			int vertexAttributeParameter
	) {
		return ARBDirectStateAccess.glGetVertexArrayIndexed64i(
				vertexArrayHandle,
				vertexAttributeIndex,
				vertexAttributeParameter
		);
	}

	@Override
	public int createSampler() {
		var samplerHandle = GL33.glGenSamplers();

		samplerStateManager.runScoped(Map.of(0, samplerHandle), () -> {});

		return samplerHandle;
	}
}
