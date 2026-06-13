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

package com.github.argon4w.renderfox.opengl.function.helper;

import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.function.GLBufferFunctionsHelper;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelParameter;
import org.lwjgl.opengl.GL13;

public abstract class AbstractGLGlobalFunctionsHelper implements IGLGlobalFunctionsHelper {

	public abstract int					getInt			(IGLParameter parameter);
	public abstract int					getIntIndexed	(IGLParameter parameter, int index);
	public abstract long				getLongIndexed	(IGLParameter parameter, int index);
	public abstract IGLBindingSource	getBindingSource();

	@Override
	public int getBufferOffsetAlign(GLBufferBlockType bufferBlockType) {
		return bufferBlockType.getMaxBindingsParameter() == GLGlobalParameter.INVALID ? 1 : getInt(bufferBlockType.getMaxBindingsParameter());
	}

	@Override
	public int getBufferMaxBindings(GLBufferBlockType bufferBlockType) {
		return bufferBlockType.getMaxBindingsParameter() == GLGlobalParameter.INVALID ? 0 : getInt(bufferBlockType.getMaxBindingsParameter());
	}

	@Override
	public long getBoundBufferLength(GLBufferBlockType bufferBlockType, int index) {
		return getBindingSource().getBoundBufferLength(bufferBlockType, index);
	}

	@Override
	public long getBoundBufferOffset(GLBufferBlockType bufferBlockType, int index) {
		return getBindingSource().getBoundBufferOffset(bufferBlockType, index);
	}

	@Override
	public int getBoundBuffer(GLBufferBlockType bufferBlockType, int index) {
		return getBindingSource().getBoundBuffer(bufferBlockType, index);
	}

	@Override
	public int getBoundBuffer(GLBufferType bufferType) {
		return getBindingSource().getBoundBuffer(bufferType);
	}

	@Override
	public GLBufferFunctionsHelper setBoundBuffer(GLBufferType bufferType, GLBufferFunctionsHelper bufferHelper) {
		return bufferHelper.setBuffer(getBoundBuffer(bufferType), bufferType);
	}

	@Override
	public int getMaxTextureUnits() {
		return getInt(GLGlobalParameter.MAX_COMBINED_TEXTURE_IMAGE_UNITS);
	}

	@Override
	public int getMaxTextureSize() {
		return getInt(GLGlobalParameter.MAX_COMBINED_TEXTURE_IMAGE_UNITS);
	}

	@Override
	public int getMaxArrayTextureLayers() {
		return getInt(GLGlobalParameter.MAX_ARRAY_TEXTURE_LAYERS);
	}

	@Override
	public int getMaxDepthTextureSamples() {
		return getInt(GLGlobalParameter.MAX_DEPTH_TEXTURE_SAMPLES);
	}

	@Override
	public int getMaxColorTextureSamples() {
		return getInt(GLGlobalParameter.MAX_COLOR_TEXTURE_SAMPLES);
	}

	@Override
	public int getMaxIntegerSamples() {
		return getInt(GLGlobalParameter.MAX_INTEGER_SAMPLES);
	}

	@Override
	public int getActiveTextureUnit() {
		return getBindingSource().getActiveTexture() - GL13.GL_TEXTURE0;
	}

	@Override
	public int getBoundTexture(GLTextureType textureType) {
		return getBindingSource().getBoundTexture(textureType);
	}

	@Override
	public int getBoundSampler(int textureUnit) {
		return getBindingSource().getBoundSampler(textureUnit);
	}

	@Override
	public int getBoundFramebuffer(GLFramebufferType framebufferType) {
		return getBindingSource().getBoundFramebuffer(framebufferType);
	}

	@Override
	public int getPixelStorageMode(GLPixelParameter parameter) {
		return getBindingSource().getPixelStorageMode(parameter);
	}

	@Override
	public boolean getPackSwapBytes() {
		return getBindingSource().getPackSwapBytes();
	}

	@Override
	public boolean getPackLSBFirst() {
		return getBindingSource().getPackLSBFirst();
	}

	@Override
	public int getPackRowLength() {
		return getBindingSource().getPackRowLength();
	}

	@Override
	public int getPackImageHeight() {
		return getBindingSource().getPackImageHeight();
	}

	@Override
	public int getPackSkipRows() {
		return getBindingSource().getPackSkipRows();
	}

	@Override
	public int getPackSkipPixels() {
		return getBindingSource().getPackSkipPixels();
	}

	@Override
	public int getPackSkipImages() {
		return getBindingSource().getPackSkipImages();
	}

	@Override
	public int getPackAlignment() {
		return getBindingSource().getPackAlignment();
	}

	@Override
	public boolean getUnpackSwapBytes() {
		return getBindingSource().getUnpackSwapBytes();
	}

	@Override
	public boolean getUnpackLSBFirst() {
		return getBindingSource().getUnpackLSBFirst();
	}

	@Override
	public int getUnpackRowLength() {
		return getBindingSource().getUnpackRowLength();
	}

	@Override
	public int getUnpackImageHeight() {
		return getBindingSource().getUnpackImageHeight();
	}

	@Override
	public int getUnpackSkipRows() {
		return getBindingSource().getUnpackSkipRows();
	}

	@Override
	public int getUnpackSkipPixels() {
		return getBindingSource().getUnpackSkipPixels();
	}

	@Override
	public int getUnpackSkipImages() {
		return getBindingSource().getUnpackSkipImages();
	}

	@Override
	public int getUnpackAlignment() {
		return getBindingSource().getUnpackAlignment();
	}
}
