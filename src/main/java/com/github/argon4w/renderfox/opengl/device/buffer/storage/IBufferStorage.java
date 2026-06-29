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
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.IGLMutableBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.IGLMutableMappedBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBuffer;

public interface IBufferStorage {

	IGLBuffer				createBuffer				(GLBufferCreateInfo			info);
	IGLMutableBuffer		createMutableBuffer			(GLBufferCreateInfo			info);
	IGLMappedBuffer			createMappedBuffer			(GLMappedBufferCreateInfo	info);
	IGLMutableMappedBuffer	createMutableMappedBuffer	(GLMappedBufferCreateInfo	info);

	IGLBuffer				createBuffer				(GLBufferCreateInfo			info, long size);
	IGLMutableBuffer		createMutableBuffer			(GLBufferCreateInfo			info, long size);
	IGLMappedBuffer			createMappedBuffer			(GLMappedBufferCreateInfo	info, long size);
	IGLMutableMappedBuffer	createMutableMappedBuffer	(GLMappedBufferCreateInfo	info, long size);

	IGLBuffer				createBuffer				(GLBufferCreateInfo			info, IDataView<?> bufferData);
	IGLMutableBuffer		createMutableBuffer			(GLBufferCreateInfo			info, IDataView<?> bufferData);
	IGLMappedBuffer			createMappedBuffer			(GLMappedBufferCreateInfo	info, IDataView<?> bufferData);
	IGLMutableMappedBuffer	createMutableMappedBuffer	(GLMappedBufferCreateInfo	info, IDataView<?> bufferData);

	IGLBuffer createBuffer(
			GLBufferCreateInfo			info,
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	IGLMutableBuffer createMutableBuffer(
			GLBufferCreateInfo			info,
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	IGLMappedBuffer createMappedBuffer(
			GLMappedBufferCreateInfo	info,
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	IGLMutableMappedBuffer createMutableMappedBuffer(
			GLMappedBufferCreateInfo	info,
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	IGLBuffer createBuffer(
			GLBufferCreateInfo			info,
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	IGLMutableBuffer createMutableBuffer(
			GLBufferCreateInfo			info,
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	IGLMappedBuffer createMappedBuffer(
			GLMappedBufferCreateInfo	info,
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	IGLMutableMappedBuffer createMutableMappedBuffer(
			GLMappedBufferCreateInfo	info,
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize
	);

	void setupStorage(
			IGLRawBuffer				buffer,
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferStorageFlag			storageFlag
	);

	IGLBuffer createBuffer(
			long						bufferSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLBuffer createBuffer(
			IDataView<?>				bufferData,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLBuffer createBuffer(
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLBuffer createBuffer(
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLMutableBuffer createMutableBuffer(
			long						bufferSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLMutableBuffer createMutableBuffer(
			IDataView<?>				bufferData,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLMutableBuffer createMutableBuffer(
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLMutableBuffer createMutableBuffer(
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag
	);

	IGLMappedBuffer createMappedBuffer(
			long						bufferSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);

	IGLMappedBuffer createMappedBuffer(
			IDataView<?>				bufferData,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);

	IGLMappedBuffer createMappedBuffer(
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);

	IGLMappedBuffer createMappedBuffer(
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);

	IGLMutableMappedBuffer createMutableMappedBuffer(
			long						bufferSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);

	IGLMutableMappedBuffer createMutableMappedBuffer(
			IDataView<?>				bufferData,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);

	IGLMutableMappedBuffer createMutableMappedBuffer(
			IDataView<?>				bufferData,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);

	IGLMutableMappedBuffer createMutableMappedBuffer(
			long						bufferDataAddress,
			long						bufferDataOffset,
			long						bufferDataSize,
			GLBufferType				bufferType,
			GLBufferStorageFlag			storageFlag,
			GLBufferMapAccess			mapAccess
	);
}
