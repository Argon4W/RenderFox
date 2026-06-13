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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

public enum GLError {

	NO_ERROR						(GL11.GL_NO_ERROR,						"No error",							"No error occurred."),
	CONTEXT_LOST					(GL45.GL_CONTEXT_LOST,					"Context lost",						"Context has been lost and reset by the driver."),
	INVALID_ENUM					(GL11.GL_INVALID_ENUM,					"Invalid enum",						"Enum argument out of range."),
	INVALID_VALUE					(GL11.GL_INVALID_VALUE,					"Invalid value",					"Numeric argument out of range."),
	INVALID_OPERATION				(GL11.GL_INVALID_OPERATION,				"Invalid operation",				"Operation illegal in current state."),
	INVALID_FRAMEBUFFER_OPERATION	(GL30.GL_INVALID_FRAMEBUFFER_OPERATION,	"Invalid framebuffer operation",	"Framebuffer object is not complete."),
	OUT_OF_MEMORY					(GL11.GL_OUT_OF_MEMORY,					"Out of memory",					"Not enough memory left to execute command."),
	STACK_OVERFLOW					(GL11.GL_STACK_OVERFLOW,				"Stack overflow",					"Command would cause a stack overflow."),
	STACK_UNDERFLOW					(GL11.GL_STACK_UNDERFLOW,				"Stack underflow",					"Command would cause a stack underflow."),;

	private static final int		OFFSET	= 0x500;
	private static final GLError[]	TABLE	= {
			INVALID_ENUM,					// Gl11.INVALID_ENUM					== 0x500 == 0x500 + 0
			INVALID_VALUE,					// Gl11.INVALID_VALUE					== 0x501 == 0x500 + 1
			INVALID_OPERATION,				// GL11.INVALID_OPERATION				== 0x502 == 0x500 + 2
			STACK_OVERFLOW,					// GL11.STACK_OVERFLOW					== 0x503 == 0x500 + 3
			STACK_UNDERFLOW,				// GL11.STACK_UNDERFLOW					== 0x504 == 0x500 + 4
			OUT_OF_MEMORY,					// GL11.OUT_OF_MEMORY					== 0x505 == 0x500 + 5
			INVALID_FRAMEBUFFER_OPERATION,	// GL30.INVALID_FRAMEBUFFER_OPERATION	== 0x506 == 0x500 + 6
			CONTEXT_LOST					// GL45.GL_CONTEXT_LOST					== 0x507 == 0x500 + 7
	};

	private final int		errorCode;
	private final String	name;
	private final String description;

	GLError(
			int		errorCode,
			String	errorName,
			String	description
	) {
		this.errorCode		= errorCode;
		this.name			= errorName;
		this.description	= description;
	}

	@Override
	public String toString() {
		return "OpenGL Error \"%s\" (0x%03X): \"%s\"".formatted(
				name,
				errorCode,
				description
		);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static GLError fromErrorCode(int code) {
		return code < OFFSET ? NO_ERROR : TABLE[code - OFFSET];
	}
}
