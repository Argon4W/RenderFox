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

public class Extent3DReference extends AbstractExtent3D {

	private final	int[]	widths;
	private final	int[]	heights;
	private final	int[]	depths;
	private			int		mipLevel;

	public Extent3DReference(
			int[]	widths,
			int[]	heights,
			int[]	depths,
			int		mipLevel
	) {
		this.widths		= widths;
		this.heights	= heights;
		this.depths		= depths;
		this.mipLevel	= mipLevel;
	}

	public Extent3DReference(
			int[][]	mipDimensions,
			int		mipLevel
	) {
		this.widths		= mipDimensions[0];
		this.heights	= mipDimensions[1];
		this.depths		= mipDimensions[2];
		this.mipLevel	= mipLevel;
	}

	public void setMipLevel(int mipLevel) {
		this.mipLevel = mipLevel;
	}

	@Override
	public long getWidth() {
		return widths[mipLevel];
	}

	@Override
	public long getHeight() {
		return heights[mipLevel];
	}

	@Override
	public long getDepth() {
		return depths[mipLevel];
	}
}
