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

package com.github.argon4w.renderfox.opengl.texture.constant.filter;

import com.github.argon4w.renderfox.data.UnionValue;
import com.github.argon4w.renderfox.opengl.constant.AbstractGLDefinedConstants;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;

public enum GLTextureFilter {

	INVALID					(-1,								false,	GLMipmapMode.INVALID,	GLFilterMode.INVALID),
	NEAREST					(GL11.GL_NEAREST,					true,	GLMipmapMode.INVALID,	GLFilterMode.NEAREST),
	LINEAR					(GL11.GL_LINEAR,					true,	GLMipmapMode.INVALID,	GLFilterMode.LINEAR),
	NEAREST_MIPMAP_NEAREST	(GL11.GL_NEAREST_MIPMAP_NEAREST,	false,	GLMipmapMode.NEAREST,	GLFilterMode.NEAREST),
	LINEAR_MIPMAP_NEAREST	(GL11.GL_LINEAR_MIPMAP_NEAREST,		false,	GLMipmapMode.LINEAR,	GLFilterMode.NEAREST),
	NEAREST_MIPMAP_LINEAR	(GL11.GL_NEAREST_MIPMAP_LINEAR,		false,	GLMipmapMode.NEAREST,	GLFilterMode.LINEAR),
	LINEAR_MIPMAP_LINEAR	(GL11.GL_LINEAR_MIPMAP_LINEAR,		false,	GLMipmapMode.LINEAR,	GLFilterMode.LINEAR),;

	private static final Int2ReferenceMap		<GLTextureFilter>														TABLE;
	private static final Reference2ReferenceMap	<GLFilterMode, GLTextureFilter>											MIPMAP_LINEAR_TABLE;
	private static final Reference2ReferenceMap	<GLFilterMode, GLTextureFilter>											MIPMAP_NEAREST_TABLE;
	private static final Reference2ReferenceMap	<GLMipmapMode, Reference2ReferenceMap<GLFilterMode, GLTextureFilter>>	MIPMAP_LOOKUP_TABLE;

	static {
		TABLE					= new Int2ReferenceOpenHashMap		<>();
		MIPMAP_LINEAR_TABLE		= new Reference2ReferenceOpenHashMap<>();
		MIPMAP_NEAREST_TABLE	= new Reference2ReferenceOpenHashMap<>();
		MIPMAP_LOOKUP_TABLE		= new Reference2ReferenceOpenHashMap<>();

		MIPMAP_LOOKUP_TABLE.put(GLMipmapMode.NEAREST,	MIPMAP_NEAREST_TABLE);
		MIPMAP_LOOKUP_TABLE.put(GLMipmapMode.LINEAR,	MIPMAP_LINEAR_TABLE);

		for (var mode : GLTextureFilter.values()) {
			TABLE											.put(mode.getConstant	(), mode);
			MIPMAP_LOOKUP_TABLE.get(mode.getMipmapMode())	.put(mode.getFilterMode	(), mode);
		}
	}

	private final int			constant;
	private final boolean		canMagnify;
	private final GLMipmapMode	mipmapMode;
	private final GLFilterMode	filterMode;

	GLTextureFilter(
			int				constant,
			boolean			canMagnify,
			GLMipmapMode	mipmapMode,
			GLFilterMode	filterMode
	) {
		this.constant	= constant;
		this.canMagnify	= canMagnify;
		this.mipmapMode	= mipmapMode;
		this.filterMode	= filterMode;
	}

	public int getConstant() {
		return constant;
	}

	public boolean canMagnify() {
		return canMagnify;
	}

	public GLMipmapMode getMipmapMode() {
		return mipmapMode;
	}

	public GLFilterMode getFilterMode() {
		return filterMode;
	}

	public static GLTextureFilter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}

	public static GLTextureFilter from(GLFilterMode filterMode) {
		return from(filterMode, GLMipmapMode.INVALID);
	}

	public static GLTextureFilter from(GLFilterMode filterMode, GLMipmapMode mipmapMode) {
		return MIPMAP_LOOKUP_TABLE.getOrDefault(mipmapMode, Reference2ReferenceMaps.emptyMap()).getOrDefault(filterMode, INVALID);
	}

	public static class TextureDefinedConstants extends AbstractGLDefinedConstants<GLTextureType> {

		public static final TextureDefinedConstants MAGNIFY	= new TextureDefinedConstants(true);
		public static final TextureDefinedConstants MINIFY	= new TextureDefinedConstants(false);

		private final boolean magnify;

		private TextureDefinedConstants(boolean magnify) {
			this.magnify = magnify;
		}

		@Override
		public boolean isValidValue(GLTextureType textureType, UnionValue value) {
			var filter = fromConstant(value.asInt());

			if (filter == INVALID) {
				return false;
			}

			if (!textureType.hasMipmap() || magnify) {
				return filter.canMagnify;
			}

			return true;
		}
	}

	public static class SamplerDefinedConstants extends AbstractGLDefinedConstants<IGLGlobalFunctionsHelper> {

		public static final SamplerDefinedConstants MAGNIFY	= new SamplerDefinedConstants(true);
		public static final SamplerDefinedConstants MINIFY	= new SamplerDefinedConstants(false);

		private final boolean magnify;

		private SamplerDefinedConstants(boolean magnify) {
			this.magnify = magnify;
		}

		@Override
		public boolean isValidValue(IGLGlobalFunctionsHelper context, UnionValue value) {
			var filter = fromConstant(value.asInt());

			if (filter == INVALID) {
				return false;
			}

			return filter.canMagnify == magnify;
		}
	}
}
