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

package com.github.argon4w.renderfox.opengl.texture.object.feature;

import com.github.argon4w.renderfox.data.coordinate.extent.Extent3D;
import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureLevelParameter;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;
import org.lwjgl.opengl.GL11;

public abstract class AbstractGLTextureStore implements IGLTextureStore, IGLTextureParameter {

	@Override
	public void setParameterInt(GLTextureParameter parameter, int value) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getParameterInt(GLTextureParameter parameter) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getLevelParameterInt(GLTextureLevelParameter parameter, int mipLevel) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getBaseLevel() {
		return getParameterInt(GLTextureParameter.TEXTURE_BASE_LEVEL);
	}

	@Override
	public int getMaxLevel() {
		return getParameterInt(GLTextureParameter.TEXTURE_MAX_LEVEL);
	}

	@Override
	public int getRealLevel(int mipLevel) {
		return mipLevel;
	}

	@Override
	public int getWidth(int mipLevel) {
		return getLevelParameterInt(GLTextureLevelParameter.TEXTURE_WIDTH, mipLevel);
	}

	@Override
	public int getHeight(int mipLevel) {
		return getLevelParameterInt(GLTextureLevelParameter.TEXTURE_HEIGHT, mipLevel);
	}

	@Override
	public int getDepth(int mipLevel) {
		return getLevelParameterInt(GLTextureLevelParameter.TEXTURE_DEPTH, mipLevel);
	}

	@Override
	public GLInternalFormat getInternalFormat(int mipLevel) {
		return GLInternalFormat.fromConstant(getLevelParameterInt(GLTextureLevelParameter.TEXTURE_INTERNAL_FORMAT, mipLevel));
	}

	@Override
	public IExtent3D getExtent(int mipLevel) {
		return new Extent3D(getWidth(mipLevel), getHeight(mipLevel), getDepth(mipLevel));
	}

	@Override
	public boolean isMultisampled() {
		return GLTextureType.fromConstant(getParameterInt(GLTextureParameter.TEXTURE_TARGET)).isMultisampled();
	}

	@Override
	public boolean isImmutable() {
		return getParameterInt(GLTextureParameter.TEXTURE_IMMUTABLE_FORMAT) == GL11.GL_TRUE;
	}

	@Override
	public int getImmutableMipLevels() {
		return getParameterInt(GLTextureParameter.TEXTURE_IMMUTABLE_LEVELS);
	}

	@Override
	public int getMaxDimension(int mipLevel) {
		return Math.max(getWidth(mipLevel), Math.max(getHeight(mipLevel), getDepth(mipLevel)));
	}

	@Override
	public int getPixels(int mipLevel) {
		return getWidth(mipLevel) * getHeight(mipLevel) * getDepth(mipLevel);
	}
}
