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

package com.github.argon4w.renderfox.opengl.buffer.object;

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.data.view.wrapped.OffsetDataView;

public class GLBufferDataView extends OffsetDataView<GLBufferDataView> implements IGLBufferDataView<GLBufferDataView> {

	private final IGLBuffer		buffer;
	private final IDataView<?>	dataView;
	private final long			offset;
	private final long			depth;

	public GLBufferDataView(
			IGLBuffer			buffer,
			IDataView<?>		dataView,
			long				length
	) {
		super(length);

		this.buffer		= buffer;
		this.dataView	= dataView;
		this.offset		= 0L;
		this.depth		= 0L;
	}

	public GLBufferDataView(
			IGLBuffer		buffer,
			IDataView<?>	dataView,
			long			offset,
			long			length,
			long			depth
	) {
		super(length);

		this.buffer		= buffer;
		this.dataView	= dataView;
		this.offset		= offset;
		this.depth		= depth;
	}

	@Override
	public IDataRange flush() {
		buffer.getRawBuffer().flushMappedRange(
				this.offset,
				this.limit
		);

		return new DataRange(
				this.offset,
				this.limit
		);
	}

	@Override
	public IDataRange flush(IDataRange range) {
		if (range == null) {
			throw new IllegalArgumentException("Range cannot be null.");
		}

		if (range.getOffset() < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (range.getLength() < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (range.getRequired() > limit()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of limit.");
		}

		buffer.getRawBuffer().flushMappedRange(
				this.offset +	range.getOffset(),
								range.getLength()
		);

		return new DataRange(
				this.offset +	range.getOffset(),
								range.getLength()
		);
	}

	@Override
	public GLBufferDataView slice() {
		return new GLBufferDataView(
				this.buffer,
				this.dataView,
				this.offset +	position	(),
								remaining	(),
				this.depth + 1
		);
	}

	@Override
	public GLBufferDataView slice(IDataRange range) {
		if (range == null) {
			throw new IllegalArgumentException("Range cannot be null.");
		}

		if (range.getOffset() < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (range.getLength() < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (range.getRequired() > limit()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of mapped length.");
		}

		return new GLBufferDataView(
				this.buffer,
				this.dataView,
				this.offset +	range.getOffset(),
								range.getLength(),
				this.depth + 1
		);
	}

	@Override
	public void close() {
		if (depth == 0) {
			buffer.getRawBuffer().unmap();
		}
	}

	@Override
	public IDataView<?> getDataView() {
		return dataView;
	}

	@Override
	protected long getOffset() {
		return offset;
	}
}
