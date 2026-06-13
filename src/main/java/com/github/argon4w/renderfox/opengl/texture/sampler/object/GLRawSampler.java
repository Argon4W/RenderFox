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

import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.format.IColor;
import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareMode;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureWrapFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLTextureFilter;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;
import com.github.argon4w.renderfox.opengl.texture.sampler.functions.GLSamplerFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.feature.*;

public class GLRawSampler extends AbstractGLSamplerStore implements IGLSampler {

	private final	GLSamplerFunctionsHelper	samplerHelper;
	private			GLTextureCompareFunction	compareFunction;
	private			GLTextureCompareMode		compareMode;
	private			GLTextureWrapFunction		wrapS;
	private			GLTextureWrapFunction		wrapT;
	private			GLTextureWrapFunction		wrapR;
	private			GLTextureFilter				minFilter;
	private			GLTextureFilter				magFilter;
	private			IColor						borderColor;
	private			float						minLOD;
	private			float						maxLOD;
	private			float						LODBias;
	private			float						maxAnisotropy;

	private			boolean						deleted;

	public GLRawSampler(GLTextureContext textureContext) {
		this.samplerHelper		= textureContext			.createSamplerHelper(textureContext.createSamplerHandle());
		this.compareFunction	= GLTextureCompareFunction	.LEQUAL;
		this.compareMode		= GLTextureCompareMode		.NONE;
		this.wrapS				= GLTextureWrapFunction		.REPEAT;
		this.wrapT				= GLTextureWrapFunction		.REPEAT;
		this.wrapR				= GLTextureWrapFunction		.REPEAT;
		this.minFilter			= GLTextureFilter			.NEAREST_MIPMAP_LINEAR;
		this.magFilter			= GLTextureFilter			.LINEAR;
		this.borderColor		= null;
		this.minLOD				= -1000.0f;
		this.maxLOD				= +1000.0f;
		this.LODBias			= 0.0f;
		this.maxAnisotropy		= 1.0f;

		this.deleted		= false;
	}

	@Override
	public void setParameterInt(GLTextureParameter parameter, int value) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		samplerHelper.setParameterInt(parameter, value);
	}

	@Override
	public void setParameterFloat(GLTextureParameter parameter, float value) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		samplerHelper.setParameterFloat(parameter, value);
	}

	@Override
	public void setParameterFloats(GLTextureParameter parameter, IDataView<?> dataView) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		samplerHelper.setParameterFloats(parameter, dataView);
	}

	@Override
	public void setParameterRawInts(GLTextureParameter parameter, IDataView<?> dataView) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		samplerHelper.setParameterRawInts(parameter, dataView);
	}

	@Override
	public void bind(int textureUnit) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		samplerHelper.bind(textureUnit);
	}

	@Override
	public void setCompareFunction(GLTextureCompareFunction compareFunction) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setCompareFunction(compareFunction);

		this.compareFunction = compareFunction;
	}

	@Override
	public void setCompareMode(GLTextureCompareMode compareMode) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setCompareMode(compareMode);

		this.compareMode = compareMode;
	}

	@Override
	public void setWrapS(GLTextureWrapFunction wrapS) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setWrapS(wrapS);

		this.wrapS = wrapS;
	}

	@Override
	public void setWrapT(GLTextureWrapFunction wrapT) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setWrapT(wrapT);

		this.wrapT = wrapT;
	}

	@Override
	public void setWrapR(GLTextureWrapFunction wrapR) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setWrapR(wrapR);

		this.wrapR = wrapR;
	}

	@Override
	public void setMinFilter(GLTextureFilter minFilter) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setMinFilter(minFilter);

		this.minFilter = minFilter;
	}

	@Override
	public void setMagFilter(GLTextureFilter magFilter) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setMagFilter(magFilter);

		this.magFilter = magFilter;
	}

	@Override
	public void setBorderColorFloat(ColorFloat borderColor) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setBorderColorFloat(borderColor);

		this.borderColor = borderColor;
	}

	@Override
	public void setBorderColorInt(ColorInt borderColor) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setBorderColorInt(borderColor);

		this.borderColor = borderColor;
	}

	@Override
	public void setMinLOD(float minLOD) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setMinLOD(minLOD);

		this.minLOD = minLOD;
	}

	@Override
	public void setMaxLOD(float maxLOD) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setMaxLOD(maxLOD);

		this.maxLOD = maxLOD;
	}

	@Override
	public void setLODBias(float LODBias) {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		super.setLODBias(LODBias);

		this.LODBias = LODBias;
	}

	@Override
	public void delete() {
		if (deleted) {
			throw new IllegalStateException("The sampler has been deleted.");
		}

		deleted = true;

		samplerHelper.delete();
	}

	@Override
	public int getSamplerHandle() {
		return samplerHelper.getSamplerHandle();
	}

	@Override
	public void setMaxAnisotropy(float maxAnisotropy) {
		this.maxAnisotropy = maxAnisotropy;
	}

	@Override
	public GLTextureCompareFunction getCompareFunction() {
		return compareFunction;
	}

	@Override
	public GLTextureCompareMode getCompareMode() {
		return compareMode;
	}

	@Override
	public GLTextureWrapFunction getWrapS() {
		return wrapS;
	}

	@Override
	public GLTextureWrapFunction getWrapT() {
		return wrapT;
	}

	@Override
	public GLTextureWrapFunction getWrapR() {
		return wrapR;
	}

	@Override
	public GLTextureFilter getMinFilter() {
		return minFilter;
	}

	@Override
	public GLTextureFilter getMagFilter() {
		return magFilter;
	}

	@Override
	public ColorFloat getBorderColorFloat() {
		return borderColor.asFloat();
	}

	@Override
	public ColorInt getBorderColorInt() {
		return borderColor.asInt();
	}

	@Override
	public float getMinLOD() {
		return minLOD;
	}

	@Override
	public float getMaxLOD() {
		return maxLOD;
	}

	@Override
	public float getLODBias() {
		return LODBias;
	}

	@Override
	public float getMaxAnisotropy() {
		return maxAnisotropy;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}
}
