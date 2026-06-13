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

public class GLFormatComponent {

	public static final int COMPONENT_MASK	= 0b111111;
	public static final int COLOR_MASK		= 0b1111;
	public static final int RED_MASK		= 1 << 0;
	public static final int GREEN_MASK		= 1 << 1;
	public static final int BLUE_MASK		= 1 << 2;
	public static final int ALPHA_MASK		= 1 << 3;
	public static final int DEPTH_MASK		= 1 << 4;
	public static final int STENCIL_MASK	= 1 << 5;
	public static final int INTEGER_MASK	= 1 << 6;

	public static final int SIGNED_MASK		= 1		<< 7;
	public static final int NORMALIZED_MASK	= 1		<< 8;
	public static final int SRGB_MASK		= 1		<< 9;
	public static final int SIZE_MASK		= 0b111	<< 10;
	public static final int SIZE_1_MASK		= 0b000	<< 10;
	public static final int SIZE_4_MASK		= 0b001	<< 10;
	public static final int SIZE_8_MASK		= 0b010	<< 10;
	public static final int SIZE_16_MASK	= 0b011	<< 10;
	public static final int SIZE_24_MASK	= 0b100	<< 10;
	public static final int SIZE_32_MASK	= 0b101	<< 10;
	public static final int SIZE_64_MASK	= 0b110 << 10;
}
