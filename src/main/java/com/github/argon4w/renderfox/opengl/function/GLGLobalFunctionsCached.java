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

package com.github.argon4w.renderfox.opengl.function;

import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;

public class GLGLobalFunctionsCached implements IGLGlobalFunctions {

	private final	IGLGlobalFunctions			globalFunctions;
	private			IGLBindingSource			bindingSource;

	public GLGLobalFunctionsCached(IGLGlobalFunctions globalFunctions) {
		this.globalFunctions	= globalFunctions;
		this.bindingSource		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		globalFunctions.initialize(device);

		bindingSource = device.getBindingSource();
	}

	@Override
	public float getFloat(int parameter) {
		return globalFunctions.getFloat(parameter);
	}

	@Override
	public int getInteger(int parameter) {
		return globalFunctions.getInteger(parameter);
	}

	@Override
	public int getIntegeri(int parameter, int index) {
		return globalFunctions.getIntegeri(parameter, index);
	}

	@Override
	public long getInteger64(int parameter) {
		return globalFunctions.getInteger64(parameter);
	}

	@Override
	public long getInteger64i(int parameter, int index) {
		return globalFunctions.getInteger64i(parameter, index);
	}

	@Override
	public void activeTexture(int textureUnit) {
		if (bindingSource.getActiveTexture() == textureUnit) {
			return;
		}

		globalFunctions.activeTexture(textureUnit);
	}

	@Override
	public int getError() {
		return globalFunctions.getError();
	}
}
