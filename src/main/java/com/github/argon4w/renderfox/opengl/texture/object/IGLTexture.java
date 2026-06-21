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

package com.github.argon4w.renderfox.opengl.texture.object;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.coordinate.Offset3D;
import com.github.argon4w.renderfox.data.coordinate.extent.Extent3D;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponent;
import com.github.argon4w.renderfox.opengl.texture.object.feature.*;
import com.github.argon4w.renderfox.opengl.texture.object.raw.IGLRawTextureView;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLImageTransferInfo;

public interface IGLTexture extends IGLRawTextureBase, IGLTextureBinding, IGLTextureMipmap {

	GLImageTransferInfo.Builder transfer(int transferMipLevel);

	void uploadRangeImageFromBuffer(
			GLImageTransferInfo	imageDataInfo,
			IGLBufferBase		imageDataBuffer,
			IDataRange			imageDataRange
	);

	void downloadRangeImageToBuffer(
			GLImageTransferInfo	outDataInfo,
			IGLBufferBase		outDataBuffer,
			IDataRange			outDataRange
	);

	void clearFullImage(int clearMipLevel, ColorFloat clearColor);

	void clearFullImage(int clearMipLevel, ColorInt clearColor);

	void clearFullImage(
			int					clearMipLevel,
			float				clearColorDepth,
			int					clearColorStencil
	);

	void clearFullImage(int clearMipLevel, float clearColorDepth);

	void clearFullImage(int clearMipLevel, int clearColorStencil);

	void clearRangeImage(
			int					clearMipLevel,
			Offset3D			clearOffset,
			Extent3D			clearExtent,
			ColorFloat			clearColor
	);

	void clearRangeImage(
			int					clearMipLevel,
			Offset3D			clearOffset,
			Extent3D			clearExtent,
			ColorInt			clearColor
	);

	void clearRangeImage(
			int					clearMipLevel,
			Offset3D			clearOffset,
			Extent3D			clearExtent,
			float				clearDepth,
			int					clearStencil
	);

	void clearRangeImage(
			int					clearMipLevel,
			Offset3D			clearOffset,
			Extent3D			clearExtent,
			float				clearDepth
	);

	void clearRangeImage(
			int					clearMipLevel,
			Offset3D			clearOffset,
			Extent3D			clearExtent,
			int					clearStencil
	);

	GLTexture view(
			GLTextureComponent	viewSwizzleRed,
			GLTextureComponent	viewSwizzleGreen,
			GLTextureComponent	viewSwizzleBlue,
			GLTextureComponent	viewSwizzleAlpha,
			int					viewMinMipLevel,
			int					viewMipLevels
	);

	GLTexture			view			(int viewMinMipLevel, int viewMipLevels);
	GLTexture			view			(int viewMaxMipLevel);
	GLTexture			view			();
	IGLRawTextureView	getRawTexture	();
}
