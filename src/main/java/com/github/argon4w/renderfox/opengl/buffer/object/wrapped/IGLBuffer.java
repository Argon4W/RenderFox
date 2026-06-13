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

package com.github.argon4w.renderfox.opengl.buffer.object.wrapped;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBinding;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferStore;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public interface IGLBuffer extends IGLBufferBase, IGLBufferStore, IGLBufferBinding, IDataRange {

	IGLBufferDataView<?> mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess);

	void clearAllData(byte value);

	void clearAllData(GLInternalFormat internalFormat, ColorFloat clearColor);

	void clearAllData(GLInternalFormat internalFormat, ColorInt clearColor);

	void clearAllData(
			GLInternalFormat	internalFormat,
			float				clearDepth,
			int					clearStencil
	);

	void clearAllData(GLInternalFormat internalFormat, float clearDepth);

	void clearAllData(GLInternalFormat internalFormat, int clearStencil);

	void clearRangeData(IDataRange clearRangeElement, byte value);

	void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			ColorFloat			clearColor
	);

	void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			ColorInt			clearColor
	);

	void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			float				clearDepth,
			int					clearStencil
	);

	void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			float				clearDepth
	);

	void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			int					clearStencil
	);

	void copyRangeDataTo(
			IGLBufferBase	bufferWrite,
			IDataRange		bufferCopyRangeRead,
			IDataRange		bufferCopyRangeWrite
	);

	void copyRangeDataFrom(
			IGLBufferBase	bufferRead,
			IDataRange		bufferCopyRangeRead,
			IDataRange		bufferCopyRangeWrite
	);

	IGLBuffer view(IDataRange viewRange);

	IGLRawBufferView getRawBuffer();
}
