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

public enum GLTextureComponent {

	INVALID	(-1),
	RED		(GL11.GL_RED),
	GREEN	(GL11.GL_GREEN),
	BLUE	(GL11.GL_BLUE),
	ALPHA	(GL11.GL_ALPHA);

	private static final Int2ReferenceMap<GLTextureComponent> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var mode : GLTextureComponent.values()) {
			TABLE.put(mode.getConstant(), mode);
		}
	}

	private final int constant;

	GLTextureComponent(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLTextureComponent fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}

	public static class DefinedConstants extends AbstractGLDefinedConstants<GLTextureType> {

		public static final DefinedConstants SINGLE		= new DefinedConstants(1);
		public static final DefinedConstants COMBINED	= new DefinedConstants(4);

		private final int count;

		private DefinedConstants(int count) {
			this.count = count;
		}

		@Override
		public boolean isValidValue(GLTextureType textureType, UnionValue value) {
			return fromConstant(value.asInt()) != INVALID;
		}

		@Override
		public int getCount() {
			return count;
		}
	}
}
