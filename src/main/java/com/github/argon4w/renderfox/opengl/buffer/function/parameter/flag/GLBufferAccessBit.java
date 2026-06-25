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

public enum GLBufferAccessBit implements IBitMask {

	INVALID					(0,									GLBufferStorageBit.INVALID),
	MAP_READ				(GL30.GL_MAP_READ_BIT,				GLBufferStorageBit.MAP_READ),
	MAP_WRITE				(GL30.GL_MAP_WRITE_BIT,				GLBufferStorageBit.MAP_WRITE),
	MAP_PERSISTENT			(GL44.GL_MAP_PERSISTENT_BIT,		GLBufferStorageBit.MAP_PERSISTENT),
	MAP_COHERENT			(GL44.GL_MAP_COHERENT_BIT,			GLBufferStorageBit.MAP_COHERENT),
	MAP_INVALIDATE_RANGE	(GL44.GL_MAP_INVALIDATE_RANGE_BIT,	GLBufferStorageBit.INVALID),
	MAP_INVALIDATE_BUFFER	(GL30.GL_MAP_INVALIDATE_BUFFER_BIT,	GLBufferStorageBit.INVALID),
	MAP_FLUSH_EXPLICIT		(GL30.GL_MAP_FLUSH_EXPLICIT_BIT,	GLBufferStorageBit.INVALID),
	MAP_UNSYNCHRONIZED		(GL30.GL_MAP_UNSYNCHRONIZED_BIT,	GLBufferStorageBit.INVALID);

	public static final int ALL_BITS =	GL30.GL_MAP_READ_BIT
									|	GL30.GL_MAP_WRITE_BIT
									|	GL44.GL_MAP_PERSISTENT_BIT
									|	GL44.GL_MAP_COHERENT_BIT
									|	GL30.GL_MAP_INVALIDATE_RANGE_BIT
									|	GL30.GL_MAP_INVALIDATE_BUFFER_BIT
									|	GL30.GL_MAP_FLUSH_EXPLICIT_BIT
									|	GL30.GL_MAP_UNSYNCHRONIZED_BIT;

	public static final int COMMON_BITS =	GL30.GL_MAP_READ_BIT
										|	GL30.GL_MAP_WRITE_BIT
										|	GL44.GL_MAP_PERSISTENT_BIT
										|	GL44.GL_MAP_COHERENT_BIT;

	private final int					bitMask;
	private final GLBufferStorageBit storageBit;

	GLBufferAccessBit(int bitMask, GLBufferStorageBit storageBit) {
		this.bitMask	= bitMask;
		this.storageBit	= storageBit;
	}

	@Override
	public int getBitMask() {
		return bitMask;
	}

	public boolean isIn(int mapAccess) {
		return (mapAccess & bitMask) != 0;
	}

	public static boolean hasInvalidBits(int flags) {
		return (flags & ~ALL_BITS) != 0;
	}

	public static GLBufferMapAccess toAccess(int flags) {
		return new GLBufferMapAccess(flags);
	}
}
