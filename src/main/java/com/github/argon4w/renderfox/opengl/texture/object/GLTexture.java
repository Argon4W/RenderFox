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
import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponent;
import com.github.argon4w.renderfox.opengl.texture.object.raw.IGLRawTextureView;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLImageTransferInfo;

import java.util.Map;

public class GLTexture implements IGLTexture {

	private final IGLRawTextureView texture;

	public GLTexture(IGLRawTextureView texture) {
		this.texture = texture;
	}

	@Override
	public GLImageTransferInfo.Builder transfer(int transferMipLevel) {
		return GLImageTransferInfo
				.builder		()
				.withDefault	(texture.getInternalFormat	(transferMipLevel))
				.withDepth		(texture.getDepth			(transferMipLevel))
				.withWidth		(texture.getWidth			(transferMipLevel))
				.withHeight		(texture.getHeight			(transferMipLevel))
				.withMipLevel	(transferMipLevel);
	}

	@Override
	public void uploadRangeImageFromBuffer(
			GLImageTransferInfo	imageDataInfo,
			IGLBufferBase		imageDataBuffer,
			IDataRange			imageDataRange
	) {
		var device				= texture	.getTextureContext	().getDevice();
		var bufferStateManager	= device	.getBufferContext	().getBufferStateManager();
		var pixelStateManager	= device	.getTextureContext	().getPixelStateManager	();

		bufferStateManager.runScoped(Map.of(GLBufferType.PIXEL_UNPACK_BUFFER, imageDataBuffer.getBufferHandle()), () -> pixelStateManager.runUnpackScoped(imageDataInfo, () -> texture.uploadRangeImage(
				imageDataInfo		.getDeviceMipLevel	(),
				(int) imageDataInfo	.getDeviceOffsetX	(),
				(int) imageDataInfo	.getDeviceOffsetY	(),
				(int) imageDataInfo	.getDeviceOffsetZ	(),
				(int) imageDataInfo	.getDeviceWidth		(),
				(int) imageDataInfo	.getDeviceHeight	(),
				(int) imageDataInfo	.getDeviceDepth		(),
				imageDataInfo		.getFormat			(),
				imageDataInfo		.getDataType		(),
				imageDataRange		.getOffset			()
		)));
	}

	@Override
	public void downloadRangeImageToBuffer(
			GLImageTransferInfo	outDataInfo,
			IGLBufferBase		outDataBuffer,
			IDataRange			outDataRange
	) {
		var device				= texture	.getTextureContext	().getDevice();
		var bufferStateManager	= device	.getBufferContext	().getBufferStateManager();
		var pixelStateManager	= device	.getTextureContext	().getPixelStateManager	();

		bufferStateManager.runScoped(Map.of(GLBufferType.PIXEL_PACK_BUFFER, outDataBuffer.getBufferHandle()), () -> pixelStateManager.runPackScoped(outDataInfo, () -> texture.downloadRangeImage(
				outDataInfo			.getDeviceMipLevel	(),
				(int) outDataInfo	.getDeviceOffsetX	(),
				(int) outDataInfo	.getDeviceOffsetY	(),
				(int) outDataInfo	.getDeviceOffsetZ	(),
				(int) outDataInfo	.getDeviceWidth		(),
				(int) outDataInfo	.getDeviceHeight	(),
				(int) outDataInfo	.getDeviceDepth		(),
				outDataInfo			.getFormat			(),
				outDataInfo			.getDataType		(),
				outDataRange		.getLength			(),
				outDataRange		.getOffset			()
		)));
	}

	@Override
	public void clearFullImage(int clearMipLevel, ColorFloat clearColor) {
		try (var dataView = StackDataView.ofFloats(4L)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearFullImage(
					clearMipLevel,
					internalFormat.getFormat(),
					internalFormat.getType	(),
					dataView
							.putFloat	(0L,	clearColor.getRed	())
							.putFloat	(4L,	clearColor.getGreen	())
							.putFloat	(8L,	clearColor.getBlue	())
							.putFloat	(12L,	clearColor.getAlpha	())
							.address	()
			);
		}
	}

	@Override
	public void clearFullImage(int clearMipLevel, ColorInt clearColor) {
		try (var dataView = StackDataView.ofInts(4L)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearFullImage(
					clearMipLevel,
					internalFormat.getFormat(),
					internalFormat.getType	(),
					dataView
							.putInt	(0L,	clearColor.getRed	())
							.putInt	(4L,	clearColor.getGreen	())
							.putInt	(8L,	clearColor.getBlue	())
							.putInt	(12L,	clearColor.getAlpha	())
							.address()
			);
		}
	}

	@Override
	public void clearFullImage(
			int		clearMipLevel,
			float	clearDepth,
			int		clearStencil
	) {
		try (var dataView = StackDataView.of(8L)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearFullImage(
					clearMipLevel,
					internalFormat.getFormat(),
					internalFormat.getType	(),
					dataView
							.putFloat	(0L, clearDepth)
							.putInt		(4L, clearStencil)
							.address	()
			);
		}
	}

	@Override
	public void clearFullImage(int clearMipLevel, float clearDepth) {
		try (var dataView = StackDataView.aFloat(clearDepth)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearFullImage(
					clearMipLevel,
					internalFormat	.getFormat	(),
					internalFormat	.getType	(),
					dataView		.address	()
			);
		}
	}

	@Override
	public void clearFullImage(int clearMipLevel, int clearStencil) {
		try (var dataView = StackDataView.aInt(clearStencil)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearFullImage(
					clearMipLevel,
					internalFormat	.getFormat	(),
					internalFormat	.getType	(),
					dataView		.address	()
			);
		}
	}

	@Override
	public void clearRangeImage(
			int			clearMipLevel,
			Offset3D	clearOffset,
			Extent3D	clearExtent,
			ColorFloat	clearColor
	) {
		if (clearOffset == null) {
			throw new IllegalArgumentException("ClearOffset cannot be null.");
		}

		if (clearExtent == null) {
			throw new IllegalArgumentException("ClearExtent cannot be null.");
		}

		try (var dataView = StackDataView.ofFloats(4L)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearRangeImage(
					clearMipLevel,
					(int) clearOffset	.getOffsetX	(),
					(int) clearOffset	.getOffsetY	(),
					(int) clearOffset	.getOffsetZ	(),
					(int) clearExtent	.getWidth	(),
					(int) clearExtent	.getHeight	(),
					(int) clearExtent	.getDepth	(),
					internalFormat		.getFormat	(),
					internalFormat		.getType	(),
					dataView
							.putFloat	(0L,	clearColor.getRed	())
							.putFloat	(4L,	clearColor.getGreen	())
							.putFloat	(8L,	clearColor.getBlue	())
							.putFloat	(12L,	clearColor.getAlpha	())
							.address	()
			);
		}
	}

	@Override
	public void clearRangeImage(
			int			clearMipLevel,
			Offset3D	clearOffset,
			Extent3D	clearExtent,
			ColorInt	clearColor
	) {
		try (var dataView = StackDataView.ofInts(4L)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearRangeImage(
					clearMipLevel,
					(int) clearOffset	.getOffsetX	(),
					(int) clearOffset	.getOffsetY	(),
					(int) clearOffset	.getOffsetZ	(),
					(int) clearExtent	.getWidth	(),
					(int) clearExtent	.getHeight	(),
					(int) clearExtent	.getDepth	(),
					internalFormat		.getFormat	(),
					internalFormat		.getType	(),
					dataView
							.putInt	(0L,	clearColor.getRed	())
							.putInt	(4L,	clearColor.getGreen	())
							.putInt	(8L,	clearColor.getBlue	())
							.putInt	(12L,	clearColor.getAlpha	())
							.address()
			);
		}
	}

	@Override
	public void clearRangeImage(
			int			clearMipLevel,
			Offset3D	clearOffset,
			Extent3D	clearExtent,
			float		clearDepth,
			int			clearStencil
	) {
		try (var dataView = StackDataView.of(8L)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearRangeImage(
					clearMipLevel,
					(int) clearOffset	.getOffsetX	(),
					(int) clearOffset	.getOffsetY	(),
					(int) clearOffset	.getOffsetZ	(),
					(int) clearExtent	.getWidth	(),
					(int) clearExtent	.getHeight	(),
					(int) clearExtent	.getDepth	(),
					internalFormat		.getFormat	(),
					internalFormat		.getType	(),
					dataView
							.putFloat	(0L, clearDepth)
							.putInt		(4L, clearStencil)
							.address	()
			);
		}
	}

	@Override
	public void clearRangeImage(
			int			clearMipLevel,
			Offset3D	clearOffset,
			Extent3D	clearExtent,
			float		clearDepth
	) {
		try (var dataView = StackDataView.aFloat(clearDepth)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearRangeImage(
					clearMipLevel,
					(int) clearOffset	.getOffsetX	(),
					(int) clearOffset	.getOffsetY	(),
					(int) clearOffset	.getOffsetZ	(),
					(int) clearExtent	.getWidth	(),
					(int) clearExtent	.getHeight	(),
					(int) clearExtent	.getDepth	(),
					internalFormat		.getFormat	(),
					internalFormat		.getType	(),
					dataView			.address	()
			);
		}
	}

	@Override
	public void clearRangeImage(
			int			clearMipLevel,
			Offset3D	clearOffset,
			Extent3D	clearExtent,
			int			clearStencil
	) {
		try (var dataView = StackDataView.aInt(clearStencil)) {
			var internalFormat = texture.getInternalFormat(clearMipLevel);

			texture.clearRangeImage(
					clearMipLevel,
					(int) clearOffset	.getOffsetX	(),
					(int) clearOffset	.getOffsetY	(),
					(int) clearOffset	.getOffsetZ	(),
					(int) clearExtent	.getWidth	(),
					(int) clearExtent	.getHeight	(),
					(int) clearExtent	.getDepth	(),
					internalFormat		.getFormat	(),
					internalFormat		.getType	(),
					dataView			.address	()
			);
		}
	}

	@Override
	public GLTexture view(
			GLTextureComponent	viewSwizzleRed,
			GLTextureComponent	viewSwizzleGreen,
			GLTextureComponent	viewSwizzleBlue,
			GLTextureComponent	viewSwizzleAlpha,
			int					viewMinMipLevel,
			int					viewMipLevels
	) {
		return new GLTexture(texture.view(
				viewSwizzleRed,
				viewSwizzleGreen,
				viewSwizzleBlue,
				viewSwizzleAlpha,
				viewMinMipLevel,
				viewMipLevels
		));
	}

	@Override
	public void generateMipmap() {
		texture.generateMipmap();
	}

	@Override
	public void bind(GLTextureType textureType) {
		texture.bind(textureType);
	}

	@Override
	public void bindTextureUnit(int textureUnit) {
		texture.bindTextureUnit(textureUnit);
	}

	@Override
	public GLTexture view(int viewMinMipLevel, int viewMipLevels) {
		return view(
				GLTextureComponent.RED,
				GLTextureComponent.GREEN,
				GLTextureComponent.BLUE,
				GLTextureComponent.ALPHA,
				viewMinMipLevel,
				viewMipLevels
		);
	}

	@Override
	public GLTexture view(int viewMaxMipLevel) {
		return view(0, viewMaxMipLevel + 1);
	}

	@Override
	public GLTexture view() {
		return view(getMaxLevel());
	}

	@Override
	public IGLRawTextureView getRawTexture() {
		return texture;
	}

	@Override
	public int getTextureHandle() {
		return texture.getTextureHandle();
	}

	@Override
	public GLTextureType getTextureType() {
		return texture.getTextureType();
	}

	@Override
	public int getBaseLevel() {
		return texture.getBaseLevel();
	}

	@Override
	public int getMaxLevel() {
		return texture.getMaxLevel();
	}

	@Override
	public int getWidth(int mipLevel) {
		return texture.getWidth(mipLevel);
	}

	@Override
	public int getHeight(int mipLevel) {
		return texture.getHeight(mipLevel);
	}

	@Override
	public int getDepth(int mipLevel) {
		return texture.getDepth(mipLevel);
	}

	@Override
	public GLInternalFormat getInternalFormat(int mipLevel) {
		return texture.getInternalFormat(mipLevel);
	}

	@Override
	public IExtent3D getExtent(int mipLevel) {
		return texture.getExtent(mipLevel);
	}

	@Override
	public boolean isMultisampled() {
		return texture.isMultisampled();
	}

	@Override
	public boolean isImmutable() {
		return texture.isImmutable();
	}

	@Override
	public int getImmutableMipLevels() {
		return texture.getImmutableMipLevels();
	}

	@Override
	public int getMaxDimension(int mipLevel) {
		return texture.getMaxDimension(mipLevel);
	}

	@Override
	public int getPixels(int mipLevel) {
		return texture.getPixels(mipLevel);
	}

	@Override
	public boolean isSamplable() {
		return texture.isSamplable();
	}

	@Override
	public boolean isDeleted() {
		return texture.isDeleted();
	}

	@Override
	public void delete() {
		texture.delete();
	}
}
