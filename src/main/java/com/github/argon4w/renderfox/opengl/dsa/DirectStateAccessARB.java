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

import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBDirectStateAccess;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public class DirectStateAccessARB extends DirectStateAccessLegacy {

	public DirectStateAccessARB() {
		super();
	}

	@Override
	public int createBufferHandle(GLBufferType bufferType) {
		return ARBDirectStateAccess.glCreateBuffers();
	}

	@Override
	public void bufferStorage(
			int				bufferHandle,
			long			bufferSize,
			long			bufferDataAddress,
			int				storageFlags,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.nglNamedBufferStorage(
				bufferHandle,
				bufferSize,
				bufferDataAddress,
				storageFlags
		);
	}

	@Override
	public void bufferData(
			int				bufferHandle,
			long			bufferDataSize,
			long			bufferDataAddress,
			int				bufferUsage,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.nglNamedBufferData(
				bufferHandle,
				bufferDataSize,
				bufferDataAddress,
				bufferUsage
		);
	}

	@Override
	public void bufferSubData(
			int				bufferHandle,
			long			bufferDataOffset,
			long			bufferDataSize,
			long			bufferDataAddress,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.nglNamedBufferSubData(
				bufferHandle,
				bufferDataOffset,
				bufferDataSize,
				bufferDataAddress
		);
	}

	@Override
	public void copyBufferSubData(
			int		bufferHandleRead,
			int		bufferHandleWrite,
			long	bufferCopyOffsetRead,
			long	bufferCopyOffsetWrite,
			long	bufferCopySize
	) {
		ARBDirectStateAccess.glCopyNamedBufferSubData(
				bufferHandleRead,
				bufferHandleWrite,
				bufferCopyOffsetRead,
				bufferCopyOffsetWrite,
				bufferCopySize
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
		ARBDirectStateAccess.nglClearNamedBufferData(
				bufferHandle,
				bufferClearFormat,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		);
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
		ARBDirectStateAccess.nglClearNamedBufferSubData(
				bufferHandle,
				bufferClearFormat,
				bufferClearOffset,
				bufferClearSize,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		);
	}

	@Override
	public long mapBuffer(
			int				bufferHandle,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		return ARBDirectStateAccess.nglMapNamedBuffer(bufferHandle, mapAccess);
	}

	@Override
	public long mapBufferRange(
			int				bufferHandle,
			long			mapOffset,
			long			mapLength,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		return ARBDirectStateAccess.nglMapNamedBufferRange(
				bufferHandle,
				mapOffset,
				mapLength,
				mapAccess
		);
	}

	@Override
	public void unmapBuffer(int bufferHandle, GLBufferType bufferType) {
		ARBDirectStateAccess.glUnmapNamedBuffer(bufferHandle);
	}

	@Override
	public void flushMappedBufferRange(
			int				bufferHandle,
			long			flushOffset,
			long			flushLength,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.glFlushMappedNamedBufferRange(
				bufferHandle,
				flushOffset,
				flushLength
		);
	}

	@Override
	public int getBufferParameteri(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		return ARBDirectStateAccess.glGetNamedBufferParameteri(bufferHandle, bufferParameter);
	}

	@Override
	public void getBufferParameteriv(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.nglGetNamedBufferParameteriv(
				bufferHandle,
				bufferParameter,
				outDataAddress
		);
	}

	@Override
	public long getBufferParameteri64(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		return ARBDirectStateAccess.glGetNamedBufferParameteri64(bufferHandle, bufferParameter);
	}

	@Override
	public void getBufferParameteri64v(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.nglGetNamedBufferParameteri64v(
				bufferHandle,
				bufferParameter,
				outDataAddress
		);
	}

	@Override
	public long getBufferPointer(
			int				bufferHandle,
			int				bufferPointer,
			GLBufferType	bufferType
	) {
		return ARBDirectStateAccess.glGetNamedBufferPointer(bufferHandle, bufferPointer);
	}

	@Override
	public void getBufferPointerv(
			int				bufferHandle,
			int				bufferPointer,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.nglGetNamedBufferPointerv(
				bufferHandle,
				bufferPointer,
				outDataAddress
		);
	}

	@Override
	public void getBufferSubData(
			int				bufferHandle,
			long			getOffset,
			long			outSize,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		ARBDirectStateAccess.nglGetNamedBufferSubData(
				bufferHandle,
				getOffset,
				outSize,
				outDataAddress
		);
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
	public int createTexture(int textureTarget, GLTextureType textureType) {
		return ARBDirectStateAccess.glCreateTextures(textureTarget);
	}

	@Override
	public void textureStorage1D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.glTextureStorage1D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth
		);
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
		ARBDirectStateAccess.glTextureStorage2D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight
		);
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
		ARBDirectStateAccess.glTextureStorage3D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth
		);
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
		ARBDirectStateAccess.glTextureStorage2DMultisample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureUseFixedSampleLocations
		);
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
		ARBDirectStateAccess.glTextureStorage3DMultisample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations
		);
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
		ARBDirectStateAccess.glTextureSubImage1D(
				textureHandle,
				textureMipLevel,
				imageWritePositionX,
				imageWriteWidth,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		);
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
		ARBDirectStateAccess.glTextureSubImage2D(
				textureHandle,
				textureMipLevel,
				imageWritePositionX,
				imageWritePositionY,
				imageWriteWidth,
				imageWriteHeight,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		);
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
		ARBDirectStateAccess.glTextureSubImage3D(
				textureHandle,
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
		);
	}

	@Override
	public void textureParameterf(
			int				textureHandle,
			int				textureParameter,
			float			textureParameterValue,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.glTextureParameterf(
				textureHandle,
				textureParameter,
				textureParameterValue
		);
	}

	@Override
	public void textureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglTextureParameterfv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress
		);
	}

	@Override
	public void textureParameteri(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.glTextureParameteri(
				textureHandle,
				textureParameter,
				textureParameterValue
		);
	}

	@Override
	public void textureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglTextureParameteriv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress
		);
	}

	@Override
	public void textureParameterIi(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.glTextureParameterIi(
				textureHandle,
				textureParameter,
				textureParameterValue
		);
	}

	@Override
	public void textureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglTextureParameterIiv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress
		);
	}

	@Override
	public void textureParameterIui(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.glTextureParameterIui(
				textureHandle,
				textureParameter,
				textureParameterValue
		);
	}

	@Override
	public void textureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglTextureParameterIuiv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress
		);
	}

	@Override
	public void generateTextureMipmap(int textureHandle, GLTextureType textureType) {
		ARBDirectStateAccess.glGenerateTextureMipmap(textureHandle);
	}

	@Override
	public void bindTextureUnit(
			int				textureUnit,
			int				textureHandle,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.glBindTextureUnit(textureUnit, textureHandle);
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
		ARBDirectStateAccess.nglGetTextureImage(
				textureHandle,
				textureMipLevel,
				outDataFormat,
				outDataType,
				(int) outDataSize,
				outDataAddress
		);
	}

	@Override
	public float getTextureLevelParameterf(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		return ARBDirectStateAccess.glGetTextureLevelParameterf(
				textureHandle,
				textureMipLevel,
				textureLevelParameter
		);
	}

	@Override
	public void getTextureLevelParameterfv(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglGetTextureLevelParameterfv(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				outDataAddress
		);
	}

	@Override
	public int getTextureLevelParameteri(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		return ARBDirectStateAccess.glGetTextureLevelParameteri(
				textureHandle,
				textureMipLevel,
				textureLevelParameter
		);
	}

	@Override
	public void getTextureLevelParameteriv(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglGetTextureLevelParameteriv(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				outDataAddress
		);
	}

	@Override
	public float getTextureParameterf(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return ARBDirectStateAccess.glGetTextureParameterf(textureHandle, textureParameter);
	}

	@Override
	public void getTextureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglGetTextureParameterfv(
				textureHandle,
				textureParameter,
				outDataAddress
		);
	}

	@Override
	public int getTextureParameteri(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return ARBDirectStateAccess.glGetTextureParameteri(textureHandle, textureParameter);
	}

	@Override
	public void getTextureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglGetTextureParameteriv(
				textureHandle,
				textureParameter,
				outDataAddress
		);
	}

	@Override
	public int getTextureParameterIi(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return ARBDirectStateAccess.glGetTextureParameterIi(textureHandle, textureParameter);
	}

	@Override
	public void getTextureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglGetTextureParameterIiv(
				textureHandle,
				textureParameter,
				outDataAddress
		);
	}

	@Override
	public int getTextureParameterIui(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return ARBDirectStateAccess.glGetTextureParameterIui(textureHandle, textureParameter);
	}

	@Override
	public void getTextureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		ARBDirectStateAccess.nglGetTextureParameterIuiv(
				textureHandle,
				textureParameter,
				outDataAddress
		);
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
		return ARBDirectStateAccess.glCreateSamplers();
	}
}
