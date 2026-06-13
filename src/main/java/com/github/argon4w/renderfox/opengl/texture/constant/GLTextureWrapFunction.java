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
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL44;

public enum GLTextureWrapFunction {

	INVALID				(-1,							false),
	CLAMP_TO_EDGE		(GL12.GL_CLAMP_TO_EDGE,			true),
	CLAMP_TO_BORDER		(GL13.GL_CLAMP_TO_BORDER,		true),
	MIRRORED_REPEAT		(GL14.GL_MIRRORED_REPEAT,		false),
	REPEAT				(GL12.GL_REPEAT,				false),
	MIRROR_CLAMP_TO_EDGE(GL44.GL_MIRROR_CLAMP_TO_EDGE,	false);

	private static final Int2ReferenceMap<GLTextureWrapFunction> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var function : GLTextureWrapFunction.values()) {
			TABLE.put(function.getConstant(), function);
		}
	}

	private final int		constant;
	private final boolean	rectangle;

	GLTextureWrapFunction(int constant, boolean rectangle) {
		this.constant	= constant;
		this.rectangle	= rectangle;
	}

	public int getConstant() {
		return constant;
	}

	public boolean isRectangle() {
		return rectangle;
	}

	public static GLTextureWrapFunction fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}

	public static class TextureDefinedConstants extends AbstractGLDefinedConstants<GLTextureType> {

		public static final TextureDefinedConstants INSTANCE = new TextureDefinedConstants();

		@Override
		public boolean isValidValue(GLTextureType textureType, UnionValue value) {
			var function = fromConstant(value.asInt());

			if (function == INVALID) {
				return false;
			}

			if (textureType == GLTextureType.TEXTURE_RECTANGLE) {
				return function.rectangle;
			}

			return true;
		}
	}

	public static class SamplerDefinedConstants extends AbstractGLDefinedConstants<IGLGlobalFunctionsHelper> {

		public static final SamplerDefinedConstants INSTANCE = new SamplerDefinedConstants();

		@Override
		public boolean isValidValue(IGLGlobalFunctionsHelper context, UnionValue value) {
			return fromConstant(value.asInt()) != INVALID;
		}
	}
}
