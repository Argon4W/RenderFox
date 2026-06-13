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

import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;

public class GLBufferFunctionsCached implements IGLBufferFunctions {

	private final	IGLBufferFunctions	bufferFunctions;
	private			IGLBindingSource	bindingSource;

	public GLBufferFunctionsCached(IGLBufferFunctions bufferFunctions) {
		this.bufferFunctions	= bufferFunctions;
		this.bindingSource		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		bufferFunctions.initialize(device);
		
		bindingSource = device.getBindingSource();
	}

	@Override
	public int createBufferHandle(GLBufferType bufferType) {
		return bufferFunctions.createBufferHandle(bufferType);
	}

	@Override
	public void bufferStorage(
			int				bufferHandle,
			long			bufferSize,
			long			bufferDataAddress,
			int				storageFlags,
			GLBufferType	bufferType
	) {
		bufferFunctions.bufferStorage(
				bufferHandle,
				bufferSize,
				bufferDataAddress,
				storageFlags,
				bufferType
		);
	}

	@Override
	public void bufferData(
			int				bufferHandle,
			long			bufferDataSize,
			long			bufferDataAddress,
			int				bufferUsage,
			GLBufferType	bufferType
	) {
		bufferFunctions.bufferData(
				bufferHandle,
				bufferDataSize,
				bufferDataAddress,
				bufferUsage,
				bufferType
		);
	}

	@Override
	public void bufferSubData(
			int				bufferHandle,
			long			bufferDataOffset,
			long			bufferDataSize,
			long			bufferDataAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.bufferSubData(
				bufferHandle,
				bufferDataOffset,
				bufferDataSize,
				bufferDataAddress,
				bufferType
		);
	}

	@Override
	public void copyBufferSubData(
			int		bufferHandleRead,
			int		bufferHandleWrite,
			long	bufferCopyOffsetRead,
			long	bufferCopyOffsetWrite,
			long	bufferCopySize
	) {
		bufferFunctions.copyBufferSubData(
				bufferHandleRead,
				bufferHandleWrite,
				bufferCopyOffsetRead,
				bufferCopyOffsetWrite,
				bufferCopySize
		);
	}

	@Override
	public void clearBufferData(
			int				bufferHandle,
			int				bufferClearFormat,
			int				clearDataFormat,
			int				clearDataType,
			long			clearDataAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.clearBufferData(
				bufferHandle,
				bufferClearFormat,
				clearDataFormat,
				clearDataType,
				clearDataAddress,
				bufferType
		);
	}

	@Override
	public void clearBufferSubData(
			int				bufferHandle,
			int				bufferClearFormat,
			long			bufferClearOffset,
			long			bufferClearSize,
			int				clearDataFormat,
			int				clearDataType,
			long			clearDataAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.clearBufferSubData(
				bufferHandle,
				bufferClearFormat,
				bufferClearOffset,
				bufferClearSize,
				clearDataFormat,
				clearDataType,
				clearDataAddress,
				bufferType
		);
	}

	@Override
	public long mapBuffer(
			int				bufferHandle,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		return bufferFunctions.mapBuffer(
				bufferHandle,
				mapAccess,
				bufferType
		);
	}

	@Override
	public long mapBufferRange(
			int				bufferHandle,
			long			mapOffset,
			long			mapLength,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		return bufferFunctions.mapBufferRange(
				bufferHandle,
				mapOffset,
				mapLength,
				mapAccess,
				bufferType
		);
	}

	@Override
	public void unmapBuffer(int bufferHandle, GLBufferType bufferType) {
		bufferFunctions.unmapBuffer(bufferHandle, bufferType);
	}

	@Override
	public void flushMappedBufferRange(
			int				bufferHandle,
			long			flushOffset,
			long			flushLength,
			GLBufferType	bufferType
	) {
		bufferFunctions.flushMappedBufferRange(
				bufferHandle,
				flushOffset,
				flushLength,
				bufferType
		);
	}

	@Override
	public int getBufferParameteri(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		return bufferFunctions.getBufferParameteri(
				bufferHandle,
				bufferParameter,
				bufferType
		);
	}

	@Override
	public void getBufferParameteriv(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.getBufferParameteriv(
				bufferHandle,
				bufferParameter,
				outDataAddress,
				bufferType
		);
	}

	@Override
	public long getBufferParameteri64(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		return bufferFunctions.getBufferParameteri64(
				bufferHandle,
				bufferParameter,
				bufferType
		);
	}

	@Override
	public void getBufferParameteri64v(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.getBufferParameteri64v(
				bufferHandle,
				bufferParameter,
				outDataAddress,
				bufferType
		);
	}

	@Override
	public long getBufferPointer(
			int				bufferHandle,
			int				bufferPointer,
			GLBufferType	bufferType
	) {
		return bufferFunctions.getBufferPointer(
				bufferHandle,
				bufferPointer,
				bufferType
		);
	}

	@Override
	public void getBufferPointerv(
			int				bufferHandle,
			int				bufferPointer,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.getBufferPointerv(
				bufferHandle,
				bufferPointer,
				outDataAddress,
				bufferType
		);
	}

	@Override
	public void getBufferSubData(
			int				bufferHandle,
			long			outOffset,
			long			outSize,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.getBufferSubData(
				bufferHandle,
				outOffset,
				outSize,
				outDataAddress,
				bufferType
		);
	}

	@Override
	public boolean isBuffer(int bufferHandle) {
		return bufferFunctions.isBuffer(bufferHandle);
	}

	@Override
	public void bindBuffer(int bufferTarget, int bufferHandle) {
		var bufferType = GLBufferType.fromTypeConstant(bufferTarget);

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (bindingSource.getBoundBuffer(bufferType) == bufferHandle) {
			return;
		}

		bufferFunctions.bindBuffer(bufferTarget, bufferHandle);
	}

	@Override
	public void bindBufferBase(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			GLBufferType	bufferType
	) {
		var bufferBlockType = GLBufferBlockType.fromTypeConstant(bufferTargetIndex);

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferBlockType.");
		}

		if (		bindingSource.getBoundBuffer		(bufferBlockType, bufferTargetIndex) == bufferHandle
				&&	bindingSource.getBoundBufferOffset	(bufferBlockType, bufferTargetIndex) == 0
				&&	bindingSource.getBoundBufferLength	(bufferBlockType, bufferTargetIndex) == 0
		) {
			return;
		}

		bufferFunctions.bindBufferBase(
				bufferTarget,
				bufferTargetIndex,
				bufferHandle,
				bufferType
		);
	}

	@Override
	public void bindBufferRange(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			long			bufferBindOffset,
			long			bufferBindSize,
			GLBufferType	bufferType
	) {
		var bufferBlockType = GLBufferBlockType.fromTypeConstant(bufferTargetIndex);

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferBlockType.");
		}

		if (		bindingSource.getBoundBuffer		(bufferBlockType, bufferTargetIndex) == bufferHandle
				&&	bindingSource.getBoundBufferOffset	(bufferBlockType, bufferTargetIndex) == bufferBindOffset
				&&	bindingSource.getBoundBufferLength	(bufferBlockType, bufferTargetIndex) == bufferBindSize
		) {
			return;
		}

		bufferFunctions.bindBufferRange(
				bufferTarget,
				bufferTargetIndex,
				bufferHandle,
				bufferBindOffset,
				bufferBindSize,
				bufferType
		);
	}

	@Override
	public void bindBuffersBase(
			int				bufferTarget,
			int				bufferTargetFirstIndex,
			int				bufferCounts,
			long			bufferHandlesAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.bindBuffersBase(
				bufferTarget,
				bufferTargetFirstIndex,
				bufferCounts,
				bufferHandlesAddress,
				bufferType
		);
	}

	@Override
	public void bindBuffersRange(
			int				bufferTarget,
			int				bufferTargetFirstIndex,
			int				bufferCounts,
			long			bufferHandlesAddress,
			long			bufferOffsetsAddress,
			long			bufferLengthsAddress,
			GLBufferType	bufferType
	) {
		bufferFunctions.bindBuffersRange(
				bufferTarget,
				bufferTargetFirstIndex,
				bufferCounts,
				bufferHandlesAddress,
				bufferOffsetsAddress,
				bufferLengthsAddress,
				bufferType
		);
	}

	@Override
	public void invalidateBufferData(int bufferHandle, GLBufferType bufferType) {
		bufferFunctions.invalidateBufferData(bufferHandle, bufferType);
	}

	@Override
	public void invalidateBufferSubData(
			int				bufferHandle,
			long			bufferOffset,
			long			bufferLength,
			GLBufferType	bufferType
	) {
		bufferFunctions.invalidateBufferSubData(
				bufferHandle,
				bufferOffset,
				bufferLength,
				bufferType
		);
	}

	@Override
	public void deleteBuffer(int bufferHandle) {
		bufferFunctions.deleteBuffer(bufferHandle);
	}
}
