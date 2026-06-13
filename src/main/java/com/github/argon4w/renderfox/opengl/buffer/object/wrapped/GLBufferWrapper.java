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

package com.github.argon4w.renderfox.opengl.buffer.object.wrapped;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public abstract class GLBufferWrapper implements IGLBuffer {

	public abstract IGLBuffer getBuffer();

	@Override
	public IGLBufferDataView<?> mapRangeData(IDataRange dataRange, GLBufferMapAccess mapAccess) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().mapRangeData(dataRange, mapAccess);
	}

	@Override
	public void clearAllData(byte value) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearAllData(value);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorFloat clearColor) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearAllData(internalFormat, clearColor);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorInt clearColor) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearAllData(internalFormat, clearColor);
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

		getBuffer().clearAllData(
				internalFormat,
				clearColorDepth,
				clearColorStencil
		);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, float clearColorDepth) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearAllData(internalFormat, clearColorDepth);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, int clearColorStencil) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearAllData(internalFormat, clearColorStencil);
	}

	@Override
	public void clearRangeData(IDataRange clearRangeElement, byte value) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().clearRangeData(clearRangeElement, value);
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

		getBuffer().clearRangeData(
				internalFormat,
				clearRangeElement,
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

		getBuffer().clearRangeData(
				internalFormat,
				clearRangeElement,
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

		getBuffer().clearRangeData(
				internalFormat,
				clearRangeElement,
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

		getBuffer().clearRangeData(
				internalFormat,
				clearRangeElement,
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

		getBuffer().clearRangeData(
				internalFormat,
				clearRangeElement,
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

		getBuffer().bindBase(bufferBlockType, bindTargetIndex);
	}

	@Override
	public void bindRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferTargetIndex,
			long				bufferBindOffset,
			long				bufferBindSize
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().bindRange(
				bufferBlockType,
				bufferTargetIndex,
				bufferBindOffset,
				bufferBindSize
		);
	}

	@Override
	public void copyRangeDataTo(
			IGLBufferBase	bufferWrite,
			IDataRange		bufferCopyRangeRead,
			IDataRange		bufferCopyRangeWrite
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().copyRangeDataTo(
				bufferWrite,
				bufferCopyRangeRead,
				bufferCopyRangeWrite
		);
	}

	@Override
	public void copyRangeDataFrom(
			IGLBufferBase	bufferRead,
			IDataRange		bufferCopyRangeRead,
			IDataRange		bufferCopyRangeWrite
	) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().copyRangeDataFrom(
				bufferRead,
				bufferCopyRangeRead,
				bufferCopyRangeWrite
		);
	}

	@Override
	public IDataRange withOffset(long newOffset) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().withOffset(newOffset);
	}

	@Override
	public IDataRange withLength(long newLength) {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().withLength(newLength);
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
	public int getBufferSize() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getBufferSize();
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
	public long getOffset() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getOffset();
	}

	@Override
	public long getLength() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		return getBuffer().getLength();
	}

	@Override
	public void delete() {
		if (getBuffer() == null) {
			throw new IllegalStateException("buffer cannot be null.");
		}

		getBuffer().delete();
	}
}
