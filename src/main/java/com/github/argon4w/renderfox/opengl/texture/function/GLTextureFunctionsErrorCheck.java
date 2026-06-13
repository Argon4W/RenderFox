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

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.error.GLErrorChecker;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;

public class GLTextureFunctionsErrorCheck implements IGLTextureFunctions {

	private final	IGLTextureFunctions	textureFunctions;
	private			GLErrorChecker		errorChecker;

	public GLTextureFunctionsErrorCheck(IGLTextureFunctions textureFunctions) {
		this.textureFunctions	= textureFunctions;
		this.errorChecker		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		textureFunctions.initialize(device);

		errorChecker = device.getErrorChecker();
	}

	@Override
	public int reserveTexture() {
		return errorChecker.runChecked("reserveTexture", textureFunctions::reserveTexture);
	}

	@Override
	public int createTexture(int textureTarget, GLTextureType textureType) {
		return errorChecker.runChecked("createTexture", () -> textureFunctions.createTexture(textureTarget, textureType));
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
		errorChecker.runChecked("textureImage1D", () -> textureFunctions.textureImage1D(
				textureHandle,
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
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
		errorChecker.runChecked("textureImage2D", () -> textureFunctions.textureImage2D(
				textureHandle,
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
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
		errorChecker.runChecked("textureImage3D", () -> textureFunctions.textureImage3D(
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
		));
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
		errorChecker.runChecked("textureImage2DMultisample", () -> textureFunctions.textureImage2DMultisample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureUseFixedSampleLocations,
				textureType
		));
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
		errorChecker.runChecked("textureImage3DMultisample", () -> textureFunctions.textureImage3DMultisample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations,
				textureType
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
		errorChecker.runChecked("textureStorage1D", () -> textureFunctions.textureStorage1D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureType
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
		errorChecker.runChecked("textureStorage2D", () -> textureFunctions.textureStorage2D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureType
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
		errorChecker.runChecked("textureStorage3D", () -> textureFunctions.textureStorage3D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureType
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
		errorChecker.runChecked("textureStorage2DMultiSample", () -> textureFunctions.textureStorage2DMultiSample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureUseFixedSampleLocations,
				textureType
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
		errorChecker.runChecked("textureStorage3DMultiSample", () -> textureFunctions.textureStorage3DMultiSample(
				textureHandle,
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations,
				textureType
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
		errorChecker.runChecked("textureSubImage1D", () -> textureFunctions.textureSubImage1D(
				textureHandle,
				textureMipLevel,
				imageWritePositionX,
				imageWriteWidth,
				imageDataFormat,
				imageDataType,
				imageDataAddress,
				textureType
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
		errorChecker.runChecked("textureSubImage2D", () -> textureFunctions.textureSubImage2D(
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
		errorChecker.runChecked("textureSubImage3D", () -> textureFunctions.textureSubImage3D(
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
		));
	}

	@Override
	public void textureParameterf(
			int				textureHandle,
			int				textureParameter,
			float			textureParameterValue,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameterf", () -> textureFunctions.textureParameterf(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		));
	}

	@Override
	public void textureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameterfv", () -> textureFunctions.textureParameterfv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		));
	}

	@Override
	public void textureParameteri(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameteri", () -> textureFunctions.textureParameteri(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		));
	}

	@Override
	public void textureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameteriv", () -> textureFunctions.textureParameteriv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		));
	}

	@Override
	public void textureParameterIi(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameterIi", () -> textureFunctions.textureParameterIi(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		));
	}

	@Override
	public void textureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameterIiv", () -> textureFunctions.textureParameterIiv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		));
	}

	@Override
	public void textureParameterIui(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameterIui", () -> textureFunctions.textureParameterIui(
				textureHandle,
				textureParameter,
				textureParameterValue,
				textureType
		));
	}

	@Override
	public void textureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("textureParameterIuiv", () -> textureFunctions.textureParameterIuiv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		));
	}

	@Override
	public void generateTextureMipmap(int textureHandle, GLTextureType textureType) {
		errorChecker.runChecked("generateTextureMipmap", () -> textureFunctions.generateTextureMipmap(textureHandle, textureType));
	}

	@Override
	public void bindTextureUnit(
			int				textureUnit,
			int				textureHandle,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("bindTextureUnit", () -> textureFunctions.bindTextureUnit(
				textureUnit,
				textureHandle,
				textureType
		));
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
		errorChecker.runChecked("getTextureImage", () -> textureFunctions.getTextureImage(
				textureHandle,
				textureMipLevel,
				outDataFormat,
				outDataType,
				outDataSize,
				outDataAddress,
				textureType
		));
	}

	@Override
	public float getTextureLevelParameterf(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		return errorChecker.runChecked("getTextureLevelParameterf", () -> textureFunctions.getTextureLevelParameterf(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				textureType
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
		errorChecker.runChecked("getTextureLevelParameterfv", () -> textureFunctions.getTextureLevelParameterfv(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				outDataAddress,
				textureType
		));
	}

	@Override
	public int getTextureLevelParameteri(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	) {
		return errorChecker.runChecked("getTextureLevelParameteri", () -> textureFunctions.getTextureLevelParameteri(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				textureType
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
		errorChecker.runChecked("getTextureLevelParameteriv", () -> textureFunctions.getTextureLevelParameteriv(
				textureHandle,
				textureMipLevel,
				textureLevelParameter,
				outDataAddress,
				textureType
		));
	}

	@Override
	public float getTextureParameterf(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return errorChecker.runChecked("getTextureParameterf", () -> textureFunctions.getTextureParameterf(
				textureHandle,
				textureParameter,
				textureType
		));
	}

	@Override
	public void getTextureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("getTextureParameterfv", () -> textureFunctions.getTextureParameterfv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		));
	}

	@Override
	public int getTextureParameteri(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return errorChecker.runChecked("getTextureParameteri", () -> textureFunctions.getTextureParameteri(
				textureHandle,
				textureParameter,
				textureType
		));
	}

	@Override
	public void getTextureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("getTextureParameteriv", () -> textureFunctions.getTextureParameteriv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		));
	}

	@Override
	public int getTextureParameterIi(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return errorChecker.runChecked("getTextureParameterIi", () -> textureFunctions.getTextureParameterIi(
				textureHandle,
				textureParameter,
				textureType
		));
	}

	@Override
	public void getTextureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("getTextureParameterIiv", () -> textureFunctions.getTextureParameterIiv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		));
	}

	@Override
	public int getTextureParameterIui(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	) {
		return errorChecker.runChecked("getTextureParameterIui", () -> textureFunctions.getTextureParameterIui(
				textureHandle,
				textureParameter,
				textureType
		));
	}

	@Override
	public void getTextureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("getTextureParameterIuiv", () -> textureFunctions.getTextureParameterIuiv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		));
	}

	@Override
	public boolean isTexture(int textureHandle) {
		return errorChecker.runChecked("isTexture", () -> textureFunctions.isTexture(textureHandle));
	}

	@Override
	public void deleteTexture(int textureHandle) {
		errorChecker.runChecked("deleteTexture", () -> textureFunctions.deleteTexture(textureHandle));
	}

	@Override
	public void bindTexture(int textureTarget, int textureHandle) {
		errorChecker.runChecked("bindTexture", () -> textureFunctions.bindTexture(textureTarget, textureHandle));
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
		errorChecker.runChecked("clearTextureImage", () -> textureFunctions.clearTextureImage(
				textureHandle,
				clearMipLevel,
				clearDataFormat,
				clearDataType,
				clearDataAddress,
				textureType
		));
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
		errorChecker.runChecked("clearTextureSubImage", () -> textureFunctions.clearTextureSubImage(
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
				clearDataAddress,
				textureType
		));
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
		errorChecker.runChecked("getTextureSubImage", () -> textureFunctions.getTextureSubImage(
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
				outDataSize,
				outDataAddress,
				textureType
		));
	}

	@Override
	public void invalidateTextureImage(
			int				textureHandle,
			int				invalidateMipLevel,
			GLTextureType	textureType
	) {
		errorChecker.runChecked("invalidateTextureImage", () -> textureFunctions.invalidateTextureImage(
				textureHandle,
				invalidateMipLevel,
				textureType
		));
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
		errorChecker.runChecked("invalidateTextureImage", () -> textureFunctions.invalidateTextureSubImage(
				textureHandle,
				invalidateMipLevel,
				invalidatePositionX,
				invalidatePositionY,
				invalidatePositionZ,
				invalidateWidth,
				invalidateHeight,
				invalidateDepth,
				textureType
		));
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
		errorChecker.runChecked("textureView", () -> textureFunctions.textureView(
				viewTextureHandle,
				viewTextureTarget,
				originalTextureHandle,
				viewInternalFormat,
				viewMinLevel,
				viewLevels,
				viewMinLayer,
				viewLayers,
				viewTextureType,
				originalTextureType
		));
	}
}
