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

import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureLevelParameter;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTexture;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLTextureBase;
import com.github.argon4w.renderfox.opengl.texture.object.raw.AbstractGLRawTextureView;

public class GLTextureFunctionsHelper extends AbstractGLRawTextureView implements IGLRawTexture {

	private final	GLTextureContext	textureContext;
	private final	IGLTextureFunctions	textureFunctions;

	private			int					textureHandle;
	private			GLTextureType		textureType;

	public GLTextureFunctionsHelper(GLTextureContext textureContext) {
		this.textureContext		= textureContext;
		this.textureFunctions	= textureContext.getTextureFunctions();
		this.textureHandle		= -1;
		this.textureType		= null;
	}

	public GLTextureFunctionsHelper setTexture(int textureHandle, GLTextureType textureType) {
		this.textureHandle	= textureHandle;
		this.textureType	= textureType;

		return this;
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (textureInternalFormat == null) {
			throw new IllegalArgumentException("TextureInternalFormat cannot be null.");
		}

		if (textureInternalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid textureInternalFormat.");
		}

		if (imageDataFormat == null) {
			throw new IllegalArgumentException("ImageDataFormat cannot be null.");
		}

		if (imageDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid imageDataFormat.");
		}

		if (imageDataType == null) {
			throw new IllegalArgumentException("ImageDataType cannot be null.");
		}

		if (imageDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid imageDataType.");
		}

		var internalFormatCode	= textureInternalFormat	.getConstant();
		var formatCode			= imageDataFormat		.getConstant();
		var typeCode			= imageDataType			.getConstant();

		switch (textureType.getDimensions()) {
			case 1 -> textureFunctions.textureImage1D(
					textureHandle,
					textureMipLevel,
					internalFormatCode,
					textureWidth,
					formatCode,
					typeCode,
					imageDataAddress,
					textureType
			);
			case 2 -> textureFunctions.textureImage2D(
					textureHandle,
					textureMipLevel,
					internalFormatCode,
					textureWidth,
					textureHeight,
					formatCode,
					typeCode,
					imageDataAddress,
					textureType
			);
			case 3 -> textureFunctions.textureImage3D(
					textureHandle,
					textureMipLevel,
					internalFormatCode,
					textureWidth,
					textureHeight,
					textureDepth,
					formatCode,
					typeCode,
					imageDataAddress,
					textureType
			);
			default -> throw new IllegalArgumentException("Invalid dimensions.");
		}
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (textureInternalFormat == null) {
			throw new IllegalArgumentException("TextureInternalFormat cannot be null.");
		}

		if (textureInternalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid textureInternalFormat.");
		}

		switch (textureType.getDimensions()) {
			case 2 -> textureFunctions.textureImage2DMultisample(
					textureHandle,
					textureSamples,
					textureInternalFormat.getConstant(),
					textureWidth,
					textureHeight,
					textureUseFixedSampleLocations,
					textureType
			);
			case 3 -> textureFunctions.textureImage3DMultisample(
					textureHandle,
					textureSamples,
					textureInternalFormat.getConstant(),
					textureWidth,
					textureHeight,
					textureDepth,
					textureUseFixedSampleLocations,
					textureType
			);
			default -> throw new IllegalArgumentException("Invalid dimensions.");
		}
	}

	@Override
	public void setupStorage(
			int					textureMipLevels,
			GLInternalFormat	textureInternalFormat,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth
	) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (textureInternalFormat == null) {
			throw new IllegalArgumentException("TextureInternalFormat cannot be null.");
		}

		if (textureInternalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid textureInternalFormat.");
		}

		var internalFormatCode = textureInternalFormat.getConstant();

		switch (textureType.getDimensions()) {
			case 1 -> textureFunctions.textureStorage1D(
					textureHandle,
					textureMipLevels,
					internalFormatCode,
					textureWidth,
					textureType
			);
			case 2 -> textureFunctions.textureStorage2D(
					textureHandle,
					textureMipLevels,
					internalFormatCode,
					textureWidth,
					textureHeight,
					textureType
			);
			case 3 -> textureFunctions.textureStorage3D(
					textureHandle,
					textureMipLevels,
					internalFormatCode,
					textureWidth,
					textureHeight,
					textureDepth,
					textureType
			);
			default -> throw new IllegalArgumentException("Invalid dimensions.");
		}
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (textureInternalFormat == null) {
			throw new IllegalArgumentException("TextureInternalFormat cannot be null.");
		}

		if (textureInternalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid textureInternalFormat.");
		}

		switch (textureType.getDimensions()) {
			case 2 -> textureFunctions.textureStorage2DMultiSample(
					textureHandle,
					textureSamples,
					textureInternalFormat.getConstant(),
					textureWidth,
					textureHeight,
					textureUseFixedSampleLocations,
					textureType
			);
			case 3 -> textureFunctions.textureStorage3DMultiSample(
					textureHandle,
					textureSamples,
					textureInternalFormat.getConstant(),
					textureWidth,
					textureHeight,
					textureDepth,
					textureUseFixedSampleLocations,
					textureType
			);
			default -> throw new IllegalArgumentException("Invalid dimensions.");
		}
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (originalTexture == null) {
			throw new IllegalStateException("OriginalTexture cannot be null.");
		}

		var originalTextureType = originalTexture.getTextureType();

		textureFunctions.textureView(
				textureHandle,
				originalTextureType	.getConstant		(),
				originalTexture		.getTextureHandle	(),
				viewInternalFormat	.getConstant		(),
				viewMinLevel,
				viewLevels,
				viewMinLayer,
				originalTextureType.isArray() ? viewLayers : 1,
				originalTextureType,
				originalTextureType
		);
	}

	@Override
	public void setParameterInt(GLTextureParameter parameter, int value) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		textureFunctions.textureParameteri(
				textureHandle,
				parameter.getConstant(),
				value,
				textureType
		);
	}

	@Override
	public int getParameterInt(GLTextureParameter parameter) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return textureFunctions.getTextureParameteri(
				textureHandle,
				parameter.getConstant(),
				textureType
		);
	}

	@Override
	public int getLevelParameterInt(GLTextureLevelParameter parameter, int mipLevel) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureLevelParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return textureFunctions.getTextureLevelParameteri(
				textureHandle,
				mipLevel,
				parameter.getConstant(),
				textureType
		);
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (imageDataFormat == null) {
			throw new IllegalArgumentException("ImageDataFormat cannot be null.");
		}

		if (imageDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid imageDataFormat.");
		}

		if (imageDataType == null) {
			throw new IllegalArgumentException("ImageDataType cannot be null.");
		}

		if (imageDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid imageDataType.");
		}

		var formatCode	= imageDataFormat	.getConstant();
		var typeCode	= imageDataType		.getConstant();

		if (textureType == GLTextureType.TEXTURE_CUBE_MAP) {
			textureFunctions.textureSubImage3D(
					textureHandle,
					textureMipLevel,
					imageWritePositionX,
					imageWritePositionY,
					imageWritePositionZ,
					imageWriteWidth,
					imageWriteHeight,
					imageWriteDepth,
					formatCode,
					typeCode,
					imageDataAddress,
					textureType
			);

			return;
		}

		switch (textureType.getDimensions()) {
			case 1 -> textureFunctions.textureSubImage1D(
					textureHandle,
					textureMipLevel,
					imageWritePositionX,
					imageWriteWidth,
					formatCode,
					typeCode,
					imageDataAddress,
					textureType
			);
			case 2 -> textureFunctions.textureSubImage2D(
					textureHandle,
					textureMipLevel,
					imageWritePositionX,
					imageWritePositionY,
					imageWriteWidth,
					imageWriteHeight,
					formatCode,
					typeCode,
					imageDataAddress,
					textureType
			);
			case 3 -> textureFunctions.textureSubImage3D(
					textureHandle,
					textureMipLevel,
					imageWritePositionX,
					imageWritePositionY,
					imageWritePositionZ,
					imageWriteWidth,
					imageWriteHeight,
					imageWriteDepth,
					formatCode,
					typeCode,
					imageDataAddress,
					textureType
			);
			default -> throw new IllegalArgumentException("Invalid dimensions.");
		}
	}

	@Override
	public void downloadFullImage(
			int			textureMipLevel,
			GLFormat	outDataFormat,
			GLDataType	outDataType,
			long		outDataSize,
			long		outDataAddress
	) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (outDataFormat == null) {
			throw new IllegalArgumentException("OutDataFormat cannot be null.");
		}

		if (outDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid ouyDataFormat.");
		}

		if (outDataType == null) {
			throw new IllegalArgumentException("OutDataType cannot be null.");
		}

		if (outDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid outDataType.");
		}

		textureFunctions.getTextureImage(
				textureHandle,
				textureMipLevel,
				outDataFormat	.getConstant(),
				outDataType		.getConstant(),
				outDataSize,
				outDataAddress,
				textureType
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (outDataFormat == null) {
			throw new IllegalArgumentException("OutDataFormat cannot be null.");
		}

		if (outDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid ouyDataFormat.");
		}

		if (outDataType == null) {
			throw new IllegalArgumentException("OutDataType cannot be null.");
		}

		if (outDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid outDataType.");
		}

		textureFunctions.getTextureSubImage(
				textureHandle,
				textureMipLevel,
				outPositionX,
				outPositionY,
				outPositionZ,
				outWidth,
				outHeight,
				outDepth,
				outDataFormat	.getConstant(),
				outDataType		.getConstant(),
				outDataSize,
				outDataAddress,
				textureType
		);
	}

	@Override
	public void clearFullImage(
			int			clearMipLevel,
			GLFormat	clearDataFormat,
			GLDataType	clearDataType,
			long		clearDataAddress
	) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (clearDataFormat == null) {
			throw new IllegalArgumentException("ClearDataFormat cannot be null.");
		}

		if (clearDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataFormat.");
		}

		if (clearDataType == null) {
			throw new IllegalArgumentException("ClearDataType cannot be null.");
		}

		if (clearDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataType.");
		}

		textureFunctions.clearTextureImage(
				textureHandle,
				clearMipLevel,
				clearDataFormat	.getConstant(),
				clearDataType	.getConstant(),
				clearDataAddress,
				textureType
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (clearDataFormat == null) {
			throw new IllegalArgumentException("ClearDataFormat cannot be null.");
		}

		if (clearDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataFormat.");
		}

		if (clearDataType == null) {
			throw new IllegalArgumentException("ClearDataType cannot be null.");
		}

		if (clearDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataType.");
		}

		textureFunctions.clearTextureSubImage(
				textureHandle,
				clearMipLevel,
				clearPositionX,
				clearPositionY,
				clearPositionZ,
				clearWidth,
				clearHeight,
				clearDepth,
				clearDataFormat	.getConstant(),
				clearDataType	.getConstant(),
				clearDataAddress,
				textureType
		);
	}

	@Override
	public boolean isTexture() {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		return textureFunctions.isTexture(textureHandle);
	}

	@Override
	public void invalidateFullImage(int invalidateMipLevel) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		textureFunctions.invalidateTextureImage(
				textureHandle,
				invalidateMipLevel,
				textureType
		);
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
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		textureFunctions.invalidateTextureSubImage(
				textureHandle,
				invalidateMipLevel,
				invalidatePositionX,
				invalidatePositionY,
				invalidatePositionZ,
				invalidateWidth,
				invalidateHeight,
				invalidateDepth,
				textureType
		);
	}

	@Override
	public void bindTextureUnit(int textureUnit) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		textureFunctions.bindTextureUnit(
				textureUnit,
				textureHandle,
				textureType
		);
	}

	@Override
	public void generateMipmap() {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		textureFunctions.generateTextureMipmap(textureHandle, textureType);
	}

	@Override
	public void bind(GLTextureType textureType) {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		textureFunctions.bindTexture(textureType.getConstant(), textureHandle);
	}

	@Override
	public void delete() {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		textureFunctions.deleteTexture(textureHandle);
	}

	@Override
	public int getTextureHandle() {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		return textureHandle;
	}

	@Override
	public GLTextureType getTextureType() {
		if (textureHandle == -1) {
			throw new IllegalStateException("TextureHandle has not yet been set.");
		}

		if (textureType == null) {
			throw new IllegalStateException("TextureType has not yet been set.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalStateException("Invalid textureType.");
		}

		return textureType;
	}

	@Override
	public int getLayer(int mipLevel) {
		return switch (textureType.getLayerIndex()) {
			case 0 -> getWidth	(mipLevel);
			case 1 -> getHeight	(mipLevel);
			case 2 -> getDepth	(mipLevel);
			default -> 0;
		};
	}

	@Override
	public boolean isDeleted() {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public GLTextureContext getTextureContext() {
		return textureContext;
	}

	@Override
	public boolean isSamplable() {
		return false;
	}
}
