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

package com.github.argon4w.renderfox.data.coordinate.extent;

public class Extent3D extends AbstractExtent3D {

	private final long width;
	private final long height;
	private final long depth;

	public Extent3D(
			long width,
			long height,
			long depth
	) {
		this.width	= width;
		this.height	= height;
		this.depth	= depth;
	}

	public Extent3D(long width) {
		this.width	= width;
		this.height	= 1L;
		this.depth	= 1L;
	}

	public Extent3D(long width, long height) {
		this.width	= width;
		this.height	= height;
		this.depth	= 1L;
	}

	@Override
	public long getWidth() {
		return width;
	}

	@Override
	public long getHeight() {
		return height;
	}

	@Override
	public long getDepth() {
		return depth;
	}
}
