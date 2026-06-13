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

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferParameter;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferAccessBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageBit;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.util.RangeUtils;
import org.lwjgl.opengl.GL15;

public class GLBufferFunctionsValidation implements IGLBufferFunctions {
	
	private final	IGLBufferFunctions			bufferFunctions;

	private			GLBufferFunctionsHelper		bufferHelper;
	private			IGLBufferHelperFunctions	helperFunctions;

	public GLBufferFunctionsValidation(IGLBufferFunctions bufferFunctions) {
		this.bufferFunctions	= bufferFunctions;

		this.bufferHelper		= null;
		this.helperFunctions	= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		bufferFunctions.initialize(device);
		
		bufferHelper	= device.getBufferContext().getGlobalBufferHelper	();
		helperFunctions	= device.getGlobalContext().getGlobalHelper			();
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
			int				storageFlagBits,
			GLBufferType	bufferType
	) {
		var storageFlag = GLBufferStorageBit.toFlag(storageFlagBits);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (bufferSize <= 0) {
			throw new IllegalArgumentException("BufferSize is less than or equal to zero.");
		}

		if (storageFlag.isInvalid()) {
			throw new IllegalArgumentException("storageFlagBits has any bits set other than those defined in GLBufferStorageFlagBit.");
		}

		if (		storageFlag.isPersistent()
				&& !storageFlag.canWrite	()
				&& !storageFlag.canRead		()
		) {
			throw new IllegalArgumentException("storageFlagBits contains GL_MAP_PERSISTENT_BIT but does not contain at least one of GL_MAP_READ_BIT or GL_MAP_WRITE_BIT.");
		}

		if (		storageFlag.isCoherent	()
				&& !storageFlag.isPersistent()
		) {
			throw new IllegalArgumentException("storageFlagBits contains GL_MAP_COHERENT_BIT, but does not also contain GL_MAP_PERSISTENT_BIT.");
		}

		if (bufferHelper.isImmutable()) {
			throw new IllegalArgumentException("GL_BUFFER_IMMUTABLE_STORAGE flag of the buffer is GL_TRUE.");
		}

		bufferFunctions.bufferStorage(
				bufferHandle,
				bufferSize,
				bufferDataAddress,
				storageFlagBits,
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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferFunctions.isBuffer(bufferHandle)) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (bufferDataSize < 0) {
			throw new IllegalArgumentException("BufferDataSize is negative.");
		}

		if (GLBufferUsage.fromConstant(bufferUsage) == GLBufferUsage.INVALID) {
			throw new IllegalArgumentException("BufferUsage is not GL_STREAM_DRAW, GL_STREAM_READ, GL_STREAM_COPY, GL_STATIC_DRAW, GL_STATIC_READ, GL_STATIC_COPY, GL_DYNAMIC_DRAW, GL_DYNAMIC_READ, or GL_DYNAMIC_COPY.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferDataAddress <= 0) {
			throw new IllegalArgumentException("BufferDataAddress cannot be null.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (bufferDataOffset < 0) {
			throw new IllegalArgumentException("BufferDataOffset is negative.");
		}

		if (bufferDataSize < 0) {
			throw new IllegalArgumentException("BufferDataSize is negative.");
		}

		if (bufferDataOffset + bufferDataSize > bufferHelper.getBufferSize()) {
			throw new IllegalArgumentException("BufferDataOffset + bufferDataSize is greater than the value of GL_BUFFER_SUZE for the specified buffer object.");
		}

		if (bufferHelper.isOccupied(new DataRange(bufferDataOffset, bufferDataSize))) {
			throw new IllegalArgumentException("Part of the specified range of the buffer object is mapped with glMapBufferRange or glMapBuffer and it was not mapped with the GL_MAP_PERSISTENT_BIT set in the glMapBufferRange access flags.");
		}

		if (bufferHelper.isImmutable() && !bufferHelper.isDynamic()) {
			throw new IllegalArgumentException("The value of the GL_BUFFER_IMMUTABLE_STORAGE_FLAG of the buffer object is GL_TRUE and the value of GL_BUFFER_STORAGE_FLAGS for the buffer does not have the GL_DYNAMIC_STORAGE_BIT bit set.");
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
	public void copyBufferSubData(
			int		bufferHandleRead,
			int		bufferHandleWrite,
			long	bufferCopyOffsetRead,
			long	bufferCopyOffsetWrite,
			long	bufferCopySize
	) {
		if (bufferHandleRead == 0) {
			throw new IllegalArgumentException("BufferHandleRead 0 is used.");
		}

		if (bufferHandleWrite == 0) {
			throw new IllegalArgumentException("BufferHandleWrite 0 is used.");
		}

		var bufferRead	= new GLBufferFunctionsHelper(this).setBuffer(bufferHandleRead,		GLBufferType.COPY_READ_BUFFER);
		var bufferWrite	= new GLBufferFunctionsHelper(this).setBuffer(bufferHandleWrite,	GLBufferType.COPY_WRITE_BUFFER);

		if (!bufferRead.isBuffer()) {
			throw new IllegalArgumentException("BufferHandleRead is not the name of an existing buffer object.");
		}

		if (!bufferWrite.isBuffer()) {
			throw new IllegalArgumentException("BufferHandleWrite is not the name of an existing buffer object.");
		}

		if (bufferCopyOffsetRead < 0) {
			throw new IllegalArgumentException("BufferCopyOffsetRead is negative.");
		}

		if (bufferCopyOffsetWrite < 0) {
			throw new IllegalArgumentException("BufferCopyOffsetWrite is negative.");
		}

		if (bufferCopySize < 0) {
			throw new IllegalArgumentException("BufferCopySize is negative.");
		}

		if (bufferCopyOffsetRead + bufferCopySize > bufferRead.getBufferSize()) {
			throw new IllegalArgumentException("BufferCopyOffsetRead + bufferCopySize is greater than the size of the source buffer object (Its value of GL_BUFFER_SIZE).");
		}

		if (bufferCopyOffsetWrite + bufferCopySize > bufferWrite.getBufferSize()) {
			throw new IllegalArgumentException("BufferCopyOffsetWrite + bufferCopyWrite is greater than the size of the destination buffer object (Its value of GL_BUFFER_SIZE).");
		}

		if (bufferHandleRead == bufferHandleWrite && RangeUtils.isOverlapped(
				bufferCopyOffsetRead,
				bufferCopySize,
				bufferCopyOffsetWrite,
				bufferCopySize
		)) {
			throw new IllegalArgumentException("The source and destination are the same buffer object, and the ranges [bufferCopyReadOffset, bufferCopyReadOffset + bufferCopySize) and [bufferCopyWriteOffset, bufferCopyWriteOffset + bufferCopySize) overlay.");
		}

		if (bufferRead.isOccupied(new DataRange(bufferCopyOffsetRead, bufferCopySize))) {
			throw new IllegalArgumentException("The source buffer object is mapped with glMapBufferRange or glMapBuffer and it was not mapped with the GL_MAP_PERSISTENT_BIT set in the glMapBufferRange access flags.");
		}

		if (bufferWrite.isOccupied(new DataRange(bufferCopyOffsetWrite, bufferCopySize))) {
			throw new IllegalArgumentException("The destination buffer object is mapped with glMapBufferRange or glMapBuffer and it was not mapped with the GL_MAP_PERSISTENT_BIT set in the glMapBufferRange access flags.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (clearDataAddress == 0) {
			throw new IllegalArgumentException("ClearDataAddress cannot be null.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (GLInternalFormat.fromConstant(bufferClearFormat) == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("BufferClearFormat is not one of the valid sized internal formats listed in the GLInternalFormat.");
		}

		if (bufferHelper.isMapped() && !bufferHelper.isPersistent()) {
			throw new IllegalArgumentException("Part of the specified range of the buffer object is mapped with glMapBufferRange or glMapBuffer and it was not mapped with the GL_MAP_PERSISTENT_BIT set in the glMapBufferRange access flags.");
		}

		if (GLFormat.fromConstant(clearDataFormat) == GLFormat.INVALID) {
			throw new IllegalArgumentException("ClearDataFormat is not a valid format.");
		}

		if (GLDataType.fromConstant(clearDataType) == GLDataType.INVALID) {
			throw new IllegalArgumentException("ClearDataType is not a valid type.");
		}

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
		var internalFormat		= GLInternalFormat	.fromConstant	(bufferClearFormat);
		var internalFormatSize	= internalFormat	.getFormatSize	();

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (clearDataAddress == 0) {
			throw new IllegalArgumentException("ClearDataAddress cannot be null.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (internalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("BufferClearFormat is not one of the valid sized internal formats listed in the GLInternalFormat.");
		}

		if (bufferClearOffset % internalFormatSize != 0) {
			throw new IllegalArgumentException("BufferClearOffset are not multiples of the number of basic machine units per-element for the internal format specified by bufferClearFormat. This value may be computed by multiplying the number of the components of the bufferClearFormat from the table by the size of the base type from the table.");
		}

		if (bufferClearSize % internalFormatSize != 0) {
			throw new IllegalArgumentException("BufferClearSize are not multiples of the number of basic machine units per-element for the internal format specified by bufferClearFormat. This value may be computed by multiplying the number of the components of the bufferClearFormat from the table by the size of the base type from the table.");
		}

		if (bufferClearOffset < 0) {
			throw new IllegalArgumentException("BufferClearOffset is negative.");
		}

		if (bufferClearSize < 0) {
			throw new IllegalArgumentException("BufferClearSize is negative.");
		}

		if (bufferClearOffset + bufferClearSize > bufferHelper.getBufferSize()) {
			throw new IllegalArgumentException("BufferClearOffset + bufferClearSize is greater than the value of GL_BUFFER_SIZE for the buffer object.");
		}

		if (bufferHelper.isOccupied(new DataRange(bufferClearOffset, bufferClearSize))) {
			throw new IllegalArgumentException("Part of the specified range of the buffer object is mapped with glMapBufferRange or glMapBuffer and it was not mapped with the GL_MAP_PERSISTENT_BIT set in the glMapBufferRange access flags.");
		}

		if (GLFormat.fromConstant(clearDataFormat) == GLFormat.INVALID) {
			throw new IllegalArgumentException("ClearDataFormat is not a valid format.");
		}
		if (GLDataType.fromConstant(clearDataType) == GLDataType.INVALID) {
			throw new IllegalArgumentException("ClearDataType is not a valid type.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (GLBufferMapAccessLegacy.fromConstant(mapAccess) == GLBufferMapAccessLegacy.INVALID) {
			throw new IllegalArgumentException("mapAccess is not GL_READ_ONLY, GL_WRITE_ONLY, or GL_READ_WRITE.");
		}

		if (bufferHelper.isBuffer()) {
			throw new IllegalArgumentException("The buffer object is in a mapped state.");
		}

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
			int				mapAccessBits,
			GLBufferType	bufferType
	) {
		var mapAccess = GLBufferAccessBit.toAccess(mapAccessBits);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (mapOffset + mapLength > bufferHelper.getBufferSize()) {
			throw new IllegalArgumentException("MapOffset + mapLength is greater than the value of GL_BUFFER_SIZE for the buffer object.");
		}

		if (mapLength == 0) {
			throw new IllegalArgumentException("MapLength is zero.");
		}

		if (bufferHelper.isMapped()) {
			throw new IllegalArgumentException("The buffer object is in a mapped state.");
		}

		if (mapAccess.isInvalid()) {
			throw new IllegalArgumentException("mapAccessBits has any bits set other than those defined in GLBufferAccessBit.");
		}

		if (!mapAccess.canRead() && !mapAccess.canWrite()) {
			throw new IllegalArgumentException("Neither GL_MAP_READ_BIT nor GL_MAP_WRITE_BIT is set.");
		}

		if (mapAccess.isExplicit() && !mapAccess.canWrite()) {
			throw new IllegalArgumentException("GL_MAP_FLUSH_EXPLICIT is set and GL_MAP_WRITE_BIT is not set.");
		}

		if (mapAccess.matches(bufferHelper.getStorageFlag())) {
			throw new IllegalArgumentException("Any of GL_MAP_READ_BIT, GL_MAP_WRITE_BIT, GL_MAP_PERSISTENT_BIT, or GL_MAP_COHERENT_BIT are set, but the same bit is not included in the buffer's storage flags.");
		}

		if (mapAccess.canRead() && (mapAccess.isInvalidated() || mapAccess.isUnsynchronized())) {
			throw new IllegalArgumentException("GL_MAP_READ_BIT is set and any of GL_MAP_INVALIDATE_RANGE_BIT, GL_MAP_INVALIDATE_BUFFER_BIT or GL_MAP_UNSYNCHRONIZED_BIT is set.");
		}

		return bufferFunctions.mapBufferRange(
				bufferHandle,
				mapOffset,
				mapLength,
				mapAccessBits,
				bufferType
		);
	}

	@Override
	public void unmapBuffer(int bufferHandle, GLBufferType bufferType) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (!bufferHelper.isMapped()) {
			throw new IllegalArgumentException("The buffer object is not in a mapped state.");
		}

		bufferFunctions.unmapBuffer(bufferHandle, bufferType);
	}

	@Override
	public void flushMappedBufferRange(
			int				bufferHandle,
			long			flushOffset,
			long			flushLength,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (flushOffset < 0) {
			throw new IllegalArgumentException("FlushOffset is negative.");
		}

		if (flushLength < 0) {
			throw new IllegalArgumentException("FlushLength is negative.");
		}

		var mapOffset = bufferHelper.getMapOffset();
		var mapLength = bufferHelper.getMapLength();

		if (flushLength > mapLength) {
			throw new IllegalArgumentException("FlushLength exceeds the size of mapping.");
		}

		if (flushOffset + flushLength > mapOffset + mapLength) {
			throw new IllegalArgumentException("FlushOffset + flushLength exceeds the size of mapping.");
		}

		if (flushOffset + flushLength <= mapOffset) {
			throw new IllegalArgumentException("FlushOffset + flushLength exceeds the size of mapping.");
		}

		if (!bufferHelper.isMapped()) {
			throw new IllegalArgumentException("The buffer object is not mapped.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferFunctions.isBuffer(bufferHandle)) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (GLBufferParameter.fromConstant(bufferParameter) == GLBufferParameter.INVALID) {
			throw new IllegalArgumentException("Buffer parameter is not one of the buffer object parameter names described in GLBufferParameter.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (outDataAddress == 0) {
			throw new IllegalArgumentException("OutDataAddress cannot be null.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferFunctions.isBuffer(bufferHandle)) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (GLBufferParameter.fromConstant(bufferParameter) == GLBufferParameter.INVALID) {
			throw new IllegalArgumentException("Buffer parameter is not one of the buffer object parameter names described in GLBufferParameter.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferFunctions.isBuffer(bufferHandle)) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (GLBufferParameter.fromConstant(bufferParameter) == GLBufferParameter.INVALID) {
			throw new IllegalArgumentException("Buffer parameter is not one of the buffer object parameter names described in GLBufferParameter.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (outDataAddress == 0) {
			throw new IllegalArgumentException("OutDataAddress cannot be null.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferFunctions.isBuffer(bufferHandle)) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (GLBufferParameter.fromConstant(bufferParameter) == GLBufferParameter.INVALID) {
			throw new IllegalArgumentException("Buffer parameter is not one of the buffer object parameter names described in GLBufferParameter.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferFunctions.isBuffer(bufferHandle)) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (bufferPointer != GL15.GL_BUFFER_MAP_POINTER) {
			throw new IllegalArgumentException("BufferPointer is not an accepted value. It must be GL_BUFFER_MAP_POINTER.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (outDataAddress == 0) {
			throw new IllegalArgumentException("OutDataAddress cannot be null.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferFunctions.isBuffer(bufferHandle)) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (bufferPointer != GL15.GL_BUFFER_MAP_POINTER) {
			throw new IllegalArgumentException("BufferPointer is not an accepted value. It must be GL_BUFFER_MAP_POINTER.");
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the buffer binding targets listed in GLBufferType.");
		}

		if (outDataAddress == 0) {
			throw new IllegalArgumentException("OutDataAddress cannot be null.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (outOffset < 0) {
			throw new IllegalArgumentException("OutOffset is negative.");
		}

		if (outSize < 0) {
			throw new IllegalArgumentException("OutDataSize is negative.");
		}

		if (outOffset + outSize > bufferHelper.getBufferSize()) {
			throw new IllegalArgumentException("OutOffset + outDataSize is greater than the value of GL_BUFFER_SUZE for the specified buffer object.");
		}

		if (bufferHelper.isOccupied(new DataRange(outOffset, outSize))) {
			throw new IllegalArgumentException("Part of the specified range of the buffer object is mapped with glMapBufferRange or glMapBuffer and it was not mapped with the GL_MAP_PERSISTENT_BIT set in the glMapBufferRange access flags.");
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
	public boolean isBuffer(int bufferHandle) {
		return bufferFunctions.isBuffer(bufferHandle);
	}

	@Override
	public void bindBuffer(int bufferTarget, int bufferHandle) {
		if (GLBufferType.fromTypeConstant(bufferTarget) == GLBufferType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not one of the allowable values.");
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
		var bufferBlockType = GLBufferBlockType.fromTypeConstant(bufferTarget);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not GL_ATOMIC_COUNTER_BUFFER, GL_TRANSFORM_FEEDBACK_BUFFER, GL_UNIFORM_BUFFER or GL_SHADER_STORAGE_BUFFER.");
		}

		if (bufferTargetIndex < 0) {
			throw new IllegalArgumentException("BufferTargetIndex is negative.");
		}

		if (bufferTargetIndex >= helperFunctions.getBufferMaxBindings(bufferBlockType)) {
			throw new IllegalArgumentException("BufferTargetIndex is greater than or equal to the number of target-specific indexed binding points.");
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
		var bufferBlockType = GLBufferBlockType.fromTypeConstant(bufferTarget);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not GL_ATOMIC_COUNTER_BUFFER, GL_TRANSFORM_FEEDBACK_BUFFER, GL_UNIFORM_BUFFER or GL_SHADER_STORAGE_BUFFER.");
		}

		if (bufferTargetIndex < 0) {
			throw new IllegalArgumentException("BufferTargetIndex is negative.");
		}

		if (bufferTargetIndex >= helperFunctions.getBufferMaxBindings(bufferBlockType)) {
			throw new IllegalArgumentException("BufferTargetIndex is greater than or equal to the number of target-specific indexed binding points.");
		}

		if (bufferHandle != 0 && !bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("The bufferHandle is not zero or the name of an existing buffer object.");
		}

		if (bufferBindSize < 0) {
			throw new IllegalArgumentException("BufferBindSize cannot be less than zero.");
		}

		if (bufferBindOffset < 0) {
			throw new IllegalArgumentException("BufferBindSize cannot be less than zero.");
		}

		if (bufferBindOffset % helperFunctions.getBufferOffsetAlign(bufferBlockType) != 0) {
			throw new IllegalArgumentException("BufferBindOffset violates target-specific alignment restrictions.");
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
		var bufferBlockType = GLBufferBlockType.fromTypeConstant(bufferTarget);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not GL_ATOMIC_COUNTER_BUFFER, GL_TRANSFORM_FEEDBACK_BUFFER, GL_UNIFORM_BUFFER or GL_SHADER_STORAGE_BUFFER.");
		}

		if (bufferTargetFirstIndex < 0) {
			throw new IllegalArgumentException("bufferTargetFirstIndex cannot be negative.");
		}

		if (bufferCounts <= 0) {
			throw new IllegalArgumentException("BufferCounts cannot be less than or equal to zero.");
		}

		if (bufferTargetFirstIndex + bufferCounts >= helperFunctions.getBufferMaxBindings(bufferBlockType)) {
			throw new IllegalArgumentException("The range of binding points cannot exceeds the maximum index of binding points allowed of the given bufferType.");
		}

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
		var bufferBlockType = GLBufferBlockType.fromTypeConstant(bufferTarget);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("BufferTarget is not GL_ATOMIC_COUNTER_BUFFER, GL_TRANSFORM_FEEDBACK_BUFFER, GL_UNIFORM_BUFFER or GL_SHADER_STORAGE_BUFFER.");
		}

		if (bufferTargetFirstIndex < 0) {
			throw new IllegalArgumentException("bufferTargetFirstIndex cannot be negative.");
		}

		if (bufferCounts <= 0) {
			throw new IllegalArgumentException("BufferCounts cannot be less than or equal to zero.");
		}

		if (bufferTargetFirstIndex + bufferCounts >= helperFunctions.getBufferMaxBindings(bufferBlockType)) {
			throw new IllegalArgumentException("The range of binding points cannot exceeds the maximum index of binding points allowed of the given bufferType.");
		}

		try (var bufferHandlesView = StackDataView.asDataView(bufferHandlesAddress, (long) bufferCounts * Integer	.BYTES);
		     var bufferOffsetsView = StackDataView.asDataView(bufferOffsetsAddress, (long) bufferCounts * Long		.BYTES);
		     var bufferLengthsView = StackDataView.asDataView(bufferLengthsAddress, (long) bufferCounts * Long		.BYTES)
		) {
			for (var index = 0; index < bufferCounts; index ++) {
				var bufferHandle = bufferHandlesView.getInt(index);
				var bufferOffset = bufferOffsetsView.getInt(index);
				var bufferLength = bufferLengthsView.getInt(index);

				if (bufferHandle != 0 && !bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
					throw new IllegalArgumentException("The bufferHandle at index %d is not zero or the name of an existing buffer object.".formatted(index));
				}

				if (bufferLength < 0) {
					throw new IllegalArgumentException("bufferLength at index %d cannot be less than zero.".formatted(index));
				}

				if (bufferOffset < 0) {
					throw new IllegalArgumentException("bufferOffset at index %d cannot be less than zero.".formatted(index));
				}

				if (bufferOffset % helperFunctions.getBufferOffsetAlign(bufferBlockType) != 0) {
					throw new IllegalArgumentException("bufferOffset at index %d violates target-specific alignment restrictions.".formatted(index));
				}

			}
		}

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
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (bufferHelper.isMapped()) {
			throw new IllegalArgumentException("Part of the buffer is currently mapped.");
		}

		bufferFunctions.invalidateBufferData(bufferHandle, bufferType);
	}

	@Override
	public void invalidateBufferSubData(
			int				bufferHandle,
			long			bufferOffset,
			long			bufferLength,
			GLBufferType	bufferType
	) {
		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (bufferHandle == 0) {
			throw new IllegalArgumentException("BufferHandle 0 is used.");
		}

		if (!bufferHelper.setBuffer(bufferHandle, bufferType).isBuffer()) {
			throw new IllegalArgumentException("BufferHandle is not the name of an existing buffer object.");
		}

		if (bufferOffset < 0) {
			throw new IllegalArgumentException("BufferOffset is negative.");
		}

		if (bufferLength < 0) {
			throw new IllegalArgumentException("BufferLength is negative.");
		}

		if (bufferOffset + bufferLength > bufferHelper.getBufferSize()) {
			throw new IllegalArgumentException("BufferOffset + bufferSize is greater than the value of GL_BUFFER_SIZE for buffer.");
		}

		if (bufferHelper.isMapped()) {
			throw new IllegalArgumentException("Part of the buffer is currently mapped.");
		}

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
