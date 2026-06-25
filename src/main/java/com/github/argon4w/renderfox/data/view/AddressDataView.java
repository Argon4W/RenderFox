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
import org.lwjgl.system.MemoryUtil;

import java.nio.BufferOverflowException;

public class AddressDataView extends AbstractDataView<AddressDataView> {

	protected final long address;
	protected final long offset;

	public AddressDataView(
			long address,
			long offset,
			long capacity
	) {
		super(capacity);

		this.address	= address;
		this.offset		= offset;
	}

	public AddressDataView(long address, long capacity) {
		super(capacity);

		this.address	= address;
		this.offset		= 0L;
	}

	@Override
	public IDataRange flush() {
		return new DataRange(offset, limit);
	}

	@Override
	public IDataRange flush(IDataRange range) {
		return new DataRange(
				this.offset +	range.getOffset(),
								range.getLength()
		);
	}

	@Override
	public AddressDataView putByte(long position, byte value) {
		if (limit - position < 1) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memPutByte(address + offset + position, value);

		return this;
	}

	@Override
	public AddressDataView putShort(long position, short value) {
		if (limit - position < 2) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memPutShort(address + offset + position, value);

		return this;
	}

	@Override
	public AddressDataView putInt(long position, int value) {
		if (limit - position < 4) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memPutInt(address + offset + position, value);

		return this;
	}

	@Override
	public AddressDataView putLong(long position, long value) {
		if (limit - position < 8) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memPutLong(address + offset + position, value);

		return this;
	}

	@Override
	public AddressDataView putFloat(long position, float value) {
		if (limit - position < 4) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memPutFloat(address + offset + position, value);

		return this;
	}

	@Override
	public AddressDataView putDouble(long position, double value) {
		if (limit - position < 8) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memPutDouble(address + offset + position, value);

		return this;
	}

	@Override
	public AddressDataView putBytes(
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

		if (limit - position < length) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.BYTE_ARRAY_BASE_OFFSET + offset,
				null,
				address + this.offset + position,
				length
		);

		return this;
	}

	@Override
	public AddressDataView putShorts(
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

		if (limit - position < length * Short.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.SHORT_ARRAY_BASE_OFFSET + offset * Short.BYTES,
				null,
				address + this.offset + position,
				length * Short.BYTES
		);

		return this;
	}

	@Override
	public AddressDataView putInts(
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

		if (limit - position < length * Integer.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.SHORT_ARRAY_BASE_OFFSET + offset * Integer.BYTES,
				null,
				address + this.offset + position,
				length * Integer.BYTES
		);

		return this;
	}

	@Override
	public AddressDataView putLongs(
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

		if (limit - position < length * Long.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.SHORT_ARRAY_BASE_OFFSET + offset * Long.BYTES,
				null,
				address + this.offset + position,
				length * Long.BYTES
		);

		return this;
	}

	@Override
	public AddressDataView putFloats(
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

		if (limit - position < length * Float.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.SHORT_ARRAY_BASE_OFFSET + offset * Float.BYTES,
				null,
				address + this.offset + position,
				length * Float.BYTES
		);

		return this;
	}

	@Override
	public AddressDataView putDoubles(
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

		if (limit - position < length * Double.BYTES) {
			throw new BufferOverflowException();
		}

		UnsafeAccess.UNSAFE.copyMemory(
				value,
				HeapDataView.SHORT_ARRAY_BASE_OFFSET + offset * Double.BYTES,
				null,
				address + this.offset + position,
				length * Double.BYTES
		);

		return this;
	}

	@Override
	public AddressDataView putBuffer(
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

		if (!value.isOffHeap()) {
			throw new IllegalArgumentException("Value is not an off-heap data view.");
		}

		if (offset + length > value.limit()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of buffer limit.");
		}

		if (limit - position < length) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memCopy(
				value.address()			+ offset,
				address + this.offset	+ position,
				length
		);

		return this;
	}

	@Override
	public AddressDataView putBuffer(
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

		if (limit - position < length) {
			throw new BufferOverflowException();
		}

		MemoryUtil.memCopy(
				valueAddress			+ offset,
				address + this.offset	+ position,
				length
		);

		return this;
	}

	@Override
	public byte getByte(long position) {
		if (limit - position < 1) {
			throw new BufferOverflowException();
		}

		return MemoryUtil.memGetByte(address + offset + position);
	}

	@Override
	public short getShort(long position) {
		if (limit - position < 2) {
			throw new BufferOverflowException();
		}

		return MemoryUtil.memGetShort(address + offset + position);
	}

	@Override
	public int getInt(long position) {
		if (limit - position < 4) {
			throw new BufferOverflowException();
		}

		return MemoryUtil.memGetInt(address + offset + position);
	}

	@Override
	public long getLong(long position) {
		if (limit - position < 8) {
			throw new BufferOverflowException();
		}

		return MemoryUtil.memGetLong(address + offset + position);
	}

	@Override
	public float getFloat(long position) {
		if (limit - position < 4) {
			throw new BufferOverflowException();
		}

		return MemoryUtil.memGetFloat(address + offset + position);
	}

	@Override
	public double getDouble(long position) {
		if (limit - position < 8) {
			throw new BufferOverflowException();
		}

		return MemoryUtil.memGetDouble(address + offset + position);
	}

	@Override
	public AddressDataView getBytes(
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

		if (limit - position < length) {
			throw new IllegalArgumentException("Cannot get data from data view that exceeds the range of limit.");
		}

		UnsafeAccess.UNSAFE.copyMemory(
				null,
				address + this.offset + position,
				value,
				HeapDataView.BYTE_ARRAY_BASE_OFFSET + offset,
				length
		);

		return this;
	}

	@Override
	public AddressDataView slice() {
		return new AddressDataView(
				address,
				offset + position,
				limit
		);
	}

	@Override
	public AddressDataView slice(IDataRange range) {
		if (range == null) {
			throw new IllegalArgumentException("Range cannot be null.");
		}

		if (range.getOffset() < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (range.getLength() < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (range.getOffset() + range.getLength() > limit) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of limit.");
		}

		return new AddressDataView(
				this.address,
				this.offset +	range.getOffset(),
								range.getLength()
		);
	}

	@Override
	public long address() {
		return address + offset;
	}

	@Override
	public boolean isOffHeap() {
		return true;
	}

	public static IDataView<?> of(long address, long capacity) {
		return new AddressDataView(address, capacity);
	}
}
