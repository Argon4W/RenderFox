package com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped;

import com.github.argon4w.renderfox.buffer.IMappedBuffer;
import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IMappedDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.wrapped.GLBufferWrapper;
import com.github.argon4w.renderfox.opengl.buffer.object.wrapped.IGLBuffer;

public class GLMappedBufferView extends GLBufferWrapper implements IMappedBuffer {

	private final AbstractGLMappedBuffer	buffer;
	private final long						offset;
	private final long						length;

	public GLMappedBufferView(
			AbstractGLMappedBuffer	buffer,
			long					offset,
			long					length
	) {
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
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

		return buffer.reserve(size);
	}

	@Override
	public IGLBuffer view(IDataRange viewRange) {
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
				this.offset +	viewRange.getOffset(),
								viewRange.getLength()
		);
	}

	@Override
	public IGLBuffer getBuffer() {
		return buffer.view(new DataRange(offset, length));
	}

	@Override
	public IDataRange flush(IDataRange range) {
		throw new UnsupportedOperationException("Cannot flush a view of buffer.");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Cannot clear a view of mapped buffer.");
	}

	@Override
	public void delete() {
		throw new UnsupportedOperationException("Cannot delete a view of buffer.");
	}
}
