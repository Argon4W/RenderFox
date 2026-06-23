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

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
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
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public abstract class GLBufferView implements IGLBuffer {

	public abstract IGLBuffer	getBuffer();
	public abstract long		getOffset();
	public abstract long		getLength();

	@Override
	public IGLBufferDataView<?> mapRangeData(IDataRange dataRange, GLBufferMapAccess mapAccess) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (dataRange == null) {
			throw new IllegalArgumentException("DataRange cannot be null.");
		}

		if (dataRange.getOffset() + dataRange.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		return getBuffer().mapRangeData(
				new DataRange(
						getOffset() +	dataRange.getOffset(),
										dataRange.getLength()
				),
				mapAccess
		);
	}

	@Override
	public void clearAllData(byte value) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearRangeData(this, value);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorFloat clearColor) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				this,
				clearColor
		);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorInt clearColor) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				this,
				clearColor
		);
	}

	@Override
	public void clearAllData(
			GLInternalFormat	internalFormat,
			float				clearColorDepth,
			int					clearColorStencil
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				this,
				clearColorDepth,
				clearColorStencil
		);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, float clearColorDepth) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				this,
				clearColorDepth
		);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, int clearColorStencil) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				this,
				clearColorStencil
		);
	}

	@Override
	public void clearRangeData(IDataRange clearRangeElement, byte value) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeElement cannot be null.");
		}

		if (clearRangeElement.getOffset() + clearRangeElement.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().clearRangeData(
				new DataRange(
						getOffset() +	clearRangeElement.getOffset(),
										clearRangeElement.getLength()
				),
				value
		);
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			ColorFloat			clearColor
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeElement cannot be null.");
		}

		if (clearRangeElement.getOffset() + clearRangeElement.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				new DataRange(
						getOffset() +	clearRangeElement.getOffset(),
										clearRangeElement.getLength()
				),
				clearColor
		);
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			ColorInt			clearColor
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeElement cannot be null.");
		}

		if (clearRangeElement.getOffset() + clearRangeElement.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				new DataRange(
						getOffset() +	clearRangeElement.getOffset(),
										clearRangeElement.getLength()
				),
				clearColor
		);
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			float				clearColorDepth,
			int					clearColorStencil
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeElement cannot be null.");
		}

		if (clearRangeElement.getOffset() + clearRangeElement.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				new DataRange(
						getOffset() +	clearRangeElement.getOffset(),
										clearRangeElement.getLength()
				),
				clearColorDepth,
				clearColorStencil
		);
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			float				clearColorDepth
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeElement cannot be null.");
		}

		if (clearRangeElement.getOffset() + clearRangeElement.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				new DataRange(
						getOffset() +	clearRangeElement.getOffset(),
										clearRangeElement.getLength()
				),
				clearColorDepth
		);
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			int					clearColorStencil
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (clearRangeElement == null) {
			throw new IllegalArgumentException("ClearRangeElement cannot be null.");
		}

		if (clearRangeElement.getOffset() + clearRangeElement.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().clearRangeData(
				internalFormat,
				new DataRange(
						getOffset() +	clearRangeElement.getOffset(),
										clearRangeElement.getLength()
				),
				clearColorStencil
		);
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getRawBuffer();
	}

	@Override
	public void bind(GLBufferType bufferType) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().bind(bufferType);
	}

	@Override
	public void bindBase(GLBufferBlockType bufferBlockType, int bindTargetIndex) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().bindRange(
				bufferBlockType,
				this,
				bindTargetIndex
		);
	}

	@Override
	public void bindRange(
			GLBufferBlockType	bufferBlockType,
			IDataRange			bufferTargetRange,
			int					bufferTargetIndex
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (bufferTargetRange == null) {
			throw new IllegalArgumentException("BufferTargetRange cannot be null.");
		}

		if (bufferTargetRange.getOffset() + bufferTargetRange.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().bindRange(
				bufferBlockType,
				new DataRange(
						getOffset() +	bufferTargetRange.getOffset(),
										bufferTargetRange.getLength()
				),
				bufferTargetIndex
		);
	}

	@Override
	public void copyRangeDataTo(
			IGLRawBufferBase	bufferWrite,
			IDataRange			bufferCopyRangeRead,
			IDataRange			bufferCopyRangeWrite
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (bufferCopyRangeRead == null) {
			throw new IllegalArgumentException("BufferCopyRangeRead cannot be null.");
		}

		if (bufferCopyRangeRead.getOffset() + bufferCopyRangeRead.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().copyRangeDataTo(
				bufferWrite,
				new DataRange(
						getOffset() +	bufferCopyRangeRead.getOffset(),
										bufferCopyRangeRead.getLength()
				),
				bufferCopyRangeWrite
		);
	}

	@Override
	public void copyRangeDataFrom(
			IGLRawBufferBase	bufferRead,
			IDataRange			bufferCopyRangeRead,
			IDataRange			bufferCopyRangeWrite
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		if (bufferCopyRangeRead == null) {
			throw new IllegalArgumentException("BufferCopyRangeRead cannot be null.");
		}

		if (bufferCopyRangeRead.getOffset() + bufferCopyRangeRead.getLength() > getLength()) {
			throw new IllegalArgumentException("Offset + length cannot be greater than the value of length of the wrapper.");
		}

		getBuffer().copyRangeDataFrom(
				bufferRead,
				new DataRange(
						getOffset() +	bufferCopyRangeRead.getOffset(),
										bufferCopyRangeRead.getLength()
				),
				bufferCopyRangeWrite
		);
	}

	@Override
	public int getBufferHandle() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getBufferHandle();
	}

	@Override
	public GLBufferType getBufferType() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getBufferType();
	}

	@Override
	public boolean isImmutable() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().isImmutable();
	}

	@Override
	public boolean isMapped() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().isMapped();
	}

	@Override
	public long getMapOffset() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getMapOffset();
	}

	@Override
	public long getMapLength() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getMapLength();
	}

	@Override
	public IDataRange getMapRange() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getMapRange();
	}

	@Override
	public GLBufferStorageFlag getStorageFlag() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getStorageFlag();
	}

	@Override
	public GLBufferMapAccess getAccessFlag() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getAccessFlag();
	}

	@Override
	public GLBufferUsage getBufferUsage() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getBufferUsage();
	}

	@Override
	public GLBufferMapAccessLegacy getAccess() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getAccess();
	}

	@Override
	public boolean isPersistent() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().isPersistent();
	}

	@Override
	public boolean isDynamic() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().isDynamic();
	}

	@Override
	public boolean isDeleted() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().isDeleted();
	}

	@Override
	public void delete() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().delete();
	}

	@Override
	public int getBufferSize() {
		return (int) getLength();
	}
}
