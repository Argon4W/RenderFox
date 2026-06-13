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

package com.github.argon4w.renderfox.opengl.format;

import com.github.argon4w.renderfox.format.IDataType;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public enum GLDataType implements IDataType {

	INVALID				(-1,										0,				0,																										(view, value) -> {}),
	UNSIGNED_BYTE		(GL11.GL_UNSIGNED_BYTE,						Byte	.BYTES,	GLFormatComponent.SIZE_8_MASK	| GLFormatComponent.INTEGER_MASK,										(view, value) -> view.putByte	(value.asByte	())),
	UNSIGNED_SHORT		(GL11.GL_UNSIGNED_SHORT,					Short	.BYTES,	GLFormatComponent.SIZE_16_MASK	| GLFormatComponent.INTEGER_MASK,										(view, value) -> view.putShort	(value.asShort	())),
	UNSIGNED_INT24		(GL11.GL_UNSIGNED_INT,						24,				GLFormatComponent.SIZE_24_MASK	| GLFormatComponent.INTEGER_MASK,										(view, value) -> view.putInt24	(value.asInt24	())),
	UNSIGNED_INT		(GL11.GL_UNSIGNED_INT,						Integer	.BYTES,	GLFormatComponent.SIZE_32_MASK	| GLFormatComponent.INTEGER_MASK,										(view, value) -> view.putInt	(value.asInt	())),
	BYTE				(GL11.GL_BYTE,								Byte	.BYTES,	GLFormatComponent.SIZE_8_MASK	| GLFormatComponent.INTEGER_MASK	| GLFormatComponent.SIGNED_MASK,	(view, value) -> view.putByte	(value.asByte	())),
	SHORT				(GL11.GL_SHORT,								Short	.BYTES,	GLFormatComponent.SIZE_16_MASK	| GLFormatComponent.INTEGER_MASK	| GLFormatComponent.SIGNED_MASK,	(view, value) -> view.putShort	(value.asShort	())),
	INT					(GL11.GL_INT,								Integer	.BYTES,	GLFormatComponent.SIZE_32_MASK	| GLFormatComponent.INTEGER_MASK	| GLFormatComponent.SIGNED_MASK,	(view, value) -> view.putInt	(value.asInt	())),
	HALF_FLOAT			(GL30.GL_HALF_FLOAT,						Short	.BYTES,	GLFormatComponent.SIZE_16_MASK										| GLFormatComponent.SIGNED_MASK,	(view, value) -> view.putShort	(value.asFloat16())),
	FLOAT				(GL11.GL_FLOAT,								Float	.BYTES,	GLFormatComponent.SIZE_32_MASK										| GLFormatComponent.SIGNED_MASK,	(view, value) -> view.putFloat	(value.asFloat	())),
	DOUBLE				(GL11.GL_DOUBLE,							Double	.BYTES,	GLFormatComponent.SIZE_64_MASK										| GLFormatComponent.SIGNED_MASK,	(view, value) -> view.putDouble	(value.asDouble	())),
	DEPTH24_STENCIL8	(GL30.GL_UNSIGNED_INT_24_8,					Integer	.BYTES,	GLFormatComponent.SIZE_32_MASK,																			(view, value) -> view.putInt	(value.asInt	())),
	DEPTH32F_STENCIL8	(GL30.GL_FLOAT_32_UNSIGNED_INT_24_8_REV,	Long	.BYTES,	GLFormatComponent.SIZE_64_MASK,																			(view, value) -> view.putLong	(value.asLong	())),;

	private static final Int2ReferenceMap<GLDataType>	TABLE_CONSTANT;
	private static final Int2ReferenceMap<GLDataType>	TABLE_FLAGS;
	private static final Lookup							GLOBAL_LOOKUP;

	static {
		TABLE_CONSTANT	= new Int2ReferenceOpenHashMap<>();
		TABLE_FLAGS		= new Int2ReferenceOpenHashMap<>();
		GLOBAL_LOOKUP	= new Lookup					();

		for (var dataType : GLDataType.values()) {
			TABLE_CONSTANT	.put(dataType.constant,	dataType);
			TABLE_FLAGS		.put(dataType.flags,	dataType);
		}
	}

	private final int		constant;
	private final int		size;
	private final int		flags;
	private final Writer	writer;

	GLDataType(
			int		constant,
			int		size,
			int		flags,
			Writer	writer
	) {
		this.constant	= constant;
		this.size		= size;
		this.flags		= flags;
		this.writer		= writer;
	}

	public int getConstant() {
		return constant;
	}

	@Override
	public Writer getWriter() {
		return writer;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public boolean isSigned() {
		return (flags & GLFormatComponent.SIGNED_MASK) != 0;
	}

	@Override
	public boolean isInteger() {
		return (flags & GLFormatComponent.INTEGER_MASK) != 0;
	}

	public static GLDataType fromConstant(int constant) {
		return TABLE_CONSTANT.getOrDefault(constant,  GLDataType.INVALID);
	}

	public static GLDataType fromFlags(int flags) {
		return TABLE_FLAGS.getOrDefault(flags, GLDataType.INVALID);
	}

	public static Lookup globalLookup() {
		return GLOBAL_LOOKUP;
	}

	public static Lookup newLookup() {
		return new Lookup();
	}

	public static class Lookup implements IDataType.Lookup {

		private int flags;

		private Lookup() {
			this.flags = 0;
		}

		private Lookup(int flags) {
			this.flags = flags;
		}

		public Lookup reset() {
			this.flags = 0;
			return this;
		}

		@Override
		public Lookup fromCommonDataType() {
			this.flags = GLDataType.UNSIGNED_BYTE.flags;
			return this;
		}

		@Override
		public Lookup fromDataType(IDataType dataType) {
			this.flags = dataType instanceof GLDataType glDataType ? glDataType.flags : 0;
			return this;
		}

		@Override
		public Lookup withInteger() {
			this.flags |= GLFormatComponent.INTEGER_MASK;
			return this;
		}

		@Override
		public Lookup withFloatingPoint() {
			this.flags &= ~GLFormatComponent.INTEGER_MASK;
			return this;
		}

		@Override
		public Lookup withSigned() {
			this.flags |= GLFormatComponent.SIGNED_MASK;
			return this;
		}

		@Override
		public Lookup withUnsigned() {
			this.flags &= ~GLFormatComponent.SIGNED_MASK;
			return this;
		}

		@Override
		public Lookup copy() {
			return new Lookup(flags);
		}

		@Override
		public IDataType find() {
			return GLDataType.fromFlags(flags);
		}

		@Override
		public Lookup withSize(int size) {
			this.flags &= ~ GLFormatComponent.SIZE_MASK;

			this.flags |= switch (size) {
				case 8	-> GLFormatComponent.SIZE_8_MASK;
				case 16	-> GLFormatComponent.SIZE_16_MASK;
				case 32	-> GLFormatComponent.SIZE_32_MASK;
				case 64	-> GLFormatComponent.SIZE_64_MASK;
				default	-> throw new IllegalArgumentException("Invalid size: " + size);
			};

			return this;
		}
	}
}
