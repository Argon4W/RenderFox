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
import com.github.argon4w.renderfox.data.view.AbstractDataView;
import com.github.argon4w.renderfox.data.view.IDataView;

public class VirtualPageDataView extends AbstractDataView<VirtualPageDataView> {

	private final IDataView<?>	dataView;
	private final long[]		pageAddresses;
	private final long			pageOffset;
	private final long			pageLength;
	private final long			pageSize;
	private final long			offset;

	public VirtualPageDataView(
			IDataView<?>	dataView,
			long[]			pageAddresses,
			long			pageOffset,
			long			pageLength,
			long			pageSize,
			long			offset,
			long			capacity
	) {
		super(capacity);
		this.dataView		= dataView;
		this.pageAddresses	= pageAddresses;
		this.pageOffset		= pageOffset;
		this.pageLength		= pageLength;
		this.pageSize		= pageSize;
		this.offset			= offset;
	}

	private long getPageAddress(long pageIndex) {
		if (pageIndex < 0) {
			throw new IllegalArgumentException("Page index cannot be negative.");
		}

		if (pageIndex >= pageLength) {
			throw new IllegalArgumentException("Page index cannot be greater than the value of pages length.");
		}

		return pageAddresses[(int) pageOffset + (int) pageIndex];
	}

	private long getBatchSize(long position) {
		return pageSize - (position + this.offset) % pageSize;
	}

	protected long mapPosition(long position) {
		return getPageAddress((position + offset) / pageSize) + (position + offset) % pageSize;
	}

	@Override
	public VirtualPageDataView putByte(long position, byte value) {
		dataView.putByte(mapPosition(position), value);
		return this;
	}

	@Override
	public VirtualPageDataView putShort(long position, short value) {
		var byte0 = (byte) (value >>> 0 & 0xFF);
		var byte1 = (byte) (value >>> 8 & 0xFF);

		dataView.putByte(mapPosition(position + 0L), byte0);
		dataView.putByte(mapPosition(position + 1L), byte1);

		return this;
	}

	@Override
	public VirtualPageDataView putInt(long position, int value) {
		var byte0 = (byte) (value >>> 0		& 0xFF);
		var byte1 = (byte) (value >>> 8		& 0xFF);
		var byte2 = (byte) (value >>> 16	& 0xFF);
		var byte3 = (byte) (value >>> 24	& 0xFF);

		dataView.putByte(mapPosition(position + 0L), byte0);
		dataView.putByte(mapPosition(position + 1L), byte1);
		dataView.putByte(mapPosition(position + 2L), byte2);
		dataView.putByte(mapPosition(position + 3L), byte3);

		return this;
	}

	@Override
	public VirtualPageDataView putLong(long position, long value) {
		var byte0 = (byte) (value >>> 0		& 0xFF);
		var byte1 = (byte) (value >>> 8		& 0xFF);
		var byte2 = (byte) (value >>> 16	& 0xFF);
		var byte3 = (byte) (value >>> 24	& 0xFF);
		var byte4 = (byte) (value >>> 32	& 0xFF);
		var byte5 = (byte) (value >>> 40	& 0xFF);
		var byte6 = (byte) (value >>> 48	& 0xFF);
		var byte7 = (byte) (value >>> 56	& 0xFF);

		dataView.putByte(mapPosition(position + 0L), byte0);
		dataView.putByte(mapPosition(position + 1L), byte1);
		dataView.putByte(mapPosition(position + 2L), byte2);
		dataView.putByte(mapPosition(position + 3L), byte3);
		dataView.putByte(mapPosition(position + 4L), byte4);
		dataView.putByte(mapPosition(position + 5L), byte5);
		dataView.putByte(mapPosition(position + 6L), byte6);
		dataView.putByte(mapPosition(position + 7L), byte7);

		return this;
	}

	@Override
	public VirtualPageDataView putFloat(long position, float value) {
		var intBits = Float.floatToRawIntBits(value);

		var byte0 = (byte) (intBits >>> 0	& 0xFF);
		var byte1 = (byte) (intBits >>> 8	& 0xFF);
		var byte2 = (byte) (intBits >>> 16	& 0xFF);
		var byte3 = (byte) (intBits >>> 24	& 0xFF);

		dataView.putByte(mapPosition(position + 0L), byte0);
		dataView.putByte(mapPosition(position + 1L), byte1);
		dataView.putByte(mapPosition(position + 2L), byte2);
		dataView.putByte(mapPosition(position + 3L), byte3);

		return this;
	}

	@Override
	public VirtualPageDataView putDouble(long position, double value) {
		var intBits = Double.doubleToRawLongBits(value);

		var byte0 = (byte) (intBits >>> 0	& 0xFF);
		var byte1 = (byte) (intBits >>> 8	& 0xFF);
		var byte2 = (byte) (intBits >>> 16	& 0xFF);
		var byte3 = (byte) (intBits >>> 24	& 0xFF);
		var byte4 = (byte) (intBits >>> 32	& 0xFF);
		var byte5 = (byte) (intBits >>> 40	& 0xFF);
		var byte6 = (byte) (intBits >>> 48	& 0xFF);
		var byte7 = (byte) (intBits >>> 56	& 0xFF);

		dataView.putByte(mapPosition(position + 0L), byte0);
		dataView.putByte(mapPosition(position + 1L), byte1);
		dataView.putByte(mapPosition(position + 2L), byte2);
		dataView.putByte(mapPosition(position + 3L), byte3);
		dataView.putByte(mapPosition(position + 4L), byte4);
		dataView.putByte(mapPosition(position + 5L), byte5);
		dataView.putByte(mapPosition(position + 6L), byte6);
		dataView.putByte(mapPosition(position + 7L), byte7);

		return this;
	}

	@Override
	public VirtualPageDataView putBuffer(
			long			position,
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		while (length > 0) {
			var batchSize = Math.min(length, getBatchSize(position));

			dataView.putBuffer(
					mapPosition(position),
					value,
					offset,
					batchSize
			);

			position	+= batchSize;
			offset		+= batchSize;
			length		-= batchSize;
		}

		return this;
	}

	@Override
	public VirtualPageDataView putBuffer(
			long position,
			long valueAddress,
			long offset,
			long length
	) {
		while (length > 0) {
			var batchSize = Math.min(length, getBatchSize(position));

			dataView.putBuffer(
					mapPosition(position),
					valueAddress,
					offset,
					batchSize
			);

			position	+= batchSize;
			offset		+= batchSize;
			length		-= batchSize;
		}

		return this;
	}

	@Override
	public VirtualPageDataView putBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		while (length > 0) {
			var batchSize = Math.min(length, getBatchSize(position));

			dataView.putBytes(
					mapPosition(position),
					value,
					offset,
					batchSize
			);

			position	+= batchSize;
			offset		+= batchSize;
			length		-= batchSize;
		}

		return this;
	}

	@Override
	public VirtualPageDataView putShorts(
			long	position,
			short[]	value,
			long	offset,
			long	length
	) {
		if (length > 0) {
			try (var view = StackDataView.ofShorts(
					value,
					(int) offset,
					(int) length
			)) {
				putBuffer(view);
			}
		}

		return this;
	}

	@Override
	public VirtualPageDataView putInts(
			long	position,
			int[]	value,
			long	offset,
			long	length
	) {
		if (length > 0) {
			try (var view = StackDataView.ofInts(
					value,
					(int) offset,
					(int) length
			)) {
				putBuffer(view);
			}
		}

		return this;
	}

	@Override
	public VirtualPageDataView putLongs(
			long	position,
			long[]	value,
			long	offset,
			long	length
	) {
		if (length > 0) {
			try (var view = StackDataView.ofLongs(
					value,
					(int) offset,
					(int) length
			)) {
				putBuffer(view);
			}
		}

		return this;
	}

	@Override
	public VirtualPageDataView putFloats(
			long	position,
			float[]	value,
			long	offset,
			long	length
	) {
		if (length > 0) {
			try (var view = StackDataView.ofFloats(
					value,
					(int) offset,
					(int) length
			)) {
				putBuffer(view);
			}
		}

		return this;
	}

	@Override
	public VirtualPageDataView putDoubles(
			long		position,
			double[]	value,
			long		offset,
			long		length
	) {
		if (length > 0) {
			try (var view = StackDataView.ofDoubles(
					value,
					(int) offset,
					(int) length
			)) {
				putBuffer(view);
			}
		}

		return this;
	}

	@Override
	public byte getByte(long position) {
		return dataView.getByte(mapPosition(position));
	}

	@Override
	public short getShort(long position) {
		var byte0 = dataView.getByte(mapPosition(position + 0L));
		var byte1 = dataView.getByte(mapPosition(position + 1L));

		return (short) ((byte1 & 0xFF) << 8
				|		(byte0 & 0xFF) << 0);
	}

	@Override
	public int getInt(long position) {
		var byte0 = dataView.getByte(mapPosition(position + 0L));
		var byte1 = dataView.getByte(mapPosition(position + 1L));
		var byte2 = dataView.getByte(mapPosition(position + 2L));
		var byte3 = dataView.getByte(mapPosition(position + 3L));

		return (	(byte3 & 0xFF) << 24
				|	(byte2 & 0xFF) << 16
				|	(byte1 & 0xFF) << 8
				|	(byte0 & 0xFF) << 0);
	}

	@Override
	public long getLong(long position) {
		var byte0 = dataView.getByte(mapPosition(position + 0L));
		var byte1 = dataView.getByte(mapPosition(position + 1L));
		var byte2 = dataView.getByte(mapPosition(position + 2L));
		var byte3 = dataView.getByte(mapPosition(position + 3L));
		var byte4 = dataView.getByte(mapPosition(position + 4L));
		var byte5 = dataView.getByte(mapPosition(position + 5L));
		var byte6 = dataView.getByte(mapPosition(position + 6L));
		var byte7 = dataView.getByte(mapPosition(position + 7L));

		return (	(byte7 & 0xFFL) << 56
				|	(byte6 & 0xFFL) << 48
				|	(byte5 & 0xFFL) << 40
				|	(byte4 & 0xFFL) << 32
				|	(byte3 & 0xFFL) << 24
				|	(byte2 & 0xFFL) << 16
				|	(byte1 & 0xFFL) << 8
				|	(byte0 & 0xFFL) << 0);
	}

	@Override
	public float getFloat(long position) {
		var byte0 = dataView.getByte(mapPosition(position + 0L));
		var byte1 = dataView.getByte(mapPosition(position + 1L));
		var byte2 = dataView.getByte(mapPosition(position + 2L));
		var byte3 = dataView.getByte(mapPosition(position + 3L));

		return Float.intBitsToFloat((byte3 & 0xFF) << 24
				|					(byte2 & 0xFF) << 16
				|					(byte1 & 0xFF) << 8
				|					(byte0 & 0xFF) << 0);
	}

	@Override
	public double getDouble(long position) {
		var byte0 = dataView.getByte(mapPosition(position + 0L));
		var byte1 = dataView.getByte(mapPosition(position + 1L));
		var byte2 = dataView.getByte(mapPosition(position + 2L));
		var byte3 = dataView.getByte(mapPosition(position + 3L));
		var byte4 = dataView.getByte(mapPosition(position + 4L));
		var byte5 = dataView.getByte(mapPosition(position + 5L));
		var byte6 = dataView.getByte(mapPosition(position + 6L));
		var byte7 = dataView.getByte(mapPosition(position + 7L));

		return Double.longBitsToDouble(	(byte7 & 0xFFL) << 56
				|						(byte6 & 0xFFL) << 48
				|						(byte5 & 0xFFL) << 40
				|						(byte4 & 0xFFL) << 32
				|						(byte3 & 0xFFL) << 24
				|						(byte2 & 0xFFL) << 16
				|						(byte1 & 0xFFL) << 8
				|						(byte0 & 0xFFL) << 0);
	}

	@Override
	public VirtualPageDataView getBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		while (length > 0) {
			var batchSize = Math.min(length, getBatchSize(position));

			dataView.getBytes(
					position,
					value,
					offset,
					batchSize
			);

			position	+= batchSize;
			offset		+= batchSize;
			length		-= batchSize;
		}

		return this;
	}

	@Override
	public VirtualPageDataView slice() {
		var sliceOffset		= (position + offset)	% pageSize;
		var pageIndexStart	= (position + offset)	/ pageSize;
		var pageIndexEnd	= limit					/ pageSize;

		return new VirtualPageDataView(
				dataView,
				pageAddresses,
				this.pageOffset + pageIndexStart,
				pageIndexEnd	- pageIndexStart,
				this.pageSize,
				this.offset + sliceOffset,
				remaining()
		);
	}

	@Override
	public VirtualPageDataView slice(IDataRange range) {
		if (range == null) {
			throw new IllegalArgumentException("Range cannot be null.");
		}

		if (range.getOffset() < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (range.getLength() < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		var end = range.getOffset() + range.getLength();

		if (end > limit) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of limit.");
		}

		var sliceOffset		= (range.getOffset() + this.offset)	% pageSize;
		var pageIndexStart	= (range.getOffset() + this.offset)	/ pageSize;
		var pageIndexEnd	= end								/ pageSize;

		return new VirtualPageDataView(
				dataView,
				pageAddresses,
				this.pageOffset	+ pageIndexStart,
				pageIndexEnd	- pageIndexStart,
				this.pageSize,
				this.offset + sliceOffset,
				range.getLength()
		);
	}

	@Override
	public long address() {
		throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public boolean isOffHeap() {
		return dataView.isOffHeap();
	}
}
