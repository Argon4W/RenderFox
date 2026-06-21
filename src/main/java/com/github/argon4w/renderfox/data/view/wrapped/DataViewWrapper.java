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
import com.github.argon4w.renderfox.data.view.IDataViewDecorator;

@SuppressWarnings("unchecked")
public abstract class DataViewWrapper<T extends DataViewWrapper<T>> implements IDataView<T> {

	public abstract IDataView<?> getDataView();

	@Override
	public <R extends IDataView<R>> R as(IDataViewDecorator<R> dataViewDecorator) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().as(dataViewDecorator);
	}

	@Override
	public IDataRange flush() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().flush();
	}

	@Override
	public IDataRange flush(IDataRange range) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().flush(range);
	}

	@Override
	public long capacity() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().capacity();
	}

	@Override
	public long remaining() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().remaining();
	}

	@Override
	public long position() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().position();
	}

	@Override
	public long limit() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().limit();
	}

	@Override
	public long address() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().address();
	}

	@Override
	public boolean isOffHeap() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().isOffHeap();
	}

	@Override
	public T position(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().position(position);
		return (T) this;
	}

	@Override
	public T limit(long limit) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().limit(limit);
		return (T) this;
	}

	@Override
	public T rewind() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().rewind();
		return (T) this;
	}

	@Override
	public T flip() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().flip();
		return (T) this;
	}

	@Override
	public T mark() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().mark();
		return (T) this;
	}

	@Override
	public T reset() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().reset();
		return (T) this;
	}

	@Override
	public T clear() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().clear();
		return (T) this;
	}

	@Override
	public T putByte(byte value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putByte(value);
		return (T) this;
	}

	@Override
	public T putShort(short value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShort(value);
		return (T) this;
	}

	@Override
	public T putInt(int value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInt(value);
		return (T) this;
	}

	@Override
	public T putInt24(int value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInt24(value);
		return (T) this;
	}

	@Override
	public T putLong(long value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLong(value);
		return (T) this;
	}

	@Override
	public T putFloat(float value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloat(value);
		return (T) this;
	}

	@Override
	public T putDouble(double value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDouble(value);
		return (T) this;
	}

	@Override
	public T putBytes(byte[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBytes(value);
		return (T) this;
	}

	@Override
	public T putShorts(short[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShorts(value);
		return (T) this;
	}

	@Override
	public T putInts(int[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInts(value);
		return (T) this;
	}

	@Override
	public T putLongs(long[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLongs(value);
		return (T) this;
	}

	@Override
	public T putFloats(float[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloats(value);
		return (T) this;
	}

	@Override
	public T putDoubles(double[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDoubles(value);
		return (T) this;
	}

	@Override
	public T putBuffer(IDataView<?> value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(value);
		return (T) this;
	}

	@Override
	public T putBuffer(long valueAddress, long length) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(valueAddress, length);
		return (T) this;
	}

	@Override
	public T putByte(long position, byte value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putByte(position, value);
		return (T) this;
	}

	@Override
	public T putShort(long position, short value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShort(position, value);
		return (T) this;
	}

	@Override
	public T putInt(long position, int value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInt(position, value);
		return (T) this;
	}

	@Override
	public T putLong(long position, long value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLong(position, value);
		return (T) this;
	}

	@Override
	public T putFloat(long position, float value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloat(position, value);
		return (T) this;
	}

	@Override
	public T putDouble(long position, double value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDouble(position, value);
		return (T) this;
	}

	@Override
	public T putBytes(long position, byte[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBytes(position, value);
		return (T) this;
	}

	@Override
	public T putShorts(long position, short[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShorts(position, value);
		return (T) this;
	}

	@Override
	public T putInts(long position, int[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInts(position, value);
		return (T) this;
	}

	@Override
	public T putLongs(long position, long[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLongs(position, value);
		return (T) this;
	}

	@Override
	public T putFloats(long position, float[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloats(position, value);
		return (T) this;
	}

	@Override
	public T putDoubles(long position, double[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDoubles(position, value);
		return (T) this;
	}

	@Override
	public T putBuffer(long position, IDataView<?> value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(position, value);
		return (T) this;
	}

	@Override
	public T putBytes(
			byte[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBytes(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putShorts(
			short[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShorts(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putInts(
			int[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInts(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putLongs(
			long[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLongs(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putFloats(
			float[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloats(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putDoubles(
			double[]	value,
			long		offset,
			long		length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDoubles(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putBuffer(
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putBuffer(
			long valueAddress,
			long offset,
			long length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(
				valueAddress,
				offset,
				length
		);
		return (T)  this;
	}

	@Override
	public T putBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBytes(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putShorts(
			long	position,
			short[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShorts(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putInts(
			long	position,
			int[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInts(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putLongs(
			long	position,
			long[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLongs(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putFloats(
			long	position,
			float[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloats(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putDoubles(
			long		position,
			double[]	value,
			long		offset,
			long		length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDoubles(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putBuffer(
			long			position,
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T putBuffer(
			long position,
			long valueAddress,
			long offset,
			long length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(
				position,
				valueAddress,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public byte getByte() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getByte();
	}

	@Override
	public short getShort() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getShort();
	}

	@Override
	public int getInt() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getInt();
	}

	@Override
	public long getLong() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getLong();
	}

	@Override
	public float getFloat() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getFloat();
	}

	@Override
	public double getDouble() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getDouble();
	}

	@Override
	public T getBytes(byte[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().getBytes(value);
		return (T) this;
	}

	@Override
	public byte getByte(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getByte(position);
	}

	@Override
	public short getShort(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getShort(position);
	}

	@Override
	public int getInt(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getInt(position);
	}

	@Override
	public long getLong(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getLong(position);
	}

	@Override
	public float getFloat(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getFloat(position);
	}

	@Override
	public double getDouble(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getDouble(position);
	}

	@Override
	public T getBytes(long position, byte[] value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().getBytes(position, value);
		return (T) this;
	}

	@Override
	public T getBytes(
			byte[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().getBytes(
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public T getBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().getBytes(
				position,
				value,
				offset,
				length
		);
		return (T) this;
	}

	@Override
	public IDataView<?> slice() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().slice();
	}

	@Override
	public IDataView<?> slice(long length) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().slice(length);
	}

	@Override
	public IDataView<?> slice(IDataRange range) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().slice(range);
	}
}
