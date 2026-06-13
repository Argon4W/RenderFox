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

package com.github.argon4w.renderfox.opengl.texture.object.raw;

import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponent;
import com.github.argon4w.renderfox.opengl.texture.object.feature.AbstractGLTextureStore;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLTextureBinding;

public abstract class AbstractGLRawTextureView extends AbstractGLTextureStore implements IGLRawTextureView, IGLTextureBinding {

	@Override
	public IGLRawTextureView view(
			GLTextureComponent	viewSwizzleRed,
			GLTextureComponent	viewSwizzleGreen,
			GLTextureComponent	viewSwizzleBlue,
			GLTextureComponent	viewSwizzleAlpha,
			int					viewMinMipLevel,
			int					viewMipLevels
	) {
		return getTextureContext().getTextureViewCreator().createRawView(
				this,
				viewSwizzleRed,
				viewSwizzleGreen,
				viewSwizzleBlue,
				viewSwizzleAlpha,
				viewMinMipLevel,
				viewMipLevels
		);
	}

	@Override
	public IGLRawTextureView view(int viewMinMipLevel, int viewMipLevels) {
		return view(
				GLTextureComponent.RED,
				GLTextureComponent.GREEN,
				GLTextureComponent.BLUE,
				GLTextureComponent.ALPHA,
				viewMinMipLevel,
				viewMipLevels
		);
	}

	@Override
	public IGLRawTextureView view(int viewMaxMipLevel) {
		return view(0, viewMaxMipLevel + 1);
	}

	@Override
	public IGLRawTextureView view() {
		return view(getMaxLevel());
	}

	@Override
	public boolean isSamplable() {
		return true;
	}
}
