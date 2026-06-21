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

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.opengl.buffer.object.wrapped.GLBufferWrapper;
import com.github.argon4w.renderfox.opengl.buffer.object.wrapped.IGLBuffer;

public class GLMutableBufferView extends GLBufferWrapper {

	private final GLMutableBuffer	buffer;
	private final long				offset;
	private final long				length;

	public GLMutableBufferView(
			GLMutableBuffer	buffer,
			long			offset,
			long			length
	) {
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
	}

	@Override
	public GLMutableBufferView view(IDataRange viewRange) {
		if (buffer.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (viewRange == null) {
			throw new IllegalArgumentException("View range cannot be null.");
		}

		if (viewRange.getOffset() < 0) {
			throw new IllegalArgumentException("ViewOffset cannot be negative.");
		}

		if (viewRange.getLength() < 0) {
			throw new IllegalArgumentException("ViewLength cannot be negative.");
		}

		if (viewRange.getOffset() + viewRange.getLength() > this.length) {
			throw new IllegalArgumentException("ViewLength + viewLength cannot be greater than the value of the length of the current view.");
		}

		return new GLMutableBufferView(
				this.buffer,
				this.offset + viewRange.getOffset(),
				viewRange.getLength()
		);
	}

	@Override
	public IGLBuffer getBuffer() {
		return buffer.getBuffer().view(new DataRange(offset, length));
	}

	@Override
	public void delete() {
		throw new UnsupportedOperationException("Cannot delete a view of buffer.");
	}
}
