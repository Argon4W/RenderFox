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

package com.github.argon4w.renderfox.opengl.device.buffer;

import com.github.argon4w.renderfox.data.size.IResizeMethod;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferStateManager;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.*;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.GLRawBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBuffer;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.device.buffer.bind.BufferMultiBindARB;
import com.github.argon4w.renderfox.opengl.device.buffer.bind.BufferMultiBindLegacy;
import com.github.argon4w.renderfox.opengl.device.buffer.bind.IBufferMultiBind;
import com.github.argon4w.renderfox.opengl.device.buffer.storage.BufferStorageARB;
import com.github.argon4w.renderfox.opengl.device.buffer.storage.BufferStorageLegacy;
import com.github.argon4w.renderfox.opengl.device.buffer.storage.IBufferStorage;

public class GLBufferContext {

	private final OpenGLDevice				device;
	private final GLBufferStateManager		bufferStateManager;
	private final IBufferStorage			bufferStorage;
	private final IBufferMultiBind			bufferMultiBind;
	private final IGLBufferFunctions		bufferFunctions;
	private final GLBufferFunctionsHelper	bufferHelper;

	public GLBufferContext(OpenGLDevice device) {
		this.device				= device;
		this.bufferStateManager	= new GLBufferStateManager	();
		this.bufferStorage		= createBufferStorage		();
		this.bufferMultiBind	= createBufferMultiBind		();
		this.bufferFunctions	= createBufferFunctions		();
		this.bufferHelper		= createBufferHelper		();
	}

	public void initialize() {
		bufferStateManager	.initialize(device);
		bufferFunctions		.initialize(device);
	}

	private IGLBufferFunctions createBufferFunctions() {
		var bufferFunctions	= GLBufferFunctionsDirect.of();

		bufferFunctions = device.getCreateInfo().useCacheStates	() ? new GLBufferFunctionsCached	(bufferFunctions) : bufferFunctions;
		bufferFunctions	= device.getCreateInfo().useErrorCheck	() ? new GLBufferFunctionsErrorCheck(bufferFunctions) : bufferFunctions;
		bufferFunctions	= device.getCreateInfo().useValidation	() ? new GLBufferFunctionsValidation(bufferFunctions) : bufferFunctions;

		return bufferFunctions;
	}

	public IBufferMultiBind createBufferMultiBind() {
		return device.useMultiBind() ? new BufferMultiBindARB(this) : new BufferMultiBindLegacy(this);
	}

	private IBufferStorage createBufferStorage() {
		return device.useBufferStorage() ? new BufferStorageARB(this) : new BufferStorageLegacy(this);
	}

	public int createBufferHandle(GLBufferType bufferType) {
		return bufferFunctions.createBufferHandle(bufferType);
	}

	public GLBufferFunctionsHelper createBufferHelper() {
		return new GLBufferFunctionsHelper(bufferFunctions);
	}

	public GLBufferFunctionsHelper createBufferHelper(GLBufferType bufferType, int bufferHandle) {
		return new GLBufferFunctionsHelper(bufferFunctions).setBuffer(bufferHandle, bufferType);
	}

	public IGLRawBuffer createRawBuffer(GLBufferType bufferType) {
		return device.getCreateInfo().useCacheProperties() ? new GLRawBuffer(this, bufferType) : createBufferHelper(bufferType, createBufferHandle(bufferType));
	}

	public IResizeMethod getResizeMethod() {
		return device.getCreateInfo().getBufferResizeMethod();
	}

	public GLBufferStateManager getBufferStateManager() {
		return bufferStateManager;
	}

	public IGLBufferFunctions getBufferFunctions() {
		return bufferFunctions;
	}

	public IBufferStorage getBufferCreator() {
		return bufferStorage;
	}

	public IBufferMultiBind getBufferBinder() {
		return bufferMultiBind;
	}

	public GLBufferFunctionsHelper getGlobalBufferHelper() {
		return bufferHelper;
	}

	public OpenGLDevice getDevice() {
		return device;
	}
}
