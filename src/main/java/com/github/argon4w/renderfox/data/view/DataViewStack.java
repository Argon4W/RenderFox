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

package com.github.argon4w.renderfox.data.view;

import org.lwjgl.system.MemoryStack;

public class DataViewStack implements AutoCloseable {

	private final MemoryStack stack;

	public DataViewStack(MemoryStack stack) {
		this.stack	= stack;
	}

	@Override
	public void close() {
		stack.close();
	}

	public IDataView<?> asDataView(long address, long size) {
		return new AddressDataView(address, size);
	}

	public IDataView<?> of(long bytes) {
		return new AddressDataView(stack.nmalloc((int) bytes), bytes);
	}

	public IDataView<?> ofShorts(long shorts) {
		return of(Short.BYTES * shorts);
	}

	public IDataView<?> ofInts(long ints) {
		return of(Integer.BYTES * ints);
	}

	public IDataView<?> ofLongs(long longs) {
		return of(Long.BYTES * longs);
	}

	public IDataView<?> ofFloats(long floats) {
		return of(Float.BYTES * floats);
	}

	public IDataView<?> ofDoubles(long doubles) {
		return of(Double.BYTES * doubles);
	}

	public IDataView<?> of(byte[] bytes) {
		return of(bytes.length).putBytes(0L, bytes);
	}

	public IDataView<?> ofShorts(short[] shorts) {
		return ofShorts(shorts.length).putShorts(0L, shorts);
	}

	public IDataView<?> ofInts(int[] ints) {
		return ofInts(ints.length).putInts(0L, ints);
	}

	public IDataView<?> ofLongs(long[] longs) {
		return ofLongs(longs.length).putLongs(0L, longs);
	}

	public IDataView<?> ofFloats(float[] floats) {
		return ofFloats(floats.length).putFloats(0L, floats);
	}

	public IDataView<?> ofDoubles(double[] doubles) {
		return ofDoubles(doubles.length).putDoubles(0L, doubles);
	}

	public IDataView<?> aByte(byte value) {
		return of(1L).putByte(0L, value);
	}

	public IDataView<?> aShort(short value) {
		return ofShorts(1L).putShort(0L, value);
	}

	public IDataView<?> aInt(int value) {
		return ofInts(1L).putInt(0L, value);
	}

	public IDataView<?> aLong(long value) {
		return ofLongs(1L).putLong(0L, value);
	}

	public IDataView<?> aFloat(float value) {
		return ofFloats(1L).putFloat(0L, value);
	}

	public IDataView<?> aDouble(double value) {
		return ofDoubles(1L).putDouble(0L, value);
	}

	public static DataViewStack stackPush() {
		return new DataViewStack(MemoryStack.stackPush());
	}
}
