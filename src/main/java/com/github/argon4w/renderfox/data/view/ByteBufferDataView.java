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
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class ByteBufferDataView extends AbstractDataView<ByteBufferDataView> {

	private final ByteBuffer	byteBuffer;
	private final long			offset;

	public ByteBufferDataView(
			ByteBuffer	byteBuffer,
			long		capacity,
			long		offset

	) {
		super(capacity);

		this.byteBuffer	= byteBuffer;
		this.offset		= offset;
	}

	public ByteBufferDataView(ByteBuffer byteBuffer) {
		this(
				byteBuffer,
				byteBuffer.limit	(),
				byteBuffer.position	()
		);
	}

	@Override
	public IDataRange flush() {
		return new DataRange(offset, limit);
	}

	@Override
	public IDataRange flush(long offset, long length) {
		return new DataRange(this.offset + offset, length);
	}

	@Override
	public ByteBufferDataView putByte(long position, byte value) {
		byteBuffer.put((int) position + (int) offset, value);
		return this;
	}

	@Override
	public ByteBufferDataView putShort(long position, short value) {
		byteBuffer.putShort((int) position + (int) offset, value);
		return this;
	}

	@Override
	public ByteBufferDataView putInt(long position, int value) {
		byteBuffer.putInt((int) position + (int) offset, value);
		return this;
	}

	@Override
	public ByteBufferDataView putLong(long position, long value) {
		byteBuffer.putLong((int) position + (int) offset, value);
		return this;
	}

	@Override
	public ByteBufferDataView putFloat(long position, float value) {
		byteBuffer.putFloat((int) position + (int) offset, value);
		return this;
	}

	@Override
	public ByteBufferDataView putDouble(long position, double value) {
		byteBuffer.putDouble((int) position + (int) offset, value);
		return this;
	}

	@Override
	public ByteBufferDataView putBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		byteBuffer.put(
				(int) position + (int) this.offset,
				value,
				(int) offset,
				(int) length
		);
		return this;
	}

	@Override
	public ByteBufferDataView putShorts(
			long	position,
			short[]	value,
			long	offset,
			long	length
	) {
		for (var index = 0; index < length; index ++) {
			byteBuffer.putShort((int) position + (int) this.offset + index * Short.BYTES, value[(int) offset + index]);
		}
		return this;
	}

	@Override
	public ByteBufferDataView putInts(
			long	position,
			int[]	value,
			long	offset,
			long	length
	) {
		for (var index = 0; index < length; index ++) {
			byteBuffer.putInt((int) position + (int) this.offset + index * Integer.BYTES, value[(int) offset + index]);
		}
		return this;
	}

	@Override
	public ByteBufferDataView putLongs(
			long	position,
			long[]	value,
			long	offset,
			long	length
	) {
		for (var index = 0; index < length; index ++) {
			byteBuffer.putLong((int) position + (int) this.offset + index * Long.BYTES, value[(int) offset + index]);
		}
		return this;
	}

	@Override
	public ByteBufferDataView putFloats(
			long	position,
			float[]	value,
			long	offset,
			long	length
	) {
		// Sad, slow.
		for (var index = 0; index < length; index ++) {
			byteBuffer.putFloat((int) position + (int) this.offset + index * Float.BYTES, value[(int) offset + index]);
		}
		return this;
	}

	@Override
	public ByteBufferDataView putDoubles(
			long		position,
			double[]	value,
			long		offset,
			long		length
	) {
		// Sad, slow.
		for (var index = 0; index < length; index ++) {
			byteBuffer.putDouble((int) position + (int) this.offset + index * Double.BYTES, value[(int) offset + index]);
		}
		return this;
	}

	@Override
	public ByteBufferDataView putBuffer(
			long			position,
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		if (!value.isOffHeap()) {
			throw new IllegalArgumentException("Value is not an off-heap data view.");
		}

		byteBuffer.put(
				(int) position + (int) this.offset,
				MemoryUtil.memByteBuffer(value.address(), (int) value.capacity()),
				(int) offset,
				(int) length
		);
		return this;
	}

	@Override
	public ByteBufferDataView putBuffer(
			long position,
			long valueAddress,
			long offset,
			long length
	) {
		byteBuffer.put(
				(int) position + (int) this.offset,
				MemoryUtil.memByteBuffer(valueAddress, (int) offset + (int) length),
				(int) offset,
				(int) length
		);
		return this;
	}

	@Override
	public byte getByte(long position) {
		return byteBuffer.get((int) position + (int) offset);
	}

	@Override
	public short getShort(long position) {
		return byteBuffer.getShort((int) position + (int) offset);
	}

	@Override
	public int getInt(long position) {
		return byteBuffer.getInt((int) position + (int) offset);
	}

	@Override
	public long getLong(long position) {
		return byteBuffer.getLong((int) position + (int) offset);
	}

	@Override
	public float getFloat(long position) {
		return byteBuffer.getFloat((int) position + (int) offset);
	}

	@Override
	public double getDouble(long position) {
		return byteBuffer.getDouble((int) position + (int) offset);
	}

	@Override
	public ByteBufferDataView getBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		byteBuffer.get(
				(int) position + (int) this.offset,
				value,
				(int) offset,
				(int) length
		);

		return this;
	}

	@Override
	public ByteBufferDataView slice() {
		return new ByteBufferDataView(
				byteBuffer,
				remaining(),
				position + offset
		);
	}

	@Override
	public ByteBufferDataView slice(long offset, long length) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length < limit) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of limit.");
		}

		return new ByteBufferDataView(
				byteBuffer,
				length,
				offset + this.offset
		);
	}

	@Override
	public long address() {
		return MemoryUtil.memAddress(byteBuffer) + offset;
	}

	@Override
	public boolean isOffHeap() {
		return byteBuffer.isDirect();
	}
}
