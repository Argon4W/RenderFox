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
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.AbstractGLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.*;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.impl.IGLMappedBufferImpl;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.device.buffer.storage.AbstractBufferStorage;
import org.lwjgl.system.MemoryUtil;

public class GLMutableMappedBuffer extends AbstractGLBuffer implements IGLMutableMappedBuffer, IMutableSizeObjectInternal, IGLMappedBufferInternal {

	protected final	AbstractBufferStorage	bufferStorage;
	protected final	IResizeMethod			resizeMethod;
	protected final	GLMutableMappedDataView	mapView;
	protected		IGLMappedBufferImpl		mapImpl;
	protected		IDataView<?>			mapImplView;
	protected		int						mapCount;
	protected 		long					bufferSize;

	public GLMutableMappedBuffer(
			AbstractBufferStorage	bufferStorage,
			IResizeMethod			resizeMethod,
			long					bufferDataAddress,
			long					bufferOffset,
			long					bufferLength,
			GLBufferType			bufferType,
			GLBufferStorageFlag		storageFlag,
			GLBufferMapAccess		mapAccess
	) {
		this.mapImpl = bufferStorage.createMappedBufferImpl(
				bufferDataAddress,
				bufferOffset,
				bufferLength,
				bufferType,
				storageFlag,
				mapAccess
		);

		this.bufferStorage	= bufferStorage;
		this.resizeMethod	= resizeMethod;
		this.mapView		= new GLMutableMappedDataView.Root(this);

		this.mapImplView	= null;
		this.mapCount		= 0;
		this.bufferSize		= bufferLength;
	}

	@Override
	public GLMutableMappedDataView data() {
		return open();
	}

	@Override
	public GLMutableMappedDataView data(IDataRange dataRange) {
		if (dataRange == null) {
			throw new IllegalArgumentException("Data range cannot be null.");
		}

		if (dataRange.getOffset() < 0) {
			throw new IllegalArgumentException("DataOffset cannot be negative.");
		}

		if (dataRange.getLength() < 0) {
			throw new IllegalArgumentException("DataLength cannot be negative.");
		}

		if (dataRange.getRequired() > bufferSize) {
			resize(dataRange);
		}

		return open().slice(dataRange);
	}

	@Override
	public GLMutableMappedDataView mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess) {
		if (mapDataRange == null) {
			throw new IllegalArgumentException("MapDataRange cannot be null.");
		}

		if (mapDataRange.getOffset() < 0) {
			throw new IllegalArgumentException("MapOffset cannot be negative.");
		}

		if (mapDataRange.getLength() < 0) {
			throw new IllegalArgumentException("MapLength cannot be negative.");
		}

		if (!getAccessFlag().allow(mapAccess)) {
			throw new IllegalStateException("The mapAccess contains bits that are not in the pre-defined mapAccess in this buffer.");
		}

		if (mapDataRange.getRequired() > bufferSize) {
			resize(mapDataRange);
		}

		return open().slice(mapDataRange);
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
			resize(viewRange);
		}

		return new GLMappedBufferView(
				this,
				new GLMutableRawBufferView(
						this,
						viewRange.getOffset(),
						viewRange.getLength()
				)
		);
	}

	@Override
	public void doResize(long size, long bytes) {
		if (bytes < 0) {
			return;
		}

		if (this.mapCount > 0) {
			this.mapImpl.close();
			this.mapImplView = null;
		}

		var newImpl = bufferStorage.createMappedBufferImpl(
				MemoryUtil.NULL,
				MemoryUtil.NULL,
				size + bytes,
				this.mapImpl.getBufferType	(),
				this.mapImpl.getStorageFlag	(),
				this.mapImpl.getAccessFlag	()
		);

		this.mapImpl.copyRangeDataTo(
				newImpl,
				this.mapImpl,
				this.mapImpl
		);

		this.mapImpl.delete();
		this.mapImpl = newImpl;

		if (this.mapCount > 0) {
			this.mapImplView = this.mapImpl.open();
		}
	}

	@Override
	public GLMutableMappedDataView open() {
		this.mapCount ++;

		if (this.mapImplView == null) {
			this.mapImplView = this.mapImpl.open();
		}

		// IMPORTANT: Return the wrapper to prevent incorrect address after resizing.
		return this.mapView;
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
	public void delete() {
		if (this.mapCount > 0) {
			this.mapImpl.close();
			this.mapImplView = null;
		}

		super.delete();
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
	public IResizeMethod getResizeMethod() {
		return resizeMethod;
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		return mapImpl.getRawBuffer();
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
