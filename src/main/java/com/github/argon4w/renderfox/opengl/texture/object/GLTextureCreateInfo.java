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

package com.github.argon4w.renderfox.opengl.texture.object;

import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;

public class GLTextureCreateInfo {

	private final int				textureMipLevels;
	private final int				textureSamples;
	private final int				textureWidth;
	private final int				textureHeight;
	private final int				textureDepth;
	private final boolean			textureFixedSamples;
	private final GLInternalFormat	textureFormat;
	private final GLTextureType		textureType;

	public GLTextureCreateInfo(
			int					textureMipLevels,
			int					textureSample,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			boolean				textureFixedSamples,
			GLInternalFormat textureFormat,
			GLTextureType		textureType
	) {
		this.textureMipLevels		= textureMipLevels;
		this.textureSamples			= textureSample;
		this.textureWidth			= textureWidth;
		this.textureHeight			= textureHeight;
		this.textureDepth			= textureDepth;
		this.textureFixedSamples	= textureFixedSamples;
		this.textureFormat			= textureFormat;
		this.textureType			= textureType;
	}

	public int getTextureMipLevels() {
		return textureMipLevels;
	}

	public int getTextureSamples() {
		return textureSamples;
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public int getTextureDepth() {
		return textureDepth;
	}

	public boolean isTextureFixedSamples() {
		return textureFixedSamples;
	}

	public GLInternalFormat getTextureFormat() {
		return textureFormat;
	}

	public GLTextureType getTextureType() {
		return textureType;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private int					textureMipLevels;
		private int					textureSamples;
		private int					textureWidth;
		private int					textureHeight;
		private int					textureDepth;
		private boolean				textureFixedSamples;
		private GLInternalFormat	textureFormat;
		private GLTextureType		textureType;

		private Builder() {
			this.textureSamples			= 1;
			this.textureMipLevels		= -1;
			this.textureWidth			= -1;
			this.textureHeight			= 1;
			this.textureDepth			= 1;
			this.textureFixedSamples	= false;
			this.textureFormat			= GLInternalFormat	.RGBA8;
			this.textureType			= GLTextureType		.TEXTURE_2D;
		}

		public Builder withTextureMipLevels(int textureMipLevels) {
			this.textureMipLevels = textureMipLevels;
			return this;
		}

		public Builder withTextureSamples(int textureSamples) {
			this.textureSamples = textureSamples;
			return this;
		}

		public Builder withTextureWidth(int textureWidth) {
			this.textureWidth = textureWidth;
			return this;
		}

		public Builder withTextureHeight(int textureHeight) {
			this.textureHeight = textureHeight;
			return this;
		}

		public Builder withTextureDepth(int textureDepth) {
			this.textureDepth = textureDepth;
			return this;
		}

		public Builder withTextureFixedSamples(boolean textureFixedSamples) {
			this.textureFixedSamples = textureFixedSamples;
			return this;
		}

		public Builder withTextureFormat(GLInternalFormat textureFormat) {
			this.textureFormat = textureFormat;
			return this;
		}

		public Builder withTextureType(GLTextureType textureType) {
			this.textureType = textureType;
			return this;
		}

		public GLTextureCreateInfo build() {
			return new GLTextureCreateInfo(
					textureMipLevels,
					textureSamples,
					textureWidth,
					textureHeight,
					textureDepth,
					textureFixedSamples,
					textureFormat,
					textureType
			);
		}
	}
}
