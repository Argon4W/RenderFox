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

import com.github.argon4w.renderfox.opengl.buffer.object.IGLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.AbstractGLRawBufferView;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;

public class GLMutableRawBufferView extends AbstractGLRawBufferView {

	private final IGLBuffer buffer;

	public GLMutableRawBufferView(
			IGLBuffer	buffer,
			long		offset,
			long		length
	) {
		super(offset, length);

		this.buffer = buffer;
	}

	@Override
	protected IGLRawBufferView getBuffer() {
		return buffer.getRawBuffer();
	}

	@Override
	public IGLRawBufferView view(long offset, long length) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (buffer.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (offset + length > this.getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the view length.");
		}

		return new GLMutableRawBufferView(
				this.buffer,
				this.getOffset() +	offset,
									length
		);
	}
}
