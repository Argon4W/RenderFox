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

package com.github.argon4w.renderfox.opengl.texture.function;

import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.dsa.IDirectStateAccess;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import org.lwjgl.opengl.*;

public class GLTextureFunctionsDirect implements IGLTextureFunctions {

	private IDirectStateAccess	directStateAccess;
	private IGLBindingSource	bindingSource;

	public GLTextureFunctionsDirect() {
		this.directStateAccess	= null;
		this.bindingSource		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		directStateAccess	= device.getDirectStateAccess	();
		bindingSource		= device.getBindingSource		();
	}

	@Override
	public int reserveTexture() {
		return directStateAccess.reserveTexture();
	}

	@Override
	public int createTexture(int textureTarget, GLTextureType textureType) {
		return directStateAccess.createTexture(textureTarget, textureType);
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
		directStateAccess.textureImage1D(
				textureHandle,
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
		);
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
		directStateAccess.textureImage2D(
				textureHandle,
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
		);
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
		directStateAccess.textureImage3D(
				textureHandle,
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
		);
	}

	@Override
	public void textureImage2DMultisample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	) {
		directStateAccess.textureImage2DMultisample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureUseFixedSampleLocations,
				textureType
		);
	}

	@Override
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
		directStateAccess.textureImage3DMultisample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations,
				textureType
		);
	}

	@Override
	public void textureStorage1D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			GLTextureType	textureType
	) {
		directStateAccess.textureStorage1D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureType
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
		directStateAccess.textureStorage2D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureType
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
		directStateAccess.textureStorage3D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureType
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
		directStateAccess.textureStorage2DMultiSample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureUseFixedSampleLocations,
				textureType
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
		directStateAccess.textureStorage3DMultiSample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations,
				textureType
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
		directStateAccess.textureSubImage1D(
				textureHandle,
				textureMipLevel,
				imageWritePositionX,
				imageWriteWidth,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
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
		directStateAccess.textureSubImage2D(
				textureHandle,
				textureMipLevel,
				imageWritePositionX,
				imageWritePositionY,
				imageWriteWidth,
				imageWriteHeight,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
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
		directStateAccess.textureSubImage3D(
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
				imageDataAddress,
				textureType
		);
	}

	@Override
	public void textureParameterf(
			int				textureHandle,
			int				textureParameter,
			float			textureParameterValue,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameterf(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		);
	}

	@Override
	public void textureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameterfv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		);
	}

	@Override
	public void textureParameteri(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameteri(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		);
	}

	@Override
	public void textureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameteriv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		);
	}

	@Override
	public void textureParameterIi(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameterIi(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		);
	}

	@Override
	public void textureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameterIiv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		);
	}

	@Override
	public void textureParameterIui(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameterIui(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		);
	}

	@Override
	public void textureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.textureParameterIuiv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		);
	}

	@Override
	public void generateTextureMipmap(int textureHandle, GLTextureType textureType) {
		directStateAccess.generateTextureMipmap(textureHandle, textureType);
	}

	@Override
	public void bindTextureUnit(
			int				textureUnit,
			int				textureHandle,
			GLTextureType	textureType
	) {
		directStateAccess.bindTextureUnit(
				textureUnit,
				textureHandle,
				textureType
		);
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
		directStateAccess.getTextureImage(
				textureHandle,
				textureMipLevel,
				outDataFormat,
				outDataType,
				outDataSize,
				outDataAddress,
				textureType
		);
	}

	@Override
	public float getTextureLevelParameterf(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		return directStateAccess.getTextureLevelParameterf(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				textureType
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
		directStateAccess.getTextureLevelParameterfv(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				outDataAddress,
				textureType
		);
	}

	@Override
	public int getTextureLevelParameteri(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		return directStateAccess.getTextureLevelParameteri(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				textureType
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
		directStateAccess.getTextureLevelParameteriv(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				outDataAddress,
				textureType
		);
	}

	@Override
	public float getTextureParameterf(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return directStateAccess.getTextureParameterf(
				textureHandle,
				textureParameter,
				textureType
		);
	}

	@Override
	public void getTextureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.getTextureParameterfv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		);
	}

	@Override
	public int getTextureParameteri(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return directStateAccess.getTextureParameteri(
				textureHandle,
				textureParameter,
				textureType
		);
	}

	@Override
	public void getTextureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.getTextureParameteriv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		);
	}

	@Override
	public int getTextureParameterIi(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return directStateAccess.getTextureParameterIi(
				textureHandle,
				textureParameter,
				textureType
		);
	}

	@Override
	public void getTextureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.getTextureParameterIiv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		);
	}

	@Override
	public int getTextureParameterIui(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return directStateAccess.getTextureParameterIui(
				textureHandle,
				textureParameter,
				textureType
		);
	}

	@Override
	public void getTextureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		directStateAccess.getTextureParameterIuiv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		);
	}

	@Override
	public boolean isTexture(int textureHandle) {
		return GL11.glIsTexture(textureHandle);
	}

	@Override
	public void deleteTexture(int textureHandle) {
		GL11.glDeleteTextures(textureHandle);
	}

	@Override
	public void bindTexture(int textureTarget, int texture) {
		GL11.glBindTexture(textureTarget, texture);

		bindingSource.bindTexture(textureTarget, texture);
	}

	@Override
	public void clearTextureImage(
			int				textureHandle,
			int				clearMipLevel,
			int				clearDataFormat,
			int				clearDataType,
			long			clearDataAddress,
			GLTextureType	textureType
	) {
		ARBClearTexture.nglClearTexImage(
				textureHandle,
				clearMipLevel,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		);
	}

	@Override
	public void clearTextureSubImage(
			int				textureHandle,
			int				clearMipLevel,
			int				clearPositionX,
			int				clearPositionY,
			int				clearPositionZ,
			int				clearWidth,
			int				clearHeight,
			int				clearDepth,
			int				clearDataFormat,
			int				clearDataType,
			long			clearDataAddress,
			GLTextureType	textureType
	) {
		ARBClearTexture.nglClearTexSubImage(
				textureHandle,
				clearMipLevel,
				clearPositionX,
				clearPositionY,
				clearPositionZ,
				clearWidth,
				clearHeight,
				clearDepth,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		);
	}

	@Override
	public void getTextureSubImage(
			int				textureHandle,
			int				textureMipLevel,
			int				outPositionX,
			int				outPositionY,
			int				outPositionZ,
			int				outWidth,
			int				outHeight,
			int				outDepth,
			int				outDataFormat,
			int				outDataType,
			long			outDataSize,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		ARBGetTextureSubImage.glGetTextureSubImage(
				textureHandle,
				textureMipLevel,
				outPositionX,
				outPositionY,
				outPositionZ,
				outWidth,
				outHeight,
				outDepth,
				outDataFormat,
				outDataType,
				(int) outDataSize,
				outDataAddress
		);
	}

	@Override
	public void invalidateTextureImage(
			int				textureHandle,
			int				invalidateMipLevel,
			GLTextureType	textureType
	) {
		GL43.glInvalidateTexImage(textureHandle, invalidateMipLevel);
	}

	@Override
	public void invalidateTextureSubImage(
			int				textureHandle,
			int				invalidateMipLevel,
			int				invalidatePositionX,
			int				invalidatePositionY,
			int				invalidatePositionZ,
			int				invalidateWidth,
			int				invalidateHeight,
			int				invalidateDepth,
			GLTextureType	textureType
	) {
		GL43.glInvalidateTexSubImage(
				textureHandle,
				invalidateMipLevel,
				invalidatePositionX,
				invalidatePositionY,
				invalidatePositionZ,
				invalidateWidth,
				invalidateHeight,
				invalidateDepth
		);
	}

	@Override
	public void textureView(
			int				viewTextureHandle,
			int				viewTextureTarget,
			int				originalTextureHandle,
			int				viewInternalFormat,
			int				viewMinLevel,
			int				viewLevels,
			int				viewMinLayer,
			int				viewLayers,
			GLTextureType	viewTextureType,
			GLTextureType	originalTextureType
	) {
		ARBTextureView.glTextureView(
				viewTextureHandle,
				viewTextureTarget,
				originalTextureHandle,
				viewInternalFormat,
				viewMinLevel,
				viewLevels,
				viewMinLayer,
				viewLayers
		);
	}

	public static IGLTextureFunctions of() {
		return new GLTextureFunctionsDirect();
	}
}
