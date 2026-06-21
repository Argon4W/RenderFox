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

package com.github.argon4w.renderfox.data.view;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;

public interface IDataView<T extends IDataView<T>> {

	<R extends IDataView<R>> R as(IDataViewDecorator<R> dataViewDecorator);

	IDataView<?>	slice();
	IDataView<?>	slice(long			length);
	IDataView<?>	slice(IDataRange	range);
	IDataRange		flush();
	IDataRange		flush(IDataRange	range);

	long	capacity	();
	long	remaining	();
	long	position	();
	long	limit		();
	long	address		();
	boolean	isOffHeap	();

	T position	(long position);
	T limit		(long limit);
	T rewind	();
	T flip		();
	T mark		();
	T reset		();
	T clear		();

	T putByte	(byte			value);
	T putShort	(short			value);
	T putInt	(int			value);
	T putInt24	(int			value);
	T putLong	(long			value);
	T putFloat	(float			value);
	T putDouble	(double			value);
	T putBytes	(byte	[]		value);
	T putShorts	(short	[]		value);
	T putInts	(int	[]		value);
	T putLongs	(long	[]		value);
	T putFloats	(float	[]		value);
	T putDoubles(double	[]		value);
	T putBuffer	(IDataView<?>	value);
	T putBuffer	(long			valueAddress, long length);

	T putByte	(long position, byte			value);
	T putShort	(long position, short			value);
	T putInt	(long position, int				value);
	T putLong	(long position, long			value);
	T putFloat	(long position, float			value);
	T putDouble	(long position, double			value);
	T putBytes	(long position, byte	[]		value);
	T putShorts	(long position, short	[]		value);
	T putInts	(long position, int		[]		value);
	T putLongs	(long position, long	[]		value);
	T putFloats	(long position, float	[]		value);
	T putDoubles(long position, double	[]		value);
	T putBuffer	(long position, IDataView<?>	value);

	T putBytes(
			byte[]			value,
			long			offset,
			long			length
	);

	T putShorts(
			short[]			value,
			long			offset,
			long			length
	);

	T putInts(
			int[]			value,
			long			offset,
			long			length
	);

	T putLongs(
			long[]			value,
			long			offset,
			long			length
	);

	T putFloats(
			float[]			value,
			long			offset,
			long			length
	);

	T putDoubles(
			double[]		value,
			long			offset,
			long			length
	);

	T putBuffer(
			IDataView<?>	value,
			long			offset,
			long			length
	);

	T putBuffer(
			long			valueAddress,
			long			offset,
			long			length
	);

	T putBytes(
			long			position,
			byte[]			value,
			long			offset,
			long			length
	);

	T putShorts(
			long			position,
			short[]			value,
			long			offset,
			long			length
	);

	T putInts(
			long			position,
			int[]			value,
			long			offset,
			long			length
	);

	T putLongs(
			long			position,
			long[]			value,
			long			offset,
			long			length
	);

	T putFloats(
			long			position,
			float[]			value,
			long			offset,
			long			length
	);

	T putDoubles(
			long			position,
			double[]		value,
			long			offset,
			long			length
	);

	T putBuffer(
			long			position,
			IDataView<?>	value,
			long			offset,
			long			length
	);

	T putBuffer(
			long			position,
			long			valueAddress,
			long			offset,
			long			length
	);

	byte	getByte		();
	short	getShort	();
	int		getInt		();
	long	getLong		();
	float	getFloat	();
	double	getDouble	();
	T		getBytes	(byte[] value);

	byte	getByte		(long position);
	short	getShort	(long position);
	int		getInt		(long position);
	long	getLong		(long position);
	float	getFloat	(long position);
	double	getDouble	(long position);
	T		getBytes	(long position, byte[] value);

	T getBytes(
			byte[]	value,
			long	offset,
			long	length
	);

	T getBytes(
			long	position,
			byte[]	value,
			long	offset,
			long	length
	);

	default T putShortAligned	(long position, short	value) { return putShort	(position * Short	.BYTES, value); }
	default T putIntAligned		(long position, int		value) { return putInt		(position * Integer	.BYTES, value); }
	default T putLongAligned	(long position, long	value) { return putLong		(position * Long	.BYTES, value); }
	default T putFloatAligned	(long position, float	value) { return putFloat	(position * Float	.BYTES, value); }
	default T putDoubleAligned	(long position, double	value) { return putDouble	(position * Double	.BYTES, value); }
}
