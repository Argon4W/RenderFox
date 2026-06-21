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

import com.github.argon4w.renderfox.opengl.format.GLFormatComponent;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

public enum GLTextureComponentType {

	INVALID				(-1,							-1),
	NONE				(GL11.GL_NONE,					0),
	SIGNED_NORMALIZED	(GL31.GL_SIGNED_NORMALIZED,		GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK),
	UNSIGNED_NORMALIZED	(GL30.GL_UNSIGNED_NORMALIZED,	GLFormatComponent.NORMALIZED_MASK),
	FLOAT				(GL11.GL_FLOAT,															GLFormatComponent.SIGNED_MASK),
	SIGNED_INT			(GL11.GL_INT,					GLFormatComponent.INTEGER_MASK		|	GLFormatComponent.SIGNED_MASK),
	UNSIGNED_INT		(GL11.GL_UNSIGNED_INT,			GLFormatComponent.INTEGER_MASK);

	private static final Int2ReferenceMap<GLTextureComponentType> TABLE_CONSTANT;
	private static final Int2ReferenceMap<GLTextureComponentType> TABLE_FLAGS;

	static {
		TABLE_CONSTANT	= new Int2ReferenceOpenHashMap<>();
		TABLE_FLAGS		= new Int2ReferenceOpenHashMap<>();

		for (var componentType : GLTextureComponentType.values()) {
			TABLE_CONSTANT	.put(componentType.constant,	componentType);
			TABLE_FLAGS		.put(componentType.flags,		componentType);
		}
	}

	private final int constant;
	private final int flags;

	GLTextureComponentType(int constant, int flags) {
		this.constant	= constant;
		this.flags		= flags;
	}

	public int getConstant() {
		return constant;
	}

	public int getFlags() {
		return flags;
	}

	public boolean isNormalized() {
		return (flags & GLFormatComponent.NORMALIZED_MASK) != 0;
	}

	public boolean isSigned() {
		return (flags & GLFormatComponent.SIGNED_MASK) != 0;
	}

	public boolean isInteger() {
		return (flags & GLFormatComponent.INTEGER_MASK) != 0;
	}

	public static GLTextureComponentType fromConstant(int constant) {
		return TABLE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLTextureComponentType fromFlags(int flags) {
		return TABLE_FLAGS.getOrDefault(flags, INVALID);
	}

	public static GLTextureComponentType fromInternalFormat(GLInternalFormat internalFormat) {
		return TABLE_FLAGS.getOrDefault(internalFormat.getFlags() & (0b111 << 6), INVALID);
	}
}
