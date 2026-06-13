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

package com.github.argon4w.renderfox.opengl.binding;

import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.texture.GLTextureStateManager;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelParameter;
import org.lwjgl.opengl.GL11;

public class GLDriverBindingSource implements IGLBindingSource {

	private IGLGlobalFunctionsHelper	helper;
	private GLTextureStateManager		textureStateManager;

	public GLDriverBindingSource() {
		this.helper					= null;
		this.textureStateManager	= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		this.helper					= device.getGlobalContext	().getGlobalHelper			();
		this.textureStateManager	= device.getTextureContext	().getTextureStateManager	();
	}

	@Override
	public int getPixelStorageMode(GLPixelParameter parameter) {
		return helper.getInt(parameter.getParameter());
	}

	@Override
	public boolean getPackSwapBytes() {
		return helper.getInt(GLGlobalParameter.PACK_SWAP_BYTES) == GL11.GL_TRUE;
	}

	@Override
	public boolean getPackLSBFirst() {
		return helper.getInt(GLGlobalParameter.PACK_LSB_FIRST) == GL11.GL_TRUE;
	}

	@Override
	public int getPackRowLength() {
		return helper.getInt(GLGlobalParameter.PACK_ROW_LENGTH);
	}

	@Override
	public int getPackImageHeight() {
		return helper.getInt(GLGlobalParameter.PACK_IMAGE_HEIGHT);
	}

	@Override
	public int getPackSkipRows() {
		return helper.getInt(GLGlobalParameter.PACK_SKIP_ROWS);
	}

	@Override
	public int getPackSkipPixels() {
		return helper.getInt(GLGlobalParameter.PACK_SKIP_PIXELS);
	}

	@Override
	public int getPackSkipImages() {
		return helper.getInt(GLGlobalParameter.PACK_SKIP_IMAGES);
	}

	@Override
	public int getPackAlignment() {
		return helper.getInt(GLGlobalParameter.PACK_ALIGNMENT);
	}

	@Override
	public boolean getUnpackSwapBytes() {
		return helper.getInt(GLGlobalParameter.UNPACK_SWAP_BYTES) == GL11.GL_TRUE;
	}

	@Override
	public boolean getUnpackLSBFirst() {
		return helper.getInt(GLGlobalParameter.UNPACK_LSB_FIRST) == GL11.GL_TRUE;
	}

	@Override
	public int getUnpackRowLength() {
		return helper.getInt(GLGlobalParameter.UNPACK_ROW_LENGTH);
	}

	@Override
	public int getUnpackImageHeight() {
		return helper.getInt(GLGlobalParameter.UNPACK_IMAGE_HEIGHT);
	}

	@Override
	public int getUnpackSkipRows() {
		return helper.getInt(GLGlobalParameter.UNPACK_SKIP_ROWS);
	}

	@Override
	public int getUnpackSkipPixels() {
		return helper.getInt(GLGlobalParameter.UNPACK_SKIP_PIXELS);
	}

	@Override
	public int getUnpackSkipImages() {
		return helper.getInt(GLGlobalParameter.UNPACK_SKIP_IMAGES);
	}

	@Override
	public int getUnpackAlignment() {
		return helper.getInt(GLGlobalParameter.UNPACK_ALIGNMENT);
	}

	@Override
	public int getActiveTexture() {
		return helper.getInt(GLGlobalParameter.ACTIVE_TEXTURE);
	}

	@Override
	public int getBoundTexture(GLTextureType textureType) {
		return helper.getInt(textureType.getParameter());
	}

	@Override
	public int getBoundSampler(int textureUnit) {
		return textureStateManager.runUnitScoped(textureUnit, () -> helper.getInt(GLGlobalParameter.SAMPLER_BINDING));
	}

	@Override
	public int getBoundFramebuffer(GLFramebufferType framebufferType) {
		return helper.getInt(framebufferType.getParameter());
	}

	@Override
	public int getBoundBuffer(GLBufferType bufferType) {
		return helper.getInt(bufferType.getParameter());
	}

	@Override
	public int getBoundBuffer(GLBufferBlockType bufferBlockType, int index) {
		return helper.getIntIndexed(bufferBlockType.getBindingParameter(), index);
	}

	@Override
	public long getBoundBufferLength(GLBufferBlockType bufferBlockType, int index) {
		return helper.getLongIndexed(bufferBlockType.getSizeParameter(), index);
	}

	@Override
	public long getBoundBufferOffset(GLBufferBlockType bufferBlockType, int index) {
		return helper.getLongIndexed(bufferBlockType.getOffsetParameter(), index);
	}

	@Override
	public void pixelStore(int pixelParameter, int value) {

	}

	@Override
	public void activeTexture(int textureUnit) {

	}

	@Override
	public void bindTexture(int textureTarget, int textureHandle) {

	}

	@Override
	public void bindSampler(int textureUnit, int samplerHandle) {

	}

	@Override
	public void bindFramebuffer(int framebufferTarget, int framebufferHandle) {

	}

	@Override
	public void bindBuffer(int bufferTarget, int bufferHandle) {

	}

	@Override
	public void bindBufferBase(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			GLBufferType	bufferType
	) {

	}

	@Override
	public void bindBufferRange(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			long			bufferBindOffset,
			long			bufferBindSize,
			GLBufferType	bufferType
	) {

	}

	@Override
	public void bindBuffersBase(
			int				bufferTarget,
			int				bufferTargetFirstIndex,
			int				bufferCounts,
			long			bufferHandlesAddress,
			GLBufferType	bufferType
	) {

	}

	@Override
	public void bindBuffersRange(
			int				bufferTarget,
			int				bufferTargetFirstIndex,
			int				bufferCounts,
			long			bufferHandlesAddress,
			long			bufferOffsetsAddress,
			long			bufferLengthsAddress,
			GLBufferType	bufferType
	) {

	}
}
