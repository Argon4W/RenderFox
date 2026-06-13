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

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;

public class ReadOnlyDataView extends DataViewWrapper<ReadOnlyDataView> {
	
	private final IDataView<?> dataView;

	public ReadOnlyDataView(IDataView<?> dataView) {
		this.dataView = dataView;
	}
	
	@Override
	public IDataView<?> getDataView() {
		return dataView;
	}

	@Override
	public IDataRange flush() {
		throw new UnsupportedOperationException("Cannot lush a read-only data view.");
	}

	@Override
	public IDataRange flush(long offset, long length) {
		throw new UnsupportedOperationException("Cannot lush a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putByte(byte value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putShort(short value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putInt(int value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putInt24(int value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putLong(long value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putFloat(float value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putDouble(double value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBytes(byte[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putShorts(short[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putInts(int[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putLongs(long[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putFloats(float[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putDoubles(double[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBuffer(IDataView<?> value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBuffer(long valueAddress, long length) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putByte(long position, byte value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putShort(long position, short value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putInt(long position, int value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putLong(long position, long value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putFloat(long position, float value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putDouble(long position, double value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBytes(long position, byte[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putShorts(long position, short[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putInts(long position, int[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putLongs(long position, long[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putFloats(long position, float[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putDoubles(long position, double[] value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBuffer(long position, IDataView<?> value) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBytes(
			byte[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putShorts(
			short[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putInts(
			int[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putLongs(
			long[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putFloats(
			float[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putDoubles(
			double[]	value,
			long		offset,
			long		length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBuffer(
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBuffer(
			long valueAddress,
			long offset,
			long length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putShorts(
			long	position,
			short[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putInts(
			long	position,
			int[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putLongs(
			long	position,
			long[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putFloats(
			long	position,
			float[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putDoubles(
			long		position,
			double[]	value,
			long		offset,
			long		length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBuffer(
			long			position,
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView putBuffer(
			long position,
			long valueAddress,
			long offset,
			long length
	) {
		throw new UnsupportedOperationException("Cannot put data to a read-only data view.");
	}

	@Override
	public ReadOnlyDataView slice() {
		return new ReadOnlyDataView(super.slice());
	}

	@Override
	public ReadOnlyDataView slice(long offset, long length) {
		return new ReadOnlyDataView(super.slice(offset, length));
	}
}
