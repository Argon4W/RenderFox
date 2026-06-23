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

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.data.view.IMappedDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferAccessBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.GLMutableBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public abstract class AbstractGLMappedBuffer extends GLMutableBuffer implements IGLMappedBuffer {

	protected final	GLBufferMapAccess		mapAccess;
	protected final	GLMappedDataView.Root	mapView;

	protected		long					generation;

	public AbstractGLMappedBuffer(
			GLBufferContext		bufferContext,
			long				bufferDataAddress,
			long				bufferOffset,
			long				bufferLength,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		super(
				bufferContext,
				bufferDataAddress,
				bufferOffset,
				bufferLength,
				bufferType,
				storageFlag
		);

		this.generation	= 0;
		this.mapAccess	= mapAccess.copy();
		this.mapView	= new GLMappedDataView.Root(this);

		this.mapAccess.remove(GLBufferAccessBit.MAP_PERSISTENT);
		this.mapAccess.remove(GLBufferAccessBit.MAP_COHERENT);
	}

	protected abstract void			open		();
	protected abstract void			close		();
	protected abstract void			map			();
	protected abstract void			unmap		();
	protected abstract IDataView<?>	getDataView	();
	protected abstract IDataRange	flush		(IDataRange range);

	@Override
	public IMappedDataView<?> reserve(long size) {
		open();

		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative.");
		}

		if (this.buffer.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!this.buffer.isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (this.mapView.remaining() < size) {
			resize(this.mapView.position() + size);
		}

		return this.mapView.slice(size);
	}

	@Override
	public IGLBufferDataView<?> mapRangeData(IDataRange mapRange, GLBufferMapAccess mapAccess) {
		open();

		if (this.buffer.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!this.buffer.isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (!this.mapAccess.same(mapAccess)) {
			throw new IllegalStateException("The mapAccess is not equal to the pre-defined mapAccess in the buffer.");
		}

		return mapView.slice(mapRange);
	}

	@Override
	public void clear() {
		generation ++;

		mapView.clear	();
		mapView.sync	();
	}

	@Override
	public void beforeResize() {
		unmap();
	}

	@Override
	public void afterResize() {
		map();
	}

	public long getGeneration() {
		return generation;
	}

	@Override
	public GLMappedBufferView view(IDataRange viewRange) {
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

		return new GLMappedBufferView(
				this,
				mapView		.slice		(viewRange),
				viewRange	.getOffset	(),
				viewRange	.getLength	()
		);
	}
}
