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

package com.github.argon4w.renderfox.opengl.buffer.object.mapped;

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.object.GLBuffer;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBufferDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.raw.IGLRawBufferView;

public class GLMappedBufferView extends GLBuffer implements IGLMappedBuffer {

	private final IGLMappedBuffer	buffer;
	private final IGLRawBufferView	bufferView;

	public GLMappedBufferView(IGLMappedBuffer buffer, IGLRawBufferView bufferView) {
		super(bufferView);

		this.buffer		= buffer;
		this.bufferView	= bufferView;
	}

	@Override
	public IGLBufferDataView<?> data() {
		return buffer.data(this);
	}

	@Override
	public IGLBufferDataView<?> data(IDataRange dataRange) {
		if (dataRange == null) {
			throw new IllegalArgumentException("Data range cannot be null.");
		}

		if (dataRange.getOffset() < 0) {
			throw new IllegalArgumentException("DataOffset cannot be negative.");
		}

		if (dataRange.getLength() < 0) {
			throw new IllegalArgumentException("DataLength cannot be negative.");
		}

		if (dataRange.getRequired() > this.getLength()) {
			throw new IllegalArgumentException("DataOffset + DataLength cannot be greater than the value of the length of the current view.");
		}

		return buffer.data(new DataRange(
				getOffset() +	dataRange.getOffset(),
								dataRange.getLength()
		));
	}

	@Override
	public IGLBufferDataView<?> mapRangeData(IDataRange mapDataRange, GLBufferMapAccess mapAccess) {
		if (mapDataRange == null) {
			throw new IllegalArgumentException("MapDataRange cannot be null.");
		}

		if (mapDataRange.getOffset() < 0) {
			throw new IllegalArgumentException("MapDataOffset cannot be negative.");
		}

		if (mapDataRange.getLength() < 0) {
			throw new IllegalArgumentException("MapDataLength cannot be negative.");
		}

		if (mapDataRange.getRequired() > this.getLength()) {
			throw new IllegalArgumentException("MapDataOffset + mapDataLength cannot be greater than the value of the length of the current view.");
		}

		return buffer.mapRangeData(
				new DataRange(
						getOffset() +	mapDataRange.getOffset(),
										mapDataRange.getLength()
				),
				mapAccess
		);
	}

	@Override
	public GLMappedBufferView view(IDataRange viewRange) {
		if (viewRange == null) {
			throw new IllegalArgumentException("View range cannot be null.");
		}

		if (viewRange.getOffset() < 0) {
			throw new IllegalArgumentException("ViewOffset cannot be negative.");
		}

		if (viewRange.getLength() < 0) {
			throw new IllegalArgumentException("ViewLength cannot be negative.");
		}

		if (viewRange.getRequired() > this.getLength()) {
			throw new IllegalArgumentException("ViewLength + viewLength cannot be greater than the value of the length of the current view.");
		}

		return new GLMappedBufferView(
				this.buffer,
				this.bufferView.view(
						this.getOffset() +	viewRange.getOffset(),
											viewRange.getLength()
				)
		);
	}

	@Override
	public IDataRange getMapRange() {
		throw new UnsupportedOperationException("Cannot get the map range from a view of mapped buffer.");
	}

	@Override
	public long getMapOffset() {
		throw new UnsupportedOperationException("Cannot get the map offset from a view of mapped buffer.");
	}

	@Override
	public long getMapLength() {
		throw new UnsupportedOperationException("Cannot get the map length from a view of mapped buffer.");
	}

	@Override
	public void delete() {
		throw new UnsupportedOperationException("Cannot delete a view of mapped buffer.");
	}
}
