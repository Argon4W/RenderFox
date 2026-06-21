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
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferStateManager;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.IGLFramebufferBase;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelParameter;
import org.lwjgl.opengl.GL13;

import java.util.Map;

public abstract class AbstractGLGlobalFunctionsHelper implements IGLGlobalFunctionsHelper {

	private GLFramebufferStateManager framebufferStateManager;

	public AbstractGLGlobalFunctionsHelper() {
		this.framebufferStateManager = null;
	}

	public abstract int					getInt			(IGLParameter parameter);
	public abstract int					getIntIndexed	(IGLParameter parameter, int index);
	public abstract long				getLongIndexed	(IGLParameter parameter, int index);
	public abstract IGLBindingSource	getBindingSource();

	@Override
	public void initialize(OpenGLDevice device) {
		framebufferStateManager = device.getFramebufferContext().getFramebufferStateManager();
	}

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
		return getInt(GLGlobalParameter.MAX_TEXTURE_SIZE);
	}

	@Override
	public int getMax3DTextureSize() {
		return getInt(GLGlobalParameter.MAX_3D_TEXTURE_SIZE);
	}

	@Override
	public int getMaxCubeMapTextureSize() {
		return getInt(GLGlobalParameter.MAX_CUBE_MAP_TEXTURE_SIZE);
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
	public GLFramebufferAttachment getDrawBuffer(int framebufferHandle, int index) {
		return GLFramebufferAttachment.fromConstant(framebufferStateManager.runScoped(Map.of(GLFramebufferType.DRAW_FRAMEBUFFER, framebufferHandle), () -> getInt(GLGlobalParameter.DRAW_BUFFERS[index])));
	}

	@Override
	public GLFramebufferAttachment getReadBuffer(int framebufferHandle) {
		return GLFramebufferAttachment.fromConstant(framebufferStateManager.runScoped(Map.of(GLFramebufferType.READ_FRAMEBUFFER, framebufferHandle), () -> getInt(GLGlobalParameter.READ_BUFFER)));
	}

	@Override
	public int getMaxDrawBuffers() {
		return getInt(GLGlobalParameter.MAX_DRAW_BUFFERS);
	}

	@Override
	public int getMaxColorAttachments() {
		return getInt(GLGlobalParameter.MAX_COLOR_ATTACHMENTS);
	}

	@Override
	public int getMaxFramebufferWidth() {
		return getInt(GLGlobalParameter.MAX_FRAMEBUFFER_WIDTH);
	}

	@Override
	public int getMaxFramebufferHeight() {
		return getInt(GLGlobalParameter.MAX_FRAMEBUFFER_HEIGHT);
	}

	@Override
	public int getMaxFramebufferLayers() {
		return getInt(GLGlobalParameter.MAX_FRAMEBUFFER_LAYERS);
	}

	@Override
	public int getMaxFramebufferSamples() {
		return getInt(GLGlobalParameter.MAX_FRAMEBUFFER_SAMPLES);
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
