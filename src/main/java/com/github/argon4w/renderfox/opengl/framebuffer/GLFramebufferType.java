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

package com.github.argon4w.renderfox.opengl.framebuffer;

import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL30;

public enum GLFramebufferType {

	INVALID			(-1,						GLGlobalParameter.INVALID),
	FRAMEBUFFER		(GL30.GL_FRAMEBUFFER,		GLGlobalParameter.FRAMEBUFFER_BINDING),
	READ_FRAMEBUFFER(GL30.GL_READ_FRAMEBUFFER,	GLGlobalParameter.READ_FRAMEBUFFER_BINDING),
	DRAW_FRAMEBUFFER(GL30.GL_DRAW_FRAMEBUFFER,	GLGlobalParameter.DRAW_FRAMEBUFFER_BINDING);

	private static final Int2ReferenceMap<GLFramebufferType> TABLE_TYPE_CONSTANT;
	private static final Int2ReferenceMap<GLFramebufferType> TABLE_BINDING_CONSTANT;

	static {
		TABLE_TYPE_CONSTANT		= new Int2ReferenceOpenHashMap<>();
		TABLE_BINDING_CONSTANT	= new Int2ReferenceOpenHashMap	<>();

		for (var framebufferType : GLFramebufferType.values()) {
			TABLE_TYPE_CONSTANT		.put(framebufferType.getConstant		(), framebufferType);
			TABLE_BINDING_CONSTANT	.put(framebufferType.getBindingConstant	(), framebufferType);
		}
	}

	private final int			constant;
	private final IGLParameter	parameter;

	GLFramebufferType(int constant, IGLParameter parameter) {
		this.constant	= constant;
		this.parameter	= parameter;
	}

	public int getConstant() {
		return constant;
	}

	public int getBindingConstant() {
		return parameter.getConstant();
	}

	public IGLParameter getParameter() {
		return parameter;
	}

	public static GLFramebufferType fromTypeConstant(int constant) {
		return TABLE_TYPE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLFramebufferType fromBindingConstant(int bindingConstant) {
		return TABLE_BINDING_CONSTANT.getOrDefault(bindingConstant, INVALID);
	}
}
