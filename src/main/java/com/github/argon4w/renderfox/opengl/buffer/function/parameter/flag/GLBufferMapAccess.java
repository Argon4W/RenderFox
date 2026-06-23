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

import com.github.argon4w.renderfox.util.BitFlagUtils;

public class GLBufferMapAccess {

	private int bitFlags;

	public GLBufferMapAccess(GLBufferMapAccess access) {
		this.bitFlags = access.bitFlags;
	}

	public GLBufferMapAccess(int bitFlags) {
		this.bitFlags = bitFlags;
	}

	public GLBufferMapAccess() {
		this.bitFlags = 0;
	}

	public int getFlags() {
		return bitFlags;
	}

	public void add(GLBufferAccessBit accessBit) {
		bitFlags |= accessBit.getBitMask();
	}

	public boolean isEmpty() {
		return bitFlags == 0;
	}

	public void remove(GLBufferAccessBit accessBit) {
		bitFlags &= ~accessBit.getBitMask();
	}

	public boolean same(GLBufferMapAccess access) {
		return access.bitFlags == this.bitFlags;
	}

	public boolean has(GLBufferAccessBit accessBit) {
		return (bitFlags & accessBit.getBitMask()) != 0;
	}

	public boolean isInvalid() {
		return GLBufferAccessBit.hasInvalidBits(bitFlags);
	}

	public boolean isPersistent() {
		return has(GLBufferAccessBit.MAP_PERSISTENT);
	}

	public boolean isCoherent() {
		return has(GLBufferAccessBit.MAP_COHERENT);
	}

	public boolean canRead() {
		return has(GLBufferAccessBit.MAP_READ);
	}

	public boolean canWrite() {
		return has(GLBufferAccessBit.MAP_WRITE);
	}

	public boolean isExplicit() {
		return has(GLBufferAccessBit.MAP_FLUSH_EXPLICIT);
	}

	public boolean isUnsynchronized() {
		return has(GLBufferAccessBit.MAP_UNSYNCHRONIZED);
	}

	public boolean isInvalidated() {
		return has(GLBufferAccessBit.MAP_INVALIDATE_BUFFER) || has(GLBufferAccessBit.MAP_INVALIDATE_RANGE);
	}

	public GLBufferMapAccess copy() {
		return new GLBufferMapAccess(bitFlags);
	}

	public boolean matches(GLBufferStorageFlag storageFlag) {
		for (var accessBit : GLBufferAccessBit.values()) {
			if (has(accessBit) && !accessBit.isIn(storageFlag)) {
				return false;
			}
		}

		return true;
	}

	public static GLBufferMapAccess of() {
		return new GLBufferMapAccess();
	}

	public static GLBufferMapAccess of(GLBufferAccessBit... accessBits) {
		if (accessBits == null) {
			throw new IllegalArgumentException("AccessBits cannot be null.");
		}

		if (accessBits.length == 0) {
			throw new IllegalArgumentException("AccessBits cannot be empty.");
		}

		return new GLBufferMapAccess(BitFlagUtils.toFlag(accessBits));
	}
}
