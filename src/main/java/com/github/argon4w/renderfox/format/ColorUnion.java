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

import com.github.argon4w.renderfox.data.UnionValue;

public class ColorUnion {

	private final UnionValue	colorRed;
	private final UnionValue	colorGreen;
	private final UnionValue	colorBlue;
	private final UnionValue	colorAlpha;
	private final boolean		colorFloat;

	public ColorUnion() {
		this.colorRed	= new UnionValue(0.0f);
		this.colorGreen	= new UnionValue(0.0f);
		this.colorBlue	= new UnionValue(0.0f);
		this.colorAlpha	= new UnionValue(0.0f);
		this.colorFloat	= true;
	}

	public ColorUnion(ColorFloat color) {
		this.colorRed	= new UnionValue(color.getRed	());
		this.colorGreen	= new UnionValue(color.getGreen	());
		this.colorBlue	= new UnionValue(color.getBlue	());
		this.colorAlpha	= new UnionValue(color.getAlpha	());
		this.colorFloat	= true;
	}

	public ColorUnion(ColorInt color) {
		this.colorRed	= new UnionValue(color.getRed	());
		this.colorGreen	= new UnionValue(color.getGreen	());
		this.colorBlue	= new UnionValue(color.getBlue	());
		this.colorAlpha	= new UnionValue(color.getAlpha	());
		this.colorFloat	= false;
	}

	public ColorUnion(
			float colorRed,
			float colorGreen,
			float colorBlue,
			float colorAlpha
	) {
		this.colorRed	= new UnionValue(colorRed);
		this.colorGreen	= new UnionValue(colorGreen);
		this.colorBlue	= new UnionValue(colorBlue);
		this.colorAlpha	= new UnionValue(colorAlpha);
		this.colorFloat	= true;
	}

	public ColorUnion(
			int colorRed,
			int colorGreen,
			int colorBlue,
			int colorAlpha
	) {
		this.colorRed	= new UnionValue(colorRed);
		this.colorGreen	= new UnionValue(colorGreen);
		this.colorBlue	= new UnionValue(colorBlue);
		this.colorAlpha	= new UnionValue(colorAlpha);
		this.colorFloat	= false;
	}

	public ColorFloat asRawFloat() {
		return new ColorFloat(
				colorRed	.asFloat(),
				colorGreen	.asFloat(),
				colorBlue	.asFloat(),
				colorAlpha	.asFloat()
		);
	}

	public ColorFloat asFloat() {
		return new ColorFloat(
				(float) colorRed	.asInt(),
				(float) colorGreen	.asInt(),
				(float) colorBlue	.asInt(),
				(float) colorAlpha	.asInt()
		);
	}

	public ColorInt asInt() {
		return new ColorInt(
				colorRed	.asInt(),
				colorGreen	.asInt(),
				colorBlue	.asInt(),
				colorAlpha	.asInt()
		);
	}

	public boolean isFloat() {
		return colorFloat;
	}
}
