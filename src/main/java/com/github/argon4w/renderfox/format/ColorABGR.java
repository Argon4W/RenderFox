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

package com.github.argon4w.renderfox.format;

public class ColorABGR {

	public static final int ALPHA_OFFSET	= 24;
	public static final int BLUE_OFFSET		= 16;
	public static final int GREEN_OFFSET	= 8;
	public static final int RED_OFFSET		= 0;

	public static int of(
			int alpha,
			int red,
			int green,
			int blue
	) {
		return 		((alpha	& 0xFF) << ALPHA_OFFSET)
				|	((blue	& 0xFF) << BLUE_OFFSET)
				|	((green	& 0xFF) << GREEN_OFFSET)
				|	((red	& 0xFF) << RED_OFFSET);
	}

	public static int of(
			float alpha,
			float red,
			float green,
			float blue
	) {
		return of(
				(int) (alpha	* 255.0f),
				(int) (red		* 255.0f),
				(int) (green	* 255.0f),
				(int) (blue		* 255.0f)
		);
	}

	public static int fromARGB(int color) {
		return		(color & 0xFF00FF00)
				| (	(color & 0x00FF0000) >> 16)
				| (	(color & 0x000000FF) << 16);
	}

	public static int toARGB(int color) {
		return fromARGB(color);
	}
}
