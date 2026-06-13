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

package com.github.argon4w.renderfox.opengl.texture.pixel.function;

import com.github.argon4w.renderfox.data.UnionValue;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelParameter;

public class GLPixelFunctionsValidation implements IGLPixelFunctions {

	private final IGLPixelFunctions pixelFunctions;

	public GLPixelFunctionsValidation(IGLPixelFunctions pixelFunctions) {
		this.pixelFunctions = pixelFunctions;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		pixelFunctions.initialize(device);
	}

	@Override
	public void pixelStore(int pixelParameter, int value) {
		var parameter = GLPixelParameter.fromConstant(pixelParameter);

		if (parameter == GLPixelParameter.INVALID) {
			throw new IllegalArgumentException("PixelParameter is not an accepted value in GLPixelParameter.");
		}

		if (!parameter.getAllowedValues().isValidValue(null, new UnionValue(value))) {
			throw new IllegalArgumentException("A negative row length, pixel skip, or row skip value is specified, or if alignment is specified as other than 1, 2, 4 or 8.");
		}

		pixelFunctions.pixelStore(pixelParameter, value);
	}
}
