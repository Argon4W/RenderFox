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

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;

import java.nio.InvalidMarkException;

@SuppressWarnings("unchecked")
public abstract class AbstractDataView<T extends AbstractDataView<T>> implements IDataView<T> {

	protected final	long capacity;
	protected		long position;
	protected		long limit;
	protected		long mark;

	public AbstractDataView(long capacity) {
		this.capacity	= capacity;
		this.position	= 0;
		this.limit		= capacity;
		this.mark		= -1;
	}

	@Override
	public abstract T slice(IDataRange range);

	@Override
	public <R extends IDataView<R>> R as(IDataViewDecorator<R> dataViewDecorator) {
		return dataViewDecorator.decorate(this);
	}

	@Override
	public IDataRange flush() {
		return new DataRange(limit);
	}

	@Override
	public IDataRange flush(IDataRange range) {
		return range;
	}

	@Override
	public long capacity() {
		return capacity;
	}

	@Override
	public long remaining() {
		var remaining = limit - position;

		return remaining < 0 ? 0 : remaining;
	}

	@Override
	public long position() {
		return position;
	}

	@Override
	public long limit() {
		return limit;
	}

	@Override
	public T position(long position) {
		if (position < 0) {
			throw new IllegalArgumentException("Position cannot be negative.");
		}

		if (position > limit) {
			throw new IllegalArgumentException("Position cannot be greater than the value of limit.");
		}

		this.position = position;
		return (T) this;
	}

	@Override
	public T limit(long limit) {
		if (limit < 0) {
			throw new IllegalArgumentException("Limit cannot be negative.");
		}

		if (limit > capacity) {
			throw new IllegalArgumentException("Limit cannot be greater than the value of capacity.");
		}

		this.limit = limit;
		return (T) this;
	}

	@Override
	public T rewind() {
		position	= 0;
		mark		= -1;

		return (T) this;
	}

	@Override
	public T flip() {
		limit		= position;
		position	= 0;
		mark		= -1;

		return (T) this;
	}

	@Override
	public T mark() {
		mark = position;
		return (T) this;
	}

	@Override
	public T reset() {
		if (this.mark < 0) {
			throw new InvalidMarkException();
		}

		position = mark;

		return (T) this;
	}

	@Override
	public T clear() {
		position	= 0;
		limit		= capacity;
		mark		= -1;

		return (T) this;
	}

	@Override
	public T putByte(byte value) {
		putByte(position, value);

		position += 1;

		return (T) this;
	}

	@Override
	public T putShort(short value) {
		putShort(position, value);

		position += 2;

		return (T) this;
	}

	@Override
	public T putInt(int value) {
		putInt(position, value);

		position += 4;

		return (T) this;
	}

	@Override
	public T putInt24(int value) {
		putInt(position, value & 0xFFFFFF);

		position += 3;

		return (T) this;
	}

	@Override
	public T putLong(long value) {
		putLong(position, value);

		position += 8;

		return (T) this;
	}

	@Override
	public T putFloat(float value) {
		putFloat(position, value);

		position += 4;

		return (T) this;
	}

	@Override
	public T putDouble(double value) {
		putDouble(position, value);

		position += 8;

		return (T) this;
	}

	@Override
	public T putBytes(byte[] value) {
		putBytes(position, value);

		position += value.length;

		return (T) this;
	}

	@Override
	public T putShorts(short[] value) {
		putShorts(position, value);

		position += (long) value.length * Short.BYTES;

		return (T) this;
	}

	@Override
	public T putInts(int[] value) {
		putInts(position, value);

		position += (long) value.length * Integer.BYTES;

		return (T) this;
	}

	@Override
	public T putLongs(long[] value) {
		putLongs(position, value);

		position += (long) value.length * Long.BYTES;

		return (T) this;
	}

	@Override
	public T putFloats(float[] value) {
		putFloats(position, value);

		position += (long) value.length * Float.BYTES;

		return (T) this;
	}

	@Override
	public T putDoubles(double[] value) {
		putDoubles(position, value);

		position += (long) value.length * Double.BYTES;

		return (T) this;
	}

	@Override
	public T putBuffer(IDataView<?> value) {
		putBuffer(position, value);

		position += value.remaining();

		return (T) this;
	}

	@Override
	public T putBuffer(long valueAddress, long length) {
		putBuffer(position, valueAddress, 0, length);

		position += length;

		return (T) this;
	}

	@Override
	public T putBytes(
			byte[]	value,
			long	offset,
			long	length
	) {
		putBytes(
				position,
				value,
				offset,
				length
		);

		position += length;

		return (T) this;
	}

	@Override
	public T putShorts(
			short[]	value,
			long	offset,
			long	length
	) {
		putShorts(
				position,
				value,
				offset,
				length
		);

		position += length * Short.BYTES;

		return (T) this;
	}

	@Override
	public T putInts(
			int[]	value,
			long	offset,
			long	length
	) {
		putInts(
				position,
				value,
				offset,
				length
		);

		position += length * Integer.BYTES;

		return (T) this;
	}

	@Override
	public T putLongs(
			long[]	value,
			long	offset,
			long	length
	) {
		putLongs(
				position,
				value,
				offset,
				length
		);

		position += length * Long.BYTES;

		return (T) this;
	}

	@Override
	public T putFloats(
			float[]	value,
			long	offset,
			long	length
	) {
		putFloats(
				position,
				value,
				offset,
				length
		);

		position += length * Float.BYTES;

		return (T) this;
	}

	@Override
	public T putDoubles(
			double[]	value,
			long		offset,
			long		length
	) {
		putDoubles(
				position,
				value,
				offset,
				length
		);

		position += length * Double.BYTES;

		return (T) this;
	}

	@Override
	public T putBuffer(
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		putBuffer(
				position,
				value,
				offset,
				length
		);

		position += length;

		return (T) this;
	}

	@Override
	public T putBuffer(
			long valueAddress,
			long offset,
			long length
	) {
		putBuffer(
				position,
				valueAddress,
				offset,
				length
		);

		position += length;

		return (T) this;
	}

	@Override
	public T putBytes(long position, byte[] value) {
		putBytes(
				position,
				value,
				0,
				value.length
		);

		return (T) this;
	}

	@Override
	public T putShorts(long position, short[] value) {
		putShorts(
				position,
				value,
				0,
				value.length
		);

		return (T) this;
	}

	@Override
	public T putInts(long position, int[] value) {
		putInts(
				position,
				value,
				0,
				value.length
		);

		return (T) this;
	}

	@Override
	public T putLongs(long position, long[] value) {
		putLongs(
				position,
				value,
				0,
				value.length
		);

		return (T) this;
	}

	@Override
	public T putFloats(long position, float[] value) {
		putFloats(
				position,
				value,
				0,
				value.length
		);

		return (T) this;
	}

	@Override
	public T putDoubles(long position, double[] value) {
		putDoubles(
				position,
				value,
				0,
				value.length
		);

		return (T) this;
	}

	@Override
	public T putBuffer(long position, IDataView<?> value) {
		putBuffer(
				position,
				value,
				value.position	(),
				value.remaining	()
		);

		return (T) this;
	}

	@Override
	public byte getByte() {
		var value = getByte(position);

		position += 1;

		return value;
	}

	@Override
	public short getShort() {
		var value = getShort(position);

		position += 2;

		return value;
	}

	@Override
	public int getInt() {
		var value = getInt(position);

		position += 4;

		return value;
	}

	@Override
	public long getLong() {
		var value = getLong(position);

		position += 8;

		return value;
	}

	@Override
	public float getFloat() {
		var value = getFloat(position);

		position += 4;

		return value;
	}

	@Override
	public double getDouble() {
		var value = getDouble(position);

		position += 8;

		return value;
	}

	@Override
	public T getBytes(byte[] value) {
		getBytes(
				position,
				value,
				0,
				value.length
		);

		position += value.length;

		return (T)  this;
	}

	@Override
	public T getBytes(long position, byte[] value) {
		getBytes(
				position,
				value,
				0,
				value.length
		);

		return (T) this;
	}

	@Override
	public T getBytes(byte[] value, long offset, long length) {
		getBytes(
				position,
				value,
				offset,
				length
		);

		position += length;

		return (T) this;
	}

	@Override
	public T slice(long length) {
		var range = new DataRange(
				position,
				length
		);

		var view = slice(range);

		position += length;

		return view;
	}
}
