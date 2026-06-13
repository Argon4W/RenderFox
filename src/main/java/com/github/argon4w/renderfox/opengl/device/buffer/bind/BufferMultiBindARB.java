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

import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public class BufferMultiBindARB extends AbstractBufferMultiBind {

	public BufferMultiBindARB(GLBufferContext bufferContext) {
		super(bufferContext);
	}

	@Override
	public IGLBufferBindInfo.AbstractBuilder builder(GLBufferBlockType bufferBlockType) {
		return IGLBufferBindInfo.OffHeap.builder(bufferBlockType);
	}

	@Override
	public void bindBuffersBase(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			int					bufferBlockCount,
			long				bufferHandlesAddress
	) {
		bufferContext.getBufferFunctions().bindBuffersBase(
				bufferBlockType.getTypeConstant(),
				bufferBlockFirstIndex,
				bufferBlockCount,
				bufferHandlesAddress,
				bufferBlockType.getBufferType()
		);
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
		bufferContext.getBufferFunctions().bindBuffersRange(
				bufferBlockType.getTypeConstant(),
				bufferBlockFirstIndex,
				bufferBlockCount,
				bufferHandlesAddress,
				bufferOffsetsAddress,
				bufferLengthsAddress,
				bufferBlockType.getBufferType()
		);
	}
}
