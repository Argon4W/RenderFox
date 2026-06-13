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

import com.github.argon4w.renderfox.data.view.IDataView;

public class VirtualPageDataView extends MappingDataView<VirtualPageDataView> {

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

	@Override
	protected long mapPosition(long position) {
		return getPageAddress((position + offset) / pageSize) + (position + offset) % pageSize;
	}

	@Override
	public IDataView<?> getDataView() {
		return dataView;
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

			super.putBuffer(
					position,
					value,
					offset,
					batchSize
			);

			offset		+= batchSize;
			position	+= batchSize;
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

			super.putBuffer(
					position,
					valueAddress,
					offset,
					batchSize
			);

			offset		+= batchSize;
			position	+= batchSize;
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

			super.putBytes(
					position,
					value,
					offset,
					batchSize
			);

			offset		+= batchSize;
			position	+= batchSize;
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
		while (length > 0) {
			var batchSize = Math.min(length * Short.BYTES, getBatchSize(position)) / Short.BYTES;

			super.putShorts(
					position,
					value,
					offset,
					batchSize
			);

			position	+= batchSize * Short.BYTES;
			offset		+= batchSize;
			length		-= batchSize;
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
		while (length > 0) {
			var batchSize = Math.min(length * Integer.BYTES, getBatchSize(position)) / Integer.BYTES;

			super.putInts(
					position,
					value,
					offset,
					batchSize
			);

			position	+= batchSize * Integer.BYTES;
			offset		+= batchSize;
			length		-= batchSize;
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
		while (length > 0) {
			var batchSize = Math.min(length * Long.BYTES, getBatchSize(position)) / Long.BYTES;

			super.putLongs(
					position,
					value,
					offset,
					batchSize
			);

			position	+= batchSize * Long.BYTES;
			offset		+= batchSize;
			length		-= batchSize;
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
		while (length > 0) {
			var batchSize = Math.min(length * Float.BYTES, getBatchSize(position)) / Float.BYTES;

			super.putFloats(
					position,
					value,
					offset,
					batchSize
			);

			position	+= batchSize * Float.BYTES;
			offset		+= batchSize;
			length		-= batchSize;
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
		while (length > 0) {
			var batchSize = Math.min(length * Double.BYTES, getBatchSize(position)) / Double.BYTES;

			super.putDoubles(
					position,
					value,
					offset,
					batchSize
			);

			position	+= batchSize * Double.BYTES;
			offset		+= batchSize;
			length		-= batchSize;
		}

		return this;
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

			super.getBytes(
					position,
					value,
					offset,
					batchSize
			);

			offset		+= batchSize;
			position	+= batchSize;
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
	public VirtualPageDataView slice(long offset, long length) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		var end = offset + length;

		if (end > limit) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of limit.");
		}

		var sliceOffset		= (offset + this.offset)	% pageSize;
		var pageIndexStart	= (offset + this.offset)	/ pageSize;
		var pageIndexEnd	= end						/ pageSize;

		return new VirtualPageDataView(
				dataView,
				pageAddresses,
				this.pageOffset	+ pageIndexStart,
				pageIndexEnd	- pageIndexStart,
				this.pageSize,
				this.offset + sliceOffset,
				length
		);
	}

	@Override
	public long address() {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}
}
