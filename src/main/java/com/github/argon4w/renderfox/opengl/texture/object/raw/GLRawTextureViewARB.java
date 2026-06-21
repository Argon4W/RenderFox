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

package com.github.argon4w.renderfox.opengl.texture.object.raw;

import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.function.GLTextureFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponent;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;

public class GLRawTextureViewARB extends AbstractGLRawTextureView {

	private final	IGLRawTextureView			texture;
	private final	GLTextureContext			textureContext;
	private final	GLTextureFunctionsHelper	textureHelper;
	private final	int							textureHandle;
	private final	int							baseMipLevel;
	private final	int							numMipLevels;
	private			boolean						deleted;

	public GLRawTextureViewARB(
			IGLRawTextureView	texture,
			GLTextureContext	textureContext,
			GLTextureComponent	viewSwizzleRed,
			GLTextureComponent	viewSwizzleGreen,
			GLTextureComponent	viewSwizzleBlue,
			GLTextureComponent	viewSwizzleAlpha,
			int					viewMinMipLevel,
			int					viewMipLevels
	) {
		this.texture		= texture;
		this.textureContext	= textureContext;
		this.textureHandle	= textureContext.getTextureFunctions().reserveTexture();
		this.textureHelper	= textureContext.createTextureHelper(texture.getTextureType(), this.textureHandle);
		this.baseMipLevel	= viewMinMipLevel;
		this.numMipLevels	= viewMipLevels;
		this.deleted		= false;

		this.textureHelper.setupView(
				this.texture,
				this.texture.getInternalFormat(0),
				this.baseMipLevel,
				this.numMipLevels,
				0,
				this.texture.getLayer(0)
		);

		this.textureHelper.setParameterInt(GLTextureParameter.TEXTURE_SWIZZLE_R, viewSwizzleRed		.getConstant());
		this.textureHelper.setParameterInt(GLTextureParameter.TEXTURE_SWIZZLE_G, viewSwizzleGreen	.getConstant());
		this.textureHelper.setParameterInt(GLTextureParameter.TEXTURE_SWIZZLE_B, viewSwizzleBlue	.getConstant());
		this.textureHelper.setParameterInt(GLTextureParameter.TEXTURE_SWIZZLE_A, viewSwizzleAlpha	.getConstant());
	}

	@Override
	public void uploadRangeImage(
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
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.uploadRangeImage(
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
	public void downloadFullImage(
			int			textureMipLevel,
			GLFormat	outDataFormat,
			GLDataType	outDataType,
			long		outDataSize,
			long		outDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.downloadFullImage(
				textureMipLevel,
				outDataFormat,
				outDataType,
				outDataSize,
				outDataAddress
		);
	}

	@Override
	public void downloadRangeImage(
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
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.downloadRangeImage(
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
				outDataAddress
		);
	}

	@Override
	public void clearFullImage(
			int			clearMipLevel,
			GLFormat	clearDataFormat,
			GLDataType	clearDataType,
			long		clearDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.clearFullImage(
				clearMipLevel,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		);
	}

	@Override
	public void clearRangeImage(
			int				clearMipLevel,
			int				clearPositionX,
			int				clearPositionY,
			int				clearPositionZ,
			int				clearWidth,
			int				clearHeight,
			int				clearDepth,
			GLFormat		clearDataFormat,
			GLDataType		clearDataType,
			long			clearDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.clearRangeImage(
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
	public void invalidateFullImage(int invalidateMipLevel) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.invalidateFullImage(invalidateMipLevel);
	}

	@Override
	public void invalidateRangeImage(
			int invalidateMipLevel,
			int invalidatePositionX,
			int invalidatePositionY,
			int invalidatePositionZ,
			int invalidateWidth,
			int invalidateHeight,
			int invalidateDepth
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.invalidateRangeImage(
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
	public void bindTextureUnit(int textureUnit) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.bindTextureUnit(textureUnit);
	}

	@Override
	public void bind(GLTextureType textureType) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.bind(textureType);
	}

	@Override
	public void delete() {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		this.textureHelper.delete();
		this.deleted = true;
	}

	@Override
	public void generateMipmap() {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getBaseLevel() {
		return texture.getBaseLevel() + baseMipLevel;
	}

	@Override
	public int getMaxLevel() {
		return texture.getBaseLevel() + baseMipLevel + numMipLevels - 1;
	}

	@Override
	public int getWidth(int mipLevel) {
		return texture.getWidth(baseMipLevel + mipLevel);
	}

	@Override
	public int getHeight(int mipLevel) {
		return texture.getHeight(baseMipLevel + mipLevel);
	}

	@Override
	public int getDepth(int mipLevel) {
		return texture.getDepth(baseMipLevel + mipLevel);
	}

	@Override
	public int getLayer(int mipLevel) {
		return texture.getLayer(baseMipLevel + mipLevel);
	}

	@Override
	public GLInternalFormat getInternalFormat(int mipLevel) {
		return texture.getInternalFormat(baseMipLevel + mipLevel);
	}

	@Override
	public IExtent3D getExtent(int mipLevel) {
		return texture.getExtent(baseMipLevel + mipLevel);
	}

	@Override
	public GLTextureContext getTextureContext() {
		return textureContext;
	}

	@Override
	public GLTextureType getTextureType() {
		return texture.getTextureType();
	}

	@Override
	public int getImmutableMipLevels() {
		return numMipLevels;
	}

	@Override
	public boolean isMultisampled() {
		return getTextureType().isMultisampled();
	}

	@Override
	public boolean isImmutable() {
		return true;
	}

	@Override
	public int getTextureHandle() {
		return textureHandle;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public boolean isTexture() {
		return !deleted;
	}
}
