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
import com.github.argon4w.renderfox.opengl.buffer.object.GLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;
import org.lwjgl.system.MemoryUtil;

public class GLMutableBuffer extends AbstractGLBuffer implements IGLMutableBuffer, IMutableSizeObjectInternal {

	protected final	GLBufferContext	bufferContext;
	protected final	IResizeMethod	resizeMethod;
	protected		IGLRawBuffer	buffer;
	protected 		long			bufferSize;

	public GLMutableBuffer(
			GLBufferContext		bufferContext,
			long				bufferDataAddress,
			long				bufferOffset,
			long				bufferLength,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		this.bufferContext	= bufferContext;
		this.resizeMethod	= bufferContext.getResizeMethod();
		this.buffer			= bufferContext.createRawBuffer(bufferType);
		this.bufferSize		= bufferLength;

		bufferContext.getBufferCreator().setupStorage(
				this.buffer,
				bufferDataAddress,
				bufferOffset,
				bufferLength,
				storageFlag
		);
	}

	@Override
	public GLBuffer view(IDataRange viewRange) {
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
			resize(viewRange);
		}

		return new GLBuffer(
				new GLMutableRawBufferView(
						this,
						viewRange.getOffset(),
						viewRange.getLength()
				)
		);
	}

	@Override
	public IGLBufferDataView<?> mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess) {
		if (mapDataRange == null) {
			throw new IllegalArgumentException("MapDataRange cannot be null.");
		}

		if (mapDataRange.getOffset() < 0) {
			throw new IllegalArgumentException("MapOffset cannot be negative.");
		}

		if (mapDataRange.getLength() < 0) {
			throw new IllegalArgumentException("MapLength cannot be negative.");
		}

		if (mapDataRange.getRequired() > bufferSize) {
			resize(mapDataRange);
		}

		return super.mapRangeData(mapDataRange, mapAccess);
	}

	@Override
	public void doResize(long size, long bytes) {
		if (bytes < 0) {
			return;
		}

		var newBuffer = this.bufferContext.createRawBuffer(this.buffer.getBufferType());

		this.bufferContext.getBufferCreator().setupStorage(
				newBuffer,
				MemoryUtil.NULL,
				MemoryUtil.NULL,
				size + bytes,
				this.buffer.getStorageFlag()
		);

		newBuffer.copyRangeDataFrom(
				this.buffer,
				this.buffer	.getOffset(),
				newBuffer	.getOffset(),
				size
		);

		this.buffer.delete();
		this.buffer = newBuffer;
	}

	@Override
	public IResizeMethod getResizeMethod() {
		return resizeMethod;
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		return buffer;
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
	public void onResize(long size, long bytes) {

	}

	@Override
	public void beforeResize() {

	}

	@Override
	public void afterResize() {

	}
}
