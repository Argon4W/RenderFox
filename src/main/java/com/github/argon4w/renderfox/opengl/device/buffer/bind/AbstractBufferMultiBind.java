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

import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import com.github.argon4w.renderfox.opengl.device.buffer.GLBufferContext;

public abstract class AbstractBufferMultiBind implements IBufferMultiBind {

	protected final GLBufferContext bufferContext;

	public AbstractBufferMultiBind(GLBufferContext bufferContext) {
		this.bufferContext = bufferContext;
	}

	@Override
	public void bindBuffersBase(
			GLBufferBlockType	bufferBlockType,
			int					bufferBlockFirstIndex,
			int[]				bufferHandles
	) {
		try (var bufferHandlesView = StackDataView.ofInts(bufferHandles)) {
			bindBuffersBase(
					bufferBlockType,
					bufferBlockFirstIndex,
					bufferHandles.length,
					bufferHandlesView.address()
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

		try (var bufferHandlesView = StackDataView.ofInts(bufferBlockCount)) {
			for (var index = 0; index < bufferBlockCount; index++) {
				bufferHandlesView.putInt((long) index * Integer.BYTES, buffers[index].getBufferHandle());
			}

			bindBuffersBase(
					bufferBlockType,
					bufferBlockFirstIndex,
					buffers.length,
					bufferHandlesView.address()
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

		try (var bufferHandlesView = StackDataView.ofInts	(bufferHandles);
			 var bufferOffsetsView = StackDataView.ofLongs	(bufferOffsets);
			 var bufferLengthsView = StackDataView.ofLongs	(bufferLengths)
		) {
			bindBuffersRange(
					bufferBlockType,
					bufferBlockFirstIndex,
					bufferHandles.length,
					bufferHandlesView.address(),
					bufferOffsetsView.address(),
					bufferLengthsView.address()
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
			throw new IllegalArgumentException("The length of the array of buffer binding offsets must be equal to the length of the array of buffers.");
		}

		if (bufferBlockCount != bufferLengths.length) {
			throw new IllegalArgumentException("The length of the array of buffer binding lengths must be equal to the length of the array of buffers.");
		}

		try (var bufferHandlesView = StackDataView.ofInts	(bufferBlockCount);
		     var bufferOffsetsView = StackDataView.ofLongs	(bufferOffsets);
		     var bufferLengthsView = StackDataView.ofLongs	(bufferLengths)
		) {
			for (var index = 0; index < bufferBlockCount; index ++) {
				bufferHandlesView.putInt((long) index * Integer.BYTES, buffers[index].getBufferHandle());
			}

			bindBuffersRange(
					bufferBlockType,
					bufferBlockFirstIndex,
					bufferBlockCount,
					bufferHandlesView.address(),
					bufferOffsetsView.address(),
					bufferLengthsView.address()
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

		try (var bufferHandlesView = StackDataView.ofInts	(bufferBlockCount);
		     var bufferOffsetsView = StackDataView.ofLongs	(bufferBlockCount);
		     var bufferLengthsView = StackDataView.ofLongs	(bufferBlockCount)
		) {
			for (var index = 0; index < bufferBlockCount; index ++) {
				var bufferBind = bufferBinds[index];

				bufferHandlesView.putInt	((long) index * Integer	.BYTES, bufferBind.bufferHandle	());
				bufferOffsetsView.putLong	((long) index * Long	.BYTES, bufferBind.offset		());
				bufferLengthsView.putLong	((long) index * Long	.BYTES, bufferBind.length		());
			}

			bindBuffersRange(
					bufferBlockType,
					bufferBlockFirstIndex,
					bufferBlockCount,
					bufferHandlesView.address(),
					bufferOffsetsView.address(),
					bufferLengthsView.address()
			);
		}
	}

	@Override
	public void bindBuffersRange(IGLBufferBindInfo info) {
		info.bind(this);
	}
}
