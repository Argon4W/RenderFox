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
import com.github.argon4w.renderfox.data.UnsafeAccess;

import java.nio.BufferOverflowException;

public class HeapDataView extends AbstractDataView<HeapDataView> {

	public static final long BYTE_ARRAY_BASE_OFFSET		= UnsafeAccess.UNSAFE.arrayBaseOffset(byte	[].class);
	public static final long SHORT_ARRAY_BASE_OFFSET	= UnsafeAccess.UNSAFE.arrayBaseOffset(short	[].class);
	public static final long INT_ARRAY_BASE_OFFSET		= UnsafeAccess.UNSAFE.arrayBaseOffset(int	[].class);
	public static final long LONG_ARRAY_BASE_OFFSET		= UnsafeAccess.UNSAFE.arrayBaseOffset(long	[].class);
	public static final long FLOAT_ARRAY_BASE_OFFSET	= UnsafeAccess.UNSAFE.arrayBaseOffset(float	[].class);
	public static final long DOUBLE_ARRAY_BASE_OFFSET	= UnsafeAccess.UNSAFE.arrayBaseOffset(double[].class);

	private final byte[]	memory;
	private final long		offset;

	private HeapDataView(
			byte[]	memory,
			long	capacity,
			long	offset
	) {
		super(capacity);

		this.memory = memory;
		this.offset = offset;
	}

	@Override
	public IDataRange flush() {
		return new DataRange(offset, limit());
	}

	@Override
	public IDataRange flush(IDataRange range) {
		return range;
	}

	@Override
	public HeapDataView putByte(long position, byte value) {
		if (limit() - (position + offset) < 1) {
			throw new BufferOverflowException();
		}

		memory[(int) position + (int) offset] = value;

		return this;
	}

	@Override
	public HeapDataView putShort(long position, short value) {
		if (limit() - (position + offset) < 2) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.putShort(memory, BYTE_ARRAY_BASE_OFFSET + position + offset, value);

		return this;
	}

	@Override
	public HeapDataView putInt(long position, int value) {
		if (limit() - (position + offset) < 4) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.putInt(memory, BYTE_ARRAY_BASE_OFFSET + position + offset, value);

		return this;
	}

	@Override
	public HeapDataView putLong(long position, long value) {
		if (limit() - (position + offset) < 8) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.putLong(memory, BYTE_ARRAY_BASE_OFFSET + position + offset, value);

		return this;
	}

	@Override
	public HeapDataView putFloat(long position, float value) {
		if (limit() - (position + offset) < 4) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.putFloat(memory, BYTE_ARRAY_BASE_OFFSET + position + offset, value);

		return this;
	}

	@Override
	public HeapDataView putDouble(long position, double value) {
		if (limit() - (position + offset) < 8) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.putDouble(memory, BYTE_ARRAY_BASE_OFFSET + position + offset, value);

		return this;
	}

	@Override
	public HeapDataView putBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		if (limit() - (position + this.offset) < length) {
			throw new BufferOverflowException();
		}

		System.arraycopy(
				value,
				(int) offset,
				memory,
				(int) position + (int) this.offset,
				(int) length
		);

		return this;
	}

	@Override
	public HeapDataView putShorts(
			long	position,
			short[]	value,
			long	offset,
			long	length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		if (limit() - (position + this.offset) < length * Short.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.SHORT_ARRAY_BASE_OFFSET + offset * Short.BYTES,
				memory,
				HeapDataView.BYTE_ARRAY_BASE_OFFSET + this.offset + position,
				length * Short.BYTES
		);

		return this;
	}

	@Override
	public HeapDataView putInts(
			long	position,
			int[]	value,
			long	offset,
			long	length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		if (limit() - (position + this.offset) < length * Integer.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.INT_ARRAY_BASE_OFFSET + offset * Integer.BYTES,
				memory,
				HeapDataView.BYTE_ARRAY_BASE_OFFSET + this.offset + position,
				length * Integer.BYTES
		);

		return this;
	}

	@Override
	public HeapDataView putLongs(
			long	position,
			long[]	value,
			long	offset,
			long	length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		if (limit() - (position + this.offset) < length * Long.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.LONG_ARRAY_BASE_OFFSET + offset * Long.BYTES,
				memory,
				HeapDataView.BYTE_ARRAY_BASE_OFFSET + this.offset + position,
				length * Long.BYTES
		);

		return this;
	}

	@Override
	public HeapDataView putFloats(
			long	position,
			float[]	value,
			long	offset,
			long	length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		if (limit() - (position + this.offset) < length * Float.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.FLOAT_ARRAY_BASE_OFFSET + offset * Float.BYTES,
				memory,
				HeapDataView.BYTE_ARRAY_BASE_OFFSET + this.offset + position,
				length * Float.BYTES
		);

		return this;
	}

	@Override
	public HeapDataView putDoubles(
			long		position,
			double[]	value,
			long		offset,
			long		length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		if (limit() - (position + this.offset) < length * Double.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.DOUBLE_ARRAY_BASE_OFFSET + offset * Double.BYTES,
				memory,
				HeapDataView.BYTE_ARRAY_BASE_OFFSET + this.offset + position,
				length * Double.BYTES
		);

		return this;
	}

	@Override
	public HeapDataView putBuffer(
			long			position,
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.limit()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of buffer limit.");
		}

		if (limit() - (position + this.offset) < length) {
			throw new BufferOverflowException();
		}

		value.getBytes(
				offset,
				memory,
				position + this.offset,
				length
		);

		return this;
	}

	@Override
	public HeapDataView putBuffer(
			long position,
			long valueAddress,
			long offset,
			long length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (limit() - (position + this.offset) < length) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				null,
				valueAddress + offset,
				memory,
				BYTE_ARRAY_BASE_OFFSET + position + this.offset,
				length
		);

		return this;
	}

	@Override
	public byte getByte(long position) {
		if (limit() - position < 1) {
			throw new BufferOverflowException();
		}

		return memory[(int) position + (int) offset];
	}

	@Override
	public short getShort(long position) {
		if (limit() - position < 2) {
			throw new BufferOverflowException();
		}

		return UnsafeAccess.UNSAFE.getShort(BYTE_ARRAY_BASE_OFFSET + position + offset);
	}

	@Override
	public int getInt(long position) {
		if (limit() - position < 4) {
			throw new BufferOverflowException();
		}

		return UnsafeAccess.UNSAFE.getInt(BYTE_ARRAY_BASE_OFFSET + position + offset);
	}

	@Override
	public long getLong(long position) {
		if (limit() - position < 8) {
			throw new BufferOverflowException();
		}

		return UnsafeAccess.UNSAFE.getLong(BYTE_ARRAY_BASE_OFFSET + position + offset);
	}

	@Override
	public float getFloat(long position) {
		if (limit() - position < 4) {
			throw new BufferOverflowException();
		}

		return UnsafeAccess.UNSAFE.getFloat(BYTE_ARRAY_BASE_OFFSET + position + offset);
	}

	@Override
	public double getDouble(long position) {
		if (limit() - position < 8) {
			throw new BufferOverflowException();
		}

		return UnsafeAccess.UNSAFE.getDouble(BYTE_ARRAY_BASE_OFFSET + position + offset);
	}

	@Override
	public HeapDataView getBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > value.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		if (limit() - (position + this.offset) < length) {
			throw new IllegalArgumentException("Cannot get data from data view that exceeds the range of limit.");
		}

		System.arraycopy(
				memory,
				(int) position + (int) this.offset,
				value,
				(int) offset,
				(int) length
		);

		return this;
	}

	@Override
	public HeapDataView slice() {
		return new HeapDataView(
				memory,
				remaining	(),
				position	() + offset
		);
	}

	@Override
	public HeapDataView slice(IDataRange range) {
		if (range == null) {
			throw new IllegalArgumentException("Range cannot be null.");
		}

		if (range.getOffset() < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (range.getLength() < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (range.getOffset() + range.getLength() > limit()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of limit.");
		}

		return new HeapDataView(
				memory,
				range.getLength(),
				range.getOffset() + this.offset
		);
	}

	@Override
	public long address() {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public boolean isOffHeap() {
		return false;
	}

	public static HeapDataView wrap(byte[] memory) {
		return new HeapDataView(
				memory,
				memory.length,
				0L
		);
	}

	public static HeapDataView wrap(
			byte[]	memory,
			long	length,
			long	offset

	) {
		return new HeapDataView(
				memory,
				length,
				offset
		);
	}

	public static HeapDataView from(byte[] bytes) {
		if (bytes == null) {
			throw new IllegalArgumentException("Bytes cannot be null.");
		}

		var memory = new byte[bytes.length];

		System.arraycopy(
				bytes,
				0,
				memory,
				0,
				bytes.length
		);

		return new HeapDataView(
				memory,
				memory.length,
				0L
		);
	}

	public static HeapDataView from(
			byte[]	bytes,
			long	offset,
			long	length
	) {
		if (bytes == null) {
			throw new IllegalArgumentException("Bytes cannot be null.");
		}

		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > bytes.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of array length.");
		}

		var memory = new byte[(int) length];

		System.arraycopy(
				bytes,
				(int) offset,
				memory,
				0,
				(int) length
		);

		return new HeapDataView(
				memory,
				memory.length,
				0L
		);
	}
}
