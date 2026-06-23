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

import com.github.argon4w.renderfox.util.BitFlagUtils;

public class GLFramebufferWriteBuffer {

	private int bitFlags;

	public GLFramebufferWriteBuffer(GLFramebufferWriteBuffer writeBuffer) {
		this.bitFlags = writeBuffer.bitFlags;
	}

	public GLFramebufferWriteBuffer(int bitFlags) {
		this.bitFlags = bitFlags;
	}

	public GLFramebufferWriteBuffer() {
		this.bitFlags = 0;
	}

	public int getFlags() {
		return bitFlags;
	}

	public void add(GLFramebufferWriteBufferBit writeBufferBit) {
		bitFlags |= writeBufferBit.getBitMask();
	}

	public boolean isEmpty() {
		return bitFlags == 0;
	}

	public void remove(GLFramebufferWriteBufferBit writeBufferBit) {
		bitFlags &= ~writeBufferBit.getBitMask();
	}

	public boolean has(GLFramebufferWriteBufferBit writeBufferBit) {
		return (bitFlags & writeBufferBit.getBitMask()) != 0;
	}

	public boolean isInvalid() {
		return GLFramebufferWriteBufferBit.hasInvalidBits(bitFlags);
	}

	public boolean writeColor() {
		return has(GLFramebufferWriteBufferBit.COLOR_BUFFER_BIT);
	}

	public boolean writeDepth() {
		return has(GLFramebufferWriteBufferBit.DEPTH_BUFFER_BIT);
	}

	public boolean writeStencil() {
		return has(GLFramebufferWriteBufferBit.STENCIL_BUFFER_BIT);
	}

	public boolean writeDepthOrStencil() {
		return writeDepth() || writeStencil();
	}

	public GLFramebufferWriteBuffer copy() {
		return new GLFramebufferWriteBuffer(bitFlags);
	}

	public static GLFramebufferWriteBuffer of() {
		return new GLFramebufferWriteBuffer();
	}

	public static GLFramebufferWriteBuffer of(GLFramebufferWriteBufferBit... writeBufferBits) {
		if (writeBufferBits == null) {
			throw new IllegalArgumentException("AccessBits cannot be null.");
		}

		if (writeBufferBits.length == 0) {
			throw new IllegalArgumentException("AccessBits cannot be empty.");
		}

		return new GLFramebufferWriteBuffer(BitFlagUtils.toFlag(writeBufferBits));
	}
}
