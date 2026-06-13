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

package com.github.argon4w.renderfox.opengl.texture.function.parameter;

import com.github.argon4w.renderfox.opengl.constant.*;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.constant.*;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLTextureFilter;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.*;

public enum GLTextureParameter {

	INVALID							(-1,													0, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	DEPTH_STENCIL_TEXTURE_MODE		(GL43.GL_DEPTH_STENCIL_TEXTURE_MODE,					1, 0,																	GLDepthStencilTextureMode.DefinedConstants		.of(),		GLInvalidConstants								.of	()),
	TEXTURE_BASE_LEVEL				(GL12.GL_TEXTURE_BASE_LEVEL,							1, 0,																	GLNonNegativeConstants							.of(),		GLInvalidConstants								.of	()),
	TEXTURE_BORDER_COLOR			(GL11.GL_TEXTURE_BORDER_COLOR,							4, GLTexParameterFeatures.SAMPLER	| GLTexParameterFeatures.FLOAT,		GLNoneConstants									.of(),		GLNoneConstants									.of	()),
	TEXTURE_COMPARE_FUNC			(GL14.GL_TEXTURE_COMPARE_FUNC,							1, GLTexParameterFeatures.SAMPLER,										GLTextureCompareFunction.DefinedConstants		.of(),		GLTextureCompareFunction.DefinedConstants		.of	()),
	TEXTURE_COMPARE_MODE			(GL14.GL_TEXTURE_COMPARE_MODE,							1, GLTexParameterFeatures.SAMPLER,										GLTextureCompareMode	.DefinedConstants		.of(),		GLTextureCompareMode	.DefinedConstants		.of	()),
	TEXTURE_LOD_BIAS				(GL14.GL_TEXTURE_LOD_BIAS,								1, GLTexParameterFeatures.SAMPLER	| GLTexParameterFeatures.FLOAT,		GLNoneConstants									.of(),		GLNoneConstants									.of	()),
	TEXTURE_MIN_FILTER				(GL11.GL_TEXTURE_MIN_FILTER,							1, GLTexParameterFeatures.SAMPLER,										GLTextureFilter.TextureDefinedConstants			.MINIFY,	GLTextureFilter.SamplerDefinedConstants			.MINIFY),
	TEXTURE_MAG_FILTER				(GL11.GL_TEXTURE_MAG_FILTER,							1, GLTexParameterFeatures.SAMPLER,										GLTextureFilter.TextureDefinedConstants			.MAGNIFY,	GLTextureFilter.SamplerDefinedConstants			.MAGNIFY),
	TEXTURE_MIN_LOD					(GL12.GL_TEXTURE_MIN_LOD,								1, GLTexParameterFeatures.SAMPLER	| GLTexParameterFeatures.FLOAT,		GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_MAX_LOD					(GL12.GL_TEXTURE_MAX_LOD,								1, GLTexParameterFeatures.SAMPLER	| GLTexParameterFeatures.FLOAT,		GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_MAX_LEVEL				(GL12.GL_TEXTURE_MAX_LEVEL,								1, 0,																	GLNonNegativeConstants							.of(),		GLInvalidConstants								.of	()),
	TEXTURE_SWIZZLE_R				(GL33.GL_TEXTURE_SWIZZLE_R,								1, 0,																	GLTextureComponent.DefinedConstants				.SINGLE,	GLInvalidConstants								.of	()),
	TEXTURE_SWIZZLE_G				(GL33.GL_TEXTURE_SWIZZLE_G,								1, 0,																	GLTextureComponent.DefinedConstants				.SINGLE,	GLInvalidConstants								.of	()),
	TEXTURE_SWIZZLE_B				(GL33.GL_TEXTURE_SWIZZLE_B,								1, 0,																	GLTextureComponent.DefinedConstants				.SINGLE,	GLInvalidConstants								.of	()),
	TEXTURE_SWIZZLE_A				(GL33.GL_TEXTURE_SWIZZLE_A,								1, 0,																	GLTextureComponent.DefinedConstants				.SINGLE,	GLInvalidConstants								.of	()),
	TEXTURE_SWIZZLE_RGBA			(GL33.GL_TEXTURE_SWIZZLE_RGBA,							4, 0,																	GLTextureComponent.DefinedConstants				.COMBINED,	GLInvalidConstants								.of	()),
	TEXTURE_WRAP_S					(GL11.GL_TEXTURE_WRAP_S,								1, GLTexParameterFeatures.SAMPLER,										GLTextureWrapFunction.TextureDefinedConstants	.INSTANCE,	GLTextureWrapFunction.SamplerDefinedConstants	.INSTANCE),
	TEXTURE_WRAP_T					(GL11.GL_TEXTURE_WRAP_T,								1, GLTexParameterFeatures.SAMPLER,										GLTextureWrapFunction.TextureDefinedConstants	.INSTANCE,	GLTextureWrapFunction.SamplerDefinedConstants	.INSTANCE),
	TEXTURE_WRAP_R					(GL12.GL_TEXTURE_WRAP_R,								1, GLTexParameterFeatures.SAMPLER,										GLTextureWrapFunction.TextureDefinedConstants	.INSTANCE,	GLTextureWrapFunction.SamplerDefinedConstants	.INSTANCE),
	TEXTURE_VIEW_MIN_LEVEL			(GL43.GL_TEXTURE_VIEW_MIN_LEVEL,						1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_VIEW_NUM_LEVELS			(GL43.GL_TEXTURE_VIEW_NUM_LEVELS,						1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_VIEW_MIN_LAYER			(GL43.GL_TEXTURE_VIEW_MIN_LAYER,						1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_VIEW_NUM_LAYERS			(GL43.GL_TEXTURE_VIEW_NUM_LAYERS,						1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_IMMUTABLE_LEVELS		(GL43.GL_TEXTURE_IMMUTABLE_LEVELS,						1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	IMAGE_FORMAT_COMPATIBILITY_TYPE	(GL42.GL_IMAGE_FORMAT_COMPATIBILITY_TYPE,				1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_IMMUTABLE_FORMAT		(GL42.GL_TEXTURE_IMMUTABLE_FORMAT,						1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_TARGET					(GL45.GL_TEXTURE_TARGET,								1, GLTexParameterFeatures.READ_ONLY,									GLNoneConstants									.of(),		GLInvalidConstants								.of	()),
	TEXTURE_MAX_ANISOTROPIC			(ARBTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY, 1, GLTexParameterFeatures.SAMPLER,										GLNoneConstants									.of(),		new GLQueryConstantsFloat							(GLGlobalParameter.MAX_TEXTURE_MAX_ANISOTROPY, 1.0f));

	private static final Int2ReferenceMap<GLTextureParameter> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var parameter : GLTextureParameter.values()) {
			TABLE.put(parameter.constant, parameter);
		}
	}

	private final int											constant;
	private final int											count;
	private final int											featureFlags;
	private final IGLDefinedConstants<GLTextureType>			textureValues;
	private final IGLDefinedConstants<IGLGlobalFunctionsHelper>	samplerValues;

	GLTextureParameter(
			int												constant,
			int												count,
			int												featureFlags,
			IGLDefinedConstants<GLTextureType>				textureValues,
			IGLDefinedConstants<IGLGlobalFunctionsHelper>	samplerValues
	) {
		this.constant		= constant;
		this.count			= count;
		this.featureFlags	= featureFlags;
		this.textureValues	= textureValues;
		this.samplerValues	= samplerValues;
	}

	public int getConstant() {
		return constant;
	}

	public int getCount() {
		return count;
	}

	public boolean isReadOnly() {
		return (featureFlags & GLTexParameterFeatures.READ_ONLY) != 0;
	}

	public boolean isSampler() {
		return (featureFlags & GLTexParameterFeatures.SAMPLER) != 0;
	}

	public boolean isFloat() {
		return (featureFlags & GLTexParameterFeatures.FLOAT) != 0;
	}

	public IGLDefinedConstants<GLTextureType> getTextureValues() {
		return textureValues;
	}

	public IGLDefinedConstants<IGLGlobalFunctionsHelper> getSamplerValues() {
		return samplerValues;
	}

	public static GLTextureParameter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
