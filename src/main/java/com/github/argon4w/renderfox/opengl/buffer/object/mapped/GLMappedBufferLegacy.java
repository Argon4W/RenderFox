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
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferAccessBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;

public class GLMappedBufferLegacy extends AbstractGLMappedBuffer {

	private IGLBufferDataView<?>	dataView;
	private int						dataViewRefCount;

	public GLMappedBufferLegacy(
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess,
			IGLRawBufferView	buffer
	) {
		super(
				storageFlag,
				mapAccess,
				buffer
		);

		this.dataView			= null;
		this.dataViewRefCount	= 0;

		this.mapAccess.remove(GLBufferAccessBit.MAP_INVALIDATE_RANGE);
		this.mapAccess.remove(GLBufferAccessBit.MAP_PERSISTENT);
		this.mapAccess.remove(GLBufferAccessBit.MAP_COHERENT);
	}

	@Override
	public void open() {
		this.dataViewRefCount ++;

		if (this.dataView == null) {
			this.dataView = mapBuffer();
		}
	}

	@Override
	public void close() {
		this.dataViewRefCount --;

		if (this.dataViewRefCount <= 0) {
			this.dataView.close();
			this.dataView = null;
		}
	}

	@Override
	public void delete() {
		if (this.dataViewRefCount > 0) {
			this.dataViewRefCount = 0;
			this.dataView.close();
		}

		super.delete();
	}

	@Override
	public IDataRange flush(IDataRange range) {
		return range;
	}

	@Override
	public IGLBufferDataView<?> getView() {
		return dataView;
	}
}
