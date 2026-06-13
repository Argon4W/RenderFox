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

public enum GLBufferUsage {

	INVALID			(-1),
	STREAM_DRAW		(GL15.GL_STREAM_DRAW),
	STREAM_READ		(GL15.GL_STREAM_READ),
	STREAM_COPY		(GL15.GL_STREAM_COPY),
	STATIC_DRAW		(GL15.GL_STATIC_DRAW),
	STATIC_READ		(GL15.GL_STATIC_READ),
	STATIC_COPY		(GL15.GL_STATIC_COPY),
	DYNAMIC_DRAW	(GL15.GL_DYNAMIC_DRAW),
	DYNAMIC_READ	(GL15.GL_DYNAMIC_READ),
	DYNAMIC_COPY	(GL15.GL_DYNAMIC_COPY);

	public static final Int2ReferenceMap<GLBufferUsage> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var bufferUsage : GLBufferUsage.values()) {
			TABLE.put(bufferUsage.constant,  bufferUsage);
		}
	}

	private final int constant;

	GLBufferUsage(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLBufferUsage fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
