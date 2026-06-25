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

public interface IMutableSizeObject {

	void onResize		(long size, long bytes);
	void doResize		(long size, long bytes);
	void beforeResize	();
	void afterResize	();

	void			setSize			(long size);
	long			getSize			();
	IResizeMethod	getResizeMethod	();

	default void expand(long bytes) {
		if (bytes <= 0) {
			return;
		}

		var size = getSize();

		beforeResize();

		onResize	(size, bytes);
		doResize	(size, bytes);
		setSize		(size + bytes);

		afterResize	();
	}

	default void resize(long atLeast) {
		resizeTo(getResizeMethod().resize(atLeast));
	}

	default void resizeTo(long newBufferSize) {
		expand(newBufferSize - getSize());
	}
}
