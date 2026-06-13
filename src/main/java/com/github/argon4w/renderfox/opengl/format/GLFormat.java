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
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

public enum GLFormat implements IFormat<GLDataType> {

	INVALID			(-1,						0,	0,																																							GLDataType.INVALID),
	RED				(GL11.GL_RED,				1,	GLFormatComponent.RED_MASK,																																	GLDataType.FLOAT),
	RG				(GL30.GL_RG,				2,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK,																									GLDataType.FLOAT),
	RGB				(GL11.GL_RGB,				3,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK,																	GLDataType.FLOAT),
	BGR				(GL12.GL_BGR,				3,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK,																	GLDataType.FLOAT),
	RGBA			(GL11.GL_RGBA,				4,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK | GLFormatComponent.ALPHA_MASK,										GLDataType.FLOAT),
	BGRA			(GL12.GL_BGRA,				4,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK | GLFormatComponent.ALPHA_MASK,										GLDataType.FLOAT),
	RED_INTEGER		(GL30.GL_RED_INTEGER,		1,	GLFormatComponent.RED_MASK | GLFormatComponent.INTEGER_MASK,																								GLDataType.INT),
	RG_INTEGER		(GL30.GL_RG_INTEGER,		2,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.INTEGER_MASK,																	GLDataType.INT),
	RGB_INTEGER		(GL30.GL_RGB_INTEGER,		3,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK | GLFormatComponent.INTEGER_MASK,									GLDataType.INT),
	BGR_INTEGER		(GL30.GL_BGR_INTEGER,		3,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK | GLFormatComponent.INTEGER_MASK,									GLDataType.INT),
	RGBA_INTEGER	(GL30.GL_RGBA_INTEGER,		4,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK | GLFormatComponent.ALPHA_MASK | GLFormatComponent.INTEGER_MASK,	GLDataType.INT),
	BGRA_INTEGER	(GL30.GL_BGRA_INTEGER,		4,	GLFormatComponent.RED_MASK | GLFormatComponent.GREEN_MASK | GLFormatComponent.BLUE_MASK | GLFormatComponent.ALPHA_MASK | GLFormatComponent.INTEGER_MASK,	GLDataType.INT),
	STENCIL_INDEX	(GL11.GL_STENCIL_INDEX,		1,	GLFormatComponent.STENCIL_MASK,																																GLDataType.INT),
	DEPTH_COMPONENT	(GL11.GL_DEPTH_COMPONENT,	1,	GLFormatComponent.DEPTH_MASK,																																GLDataType.FLOAT),
	DEPTH_STENCIL	(GL30.GL_DEPTH_STENCIL,		1,	GLFormatComponent.DEPTH_MASK | GLFormatComponent.STENCIL_MASK,																								GLDataType.DEPTH32F_STENCIL8);

	private static final Int2ReferenceMap<GLFormat>	TABLE_CONSTANT;
	private static final Int2ReferenceMap<GLFormat>	TABLE_FLAGS;
	private static final Lookup						GLOBAL_LOOKUP;

	static {
		TABLE_CONSTANT	= new Int2ReferenceOpenHashMap<>();
		TABLE_FLAGS		= new Int2ReferenceOpenHashMap<>();
		GLOBAL_LOOKUP	= new Lookup					();

		for (var format : GLFormat.values()) {
			TABLE_CONSTANT	.put(format.constant,	format);
			TABLE_FLAGS		.put(format.flags,		format);
		}
	}

	private final int			constant;
	private final int			flags;
	private final int			count;
	private final int			componentFlags;
	private final GLDataType	type;

	GLFormat(
			int			constant,
			int			count,
			int			flags,
			GLDataType	type
	) {
		this.constant		= constant;
		this.flags			= flags;
		this.count			= count;
		this.type			= type;
		this.componentFlags	= flags & GLFormatComponent.COMPONENT_MASK;
	}

	public int getConstant() {
		return constant;
	}

	public int getFlags() {
		return flags;
	}

	public int getComponentCount() {
		return count;
	}

	@Override
	public GLDataType getType() {
		return type;
	}

	@Override
	public boolean isCompatible(IFormat<?> other) {
		return other instanceof GLFormat format && (componentFlags & format.componentFlags) == componentFlags;
	}

	@Override
	public boolean isInteger() {
		return (flags & GLFormatComponent.INTEGER_MASK) != 0;
	}

	@Override
	public boolean hasRed() {
		return (flags & GLFormatComponent.RED_MASK) != 0;
	}

	@Override
	public boolean hasGreen() {
		return (flags & GLFormatComponent.GREEN_MASK) != 0;
	}

	@Override
	public boolean hasBlue() {
		return (flags & GLFormatComponent.BLUE_MASK) != 0;
	}

	@Override
	public boolean hasAlpha() {
		return (flags & GLFormatComponent.ALPHA_MASK) != 0;
	}

	@Override
	public boolean hasDepth() {
		return (flags & GLFormatComponent.DEPTH_MASK) != 0;
	}

	@Override
	public boolean hasStencil() {
		return (flags & GLFormatComponent.STENCIL_MASK) != 0;
	}

	@Override
	public boolean hasColor() {
		return (flags & GLFormatComponent.COLOR_MASK) != 0;
	}

	public static GLFormat fromConstant(int constant) {
		return TABLE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLFormat fromFlags(int flags) {
		return TABLE_FLAGS.getOrDefault(flags, INVALID);
	}

	public static Lookup globalLookup() {
		return GLOBAL_LOOKUP;
	}

	public static Lookup newLookup() {
		return new Lookup();
	}

	public static class Lookup implements IFormat.Lookup {

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
		public IFormat.Lookup fromCommonFormat() {
			this.flags = GLFormat.RGBA.flags;
			return this;
		}

		@Override
		public Lookup fromFormat(IFormat<?> format) {
			this.flags = format instanceof GLFormat glFormat ? glFormat.flags : 0;
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

		public Lookup copy() {
			return new Lookup(flags);
		}

		public GLFormat find() {
			return GLFormat.fromFlags(flags);
		}
	}
}
