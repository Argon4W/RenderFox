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

public interface IGLTextureStatelessFunctions extends IGLTextureBindingFunctions {

	boolean	isTexture		(int textureHandle);
	void	deleteTexture	(int textureHandle);

	void clearTextureImage(
			int				textureHandle,
			int				clearMipLevel,
			int				clearDataFormat,
			int				clearDataType,
			long			clearDataAddress,
			GLTextureType	textureType
	);

	void clearTextureSubImage(
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
	);

	void getTextureSubImage(
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
	);

	void invalidateTextureImage(
			int				textureHandle,
			int				invalidateMipLevel,
			GLTextureType	textureType
	);

	void invalidateTextureSubImage(
			int				textureHandle,
			int				invalidateMipLevel,
			int				invalidatePositionX,
			int				invalidatePositionY,
			int				invalidatePositionZ,
			int				invalidateWidth,
			int				invalidateHeight,
			int				invalidateDepth,
			GLTextureType	textureType
	);

	void textureView(
			int				viewTextureHandle,
			int				originalTextureHandle,
			int				viewTextureTarget,
			int				viewInternalFormat,
			int				viewMinLevel,
			int				viewLevels,
			int				viewMinLayer,
			int				viewLayers,
			GLTextureType	viewTextureType,
			GLTextureType	originalTextureType
	);
}
