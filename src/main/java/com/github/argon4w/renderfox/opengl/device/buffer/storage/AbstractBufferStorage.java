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
import com.github.argon4w.renderfox.opengl.buffer.object.mutable.GLMutableBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.GLMappedBufferCreateInfo;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.IGLMappedBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBuffer;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;
import org.lwjgl.system.MemoryUtil;

public abstract class AbstractBufferStorage implements IBufferStorage {

	protected final GLBufferContext bufferContext;

	public AbstractBufferStorage(GLBufferContext bufferContext) {
		this.bufferContext = bufferContext;
	}

	protected abstract IGLMappedBuffer setupMapped(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess,
			IGLMappedBuffer		prototype
	);

	protected IGLMappedBuffer setupPersistent(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess,
			IGLMappedBuffer		prototype
	) {
		return setupMapped(
				bufferDataAddress,
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag,
				mapAccess,
				prototype
		);
	}

	@Override
	public IGLBuffer createBuffer(GLBufferCreateInfo info) {
		if (info == null) {
			throw new IllegalArgumentException("Info cannot be null.");
		}

		return createBuffer(
				info.getBufferSize	(),
				info.getBufferType	(),
				info.getStorageFlag	()
		);
	}

	@Override
	public IGLMappedBuffer createMappedBuffer(GLMappedBufferCreateInfo info) {
		if (info == null) {
			throw new IllegalArgumentException("Info cannot be null.");
		}

		return createMappedBuffer(
				info.getBufferSize	(),
				info.getBufferType	(),
				info.getStorageFlag	(),
				info.getMapAccess	()
		);
	}

	@Override
	public IGLBuffer createBuffer(
			long				bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		return createBuffer(
				MemoryUtil.NULL,
				MemoryUtil.NULL,
				bufferSize,
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLBuffer createBuffer(
			IDataView<?>		bufferData,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		if (bufferData == null) {
			throw new IllegalArgumentException("bufferData cannot be null.");
		}

		return createBuffer(
				bufferData,
				bufferData.position	(),
				bufferData.remaining(),
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLBuffer createBuffer(
			IDataView<?>		bufferData,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		if (bufferData == null) {
			throw new IllegalArgumentException("BufferData cannot be null.");
		}

		if (!bufferData.isOffHeap()) {
			throw new IllegalArgumentException("BufferData is not an off-heap data view.");
		}

		if (bufferDataOffset + bufferDataSize > bufferData.limit()) {
			throw new IllegalArgumentException("BufferDataOffset + bufferDataSize cannot be greater than the value of buffer limit.");
		}

		return createBuffer(
				bufferData.address(),
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLBuffer createBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		if (storageFlag == null) {
			throw new IllegalArgumentException("StorageFlag cannot be null.");
		}

		var buffer = bufferContext.createRawBuffer(bufferType);

		setupStorage(
				buffer,
				bufferDataAddress,
				bufferDataOffset,
				bufferDataSize,
				storageFlag
		);

		return new GLBuffer(buffer);
	}

	@Override
	public IGLBuffer createMutableBuffer(
			long				bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		return createMutableBuffer(
				MemoryUtil.NULL,
				MemoryUtil.NULL,
				bufferSize,
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLBuffer createMutableBuffer(
			IDataView<?>		bufferData,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		if (bufferData == null) {
			throw new IllegalArgumentException("BufferData cannot be null.");
		}

		return createMutableBuffer(
				bufferData,
				bufferData.position	(),
				bufferData.remaining(),
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLBuffer createMutableBuffer(
			IDataView<?>		bufferData,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		if (bufferData == null) {
			throw new IllegalArgumentException("BufferData cannot be null.");
		}

		if (!bufferData.isOffHeap()) {
			throw new IllegalArgumentException("BufferData is not an off-heap data view.");
		}

		if (bufferDataOffset + bufferDataSize > bufferData.limit()) {
			throw new IllegalArgumentException("BufferDataOffset + bufferDataSize cannot be greater than the value of buffer limit.");
		}

		return createMutableBuffer(
				bufferData.address(),
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLBuffer createMutableBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		return new GLMutableBuffer(
				bufferContext,
				bufferDataAddress,
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag
		);
	}

	@Override
	public IGLMappedBuffer createMappedBuffer(
			long				bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		return createMappedBuffer(
				MemoryUtil.NULL,
				MemoryUtil.NULL,
				bufferSize,
				bufferType,
				storageFlag,
				mapAccess
		);
	}

	@Override
	public IGLMappedBuffer createMappedBuffer(
			IDataView<?>		bufferData,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		if (bufferData == null) {
			throw new IllegalArgumentException("BufferData cannot be null.");
		}

		return createMappedBuffer(
				bufferData,
				bufferData.position	(),
				bufferData.remaining(),
				bufferType,
				storageFlag,
				mapAccess
		);
	}

	@Override
	public IGLMappedBuffer createMappedBuffer(
			IDataView<?>		bufferData,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		if (bufferData == null) {
			throw new IllegalArgumentException("BufferData cannot be null.");
		}

		if (!bufferData.isOffHeap()) {
			throw new IllegalArgumentException("BufferData is not an off-heap data view.");
		}

		if (bufferDataOffset + bufferDataSize > bufferData.limit()) {
			throw new IllegalArgumentException("BufferDataOffset + bufferDataSize cannot be greater than the value of buffer limit.");
		}

		return createMappedBuffer(
				bufferData.address(),
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag,
				mapAccess
		);
	}

	@Override
	public IGLMappedBuffer createMappedBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		return createMappedBuffer(
				bufferDataAddress,
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag,
				mapAccess,
				null
		);
	}

	@Override
	public IGLMappedBuffer createMappedBuffer(
			long				bufferDataAddress,
			long				bufferDataOffset,
			long				bufferDataSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess,
			IGLMappedBuffer		prototype
	) {
		if (storageFlag == null) {
			throw new IllegalArgumentException("StorageFlag cannot be null.");
		}

		if (mapAccess == null) {
			throw new IllegalArgumentException("MapAccess cannot be null.");
		}

		if (mapAccess.isEmpty()) {
			throw new IllegalArgumentException("MapAccess cannot be empty.");
		}

		if (mapAccess.isPersistent()) {
			return setupPersistent(
					bufferDataAddress,
					bufferDataOffset,
					bufferDataSize,
					bufferType,
					storageFlag,
					mapAccess,
					prototype
			);
		}

		return setupMapped(
				bufferDataAddress,
				bufferDataOffset,
				bufferDataSize,
				bufferType,
				storageFlag,
				mapAccess,
				prototype
		);
	}
}
