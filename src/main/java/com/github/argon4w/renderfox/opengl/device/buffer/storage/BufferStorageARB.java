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
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped.GLMappedBufferLegacy;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped.GLMappedBufferPersistent;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped.IGLMappedBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.GLRawBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBuffer;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public class BufferStorageARB extends AbstractBufferStorage {

	public BufferStorageARB(GLBufferContext bufferContext) {
		super(bufferContext);
	}

	@Override
	public void setupStorage(
			IGLRawBuffer		buffer,
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferStorageFlag	storageFlag
	) {
		buffer.setupStorage(
				bufferDataSize,
				bufferDataAddress + bufferDataOffset,
				storageFlag
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

	@Override
	protected IGLMappedBuffer setupPersistent(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		return new GLMappedBufferPersistent(
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
