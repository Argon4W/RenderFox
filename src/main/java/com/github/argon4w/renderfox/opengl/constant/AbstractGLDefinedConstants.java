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

package com.github.argon4w.renderfox.opengl.constant;

import com.github.argon4w.renderfox.data.UnionValue;
import com.github.argon4w.renderfox.data.view.DataViews;

public abstract class AbstractGLDefinedConstants<T> implements IGLDefinedConstants<T> {

	@Override
	public boolean isValidAddress(T context, long address) {
		var view = DataViews.wrapInts(address, getCount());

		for (var i = 0; i < getCount(); i ++) {
			if (!isValidValue(context, new UnionValue(view.getInt((long) i * Integer.BYTES)))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int getCount() {
		return 1;
	}
}
