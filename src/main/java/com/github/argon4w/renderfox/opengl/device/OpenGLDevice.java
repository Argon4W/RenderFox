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

package com.github.argon4w.renderfox.opengl.device;

import com.github.argon4w.renderfox.opengl.binding.GLBindingSourceType;
import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;
import com.github.argon4w.renderfox.opengl.device.framebuffer.GLFramebufferContext;
import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.dsa.DirectStateAccessARB;
import com.github.argon4w.renderfox.opengl.dsa.DirectStateAccessLegacy;
import com.github.argon4w.renderfox.opengl.dsa.IDirectStateAccess;
import com.github.argon4w.renderfox.opengl.error.GLError;
import com.github.argon4w.renderfox.opengl.error.GLErrorChecker;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class OpenGLDevice {

	private final OpenGLDeviceCreateInfo	createInfo;
	private final GLCapabilities			capabilities;
	private final GLErrorChecker			errorChecker;

	private final IDirectStateAccess		directStateAccess;
	private final IGLBindingSource			bindingSource;
	private final GLBufferContext			bufferContext;
	private final GLTextureContext			textureContext;
	private final GLFramebufferContext		framebufferContext;
	private final GLGlobalContext			globalContext;


	public OpenGLDevice(OpenGLDeviceCreateInfo createInfo) {
		this.createInfo			= createInfo;
		this.capabilities		= GL.createCapabilities();

		this.directStateAccess	= createDirectStateAccess	();
		this.bindingSource		= createBindingSource		();
		this.errorChecker		= new GLErrorChecker		(this);
		this.bufferContext		= new GLBufferContext		(this);
		this.textureContext		= new GLTextureContext		(this);
		this.framebufferContext	= new GLFramebufferContext	(this);
		this.globalContext		= new GLGlobalContext		(this);

		initialize();
	}

	private void initialize() {
		bufferContext		.initialize();
		textureContext		.initialize();
		framebufferContext	.initialize();
		globalContext		.initialize();
		directStateAccess	.initialize(this);
		bindingSource		.initialize(this);
	}

	public GLError getError() {
		return GLError.fromErrorCode(globalContext.getGlobalFunctions().getError());
	}

	private IDirectStateAccess createDirectStateAccess() {
		return useDirectStateAccess() ? new DirectStateAccessARB() : new DirectStateAccessLegacy();
	}

	private IGLBindingSource createBindingSource() {
		return getBindingSourceType().create();
	}

	public boolean useDirectStateAccess() {
		return supportDirectStateAccess() && createInfo.useDirectStateAccess();
	}

	public boolean useMultiBind() {
		return supportMultiBind() && createInfo.useMultiBind();
	}

	public boolean useBufferStorage() {
		return supportBufferStorage() && createInfo.useBufferStorage();
	}

	public boolean useTextureStorage() {
		return supportTextureStorage() && createInfo.useTextureStorage();
	}

	public boolean useTextureView() {
		return supportTextureView() && createInfo.useTextureView();
	}

	public boolean useTextureAnisotropy() {
		return supportTextureFilterAnisotropy() && createInfo.useTextureAnisotropy();
	}

	public boolean useVertexAttribBinding() {
		return supportVertexAttribBinding() && createInfo.useAttribBinding();
	}

	private GLBindingSourceType getBindingSourceType() {
		return createInfo.getBindingSourceType();
	}

	public boolean supportDirectStateAccess() {
		return capabilities.GL_ARB_direct_state_access;
	}

	public boolean supportMultiBind() {
		return capabilities.GL_ARB_multi_bind;
	}

	public boolean supportBufferStorage() {
		return capabilities.GL_ARB_buffer_storage;
	}

	public boolean supportTextureStorage() {
		return capabilities.GL_ARB_texture_storage;
	}

	public boolean supportTextureView() {
		return capabilities.GL_ARB_texture_view;
	}

	public boolean supportVertexAttribBinding() {
		return capabilities.GL_ARB_vertex_attrib_binding;
	}

	public boolean supportTextureFilterAnisotropy() {
		return capabilities.GL_ARB_texture_filter_anisotropic;
	}

	public IDirectStateAccess getDirectStateAccess() {
		return directStateAccess;
	}

	public IGLBindingSource getBindingSource() {
		return bindingSource;
	}

	public GLBufferContext getBufferContext() {
		return bufferContext;
	}

	public GLTextureContext getTextureContext() {
		return textureContext;
	}

	public GLFramebufferContext getFramebufferContext() {
		return framebufferContext;
	}

	public GLGlobalContext getGlobalContext() {
		return globalContext;
	}

	public GLErrorChecker getErrorChecker() {
		return errorChecker;
	}

	public OpenGLDeviceCreateInfo getCreateInfo() {
		return createInfo;
	}
}
