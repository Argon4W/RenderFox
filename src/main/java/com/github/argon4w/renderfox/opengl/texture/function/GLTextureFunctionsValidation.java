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

import com.github.argon4w.renderfox.data.UnionValue;
import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.Offset3D;
import com.github.argon4w.renderfox.data.coordinate.extent.Extent3D;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.GLBufferFunctionsHelper;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureLevelParameter;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelStateManager;
import com.github.argon4w.renderfox.util.MathUtils;
import org.lwjgl.system.MemoryUtil;

public class GLTextureFunctionsValidation implements IGLTextureFunctions {

	private final	IGLTextureFunctions			textureFunctions;

	private			GLPixelStateManager			pixelStateManager;
	private			GLTextureFunctionsHelper	textureHelper;
	private			IGLGlobalFunctionsHelper	helperFunctions;
	private			GLBufferFunctionsHelper		bufferHelper;

	public GLTextureFunctionsValidation(IGLTextureFunctions textureFunctions) {
		this.textureFunctions	= textureFunctions;

		this.pixelStateManager	= null;
		this.textureHelper		= null;
		this.helperFunctions	= null;
		this.bufferHelper		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		textureFunctions.initialize(device);

		pixelStateManager	= device.getTextureContext	().getPixelStateManager		();
		textureHelper		= device.getTextureContext	().getGlobalTextureHelper	();
		helperFunctions		= device.getGlobalContext	().getGlobalHelper			();
		bufferHelper		= device.getBufferContext	().getGlobalBufferHelper	();
	}

	@Override
	public int reserveTexture() {
		return textureFunctions.reserveTexture();
	}

	@Override
	public int createTexture(int textureTarget, GLTextureType textureType) {
		var targetType = GLTextureType.fromConstant(textureTarget);

		if (targetType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		return textureFunctions.createTexture(textureTarget, targetType);
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
		var internalFormat	= GLInternalFormat	.fromConstant		(textureInternalFormat);
		var format			= GLFormat			.fromConstant		(imageDataFormat);
		var type			= GLDataType		.fromConstant		(imageDataType);
		var maxSize			= helperFunctions	.getMaxTextureSize	();

		var bufferSize = pixelStateManager
				.capturePixelState	()
				.asUnpackTransfer	()
				.asBuilder			()
				.withDeviceExtent	(new Extent3D(textureWidth))
				.withDeviceOffset	(new Offset3D())
				.withFormat			(format)
				.withType			(type)
				.build				()
				.getRequiredSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (textureType.getDimensions() != 1) {
			throw new IllegalArgumentException("TextureType is not GL_TEXTURE_1D or GL_PROXY_TEXTURE_1D.");
		}

		if (format == GLFormat.INVALID || format == GLFormat.STENCIL_INDEX) {
			throw new IllegalArgumentException("ImageDataFormat is not an accepted format constant. Format constants other than GL_STENCIL_INDEX are accepted.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("ImageDataType is not a type constant.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(maxSize)) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an accepted resolution and format symbolic constants.");
		}

		if (textureWidth < 0) {
			throw new IllegalArgumentException("TextureWidth is less than 0.");
		}

		if (textureWidth > maxSize) {
			throw new IllegalArgumentException("TextureWidth is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (format == GLFormat.DEPTH_COMPONENT && internalFormat.getFormat() != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("ImageDataFormat is GL_DEPTH_COMPONENT and textureImageInternalFormat is not GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT16, GL_DEPTH_COMPONENT24, or GL_DEPTH_COMPONENT32F.");
		}

		if (internalFormat.getFormat() == GLFormat.DEPTH_COMPONENT && format != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("TextureImageInternalFormat is GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT16, GL_DEPTH_COMPONENT24, or GL_DEPTH_COMPONENT32F, and imageDataFormat is not GL_DEPTH_COMPONENT.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_UNPACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(imageDataAddress, bufferSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_UNPACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - imageDataAddress < bufferSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and the data would be unpacked from the buffer object such that the memory reads required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by imageDataType.");
		}

		textureFunctions.textureImage1D(
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
		var internalFormat	= GLInternalFormat	.fromConstant		(textureInternalFormat);
		var format			= GLFormat			.fromConstant		(imageDataFormat);
		var type			= GLDataType		.fromConstant		(imageDataType);
		var maxSize			= helperFunctions	.getMaxTextureSize	();

		var bufferSize = pixelStateManager
				.capturePixelState	()
				.asUnpackTransfer	()
				.asBuilder			()
				.withDeviceExtent	(new Extent3D(textureWidth, textureHeight))
				.withDeviceOffset	(new Offset3D())
				.withFormat			(format)
				.withType			(type)
				.build				()
				.getRequiredSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (textureType.getDimensions() != 2 || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not GL_TEXTURE_2D, GL_TEXTURE_1D_ARRAY, GL_TEXTURE_RECTANGLE, GL_PROXY_TEXTURE_2D, GL_PROXY_TEXTURE_1D_ARRAY, GL_PROXY_TEXTURE_RECTANGLE, GL_PROXY_TEXTURE_CUBE_MAP, GL_TEXTURE_CUBE_MAP_POSITIVE_X, GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL_TEXTURE_CUBE_MAP_POSITIVE_Y, GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL_TEXTURE_CUBE_MAP_POSITIVE_Z, GL_TEXTURE_CUBE_MAP_NEGATIVE_Z.");
		}

		if (!textureType.isComplete() && textureWidth != textureHeight) {
			throw new IllegalArgumentException("TextureType is one of the six cube map 2D image targets and the width and height parameters are not equal.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("ImageDataType is not a type constant.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("ImageDataFormat is not an accepted format constant.");
		}

		if (textureWidth < 0) {
			throw new IllegalArgumentException("TextureWidth is less than 0.");
		}

		if (textureWidth > maxSize) {
			throw new IllegalArgumentException("TextureWidth is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureHeight < 0) {
			throw new IllegalArgumentException("TextureHeight is less than 0.");
		}

		if (textureHeight > maxSize) {
			throw new IllegalArgumentException("TextureHeight is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureType.isArray() && textureHeight > helperFunctions.getMaxArrayTextureLayers()) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_1D_ARRAY or GL_PROXY_TEXTURE_1D_ARRAY and height is greater than GL_MAX_ARRAY_TEXTURE_LAYERS.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(maxSize)) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an accepted resolution and format symbolic constants.");
		}

		if (!textureType.canDepth() && internalFormat.getFormat() == GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("TextureType is not GL_TEXTURE_2D, GL_PROXY_TEXTURE_2D, GL_TEXTURE_RECTANGLE, GL_PROXY_TEXTURE_RECTANGLE and textureInternalFormat is GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT16, GL_DEPTH_COMPONENT_24, or GL_DEPTH_COMPONENT_32F.");
		}

		if (format == GLFormat.DEPTH_COMPONENT && internalFormat.getFormat() != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("ImageDataFormat is GL_DEPTH_COMPONENT and textureImageInternalFormat is not GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT16, GL_DEPTH_COMPONENT24, or GL_DEPTH_COMPONENT32.");
		}

		if (internalFormat.getFormat() == GLFormat.DEPTH_COMPONENT && format != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("TextureImageInternalFormat is GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT16, GL_DEPTH_COMPONENT24, or GL_DEPTH_COMPONENT32, and imageDataFormat is not GL_DEPTH_COMPONENT.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_UNPACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(imageDataAddress, bufferSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_UNPACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - imageDataAddress < bufferSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and the data would be unpacked from the buffer object such that the memory reads required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by imageDataType.");
		}

		if (textureType == GLTextureType.TEXTURE_RECTANGLE && textureMipLevel != 0) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_RECTANGLE or GL_PROXY_TEXTURE_RECTANGLE and textureMipLevel is not 0.");
		}

		textureFunctions.textureImage2D(
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
		var internalFormat	= GLInternalFormat	.fromConstant		(textureInternalFormat);
		var format			= GLFormat			.fromConstant		(imageDataFormat);
		var type			= GLDataType		.fromConstant		(imageDataType);
		var maxSize			= helperFunctions	.getMaxTextureSize	();

		var bufferSize = pixelStateManager
				.capturePixelState	()
				.asUnpackTransfer	()
				.asBuilder			()
				.withDeviceExtent	(new Extent3D(textureWidth, textureHeight, textureDepth))
				.withDeviceOffset	(new Offset3D())
				.withFormat			(format)
				.withType			(type)
				.build				()
				.getRequiredSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 3 || textureType.isCube() || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (format == GLFormat.INVALID || format == GLFormat.STENCIL_INDEX) {
			throw new IllegalArgumentException("ImageDataFormat is not an accepted format constant. Format constants other than GL_STENCIL_INDEX are accepted.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("ImageDataType is not a type constant.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(maxSize)) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an accepted resolution and format symbolic constants.");
		}

		if (textureWidth < 0) {
			throw new IllegalArgumentException("TextureWidth is less than 0.");
		}

		if (textureWidth > maxSize) {
			throw new IllegalArgumentException("TextureWidth is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureHeight < 0) {
			throw new IllegalArgumentException("TextureHeight is less than 0.");
		}

		if (textureHeight > maxSize) {
			throw new IllegalArgumentException("textureHeight is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureDepth < 0) {
			throw new IllegalArgumentException("TextureDepth is less than 0.");
		}

		if (textureDepth > maxSize) {
			throw new IllegalArgumentException("TextureDepth is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (format == GLFormat.DEPTH_COMPONENT && internalFormat.getFormat() != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("ImageDataFormat is GL_DEPTH_COMPONENT and textureImageInternalFormat is not GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT16, GL_DEPTH_COMPONENT24, or GL_DEPTH_COMPONENT32.");
		}

		if (internalFormat.getFormat() == GLFormat.DEPTH_COMPONENT && format != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("TextureImageInternalFormat is GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT16, GL_DEPTH_COMPONENT24, or GL_DEPTH_COMPONENT32, and imageDataFormat is not GL_DEPTH_COMPONENT.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_UNPACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(imageDataAddress, bufferSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_UNPACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - imageDataAddress < bufferSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and the data would be unpacked from the buffer object such that the memory reads required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by imageDataType.");
		}

		textureFunctions.textureImage3D(
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
		var internalFormat	= GLInternalFormat	.fromConstant		(textureInternalFormat);
		var maxSize			= helperFunctions	.getMaxTextureSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an accepted resolution and format symbolic constants.");
		}

		if ((internalFormat.hasDepth() || internalFormat.hasStencil()) && textureSamples > helperFunctions.getMaxDepthTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a depth- or stencil-renderable format and textureSamples is greater than the value of GL_MAX_DEPTH_TEXTURE_SAMPLES.");
		}

		if (internalFormat.hasColor() && textureSamples > helperFunctions.getMaxColorTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a color-renderable format and textureSamples is greater than the value of GL_MAX_COLOR_TEXTURE_SAMPLES.");
		}

		if (internalFormat.isInteger() && textureSamples > helperFunctions.getMaxIntegerSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a signed or unsigned integer format and textureSamples is greater than the value of GL_MAX_INTEGERE_SAMPLES.");
		}

		if (textureWidth < 0) {
			throw new IllegalArgumentException("TextureWidth is less than 0.");
		}

		if (textureWidth > maxSize) {
			throw new IllegalArgumentException("TextureWidth is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureHeight < 0) {
			throw new IllegalArgumentException("TextureHeight is less than 0.");
		}

		if (textureHeight > maxSize) {
			throw new IllegalArgumentException("TextureHeight is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureSamples <= 0) {
			throw new IllegalArgumentException("TextureSamples is zero.");
		}

		textureFunctions.textureImage2DMultisample(
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
		var internalFormat	= GLInternalFormat	.fromConstant		(textureInternalFormat);
		var maxSize			= helperFunctions	.getMaxTextureSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an accepted resolution and format symbolic constants.");
		}

		if ((internalFormat.hasDepth() || internalFormat.hasStencil()) && textureSamples > helperFunctions.getMaxDepthTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a depth- or stencil-renderable format and textureSamples is greater than the value of GL_MAX_DEPTH_TEXTURE_SAMPLES.");
		}

		if (internalFormat.hasColor() && textureSamples > helperFunctions.getMaxColorTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a color-renderable format and textureSamples is greater than the value of GL_MAX_COLOR_TEXTURE_SAMPLES.");
		}

		if (internalFormat.isInteger() && textureSamples > helperFunctions.getMaxIntegerSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a signed or unsigned integer format and textureSamples is greater than the value of GL_MAX_INTEGERE_SAMPLES.");
		}

		if (textureWidth < 0) {
			throw new IllegalArgumentException("TextureWidth is less than 0.");
		}

		if (textureWidth > maxSize) {
			throw new IllegalArgumentException("TextureWidth is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureHeight < 0) {
			throw new IllegalArgumentException("TextureHeight is less than 0.");
		}

		if (textureHeight > maxSize) {
			throw new IllegalArgumentException("TextureHeight is greater than GL_MAX_TEXTURE_SIZE.");
		}

		if (textureDepth < 0) {
			throw new IllegalArgumentException("TextureDepth is less than 0.");
		}

		if (textureDepth > helperFunctions.getMaxArrayTextureLayers()) {
			throw new IllegalArgumentException("TextureDepth is greater than GL_MAX_ARRAY_TEXTURE_LAYERS.");
		}

		if (textureSamples <= 0) {
			throw new IllegalArgumentException("TextureSamples is zero.");
		}

		textureFunctions.textureImage3DMultisample(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 1) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (textureHandle == 0) {
			throw new IllegalArgumentException("Zero is bound to textureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (GLInternalFormat.fromConstant(textureInternalFormat) == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an valid sized internal format.");
		}

		if (textureWidth < 1) {
			throw new IllegalArgumentException("TextureWidth is less than 1.");
		}

		if (textureMipLevels < 1) {
			throw new IllegalArgumentException("TextureMipLevels is less than 1.");
		}

		if (textureMipLevels > Math.floor(MathUtils.log2(textureWidth)) + 1) {
			throw new IllegalArgumentException("TextureMipLevels is greater than floor(log2(textureWidth)) + 1.");
		}

		if (textureHelper.isImmutable()) {
			throw new IllegalArgumentException("The value of GL_TEXTURE_IMMUTABLE_FORMAT for the texture bound to textureType is not GL_FALSE.");
		}

		textureFunctions.textureStorage1D(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 2 || !textureType.isComplete() || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (textureHandle == 0) {
			throw new IllegalArgumentException("Zero is bound to textureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (GLInternalFormat.fromConstant(textureInternalFormat) == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an valid sized internal format.");
		}

		if (textureWidth < 1) {
			throw new IllegalArgumentException("TextureWidth is less than 1.");
		}

		if (textureHeight < 1) {
			throw new IllegalArgumentException("TextureHeight is less than 1.");
		}

		if (textureMipLevels < 1) {
			throw new IllegalArgumentException("TextureMipLevels is less than 1.");
		}

		if (textureType.isArray() && textureMipLevels > Math.floor(MathUtils.log2(textureWidth)) + 1) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_1D_ARRAY or GL_PROXY_TEXTURE_1D_ARRAY and textureMipLevels is greater than floor(log2(textureWidth)) + 1.");
		}

		if (!textureType.isArray() && textureMipLevels > Math.floor(MathUtils.log2(Math.max(textureWidth, textureHeight))) + 1) {
			throw new IllegalArgumentException("TextureType is not GL_TEXTURE_1D_ARRAY or GL_PROXY_TEXTURE_1D_ARRAY and textureMipLevels is greater than floor(log2(max(textureWidth, textureHeight))) + 1.");
		}

		if (textureHelper.isImmutable()) {
			throw new IllegalArgumentException("The value of GL_TEXTURE_IMMUTABLE_FORMAT for the texture bound to textureType is not GL_FALSE.");
		}

		textureFunctions.textureStorage2D(
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
		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 3 || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (textureHandle == 0) {
			throw new IllegalArgumentException("Zero is bound to textureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (GLInternalFormat.fromConstant(textureInternalFormat) == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an valid sized internal format.");
		}

		if (textureWidth < 1) {
			throw new IllegalArgumentException("TextureWidth is less than 1.");
		}

		if (textureHeight < 1) {
			throw new IllegalArgumentException("TextureHeight is less than 1.");
		}

		if (textureDepth < 1) {
			throw new IllegalArgumentException("TextureDepth is less than 1.");
		}

		if (textureMipLevels < 1) {
			throw new IllegalArgumentException("TextureMipLevels is less than 1.");
		}

		if (textureType == GLTextureType.TEXTURE_3D && textureMipLevels > Math.floor(MathUtils.log2(Math.max(Math.max(textureWidth, textureHeight), textureDepth))) + 1) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_3D or GL_PROXY_TEXTURE_3D and textureMipLevels is greater than floor(log2(max(textureWidth, textureHeight, textureDepth))) + 1.");
		}

		if (textureType.isArray() && textureMipLevels > Math.floor(MathUtils.log2(Math.max(textureWidth, textureHeight))) + 1) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_2D_ARRAY, GL_PROXY_TEXTURE_2D_ARRAY, GL_TEXTURE_CUBE_MAP_ARRAY, or GL_PROXY_TEXTURE_CUBE_MAP_ARRAY and textureMipLevels is greater than floor(log2(max(textureWidth, textureHeight))) + 1.");
		}

		if (textureHelper.isImmutable()) {
			throw new IllegalArgumentException("The value of GL_TEXTURE_IMMUTABLE_FORMAT for the texture bound to textureType is not GL_FALSE.");
		}

		textureFunctions.textureStorage2D(
				textureHandle,
				textureMipLevels,
				textureInternalFormat,
				textureWidth,
				textureHeight,
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
		var internalFormat = GLInternalFormat.fromConstant(textureInternalFormat);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 2 || !textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (textureHandle == 0) {
			throw new IllegalArgumentException("Zero is bound to textureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an valid sized internal format.");
		}

		if ((internalFormat.hasDepth() || internalFormat.hasStencil()) && textureSamples > helperFunctions.getMaxDepthTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a depth- or stencil-renderable format and textureSamples is greater than the value of GL_MAX_DEPTH_TEXTURE_SAMPLES.");
		}

		if (internalFormat.hasColor() && textureSamples > helperFunctions.getMaxColorTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a color-renderable format and textureSamples is greater than the value of GL_MAX_COLOR_TEXTURE_SAMPLES.");
		}

		if (internalFormat.isInteger() && textureSamples > helperFunctions.getMaxIntegerSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a signed or unsigned integer format and textureSamples is greater than the value of GL_MAX_INTEGERE_SAMPLES.");
		}

		if (textureWidth < 1) {
			throw new IllegalArgumentException("TextureWidth is less than 1.");
		}

		if (textureHeight < 1) {
			throw new IllegalArgumentException("TextureHeight is less than 1.");
		}

		if (textureSamples == 0) {
			throw new IllegalArgumentException("TextureSamples is zero.");
		}

		if (textureHelper.isImmutable()) {
			throw new IllegalArgumentException("The value of GL_TEXTURE_IMMUTABLE_FORMAT for the texture bound to textureType is not GL_FALSE.");
		}

		textureFunctions.textureStorage2DMultiSample(
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
		var internalFormat = GLInternalFormat.fromConstant(textureInternalFormat);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 3 || !textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (textureHandle == 0) {
			throw new IllegalArgumentException("Zero is bound to textureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("TextureInternalFormat is not an valid sized internal format.");
		}

		if ((internalFormat.hasDepth() || internalFormat.hasStencil()) && textureSamples > helperFunctions.getMaxDepthTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a depth- or stencil-renderable format and textureSamples is greater than the value of GL_MAX_DEPTH_TEXTURE_SAMPLES.");
		}

		if (internalFormat.hasColor() && textureSamples > helperFunctions.getMaxColorTextureSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a color-renderable format and textureSamples is greater than the value of GL_MAX_COLOR_TEXTURE_SAMPLES.");
		}

		if (internalFormat.isInteger() && textureSamples > helperFunctions.getMaxIntegerSamples()) {
			throw new IllegalArgumentException("TextureInternalFormat is a signed or unsigned integer format and textureSamples is greater than the value of GL_MAX_INTEGERE_SAMPLES.");
		}

		if (textureWidth < 1) {
			throw new IllegalArgumentException("TextureWidth is less than 1.");
		}

		if (textureHeight < 1) {
			throw new IllegalArgumentException("TextureHeight is less than 1.");
		}

		if (textureDepth < 1) {
			throw new IllegalArgumentException("TextureDepth is less than 1.");
		}

		if (textureSamples == 0) {
			throw new IllegalArgumentException("TextureSamples is zero.");
		}

		if (textureHelper.isImmutable()) {
			throw new IllegalArgumentException("The value of GL_TEXTURE_IMMUTABLE_FORMAT for the texture bound to textureType is not GL_FALSE.");
		}

		textureFunctions.textureStorage3DMultiSample(
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
		var format	= GLFormat	.fromConstant(imageDataFormat);
		var type	= GLDataType.fromConstant(imageDataType);

		var bufferSize = pixelStateManager
				.capturePixelState	()
				.asUnpackTransfer	()
				.asBuilder			()
				.withDeviceExtent	(new Extent3D(imageWriteWidth))
				.withDeviceOffset	(new Offset3D(imageWritePositionX))
				.withFormat			(format)
				.withType			(type)
				.build				()
				.getRequiredSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 1) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (textureHandle == 0) {
			throw new IllegalArgumentException("Zero is bound to textureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is nor the name of an existing texture object.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("ImageDataFormat is not an accepted format constant.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("TextureType is not a type constant.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned valud of GL_MAX_TEXTURE_SIZE.");
		}

		if (imageWritePositionX < 0) {
			throw new IllegalArgumentException("ImageWritePositionX < -b, where b is the width of th GL_TEXTURE_BORDER of the texture image being modified (which is zero).");
		}

		if (imageWritePositionX + imageWriteWidth > textureHelper.getWidth(textureMipLevel)) {
			throw new IllegalArgumentException("ImageWritePositionX + imageWriteWidth > (w - b), where w is the GL_TEXTURE_WIDTH, and b is the width of the GL_TEXTURE_BORDER of the texture image being modified (Which is zero). Note that w includes twice the border width.");
		}

		if (imageWriteWidth < 0) {
			throw new IllegalArgumentException("ImageWriteWidth is less than 0.");
		}

		if (format == GLFormat.STENCIL_INDEX && textureHelper.getInternalFormat(textureMipLevel).getFormat() != GLFormat.STENCIL_INDEX) {
			throw new IllegalArgumentException("TextureFormat is GL_STENCIL_INDEX and the base internal format is not GL_STENCIL_INDEX.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_UNPACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(imageDataAddress, bufferSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_UNPACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - imageDataAddress < bufferSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and the data would be unpacked from the buffer object such that the memory reads required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by imageDataType.");
		}

		textureFunctions.textureSubImage1D(
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
		var format	= GLFormat	.fromConstant(imageDataFormat);
		var type	= GLDataType.fromConstant(imageDataType);

		var bufferSize = pixelStateManager
				.capturePixelState	()
				.asUnpackTransfer	()
				.asBuilder			()
				.withDeviceExtent	(new Extent3D(imageWriteWidth,		imageWriteHeight))
				.withDeviceOffset	(new Offset3D(imageWritePositionX,	imageWritePositionY))
				.withFormat			(format)
				.withType			(type)
				.build				()
				.getRequiredSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 2 || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureTarget is not one of the allowable values in GLTextureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is nor the name of an existing texture object.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("ImageDataFormat is not an accepted format constant.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("TextureType is not a type constant.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned valud of GL_MAX_TEXTURE_SIZE.");
		}

		if (imageWritePositionX < 0) {
			throw new IllegalArgumentException("ImageWritePositionX < -b, where b is the width of th GL_TEXTURE_BORDER of the texture image being modified (which is zero).");
		}

		if (imageWritePositionY < 0) {
			throw new IllegalArgumentException("ImageWritePositionY < -b, where b is the width of th GL_TEXTURE_BORDER of the texture image being modified (which is zero).");
		}

		if (imageWritePositionX + imageWriteWidth > textureHelper.getWidth(textureMipLevel)) {
			throw new IllegalArgumentException("ImageWritePositionX + imageWriteWidth > (w - b), where w is the GL_TEXTURE_WIDTH, and b is the width of the GL_TEXTURE_BORDER of the texture image being modified (Which is zero). Note that w includes twice the border width.");
		}

		if (imageWritePositionY + imageWriteHeight > textureHelper.getHeight(textureMipLevel)) {
			throw new IllegalArgumentException("ImageWritePositionY + imageWriteHeight > (h - b), where h is the GL_TEXTURE_HEIGHT, and b is the width of the GL_TEXTURE_BORDER of the texture image being modified (Which is zero). Note that h includes twice the border width.");
		}

		if (imageWriteWidth < 0) {
			throw new IllegalArgumentException("ImageWriteWidth is less than 0.");
		}

		if (imageWriteHeight < 0) {
			throw new IllegalArgumentException("ImageWriteHeight is less than 0.");
		}

		if (format == GLFormat.STENCIL_INDEX && textureHelper.getInternalFormat(textureMipLevel).getFormat() != GLFormat.STENCIL_INDEX) {
			throw new IllegalArgumentException("TextureFormat is GL_STENCIL_INDEX and the base internal format is not GL_STENCIL_INDEX.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_UNPACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(imageDataAddress, bufferSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_UNPACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - imageDataAddress < bufferSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and the data would be unpacked from the buffer object such that the memory reads required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by imageDataType.");
		}

		textureFunctions.textureSubImage2D(
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
		var format	= GLFormat	.fromConstant(imageDataFormat);
		var type	= GLDataType.fromConstant(imageDataType);

		var bufferSize = pixelStateManager
				.capturePixelState	()
				.asUnpackTransfer	()
				.asBuilder			()
				.withDeviceExtent	(new Extent3D(imageWriteWidth,		imageWriteHeight,		imageWriteDepth))
				.withDeviceOffset	(new Offset3D(imageWritePositionX,	imageWritePositionY,	imageWritePositionZ))
				.withFormat			(format)
				.withType			(type)
				.build				()
				.getRequiredSize	();

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.getDimensions() != 3 || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureTarget is not one of the allowable values in GLTextureType.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is nor the name of an existing texture object.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("ImageDataFormat is not an accepted format constant.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("TextureType is not a type constant.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned valud of GL_MAX_TEXTURE_SIZE.");
		}

		if (imageWritePositionX < 0) {
			throw new IllegalArgumentException("ImageWritePositionX < -b, where b is the width of th GL_TEXTURE_BORDER of the texture image being modified (which is zero).");
		}

		if (imageWritePositionY < 0) {
			throw new IllegalArgumentException("ImageWritePositionY < -b, where b is the width of th GL_TEXTURE_BORDER of the texture image being modified (which is zero).");
		}

		if (imageWritePositionZ < 0) {
			throw new IllegalArgumentException("ImageWritePositionZ < -b, where b is the width of th GL_TEXTURE_BORDER of the texture image being modified (which is zero).");
		}

		if (imageWritePositionX + imageWriteWidth > textureHelper.getWidth(textureMipLevel)) {
			throw new IllegalArgumentException("ImageWritePositionX + imageWriteWidth > (w - b), where w is the GL_TEXTURE_WIDTH, and b is the width of the GL_TEXTURE_BORDER of the texture image being modified (Which is zero). Note that w includes twice the border width.");
		}

		if (imageWritePositionY + imageWriteHeight > textureHelper.getHeight(textureMipLevel)) {
			throw new IllegalArgumentException("ImageWritePositionY + imageWriteHeight > (h - b), where h is the GL_TEXTURE_HEIGHT, and b is the width of the GL_TEXTURE_BORDER of the texture image being modified (Which is zero). Note that h includes twice the border width.");
		}

		if (imageWritePositionZ + imageWriteDepth > textureHelper.getDepth(textureMipLevel)) {
			throw new IllegalArgumentException("ImageWritePositionZ + imageWriteDepth > (d - b), where d is the GL_TEXTURE_DEPTH, and b is the width of the GL_TEXTURE_BORDER of the texture image being modified (Which is zero). Note that d includes twice the border width.");
		}

		if (imageWriteWidth < 0) {
			throw new IllegalArgumentException("ImageWriteWidth is less than 0.");
		}

		if (imageWriteHeight < 0) {
			throw new IllegalArgumentException("ImageWriteHeight is less than 0.");
		}

		if (imageWriteDepth < 0) {
			throw new IllegalArgumentException("ImageWriteDepth is less than 0.");
		}

		if (format == GLFormat.STENCIL_INDEX && textureHelper.getInternalFormat(textureMipLevel).getFormat() != GLFormat.STENCIL_INDEX) {
			throw new IllegalArgumentException("TextureFormat is GL_STENCIL_INDEX and the base internal format is not GL_STENCIL_INDEX.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_UNPACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(imageDataAddress, bufferSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_UNPACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - imageDataAddress < bufferSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and the data would be unpacked from the buffer object such that the memory reads required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && imageDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_UNPACK_BUFFER target and imageDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by imageDataType.");
		}

		textureFunctions.textureSubImage3D(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (parameter.getCount() != 1) {
			throw new IllegalArgumentException("glTexParameter{if} or glTextureParameter{if} is called for a non-scalar parameter (textureParameter GL_TEXTURE_BORDER_COLOR or GL_TEXTURE_SWIZZLE_RGBA).");
		}

		textureFunctions.textureParameterf(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (parameter.getCount() != 1) {
			throw new IllegalArgumentException("glTexParameter{if} or glTextureParameter{if} is called for a non-scalar parameter (textureParameter GL_TEXTURE_BORDER_COLOR or GL_TEXTURE_SWIZZLE_RGBA).");
		}

		textureFunctions.textureParameterfv(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (!parameter.getTextureValues().isValidValue(textureType, new UnionValue(textureParameterValue))) {
			throw new IllegalArgumentException("TextureParameterValue should have a defined constant value (base on teh value of TextureParameter) and does not.");
		}

		if (parameter.getCount() != 1) {
			throw new IllegalArgumentException("glTexParameter{if} or glTextureParameter{if} is called for a non-scalar parameter (textureParameter GL_TEXTURE_BORDER_COLOR or GL_TEXTURE_SWIZZLE_RGBA).");
		}

		if (textureType.isMultisampled() && parameter.isSampler()) {
			throw new IllegalArgumentException("The effective target is either GL_TEXTURE_2D_MULTISAMPLE or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter is any of the sampler states.");
		}

		if (!textureType.hasMipmap() && parameter == GLTextureParameter.TEXTURE_BASE_LEVEL && textureParameterValue != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter GL_TEXTURE_BASE_LEVEL is set to a value other than zero.");
		}

		textureFunctions.textureParameteri(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (!parameter.getTextureValues().isValidAddress(textureType, textureParameterDataAddress)) {
			throw new IllegalArgumentException("TextureParameterValue should have a defined constant value (base on teh value of TextureParameter) and does not.");
		}

		if (textureType.isMultisampled() && parameter.isSampler()) {
			throw new IllegalArgumentException("The effective target is either GL_TEXTURE_2D_MULTISAMPLE or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter is any of the sampler states.");
		}

		if (!textureType.hasMipmap() && parameter == GLTextureParameter.TEXTURE_BASE_LEVEL && MemoryUtil.memGetInt(textureParameterDataAddress) != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter GL_TEXTURE_BASE_LEVEL is set to a value other than zero.");
		}

		textureFunctions.textureParameteriv(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (!parameter.getTextureValues().isValidValue(textureType, new UnionValue(textureParameterValue))) {
			throw new IllegalArgumentException("TextureParameterValue should have a defined constant value (base on teh value of TextureParameter) and does not.");
		}

		if (parameter.getCount() != 1) {
			throw new IllegalArgumentException("glTexParameter{if} or glTextureParameter{if} is called for a non-scalar parameter (textureParameter GL_TEXTURE_BORDER_COLOR or GL_TEXTURE_SWIZZLE_RGBA).");
		}

		if (textureType.isMultisampled() && parameter.isSampler()) {
			throw new IllegalArgumentException("The effective target is either GL_TEXTURE_2D_MULTISAMPLE or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter is any of the sampler states.");
		}

		if (!textureType.hasMipmap() && parameter == GLTextureParameter.TEXTURE_BASE_LEVEL && textureParameterValue != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter GL_TEXTURE_BASE_LEVEL is set to a value other than zero.");
		}

		textureFunctions.textureParameterIi(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (!parameter.getTextureValues().isValidAddress(textureType, textureParameterDataAddress)) {
			throw new IllegalArgumentException("TextureParameterValue should have a defined constant value (base on teh value of TextureParameter) and does not.");
		}

		if (textureType.isMultisampled() && parameter.isSampler()) {
			throw new IllegalArgumentException("The effective target is either GL_TEXTURE_2D_MULTISAMPLE or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter is any of the sampler states.");
		}

		if (!textureType.hasMipmap() && parameter == GLTextureParameter.TEXTURE_BASE_LEVEL && MemoryUtil.memGetInt(textureParameterDataAddress) != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter GL_TEXTURE_BASE_LEVEL is set to a value other than zero.");
		}

		textureFunctions.textureParameterIiv(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (!parameter.getTextureValues().isValidValue(textureType, new UnionValue(textureParameterValue))) {
			throw new IllegalArgumentException("TextureParameterValue should have a defined constant value (base on teh value of TextureParameter) and does not.");
		}

		if (parameter.getCount() != 1) {
			throw new IllegalArgumentException("glTexParameter{if} or glTextureParameter{if} is called for a non-scalar parameter (textureParameter GL_TEXTURE_BORDER_COLOR or GL_TEXTURE_SWIZZLE_RGBA).");
		}

		if (textureType.isMultisampled() && parameter.isSampler()) {
			throw new IllegalArgumentException("The effective target is either GL_TEXTURE_2D_MULTISAMPLE or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter is any of the sampler states.");
		}

		if (!textureType.hasMipmap() && parameter == GLTextureParameter.TEXTURE_BASE_LEVEL && textureParameterValue != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter GL_TEXTURE_BASE_LEVEL is set to a value other than zero.");
		}

		textureFunctions.textureParameterIui(
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
		var parameter = GLTextureParameter.fromConstant(textureParameter);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (parameter == GLTextureParameter.INVALID || !parameter.isFloat()) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted defined values in GLTextureParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("TextureParameter is read-only.");
		}

		if (!parameter.getTextureValues().isValidAddress(textureType, textureParameterDataAddress)) {
			throw new IllegalArgumentException("TextureParameterValue should have a defined constant value (base on teh value of TextureParameter) and does not.");
		}

		if (textureType.isMultisampled() && parameter.isSampler()) {
			throw new IllegalArgumentException("The effective target is either GL_TEXTURE_2D_MULTISAMPLE or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter is any of the sampler states.");
		}

		if (!textureType.hasMipmap() && parameter == GLTextureParameter.TEXTURE_BASE_LEVEL && MemoryUtil.memGetInt(textureParameterDataAddress) != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureParameter GL_TEXTURE_BASE_LEVEL is set to a value other than zero.");
		}

		textureFunctions.textureParameterIuiv(
				textureHandle,
				textureParameter,
				textureParameterDataAddress,
				textureType
		);
	}

	@Override
	public void generateTextureMipmap(int textureHandle, GLTextureType textureType) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.hasMipmap() || !textureType.isComplete() || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		textureFunctions.generateTextureMipmap(textureHandle, textureType);
	}

	@Override
	public void bindTextureUnit(
			int				textureUnit,
			int				textureHandle,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the accepted defined values.");
		}

		if (textureHandle != 0 && !textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not zero or the name of an existing texture object.");
		}

		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit is negative");
		}

		textureFunctions.bindTextureUnit(
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
		var format	= GLFormat	.fromConstant(outDataFormat);
		var type	= GLDataType.fromConstant(outDataType);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || textureType.isMultisampled()) {
			throw new IllegalArgumentException("TextureType is not a accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("ImageDataFormat is not an accepted format constant.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("ImageDataType is not a type constant.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (!textureType.hasMipmap() && textureMipLevel != 0) {
			throw new IllegalArgumentException("TextureMipLevel is non-zero and the effective target is GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY.");
		}

		if (format == GLFormat.STENCIL_INDEX && textureHelper.setTexture(textureHandle, textureType).getInternalFormat(textureMipLevel).getFormat().hasStencil()) {
			throw new IllegalArgumentException("OutDataFormat is GL_STENCIL_INDEX and the base internal format is not GL_STENCIL_INDEX or GL_DEPTH_STENCIL");
		}

		if (outDataSize < type.getSize() * format.getComponentCount() * textureHelper.getPixels(textureMipLevel)) {
			throw new IllegalArgumentException("The buffer size required to store the requested data is greater than bufSize.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_PACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(outDataAddress, outDataSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_PACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && outDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_PACK_BUFFER target and outDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - outDataAddress < outDataSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_PACK_BUFFER target and the data would be packed to the buffer object such that the memory writes required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && outDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_PACK_BUFFER target and outDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by outDataType.");
		}

		textureFunctions.getTextureImage(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureLevelParameter.fromConstant(textureLevelParameter) == GLTextureLevelParameter.INVALID) {
			throw new IllegalArgumentException("TextureLevelParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (!textureType.hasMipmap() && textureMipLevel != 0) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_BUFFER, GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureMipLevel is not zero.");
		}

		return textureFunctions.getTextureLevelParameterf(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureLevelParameter.fromConstant(textureLevelParameter) == GLTextureLevelParameter.INVALID) {
			throw new IllegalArgumentException("TextureLevelParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (!textureType.hasMipmap() && textureMipLevel != 0) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_BUFFER, GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureMipLevel is not zero.");
		}

		textureFunctions.getTextureLevelParameterfv(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureLevelParameter.fromConstant(textureLevelParameter) == GLTextureLevelParameter.INVALID) {
			throw new IllegalArgumentException("TextureLevelParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (!textureType.hasMipmap() && textureMipLevel != 0) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_BUFFER, GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureMipLevel is not zero.");
		}

		return textureFunctions.getTextureLevelParameteri(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureLevelParameter.fromConstant(textureLevelParameter) == GLTextureLevelParameter.INVALID) {
			throw new IllegalArgumentException("TextureLevelParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (textureMipLevel < 0) {
			throw new IllegalArgumentException("TextureMipLevel is less than 0.");
		}

		if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
			throw new IllegalArgumentException("TextureMipLevel is greater than log2(max), where max is the returned value of GL_MAX_TEXTURE_SIZE.");
		}

		if (!textureType.hasMipmap() && textureMipLevel != 0) {
			throw new IllegalArgumentException("TextureType is GL_TEXTURE_BUFFER, GL_TEXTURE_RECTANGLE, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and textureMipLevel is not zero.");
		}

		textureFunctions.getTextureLevelParameteriv(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		return textureFunctions.getTextureParameterf(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		textureFunctions.getTextureParameterfv(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		return textureFunctions.getTextureParameteri(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		textureFunctions.getTextureParameteriv(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		return textureFunctions.getTextureParameterIi(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		textureFunctions.getTextureParameterIiv(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		return textureFunctions.getTextureParameterIui(
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (GLTextureParameter.fromConstant(textureParameter) == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("TextureParameter is not one of the accepted values.");
		}

		if (!textureFunctions.isTexture(textureHandle)) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		textureFunctions.getTextureParameterIuiv(
				textureHandle,
				textureParameter,
				outDataAddress,
				textureType
		);
	}

	@Override
	public boolean isTexture(int textureHandle) {
		return textureFunctions.isTexture(textureHandle);
	}

	@Override
	public void deleteTexture(int textureHandle) {
		textureFunctions.deleteTexture(textureHandle);
	}

	@Override
	public void bindTexture(int textureTarget, int textureHandle) {
		if (GLTextureType.fromConstant(textureTarget) == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		textureFunctions.bindTexture(textureTarget, textureHandle);
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
		var format	= GLFormat	.fromConstant(clearDataFormat);
		var type	= GLDataType.fromConstant(clearDataType);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("ClearDataFormat is not an accepted format constant.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("ClearDataType is not a type constant.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat() == GLFormat.DEPTH_COMPONENT && format != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("The base internal format is GL_DEPTH_COMPONENT and clearDataFormat is not GL_DEPTH_COMPONENT.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat() == GLFormat.DEPTH_STENCIL && format != GLFormat.DEPTH_STENCIL) {
			throw new IllegalArgumentException("The base internal format is DEPTH_STENCIL and clearDataFormat is not DEPTH_STENCIL.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat() == GLFormat.STENCIL_INDEX && format != GLFormat.STENCIL_INDEX) {
			throw new IllegalArgumentException("The base internal format is STENCIL_INDEX and clearDataFormat is not STENCIL_INDEX.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat().hasColor() && !format.hasColor()) {
			throw new IllegalArgumentException("The base internal format is GL_RGBA and clearDataFormat is GL_DEPTH_COMPONENT, STENCIL_INDEX, or DEPTH_STENCIL.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat().isInteger() && !format.isInteger()) {
			throw new IllegalArgumentException("The base internal format is integer and clearDataFormat does snot specify integer data.");
		}

		textureFunctions.clearTextureImage(
				textureHandle,
				clearMipLevel,
				clearDataFormat,
				clearDataType,
				clearDataAddress,
				textureType
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
		var format	= GLFormat	.fromConstant(clearDataFormat);
		var type	= GLDataType.fromConstant(clearDataType);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID || !textureType.isComplete()) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("ClearDataFormat is not an accepted format constant.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("ClearDataType is not a type constant.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat() == GLFormat.DEPTH_COMPONENT && format != GLFormat.DEPTH_COMPONENT) {
			throw new IllegalArgumentException("The base internal format is GL_DEPTH_COMPONENT and clearDataFormat is not GL_DEPTH_COMPONENT.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat() == GLFormat.DEPTH_STENCIL && format != GLFormat.DEPTH_STENCIL) {
			throw new IllegalArgumentException("The base internal format is DEPTH_STENCIL and clearDataFormat is not DEPTH_STENCIL.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat() == GLFormat.STENCIL_INDEX && format != GLFormat.STENCIL_INDEX) {
			throw new IllegalArgumentException("The base internal format is STENCIL_INDEX and clearDataFormat is not STENCIL_INDEX.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat().hasColor() && !format.hasColor()) {
			throw new IllegalArgumentException("The base internal format is GL_RGBA and clearDataFormat is GL_DEPTH_COMPONENT, STENCIL_INDEX, or DEPTH_STENCIL.");
		}

		if (textureHelper.getInternalFormat(clearMipLevel).getFormat().isInteger() && !format.isInteger()) {
			throw new IllegalArgumentException("The base internal format is integer and clearDataFormat does snot specify integer data.");
		}

		if (clearPositionX < 0) {
			throw new IllegalArgumentException("ClearPositionX specify a region that falls outside the defined texture image array.");
		}

		if (clearPositionY < 0) {
			throw new IllegalArgumentException("ClearPositionY specify a region that falls outside the defined texture image array.");
		}

		if (clearPositionZ < 0) {
			throw new IllegalArgumentException("ClearPositionZ specify a region that falls outside the defined texture image array.");
		}

		if (clearPositionX + clearWidth > textureHelper.getWidth(clearMipLevel)) {
			throw new IllegalArgumentException("ClearPositionX + clearWidth specify a region that falls outside the defined texture image array.");
		}

		if (clearPositionY + clearHeight > textureHelper.getWidth(clearMipLevel)) {
			throw new IllegalArgumentException("ClearPositionY + clearHeight specify a region that falls outside the defined texture image array.");
		}

		if (clearPositionZ + clearDepth > textureHelper.getWidth(clearMipLevel)) {
			throw new IllegalArgumentException("ClearPositionZ + clearDepth specify a region that falls outside the defined texture image array.");
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
				clearDataFormat,
				clearDataType,
				clearDataAddress,
				textureType
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
		var format	= GLFormat	.fromConstant(outDataFormat);
		var type	= GLDataType.fromConstant(outDataType);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (format == GLFormat.INVALID) {
			throw new IllegalArgumentException("OutDataFormat is not an accepted format constant.");
		}

		if (type == GLDataType.INVALID) {
			throw new IllegalArgumentException("OutDataType is not a type constant.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (textureHelper.isMultisampled()) {
			throw new IllegalArgumentException("TextureHandle is the name of a buffer or multisample texture.");
		}

		if (outPositionX < 0) {
			throw new IllegalArgumentException("OutPositionX is negative.");
		}

		if (outPositionY < 0) {
			throw new IllegalArgumentException("OutPositionY is negative.");
		}

		if (outPositionZ < 0) {
			throw new IllegalArgumentException("OutPositionZ is negative.");
		}

		if (outPositionX + outWidth > textureHelper.getWidth(textureMipLevel)) {
			throw new IllegalArgumentException("OutPositionX + outWidth is greater than the texture's width.");
		}

		if (outPositionY + outHeight > textureHelper.getHeight(textureMipLevel)) {
			throw new IllegalArgumentException("OutPositionY + outHeight is greater than the texture's height.");
		}

		if (outPositionZ + outDepth > textureHelper.getDepth(textureMipLevel)) {
			throw new IllegalArgumentException("OutPositionZ + outDepth is greater than the texture's depth.");
		}

		if (textureType.getDimensions() < 2 && outPositionY != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_1D and outPositonY is not zero.");
		}

		if (textureType.getDimensions() < 2 && outHeight != 1) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_1D and outHeight is not one.");
		}

		if (textureType.getDimensions() < 3 && outPositionZ != 0) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_1D, GL_TEXTURE1D_ARRAY, GL_TEXTURE_2D or GL_TEXTURE_RECTANGLE and outPositonZ is not zero.");
		}

		if (textureType.getDimensions() < 3 && outDepth != 1) {
			throw new IllegalArgumentException("The effective target is GL_TEXTURE_1D, GL_TEXTURE1D_ARRAY, GL_TEXTURE_2D or GL_TEXTURE_RECTANGLE and outDepth is not one.");
		}

		if (outDataSize < type.getSize() * format.getComponentCount() * textureHelper.getPixels(textureMipLevel)) {
			throw new IllegalArgumentException("The buffer size required to store the requested data is greater than bufSize.");
		}

		if (helperFunctions.setBoundBuffer(GLBufferType.PIXEL_PACK_BUFFER, bufferHelper).getBufferHandle() != 0 && bufferHelper.isOccupied(new DataRange(outDataAddress, outDataSize))) {
			throw new IllegalArgumentException("A non-zero buffer object name is bound to the GL_PIXEL_PACK_BUFFER target and the buffer object's data store is currently mapped.");
		}

		if (bufferHelper.getBufferHandle() != 0 && outDataAddress < 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_PACK_BUFFER target and imageDataAddress is less than zero.");
		}

		if (bufferHelper.getBufferHandle() != 0 && bufferHelper.getBufferSize() - outDataAddress < outDataSize) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_PACK_BUFFER target and the data would be packed to the buffer object such that the memory writes required would exceed the data store size.");
		}

		if (bufferHelper.getBufferHandle() != 0 && outDataAddress % type.getSize() != 0) {
			throw new IllegalArgumentException("A non-zero buffer object is bound to the GL_PIXEL_PACK_BUFFER target and outDataAddress is not evenly divisible into the number of bytes needed to store in memory a datum indicated by outDataType.");
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
				outDataFormat,
				outDataType,
				outDataSize,
				outDataAddress,
				textureType
		);
	}

	@Override
	public void invalidateTextureImage(
			int				textureHandle,
			int				invalidateMipLevel,
			GLTextureType	textureType
	) {
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureTarget is not one of the allowable values in GLTextureType.");
		}

		if (invalidateMipLevel < 0) {
			throw new IllegalArgumentException("InvalidateMipLevel is less than 0.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (invalidateMipLevel >= MathUtils.log2(textureHelper.getMaxDimension(0))) {
			throw new IllegalArgumentException("InvalidateMipLevel is greater or equal to the base 2 logarithm of the maximum texture width, height or depth.");
		}

		if (!textureType.hasMipmap() && invalidateMipLevel != 0) {
			throw new IllegalArgumentException("TextureType is any of GL_TEXTURE_RECTANGLE, GL_TEXTURE_BUFFER, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and level is not zero.");
		}

		textureFunctions.invalidateTextureImage(
				textureHandle,
				invalidateMipLevel,
				textureType
		);
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
		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("TextureType is not one of the allowable values in GLTextureType.");
		}

		if (invalidatePositionX < 0) {
			throw new IllegalArgumentException("InvalidatePositionX is less than 0.");
		}

		if (invalidatePositionY < 0) {
			throw new IllegalArgumentException("InvalidatePositionY is less than 0.");
		}

		if (invalidatePositionZ < 0) {
			throw new IllegalArgumentException("InvalidatePositionZ is less than 0.");
		}

		if (invalidateMipLevel < 0) {
			throw new IllegalArgumentException("InvalidateMipLevel is less than 0.");
		}

		if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
			throw new IllegalArgumentException("TextureHandle is not the name of an existing texture object.");
		}

		if (invalidatePositionX + invalidateWidth > textureHelper.getWidth(invalidateMipLevel)) {
			throw new IllegalArgumentException("InvalidatePositionX + invalidateWidth is greater than the size of the image width.");
		}

		if (invalidatePositionY + invalidateHeight > textureHelper.getHeight(invalidateMipLevel)) {
			throw new IllegalArgumentException("InvalidatePositionY + invalidateHeight is greater than the size of image height.");
		}

		if (invalidatePositionZ + invalidateDepth > textureHelper.getDepth(invalidateMipLevel)) {
			throw new IllegalArgumentException("InvalidatePositionZ + invalidateDepth is greater than the size of image depth.");
		}

		if (invalidateMipLevel >= MathUtils.log2(textureHelper.getPixels(0))) {
			throw new IllegalArgumentException("InvalidateMipLevel is greater or equal to the base 2 logarithm of the maximum texture width, height or depth.");
		}

		if (!textureType.hasMipmap() && invalidateMipLevel != 0) {
			throw new IllegalArgumentException("TextureType is any of GL_TEXTURE_RECTANGLE, GL_TEXTURE_BUFFER, GL_TEXTURE_2D_MULTISAMPLE, or GL_TEXTURE_2D_MULTISAMPLE_ARRAY and level is not zero.");
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
		if (viewTextureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (originalTextureType == null) {
			throw new IllegalArgumentException("OriginalTextureType cannot be null.");
		}

		if (viewTextureType == GLTextureType.INVALID || !viewTextureType.isComplete()) {
			throw new IllegalArgumentException("ViewTextureTarget is not one of the allowable values in GLTextureType.");
		}

		if (originalTextureType == GLTextureType.INVALID || !originalTextureType.isComplete()) {
			throw new IllegalArgumentException("OriginalTextureTarget is not one of the allowable values in GLTextureType.");
		}

		if (!textureHelper.setTexture(originalTextureHandle, originalTextureType).isTexture()) {
			throw new IllegalArgumentException("OriginalTextureHandle is not the name of an existing texture object.");
		}

		if (!textureHelper.isImmutable()) {
			throw new IllegalArgumentException("The value of GL_TEXTURE_IMMUTABLE_FORMAT for originalTextureHandle is not GL_TRUE.");
		}

		if (viewMinLayer > textureHelper.getDepth(0)) {
			throw new IllegalArgumentException("ViewMinLayer is larger than the greatest layer of originalTextureHandle.");
		}

		if (viewMinLevel > textureHelper.getImmutableMipLevels()) {
			throw new IllegalArgumentException("ViewMinLevel is larget than the greatest level of originalTextureHandle.");
		}

		if (!originalTextureType.isCompatible(viewTextureType)) {
			throw new IllegalArgumentException("ViewTextureType is not compatible with the target of originalTextureType.");
		}

		if (GLInternalFormat.fromConstant(viewInternalFormat).getFormatSize() != textureHelper.getInternalFormat(0).getFormatSize()) {
			throw new IllegalArgumentException("ViewInternalFormat is not compatible with the internal format of originalTextureHandle.");
		}

		if (viewTextureType == GLTextureType.TEXTURE_CUBE_MAP && viewLayers != 6) {
			throw new IllegalArgumentException("ViewTextureType is GL_TEXTURE_CUBE_MAP and viewLayers is not 6.");
		}

		if (viewTextureType == GLTextureType.TEXTURE_CUBE_MAP_ARRAY && viewLayers % 6 != 0) {
			throw new IllegalArgumentException("ViewTextureType is GL_TEXTURE_CUBE_MAP_ARRAY and viewLayers is not an integer multiple of 6.");
		}

		if (!viewTextureType.isArray() && !viewTextureType.isCube() && viewLayers != 1) {
			throw new IllegalArgumentException("ViewTextureType is GL_TEXTURE_1D, GL_TEXTURE2D, GL_TEXTURE3D, GL_TEXTURE_RECTANGLE, or GL_TEXTURE2D_MULTISAMPLE and viewLayers is not equal 1.");
		}

		textureFunctions.textureView(
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
		);
	}
}
