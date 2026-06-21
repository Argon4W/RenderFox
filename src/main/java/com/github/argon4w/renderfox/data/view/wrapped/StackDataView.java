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

package com.github.argon4w.renderfox.data.view.wrapped;

import com.github.argon4w.renderfox.data.view.DataViewStack;
import com.github.argon4w.renderfox.data.view.IDataView;

public class StackDataView extends DataViewWrapper<StackDataView> implements AutoCloseable {

	private final DataViewStack	dataViewStack;
	private final IDataView<?>	dataView;

	public StackDataView(long capacity, boolean zeros) {
		this.dataViewStack	= DataViewStack.stackPush();
		this.dataView		= zeros
				? dataViewStack.of		(capacity)
				: dataViewStack.ofZeros	(capacity);
	}

	@Override
	public IDataView<?> getDataView() {
		return dataView;
	}

	@Override
	public void close() {
		dataViewStack.close();
	}

	public static StackDataView of(long bytes) {
		return new StackDataView(bytes, false);
	}

	public static StackDataView ofZeros(long bytes) {
		return new StackDataView(bytes, true);
	}

	public static StackDataView aByte(byte value) {
		return of(1L).putByte(0L, value);
	}

	public static StackDataView aShort(short value) {
		return ofShorts(1L).putShort(0L, value);
	}

	public static StackDataView aInt(int value) {
		return ofInts(1L).putInt(0L, value);
	}

	public static StackDataView aLong(long value) {
		return ofLongs(1L).putLong(0L, value);
	}

	public static StackDataView aFloat(float value) {
		return ofFloats(1L).putFloat(0L, value);
	}

	public static StackDataView aDouble(double value) {
		return ofDoubles(1L).putDouble(0L, value);
	}

	public static StackDataView ofShorts(long shorts) {
		return of(Short.BYTES * shorts);
	}

	public static StackDataView ofInts(long ints) {
		return of(Integer.BYTES * ints);
	}

	public static StackDataView ofLongs(long longs) {
		return of(Long.BYTES * longs);
	}

	public static StackDataView ofFloats(long floats) {
		return of(Float.BYTES * floats);
	}

	public static StackDataView ofDoubles(long doubles) {
		return of(Double.BYTES * doubles);
	}

	public static StackDataView ofShortsZeros(long shorts) {
		return ofZeros(Short.BYTES * shorts);
	}

	public static StackDataView ofIntsZeros(long ints) {
		return ofZeros(Integer.BYTES * ints);
	}

	public static StackDataView ofLongsZeros(long longs) {
		return ofZeros(Long.BYTES * longs);
	}

	public static StackDataView ofFloatsZeros(long floats) {
		return ofZeros(Float.BYTES * floats);
	}

	public static StackDataView ofDoublesZeros(long doubles) {
		return ofZeros(Double.BYTES * doubles);
	}

	public static StackDataView of(byte[] bytes) {
		return of(bytes.length).putBytes(0L, bytes);
	}

	public static StackDataView ofShorts(short[] shorts) {
		return ofShorts(shorts.length).putShorts(0L, shorts);
	}

	public static StackDataView ofInts(int[] ints) {
		return ofInts(ints.length).putInts(0L, ints);
	}

	public static StackDataView ofLongs(long[] longs) {
		return ofLongs(longs.length).putLongs(0L, longs);
	}

	public static StackDataView ofFloats(float[] floats) {
		return ofFloats(floats.length).putFloats(0L, floats);
	}

	public static StackDataView ofDoubles(double[] doubles) {
		return ofDoubles(doubles.length).putDoubles(0L, doubles);
	}

	public static StackDataView of(
			byte[] bytes,
			int offset,
			int length
	) {
		return of(bytes.length).putBytes(
				0L,
				bytes,
				offset,
				length
		);
	}

	public static StackDataView ofShorts(
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

	public static StackDataView ofInts(
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

	public static StackDataView ofLongs(
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

	public static StackDataView ofFloats(
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

	public static StackDataView ofDoubles(
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
}
