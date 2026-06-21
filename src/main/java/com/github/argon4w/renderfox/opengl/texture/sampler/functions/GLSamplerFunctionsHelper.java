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

package com.github.argon4w.renderfox.opengl.texture.sampler.functions;

import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.IGLSampler;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.feature.AbstractGLSamplerStore;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.feature.IGLSamplerOperation;

public class GLSamplerFunctionsHelper extends AbstractGLSamplerStore implements IGLSampler, IGLSamplerOperation {

	private final	IGLSamplerFunctions	samplerFunctions;
	private			int					samplerHandle;

	public GLSamplerFunctionsHelper(IGLSamplerFunctions samplerFunctions) {
		this.samplerFunctions	= samplerFunctions;
		this.samplerHandle		= -1;
	}

	public GLSamplerFunctionsHelper setSampler(int samplerHandle) {
		this.samplerHandle = samplerHandle;
		return this;
	}

	@Override
	public void setParameterInt(GLTextureParameter parameter, int value) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		samplerFunctions.samplerParameteri(
				samplerHandle,
				parameter.getConstant(),
				value
		);
	}

	@Override
	public void setParameterFloat(GLTextureParameter parameter, float value) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		samplerFunctions.samplerParameterf(
				samplerHandle,
				parameter.getConstant(),
				value
		);
	}

	@Override
	public void setParameterFloats(GLTextureParameter parameter, IDataView<?> dataView) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		if (dataView == null) {
			throw new IllegalArgumentException("DataView cannot be null.");
		}

		if (!dataView.isOffHeap()) {
			throw new IllegalArgumentException("DataView is not an off-heap data view.");
		}

		samplerFunctions.samplerParameterfv(
				samplerHandle,
				parameter	.getConstant(),
				dataView	.address	()
		);
	}

	@Override
	public void setParameterRawInts(GLTextureParameter parameter, IDataView<?> dataView) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		if (dataView == null) {
			throw new IllegalArgumentException("DataView cannot be null.");
		}

		if (!dataView.isOffHeap()) {
			throw new IllegalArgumentException("DataView is not an off-heap data view.");
		}

		samplerFunctions.samplerParameterIiv(
				samplerHandle,
				parameter	.getConstant(),
				dataView	.address	()
		);
	}

	@Override
	public int getParameterInt(GLTextureParameter parameter) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		return samplerFunctions.getSamplerParameteri(samplerHandle, parameter.getConstant());
	}

	@Override
	public float getParameterFloat(GLTextureParameter parameter) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		return samplerFunctions.getSamplerParameterf(samplerHandle, parameter.getConstant());
	}

	@Override
	public <T extends IDataView<?>> T getParameterFloats(GLTextureParameter parameter, T outDataView) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		if (outDataView == null) {
			throw new IllegalArgumentException("OutDataView cannot be null.");
		}

		if (!outDataView.isOffHeap()) {
			throw new IllegalArgumentException("OutDataView is not an off-heap data view.");
		}

		samplerFunctions.getSamplerParameterfv(
				samplerHandle,
				parameter	.getConstant(),
				outDataView	.address	()
		);

		return outDataView;
	}

	@Override
	public <T extends IDataView<?>> T getParameterRawInts(GLTextureParameter parameter, T outDataView) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Parameter is not a sampler parameter.");
		}

		if (outDataView == null) {
			throw new IllegalArgumentException("OutDataView cannot be null.");
		}

		if (!outDataView.isOffHeap()) {
			throw new IllegalArgumentException("OutDataView is not an off-heap data view.");
		}

		samplerFunctions.getSamplerParameterIiv(
				samplerHandle,
				parameter	.getConstant(),
				outDataView	.address	()
		);

		return outDataView;
	}

	@Override
	public void bind(int textureUnit) {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		samplerFunctions.bindSampler(samplerHandle, textureUnit);
	}

	@Override
	public boolean isSampler() {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		return samplerFunctions.isSampler(samplerHandle);
	}

	@Override
	public void delete() {
		if (samplerHandle == -1) {
			throw new IllegalStateException("SamplerHandle has not yet been set.");
		}

		samplerFunctions.deleteSampler(samplerHandle);
	}

	@Override
	public int getSamplerHandle() {
		return samplerHandle;
	}

	@Override
	public boolean isDeleted() {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}
}
