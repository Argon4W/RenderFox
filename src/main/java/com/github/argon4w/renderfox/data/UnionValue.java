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

package com.github.argon4w.renderfox.data;

public class UnionValue {

	private final long value;

	public UnionValue(byte value) {
		this.value = value;
	}

	public UnionValue(short value) {
		this.value = value;
	}

	public UnionValue(int value) {
		this.value = value;
	}

	public UnionValue(long value) {
		this.value = value;
	}

	public UnionValue(double value) {
		this.value = Double.doubleToRawLongBits(value);
	}

	public byte asByte() {
		return (byte) (value & 0xFF);
	}

	public short asShort() {
		return (short) (value & 0xFFFF);
	}

	public int asInt() {
		return (int) (value & 0xFFFFFFFFL);
	}

	public int asInt24() {
		return (int) (value & 0xFFFFFF);
	}

	public long asLong() {
		return value;
	}

	public double asDouble() {
		return Double.longBitsToDouble(asLong());
	}

	public float asFloat() {
		return Float.intBitsToFloat(asInt());
	}

	public short asFloat16() {
		return Float.floatToFloat16(asFloat());
	}
}
