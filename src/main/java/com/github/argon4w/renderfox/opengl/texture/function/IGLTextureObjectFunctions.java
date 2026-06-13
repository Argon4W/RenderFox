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

import com.github.argon4w.renderfox.opengl.texture.GLTextureType;

public interface IGLTextureObjectFunctions {

	int reserveTexture();

	int createTexture(int textureTarget, GLTextureType textureType);

	void textureImage1D(
			int				textureHandle,
			int				textureMipLevel,
			int				textureInternalFormat,
			int				textureWidth,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	);

	void textureImage2D(
			int				textureHandle,
			int				textureMipLevel,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	);

	void textureImage3D(
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
	);

	void textureImage2DMultisample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	);

	void textureImage3DMultisample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				textureDepth,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	);

	void textureStorage1D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			GLTextureType	textureType
	);

	void textureStorage2D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			GLTextureType	textureType
	);

	void textureStorage3D(
			int				textureHandle,
			int				textureMipLevels,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				textureDepth,
			GLTextureType	textureType
	);

	void textureStorage2DMultiSample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	);

	void textureStorage3DMultiSample(
			int				textureHandle,
			int				textureSamples,
			int				textureInternalFormat,
			int				textureWidth,
			int				textureHeight,
			int				textureDepth,
			boolean			textureUseFixedSampleLocations,
			GLTextureType	textureType
	);

	void textureSubImage1D(
			int				textureHandle,
			int				textureMipLevel,
			int				imageWritePositionX,
			int				imageWriteWidth,
			int				imageDataFormat,
			int				imageDataType,
			long			imageDataAddress,
			GLTextureType	textureType
	);

	void textureSubImage2D(
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
	);

	void textureSubImage3D(
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
	);

	void textureParameterf(
			int				textureHandle,
			int				textureParameter,
			float			textureParameterValue,
			GLTextureType	textureType
	);

	void textureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	);

	void textureParameteri(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	);

	void textureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	);

	void textureParameterIi(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	);

	void textureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	);

	void textureParameterIui(
			int				textureHandle,
			int				textureParameter,
			int				textureParameterValue,
			GLTextureType	textureType
	);

	void textureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			textureParameterDataAddress,
			GLTextureType	textureType
	);

	void generateTextureMipmap(int textureHandle, GLTextureType textureType);

	void bindTextureUnit(
			int				textureUnit,
			int				textureHandle,
			GLTextureType	textureType
	);

	void getTextureImage(
			int				textureHandle,
			int				textureMipLevel,
			int				outDataFormat,
			int				outDataType,
			long			outDataSize,
			long			outDataAddress,
			GLTextureType	textureType
	);

	float getTextureLevelParameterf(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	);

	void getTextureLevelParameterfv(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			long			outDataAddress,
			GLTextureType	textureType
	);

	int getTextureLevelParameteri(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			GLTextureType	textureType
	);

	void getTextureLevelParameteriv(
			int				textureHandle,
			int				textureMipLevel,
			int				textureLevelParameter,
			long			outDataAddress,
			GLTextureType	textureType
	);

	float getTextureParameterf(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	);

	void getTextureParameterfv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	);

	int getTextureParameteri(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	);

	void getTextureParameteriv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	);

	int getTextureParameterIi(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	);

	void getTextureParameterIiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	);

	int getTextureParameterIui(
			int				textureHandle,
			int				textureParameter,
			GLTextureType	textureType
	);

	void getTextureParameterIuiv(
			int				textureHandle,
			int				textureParameter,
			long			outDataAddress,
			GLTextureType	textureType
	);
}
