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

package com.github.argon4w.renderfox.data.size;

public interface IResizeMethod {

	IResizeMethod LEAST		= atLeast -> atLeast;
	IResizeMethod TWICE		= atLeast -> atLeast * 2L;
	IResizeMethod TRIPLE	= atLeast -> atLeast * 3L;
	IResizeMethod POWER		= atLeast -> Long.highestOneBit(atLeast) << 1L;

	long resize(long atLeast);
}
