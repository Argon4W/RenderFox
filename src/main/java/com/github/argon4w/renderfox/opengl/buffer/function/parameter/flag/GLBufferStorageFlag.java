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

public class GLBufferStorageFlag {

	private int bitFlags;

	public GLBufferStorageFlag(GLBufferStorageFlag flag) {
		this.bitFlags = flag.bitFlags;
	}

	public GLBufferStorageFlag(int bitFlags) {
		this.bitFlags = bitFlags;
	}

	public GLBufferStorageFlag() {
		this.bitFlags = 0;
	}

	public int getFlags() {
		return bitFlags;
	}

	public void add(GLBufferStorageBit flagBit) {
		bitFlags |= flagBit.getBitMask();
	}

	public boolean isEmpty() {
		return bitFlags == 0;
	}

	public boolean has(GLBufferStorageBit flagBit) {
		return (bitFlags & flagBit.getBitMask()) != 0;
	}

	public boolean isInvalid() {
		return GLBufferStorageBit.hasInvalidBits(bitFlags);
	}

	public boolean isDynamic() {
		return has(GLBufferStorageBit.DYNAMIC_STORAGE);
	}

	public boolean canRead() {
		return has(GLBufferStorageBit.MAP_READ);
	}

	public boolean canWrite() {
		return has(GLBufferStorageBit.MAP_WRITE);
	}

	public boolean isPersistent() {
		return has(GLBufferStorageBit.MAP_PERSISTENT);
	}

	public boolean isCoherent() {
		return has(GLBufferStorageBit.MAP_COHERENT);
	}

	public GLBufferStorageFlag copy() {
		return new GLBufferStorageFlag(bitFlags);
	}

	public static GLBufferStorageFlag of() {
		return new GLBufferStorageFlag();
	}

	public static GLBufferStorageFlag of(GLBufferStorageBit... storageFlagBits) {
		if (storageFlagBits == null) {
			throw new IllegalArgumentException("StorageFlagBits cannot be null.");
		}

		if (storageFlagBits.length == 0) {
			throw new IllegalArgumentException("StorageFlagBits cannot be empty.");
		}

		return new GLBufferStorageFlag(BitFlagUtils.toFlag(storageFlagBits));
	}
}
