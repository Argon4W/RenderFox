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

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.wrapped.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public class GLMappedBufferPersistent extends AbstractGLMappedBuffer {

	private IGLBufferDataView<?>	dataView;
	private boolean					mapped;

	public GLMappedBufferPersistent(
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
				storageFlag,
				mapAccess
		);

		this.dataView	= null;
		this.mapped		= false;

		map();
	}

	@Override
	protected void map() {
		mapped		= true;
		dataView	= buffer.mapRangeData(new DataRange(bufferSize), mapAccess);
	}

	@Override
	protected void unmap() {
		dataView.close();
		dataView	= null;
		mapped		= false;
	}

	@Override
	public void flush(long offset, long length) {
		dataView.flush(offset, length);
	}

	@Override
	protected IGLBufferDataView<?> getDataView() {
		return dataView;
	}

	@Override
	public boolean isMapped() {
		return mapped;
	}

	@Override
	public void open() {

	}
}
