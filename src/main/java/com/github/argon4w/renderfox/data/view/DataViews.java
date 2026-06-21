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

public class DataViews {

	public static AddressDataView wrap(long address, long bytes) {
		return new AddressDataView(address, bytes);
	}

	public static AddressDataView wrapShorts(long address, long shorts) {
		return wrap(address, Short.BYTES * shorts);
	}

	public static AddressDataView wrapInts(long address, long ints) {
		return wrap(address, Integer.BYTES * ints);
	}

	public static AddressDataView wrapLongs(long address, long longs) {
		return wrap(address, Long.BYTES * longs);
	}

	public static AddressDataView wrapFloats(long address, long floats) {
		return wrap(address, Float.BYTES * floats);
	}

	public static AddressDataView wrapDoubles(long address, long doubles) {
		return wrap(address, Double.BYTES * doubles);
	}

	public static AddressDataView of(long bytes) {
		return new AddressDataView(MemoryUtil.nmemAlloc(bytes), bytes);
	}

	public static AddressDataView ofZeros(long bytes) {
		return new AddressDataView(MemoryUtil.nmemCalloc(bytes, 1), bytes);
	}

	public static AddressDataView aByte(byte value) {
		return of(1L).putByte(0L, value);
	}

	public static AddressDataView aShort(short value) {
		return ofShorts(1L).putShort(0L, value);
	}

	public static AddressDataView aInt(int value) {
		return ofInts(1L).putInt(0L, value);
	}

	public static AddressDataView aLong(long value) {
		return ofLongs(1L).putLong(0L, value);
	}

	public static AddressDataView aFloat(float value) {
		return ofFloats(1L).putFloat(0L, value);
	}

	public static AddressDataView aDouble(double value) {
		return ofDoubles(1L).putDouble(0L, value);
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

	public static AddressDataView ofShortsZeros(long shorts) {
		return ofZeros(Short.BYTES * shorts);
	}

	public static AddressDataView ofIntsZeros(long ints) {
		return ofZeros(Integer.BYTES * ints);
	}

	public static AddressDataView ofLongsZeros(long longs) {
		return ofZeros(Long.BYTES * longs);
	}

	public static AddressDataView ofFloatsZeros(long floats) {
		return ofZeros(Float.BYTES * floats);
	}

	public static AddressDataView ofDoublesZeros(long doubles) {
		return ofZeros(Double.BYTES * doubles);
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

	public static AddressDataView of(
			byte[]	bytes,
			int		offset,
			int		length
	) {
		return of(bytes.length).putBytes(
				0L,
				bytes,
				offset,
				length
		);
	}

	public static AddressDataView ofShorts(
			short[]	shorts,
			int		offset,
			int		length
	) {
		return ofShorts(shorts.length).putShorts(
				0L,
				shorts,
				offset,
				length
		);
	}

	public static AddressDataView ofInts(
			int[]	ints,
			int		offset,
			int		length
	) {
		return ofInts(ints.length).putInts(
				0L,
				ints,
				offset,
				length
		);
	}

	public static AddressDataView ofLongs(
			long[]	longs,
			int		offset,
			int		length
	) {
		return ofLongs(longs.length).putLongs(
				0L,
				longs,
				offset,
				length
		);
	}

	public static AddressDataView ofFloats(
			float[]	floats,
			int		offset,
			int		length
	) {
		return ofFloats(floats.length).putFloats(
				0L,
				floats,
				offset,
				length
		);
	}

	public static AddressDataView ofDoubles(
			double[]	doubles,
			int			offset,
			int			length
	) {
		return ofDoubles(doubles.length).putDoubles(
				0L,
				doubles,
				offset,
				length
		);
	}

	public static AddressDataView resize(AddressDataView view, long bytes) {
		return new AddressDataView(MemoryUtil.nmemRealloc(view.address, bytes), bytes).position(Math.min(view.position(), bytes));
	}

	public static AddressDataView resizeShorts(AddressDataView view, long shorts) {
		return resize(view, shorts * Short.BYTES);
	}

	public static AddressDataView resizeInts(AddressDataView view, long ints) {
		return resize(view, ints * Integer.BYTES);
	}

	public static AddressDataView resizeLongs(AddressDataView view, long longs) {
		return resize(view, longs * Long.BYTES);
	}

	public static AddressDataView resizeFloats(AddressDataView view, long floats) {
		return resize(view, floats * Float.BYTES);
	}

	public static AddressDataView resizeDoubles(AddressDataView view, long doubles) {
		return resize(view, doubles * Double.BYTES);
	}

	public static void free(AddressDataView view) {
		MemoryUtil.nmemFree(view.address());
	}
}
