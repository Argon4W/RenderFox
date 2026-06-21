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

package com.github.argon4w.renderfox.opengl.device.buffer.bind;

import com.github.argon4w.renderfox.data.view.DataViews;
import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public class BufferMultiBindLegacy extends AbstractBufferMultiBind {

	public BufferMultiBindLegacy(GLBufferContext bufferContext) {
		super(bufferContext);
	}

	@Override
	public IGLBufferBindInfo.AbstractBuilder builder(GLBufferBlockType bufferBlockType) {
		return IGLBufferBindInfo.Heap.builder(bufferBlockType);
	}

	@Override
	public void bindBuffersBase(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			int					bufferBlockCount,
			long				bufferHandlesAddress
	) {
		var bufferHandlesView = DataViews.wrapInts(bufferHandlesAddress, bufferBlockCount);

		for (var index = 0; index < bufferBlockCount; index ++) {
			bufferContext.getBufferFunctions().bindBufferBase(
					bufferBlockType.getTypeConstant(),
					bufferBlockFirstIndex + index,
					bufferHandlesView	.getInt			(index),
					bufferBlockType		.getBufferType	()
			);
		}
	}

	@Override
	public void bindBuffersRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			int					bufferBlockCount,
			long				bufferHandlesAddress,
			long				bufferOffsetsAddress,
			long				bufferLengthsAddress
	) {
		var bufferHandlesView = DataViews.wrapInts	(bufferHandlesAddress, bufferBlockCount);
		var bufferOffsetsView = DataViews.wrapLongs	(bufferOffsetsAddress, bufferBlockCount);
		var bufferLengthsView = DataViews.wrapLongs	(bufferLengthsAddress, bufferBlockCount);

		for (var index = 0; index < bufferBlockCount; index ++) {
			bufferContext.getBufferFunctions().bindBufferRange(
					bufferBlockType.getTypeConstant(),
					bufferBlockFirstIndex + index,
					bufferHandlesView	.getInt			(index),
					bufferOffsetsView	.getLong		(index),
					bufferLengthsView	.getLong		(index),
					bufferBlockType		.getBufferType	()
			);
		}
	}

	@Override
	public void bindBuffersBase(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			int[]				bufferHandles
	) {
		var bufferBlockCount = bufferHandles.length;

		for (var index = 0; index < bufferBlockCount; index ++) {
			bufferContext.getBufferFunctions().bindBufferBase(
					bufferBlockType.getTypeConstant(),
					bufferBlockFirstIndex + index,
					bufferHandles[index],
					bufferBlockType.getBufferType()
			);
		}
	}

	@Override
	public void bindBuffersBase(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			IGLBufferBase[]		buffers
	) {
		var bufferBlockCount = buffers.length;

		for (var index = 0; index < bufferBlockCount; index ++) {
			bufferContext.getBufferFunctions().bindBufferBase(
					bufferBlockType.getTypeConstant(),
					bufferBlockFirstIndex + index,
					buffers[index]	.getBufferHandle(),
					bufferBlockType	.getBufferType	()
			);
		}
	}

	@Override
	public void bindBuffersRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			int	[]				bufferHandles,
			long[]				bufferOffsets,
			long[]				bufferLengths
	) {
		if (bufferHandles.length != bufferOffsets.length) {
			throw new IllegalArgumentException("The length of the array of buffer binding offsets must be equal to the length of the array of buffer handles.");
		}

		if (bufferHandles.length != bufferLengths.length) {
			throw new IllegalArgumentException("The length of the array of buffer binding lengths must be equal to the length of the array of buffer handles.");
		}

		var bufferBlockCount = bufferHandles.length;

		for (var index = 0; index < bufferBlockCount; index ++) {
			bufferContext.getBufferFunctions().bindBufferRange(
					bufferBlockType.getTypeConstant(),
					bufferBlockFirstIndex + index,
					bufferHandles[index],
					bufferOffsets[index],
					bufferLengths[index],
					bufferBlockType.getBufferType()
			);
		}
	}

	@Override
	public void bindBuffersRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			IGLBufferBase	[]	buffers,
			long			[]	bufferOffsets,
			long			[]	bufferLengths
	) {
		var bufferBlockCount = buffers.length;

		if (bufferBlockCount != bufferOffsets.length) {
			throw new IllegalArgumentException("The length of the array of buffer binding offsets must be equal to the length of the array of buffer handles.");
		}

		if (bufferBlockCount != bufferLengths.length) {
			throw new IllegalArgumentException("The length of the array of buffer binding lengths must be equal to the length of the array of buffer handles.");
		}

		for (var index = 0; index < bufferBlockCount; index ++) {
			bufferContext.getBufferFunctions().bindBufferRange(
					bufferBlockType.getTypeConstant(),
					bufferBlockFirstIndex + index,
					buffers			[index].getBufferHandle(),
					bufferOffsets	[index],
					bufferLengths	[index],
					bufferBlockType.getBufferType()
			);
		}
	}

	@Override
	public void bindBuffersRange(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			GLBufferBind[]		bufferBinds
	) {
		var bufferBlockCount = bufferBinds.length;

		for (var index = 0; index < bufferBlockCount; index ++) {
			var bufferBind = bufferBinds[index];

			bufferContext.getBufferFunctions().bindBufferRange(
					bufferBlockType.getTypeConstant(),
					bufferBlockFirstIndex + index,
					bufferBind		.bufferHandle	(),
					bufferBind		.offset			(),
					bufferBind		.length			(),
					bufferBlockType	.getBufferType	()
			);
		}
	}
}
