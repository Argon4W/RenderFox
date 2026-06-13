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
import org.lwjgl.opengl.GL30;

public enum GLTextureCompareMode {

	INVALID					(-1),
	COMPARE_REF_TO_TEXTURE	(GL30.GL_COMPARE_REF_TO_TEXTURE),
	NONE					(GL11.GL_NONE);

	private static final Int2ReferenceMap<GLTextureCompareMode> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var mode : GLTextureCompareMode.values()) {
			TABLE.put(mode.getConstant(), mode);
		}
	}

	private final int constant;

	GLTextureCompareMode(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLTextureCompareMode fromConstant(int constant) {
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
