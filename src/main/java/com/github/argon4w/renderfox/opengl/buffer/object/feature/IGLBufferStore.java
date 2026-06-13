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

package com.github.argon4w.renderfox.opengl.buffer.object.feature;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;

public interface IGLBufferStore {

	int						getBufferSize	();
	boolean					isImmutable		();
	boolean					isMapped		();
	long					getMapOffset	();
	long					getMapLength	();
	IDataRange				getMapRange		();
	GLBufferStorageFlag		getStorageFlag	();
	GLBufferMapAccess		getAccessFlag	();
	GLBufferUsage			getBufferUsage	();
	GLBufferMapAccessLegacy	getAccess		();
	boolean					isPersistent	();
	boolean					isDynamic		();
}
