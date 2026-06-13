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

public final class ColorInt implements IColor {

	private final int colorRed;
	private final int colorGreen;
	private final int colorBlue;
	private final int colorAlpha;

	public ColorInt(
			int colorRed,
			int colorGreen,
			int colorBlue,
			int colorAlpha
	) {
		this.colorRed	= colorRed;
		this.colorGreen	= colorGreen;
		this.colorBlue	= colorBlue;
		this.colorAlpha	= colorAlpha;
	}

	public int getRed() {
		return colorRed;
	}

	public int getGreen() {
		return colorGreen;
	}

	public int getBlue() {
		return colorBlue;
	}

	public int getAlpha() {
		return colorAlpha;
	}

	@Override
	public ColorFloat asFloat() {
		return new ColorFloat(
				(float) colorRed,
				(float) colorGreen,
				(float) colorBlue,
				(float) colorAlpha
		);
	}

	@Override
	public ColorInt asInt() {
		return this;
	}
}
