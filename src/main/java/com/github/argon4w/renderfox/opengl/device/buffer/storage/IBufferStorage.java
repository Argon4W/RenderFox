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

import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBufferCreateInfo;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.GLMappedBufferCreateInfo;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.IGLMappedBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBuffer;

public interface IBufferStorage {

	IGLBuffer		createBuffer		(GLBufferCreateInfo			info);
	IGLMappedBuffer	createMappedBuffer	(GLMappedBufferCreateInfo	info);

	void setupStorage(
			IGLRawBuffer		buffer,
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createBuffer(
			long				bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createBuffer(
			IDataView<?>		bufferData,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createBuffer(
			IDataView<?>		bufferData,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createMutableBuffer(
			long				bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createMutableBuffer(
			IDataView<?>		bufferData,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createMutableBuffer(
			IDataView<?>		bufferData,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLBuffer createMutableBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	);

	IGLMappedBuffer createMappedBuffer(
			long				bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	);

	IGLMappedBuffer createMappedBuffer(
			IDataView<?>		bufferData,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	);

	IGLMappedBuffer createMappedBuffer(
			IDataView<?>		bufferData,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	);

	IGLMappedBuffer createMappedBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	);

	IGLMappedBuffer createMappedBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess,
			IGLMappedBuffer		prototype
	);
}
