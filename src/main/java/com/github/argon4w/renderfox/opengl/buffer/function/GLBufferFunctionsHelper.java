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

package com.github.argon4w.renderfox.opengl.buffer.function;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferParameter;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.AbstractGLBufferStore;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferOperation;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferSetup;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.GLRawBufferView;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.util.RangeUtils;

public class GLBufferFunctionsHelper extends AbstractGLBufferStore implements IGLRawBuffer {

	private final	IGLBufferFunctions	bufferFunctions;

	private			int					bufferHandle;
	private			GLBufferType		bufferType;

	public GLBufferFunctionsHelper(IGLBufferFunctions bufferFunctions) {
		this.bufferFunctions	= bufferFunctions;
		this.bufferHandle		= -1;
		this.bufferType			= null;
	}

	public GLBufferFunctionsHelper setBuffer(int bufferHandle, GLBufferType bufferType) {
		this.bufferHandle	= bufferHandle;
		this.bufferType		= bufferType;

		return this;
	}

	public boolean isOccupied(IDataRange range) {
		return isMapped() && !isPersistent() && RangeUtils.isOverlapped(getMapRange(), range);
	}

	@Override
	public void setupStorage(
			long				bufferSize,
			long				bufferDataAddress,
			GLBufferStorageFlag	storageFlag
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (storageFlag == null) {
			throw new IllegalArgumentException("StorageFlag cannot be null.");
		}

		bufferFunctions.bufferStorage(
				bufferHandle,
				bufferSize,
				bufferDataAddress,
				storageFlag.getRawFlags(),
				bufferType
		);
	}

	@Override
	public void uploadData(
			long			bufferDataSize,
			long			bufferDataAddress,
			GLBufferUsage	bufferUsage
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (bufferUsage == null) {
			throw new IllegalArgumentException("BufferUsage cannot be null.");
		}

		if (bufferUsage == GLBufferUsage.INVALID) {
			throw new IllegalArgumentException("Invalid bufferUsage.");
		}

		bufferFunctions.bufferData(
				bufferHandle,
				bufferDataSize,
				bufferDataAddress,
				bufferUsage.getConstant(),
				bufferType
		);
	}

	@Override
	public int getParameterInt(GLBufferParameter parameter) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLBufferParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return bufferFunctions.getBufferParameteri(
				bufferHandle,
				parameter.getConstant(),
				bufferType
		);
	}

	@Override
	public long getParameterLong(GLBufferParameter parameter) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLBufferParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return bufferFunctions.getBufferParameteri64(
				bufferHandle,
				parameter.getConstant(),
				bufferType
		);
	}

	public void uploadRangeData(
			long bufferDataOffset,
			long bufferDataSize,
			long bufferDataAddress
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		bufferFunctions.bufferSubData(
				bufferHandle,
				bufferDataOffset,
				bufferDataSize,
				bufferDataAddress,
				bufferType
		);
	}

	@Override
	public void copyRangeDataTo(
			IGLBufferBase	bufferWrite,
			long			bufferCopyOffsetRead,
			long			bufferCopyOffsetWrite,
			long			bufferCopySize
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (bufferWrite == null) {
			throw new IllegalArgumentException("BufferWrite cannot be null.");
		}

		bufferFunctions.copyBufferSubData(
				bufferHandle,
				bufferWrite.getBufferHandle(),
				bufferCopyOffsetRead,
				bufferCopyOffsetWrite,
				bufferCopySize
		);
	}

	@Override
	public void copyRangeDataFrom(
			IGLBufferBase	bufferRead,
			long			bufferCopyOffsetRead,
			long			bufferCopyOffsetWrite,
			long			bufferCopySize
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (bufferRead == null) {
			throw new IllegalArgumentException("BufferRead cannot be null.");
		}

		bufferFunctions.copyBufferSubData(
				bufferRead.getBufferHandle(),
				bufferHandle,
				bufferCopyOffsetRead,
				bufferCopyOffsetWrite,
				bufferCopySize
		);
	}

	@Override
	public void clearAllData(
			GLInternalFormat	bufferClearFormat,
			GLFormat			clearDataFormat,
			GLDataType			clearDataType,
			long				clearDataAddress
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (bufferClearFormat == null) {
			throw new IllegalArgumentException("BufferClearFormat cannot be null.");
		}

		if (bufferClearFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid bufferClearFormat.");
		}

		if (clearDataFormat == null) {
			throw new IllegalArgumentException("clearDataFormat cannot be null.");
		}

		if (clearDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataFormat.");
		}

		if (clearDataType == null) {
			throw new IllegalArgumentException("ClearDataType cannot be null.");
		}

		if (clearDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataType.");
		}

		bufferFunctions.clearBufferData(
				bufferHandle,
				bufferClearFormat	.getConstant(),
				clearDataFormat		.getConstant(),
				clearDataType		.getConstant(),
				clearDataAddress,
				bufferType
		);
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	bufferClearFormat,
			long				bufferClearOffset,
			long				bufferClearSize,
			GLFormat			clearDataFormat,
			GLDataType			clearDataType,
			long				clearDataAddress
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (bufferClearFormat == null) {
			throw new IllegalArgumentException("BufferClearFormat cannot be null.");
		}

		if (bufferClearFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid bufferClearFormat.");
		}

		if (clearDataFormat == null) {
			throw new IllegalArgumentException("clearDataFormat cannot be null.");
		}

		if (clearDataFormat == GLFormat.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataFormat.");
		}

		if (clearDataType == null) {
			throw new IllegalArgumentException("ClearDataType cannot be null.");
		}

		if (clearDataType == GLDataType.INVALID) {
			throw new IllegalArgumentException("Invalid clearDataType.");
		}

		bufferFunctions.clearBufferSubData(
				bufferHandle,
				bufferClearFormat.getConstant(),
				bufferClearOffset,
				bufferClearSize,
				clearDataFormat	.getConstant(),
				clearDataType	.getConstant(),
				clearDataAddress,
				bufferType
		);
	}

	@Override
	public long map(GLBufferMapAccessLegacy mapAccess) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (mapAccess == null) {
			throw new IllegalArgumentException("MapAccess cannot be null.");
		}

		if (mapAccess == GLBufferMapAccessLegacy.INVALID) {
			throw new IllegalArgumentException("Invalid mapAccess.");
		}

		return bufferFunctions.mapBuffer(
				bufferHandle,
				mapAccess.getConstant(),
				bufferType
		);
	}

	@Override
	public long mapRange(
			long				mapOffset,
			long				mapLength,
			GLBufferMapAccess	mapAccess
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (mapAccess == null) {
			throw new IllegalArgumentException("MapAccess cannot be null.");
		}

		if (mapAccess.isEmpty()) {
			throw new IllegalArgumentException("MapAccess cannot be empty.");
		}

		return bufferFunctions.mapBufferRange(
				bufferHandle,
				mapOffset,
				mapLength,
				mapAccess.getRawFlags(),
				bufferType
		);
	}

	@Override
	public void unmap() {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		bufferFunctions.unmapBuffer(bufferHandle, bufferType);
	}

	@Override
	public void flushMappedRange(long flushOffset, long flushLength) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		bufferFunctions.flushMappedBufferRange(
				bufferHandle,
				flushOffset,
				flushLength,
				bufferType
		);
	}

	@Override
	public void downloadRangeData(
			long outOffset,
			long outSize,
			long outDataAddress
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		bufferFunctions.getBufferSubData(
				bufferHandle,
				outOffset,
				outSize,
				outDataAddress,
				bufferType
		);
	}

	@Override
	public boolean isBuffer() {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		return bufferFunctions.isBuffer(bufferHandle);
	}

	@Override
	public void bind(GLBufferType bufferType) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		bufferFunctions.bindBuffer(bufferHandle,bufferType.getConstant());
	}

	@Override
	public void bindBase(GLBufferBlockType bufferBlockType, int bindTargetIndex) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (bufferBlockType == null) {
			throw new IllegalArgumentException("BufferBlockType cannot be null.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferBlockType.");
		}

		bufferFunctions.bindBufferBase(
				bufferHandle,
				bufferBlockType.getTypeConstant(),
				bindTargetIndex,
				bufferType
		);
	}

	@Override
	public void bindRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferTargetIndex,
			long				bufferBindOffset,
			long				bufferBindSize
	) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		if (bufferBlockType == null) {
			throw new IllegalArgumentException("BufferBlockType cannot be null.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferBlockType.");
		}

		bufferFunctions.bindBufferRange(
				bufferHandle,
				bufferBlockType.getTypeConstant(),
				bufferTargetIndex,
				bufferBindOffset,
				bufferBindSize,
				bufferType
		);
	}

	@Override
	public void invalidateAllData() {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		bufferFunctions.invalidateBufferData(bufferHandle, bufferType);
	}

	@Override
	public void invalidateRangeData(long bufferOffset, long bufferLength) {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		bufferFunctions.invalidateBufferSubData(
				bufferHandle,
				bufferOffset,
				bufferLength,
				bufferType
		);
	}

	@Override
	public void delete() {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		bufferFunctions.deleteBuffer(bufferHandle);
	}

	@Override
	public int getBufferHandle() {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		return bufferHandle;
	}

	@Override
	public GLBufferType getBufferType() {
		if (bufferHandle == -1) {
			throw new IllegalStateException("BufferHandle has not yet been set.");
		}

		if (bufferType == null) {
			throw new IllegalStateException("BufferType has not yet been set.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalStateException("Invalid BufferType.");
		}

		return bufferType;
	}

	@Override
	public boolean isDeleted() {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public IGLRawBufferView view(long offset, long length) {
		return new GLRawBufferView(this, offset, length);
	}

	@Override
	public long getLength() {
		return getBufferSize();
	}

	@Override
	public long getOffset() {
		return 0;
	}
}
