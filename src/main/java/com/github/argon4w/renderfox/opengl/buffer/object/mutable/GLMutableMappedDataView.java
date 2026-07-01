package com.github.argon4w.renderfox.opengl.buffer.object.mutable;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.data.view.wrapped.OffsetDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;

public class GLMutableMappedDataView extends OffsetDataView<GLMutableMappedDataView> implements IGLBufferDataView<GLMutableMappedDataView> {

	private final GLMutableMappedBuffer	buffer;
	private final long					offset;
	private final long					depth;

	public GLMutableMappedDataView(GLMutableMappedBuffer buffer) {
		super(buffer.getBufferSize());

		this.buffer	= buffer;
		this.offset	= 0;
		this.depth	= 0;
	}

	public GLMutableMappedDataView(
			GLMutableMappedBuffer	buffer,
			long					offset,
			long					length,
			long					depth
	) {
		super(length);

		this.buffer	= buffer;
		this.offset	= offset;
		this.depth	= depth;
	}

	@Override
	public GLMutableMappedDataView slice() {
		return new GLMutableMappedDataView(
				this.buffer,
				this.offset +	this.position	(),
								this.remaining	(),
				this.depth + 1
		);
	}

	@Override
	public GLMutableMappedDataView slice(IDataRange range) {
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
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of view limit.");
		}

		return new GLMutableMappedDataView(
				this.buffer,
				this.offset +	range.getOffset(),
								range.getLength(),
				this.depth + 1
		);
	}

	@Override
	public void close() {
		if (this.depth <= 1) {
			this.buffer.close();
		}
	}

	@Override
	public IDataView<?> getDataView() {
		return buffer.mapImplView;
	}

	@Override
	public long getOffset() {
		return offset;
	}
}
