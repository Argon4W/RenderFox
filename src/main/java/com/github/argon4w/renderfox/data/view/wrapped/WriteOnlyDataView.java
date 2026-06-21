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

package com.github.argon4w.renderfox.data.view.wrapped;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IDataView;

public class WriteOnlyDataView extends DataViewWrapper<WriteOnlyDataView> {

	private final IDataView<?> dataView;;

	public WriteOnlyDataView(IDataView<?> dataView) {
		this.dataView = dataView;
	}

	@Override
	public IDataView<?> getDataView() {
		return dataView;
	}

	@Override
	public byte getByte() {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public short getShort() {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public int getInt() {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public long getLong() {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public float getFloat() {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public double getDouble() {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public WriteOnlyDataView getBytes(byte[] value) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public byte getByte(long position) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public short getShort(long position) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public int getInt(long position) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public long getLong(long position) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public float getFloat(long position) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public double getDouble(long position) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public WriteOnlyDataView getBytes(long position, byte[] value) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public WriteOnlyDataView getBytes(
			byte[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public WriteOnlyDataView getBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		throw new UnsupportedOperationException("Cannot get data from a write-only data view.");
	}

	@Override
	public WriteOnlyDataView slice() {
		return new WriteOnlyDataView(super.slice());
	}

	@Override
	public IDataView<?> slice(long length) {
		return new WriteOnlyDataView(super.slice(length));
	}

	@Override
	public WriteOnlyDataView slice(IDataRange range) {
		return new WriteOnlyDataView(super.slice(range));
	}
}
