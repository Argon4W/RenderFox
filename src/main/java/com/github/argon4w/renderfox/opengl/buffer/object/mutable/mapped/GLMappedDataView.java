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

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.data.view.wrapped.MappingDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.wrapped.IGLBufferDataView;

public class GLMappedDataView extends MappingDataView<GLMappedDataView> implements IGLBufferDataView<GLMappedDataView> {

	protected final	AbstractGLMappedBuffer	buffer;
	protected final	long					offset;

	protected		long					generation;

	public GLMappedDataView(
			AbstractGLMappedBuffer	buffer,
			long					generation,
			long					offset,
			long					length
	) {
		super(length);

		this.buffer		= buffer;
		this.generation	= generation;
		this.offset		= offset;
	}

	public GLMappedDataView(AbstractGLMappedBuffer buffer) {
		super(buffer.getSize());

		this.buffer		= buffer;
		this.generation	= buffer.getGeneration();
		this.offset		= 0;
	}

	GLMappedDataView reserve(long size) {
		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative.");
		}

		if (position + size > limit) {
			throw new IllegalArgumentException("Size + position cannot be greater than the limit.");
		}

		var slice = slice(position, size);

		position += size;

		return slice;
	}

	@Override
	public IDataRange flush() {
		buffer.flush(offset, limit());

		return new DataRange(offset, limit());
	}

	@Override
	public IDataRange flush(long offset, long length) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > limit()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of limit.");
		}

		buffer.flush(this.offset + offset, length);

		return new DataRange(this.offset + offset, length);
	}

	@Override
	public GLMappedDataView slice(long offset, long length) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (offset + length > limit()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of view limit.");
		}

		return new GLMappedDataView(
				this.buffer,
				this.generation,
				this.offset + offset,
				length
		);
	}

	@Override
	public GLMappedDataView slice() {
		return new GLMappedDataView(
				buffer,
				generation,
				offset + position(),
				remaining()
		);
	}

	@Override
	public IDataView<?> getDataView() {
		if (!buffer.isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (buffer.getGeneration() != generation) {
			throw new IllegalStateException("This view of the buffer is outdated.");
		}

		return buffer.getDataView();
	}

	@Override
	protected long mapPosition(long position) {
		if (position < 0) {
			throw new IllegalArgumentException("Position cannot be negative.");
		}

		if (position > limit()) {
			throw new IllegalArgumentException("Position cannot be greater than the value of view limit.");
		}

		return offset + position;
	}

	@Override
	public long address() {
		return super.address() + offset;
	}

	@Override
	public void close() {
		flush();
	}

	public static class Root extends GLMappedDataView {

		public Root(AbstractGLMappedBuffer buffer) {
			super(buffer);
		}

		@Override
		public Root limit(long limit) {
			throw new UnsupportedOperationException("Unsupported Operation.");
		}

		@Override
		public Root mark() {
			throw new UnsupportedOperationException("Unsupported Operation.");
		}

		@Override
		public void close() {
			throw new UnsupportedOperationException("Unsupported Operation.");
		}

		@Override
		public long limit() {
			return capacity;
		}

		void sync() {
			this.generation = buffer.getGeneration();
		}
	}
}
