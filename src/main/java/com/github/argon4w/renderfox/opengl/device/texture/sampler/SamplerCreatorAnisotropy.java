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

package com.github.argon4w.renderfox.opengl.device.texture.sampler;

import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareMode;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureWrapFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLMipmapMode;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;
import com.github.argon4w.renderfox.opengl.texture.sampler.functions.GLSamplerFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.GLRawSampler;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.IGLSampler;

public class SamplerCreatorAnisotropy extends SamplerCreatorLegacy {

	public SamplerCreatorAnisotropy(GLTextureContext textureContext) {
		super(textureContext);
	}

	@Override
	protected void setupSampler(
			IGLSampler 					sampler,
			GLTextureCompareFunction	compareFunction,
			GLTextureCompareMode		compareMode,
			GLTextureWrapFunction		wrapS,
			GLTextureWrapFunction		wrapT,
			GLTextureWrapFunction		wrapR,
			GLFilterMode				minFilter,
			GLFilterMode				magFilter,
			GLMipmapMode				mipmapMode,
			float						minLOD,
			float						maxLOD,
			float						LODBias,
			float						maxAnisotropy
	) {
		super.setupSampler(
				sampler,
				compareFunction,
				compareMode,
				wrapS,
				wrapT,
				wrapR,
				minFilter,
				magFilter,
				mipmapMode,
				minLOD,
				maxLOD,
				LODBias,
				maxAnisotropy
		);

		sampler.setMaxAnisotropy(maxAnisotropy);
	}
}
