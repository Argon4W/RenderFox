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

import com.github.argon4w.renderfox.data.UnionValue;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;

public class GLSamplerFunctionsValidation implements IGLSamplerFunctions {

	private final	IGLSamplerFunctions			samplerFunctions;
	private			IGLGlobalFunctionsHelper	helperFunctions;

	public GLSamplerFunctionsValidation(IGLSamplerFunctions samplerFunctions) {
		this.samplerFunctions	= samplerFunctions;
		this.helperFunctions	= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		samplerFunctions.initialize(device);

		helperFunctions = device.getGlobalContext().getGlobalHelper();
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
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

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
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		samplerFunctions.samplerParameterfv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		);
	}

	@Override
	public void samplerParameteri(
			int		samplerHandle,
			int		samplerParameter,
			int		value
	) {
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.getSamplerValues().isValidValue(helperFunctions, new UnionValue(value))) {
			throw new IllegalArgumentException("Value should have a defined constant value (base on the value of samplerParameter) and does not.");
		}

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
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.getSamplerValues().isValidAddress(helperFunctions, samplerParameter)) {
			throw new IllegalArgumentException("Value should have a defined constant value (base on the value of samplerParameter) and does not.");
		}

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
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.getSamplerValues().isValidAddress(helperFunctions, samplerParameterDataAddress)) {
			throw new IllegalArgumentException("Value should have a defined constant value (base on the value of samplerParameter) and does not.");
		}

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
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.getSamplerValues().isValidAddress(helperFunctions, samplerParameterDataAddress)) {
			throw new IllegalArgumentException("Value should have a defined constant value (base on the value of samplerParameter) and does not.");
		}

		samplerFunctions.samplerParameterIuiv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		);
	}

	@Override
	public float getSamplerParameterf(int samplerHandle, int samplerParameter) {
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return samplerFunctions.getSamplerParameterf(samplerHandle, samplerParameter);
	}

	@Override
	public void getSamplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		samplerFunctions.getSamplerParameterfv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public int getSamplerParameteri(int samplerHandle, int samplerParameter) {
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return samplerFunctions.getSamplerParameteri(samplerHandle, samplerParameter);
	}

	@Override
	public void getSamplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

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
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

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
		var parameter = GLTextureParameter.fromConstant(samplerParameter);

		if (!samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not the name of a sampler object previously returned from a call to glGenSamplers.");
		}

		if (parameter == GLTextureParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		if (!parameter.isSampler()) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		samplerFunctions.getSamplerParameterIuiv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public void bindSampler(int textureUnit, int samplerHandle) {
		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit cannot be negative.");
		}

		if (textureUnit >= helperFunctions.getMaxTextureUnits()) {
			throw new IllegalArgumentException("TextureUnit is greater than or equal to the value of GL_MAX_COMBINED_TEXTURE_UNITS.");
		}

		if (samplerHandle != 0 && !samplerFunctions.isSampler(samplerHandle)) {
			throw new IllegalArgumentException("SamplerHandle is not zero or a name previously returned from a call to glGenSamplers, or if such a name has been deleted by a call to glDeleteSamplers.");
		}

		samplerFunctions.bindSampler(textureUnit, samplerHandle);
	}

	@Override
	public boolean isSampler(int samplerHandle) {
		return samplerFunctions.isSampler(samplerHandle);
	}

	@Override
	public void deleteSampler(int samplerHandle) {
		samplerFunctions.deleteSampler(samplerHandle);
	}
}
