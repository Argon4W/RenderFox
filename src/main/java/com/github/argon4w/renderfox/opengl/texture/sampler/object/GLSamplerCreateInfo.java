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

import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureCompareMode;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureWrapFunction;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLMipmapMode;

public class GLSamplerCreateInfo {

	private final GLTextureCompareFunction	compareFunction;
	private final GLTextureCompareMode		compareMode;
	private final GLTextureWrapFunction		wrapS;
	private final GLTextureWrapFunction		wrapT;
	private final GLTextureWrapFunction		wrapR;
	private final GLFilterMode				minFilter;
	private final GLFilterMode				magFilter;
	private final GLMipmapMode				mipmapMode;
	private final float						minLOD;
	private final float						maxLOD;
	private final float						LODBias;
	private final float						maxAnisotropy;

	public GLSamplerCreateInfo(
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
		this.compareFunction	= compareFunction;
		this.compareMode		= compareMode;
		this.wrapS				= wrapS;
		this.wrapT				= wrapT;
		this.wrapR				= wrapR;
		this.minFilter			= minFilter;
		this.magFilter			= magFilter;
		this.mipmapMode			= mipmapMode;
		this.minLOD				= minLOD;
		this.maxLOD				= maxLOD;
		this.LODBias			= LODBias;
		this.maxAnisotropy		= maxAnisotropy;
	}

	public GLTextureCompareFunction getCompareFunction() {
		return compareFunction;
	}

	public GLTextureCompareMode getCompareMode() {
		return compareMode;
	}

	public GLTextureWrapFunction getWrapS() {
		return wrapS;
	}

	public GLTextureWrapFunction getWrapT() {
		return wrapT;
	}

	public GLTextureWrapFunction getWrapR() {
		return wrapR;
	}

	public GLFilterMode getMinFilter() {
		return minFilter;
	}

	public GLFilterMode getMagFilter() {
		return magFilter;
	}

	public GLMipmapMode getMipmapMode() {
		return mipmapMode;
	}

	public float getMinLOD() {
		return minLOD;
	}

	public float getMaxLOD() {
		return maxLOD;
	}

	public float getLODBias() {
		return LODBias;
	}

	public float getMaxAnisotropy() {
		return maxAnisotropy;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private GLTextureCompareFunction	compareFunction;
		private GLTextureCompareMode		compareMode;
		private GLTextureWrapFunction		wrapS;
		private GLTextureWrapFunction		wrapT;
		private GLTextureWrapFunction		wrapR;
		private GLFilterMode				minFilter;
		private GLFilterMode				magFilter;
		private GLMipmapMode				mipmapMode;
		private float						minLOD;
		private float						maxLOD;
		private float						LODBias;
		private float						maxAnisotropy;

		private Builder() {
			this.compareFunction	= GLTextureCompareFunction	.LEQUAL;
			this.compareMode		= GLTextureCompareMode		.NONE;
			this.wrapS				= GLTextureWrapFunction		.REPEAT;
			this.wrapT				= GLTextureWrapFunction		.REPEAT;
			this.wrapR				= GLTextureWrapFunction		.REPEAT;
			this.minFilter			= GLFilterMode				.LINEAR;
			this.magFilter			= GLFilterMode				.LINEAR;
			this.mipmapMode			= GLMipmapMode				.LINEAR;
			this.minLOD				= -1000.0f;
			this.maxLOD				= +1000.0f;
			this.LODBias			= 0.0f;
			this.maxAnisotropy		= 1.0f;
		}

		public Builder(
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
			this.compareFunction	= compareFunction;
			this.compareMode		= compareMode;
			this.wrapS				= wrapS;
			this.wrapT				= wrapT;
			this.wrapR				= wrapR;
			this.minFilter			= minFilter;
			this.magFilter			= magFilter;
			this.mipmapMode			= mipmapMode;
			this.minLOD				= minLOD;
			this.maxLOD				= maxLOD;
			this.LODBias			= LODBias;
			this.maxAnisotropy		= maxAnisotropy;
		}

		public Builder withCompareFunction(GLTextureCompareFunction compareFunction) {
			this.compareFunction = compareFunction;
			return this;
		}

		public Builder withCompareMode(GLTextureCompareMode compareMode) {
			this.compareMode = compareMode;
			return this;
		}

		public Builder withWrapS(GLTextureWrapFunction wrapS) {
			this.wrapS = wrapS;
			return this;
		}

		public Builder withWrapT(GLTextureWrapFunction wrapT) {
			this.wrapT = wrapT;
			return this;
		}

		public Builder withWrapR(GLTextureWrapFunction wrapR) {
			this.wrapR = wrapR;
			return this;
		}

		public Builder withMinFilter(GLFilterMode minFilter) {
			this.minFilter = minFilter;
			return this;
		}

		public Builder withMagFilter(GLFilterMode magFilter) {
			this.magFilter = magFilter;
			return this;
		}

		public Builder withMipmapMode(GLMipmapMode mipmapMode) {
			this.mipmapMode = mipmapMode;
			return this;
		}

		public Builder withMinLOD(float minLOD) {
			this.minLOD = minLOD;
			return this;
		}

		public Builder withMaxLOD(float maxLOD) {
			this.maxLOD = maxLOD;
			return this;
		}

		public Builder withLODBias(float maxAnisotropy) {
			this.LODBias = maxAnisotropy;
			return this;
		}

		public Builder withMaxAnisotropy(float maxAnisotropy) {
			this.maxAnisotropy = maxAnisotropy;
			return this;
		}

		public Builder copy() {
			return new Builder(
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
		}

		public GLSamplerCreateInfo build() {
			return new GLSamplerCreateInfo(
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
		}
	}
}
