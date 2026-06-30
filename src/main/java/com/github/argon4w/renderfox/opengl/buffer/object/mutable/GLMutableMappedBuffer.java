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
import com.github.argon4w.renderfox.data.size.IMutableSizeObjectInternal;
import com.github.argon4w.renderfox.data.size.IResizeMethod;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.AbstractGLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.*;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;
import org.lwjgl.system.MemoryUtil;

public class GLMutableMappedBuffer extends AbstractGLBuffer implements IGLMutableMappedBuffer, IGLMappedBufferInternal, IMutableSizeObjectInternal {

	private final	GLBufferContext				bufferContext;
	private final	IResizeMethod				resizeMethod;
	private final	GLMappedBufferCreateInfo	createInfo;
	private final	GLMappedDataView.Root		mapView;
	private			AbstractGLMappedBuffer		buffer;
	private			boolean						bufferOpened;
	private			int							bufferOpenCount;
	private			int							generation;
	private 		long						bufferSize;

	public GLMutableMappedBuffer(
			GLBufferContext		bufferContext,
			long				bufferDataAddress,
			long				bufferOffset,
			long				bufferLength,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		this.createInfo = new GLMappedBufferCreateInfo(
				-1,
				bufferType,
				storageFlag	.copy(),
				mapAccess	.copy()
		);

		this.buffer = (AbstractGLMappedBuffer) bufferContext.getBufferCreator().createMappedBuffer(
				this.createInfo,
				bufferDataAddress,
				bufferOffset,
				bufferLength
		);

		this.bufferContext		= bufferContext;
		this.resizeMethod		= bufferContext.getResizeMethod();
		this.mapView			= new GLMappedDataView.Root(this);

		this.bufferOpened		= false;
		this.bufferOpenCount	= 0;
		this.generation			= 0;
		this.bufferSize			= bufferLength;
	}

	@Override
	public GLMappedDataView reserve(long size) {
		open();

		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative.");
		}

		if (isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (mapView.remaining() < size) {
			resize(mapView.position() + size);
		}

		return mapView.slice(size);
	}

	@Override
	public GLMappedBufferView view(IDataRange viewRange) {
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

		if (viewRange.getRequired() > bufferSize) {
			resize(viewRange.getRequired());
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
	public GLMappedDataView mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess) {
		open();

		if (this.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!this.isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (!this.createInfo.getMapAccess().allow(mapAccess)) {
			throw new IllegalStateException("The mapAccess contains bits that are not in the pre-defined mapAccess in this buffer.");
		}

		if (this.bufferSize < mapDataRange.getRequired()) {
			resize(mapDataRange.getRequired());
		}

		return this.mapView.slice(mapDataRange);
	}

	@Override
	public void doResize(long size, long bytes) {
		if (bytes < 0) {
			return;
		}

		if (this.bufferOpened) {
			this.buffer.close();
		}

		var newBuffer = (AbstractGLMappedBuffer) this.bufferContext.getBufferCreator().createMappedBuffer(
				this.createInfo,
				MemoryUtil.NULL,
				MemoryUtil.NULL,
				size + bytes
		);

		this.buffer.copyRangeDataTo(
				newBuffer,
				this.buffer,
				this.buffer
		);

		this.buffer.delete();
		this.buffer = newBuffer;

		if (this.bufferOpened) {
			this.buffer.open();
		}
	}

	@Override
	public void clear() {
		this.generation		++;
		this.mapView.clear	();
		this.mapView.sync	();
	}

	@Override
	public void open() {
		this.bufferOpenCount ++;

		if (!this.bufferOpened) {
			this.bufferOpened = true;
			this.buffer.open();
		}
	}

	@Override
	public void close() {
		this.bufferOpenCount --;

		if (this.bufferOpenCount == 0) {
			this.bufferOpened = false;
			this.buffer.close();
		}
	}

	@Override
	public void delete() {
		if (this.bufferOpened) {
			this.bufferOpened = false;
			this.buffer.close();
		}

		super.delete();
	}

	@Override
	public IDataRange flush(IDataRange range) {
		return buffer.flush(range);
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		return buffer.getRawBuffer();
	}

	@Override
	public IGLBufferDataView<?> getView() {
		return buffer.getView();
	}

	@Override
	public IResizeMethod getResizeMethod() {
		return resizeMethod;
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
	public int getGeneration() {
		return generation;
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
