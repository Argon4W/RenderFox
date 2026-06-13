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

import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;

public class GLSamplerFunctionsCached implements IGLSamplerFunctions {

	private final	IGLSamplerFunctions			samplerFunctions;
	private			IGLGlobalFunctionsHelper	globalHelper;
	private			IGLBindingSource			bindingSource;

	public GLSamplerFunctionsCached(IGLSamplerFunctions samplerFunctions) {
		this.samplerFunctions	= samplerFunctions;
		this.globalHelper		= null;
		this.bindingSource		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		samplerFunctions.initialize(device);

		globalHelper	= device.getGlobalContext().getGlobalHelper();
		bindingSource	= device.getBindingSource();
	}

	@Override
	public int createSampler() {
		return samplerFunctions.createSampler();
	}

	@Override
	public void samplerParameterf(
			int		samplerHandle,
			int		samplerParameter,
			float	value
	) {
		samplerFunctions.samplerParameterf(
				samplerHandle,
				samplerParameter,
				value
		);
	}

	@Override
	public void samplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		samplerFunctions.samplerParameterfv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		);
	}

	@Override
	public void samplerParameteri(
			int samplerHandle,
			int samplerParameter,
			int value
	) {
		samplerFunctions.samplerParameteri(
				samplerHandle,
				samplerParameter,
				value
		);
	}

	@Override
	public void samplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		samplerFunctions.samplerParameteriv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		);
	}

	@Override
	public void samplerParameterIiv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		samplerFunctions.samplerParameterIiv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		);
	}

	@Override
	public void samplerParameterIuiv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		samplerFunctions.samplerParameterIuiv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		);
	}

	@Override
	public float getSamplerParameterf(int samplerHandle, int samplerParameter) {
		return samplerFunctions.getSamplerParameterf(samplerHandle, samplerParameter);
	}

	@Override
	public void getSamplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		samplerFunctions.getSamplerParameterfv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public int getSamplerParameteri(int samplerHandle, int samplerParameter) {
		return samplerFunctions.getSamplerParameteri(samplerHandle, samplerParameter);
	}

	@Override
	public void getSamplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		samplerFunctions.getSamplerParameteriv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public void getSamplerParameterIiv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		samplerFunctions.getSamplerParameterIiv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public void getSamplerParameterIuiv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		samplerFunctions.getSamplerParameterIuiv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public boolean isSampler(int samplerHandle) {
		return samplerFunctions.isSampler(samplerHandle);
	}

	@Override
	public void deleteSampler(int samplerHandle) {
		samplerFunctions.deleteSampler(samplerHandle);
	}

	@Override
	public void bindSampler(int textureUnit, int samplerHandle) {
		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit cannot be negative.");
		}

		if (textureUnit >= globalHelper.getMaxTextureUnits()) {
			throw new IllegalArgumentException("TextureUnit cannot be greater than the value of GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS minus one.");
		}

		if (bindingSource.getBoundSampler(textureUnit) == samplerHandle) {
			return;
		}

		samplerFunctions.bindSampler(textureUnit, samplerHandle);
	}
}
