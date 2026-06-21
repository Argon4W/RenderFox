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

package com.github.argon4w.renderfox.opengl.buffer.object.raw;

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.function.GLBufferFunctionsHelper;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.AbstractGLBufferStore;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferSetup;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public class GLRawBuffer extends AbstractGLBufferStore implements IGLRawBuffer {

	private final	GLBufferType			bufferType;
	private final	GLBufferFunctionsHelper	bufferHelper;
	private			long					size;

	private			GLBufferStorageFlag		storageFlags;
	private			GLBufferMapAccess		accessFlags;
	private			GLBufferUsage			usage;
	private			GLBufferMapAccessLegacy	access;
	private			long					mapLength;
	private			long					mapOffset;
	private			boolean					setup;
	private			boolean					mapped;
	private			boolean					immutable;
	private			boolean					deleted;

	public GLRawBuffer(GLBufferContext bufferContext, GLBufferType bufferType) {
		this.bufferType		= bufferType;
		this.bufferHelper	= bufferContext.createBufferHelper(bufferType, bufferContext.createBufferHandle(bufferType));
		this.size			= 0;

		this.storageFlags	= GLBufferStorageFlag		.of();
		this.accessFlags	= GLBufferMapAccess			.of();
		this.usage			= GLBufferUsage				.INVALID;
		this.access			= GLBufferMapAccessLegacy	.INVALID;
		this.mapLength		= 0;
		this.mapOffset		= 0;
		this.setup			= false;
		this.mapped			= false;
		this.immutable		= false;
		this.deleted		= false;
	}

	@Override
	public void setupStorage(
			long				bufferSize,
			long				bufferDataAddress,
			GLBufferStorageFlag	storageFlag
	) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (immutable) {
			throw new IllegalStateException("The buffer has been setup with immutable storage.");
		}

		this.size			= bufferSize;
		this.storageFlags	= storageFlag;
		this.immutable		= true;
		this.setup			= true;

		bufferHelper.setupStorage(
				bufferSize,
				bufferDataAddress,
				storageFlag
		);
	}

	@Override
	public void uploadData(
			long			bufferDataSize,
			long			bufferDataAddress,
			GLBufferUsage	bufferUsage
	) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (immutable) {
			throw new IllegalStateException("The buffer has been setup  with immutable storage.");
		}

		this.size	= bufferDataSize;
		this.usage	= bufferUsage;
		this.setup	= true;

		bufferHelper.uploadData(
				bufferDataSize,
				bufferDataAddress,
				bufferUsage
		);
	}

	@Override
	public void uploadRangeData(
			long bufferDataOffset,
			long bufferDataSize,
			long bufferDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.uploadRangeData(
				bufferDataOffset,
				bufferDataSize,
				bufferDataAddress
		);
	}

	@Override
	public void copyRangeDataTo(
			IGLBufferBase	bufferWrite,
			long			bufferCopyOffsetRead,
			long			bufferCopyOffsetWrite,
			long			bufferCopySize
	) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.copyRangeDataTo(
				bufferWrite,
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
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.copyRangeDataFrom(
				bufferRead,
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
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.clearAllData(
				bufferClearFormat,
				clearDataFormat,
				clearDataType,
				clearDataAddress
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
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.clearRangeData(
				bufferClearFormat,
				bufferClearOffset,
				bufferClearSize,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		);
	}

	@Override
	public long map(GLBufferMapAccessLegacy mapAccess) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		this.mapped		= true;
		this.access		= mapAccess;
		this.mapLength	= size;
		this.mapOffset	= 0;

		return bufferHelper.map(mapAccess);
	}

	@Override
	public long mapRange(
			long				mapOffset,
			long				mapLength,
			GLBufferMapAccess	mapAccess
	) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		this.mapped			= true;
		this.accessFlags	= mapAccess;
		this.mapLength		= mapLength;
		this.mapOffset		= mapOffset;

		return bufferHelper.mapRange(
				mapOffset,
				mapLength,
				mapAccess
		);
	}

	@Override
	public void unmap() {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		this.mapped			= false;
		this.access			= GLBufferMapAccessLegacy	.INVALID;
		this.accessFlags	= GLBufferMapAccess			.of();
		this.mapLength		= 0;
		this.mapOffset		= 0;

		bufferHelper.unmap();
	}

	@Override
	public void flushMappedRange(long flushOffset, long flushLength) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.flushMappedRange(flushOffset, flushLength);
	}

	@Override
	public void downloadRangeData(
			long outOffset,
			long outSize,
			long outDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.downloadRangeData(
				outOffset,
				outSize,
				outDataAddress
		);
	}

	@Override
	public void bind(GLBufferType bufferType) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.bind(bufferType);
	}

	@Override
	public void bindBase(GLBufferBlockType bufferBlockType, int bindTargetIndex) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.bindBase(bufferBlockType, bindTargetIndex);
	}

	@Override
	public void bindRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferTargetIndex,
			long				bufferBindOffset,
			long				bufferBindSize
	) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.bindRange(
				bufferBlockType,
				bufferTargetIndex,
				bufferBindOffset,
				bufferBindSize
		);
	}

	@Override
	public void invalidateAllData() {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.invalidateAllData();
	}

	@Override
	public void invalidateRangeData(long bufferOffset, long bufferLength) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		bufferHelper.invalidateRangeData(bufferOffset, bufferLength);
	}

	@Override
	public IGLRawBufferView view(long offset, long length) {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (!setup) {
			throw new IllegalStateException("The buffer has not been setup yet.");
		}

		return new GLRawBufferView(this, offset, length);
	}

	@Override
	public void delete() {
		if (deleted) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		bufferHelper.delete();

		this.setup		= false;
		this.immutable	= false;
		this.deleted	= true;
	}

	@Override
	public int getBufferHandle() {
		return bufferHelper.getBufferHandle();
	}

	@Override
	public GLBufferType getBufferType() {
		return bufferType;
	}

	@Override
	public int getBufferSize() {
		return (int) size;
	}

	@Override
	public GLBufferStorageFlag getStorageFlag() {
		return storageFlags;
	}

	@Override
	public GLBufferMapAccess getAccessFlag() {
		return accessFlags;
	}

	@Override
	public GLBufferUsage getBufferUsage() {
		return usage;
	}

	@Override
	public GLBufferMapAccessLegacy getAccess() {
		return access;
	}

	@Override
	public boolean isDynamic() {
		return getStorageFlag().isDynamic();
	}

	@Override
	public boolean isImmutable() {
		return immutable;
	}

	@Override
	public boolean isPersistent() {
		return getAccessFlag().isPersistent();
	}

	@Override
	public boolean isMapped() {
		return mapped;
	}

	@Override
	public long getMapLength() {
		return mapLength;
	}

	@Override
	public long getMapOffset() {
		return mapOffset;
	}

	@Override
	public IDataRange getMapRange() {
		return new DataRange(mapOffset, mapLength);
	}

	@Override
	public long getOffset() {
		return 0;
	}

	@Override
	public long getLength() {
		return size;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public boolean isBuffer() {
		return !deleted;
	}
}
