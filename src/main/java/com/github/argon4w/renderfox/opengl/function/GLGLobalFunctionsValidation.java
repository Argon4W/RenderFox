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

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalIndexedParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.texture.function.IGLTextureHelperFunctions;
import org.lwjgl.opengl.GL13;

public class GLGLobalFunctionsValidation implements IGLGlobalFunctions {

	private final	IGLGlobalFunctions			globalFunctions;
	private			IGLTextureHelperFunctions	textureHelperFunctions;

	public GLGLobalFunctionsValidation(IGLGlobalFunctions globalFunctions) {
		this.globalFunctions		= globalFunctions;
		this.textureHelperFunctions	= null;
	}

	public void initialize(OpenGLDevice device) {
		globalFunctions.initialize(device);

		textureHelperFunctions = device.getGlobalContext().getGlobalHelper();
	}

	@Override
	public float getFloat(int parameter) {
		if (GLGlobalParameter.fromConstant(parameter) == GLGlobalParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return globalFunctions.getFloat(parameter);
	}

	@Override
	public int getInteger(int parameter) {
		if (GLGlobalParameter.fromConstant(parameter) == GLGlobalParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return globalFunctions.getInteger(parameter);
	}

	@Override
	public long getInteger64(int parameter) {
		if (GLGlobalParameter.fromConstant(parameter) == GLGlobalParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return globalFunctions.getInteger64(parameter);
	}

	@Override
	public int getIntegeri(int parameterCode, int index) {
		var parameter = GLGlobalIndexedParameter.fromConstant(parameterCode);

		if (parameter == GLGlobalIndexedParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (index < 0) {
			throw new IllegalArgumentException("Index is negative.");
		}

		if (index >= parameter.getIndexSource().getRange(this)) {
			throw new IllegalArgumentException("Index is outside of the valid range for the indexed state parameter.");
		}

		return globalFunctions.getIntegeri(parameterCode, index);
	}

	@Override
	public long getInteger64i(int parameterCode, int index) {
		var parameter = GLGlobalIndexedParameter.fromConstant(parameterCode);

		if (parameter == GLGlobalIndexedParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (index < 0) {
			throw new IllegalArgumentException("Index is negative.");
		}

		if (index >= parameter.getIndexSource().getRange(this)) {
			throw new IllegalArgumentException("Index is outside of the valid range for the indexed state parameter.");
		}

		return globalFunctions.getInteger64i(parameterCode, index);
	}

	@Override
	public void activeTexture(int textureUnit) {
		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit cannot be negative.");
		}

		if (textureUnit < GL13.GL_TEXTURE0) {
			throw new IllegalArgumentException("TextureUnit is not one of GL_TEXTUREi, where i ranges from zero to the value of GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS minus one.");
		}

		if (textureUnit - GL13.GL_TEXTURE0 >= textureHelperFunctions.getMaxTextureUnits()) {
			throw new IllegalArgumentException("TextureUnit is not one of GL_TEXTUREi, where i ranges from zero to the value of GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS minus one.");
		}

		globalFunctions.activeTexture(textureUnit);
	}

	@Override
	public int getError() {
		return globalFunctions.getError();
	}
}
