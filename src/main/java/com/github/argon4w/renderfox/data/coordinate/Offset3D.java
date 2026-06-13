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

package com.github.argon4w.renderfox.data.coordinate;

public class Offset3D implements IOffset3D {

	private final long offsetX;
	private final long offsetY;
	private final long offsetZ;

	public Offset3D() {
		this.offsetX = 0L;
		this.offsetY = 0L;
		this.offsetZ = 0L;
	}

	public Offset3D(long offsetX) {
		this.offsetX = offsetX;
		this.offsetY = 0L;
		this.offsetZ = 0L;
	}

	public Offset3D(long offsetX, long offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = 0L;
	}

	public Offset3D(
			long offsetX,
			long offsetY,
			long offsetZ
	) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}

	@Override
	public long getOffsetX() {
		return offsetX;
	}

	@Override
	public long getOffsetY() {
		return offsetY;
	}

	@Override
	public long getOffsetZ() {
		return offsetZ;
	}
}
