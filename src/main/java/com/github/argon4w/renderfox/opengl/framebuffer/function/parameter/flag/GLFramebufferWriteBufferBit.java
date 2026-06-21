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

package com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag;

import com.github.argon4w.renderfox.util.IBitMask;
import org.lwjgl.opengl.GL11;

public enum GLFramebufferWriteBufferBit implements IBitMask {

	INVALID				(-1),
	COLOR_BUFFER_BIT	(GL11.GL_COLOR_BUFFER_BIT),
	DEPTH_BUFFER_BIT	(GL11.GL_DEPTH_BUFFER_BIT),
	STENCIL_BUFFER_BIT	(GL11.GL_STENCIL_BUFFER_BIT);

	public static final int ALL_BITS =	GL11.GL_COLOR_BUFFER_BIT
			|							GL11.GL_DEPTH_BUFFER_BIT
			|							GL11.GL_STENCIL_BUFFER_BIT;

	private final int bitMask;

	GLFramebufferWriteBufferBit(int bitMask) {
		this.bitMask = bitMask;
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

	public static GLFramebufferWriteBuffer toFlags(int flags) {
		return new GLFramebufferWriteBuffer(flags);
	}
}
