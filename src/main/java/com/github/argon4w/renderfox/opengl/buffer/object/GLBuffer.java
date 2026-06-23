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

package com.github.argon4w.renderfox.opengl.buffer.object;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.AddressDataView;
import com.github.argon4w.renderfox.data.view.IDataViewDecorator;
import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferBase;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public class GLBuffer implements IGLBuffer {

	private final IGLRawBufferView buffer;

	public GLBuffer(IGLRawBufferView buffer) {
		this.buffer = buffer;
	}

	@Override
	public IGLBufferDataView<?> mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess) {
		if (mapDataRange == null) {
			throw new IllegalArgumentException("MapDataRange cannot be null");
		}

		if (mapAccess == null) {
			throw new IllegalArgumentException("MapAccess cannot be null.");
		}

		if (mapAccess.isEmpty()) {
			throw new IllegalArgumentException("MapAccess cannot be empty.");
		}

		var mapOffset = mapDataRange.getOffset();
		var mapLength = mapDataRange.getLength();

		var address = buffer.mapRange(
				mapOffset,
				mapLength,
				mapAccess
		);

		var dataView = AddressDataView.of(address, mapLength);

		dataView = !mapAccess.canWrite	() ? dataView.as(IDataViewDecorator.READ_ONLY)	: dataView;
		dataView = !mapAccess.canRead	() ? dataView.as(IDataViewDecorator.WRITE_ONLY)	: dataView;

		return new GLBufferDataView(
				this,
				dataView,
				mapLength
		);
	}

	@Override
	public void clearAllData(byte value) {
		try (var dataView = StackDataView.aByte(value)) {
			buffer.clearAllData(
					GLInternalFormat.R8UI,
					GLFormat		.RED_INTEGER,
					GLDataType		.BYTE,
					dataView		.address()
			);
		}
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorFloat clearColor) {
		if (clearColor == null) {
			throw new IllegalArgumentException("ClearColor cannot be null.");
		}

		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.ofFloats(4L)) {
			buffer.clearAllData(
					internalFormat,
					internalFormat.getFormat(),
					internalFormat.getType	(),
					dataView
							.putFloat	(0L,	clearColor.getRed	())
							.putFloat	(4L,	clearColor.getGreen	())
							.putFloat	(8L,	clearColor.getBlue	())
							.putFloat	(12L,	clearColor.getAlpha	())
							.address	()
			);
		}
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorInt clearColor) {
		if (clearColor == null) {
			throw new IllegalArgumentException("ClearColor cannot be null.");
		}

		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.ofInts(4L)) {
			buffer.clearAllData(
					internalFormat,
					internalFormat.getFormat(),
					internalFormat.getType	(),
					dataView
							.putInt	(0L,	clearColor.getRed	())
							.putInt	(4L,	clearColor.getGreen	())
							.putInt	(8L,	clearColor.getBlue	())
							.putInt	(12L,	clearColor.getAlpha	())
							.address()
			);
		}
	}

	@Override
	public void clearAllData(
			GLInternalFormat	internalFormat,
			float				clearDepth,
			int					clearStencil
	) {
		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.of(8L)) {
			buffer.clearAllData(
					internalFormat,
					internalFormat.getFormat(),
					internalFormat.getType	(),
					dataView
							.putFloat	(0L, clearDepth)
							.putInt		(4L, clearStencil)
							.address	()
			);
		}
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, float clearDepth) {
		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.aFloat(clearDepth)) {
			buffer.clearAllData(
					internalFormat,
					internalFormat	.getFormat	(),
					internalFormat	.getType	(),
					dataView		.address	()
			);
		}
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, int clearStencil) {
		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.aInt(clearStencil)) {
			buffer.clearAllData(
					internalFormat,
					internalFormat	.getFormat	(),
					internalFormat	.getType	(),
					dataView		.address	()
			);
		}
	}

	@Override
	public void clearRangeData(IDataRange clearRangeElement, byte value) {
		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeClement cannot be null.");
		}

		try (var dataView = StackDataView.aByte(value)) {
			buffer.clearRangeData(
					GLInternalFormat.R8UI,
					GLInternalFormat.R8UI.getFormatSize() * clearRangeElement.getOffset(),
					GLInternalFormat.R8UI.getFormatSize() * clearRangeElement.getLength(),
					GLFormat		.RED_INTEGER,
					GLDataType		.BYTE,
					dataView		.address()
			);
		}
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			ColorFloat			clearColor
	) {
		if (clearColor == null) {
			throw new IllegalArgumentException("ClearColor cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeClement cannot be null.");
		}

		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.ofFloats(4L)) {
			buffer.clearRangeData(
					internalFormat,
					internalFormat.getFormatSize() * clearRangeElement.getOffset(),
					internalFormat.getFormatSize() * clearRangeElement.getLength(),
					internalFormat.getFormat	(),
					internalFormat.getType		(),
					dataView
							.putFloat	(0L,	clearColor.getRed	())
							.putFloat	(4L,	clearColor.getGreen	())
							.putFloat	(8L,	clearColor.getBlue	())
							.putFloat	(12L,	clearColor.getAlpha	())
							.address	()
			);
		}
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			ColorInt			clearColor
	) {
		if (clearColor == null) {
			throw new IllegalArgumentException("ClearColor cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeClement cannot be null.");
		}

		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.ofInts(4L)) {
			buffer.clearRangeData(
					internalFormat,
					internalFormat.getFormatSize() * clearRangeElement.getOffset(),
					internalFormat.getFormatSize() * clearRangeElement.getLength(),
					internalFormat.getFormat	(),
					internalFormat.getType		(),
					dataView
							.putInt	(0L,	clearColor.getRed	())
							.putInt	(4L,	clearColor.getGreen	())
							.putInt	(8L,	clearColor.getBlue	())
							.putInt	(12L,	clearColor.getAlpha	())
							.address()
			);
		}
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			float				clearDepth,
			int					clearStencil
	) {
		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeClement cannot be null.");
		}

		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.of(8L)) {
			buffer.clearRangeData(
					internalFormat,
					internalFormat.getFormatSize() * clearRangeElement.getOffset(),
					internalFormat.getFormatSize() * clearRangeElement.getLength(),
					internalFormat.getFormat	(),
					internalFormat.getType		(),
					dataView
							.putFloat	(0L, clearDepth)
							.putInt		(4L, clearStencil)
							.address	()
			);
		}
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			float				clearDepth
	) {
		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeClement cannot be null.");
		}

		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.aFloat(clearDepth)) {
			buffer.clearRangeData(
					internalFormat,
					internalFormat	.getFormatSize	() * clearRangeElement.getOffset(),
					internalFormat	.getFormatSize	() * clearRangeElement.getLength(),
					internalFormat	.getFormat		(),
					internalFormat	.getType		(),
					dataView		.address		()
			);
		}
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			int					clearStencil
	) {
		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeClement cannot be null.");
		}

		if (internalFormat == null) {
			throw new IllegalArgumentException("InternalFormat cannot be null.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid internalFormat.");
		}

		try (var dataView = StackDataView.aInt(clearStencil)) {
			buffer.clearRangeData(
					internalFormat,
					internalFormat	.getFormatSize	() * clearRangeElement.getOffset(),
					internalFormat	.getFormatSize	() * clearRangeElement.getLength(),
					internalFormat	.getFormat		(),
					internalFormat	.getType		(),
					dataView		.address		()
			);
		}
	}

	@Override
	public void copyRangeDataTo(
			IGLRawBufferBase	bufferWrite,
			IDataRange			bufferCopyRangeRead,
			IDataRange			bufferCopyRangeWrite
	) {
		if (bufferCopyRangeRead == null) {
			throw new IllegalArgumentException("BufferCopyRangeRead cannot be null.");
		}

		if (bufferCopyRangeWrite == null) {
			throw new IllegalArgumentException("BufferCopyRangeWrite cannot be null.");
		}

		if (bufferWrite == null) {
			throw new IllegalArgumentException("BufferWrite cannot be null.");
		}

		buffer.copyRangeDataTo(
				bufferWrite,
				bufferCopyRangeRead	.getOffset(),
				bufferCopyRangeWrite.getOffset(),
				bufferCopyRangeRead	.getLength()
		);
	}

	@Override
	public void copyRangeDataFrom(
			IGLRawBufferBase	bufferRead,
			IDataRange			bufferCopyRangeRead,
			IDataRange			bufferCopyRangeWrite
	) {
		if (bufferCopyRangeRead == null) {
			throw new IllegalArgumentException("BufferCopyRangeRead cannot be null.");
		}

		if (bufferCopyRangeWrite == null) {
			throw new IllegalArgumentException("BufferCopyRangeWrite cannot be null.");
		}

		if (bufferRead == null) {
			throw new IllegalArgumentException("BufferRead cannot be null.");
		}

		buffer.copyRangeDataFrom(
				bufferRead,
				bufferCopyRangeRead	.getOffset(),
				bufferCopyRangeWrite.getOffset(),
				bufferCopyRangeRead	.getLength()
		);
	}

	@Override
	public void bind(GLBufferType bufferType) {
		buffer.bind(bufferType);
	}

	@Override
	public void bindBase(GLBufferBlockType bufferBlockType, int bindTargetIndex) {
		buffer.bindBase(bufferBlockType, bindTargetIndex);
	}

	@Override
	public void bindRange(
			GLBufferBlockType	bufferBlockType,
			IDataRange			bufferTargetRange,
			int					bufferTargetIndex
	) {
		if (bufferTargetRange == null) {
			throw new IllegalArgumentException("BufferTargetRange cannot be null.");
		}

		buffer.bindRange(
				bufferBlockType,
				bufferTargetIndex,
				bufferTargetRange.getOffset(),
				bufferTargetRange.getLength()
		);
	}

	@Override
	public IGLBuffer view(IDataRange viewRange) {
		return new GLBuffer(
				buffer.view(
						viewRange.getOffset(),
						viewRange.getLength()
				)
		);
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		return buffer;
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
		return buffer.getBufferSize();
	}

	@Override
	public boolean isImmutable() {
		return buffer.isImmutable();
	}

	@Override
	public boolean isMapped() {
		return buffer.isMapped();
	}

	@Override
	public long getMapOffset() {
		return buffer.getMapOffset();
	}

	@Override
	public long getMapLength() {
		return buffer.getMapLength();
	}

	@Override
	public IDataRange getMapRange() {
		return buffer.getMapRange();
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
		return buffer.getOffset();
	}

	@Override
	public long getLength() {
		return buffer.getLength();
	}

	@Override
	public void delete() {
		buffer.delete();
	}
}
