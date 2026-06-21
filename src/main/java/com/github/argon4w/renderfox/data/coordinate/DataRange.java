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

public class DataRange implements IDataRange {

	private final long offset;
	private final long length;

	public DataRange(long offset, long length) {
		this.offset = offset;
		this.length = length;
	}

	public DataRange(long length) {
		this.offset = 0L;
		this.length = length;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public long getLength() {
		return length;
	}
}
