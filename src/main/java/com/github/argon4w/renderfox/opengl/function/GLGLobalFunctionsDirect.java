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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class GLGLobalFunctionsDirect implements IGLGlobalFunctions {

	private IGLBindingSource bindingSource;

	public GLGLobalFunctionsDirect() {
		this.bindingSource = null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		bindingSource = device.getBindingSource();
	}

	@Override
	public float getFloat(int parameter) {
		return GL11.glGetFloat(parameter);
	}

	@Override
	public int getInteger(int parameter) {
		return GL11.glGetInteger(parameter);
	}

	@Override
	public int getIntegeri(int parameter, int index) {
		return GL30.glGetIntegeri(parameter, index);
	}

	@Override
	public long getInteger64(int parameter) {
		return GL32.glGetInteger64(parameter);
	}

	@Override
	public long getInteger64i(int parameter, int index) {
		return GL32.glGetInteger64i(parameter, index);
	}

	@Override
	public void activeTexture(int textureUnit) {
		GL13.glActiveTexture(textureUnit);

		bindingSource.activeTexture(textureUnit);
	}

	@Override
	public int getError() {
		return GL11.glGetError();
	}

	public static IGLGlobalFunctions of() {
		return new GLGLobalFunctionsDirect();
	}
}
