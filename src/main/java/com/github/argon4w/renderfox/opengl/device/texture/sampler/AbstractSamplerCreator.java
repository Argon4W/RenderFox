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

import com.github.argon4w.renderfox.format.ColorFloat;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareMode;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureWrapFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLMipmapMode;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.GLRawSampler;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.GLSampler;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.GLSamplerCreateInfo;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.IGLSampler;

public abstract class AbstractSamplerCreator implements ISamplerCreator {

	protected final GLTextureContext textureContext;

	public AbstractSamplerCreator(GLTextureContext textureContext) {
		this.textureContext = textureContext;
	}

	protected abstract void setupSampler(
			GLRawSampler				sampler,
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
	);

	@Override
	public IGLSampler createSampler(GLSamplerCreateInfo info, ColorFloat borderColor) {
		return createSampler(
				info.getCompareFunction	(),
				info.getCompareMode		(),
				info.getWrapS			(),
				info.getWrapT			(),
				info.getWrapR			(),
				info.getMinFilter		(),
				info.getMagFilter		(),
				info.getMipmapMode		(),
				borderColor,
				info.getMinLOD			(),
				info.getMaxLOD			(),
				info.getLODBias			(),
				info.getMaxAnisotropy	()
		);
	}

	@Override
	public IGLSampler createSampler(GLSamplerCreateInfo info, ColorInt borderColor) {
		return createSampler(
				info.getCompareFunction	(),
				info.getCompareMode		(),
				info.getWrapS			(),
				info.getWrapT			(),
				info.getWrapR			(),
				info.getMinFilter		(),
				info.getMagFilter		(),
				info.getMipmapMode		(),
				borderColor,
				info.getMinLOD			(),
				info.getMaxLOD			(),
				info.getLODBias			(),
				info.getMaxAnisotropy	()
		);
	}

	@Override
	public IGLSampler createSampler(
			GLTextureCompareFunction	compareFunction,
			GLTextureCompareMode		compareMode,
			GLTextureWrapFunction		wrapS,
			GLTextureWrapFunction		wrapT,
			GLTextureWrapFunction		wrapR,
			GLFilterMode				minFilter,
			GLFilterMode				magFilter,
			GLMipmapMode				mipmapMode,
			ColorFloat					borderColor,
			float						minLOD,
			float						maxLOD,
			float						LODBias,
			float						maxAnisotropy
	) {
		var sampler = textureContext.createRawSampler();

		setupSampler(
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

		sampler.setBorderColorFloat(borderColor);

		return new GLSampler(sampler);
	}

	@Override
	public IGLSampler createSampler(
			GLTextureCompareFunction	compareFunction,
			GLTextureCompareMode		compareMode,
			GLTextureWrapFunction		wrapS,
			GLTextureWrapFunction		wrapT,
			GLTextureWrapFunction		wrapR,
			GLFilterMode				minFilter,
			GLFilterMode				magFilter,
			GLMipmapMode				mipmapMode,
			ColorInt					borderColor,
			float						minLOD,
			float						maxLOD,
			float						LODBias,
			float						maxAnisotropy
	) {
		var sampler = textureContext.createRawSampler();

		setupSampler(
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

		sampler.setBorderColorInt(borderColor);

		return new GLSampler(sampler);
	}
}
