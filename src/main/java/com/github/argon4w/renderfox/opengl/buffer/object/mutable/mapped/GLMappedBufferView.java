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

package com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped;

import com.github.argon4w.renderfox.buffer.IMappedBuffer;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IMappedDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBufferView;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;

public class GLMappedBufferView extends GLBufferView implements IMappedBuffer {

	private final AbstractGLMappedBuffer	buffer;
	private final IGLBufferDataView<?>		dataView;
	private final long						offset;
	private final long						length;

	public GLMappedBufferView(
			AbstractGLMappedBuffer	buffer,
			IGLBufferDataView<?>	dataView,
			long					offset,
			long					length
	) {
		this.buffer		= buffer;
		this.dataView	= dataView;
		this.offset		= offset;
		this.length		= length;
	}

	@Override
	public IMappedDataView<?> reserve(long size) {
		if (buffer.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative.");
		}

		if (size > length) {
			throw new IllegalArgumentException("Size cannot be greater than length of the buffer view.");
		}

		return dataView.slice(size);
	}

	@Override
	public GLMappedBufferView view(IDataRange viewRange) {
		if (buffer.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (viewRange == null) {
			throw new IllegalArgumentException("View range cannot be null.");
		}

		if (viewRange.getOffset() < 0) {
			throw new IllegalArgumentException("ViewOffset cannot be negative.");
		}

		if (viewRange.getLength() < 0) {
			throw new IllegalArgumentException("ViewLength cannot be negative.");
		}

		if (viewRange.getOffset() + viewRange.getLength() > this.length) {
			throw new IllegalArgumentException("ViewLength + viewLength cannot be greater than the value of the length of the current view.");
		}

		return new GLMappedBufferView(
				this.buffer,
				this.dataView.slice(viewRange),
				this.offset +	viewRange.getOffset(),
								viewRange.getLength()
		);
	}

	@Override
	public IGLBuffer getBuffer() {
		return buffer;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public long getLength() {
		return length;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Cannot clear a view of mapped buffer.");
	}

	@Override
	public void delete() {
		throw new UnsupportedOperationException("Cannot delete a view of buffer.");
	}

	@Override
	public IDataRange getMapRange() {
		throw new UnsupportedOperationException("Cannot get the map range from a view of buffer.");
	}

	@Override
	public long getMapOffset() {
		throw new UnsupportedOperationException("Cannot get the map offset from a view of buffer.");
	}

	@Override
	public long getMapLength() {
		throw new UnsupportedOperationException("Cannot get the map length from a view of buffer.");
	}
}
