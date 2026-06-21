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

import com.github.argon4w.renderfox.data.coordinate.extent.Extent3DReference;
import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.function.GLTextureFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTexture;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLTextureBase;

import java.util.Arrays;

public class GLRawTexture extends AbstractGLRawTextureView implements IGLRawTexture {

	protected final	GLTextureType				textureType;
	protected final	GLTextureContext			textureContext;
	protected final	GLTextureFunctionsHelper	textureHelper;
	protected final	int[][]						mipDimensions;

	protected		int							maxMipLevel;
	protected		int							immutableMipLevels;
	protected		GLInternalFormat[]			mipFormats;
	protected		boolean						setup;
	protected		boolean						immutable;
	protected		boolean						deleted;

	public GLRawTexture(GLTextureContext textureContext, GLTextureType textureType) {
		this.textureType	= textureType;
		this.textureContext	= textureContext;
		this.textureHelper	= textureContext.createTextureHelper(textureType, this.textureContext.createTextureHandle(textureType));
		this.mipDimensions	= new int[4][];

		this.maxMipLevel		= 0;
		this.immutableMipLevels	= 0;
		this.mipDimensions[0]	= new int				[] {0};
		this.mipDimensions[1]	= new int				[] {0};
		this.mipDimensions[2]	= new int				[] {0};
		this.mipFormats			= new GLInternalFormat	[] {GLInternalFormat.INVALID};
		this.immutable			= false;
		this.deleted			= false;
	}

	@Override
	public void uploadImage(
			int					textureMipLevel,
			GLInternalFormat	textureInternalFormat,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			GLFormat			imageDataFormat,
			GLDataType			imageDataType,
			long				imageDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (immutable) {
			throw new IllegalStateException("The texture has been setup with immutable storage.");
		}

		if (textureMipLevel > maxMipLevel) {
			this.maxMipLevel		= textureMipLevel;
			this.mipDimensions[0]	= Arrays.copyOf(this.mipDimensions[0],	textureMipLevel + 1);
			this.mipDimensions[1]	= Arrays.copyOf(this.mipDimensions[1],	textureMipLevel + 1);
			this.mipDimensions[2]	= Arrays.copyOf(this.mipDimensions[2],	textureMipLevel + 1);
			this.mipFormats			= Arrays.copyOf(this.mipFormats,		textureMipLevel + 1);
		}

		this.setup									= true;
		this.mipDimensions[0]	[textureMipLevel]	= textureWidth;
		this.mipDimensions[1]	[textureMipLevel]	= textureHeight;
		this.mipDimensions[2]	[textureMipLevel]	= textureDepth;
		this.mipFormats			[textureMipLevel]	= textureInternalFormat;

		textureHelper.uploadImage(
				textureMipLevel,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				imageDataFormat,
				imageDataType,
				imageDataAddress
		);
	}

	@Override
	public void reserveImageMultisample(
			int					textureSamples,
			GLInternalFormat	textureInternalFormat,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			boolean				textureUseFixedSampleLocations
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (immutable) {
			throw new IllegalStateException("The texture has been setup with immutable storage.");
		}

		if (!textureType.isMultisampled()) {
			throw new IllegalStateException("The textureType is not multisampled.");
		}

		this.setup					= true;
		this.maxMipLevel			= 0;
		this.mipDimensions[0]	[0]	= textureWidth;
		this.mipDimensions[1]	[0]	= textureHeight;
		this.mipDimensions[2]	[0]	= textureDepth;
		this.mipFormats			[0]	= textureInternalFormat;

		textureHelper.reserveImageMultisample(
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations
		);
	}

	@Override
	public void setupStorage(
			int					textureMipLevels,
			GLInternalFormat	textureInternalFormat,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (immutable) {
			throw new IllegalStateException("The texture has been setup with immutable storage.");
		}

		this.immutableMipLevels	= textureMipLevels;
		this.immutable			= true;
		this.setup				= true;

		if (textureMipLevels - 1 > maxMipLevel) {
			this.maxMipLevel			= textureMipLevels - 1;
			this.mipDimensions[0]		= Arrays.copyOf(this.mipDimensions[0],		textureMipLevels);
			this.mipDimensions[1]		= Arrays.copyOf(this.mipDimensions[1],		textureMipLevels);
			this.mipDimensions[2]		= Arrays.copyOf(this.mipDimensions[2],		textureMipLevels);
			this.mipFormats				= Arrays.copyOf(this.mipFormats,			textureMipLevels);
		}

		for (var i = 0; i <= maxMipLevel; i ++) {
			this.mipDimensions[0]	[i] = textureWidth		/ (1 << i);
			this.mipDimensions[1]	[i] = textureHeight		/ (1 << i);
			this.mipDimensions[2]	[i] = textureDepth		/ (1 << i);
			this.mipFormats			[i] = textureInternalFormat;
		}

		textureHelper.setupStorage(
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth
		);
	}

	@Override
	public void setupStorageMultiSample(
			int					textureSamples,
			GLInternalFormat	textureInternalFormat,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			boolean				textureUseFixedSampleLocations
	) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (immutable) {
			throw new IllegalStateException("The texture has been setup with immutable storage.");
		}

		if (!textureType.isMultisampled()) {
			throw new IllegalStateException("The textureType is not multisampled.");
		}

		this.maxMipLevel		= 0;
		this.immutableMipLevels	= 1;
		this.immutable			= true;
		this.setup				= true;

		this.mipDimensions[0]	[0] = textureWidth;
		this.mipDimensions[1]	[0] = textureHeight;
		this.mipDimensions[2]	[0] = textureDepth;
		this.mipFormats			[0] = textureInternalFormat;

		textureHelper.setupStorageMultiSample(
				textureSamples,
				textureInternalFormat,
				textureWidth,
				textureHeight,
				textureDepth,
				textureUseFixedSampleLocations
		);
	}

	@Override
	public void setupView(
			IGLTextureBase		originalTexture,
			GLInternalFormat	viewInternalFormat,
			int					viewMinLevel,
			int					viewLevels,
			int					viewMinLayer,
			int					viewLayers
	) {
		throw new UnsupportedOperationException("Cannot setup raw texture as texture views.");
	}

	@Override
	public void setParameterInt(GLTextureParameter parameter, int value) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
		}

		textureHelper.setParameterInt(parameter, value);
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

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
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

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
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

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
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

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
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

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
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

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
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

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
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
	public void generateMipmap() {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
		}

		textureHelper.generateMipmap();
	}

	@Override
	public void bindTextureUnit(int textureUnit) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
		}

		textureHelper.bindTextureUnit(textureUnit);
	}

	@Override
	public void bind(GLTextureType textureType) {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The texture has not been setup yet.");
		}

		textureHelper.bind(textureType);
	}

	@Override
	public void delete() {
		if (deleted) {
			throw new IllegalStateException("The texture has been deleted.");
		}

		textureHelper.delete();

		this.setup		= false;
		this.immutable	= false;
		this.deleted	= true;
	}

	@Override
	public int getBaseLevel() {
		return 0;
	}

	@Override
	public int getMaxLevel() {
		return maxMipLevel;
	}

	@Override
	public int getWidth(int mipLevel) {
		return mipLevel > maxMipLevel ? 0 : mipDimensions[0][mipLevel];
	}

	@Override
	public int getHeight(int mipLevel) {
		return mipLevel > maxMipLevel ? 0 : mipDimensions[1][mipLevel];
	}

	@Override
	public int getDepth(int mipLevel) {
		return mipLevel > maxMipLevel ? 0 : mipDimensions[2][mipLevel];
	}

	@Override
	public int getLayer(int mipLevel) {
		return mipLevel > maxMipLevel ? 0 : textureType.getLayerIndex() < 0 ? 1 : mipDimensions[textureType.getLayerIndex()][mipLevel];
	}

	@Override
	public GLInternalFormat getInternalFormat(int mipLevel) {
		return mipLevel > maxMipLevel ? GLInternalFormat.INVALID : mipFormats[mipLevel];
	}

	@Override
	public IExtent3D getExtent(int mipLevel) {
		return new Extent3DReference(mipDimensions, mipLevel);
	}

	@Override
	public GLTextureContext getTextureContext() {
		return textureContext;
	}

	@Override
	public GLTextureType getTextureType() {
		return textureType;
	}

	@Override
	public int getImmutableMipLevels() {
		return immutableMipLevels;
	}

	@Override
	public boolean isImmutable() {
		return immutable;
	}

	@Override
	public boolean isMultisampled() {
		return textureType.isMultisampled();
	}

	@Override
	public int getTextureHandle() {
		return textureHelper.getTextureHandle();
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public boolean isTexture() {
		return !deleted;
	}

	@Override
	public boolean isSamplable() {
		return false;
	}
}
