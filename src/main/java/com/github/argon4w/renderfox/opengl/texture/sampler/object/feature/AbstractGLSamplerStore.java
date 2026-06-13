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

import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareMode;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLTextureFilter;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureWrapFunction;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;

public class AbstractGLSamplerStore implements IGLSamplerStore, IGLSamplerModifier, IGLSamplerParameter {

	@Override
	public void setParameterInt(GLTextureParameter parameter, int value) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public void setParameterFloat(GLTextureParameter parameter, float value) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public void setParameterFloats(GLTextureParameter parameter, IDataView<?> dataView) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public void setParameterRawInts(GLTextureParameter parameter, IDataView<?> dataView) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getParameterInt(GLTextureParameter parameter) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public float getParameterFloat(GLTextureParameter parameter) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public <T extends IDataView<?>> T getParameterFloats(GLTextureParameter parameter, T outDataView) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public <T extends IDataView<?>> T getParameterRawInts(GLTextureParameter parameter, T outDataView) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public GLTextureCompareFunction getCompareFunction() {
		return GLTextureCompareFunction.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_COMPARE_FUNC));
	}

	@Override
	public GLTextureCompareMode getCompareMode() {
		return GLTextureCompareMode.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_COMPARE_MODE));
	}

	@Override
	public GLTextureWrapFunction getWrapS() {
		return GLTextureWrapFunction.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_WRAP_S));
	}

	@Override
	public GLTextureWrapFunction getWrapT() {
		return GLTextureWrapFunction.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_WRAP_T));
	}

	@Override
	public GLTextureWrapFunction getWrapR() {
		return GLTextureWrapFunction.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_WRAP_R));
	}

	@Override
	public GLTextureFilter getMinFilter() {
		return GLTextureFilter.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_MIN_FILTER));
	}

	@Override
	public GLTextureFilter getMagFilter() {
		return GLTextureFilter.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_MAG_FILTER));
	}

	@Override
	public float getMinLOD() {
		return getParameterFloat(GLTextureParameter.TEXTURE_MIN_LOD);
	}

	@Override
	public float getMaxLOD() {
		return getParameterFloat(GLTextureParameter.TEXTURE_MAX_LOD);
	}

	@Override
	public float getLODBias() {
		return getParameterFloat(GLTextureParameter.TEXTURE_LOD_BIAS);
	}

	@Override
	public float getMaxAnisotropy() {
		return getParameterFloat(GLTextureParameter.TEXTURE_MAX_ANISOTROPIC);
	}

	@Override
	public void setCompareFunction(GLTextureCompareFunction compareFunction) {
		setParameterInt(GLTextureParameter.TEXTURE_COMPARE_FUNC, compareFunction.getConstant());
	}

	@Override
	public void setCompareMode(GLTextureCompareMode compareMode) {
		setParameterInt(GLTextureParameter.TEXTURE_COMPARE_MODE, compareMode.getConstant());
	}

	@Override
	public void setWrapS(GLTextureWrapFunction wrapFunction) {
		setParameterInt(GLTextureParameter.TEXTURE_WRAP_S, wrapFunction.getConstant());
	}

	@Override
	public void setWrapT(GLTextureWrapFunction wrapFunction) {
		setParameterInt(GLTextureParameter.TEXTURE_WRAP_T, wrapFunction.getConstant());
	}

	@Override
	public void setWrapR(GLTextureWrapFunction wrapFunction) {
		setParameterInt(GLTextureParameter.TEXTURE_WRAP_R, wrapFunction.getConstant());
	}

	@Override
	public void setMinFilter(GLTextureFilter minFilter) {
		setParameterInt(GLTextureParameter.TEXTURE_MIN_FILTER, minFilter.getConstant());
	}

	@Override
	public void setMagFilter(GLTextureFilter magFilter) {
		setParameterInt(GLTextureParameter.TEXTURE_MAG_FILTER, magFilter.getConstant());
	}

	@Override
	public void setMinLOD(float minLOD) {
		setParameterFloat(GLTextureParameter.TEXTURE_MIN_LOD, minLOD);
	}

	@Override
	public void setMaxLOD(float maxLOD) {
		setParameterFloat(GLTextureParameter.TEXTURE_MAX_LOD, maxLOD);
	}

	@Override
	public void setLODBias(float LODBias) {
		setParameterFloat(GLTextureParameter.TEXTURE_LOD_BIAS, LODBias);
	}

	@Override
	public void setMaxAnisotropy(float maxAnisotropy) {
		setParameterFloat(GLTextureParameter.TEXTURE_MAX_ANISOTROPIC, maxAnisotropy);
	}

	@Override
	public ColorFloat getBorderColorFloat() {
		try (var view = getParameterFloats(GLTextureParameter.TEXTURE_BORDER_COLOR, StackDataView.ofFloats(4L))) {
			return new ColorFloat(
					view.getFloat(0L),
					view.getFloat(1L),
					view.getFloat(2L),
					view.getFloat(3L)
			);
		}
	}

	@Override
	public ColorInt getBorderColorInt() {
		try (var view = getParameterRawInts(GLTextureParameter.TEXTURE_BORDER_COLOR, StackDataView.ofInts(4L))) {
			return new ColorInt(
					view.getInt(0L),
					view.getInt(1L),
					view.getInt(2L),
					view.getInt(3L)
			);
		}
	}

	@Override
	public void setBorderColorFloat(ColorFloat borderColor) {
		try (var view = StackDataView.ofFloats(4L)) {
			setParameterFloats(GLTextureParameter.TEXTURE_BORDER_COLOR, view
					.putFloat(borderColor.getRed	())
					.putFloat(borderColor.getGreen	())
					.putFloat(borderColor.getBlue	())
					.putFloat(borderColor.getAlpha	())
			);
		}
	}

	@Override
	public void setBorderColorInt(ColorInt borderColor) {
		try (var view = StackDataView.ofInts(4L)) {
			setParameterRawInts(GLTextureParameter.TEXTURE_BORDER_COLOR, view
					.putInt(borderColor.getRed	())
					.putInt(borderColor.getGreen())
					.putInt(borderColor.getBlue	())
					.putInt(borderColor.getAlpha())
			);
		}
	}
}
