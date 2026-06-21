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

import com.github.argon4w.renderfox.data.view.AbstractDataView;
import com.github.argon4w.renderfox.data.view.IDataView;

@SuppressWarnings("unchecked")
public abstract class OffsetDataView<T extends OffsetDataView<T>> extends AbstractDataView<T> {

	public OffsetDataView(long capacity) {
		super(capacity);
	}

	protected abstract IDataView<?>	getDataView	();
	protected abstract long			getOffset	();

	@Override
	public long address() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().address();
	}

	@Override
	public boolean isOffHeap() {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().isOffHeap();
	}

	@Override
	public T putByte(long position, byte value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putByte(getOffset() + position, value);
		return (T) this;
	}

	@Override
	public T putShort(long position, short value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShort(getOffset() + position, value);
		return (T) this;
	}

	@Override
	public T putInt(long position, int value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInt(getOffset() + position, value);
		return (T) this;
	}

	@Override
	public T putLong(long position, long value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLong(getOffset() + position, value);
		return (T) this;
	}

	@Override
	public T putFloat(long position, float value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloat(getOffset() + position, value);
		return (T) this;
	}

	@Override
	public T putDouble(long position, double value) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDouble(getOffset() + position, value);
		return (T) this;
	}

	@Override
	public T putBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBytes(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public T putShorts(
			long	position,
			short[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putShorts(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public T putInts(
			long	position,
			int[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putInts(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public T putLongs(
			long	position,
			long[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putLongs(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public T putFloats(
			long	position,
			float[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putFloats(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public T putDoubles(
			long		position,
			double[]	value,
			long		offset,
			long		length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putDoubles(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public T putBuffer(
			long			position,
			IDataView<?>	value,
			long			offset,
			long			length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public T putBuffer(
			long position,
			long valueAddress,
			long offset,
			long length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().putBuffer(
				getOffset() + position,
				valueAddress,
				offset,
				length
		);

		return (T) this;
	}

	@Override
	public byte getByte(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getByte(getOffset() + position);
	}

	@Override
	public short getShort(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getShort(getOffset() + position);
	}

	@Override
	public int getInt(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getInt(getOffset() + position);
	}

	@Override
	public long getLong(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getLong(getOffset() + position);
	}

	@Override
	public float getFloat(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getFloat(getOffset() + position);
	}

	@Override
	public double getDouble(long position) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		return getDataView().getDouble(getOffset() + position);
	}

	@Override
	public T getBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	) {
		if (getDataView() == null) {
			throw new IllegalStateException("DataView cannot be null.");
		}

		getDataView().getBytes(
				getOffset() + position,
				value,
				offset,
				length
		);

		return (T) this;
	}
}
