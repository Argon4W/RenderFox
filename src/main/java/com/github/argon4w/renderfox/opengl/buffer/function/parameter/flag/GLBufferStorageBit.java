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

package com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag;

import com.github.argon4w.renderfox.util.IBitMask;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;

public enum GLBufferStorageBit implements IBitMask {

	INVALID			(0),
	DYNAMIC_STORAGE	(GL44.GL_DYNAMIC_STORAGE_BIT),
	MAP_READ		(GL30.GL_MAP_READ_BIT),
	MAP_WRITE		(GL30.GL_MAP_WRITE_BIT),
	MAP_PERSISTENT	(GL44.GL_MAP_PERSISTENT_BIT),
	MAP_COHERENT	(GL44.GL_MAP_COHERENT_BIT),
	CLIENT_STORAGE	(GL44.GL_CLIENT_STORAGE_BIT);

	public static final int ALL_BITS =	GL44.GL_DYNAMIC_STORAGE_BIT
									|	GL30.GL_MAP_READ_BIT
									|	GL30.GL_MAP_WRITE_BIT
									|	GL44.GL_MAP_PERSISTENT_BIT
									|	GL44.GL_MAP_COHERENT_BIT
									|	GL44.GL_CLIENT_STORAGE_BIT;

	private final int bitMask;

	GLBufferStorageBit(int bitMask) {
		this.bitMask = bitMask;
	}

	@Override
	public int getBitMask() {
		return bitMask;
	}

	public boolean isIn(int flags) {
		return (flags & bitMask) != 0;
	}

	public static boolean hasInvalidBits(int flags) {
		return (flags & ~ALL_BITS) != 0;
	}

	public static GLBufferStorageFlag toFlag(int flags) {
		return new GLBufferStorageFlag(flags);
	}
}
