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

import com.github.argon4w.renderfox.data.view.AddressDataView;
import com.github.argon4w.renderfox.data.view.DataViews;
import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.buffer.object.feature.IGLBufferBase;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;

public interface IGLBufferBindInfo extends AutoCloseable {

	void	bind			(IBufferMultiBind	multiBind);
	int		getBufferHandle	(int				index);
	long	getBufferOffset	(int				index);
	long	getBufferLength	(int				index);
	int		getBufferCount	();
	int		getFirstIndex	();

	class Heap implements IGLBufferBindInfo {

		private final GLBufferBlockType	bufferType;
		private final int				firstIndex;
		private final int	[]			bufferHandles;
		private final long	[]			bufferOffsets;
		private final long	[]			bufferLengths;

		public Heap(
				GLBufferBlockType	bufferType,
				int					firstIndex,
				int	[]				bufferHandles,
				long[]				bufferOffsets,
				long[]				bufferLengths
		) {
			this.bufferType		= bufferType;
			this.firstIndex		= firstIndex;
			this.bufferHandles	= bufferHandles;
			this.bufferOffsets	= bufferOffsets;
			this.bufferLengths	= bufferLengths;
		}

		@Override
		public void bind(IBufferMultiBind multiBind) {
			multiBind.bindBuffersRange(
					bufferType,
					firstIndex,
					bufferHandles,
					bufferOffsets,
					bufferLengths
			);
		}

		@Override
		public int getBufferHandle(int index) {
			return bufferHandles[index];
		}

		@Override
		public long getBufferOffset(int index) {
			return bufferOffsets[index];
		}

		@Override
		public long getBufferLength(int index) {
			return bufferLengths[index];
		}

		@Override
		public int getBufferCount() {
			return bufferHandles.length;
		}

		@Override
		public int getFirstIndex() {
			return firstIndex;
		}

		@Override
		public void close() throws Exception {

		}

		public static Builder builder(GLBufferBlockType bufferBlockType) {
			return new Builder(bufferBlockType);
		}

		public static class Builder extends AbstractBuilder {

			private Builder(GLBufferBlockType bufferType) {
				super(bufferType);
			}

			@Override
			public IGLBufferBindInfo build() {
				var firstIndex		= bufferBinds.firstIntKey	();
				var bufferCount		= bufferBinds.size			();
				var bufferHandles	= new int					[bufferCount];
				var bufferOffsets	= new long					[bufferCount];
				var bufferLengths	= new long					[bufferCount];

				for (var entry : bufferBinds.int2ObjectEntrySet()) {
					var bindingPoint	= entry.getIntKey	();
					var bufferBind		= entry.getValue	();
					var index			= bindingPoint - firstIndex;

					bufferHandles[index] = bufferBind.bufferHandle	();
					bufferOffsets[index] = bufferBind.offset		();
					bufferLengths[index] = bufferBind.length		();
				}

				return new Heap(
						bufferType,
						firstIndex,
						bufferHandles,
						bufferOffsets,
						bufferLengths
				);
			}
		}
	}

	class OffHeap implements IGLBufferBindInfo {

		private final GLBufferBlockType	bufferType;
		private final int				firstIndex;
		private final int				bufferCount;
		private final AddressDataView	bufferHandlesView;
		private final AddressDataView	bufferOffsetsView;
		private final AddressDataView	bufferLengthsView;

		public OffHeap(
				GLBufferBlockType	bufferType,
				int					firstIndex,
				int					bufferCount,
				AddressDataView		bufferHandlesView,
				AddressDataView		bufferOffsetsView,
				AddressDataView		bufferLengthsView
		) {
			this.bufferType			= bufferType;
			this.firstIndex			= firstIndex;
			this.bufferCount		= bufferCount;
			this.bufferHandlesView	= bufferHandlesView;
			this.bufferOffsetsView	= bufferOffsetsView;
			this.bufferLengthsView	= bufferLengthsView;
		}

		@Override
		public void bind(IBufferMultiBind multiBind) {
			multiBind.bindBuffersRange(
					bufferType,
					firstIndex,
					bufferCount,
					bufferHandlesView.address(),
					bufferOffsetsView.address(),
					bufferLengthsView.address()
			);
		}

		@Override
		public void close() throws Exception {
			DataViews.free(bufferHandlesView);
			DataViews.free(bufferLengthsView);
			DataViews.free(bufferOffsetsView);
		}

		@Override
		public int getBufferHandle(int index) {
			return bufferHandlesView.getInt((long) index * Integer.BYTES);
		}

		@Override
		public long getBufferOffset(int index) {
			return bufferOffsetsView.getLong((long) index * Long.BYTES);
		}

		@Override
		public long getBufferLength(int index) {
			return bufferLengthsView.getLong((long) index * Long.BYTES);
		}

		@Override
		public int getFirstIndex() {
			return firstIndex;
		}

		@Override
		public int getBufferCount() {
			return bufferCount;
		}

		public static Builder builder(GLBufferBlockType bufferType) {
			return new Builder(bufferType);
		}

		public static class Builder extends AbstractBuilder {

			private Builder(GLBufferBlockType bufferType) {
				super(bufferType);
			}

			@Override
			public IGLBufferBindInfo build() {
				var firstIndex			= bufferBinds	.firstIntKey();
				var bufferCount			= bufferBinds	.size		();
				var bufferHandlesView	= DataViews		.ofInts		(bufferCount);
				var bufferOffsetsView	= DataViews		.ofLongs	(bufferCount);
				var bufferLengthsView	= DataViews		.ofLongs	(bufferCount);

				for (var entry : bufferBinds.int2ObjectEntrySet()) {
					var bindingPoint	= entry.getIntKey	();
					var bufferBind		= entry.getValue	();
					var index			= bindingPoint - firstIndex;

					bufferHandlesView.putInt	(index, bufferBind.bufferHandle	());
					bufferOffsetsView.putLong	(index, bufferBind.offset		());
					bufferLengthsView.putLong	(index, bufferBind.length		());
				}

				return new OffHeap(
						bufferType,
						firstIndex,
						bufferCount,
						bufferHandlesView,
						bufferOffsetsView,
						bufferLengthsView
				);
			}
		}
	}

	abstract class AbstractBuilder {

		protected final	GLBufferBlockType					bufferType;
		protected final	Int2ObjectSortedMap<GLBufferBind>	bufferBinds;
		private			IGLBufferBase						currentBuffer;
		private			int									currentBinding;
		private			long								currentOffset;
		private			long								currentLength;

		private AbstractBuilder(GLBufferBlockType bufferType) {
			this.bufferType		= bufferType;
			this.bufferBinds	= new Int2ObjectAVLTreeMap<>();
			this.currentBuffer	= null;
			this.currentBinding	= -1;
			this.currentOffset	= -1;
			this.currentLength	= -1;
		}

		public abstract IGLBufferBindInfo build();

		public AbstractBuilder setBuffer(IGLBufferBase buffer) {
			this.currentBuffer = buffer;
			return this;
		}

		public AbstractBuilder setBindingPoint(int bindingPoint) {
			this.currentBinding = bindingPoint;
			return this;
		}

		public AbstractBuilder setOffset(long offset) {
			this.currentOffset = offset;
			return this;
		}

		public AbstractBuilder setLength(long length) {
			this.currentLength = length;
			return this;
		}

		public AbstractBuilder next() {
			if (this.currentBuffer == null) {
				throw new IllegalStateException("Cannot add a buffer bind with no buffer.");
			}

			if (this.currentBinding == -1) {
				throw new IllegalStateException("Cannot add a buffer bind with no binding point.");
			}

			if (this.currentOffset == -1) {
				throw new IllegalStateException("Cannot add a buffer bind with no offset.");
			}

			if (this.currentLength == -1) {
				throw new IllegalStateException("Cannot add a buffer bind with no length.");
			}

			bufferBinds.put(
					this.currentBinding,
					new GLBufferBind(
						this.currentBuffer,
						this.currentOffset,
						this.currentLength
					)
			);

			this.currentBuffer	= null;
			this.currentBinding	= -1;
			this.currentOffset	= -1;
			this.currentLength	= -1;

			return this;
		}

		public AbstractBuilder addBind(
				IGLBufferBase	bindingBuffer,
				int				bindingPoint,
				long			bindingOffset,
				long			bindingLength
		) {
			if (this.currentBuffer == null) {
				throw new IllegalStateException("Cannot add a buffer bind with no buffer.");
			}

			this.currentBuffer	= null;
			this.currentBinding	= -1;
			this.currentOffset	= -1;
			this.currentLength	= -1;

			bufferBinds.put(
					bindingPoint,
					new GLBufferBind(
							bindingBuffer,
							bindingOffset,
							bindingLength
					)
			);

			return this;
		}

		public AbstractBuilder addBind(
				int		bindingBufferHandle,
				int		bindingPoint,
				long	bindingOffset,
				long	bindingLength
		) {
			if (this.currentBuffer == null) {
				throw new IllegalStateException("Cannot add a buffer bind with no buffer.");
			}

			this.currentBuffer	= null;
			this.currentBinding	= -1;
			this.currentOffset	= -1;
			this.currentLength	= -1;

			bufferBinds.put(
					bindingPoint,
					new GLBufferBind(
							bindingBufferHandle,
							bindingOffset,
							bindingLength
					)
			);

			return this;
		}

		public AbstractBuilder addBinds(IGLBufferBindInfo info) {
			var firstIndex = info.getFirstIndex();

			this.currentBuffer	= null;
			this.currentBinding	= -1;
			this.currentOffset	= -1;
			this.currentLength	= -1;

			for (int index = 0, count = info.getBufferCount(); index < count; index ++) {
				this.addBind(
						info.getBufferHandle(index),
						firstIndex + index,
						info.getBufferOffset(index),
						info.getBufferLength(index)
				);
			}

			return this;
		}
	}
}
