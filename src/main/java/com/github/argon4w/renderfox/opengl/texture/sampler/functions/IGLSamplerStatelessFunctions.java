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

public interface IGLSamplerStatelessFunctions extends IGLSamplerBindingFunctions {

	void samplerParameterf(
			int		samplerHandle,
			int		samplerParameter,
			float	value
	);

	void samplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	);

	void samplerParameteri(
			int		samplerHandle,
			int		samplerParameter,
			int		value
	);

	void samplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	);

	void samplerParameterIiv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	);

	void samplerParameterIuiv(
			int		samplerHandle,
			int		samplerParameter,
			long	samplerParameterDataAddress
	);

	float getSamplerParameterf(int samplerHandle, int samplerParameter);

	void getSamplerParameterfv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	);

	int getSamplerParameteri(int samplerHandle, int samplerParameter);

	void getSamplerParameteriv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	);

	void getSamplerParameterIiv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	);

	void getSamplerParameterIuiv(
			int		samplerHandle,
			int		samplerParameter,
			long	outDataAddress
	);

	boolean isSampler(int samplerHandle);

	void deleteSampler(int samplerHandle);
}
