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

package com.github.argon4w.renderfox.opengl.buffer.object.mapped.impl;

import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;

public class GLMappedBufferImplPersistent extends GLBuffer implements IGLMappedBufferImpl {

	protected final GLBufferMapAccess		mapAccess;
	protected final IGLBufferDataView<?>	dataView;

	public GLMappedBufferImplPersistent(IGLRawBufferView buffer, GLBufferMapAccess mapAccess) {
		super(buffer);

		this.mapAccess	= mapAccess;
		this.dataView	= mapRangeData(
				this,
				this.mapAccess
		);
	}

	@Override
	public void close() {

	}

	@Override
	public IDataView<?> open() {
		return this.dataView;
	}

	@Override
	public void delete() {
		this.dataView.flush();
		this.dataView.close();

		super.delete();
	}
}
