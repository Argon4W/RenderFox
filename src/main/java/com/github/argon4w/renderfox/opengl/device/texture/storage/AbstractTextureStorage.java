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

package com.github.argon4w.renderfox.opengl.device.texture.storage;

import com.github.argon4w.renderfox.opengl.device.texture.GLTextureContext;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.object.GLTexture;
import com.github.argon4w.renderfox.opengl.texture.object.GLTextureCreateInfo;
import com.github.argon4w.renderfox.opengl.texture.object.IGLTexture;
import com.github.argon4w.renderfox.opengl.texture.object.raw.GLRawTexture;

public abstract class AbstractTextureStorage implements ITextureStorage {

	private final GLTextureContext textureContext;

	public AbstractTextureStorage(GLTextureContext textureContext) {
		this.textureContext = textureContext;
	}

	public abstract void setupStorage(
			GLRawTexture		texture,
			int					textureMipLevels,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			GLInternalFormat	textureInternalFormat
	);

	public abstract void setupStorageMultisampled(
			GLRawTexture		texture,
			boolean				textureFixedSamples,
			int					textureSamples,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			GLInternalFormat	textureInternalFormat
	);

	@Override
	public IGLTexture createTexture(GLTextureCreateInfo info) {
		return createTexture(
				info.getTextureMipLevels		(),
				info.getTextureSamples			(),
				info.getTextureWidth			(),
				info.getTextureHeight			(),
				info.getTextureDepth			(),
				info.isTextureFixedSamples		(),
				info.getTextureFormat			(),
				info.getTextureType				()
		);
	}

	@Override
	public IGLTexture createTexture(
			int					textureMipLevels,
			int					textureWidth,
			int					textureHeight,
			GLInternalFormat	textureInternalFormat,
			GLTextureType textureType
	) {
		return createTexture(
				textureMipLevels,
				textureWidth,
				textureHeight,
				1,
				textureInternalFormat,
				textureType
		);
	}

	@Override
	public IGLTexture createTexture(
			int					textureMipLevels,
			int					textureSamples,
			int					textureWidth,
			int					textureHeight,
			boolean				textureFixedSamples,
			GLInternalFormat	textureInternalFormat,
			GLTextureType		textureType
	) {
		return createTexture(
				textureMipLevels,
				textureSamples,
				textureWidth,
				textureHeight,
				1,
				textureFixedSamples,
				textureInternalFormat,
				textureType
		);
	}

	@Override
	public IGLTexture createTexture(
			int					textureMipLevels,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			GLInternalFormat	textureInternalFormat,
			GLTextureType		textureType
	) {
		return createTexture(
				textureMipLevels,
				0,
				textureWidth,
				textureHeight,
				textureDepth,
				true,
				textureInternalFormat,
				textureType
		);
	}

	@Override
	public IGLTexture createTexture(
			int					textureMipLevels,
			int					textureSamples,
			int					textureWidth,
			int					textureHeight,
			int					textureDepth,
			boolean				textureFixedSamples,
			GLInternalFormat	textureInternalFormat,
			GLTextureType		textureType
	) {
		if (textureInternalFormat == null) {
			throw new IllegalArgumentException("TextureInternalFormat cannot be null.");
		}

		if (textureInternalFormat == GLInternalFormat.INVALID) {
			throw new IllegalArgumentException("Invalid textureInternalFormat.");
		}

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		var texture = textureContext.createRawTexture(textureType);

		if (textureSamples > 1) {
			setupStorageMultisampled(
					texture,
					textureFixedSamples,
					textureSamples,
					textureWidth,
					textureHeight,
					textureDepth,
					textureInternalFormat
			);
		} else {
			setupStorage(
					texture,
					textureMipLevels,
					textureWidth,
					textureHeight,
					textureDepth,
					textureInternalFormat
			);
		}

		return new GLTexture(texture);
	}
}
