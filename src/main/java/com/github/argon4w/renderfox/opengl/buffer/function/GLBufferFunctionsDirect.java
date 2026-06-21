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
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.dsa.IDirectStateAccess;
import org.lwjgl.opengl.ARBMultiBind;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;

public class GLBufferFunctionsDirect implements IGLBufferFunctions {

	private IDirectStateAccess	directStateAccess;
	private IGLBindingSource	bindingSource;

	public GLBufferFunctionsDirect() {
		this.directStateAccess	= null;
		this.bindingSource		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		directStateAccess	= device.getDirectStateAccess	();
		bindingSource		= device.getBindingSource		();
	}

	@Override
	public int createBufferHandle(GLBufferType bufferType) {
		return directStateAccess.createBufferHandle(bufferType);
	}

	@Override
	public void bufferStorage(
			int				bufferHandle,
			long			bufferSize,
			long			bufferDataAddress,
			int				storageFlags,
			GLBufferType	bufferType
	) {
		directStateAccess.bufferStorage(
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
		directStateAccess.bufferData(
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
		directStateAccess.bufferSubData(
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
		directStateAccess.copyBufferSubData(
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
		directStateAccess.clearBufferData(
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
		directStateAccess.clearBufferSubData(
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
		return directStateAccess.mapBuffer(
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
		return directStateAccess.mapBufferRange(
				bufferHandle,
				mapOffset,
				mapLength,
				mapAccess,
				bufferType
		);
	}

	@Override
	public void unmapBuffer(int bufferHandle, GLBufferType bufferType) {
		directStateAccess.unmapBuffer(bufferHandle, bufferType);
	}

	@Override
	public void flushMappedBufferRange(
			int				bufferHandle,
			long			flushOffset,
			long			flushLength,
			GLBufferType	bufferType
	) {
		directStateAccess.flushMappedBufferRange(
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
		return directStateAccess.getBufferParameteri(
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
		directStateAccess.getBufferParameteriv(
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
		return directStateAccess.getBufferParameteri64(
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
		directStateAccess.getBufferParameteri64v(
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
		return directStateAccess.getBufferPointer(
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
		directStateAccess.getBufferPointerv(
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
		directStateAccess.getBufferSubData(
				bufferHandle,
				outOffset,
				outSize,
				outDataAddress,
				bufferType
		);
	}

	@Override
	public boolean isBuffer(int bufferHandle) {
		return GL15.glIsBuffer(bufferHandle);
	}

	@Override
	public void bindBuffer(int bufferTarget, int bufferHandle) {
		GL15.glBindBuffer(bufferTarget, bufferHandle);

		bindingSource.bindBuffer(bufferTarget, bufferHandle);
	}

	@Override
	public void bindBufferBase(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			GLBufferType	bufferType
	) {
		GL30.glBindBufferBase(
				bufferTarget,
				bufferTargetIndex,
				bufferHandle
		);

		bindingSource.bindBufferBase(
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
		GL30.glBindBufferRange(
				bufferTarget,
				bufferTargetIndex,
				bufferHandle,
				bufferBindOffset,
				bufferBindSize
		);

		bindingSource.bindBufferRange(
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
		ARBMultiBind.nglBindBuffersBase(
				bufferTarget,
				bufferTargetFirstIndex,
				bufferCounts,
				bufferHandlesAddress
		);

		bindingSource.bindBuffersBase(
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
		ARBMultiBind.nglBindBuffersBase(
				bufferTarget,
				bufferTargetFirstIndex,
				bufferCounts,
				bufferHandlesAddress
		);

		bindingSource.bindBuffersRange(
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
		GL43.glInvalidateBufferData(bufferHandle);
	}

	@Override
	public void invalidateBufferSubData(
			int				bufferHandle,
			long			bufferOffset,
			long			bufferLength,
			GLBufferType	bufferType
	) {
		GL43.glInvalidateBufferSubData(
				bufferHandle,
				bufferOffset,
				bufferLength
		);
	}

	@Override
	public void deleteBuffer(int bufferHandle) {
		GL15.glDeleteBuffers(bufferHandle);
	}

	public static IGLBufferFunctions of() {
		return new GLBufferFunctionsDirect();
	}
}
