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

package com.github.argon4w.renderfox.opengl.error;

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;

import java.util.function.Supplier;

public class GLErrorChecker {

	private final OpenGLDevice device;

	public GLErrorChecker(OpenGLDevice device) {
		this.device = device;
	}

	public void runChecked(String label, Runnable runnable) {
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		clearError(label);

		runnable.run();

		checkError();
	}

	public <T> T runChecked(String label, Supplier<T> supplier) {
		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		clearError(label);

		var result = supplier.get();

		checkError();

		return result;
	}

	public void clearError(String label) {
		if (label == null) {
			throw new IllegalArgumentException("Label cannot be null.");
		}

		var error = device.getError();

		switch (error) {
			case NO_ERROR						-> {}
			case OUT_OF_MEMORY					-> throw new OutOfMemoryError			("Uncaught OpenGL out of memory error before invoking \"%s\": %s"	.formatted(label, error));
			case INVALID_VALUE, INVALID_ENUM	-> throw new IllegalArgumentException	("Uncaught OpenGL invalid argument error before invoking \"%s\": %s".formatted(label, error));
			default								-> throw new IllegalStateException		("Uncaught OpenGL error before invoking \"%s\": %s"					.formatted(label, error));
		}
	}

	public void checkError() {
		var error = device.getError();

		switch (error) {
			case NO_ERROR						-> {}
			case OUT_OF_MEMORY					-> throw new OutOfMemoryError			("OpenGL out of memory: %s"		.formatted(error.toString()));
			case INVALID_VALUE, INVALID_ENUM	-> throw new IllegalArgumentException	("Invalid OpenGL argument: %s"	.formatted(error.toString()));
			default								-> throw new IllegalStateException		("OpenGL error occurred: %s"	.formatted(error.toString()));
		}
	}
}
