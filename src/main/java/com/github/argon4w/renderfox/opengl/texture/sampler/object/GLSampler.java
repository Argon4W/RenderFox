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

package com.github.argon4w.renderfox.opengl.texture.sampler.object;

import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareMode;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureWrapFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLTextureFilter;

public class GLSampler implements IGLSampler {

	private final GLRawSampler sampler;

	public GLSampler(GLRawSampler sampler) {
		this.sampler = sampler;
	}

	@Override
	public void bind(int textureUnit) {
		sampler.bind(textureUnit);
	}

	@Override
	public void delete() {
		sampler.delete();
	}

	@Override
	public GLTextureCompareFunction getCompareFunction() {
		return sampler.getCompareFunction();
	}

	@Override
	public GLTextureCompareMode getCompareMode() {
		return sampler.getCompareMode();
	}

	@Override
	public GLTextureWrapFunction getWrapS() {
		return sampler.getWrapS();
	}

	@Override
	public GLTextureWrapFunction getWrapT() {
		return sampler.getWrapT();
	}

	@Override
	public GLTextureWrapFunction getWrapR() {
		return sampler.getWrapR();
	}

	@Override
	public GLTextureFilter getMinFilter() {
		return sampler.getMinFilter();
	}

	@Override
	public GLTextureFilter getMagFilter() {
		return sampler.getMagFilter();
	}

	@Override
	public ColorFloat getBorderColorFloat() {
		return sampler.getBorderColorFloat();
	}

	@Override
	public ColorInt getBorderColorInt() {
		return sampler.getBorderColorInt();
	}

	@Override
	public float getMinLOD() {
		return sampler.getMinLOD();
	}

	@Override
	public float getMaxLOD() {
		return sampler.getMaxLOD();
	}

	@Override
	public float getLODBias() {
		return sampler.getLODBias();
	}

	@Override
	public float getMaxAnisotropy() {
		return sampler.getMaxAnisotropy();
	}

	@Override
	public int getSamplerHandle() {
		return sampler.getSamplerHandle();
	}

	@Override
	public boolean isDeleted() {
		return sampler.isDeleted();
	}
}
