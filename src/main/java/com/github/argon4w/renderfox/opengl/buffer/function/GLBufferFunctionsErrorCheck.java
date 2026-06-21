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

import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.error.GLErrorChecker;

public class GLBufferFunctionsErrorCheck implements IGLBufferFunctions {

	private final	IGLBufferFunctions	bufferFunctions;
	private			GLErrorChecker		errorChecker;

	public GLBufferFunctionsErrorCheck(IGLBufferFunctions bufferFunctions) {
		this.bufferFunctions = bufferFunctions;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		bufferFunctions.initialize(device);

		errorChecker = device.getErrorChecker();
	}

	@Override
	public int createBufferHandle(GLBufferType bufferType) {
		return errorChecker.runChecked("createBuffer", () -> bufferFunctions.createBufferHandle(bufferType));
	}

	@Override
	public void bufferStorage(
			int				bufferHandle,
			long			bufferSize,
			long			bufferDataAddress,
			int				storageFlags,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("bufferStorage", () -> bufferFunctions.bufferStorage(
				bufferHandle,
				bufferSize,
				bufferDataAddress,
				storageFlags,
				bufferType
		));
	}

	@Override
	public void bufferData(
			int				bufferHandle,
			long			bufferDataSize,
			long			bufferDataAddress,
			int				bufferUsage,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("bufferData", () -> bufferFunctions.bufferData(
				bufferHandle,
				bufferDataSize,
				bufferDataAddress,
				bufferUsage,
				bufferType
		));
	}

	@Override
	public void bufferSubData(
			int				bufferHandle,
			long			bufferDataOffset,
			long			bufferDataSize,
			long			bufferDataAddress,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("bufferSubData", () -> bufferFunctions.bufferSubData(
				bufferHandle,
				bufferDataOffset,
				bufferDataSize,
				bufferDataAddress,
				bufferType
		));
	}

	@Override
	public void copyBufferSubData(
			int		bufferHandleRead,
			int		bufferHandleWrite,
			long	bufferCopyOffsetRead,
			long	bufferCopyOffsetWrite,
			long	bufferCopySize
	) {
		errorChecker.runChecked("copyBufferSubData", () -> bufferFunctions.copyBufferSubData(
				bufferHandleRead,
				bufferHandleWrite,
				bufferCopyOffsetRead,
				bufferCopyOffsetWrite,
				bufferCopySize
		));
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
		errorChecker.runChecked("clearBufferData", () -> bufferFunctions.clearBufferData(
				bufferHandle,
				bufferClearFormat,
				clearDataFormat,
				clearDataType,
				clearDataAddress,
				bufferType
		));
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
		errorChecker.runChecked("clearBufferSubData", () -> bufferFunctions.clearBufferSubData(
				bufferHandle,
				bufferClearFormat,
				bufferClearOffset,
				bufferClearSize,
				clearDataFormat,
				clearDataType,
				clearDataAddress,
				bufferType
		));
	}

	@Override
	public long mapBuffer(
			int				bufferHandle,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		return errorChecker.runChecked("mapBuffer", () -> bufferFunctions.mapBuffer(
				bufferHandle,
				mapAccess,
				bufferType
		));
	}

	@Override
	public long mapBufferRange(
			int				bufferHandle,
			long			mapOffset,
			long			mapLength,
			int				mapAccess,
			GLBufferType	bufferType
	) {
		return errorChecker.runChecked("mapBufferRange", () -> bufferFunctions.mapBufferRange(
				bufferHandle,
				mapOffset,
				mapLength,
				mapAccess,
				bufferType
		));
	}

	@Override
	public void unmapBuffer(int bufferHandle, GLBufferType bufferType) {
		errorChecker.runChecked("unmapBuffer", () -> bufferFunctions.unmapBuffer(bufferHandle, bufferType));
	}

	@Override
	public void flushMappedBufferRange(
			int				bufferHandle,
			long			flushOffset,
			long			flushLength,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("flushMappedBufferRange", () -> bufferFunctions.flushMappedBufferRange(
				bufferHandle,
				flushOffset,
				flushLength,
				bufferType
		));
	}

	@Override
	public int getBufferParameteri(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		return errorChecker.runChecked("getBufferParameteri", () -> bufferFunctions.getBufferParameteri(
				bufferHandle,
				bufferParameter,
				bufferType
		));
	}

	@Override
	public void getBufferParameteriv(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("getBufferParameteriv", () -> bufferFunctions.getBufferParameteriv(
				bufferHandle,
				bufferParameter,
				outDataAddress,
				bufferType
		));
	}

	@Override
	public long getBufferParameteri64(
			int				bufferHandle,
			int				bufferParameter,
			GLBufferType	bufferType
	) {
		return errorChecker.runChecked("getBufferParameteri64", () -> bufferFunctions.getBufferParameteri64(
				bufferHandle,
				bufferParameter,
				bufferType
		));
	}

	@Override
	public void getBufferParameteri64v(
			int				bufferHandle,
			int				bufferParameter,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("getBufferParameteri64v", () -> bufferFunctions.getBufferParameteri64v(
				bufferHandle,
				bufferParameter,
				outDataAddress,
				bufferType
		));
	}

	@Override
	public long getBufferPointer(
			int				bufferHandle,
			int				bufferPointer,
			GLBufferType	bufferType
	) {
		return errorChecker.runChecked("getBufferPointer", () -> bufferFunctions.getBufferPointer(
				bufferHandle,
				bufferPointer,
				bufferType
		));
	}

	@Override
	public void getBufferPointerv(
			int				bufferHandle,
			int				bufferPointer,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("getBufferPointerv", () -> bufferFunctions.getBufferPointerv(
				bufferHandle,
				bufferPointer,
				outDataAddress,
				bufferType
		));
	}

	@Override
	public void getBufferSubData(
			int				bufferHandle,
			long			outOffset,
			long			outSize,
			long			outDataAddress,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("getBufferSubData", () -> bufferFunctions.getBufferSubData(
				bufferHandle,
				outOffset,
				outSize,
				outDataAddress,
				bufferType
		));
	}

	@Override
	public boolean isBuffer(int bufferHandle) {
		return errorChecker.runChecked("isBuffer", () -> bufferFunctions.isBuffer(bufferHandle));
	}

	@Override
	public void bindBuffer(int bufferTarget, int bufferHandle) {
		errorChecker.runChecked("bindBuffer", () -> bufferFunctions.bindBuffer(bufferTarget, bufferHandle));
	}

	@Override
	public void bindBufferBase(
			int				bufferTarget,
			int				bufferTargetIndex,
			int				bufferHandle,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("bindBase", () -> bufferFunctions.bindBufferBase(
				bufferTarget,
				bufferTargetIndex,
				bufferHandle,
				bufferType
		));
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
		errorChecker.runChecked("bindRange", () -> bufferFunctions.bindBufferRange(
				bufferTarget,
				bufferTargetIndex,
				bufferHandle,
				bufferBindOffset,
				bufferBindSize,
				bufferType
		));
	}

	@Override
	public void bindBuffersBase(
			int				bufferTarget,
			int				bufferTargetFirstIndex,
			int				bufferCounts,
			long			bufferHandlesAddress,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("bindBases", () -> bufferFunctions.bindBuffersBase(
				bufferTarget,
				bufferTargetFirstIndex,
				bufferCounts,
				bufferHandlesAddress,
				bufferType
		));
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
		errorChecker.runChecked("bindRanges", () -> bufferFunctions.bindBuffersRange(
				bufferTarget,
				bufferTargetFirstIndex,
				bufferCounts,
				bufferHandlesAddress,
				bufferOffsetsAddress,
				bufferLengthsAddress,
				bufferType
		));
	}

	@Override
	public void invalidateBufferData(int bufferHandle, GLBufferType bufferType) {
		errorChecker.runChecked("invalidateBufferData", () -> bufferFunctions.invalidateBufferData(bufferHandle, bufferType));
	}

	@Override
	public void invalidateBufferSubData(
			int				bufferHandle,
			long			bufferOffset,
			long			bufferLength,
			GLBufferType	bufferType
	) {
		errorChecker.runChecked("invalidateBufferSubData", () -> bufferFunctions.invalidateBufferSubData(
				bufferHandle,
				bufferOffset,
				bufferLength,
				bufferType
		));
	}

	@Override
	public void deleteBuffer(int bufferHandle) {
		errorChecker.runChecked("deleteBuffer", () -> bufferFunctions.deleteBuffer(bufferHandle));
	}
}
