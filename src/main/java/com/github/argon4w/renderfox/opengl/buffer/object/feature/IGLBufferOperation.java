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

import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public interface IGLBufferOperation extends IGLBufferBinding, IGLBufferTransfer {

	void uploadRangeData(
			long					bufferDataOffset,
			long					bufferDataSize,
			long					bufferDataAddress
	);

	void clearAllData(
			GLInternalFormat		bufferClearFormat,
			GLFormat				clearDataFormat,
			GLDataType				clearDataType,
			long					clearDataAddress
	);

	void clearRangeData(
			GLInternalFormat		bufferClearFormat,
			long					bufferClearOffset,
			long					bufferClearSize,
			GLFormat				clearDataFormat,
			GLDataType				clearDataType,
			long					clearDataAddress
	);

	long map(GLBufferMapAccessLegacy mapAccess);

	long mapRange(
			long					mapOffset,
			long					mapLength,
			GLBufferMapAccess		mapAccess
	);

	void unmap();

	void flushMappedRange(long flushOffset, long flushLength);

	void downloadRangeData(
			long					outOffset,
			long					outSize,
			long					outDataAddress
	);

	boolean isBuffer();

	void invalidateAllData();

	void invalidateRangeData(long bufferOffset, long bufferLength);
}
