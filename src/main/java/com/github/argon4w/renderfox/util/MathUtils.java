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

package com.github.argon4w.renderfox.util;

public class MathUtils {

	public static final double LOGE2 = Math.log(2.0);

	public static double log2(double a) {
		return Math.log(a) / LOGE2;
	}

	public static long ceilDiv(long value, long factor) {
		return ((value + factor - 1L) / factor);
	}

	public static int ceilDiv(int value, int factor) {
		return ((value + factor - 1) / factor);
	}

	public static long norm(double value, long bits) {
		return (long) (Math.clamp(value, 0.0f, 1.0f) * ((1L << bits * 8L) - 1));
	}
}
