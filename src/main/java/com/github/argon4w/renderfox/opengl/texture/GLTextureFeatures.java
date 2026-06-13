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

package com.github.argon4w.renderfox.opengl.texture;

public class GLTextureFeatures {

	public static final int COMPLETE		= 1 << 0;
	public static final int MULTISAMPLED	= 1 << 1;
	public static final int MIPMAP			= 1 << 2;
	public static final int ARRAY			= 1 << 3;
	public static final int DEPTH			= 1 << 4;
	public static final int CUBE			= 1 << 5;
}
