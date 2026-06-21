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

package com.github.argon4w.renderfox.opengl.buffer.function.parameter;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;

public enum GLBufferParameter {

	INVALID					(-1,								0),
	BUFFER_ACCESS			(GL15.GL_BUFFER_ACCESS,				32),
	BUFFER_ACCESS_FLAGS		(GL30.GL_BUFFER_ACCESS_FLAGS,		32),
	BUFFER_IMMUTABLE_STORAGE(GL44.GL_BUFFER_IMMUTABLE_STORAGE,	32),
	BUFFER_MAPPED			(GL15.GL_BUFFER_MAPPED,				32),
	BUFFER_MAP_LENGTH		(GL30.GL_BUFFER_MAP_LENGTH,			64),
	BUFFER_MAP_OFFSET		(GL30.GL_BUFFER_MAP_OFFSET, 		64),
	BUFFER_SIZE				(GL15.GL_BUFFER_SIZE,				32),
	BUFFER_STORAGE_FLAGS	(GL44.GL_BUFFER_STORAGE_FLAGS,		32),
	GL_BUFFER_USAGE			(GL15.GL_BUFFER_USAGE,				32);

	private static final Int2ReferenceMap<GLBufferParameter> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var bufferParameter : GLBufferParameter.values()) {
			TABLE.put(bufferParameter.constant, bufferParameter);
		}
	}

	private final int constant;
	private final int size;

	GLBufferParameter(int constant, int size) {
		this.constant	= constant;
		this.size		= size;
	}

	public int getConstant() {
		return constant;
	}

	public int getSize() {
		return size;
	}

	public static GLBufferParameter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
