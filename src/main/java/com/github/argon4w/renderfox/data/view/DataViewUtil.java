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

import org.lwjgl.system.MemoryUtil;

public class DataViewUtil {

	public static AddressDataView of(long bytes) {
		return new AddressDataView(MemoryUtil.nmemAlloc(bytes), bytes);
	}

	public static AddressDataView ofShorts(long shorts) {
		return of(shorts * Short.BYTES);
	}

	public static AddressDataView ofInts(long ints) {
		return of(ints * Integer.BYTES);
	}

	public static AddressDataView ofLongs(long longs) {
		return of(longs * Long.BYTES);
	}

	public static AddressDataView ofFloats(long floats) {
		return of(floats * Float.BYTES);
	}

	public static AddressDataView ofDoubles(long doubles) {
		return of(doubles * Double.BYTES);
	}

	public static AddressDataView of(byte[] bytes) {
		return of(bytes.length).putBytes(0L, bytes);
	}

	public static AddressDataView ofShorts(short[] shorts) {
		return ofShorts(shorts.length).putShorts(0L, shorts);
	}

	public static AddressDataView ofInts(int[] ints) {
		return ofInts(ints.length).putInts(0L, ints);
	}

	public static AddressDataView ofLongs(long[] longs) {
		return ofLongs(longs.length).putLongs(0L, longs);
	}

	public static AddressDataView ofFloats(float[] floats) {
		return ofFloats(floats.length).putFloats(0L, floats);
	}

	public static AddressDataView ofDoubles(double[] doubles) {
		return ofDoubles(doubles.length).putDoubles(0L, doubles);
	}

	public static IDataView<?> aByte(byte value) {
		return of(1L).putByte(0L, value);
	}

	public static IDataView<?> aShort(short value) {
		return ofShorts(1L).putShort(0L, value);
	}

	public static IDataView<?> aInt(int value) {
		return ofInts(1L).putInt(0L, value);
	}

	public static IDataView<?> aLong(long value) {
		return ofLongs(1L).putLong(0L, value);
	}

	public static IDataView<?> aFloat(float value) {
		return ofFloats(1L).putFloat(0L, value);
	}

	public static IDataView<?> aDouble(double value) {
		return ofDoubles(1L).putDouble(0L, value);
	}

	public static void free(AddressDataView view) {
		MemoryUtil.nmemFree(view.address());
	}
}
