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

package com.github.argon4w.renderfox.opengl.texture.constant;

import com.github.argon4w.renderfox.data.UnionValue;
import com.github.argon4w.renderfox.opengl.constant.AbstractGLDefinedConstants;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;

public enum GLTextureCompareFunction {

	INVALID		(-1),
	LEQUAL		(GL11.GL_LEQUAL),
	GEQUAL		(GL11.GL_GEQUAL),
	LESS		(GL11.GL_LESS),
	GREATER		(GL11.GL_GREATER),
	EQUAL		(GL11.GL_EQUAL),
	NOTEQUAL	(GL11.GL_NOTEQUAL),
	ALWAYS		(GL11.GL_ALWAYS),
	NEVER		(GL11.GL_NEVER);

	private static final Int2ReferenceMap<GLTextureCompareFunction> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var function : GLTextureCompareFunction.values()) {
			TABLE.put(function.getConstant(), function);
		}
	}

	private final int constant;

	GLTextureCompareFunction(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLTextureCompareFunction fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}

	public static class DefinedConstants<T> extends AbstractGLDefinedConstants<T> {

		public static final DefinedConstants<?> INSTANCE = new DefinedConstants<>();

		@Override
		public boolean isValidValue(T context, UnionValue value) {
			return fromConstant(value.asInt()) != INVALID;
		}

		@SuppressWarnings("unchecked")
		public static <T> DefinedConstants<T> of() {
			return (DefinedConstants<T>) INSTANCE;
		}
	}
}
