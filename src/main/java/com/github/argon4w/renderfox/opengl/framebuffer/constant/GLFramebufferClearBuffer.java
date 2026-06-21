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

package com.github.argon4w.renderfox.opengl.framebuffer.constant;

import com.github.argon4w.renderfox.opengl.constant.GLNoneConstants;
import com.github.argon4w.renderfox.opengl.constant.GLQueryConstantsInt;
import com.github.argon4w.renderfox.opengl.constant.GLZeroConstant;
import com.github.argon4w.renderfox.opengl.constant.IGLDefinedConstants;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public enum GLFramebufferClearBuffer {

	INVALID			(-1,					0, GLNoneConstants.of		()),
	COLOR			(GL11.GL_COLOR,			4, new GLQueryConstantsInt	(GLGlobalParameter.MAX_DRAW_BUFFERS, 0)),
	DEPTH			(GL11.GL_DEPTH,			1, GLZeroConstant.of		()),
	STENCIL			(GL11.GL_STENCIL,		1, GLZeroConstant.of		()),
	DEPTH_STENCIL	(GL30.GL_DEPTH_STENCIL,	2, GLZeroConstant.of		());

	private static final Int2ReferenceMap<GLFramebufferClearBuffer> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var framebufferClearBuffer : GLFramebufferClearBuffer.values()) {
			TABLE.put(framebufferClearBuffer.constant, framebufferClearBuffer);
		}
	}

	private final int											constant;
	private final int											count;
	private final IGLDefinedConstants<IGLGlobalFunctionsHelper>	values;

	GLFramebufferClearBuffer(
			int												constant,
			int												count,
			IGLDefinedConstants<IGLGlobalFunctionsHelper>	values
	) {
		this.constant	= constant;
		this.count		= count;
		this.values		= values;
	}

	public int getConstant() {
		return constant;
	}

	public int getCount() {
		return count;
	}

	public IGLDefinedConstants<IGLGlobalFunctionsHelper> getValues() {
		return values;
	}

	public int getDrawBuffer() {
		return 0;
	}

	public static GLFramebufferClearBuffer fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
