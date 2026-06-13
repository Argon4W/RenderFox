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

public final class ColorFloat implements IColor {

	private final float colorRed;
	private final float colorGreen;
	private final float colorBlue;
	private final float colorAlpha;

	public ColorFloat(
			float colorRed,
			float colorGreen,
			float colorBlue,
			float colorAlpha
	) {
		this.colorRed	= colorRed;
		this.colorGreen	= colorGreen;
		this.colorBlue	= colorBlue;
		this.colorAlpha	= colorAlpha;
	}

	public ColorFloat() {
		this.colorRed	= 0.0f;
		this.colorGreen	= 0.0f;
		this.colorBlue	= 0.0f;
		this.colorAlpha	= 1.0f;
	}

	public float getRed() {
		return colorRed;
	}

	public float getGreen() {
		return colorGreen;
	}

	public float getBlue() {
		return colorBlue;
	}

	public float getAlpha() {
		return colorAlpha;
	}

	public int toABGR() {
		return ColorABGR.of(
				colorRed,
				colorGreen,
				colorBlue,
				colorAlpha
		);
	}

	@Override
	public ColorFloat asFloat() {
		return this;
	}

	@Override
	public ColorInt asInt() {
		return new ColorInt(
				(int) colorRed,
				(int) colorGreen,
				(int) colorBlue,
				(int) colorAlpha
		);
	}
}
