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

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.error.GLErrorChecker;

public class GLSamplerFunctionsErrorCheck implements IGLSamplerFunctions {

	private final	IGLSamplerFunctions	samplerFunctions;
	private			GLErrorChecker		errorChecker;

	public GLSamplerFunctionsErrorCheck(IGLSamplerFunctions samplerFunctions) {
		this.samplerFunctions = samplerFunctions;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		samplerFunctions.initialize(device);

		errorChecker = device.getErrorChecker();
	}

	@Override
	public int createSampler() {
		return errorChecker.runChecked("createSampler", samplerFunctions::createSampler);
	}

	@Override
	public void samplerParameterf(
			int		samplerHandle,
			int		samplerParameter,
			float	value
	) {
		errorChecker.runChecked("samplerParameterf", () -> samplerFunctions.samplerParameterf(
				samplerHandle,
				samplerParameter,
				value
		));
	}

	@Override
	public void samplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		errorChecker.runChecked("samplerParameterfv", () -> samplerFunctions.samplerParameterfv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		));
	}

	@Override
	public void samplerParameteri(
			int		samplerHandle,
			int		samplerParameter,
			int		value
	) {
		errorChecker.runChecked("samplerParameteri", () -> samplerFunctions.samplerParameteri(
				samplerHandle,
				samplerParameter,
				value
		));
	}

	@Override
	public void samplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		errorChecker.runChecked("samplerParameteriv", () -> samplerFunctions.samplerParameteriv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		));
	}

	@Override
	public void samplerParameterIiv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		errorChecker.runChecked("samplerParameterIiv", () -> samplerFunctions.samplerParameterIiv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		));
	}

	@Override
	public void samplerParameterIuiv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	) {
		errorChecker.runChecked("samplerParameterIuiv", () -> samplerFunctions.samplerParameterIuiv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		));
	}

	@Override
	public float getSamplerParameterf(int samplerHandle, int samplerParameter) {
		return errorChecker.runChecked("getSamplerParameterf", () -> samplerFunctions.getSamplerParameterf(samplerHandle, samplerParameter));
	}

	@Override
	public void getSamplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		errorChecker.runChecked("getSamplerParameterfv", () -> samplerFunctions.getSamplerParameterfv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		));
	}

	@Override
	public int getSamplerParameteri(int samplerHandle, int samplerParameter) {
		return errorChecker.runChecked("getSamplerParameteri", () -> samplerFunctions.getSamplerParameteri(samplerHandle, samplerParameter));
	}

	@Override
	public void getSamplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		errorChecker.runChecked("getSamplerParameteriv", () -> samplerFunctions.getSamplerParameteriv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		));
	}

	@Override
	public void getSamplerParameterIiv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		errorChecker.runChecked("getSamplerParameterIiv", () -> samplerFunctions.getSamplerParameterIiv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		));
	}

	@Override
	public void getSamplerParameterIuiv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		errorChecker.runChecked("getSamplerParameterIuiv", () -> samplerFunctions.getSamplerParameterIuiv(
				samplerHandle,
				samplerParameter,
				outDataAddress
		));
	}

	@Override
	public void bindSampler(int textureUnit, int samplerHandle) {
		errorChecker.runChecked("bindSampler", () -> samplerFunctions.bindSampler(textureUnit, samplerHandle));
	}

	@Override
	public boolean isSampler(int samplerHandle) {
		return errorChecker.runChecked("isSampler", () -> samplerFunctions.isSampler(samplerHandle));
	}

	@Override
	public void deleteSampler(int samplerHandle) {
		errorChecker.runChecked("deleteSampler", () -> samplerFunctions.deleteSampler(samplerHandle));
	}
}
