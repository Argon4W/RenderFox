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

package com.github.argon4w.renderfox.opengl.buffer.object.mapped;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IMappedDataView;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;

public abstract class AbstractGLMappedBuffer extends GLBuffer implements IGLMappedBufferInternal {

	protected final GLBufferStorageFlag		storageFlag;
	protected final	GLBufferMapAccess		mapAccess;
	protected final GLMappedDataView.Root	mapView;
	private			int						mapGeneration;

	public AbstractGLMappedBuffer(
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess,
			IGLRawBufferView	buffer
	) {
		super(buffer);

		this.mapGeneration	= 0;
		this.storageFlag	= storageFlag			.copy();
		this.mapAccess		= mapAccess				.copy();
		this.mapView		= new GLMappedDataView	.Root(this);
	}

	public abstract void disable();

	protected IGLBufferDataView<?> mapBuffer() {
		return super.mapRangeData(
				this,
				this.mapAccess
		);
	}

	@Override
	public IMappedDataView<?> reserve(long size) {
		open();

		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative.");
		}

		if (this.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!this.isMapped()) {
			throw new IllegalStateException("The buffer is not in a mapped state.");
		}

		if (this.mapView.remaining() < size) {
			throw new IllegalStateException("The buffer has not enough space to reserve.");
		}

		return this.mapView.slice(size);
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

		if (viewRange.getOffset() + viewRange.getLength() > getBufferSize()) {
			throw new IllegalArgumentException("ViewOffset + viewLength cannot be greater than the value of buffer size.");
		}

		return new GLMappedBufferView(
				this,
				this.mapView.slice	(viewRange),
				this.buffer	.view	(
						viewRange.getOffset(),
						viewRange.getLength()
				)
		);
	}

	@Override
	public void clear() {
		this.mapGeneration	++;
		this.mapView.clear	();
		this.mapView.sync	();
	}

	@Override
	public long getRemaining() {
		return mapView.remaining();
	}

	@Override
	public long getPosition() {
		return mapView.position();
	}

	@Override
	public int getGeneration() {
		return mapGeneration;
	}
}
