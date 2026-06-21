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

package com.github.argon4w.renderfox.opengl.framebuffer.object.feature;

import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferAttachmentParameter;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferParameter;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponentType;
import org.lwjgl.opengl.GL11;

public abstract class AbstractGLFramebufferStore implements IGLFramebufferStore, IGLFramebufferModifier, IGLFramebufferParameter {

	@Override
	public void setParameterInt(GLFramebufferParameter parameter, int value) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getParameterInt(GLFramebufferParameter parameter) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getAttachmentParameterInt(GLFramebufferAttachmentParameter parameter, GLFramebufferAttachment attachment) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getDefaultWidth() {
		return getParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_WIDTH);
	}

	@Override
	public int getDefaultHeight() {
		return getParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_HEIGHT);
	}

	@Override
	public int getDefaultLayers() {
		return getParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_LAYERS);
	}

	@Override
	public int getDefaultSamples() {
		return getParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_SAMPLES);
	}

	@Override
	public boolean isDefaultFixedSampleLocation() {
		return getParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS) == GL11.GL_TRUE;
	}

	@Override
	public boolean isMultisampled() {
		return getParameterInt(GLFramebufferParameter.SAMPLE_BUFFERS) == GL11.GL_TRUE;
	}

	@Override
	public int getMultisampleBits() {
		return getParameterInt(GLFramebufferParameter.SAMPLES);
	}

	@Override
	public int getAttachmentBinding(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_OBJECT_NAME, attachment);
	}

	@Override
	public int getAttachmentMipLevel(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL, attachment);
	}

	@Override
	public int getAttachmentLayer(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER, attachment);
	}

	@Override
	public int getAttachmentRedSize(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_RED_SIZE, attachment);
	}

	@Override
	public int getAttachmentGreenSize(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_GREEN_SIZE, attachment);
	}

	@Override
	public int getAttachmentBlueSize(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_BLUE_SIZE, attachment);
	}

	@Override
	public int getAttachmentAlphaSize(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE, attachment);
	}

	@Override
	public int getAttachmentDepthSize(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE, attachment);
	}

	@Override
	public int getAttachmentStencilSize(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE, attachment);
	}

	@Override
	public boolean isAttachmentLayered(GLFramebufferAttachment attachment) {
		return getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_LAYERED, attachment) == GL11.GL_TRUE;
	}

	@Override
	public GLTextureComponentType getAttachmentComponentType(GLFramebufferAttachment attachment) {
		return GLTextureComponentType.fromConstant(getAttachmentParameterInt(GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE, attachment));
	}

	@Override
	public void setDefaultWidth(int width) {
		setParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_WIDTH, width);
	}

	@Override
	public void setDefaultHeight(int height) {
		setParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_HEIGHT, height);
	}

	@Override
	public void setDefaultLayers(int layers) {
		setParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_LAYERS, layers);
	}

	@Override
	public void setDefaultSamples(int samples) {
		setParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_SAMPLES, samples);
	}

	@Override
	public void setDefaultFixedSampleLocation(boolean fixedSampleLocation) {
		setParameterInt(GLFramebufferParameter.FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS, fixedSampleLocation ? GL11.GL_TRUE : GL11.GL_FALSE);
	}
}
