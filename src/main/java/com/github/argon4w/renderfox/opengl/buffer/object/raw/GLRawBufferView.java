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
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.util.RangeUtils;

public class GLRawBufferView implements IGLRawBufferView {

	private final IGLRawBufferView	buffer;
	private final long				offset;
	private final long				length;

	public GLRawBufferView(
			IGLRawBufferView	buffer,
			long				offset,
			long				length
	) {
		this.buffer		= buffer;
		this.offset		= offset;
		this.length		= length;
	}

	@Override
	public IGLRawBufferView view(long offset, long length) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative.");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative.");
		}

		if (buffer.isDeleted()) {
			throw new IllegalStateException("The buffer has been deleted.");
		}

		if (offset + length > this.length) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the view length.");
		}

		return new GLRawBufferView(
				this.buffer,
				this.offset + offset,
				length
		);
	}

	@Override
	public void uploadRangeData(
			long bufferDataOffset,
			long bufferDataSize,
			long bufferDataAddress
	) {
		if (bufferDataSize > length - bufferDataOffset) {
			throw new IllegalArgumentException("Cannot upload data with size that exceeds the range of the buffer view.");
		}

		buffer.uploadRangeData(
				bufferDataOffset + offset,
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
		if (bufferCopySize > length - bufferCopyOffsetRead) {
			throw new IllegalArgumentException("Cannot copy data with size that exceeds the range of the buffer view.");
		}

		buffer.copyRangeDataTo(
				bufferWrite,
				bufferCopyOffsetRead + offset,
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
		if (bufferCopySize > length - bufferCopyOffsetWrite) {
			throw new IllegalArgumentException("Cannot copy data with size that exceeds the range of the buffer view.");
		}

		buffer.copyRangeDataFrom(
				bufferRead,
				bufferCopyOffsetRead,
				bufferCopyOffsetWrite + offset,
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
		buffer.clearRangeData(
				bufferClearFormat,
				offset,
				length,
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
		if (bufferClearSize > length - bufferClearOffset) {
			throw new IllegalArgumentException("Cannot clear data with size that exceeds the range of the buffer view.");
		}

		buffer.clearRangeData(
				bufferClearFormat,
				bufferClearOffset + offset,
				bufferClearSize,
				clearDataFormat,
				clearDataType,
				clearDataAddress
		);
	}

	@Override
	public long map(GLBufferMapAccessLegacy mapAccess) {
		return buffer.map(mapAccess) + this.offset;
	}

	@Override
	public long mapRange(
			long				mapOffset,
			long				mapLength,
			GLBufferMapAccess	mapAccess
	) {
		if (mapLength > length - mapOffset) {
			throw new IllegalArgumentException("Cannot map data with size that exceeds the range of the buffer view.");
		}

		return buffer.mapRange(
				mapOffset + offset,
				mapLength,
				mapAccess
		);
	}

	@Override
	public void unmap() {
		buffer.unmap();
	}

	@Override
	public void flushMappedRange(long flushOffset, long flushLength) {
		buffer.flushMappedRange(flushOffset, flushLength);
	}

	@Override
	public void downloadRangeData(
			long outOffset,
			long outSize,
			long outDataAddress
	) {
		if (outSize > length - outOffset) {
			throw new IllegalArgumentException("Cannot download data with size that exceeds the range of the buffer view.");
		}

		buffer.downloadRangeData(
				outOffset + offset,
				outSize,
				outDataAddress
		);
	}

	@Override
	public boolean isBuffer() {
		return buffer.isBuffer();
	}

	@Override
	public void bind(GLBufferType bufferType) {
		buffer.bind(bufferType);
	}

	@Override
	public void bindBase(GLBufferBlockType bufferBlockType, int bindTargetIndex) {
		buffer.bindRange(
				bufferBlockType,
				bindTargetIndex,
				offset,
				length
		);
	}

	@Override
	public void bindRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferTargetIndex,
			long				bufferBindOffset,
			long				bufferBindSize
	) {
		if (bufferBindSize > length - bufferBindOffset) {
			throw new IllegalArgumentException("Cannot bind buffer range with size that exceeds the range of the buffer view.");
		}

		buffer.bindRange(
				bufferBlockType,
				bufferTargetIndex,
				bufferBindOffset + offset,
				bufferBindSize
		);
	}

	@Override
	public void invalidateAllData() {
		buffer.invalidateRangeData(
				offset,
				length
		);
	}

	@Override
	public void invalidateRangeData(long bufferOffset, long bufferLength) {
		if (bufferLength > length - bufferOffset) {
			throw new IllegalArgumentException("Cannot invalidate data with size that exceeds the range of the buffer view.");
		}

		buffer.invalidateRangeData(
				bufferOffset + offset,
				bufferLength
		);
	}

	@Override
	public int getBufferHandle() {
		return buffer.getBufferHandle();
	}

	@Override
	public GLBufferType getBufferType() {
		return buffer.getBufferType();
	}

	@Override
	public int getBufferSize() {
		return (int) length;
	}

	@Override
	public boolean isImmutable() {
		return buffer.isImmutable();
	}

	@Override
	public boolean isMapped() {
		return buffer.isMapped() && RangeUtils.isOverlapped(this, buffer.getMapRange());
	}

	@Override
	public long getMapOffset() {
		throw new UnsupportedOperationException("Cannot get map offset from buffer view.");
	}

	@Override
	public long getMapLength() {
		throw new UnsupportedOperationException("Cannot get map length from buffer view.");
	}

	@Override
	public IDataRange getMapRange() {
		throw new UnsupportedOperationException("Cannot get map range from buffer view.");
	}

	@Override
	public GLBufferStorageFlag getStorageFlag() {
		return buffer.getStorageFlag();
	}

	@Override
	public GLBufferMapAccess getAccessFlag() {
		return buffer.getAccessFlag();
	}

	@Override
	public GLBufferUsage getBufferUsage() {
		return buffer.getBufferUsage();
	}

	@Override
	public GLBufferMapAccessLegacy getAccess() {
		return buffer.getAccess();
	}

	@Override
	public boolean isPersistent() {
		return buffer.isPersistent();
	}

	@Override
	public boolean isDynamic() {
		return buffer.isDynamic();
	}

	@Override
	public boolean isDeleted() {
		return buffer.isDeleted();
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public long getLength() {
		return length;
	}

	@Override
	public void delete() {
		throw new UnsupportedOperationException("Cannot delete a view of buffer.");
	}
}
