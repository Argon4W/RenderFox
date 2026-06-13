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
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.function.IGLGlobalFunctions;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalIndexedParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import org.lwjgl.opengl.GL13;

public class GLGlobalFunctionsHelperQuery extends AbstractGLGlobalFunctionsHelper {

	protected final	IGLGlobalFunctions	globalFunctions;
	protected		IGLBindingSource	bindingSource;

	public GLGlobalFunctionsHelperQuery(IGLGlobalFunctions globalFunctions) {
		this.globalFunctions = globalFunctions;
	}

	public void initialize(OpenGLDevice device) {
		bindingSource = device.getBindingSource();
	}

	@Override
	public int getInt(IGLParameter parameter) {
		if (parameter == null) {
			throw new NullPointerException("Parameter cannot be null.");
		}

		if (parameter == GLGlobalParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return globalFunctions.getInteger(parameter.getConstant());
	}

	@Override
	public float getFloat(IGLParameter parameter) {
		if (parameter == null) {
			throw new NullPointerException("Parameter cannot be null.");
		}

		if (parameter == GLGlobalParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return globalFunctions.getFloat(parameter.getConstant());
	}

	@Override
	public int getIntIndexed(IGLParameter parameter, int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Index cannot be negative.");
		}

		if (parameter == null) {
			throw new NullPointerException("Parameter cannot be null.");
		}

		if (parameter == GLGlobalIndexedParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return globalFunctions.getIntegeri(parameter.getConstant(), index);
	}

	@Override
	public long getLongIndexed(IGLParameter parameter, int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Index cannot be negative.");
		}

		if (parameter == null) {
			throw new NullPointerException("Parameter cannot be null.");
		}

		if (parameter == GLGlobalIndexedParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return globalFunctions.getInteger64i(parameter.getConstant(), index);
	}

	@Override
	public void activeTextureUnit(int textureUnit) {
		globalFunctions.activeTexture(GL13.GL_TEXTURE0 + textureUnit);
	}

	@Override
	public IGLBindingSource getBindingSource() {
		return bindingSource;
	}
}
