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
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBufferView;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBuffer;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public class GLMutableBuffer extends GLBufferView implements IMutableSizeObject {

	protected final	GLBufferContext		bufferContext;
	protected final	GLBufferType		bufferType;
	protected final	GLBufferStorageFlag	storageFlag;
	protected		IGLBuffer			buffer;
	protected 		long				bufferSize;

	public GLMutableBuffer(
			GLBufferContext		bufferContext,
			long				bufferDataAddress,
			long				bufferOffset,
			long				bufferLength,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		this.bufferType		= bufferType;
		this.bufferContext	= bufferContext;
		this.storageFlag	= storageFlag;
		this.bufferSize		= bufferLength;
		this.buffer			= bufferContext.getBufferCreator().createBuffer(
				bufferDataAddress,
				bufferOffset,
				bufferLength,
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLBuffer view(IDataRange viewRange) {
		if (buffer.isDeleted()) {
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

		return new GLMutableBufferView(
				this,
				viewRange.getOffset(),
				viewRange.getLength()
		);
	}

	@Override
	public void doResize(long size, long bytes) {
		if (bytes < 0) {
			return;
		}

		var newBuffer = this.bufferContext.getBufferCreator().createBuffer(
				size + bytes,
				this.bufferType,
				this.storageFlag
		);

		this.buffer.copyRangeDataTo(
				newBuffer,
				this,
				this
		);

		this.buffer.delete();
		this.buffer = newBuffer;
	}

	@Override
	public IResizeMethod getMethod() {
		return bufferContext.getResizeMethod();
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
	public long getLength() {
		return bufferSize;
	}

	@Override
	public long getOffset() {
		return 0;
	}

	@Override
	public IGLBuffer getBuffer() {
		return buffer;
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
