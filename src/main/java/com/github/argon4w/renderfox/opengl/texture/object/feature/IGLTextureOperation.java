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

package com.github.argon4w.renderfox.opengl.texture.object.feature;

import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;

public interface IGLTextureOperation extends IGLTextureBinding, IGLTextureMipmap {

	void uploadRangeImage(
			int			textureMipLevel,
			int			imageWritePositionX,
			int			imageWritePositionY,
			int			imageWritePositionZ,
			int			imageWriteWidth,
			int			imageWriteHeight,
			int			imageWriteDepth,
			GLFormat	imageDataFormat,
			GLDataType	imageDataType,
			long		imageDataAddress
	);

	void downloadFullImage(
			int			textureMipLevel,
			GLFormat	outDataFormat,
			GLDataType	outDataType,
			long		outDataSize,
			long		outDataAddress
	);

	void downloadRangeImage(
			int			textureMipLevel,
			int			outPositionX,
			int			outPositionY,
			int			outPositionZ,
			int			outWidth,
			int			outHeight,
			int			outDepth,
			GLFormat	outDataFormat,
			GLDataType	outDataType,
			long		outDataSize,
			long		outDataAddress
	);

	void clearFullImage(
			int			clearMipLevel,
			GLFormat	clearDataFormat,
			GLDataType	clearDataType,
			long		clearDataAddress
	);

	void clearRangeImage(
			int			clearMipLevel,
			int			clearPositionX,
			int			clearPositionY,
			int			clearPositionZ,
			int			clearWidth,
			int			clearHeight,
			int			clearDepth,
			GLFormat	clearDataFormat,
			GLDataType	clearDataType,
			long		clearDataAddress
	);

	boolean isTexture();

	void invalidateFullImage(int invalidateMipLevel);

	void invalidateRangeImage(
			int			invalidateMipLevel,
			int			invalidatePositionX,
			int			invalidatePositionY,
			int			invalidatePositionZ,
			int			invalidateWidth,
			int			invalidateHeight,
			int			invalidateDepth
	);
}
