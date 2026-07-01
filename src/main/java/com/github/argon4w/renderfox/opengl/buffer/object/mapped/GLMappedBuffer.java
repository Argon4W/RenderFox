package com.github.argon4w.renderfox.opengl.buffer.object.mapped;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.object.AbstractGLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.impl.IGLMappedBufferImpl;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;

public class GLMappedBuffer extends AbstractGLBuffer implements IGLMappedBufferInternal {

	protected final IGLMappedBufferImpl	mapImpl;
	protected		IDataView<?>		mapImplView;
	protected		int					mapCount;
	protected		int					mapVersion;

	public GLMappedBuffer(IGLMappedBufferImpl mapImpl) {
		this.mapImpl		= mapImpl;
		this.mapImplView	= null;
		this.mapCount		= 0;
		this.mapVersion		= 0;
	}

	@Override
	public GLMappedDataView data() {
		return new GLMappedDataView(
				this,
				getOffset(),
				getLength()
		);
	}

	@Override
	public GLMappedDataView data(IDataRange dataRange) {
		if (dataRange == null) {
			throw new IllegalArgumentException("Data range cannot be null.");
		}

		if (dataRange.getOffset() < 0) {
			throw new IllegalArgumentException("DataOffset cannot be negative.");
		}

		if (dataRange.getLength() < 0) {
			throw new IllegalArgumentException("DataLength cannot be negative.");
		}

		if (dataRange.getRequired() > getBufferSize()) {
			throw new IllegalArgumentException("DataOffset + DataLength cannot be greater than the value of buffer size.");
		}

		return new GLMappedDataView(
				this,
				dataRange.getOffset(),
				dataRange.getLength()
		);
	}

	@Override
	public GLMappedDataView mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess) {
		if (mapDataRange == null) {
			throw new IllegalArgumentException("MapDataRange cannot be null.");
		}

		if (mapDataRange.getOffset() < 0) {
			throw new IllegalArgumentException("MapOffset cannot be negative.");
		}

		if (mapDataRange.getLength() < 0) {
			throw new IllegalArgumentException("MapLength cannot be negative.");
		}

		if (mapDataRange.getRequired() > getBufferSize()) {
			throw new IllegalArgumentException("MapOffset + MapLength cannot be greater than the value of buffer size.");
		}

		if (!getAccessFlag().allow(mapAccess)) {
			throw new IllegalStateException("The mapAccess contains bits that are not in the pre-defined mapAccess in this buffer.");
		}

		return new GLMappedDataView(
				this,
				mapDataRange.getOffset(),
				mapDataRange.getLength()
		);
	}

	@Override
	public GLMappedBufferView view(IDataRange viewRange) {
		if (viewRange == null) {
			throw new IllegalArgumentException("ViewRange cannot be null.");
		}

		if (viewRange.getOffset() < 0) {
			throw new IllegalArgumentException("ViewOffset cannot be negative.");
		}

		if (viewRange.getLength() < 0) {
			throw new IllegalArgumentException("ViewLength cannot be negative.");
		}

		if (viewRange.getOffset() + viewRange.getLength() > getBufferSize()) {
			throw new IllegalArgumentException("ViewOffset + viewLength cannot be greater than the value of buffer size.");
		}

		return new GLMappedBufferView(
				this,
				this.getRawBuffer().view(
						viewRange.getOffset(),
						viewRange.getLength()
				)
		);
	}

	@Override
	public IDataView<?> open() {
		this.mapCount ++;

		if (this.mapImplView == null) {
			this.mapImplView = this.mapImpl.open();
		}

		return this.mapImplView;
	}

	@Override
	public void close() {
		this.mapCount --;

		if (this.mapCount == 0) {
			this.mapImpl.close();
			this.mapImplView = null;
		}
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		return this.mapImpl.getRawBuffer();
	}
}
