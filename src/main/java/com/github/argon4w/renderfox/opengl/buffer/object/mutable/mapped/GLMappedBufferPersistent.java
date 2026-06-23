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
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public class GLMappedBufferPersistent extends AbstractGLMappedBuffer {

	private IGLBufferDataView<?> dataView;

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

		this.dataView = this.buffer.mapRangeData(
				this.buffer,
				this.mapAccess
		);
	}

	@Override
	protected void unmap() {
		dataView.close();
	}

	@Override
	protected void map() {
		dataView = buffer.mapRangeData(buffer, mapAccess);
	}

	@Override
	public IDataRange flush(IDataRange range) {
		return dataView.flush(range);
	}

	@Override
	protected IGLBufferDataView<?> getDataView() {
		return dataView;
	}

	@Override
	public void open() {

	}

	@Override
	public void close() {

	}
}
