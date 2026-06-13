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
import com.github.argon4w.renderfox.opengl.dsa.IDirectStateAccess;
import org.lwjgl.opengl.GL33;

public class GLSamplerFunctionsDirect implements IGLSamplerFunctions {

	private IDirectStateAccess	directStateAccess;
	private IGLBindingSource	bindingSource;

	public GLSamplerFunctionsDirect() {
		this.directStateAccess	= null;
		this.bindingSource		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		directStateAccess	= device.getDirectStateAccess	();
		bindingSource		= device.getBindingSource		();
	}

	@Override
	public int createSampler() {
		return directStateAccess.createSampler();
	}

	@Override
	public void samplerParameterf(
			int		samplerHandle,
			int		samplerParameter,
			float	value
	) {
		GL33.glSamplerParameterf(
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
		GL33.nglSamplerParameterfv(
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
		GL33.glSamplerParameteri(
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
		GL33.nglSamplerParameteriv(
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
		GL33.nglSamplerParameterIiv(
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
		GL33.nglSamplerParameterIuiv(
				samplerHandle,
				samplerParameter,
				samplerParameterDataAddress
		);
	}

	@Override
	public float getSamplerParameterf(int samplerHandle, int samplerParameter) {
		return GL33.glGetSamplerParameteri(samplerHandle, samplerParameter);
	}

	@Override
	public void getSamplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		GL33.nglGetSamplerParameterfv(
				samplerParameter,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public int getSamplerParameteri(int samplerHandle, int samplerParameter) {
		return GL33.glGetSamplerParameteri(samplerHandle, samplerParameter);
	}

	@Override
	public void getSamplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	) {
		GL33.nglGetSamplerParameteriv(
				samplerParameter,
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
		GL33.nglGetSamplerParameterIiv(
				samplerParameter,
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
		GL33.nglGetSamplerParameterIuiv(
				samplerParameter,
				samplerParameter,
				outDataAddress
		);
	}

	@Override
	public void bindSampler(int textureUnit, int samplerHandle) {
		GL33.glBindSampler(textureUnit, samplerHandle);

		bindingSource.bindSampler(textureUnit, samplerHandle);
	}

	@Override
	public boolean isSampler(int samplerHandle) {
		return GL33.glIsSampler(samplerHandle);
	}

	@Override
	public void deleteSampler(int samplerHandle) {
		GL33.glDeleteSamplers(samplerHandle);
	}

	public static IGLSamplerFunctions of() {
		return new GLSamplerFunctionsDirect();
	}
}
