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

package com.github.argon4w.renderfox.opengl.device.buffer.storage;

import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped.GLMappedBufferLegacy;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped.IGLMappedBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.GLRawBuffer;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public class BufferStorageLegacy extends AbstractBufferStorage {

	public BufferStorageLegacy(GLBufferContext bufferContext) {
		super(bufferContext);
	}

	@Override
	protected void setupStorage(
			GLRawBuffer			buffer,
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferStorageFlag	storageFlag
	) {
		buffer.uploadData(
				bufferDataSize,
				bufferDataAddress + bufferDataOffset,
				GLBufferUsage.DYNAMIC_DRAW
		);
	}

	@Override
	protected IGLMappedBuffer setupMapped(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		return new GLMappedBufferLegacy(
				bufferContext,
				bufferDataAddress,
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag,
				mapAccess
		);
	}
}
