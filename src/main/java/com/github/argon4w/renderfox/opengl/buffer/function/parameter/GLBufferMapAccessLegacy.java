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

import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferAccessBit;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL15;

import java.util.Set;

public enum GLBufferMapAccessLegacy {

	INVALID			(-1),
	MAP_READ		(GL15.GL_READ_ONLY),
	MAP_WRITE		(GL15.GL_WRITE_ONLY),
	MAP_READ_WRITE	(GL15.GL_READ_WRITE);

	private	static final Int2ReferenceMap<GLBufferMapAccessLegacy>	TABLE_BIT_MASK;
	private static final GLBufferMapAccessLegacy[]					TABLE_ACCESS_BIT;

	static {
		TABLE_BIT_MASK		= new Int2ReferenceOpenHashMap<>();
		TABLE_ACCESS_BIT	= new GLBufferMapAccessLegacy	[4];

		for (var bufferMapAccessBit : GLBufferMapAccessLegacy.values()) {
			TABLE_BIT_MASK.put(bufferMapAccessBit.constant, bufferMapAccessBit);
		}

		TABLE_ACCESS_BIT[0] = INVALID;
		TABLE_ACCESS_BIT[1] = MAP_READ;
		TABLE_ACCESS_BIT[2] = MAP_WRITE;
		TABLE_ACCESS_BIT[3] = MAP_READ_WRITE;
	}

	private final int constant;

	GLBufferMapAccessLegacy(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLBufferMapAccessLegacy fromConstant(int constant) {
		return TABLE_BIT_MASK.getOrDefault(constant, INVALID);
	}

	public static GLBufferMapAccessLegacy fromMapAccessBits(Set<GLBufferAccessBit> accessBits) {
		int index = 0;

		index |= accessBits.contains(GLBufferAccessBit.MAP_READ)	? 0b01 : 0b00;
		index |= accessBits.contains(GLBufferAccessBit.MAP_WRITE)	? 0b10 : 0b00;

		return TABLE_ACCESS_BIT[index];
	}
}
