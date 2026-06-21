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

package com.github.argon4w.renderfox.opengl.buffer.object.mutable.mapped;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;
import com.github.argon4w.renderfox.opengl.buffer.object.wrapped.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public class GLMappedBufferLegacy extends AbstractGLMappedBuffer {

	private IGLBufferDataView<?> dataView;

	public GLMappedBufferLegacy(
			GLBufferContext		bufferContext,
			long				bufferDataAddress,
			long				bufferOffset,
			long				bufferLength,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		super(
				bufferContext,
				bufferDataAddress,
				bufferOffset,
				bufferLength,
				bufferType,
				storageFlag,
				mapAccess
		);

		this.dataView = null;
	}

	@Override
	protected void map() {
		dataView = buffer.mapRangeData(buffer, mapAccess);
	}

	@Override
	protected void unmap() {
		dataView.close();
		dataView = null;
	}

	@Override
	public IDataRange flush(IDataRange range) {
		unmap();
		return range;
	}

	@Override
	protected IGLBufferDataView<?> getDataView() {
		return dataView;
	}

	@Override
	public void open() {
		map();
	}

	@Override
	public void clearAllData(byte value) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearAllData(value);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorFloat clearColor) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearAllData(internalFormat, clearColor);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, ColorInt clearColor) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearAllData(internalFormat, clearColor);
	}

	@Override
	public void clearAllData(
			GLInternalFormat	internalFormat,
			float				clearColorDepth,
			int					clearColorStencil
	) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearAllData(
				internalFormat,
				clearColorDepth,
				clearColorStencil
		);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, float clearColorDepth) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearAllData(internalFormat, clearColorDepth);
	}

	@Override
	public void clearAllData(GLInternalFormat internalFormat, int clearColorStencil) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearAllData(internalFormat, clearColorStencil);
	}

	@Override
	public void clearRangeData(IDataRange clearRangeElement, byte value) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearRangeData(clearRangeElement, value);
	}

	@Override
	public void clearRangeData(
			GLInternalFormat	internalFormat,
			IDataRange			clearRangeElement,
			ColorFloat			clearColor
	) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearRangeData(
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
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearRangeData(
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
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearRangeData(
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
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearRangeData(
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
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.clearRangeData(
				internalFormat,
				clearRangeElement,
				clearColorStencil
		);
	}

	@Override
	public IGLRawBufferView getRawBuffer() {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		return super.getRawBuffer();
	}

	@Override
	public void bind(GLBufferType bufferType) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.bind(bufferType);
	}

	@Override
	public void bindBase(GLBufferBlockType bufferBlockType, int bindTargetIndex) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.bindBase(bufferBlockType, bindTargetIndex);
	}

	@Override
	public void bindRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferTargetIndex,
			long				bufferBindOffset,
			long				bufferBindSize
	) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.bindRange(
				bufferBlockType,
				bufferTargetIndex,
				bufferBindOffset,
				bufferBindSize
		);
	}

	@Override
	public void copyRangeDataTo(
			IGLBufferBase bufferWrite,
			IDataRange		bufferCopyRangeRead,
			IDataRange		bufferCopyRangeWrite
	) {
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.copyRangeDataTo(
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
		if (isMapped()) {
			throw new IllegalStateException("Buffer is occupied.");
		}

		super.copyRangeDataFrom(
				bufferRead,
				bufferCopyRangeRead,
				bufferCopyRangeWrite
		);
	}
}
