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

package com.github.argon4w.renderfox.opengl.buffer.object.mutable;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.size.IMutableSizeObject;
import com.github.argon4w.renderfox.data.size.IResizeMethod;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.data.view.IMappedDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.AbstractGLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.AbstractGLMappedBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.GLMappedBufferView;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.GLMappedDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.IGLMappedBufferInternal;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;
import org.lwjgl.system.MemoryUtil;

public class GLMutableMappedBuffer extends AbstractGLBuffer implements IGLMappedBufferInternal, IMutableSizeObject {

	private final	GLBufferContext			bufferContext;
	private final	IResizeMethod			resizeMethod;
	private final	GLBufferType			bufferType;
	private final	GLBufferStorageFlag		storageFlag;
	private final	GLBufferMapAccess		mapAccess;
	private final	GLMappedDataView.Root	mapView;
	private			AbstractGLMappedBuffer	buffer;
	private 		long					bufferSize;

	public GLMutableMappedBuffer(
			GLBufferContext		bufferContext,
			long				bufferDataAddress,
			long				bufferOffset,
			long				bufferLength,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		this.buffer = (AbstractGLMappedBuffer) bufferContext.getBufferCreator().createMappedBuffer(
				bufferDataAddress,
				bufferOffset,
				bufferLength,
				bufferType,
				storageFlag,
				mapAccess
		);

		this.bufferType		= bufferType;
		this.bufferContext	= bufferContext;
		this.resizeMethod	= bufferContext.getResizeMethod();
		this.storageFlag	= storageFlag;
		this.mapAccess		= mapAccess;
		this.bufferSize		= bufferLength;
		this.mapView		= new GLMappedDataView.Root(this);
	}

	@Override
	public IMappedDataView<?> reserve(long size) {
		buffer.open();

		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative.");
		}

		if (isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (getRemaining() < size) {
			resize(getPosition() + size);
		}

		return mapView.slice(size);
	}

	@Override
	public GLBuffer view(IDataRange viewRange) {
		if (isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (viewRange == null) {
			throw new IllegalArgumentException("ViewRange cannot be null.");
		}

		if (viewRange.getOffset() < 0) {
			throw new IllegalArgumentException("ViewOffset cannot be negative.");
		}

		if (viewRange.getLength() < 0) {
			throw new IllegalArgumentException("ViewLength cannot be negative.");
		}

		if (viewRange.getOffset() + viewRange.getLength() > bufferSize) {
			throw new IllegalArgumentException("ViewOffset + viewLength cannot be greater than the value of buffer size.");
		}

		return new GLMappedBufferView(
				this,
				this.mapView.slice			(viewRange),
				new GLMutableRawBufferView	(
						this,
						viewRange.getOffset(),
						viewRange.getLength()
				)
		);
	}

	@Override
	public IGLBufferDataView<?> mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess) {
		open();

		if (this.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!this.isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (!this.mapAccess.allow(mapAccess)) {
			throw new IllegalStateException("The mapAccess contains bits that are not in the pre-defined mapAccess in this buffer.");
		}

		return this.mapView.slice(mapDataRange);
	}

	@Override
	public void doResize(long size, long bytes) {
		if (bytes < 0) {
			return;
		}

		var newBuffer = (AbstractGLMappedBuffer) this.bufferContext.getBufferCreator().createMappedBuffer(
				MemoryUtil.NULL,
				MemoryUtil.NULL,
				size + bytes,
				this.bufferType,
				this.storageFlag,
				this.mapAccess
		);

		this.buffer.disable			();
		this.buffer.copyRangeDataTo	(
				newBuffer,
				this.buffer,
				this.buffer
		);

		this.buffer.delete();
		this.buffer = newBuffer;
	}

	@Override
	public void clear() {
		this.buffer	.clear	();
		this.mapView.clear	();
		this.mapView.sync	();
	}

	@Override
	public IDataRange flush(IDataRange range) {
		return buffer.flush(range);
	}

	@Override
	public void open() {
		buffer.open();
	}

	@Override
	public void close() {
		buffer.close();
	}

	@Override
	public IResizeMethod getResizeMethod() {
		return resizeMethod;
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		return buffer.getRawBuffer();
	}

	@Override
	public void setSize(long size) {
		bufferSize = size;
	}

	@Override
	public long getSize() {
		return bufferSize;
	}

	@Override
	public long getRemaining() {
		return buffer.getRemaining();
	}

	@Override
	public long getPosition() {
		return buffer.getPosition();
	}

	@Override
	public int getGeneration() {
		return buffer.getGeneration();
	}

	@Override
	public IDataView<?> getView() {
		return buffer.getView();
	}

	@Override
	public void onResize(long size, long bytes) {

	}

	@Override
	public void beforeResize() {

	}

	@Override
	public void afterResize() {

	}
}
