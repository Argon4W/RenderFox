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

package com.github.argon4w.renderfox.opengl.texture.sampler.object.feature;

import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareMode;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLTextureFilter;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureWrapFunction;

public interface IGLSamplerStore {

	GLTextureCompareFunction	getCompareFunction	();
	GLTextureCompareMode		getCompareMode		();
	GLTextureWrapFunction		getWrapS			();
	GLTextureWrapFunction		getWrapT			();
	GLTextureWrapFunction		getWrapR			();
	GLTextureFilter				getMinFilter		();
	GLTextureFilter				getMagFilter		();
	ColorFloat					getBorderColorFloat	();
	ColorInt					getBorderColorInt	();
	float						getMinLOD			();
	float						getMaxLOD			();
	float						getLODBias			();
	float						getMaxAnisotropy	();
}
