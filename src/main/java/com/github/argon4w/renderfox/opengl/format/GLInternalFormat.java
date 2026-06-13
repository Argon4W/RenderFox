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

import com.github.argon4w.renderfox.format.IFormat;
import com.github.argon4w.renderfox.format.IGPUFormat;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.*;

public enum GLInternalFormat implements IGPUFormat<GLDataType> {

	INVALID				(-1,							GLFormat.INVALID,			0,																											0,	GLDataType.INVALID),
	R8					(GL30.GL_R8,					GLFormat.RED,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_8_MASK,	1,	GLDataType.UNSIGNED_BYTE),
	RG8					(GL30.GL_RG8,					GLFormat.RG,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_8_MASK,	2,	GLDataType.UNSIGNED_BYTE),
	RGB8				(GL11.GL_RGB8,					GLFormat.RGB,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_8_MASK,	3,	GLDataType.UNSIGNED_BYTE),
	RGBA8				(GL11.GL_RGBA8,					GLFormat.RGBA,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_8_MASK,	4,	GLDataType.UNSIGNED_BYTE),
	R8_SNORM			(GL31.GL_R8_SNORM,				GLFormat.RED,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	1,	GLDataType.BYTE),
	RG8_SNORM			(GL31.GL_RG8_SNORM,				GLFormat.RG,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	2,	GLDataType.BYTE),
	RGB8_SNORM			(GL31.GL_RGB8_SNORM,			GLFormat.RGB,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	3,	GLDataType.BYTE),
	RGBA8_SNORM			(GL31.GL_RGBA8_SNORM,			GLFormat.RGBA,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	4,	GLDataType.BYTE),
	R16					(GL30.GL_R16,					GLFormat.RED,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_16_MASK,	2,	GLDataType.UNSIGNED_SHORT),
	RG16				(GL30.GL_RG16,					GLFormat.RG,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_16_MASK,	4,	GLDataType.UNSIGNED_SHORT),
	RGB16				(GL11.GL_RGB16,					GLFormat.RGB,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_16_MASK,	6,	GLDataType.UNSIGNED_SHORT),
	RGBA16				(GL11.GL_RGBA16,				GLFormat.RGBA,				GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_16_MASK,	8,	GLDataType.UNSIGNED_SHORT),
	R16_SNORM			(GL31.GL_R16_SNORM,				GLFormat.RED,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	2,	GLDataType.SHORT),
	RG16_SNORM			(GL31.GL_RG16_SNORM,			GLFormat.RG,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	4,	GLDataType.SHORT),
	RGB16_SNORM			(GL31.GL_RGB16_SNORM,			GLFormat.RGB,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	6,	GLDataType.SHORT),
	RGBA16_SNORM		(GL31.GL_RGBA16_SNORM,			GLFormat.RGBA,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	8,	GLDataType.SHORT),
	R16F				(GL30.GL_R16F,					GLFormat.RED,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	2,	GLDataType.HALF_FLOAT),
	RG16F				(GL30.GL_RG16F,					GLFormat.RG,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	4,	GLDataType.HALF_FLOAT),
	RGB16F				(GL30.GL_RGB16F,				GLFormat.RGB,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	6,	GLDataType.HALF_FLOAT),
	RGBA16F				(GL30.GL_RGBA16F,				GLFormat.RGBA,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	8,	GLDataType.HALF_FLOAT),
	R32F				(GL30.GL_R32F,					GLFormat.RED,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	4,	GLDataType.FLOAT),
	RG32F				(GL30.GL_R32F,					GLFormat.RG,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	8,	GLDataType.FLOAT),
	RGB32F				(GL30.GL_R32F,					GLFormat.RGB,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	12,	GLDataType.FLOAT),
	RGBA32F				(GL30.GL_R32F,					GLFormat.RGBA,														GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	16,	GLDataType.FLOAT),
	R8I					(GL30.GL_R8I,					GLFormat.RED_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	1,	GLDataType.BYTE),
	RG8I				(GL30.GL_RG8I,					GLFormat.RG_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	2,	GLDataType.BYTE),
	RGB8I				(GL30.GL_RGB8I,					GLFormat.RGB_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	3,	GLDataType.BYTE),
	RGBA8I				(GL30.GL_RGBA8I,				GLFormat.RGBA_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_8_MASK,	4,	GLDataType.BYTE),
	R8UI				(GL30.GL_R8UI,					GLFormat.RED_INTEGER,																					GLFormatComponent.SIZE_8_MASK,	1,	GLDataType.UNSIGNED_BYTE),
	RG8UI				(GL30.GL_RG8UI,					GLFormat.RG_INTEGER,																					GLFormatComponent.SIZE_8_MASK,	2,	GLDataType.UNSIGNED_BYTE),
	RGB8UI				(GL30.GL_RGB8UI,				GLFormat.RGB_INTEGER,																					GLFormatComponent.SIZE_8_MASK,	3,	GLDataType.UNSIGNED_BYTE),
	RGBA8UI				(GL30.GL_RGBA8UI,				GLFormat.RGBA_INTEGER,																					GLFormatComponent.SIZE_8_MASK,	4,	GLDataType.UNSIGNED_BYTE),
	R16I				(GL30.GL_R16I,					GLFormat.RED_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	2,	GLDataType.SHORT),
	RG16I				(GL30.GL_RG16I,					GLFormat.RG_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	4,	GLDataType.SHORT),
	RGB16I				(GL30.GL_RGB16I,				GLFormat.RGB_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	6,	GLDataType.SHORT),
	RGBA16I				(GL30.GL_RGBA16I,				GLFormat.RGBA_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_16_MASK,	8,	GLDataType.SHORT),
	R16UI				(GL30.GL_R16UI,					GLFormat.RED_INTEGER,																					GLFormatComponent.SIZE_16_MASK,	2,	GLDataType.UNSIGNED_SHORT),
	RG16UI				(GL30.GL_RG16UI,				GLFormat.RG_INTEGER,																					GLFormatComponent.SIZE_16_MASK,	4,	GLDataType.UNSIGNED_SHORT),
	RGB16UI				(GL30.GL_RGB16UI,				GLFormat.RGB_INTEGER,																					GLFormatComponent.SIZE_16_MASK,	6,	GLDataType.UNSIGNED_SHORT),
	RGBA16UI			(GL30.GL_RGBA16UI,				GLFormat.RGBA_INTEGER,																					GLFormatComponent.SIZE_16_MASK,	8,	GLDataType.UNSIGNED_SHORT),
	R32I				(GL30.GL_R32I,					GLFormat.RED_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	4,	GLDataType.INT),
	RG32I				(GL30.GL_RG32I,					GLFormat.RG_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	8,	GLDataType.INT),
	RGB32I				(GL30.GL_RGB32I,				GLFormat.RGB_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	12,	GLDataType.INT),
	RGBA32I				(GL30.GL_RGBA32I,				GLFormat.RGBA_INTEGER,												GLFormatComponent.SIGNED_MASK	|	GLFormatComponent.SIZE_32_MASK,	16,	GLDataType.INT),
	R32UI				(GL30.GL_R32UI,					GLFormat.RED_INTEGER,																					GLFormatComponent.SIZE_32_MASK,	4,	GLDataType.UNSIGNED_INT),
	RG32UI				(GL30.GL_RG32UI,				GLFormat.RG_INTEGER,																					GLFormatComponent.SIZE_32_MASK,	8,	GLDataType.UNSIGNED_INT),
	RGB32UI				(GL30.GL_RGB32UI,				GLFormat.RGB_INTEGER,																					GLFormatComponent.SIZE_32_MASK,	12,	GLDataType.UNSIGNED_INT),
	RGBA32UI			(GL30.GL_RGBA32UI,				GLFormat.RGBA_INTEGER,																					GLFormatComponent.SIZE_32_MASK,	16,	GLDataType.UNSIGNED_INT),
	SRGB8				(GL21.GL_SRGB8,					GLFormat.RGB,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SRGB_MASK		|	GLFormatComponent.SIZE_8_MASK,	3,	GLDataType.UNSIGNED_BYTE),
	SRGB8_ALPHA8		(GL21.GL_SRGB8_ALPHA8,			GLFormat.RGBA,				GLFormatComponent.NORMALIZED_MASK	|	GLFormatComponent.SRGB_MASK		|	GLFormatComponent.SIZE_8_MASK,	4,	GLDataType.UNSIGNED_BYTE),
	DEPTH16				(GL14.GL_DEPTH_COMPONENT16,		GLFormat.DEPTH_COMPONENT,	GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_16_MASK,	2,	GLDataType.UNSIGNED_SHORT),
	DEPTH32				(GL14.GL_DEPTH_COMPONENT32,		GLFormat.DEPTH_COMPONENT,	GLFormatComponent.NORMALIZED_MASK										|	GLFormatComponent.SIZE_32_MASK,	4,	GLDataType.UNSIGNED_INT),
	DEPTH32F			(GL30.GL_DEPTH_COMPONENT32F,	GLFormat.DEPTH_COMPONENT,																				GLFormatComponent.SIZE_32_MASK,	4,	GLDataType.FLOAT),
	STENCIL8			(GL30.GL_STENCIL_INDEX8,		GLFormat.STENCIL_INDEX,																					GLFormatComponent.SIZE_8_MASK,	1,	GLDataType.UNSIGNED_BYTE),
	STENCIL16			(GL30.GL_STENCIL_INDEX16,		GLFormat.STENCIL_INDEX,																					GLFormatComponent.SIZE_16_MASK,	2,	GLDataType.UNSIGNED_SHORT),
	DEPTH24_STENCIL8	(GL30.GL_DEPTH24_STENCIL8,		GLFormat.DEPTH_STENCIL,		GLFormatComponent.NORMALIZED_MASK,																			32,	GLDataType.DEPTH24_STENCIL8),
	DEPTH32F_STENCIL8	(GL30.GL_DEPTH32F_STENCIL8,		GLFormat.DEPTH_STENCIL,		0,																											40,	GLDataType.DEPTH32F_STENCIL8);

	private static final Int2ReferenceMap<GLInternalFormat>	TABLE_CONSTANT;
	private static final Int2ReferenceMap<GLInternalFormat>	TABLE_FLAGS;
	private static final Lookup								GLOBAL_LOOKUP;

	static {
		TABLE_CONSTANT	= new Int2ReferenceOpenHashMap<>();
		TABLE_FLAGS		= new Int2ReferenceOpenHashMap<>();
		GLOBAL_LOOKUP	= new Lookup					();

		for (var internalFormat : GLInternalFormat.values()) {
			TABLE_CONSTANT	.put(internalFormat.constant,	internalFormat);
			TABLE_FLAGS		.put(internalFormat.flags,		internalFormat);
		}
	}

	private final GLFormat		format;
	private final GLDataType	type;
	private final int			constant;
	private final int			flags;
	private final int			size;

	GLInternalFormat(
			int			constant,
			GLFormat	format,
			int			flags,
			int			size,
			GLDataType	type
	) {
		this.constant	= constant;
		this.format		= format;
		this.type		= type;
		this.flags		= flags | format.getFlags();
		this.size		= size;
	}

	public GLFormat getFormat() {
		return format;
	}

	public int getConstant() {
		return constant;
	}

	public int getFlags() {
		return flags;
	}

	@Override
	public GLDataType getFormatType() {
		return type;
	}

	@Override
	public long getFormatSize() {
		return size;
	}

	@Override
	public GLDataType getType() {
		return format.getType();
	}

	@Override
	public boolean isCompatible(IFormat<?> other) {
		return format.isCompatible(other);
	}

	@Override
	public boolean isNormalized() {
		return (flags & GLFormatComponent.NORMALIZED_MASK) != 0;
	}

	@Override
	public boolean isSigned() {
		return (flags & GLFormatComponent.SIGNED_MASK) != 0;
	}

	@Override
	public boolean isSRGB() {
		return (flags & GLFormatComponent.SRGB_MASK) != 0;
	}

	@Override
	public boolean isInteger() {
		return format.isInteger();
	}

	@Override
	public boolean hasRed() {
		return format.hasRed();
	}

	@Override
	public boolean hasGreen() {
		return format.hasGreen();
	}

	@Override
	public boolean hasBlue() {
		return format.hasBlue();
	}

	@Override
	public boolean hasAlpha() {
		return format.hasAlpha();
	}

	@Override
	public boolean hasDepth() {
		return format.hasDepth();
	}

	@Override
	public boolean hasStencil() {
		return format.hasStencil();
	}

	@Override
	public boolean hasColor() {
		return format.hasColor();
	}

	public static GLInternalFormat fromConstant(int constant) {
		return TABLE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLInternalFormat fromFlags(int flags) {
		return TABLE_FLAGS.getOrDefault(flags, INVALID);
	}

	public static Lookup globalLookup() {
		return GLOBAL_LOOKUP;
	}

	public static Lookup newLookup() {
		return new Lookup();
	}

	public static class Lookup implements IGPUFormat.Lookup {

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
		public Lookup fromCommonGPUFormat() {
			this.flags = GLInternalFormat.RGBA8.flags;
			return this;
		}

		@Override
		public Lookup fromFormat(IFormat<?> format) {
			this.flags = format instanceof GLFormat glFormat ? glFormat.getFlags() : 0;
			return this;
		}

		@Override
		public Lookup fromGPUFormat(IGPUFormat<?> gpuFormat) {
			this.flags = gpuFormat instanceof GLInternalFormat glInternalFormat ? glInternalFormat.flags : 0;
			return this;
		}

		@Override
		public Lookup withSRGB() {
			this.flags |= GLFormatComponent.SRGB_MASK;
			return this;
		}

		@Override
		public Lookup withoutSRGB() {
			this.flags &= ~GLFormatComponent.SRGB_MASK;
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
		public Lookup withNormalized() {
			this.flags |= GLFormatComponent.NORMALIZED_MASK;
			return this;
		}

		@Override
		public Lookup withUnnormalized() {
			this.flags &= ~GLFormatComponent.NORMALIZED_MASK;
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
		public Lookup withRed() {
			this.flags |= GLFormatComponent.RED_MASK;
			return this;
		}

		@Override
		public Lookup withoutRed() {
			this.flags &= ~GLFormatComponent.RED_MASK;
			return this;
		}

		@Override
		public Lookup withGreen() {
			this.flags |= GLFormatComponent.GREEN_MASK;
			return this;
		}

		@Override
		public Lookup withoutGreen() {
			this.flags &= ~GLFormatComponent.GREEN_MASK;
			return this;
		}

		@Override
		public Lookup withBlue() {
			this.flags |= GLFormatComponent.BLUE_MASK;
			return this;
		}

		@Override
		public Lookup withoutBlue() {
			this.flags &= ~GLFormatComponent.BLUE_MASK;
			return this;
		}

		@Override
		public Lookup withAlpha() {
			this.flags |= GLFormatComponent.ALPHA_MASK;
			return this;
		}

		@Override
		public Lookup withoutAlpha() {
			this.flags &= ~GLFormatComponent.ALPHA_MASK;
			return this;
		}

		@Override
		public Lookup withDepth() {
			this.flags |= GLFormatComponent.DEPTH_MASK;
			return this;
		}

		@Override
		public Lookup withoutDepth() {
			this.flags &= ~GLFormatComponent.DEPTH_MASK;
			return this;
		}

		@Override
		public Lookup withStencil() {
			this.flags |= GLFormatComponent.STENCIL_MASK;
			return this;
		}

		@Override
		public Lookup withoutStencil() {
			this.flags &= ~GLFormatComponent.STENCIL_MASK;
			return this;
		}

		@Override
		public Lookup withDepthStencil() {
			this.flags |=	GLFormatComponent.DEPTH_MASK
					|		GLFormatComponent.STENCIL_MASK;
			return this;
		}

		@Override
		public Lookup withStencilStencil() {
			this.flags &= ~(GLFormatComponent.DEPTH_MASK
					|		GLFormatComponent.STENCIL_MASK);
			return this;
		}

		@Override
		public Lookup withRGB() {
			this.flags |=	GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK;
			return this;
		}

		@Override
		public Lookup withoutRGB() {
			this.flags &= ~(GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK);
			return this;
		}

		@Override
		public Lookup withRGBA() {
			this.flags |=	GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK
					|		GLFormatComponent.ALPHA_MASK;
			return this;
		}

		@Override
		public Lookup withoutRGBA() {
			this.flags &= ~(GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK
					|		GLFormatComponent.ALPHA_MASK);
			return this;
		}

		@Override
		public Lookup withRGBInteger() {
			this.flags |=	GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK
					|		GLFormatComponent.INTEGER_MASK;
			return this;
		}

		@Override
		public Lookup withoutRGBInteger() {
			this.flags &= ~(GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK
					|		GLFormatComponent.INTEGER_MASK);
			return this;
		}

		@Override
		public Lookup withRGBAInteger() {
			this.flags |=	GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK
					|		GLFormatComponent.ALPHA_MASK
					|		GLFormatComponent.INTEGER_MASK;
			return this;
		}

		@Override
		public Lookup withoutRGBAInteger() {
			this.flags &= ~(GLFormatComponent.RED_MASK
					|		GLFormatComponent.GREEN_MASK
					|		GLFormatComponent.BLUE_MASK
					|		GLFormatComponent.ALPHA_MASK
					|		GLFormatComponent.INTEGER_MASK);
			return this;
		}

		@Override
		public Lookup withSize(int size) {
			this.flags &= ~ GLFormatComponent.SIZE_MASK;

			this.flags |= switch (size) {
				case 1	-> GLFormatComponent.SIZE_1_MASK;
				case 4	-> GLFormatComponent.SIZE_4_MASK;
				case 8	-> GLFormatComponent.SIZE_8_MASK;
				case 16	-> GLFormatComponent.SIZE_16_MASK;
				case 24	-> GLFormatComponent.SIZE_24_MASK;
				case 32	-> GLFormatComponent.SIZE_32_MASK;
				default	-> throw new IllegalArgumentException("Invalid size: " + size);
			};

			return this;
		}

		public Lookup copy() {
			return new Lookup(flags);
		}

		public GLInternalFormat find() {
			return GLInternalFormat.fromFlags(flags);
		}
	}
}
