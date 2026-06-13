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

package com.github.argon4w.renderfox.opengl.texture;

import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureLevelParameter;
import com.github.argon4w.renderfox.texture.ITextureType;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import org.lwjgl.opengl.*;

import java.util.Set;

public enum GLTextureType implements ITextureType {

	INVALID						(-1,									GLGlobalParameter.INVALID,								GLTextureLevelParameter.INVALID,		-1,	0, 0),
	TEXTURE_1D					(GL11.GL_TEXTURE_1D,					GLGlobalParameter.TEXTURE_1D_BINDING,					GLTextureLevelParameter.INVALID,		-1,	1, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MIPMAP),
	TEXTURE_2D					(GL11.GL_TEXTURE_2D,					GLGlobalParameter.TEXTURE_2D_BINDING,					GLTextureLevelParameter.INVALID,		-1,	2, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MIPMAP		| GLTextureFeatures.DEPTH),
	TEXTURE_3D					(GL12.GL_TEXTURE_3D,					GLGlobalParameter.TEXTURE_3D_BINDING,					GLTextureLevelParameter.INVALID,		-1,	3, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MIPMAP),
	TEXTURE_1D_ARRAY			(GL30.GL_TEXTURE_1D_ARRAY,				GLGlobalParameter.TEXTURE_1D_ARRAY_BINDING,				GLTextureLevelParameter.TEXTURE_HEIGHT,	1,	2, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MIPMAP		| GLTextureFeatures.ARRAY),
	TEXTURE_2D_ARRAY			(GL30.GL_TEXTURE_2D_ARRAY,				GLGlobalParameter.TEXTURE_2D_ARRAY_BINDING,				GLTextureLevelParameter.TEXTURE_DEPTH,	2,	3, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MIPMAP		| GLTextureFeatures.ARRAY),
	TEXTURE_RECTANGLE			(GL31.GL_TEXTURE_RECTANGLE,				GLGlobalParameter.TEXTURE_RECTANGLE_BINDING,			GLTextureLevelParameter.INVALID,		-1,	2, GLTextureFeatures.COMPLETE									| GLTextureFeatures.DEPTH),
	TEXTURE_CUBE_MAP			(GL13.GL_TEXTURE_CUBE_MAP,				GLGlobalParameter.TEXTURE_CUBE_MAP_BINDING,				GLTextureLevelParameter.TEXTURE_DEPTH,	2,	2, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MIPMAP									| GLTextureFeatures.CUBE),
	TEXTURE_CUBE_MAP_ARRAY		(GL40.GL_TEXTURE_CUBE_MAP_ARRAY,		GLGlobalParameter.TEXTURE_CUBE_MAP_ARRAY_BINDING,		GLTextureLevelParameter.TEXTURE_DEPTH,	2,	3, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MIPMAP		| GLTextureFeatures.ARRAY	| GLTextureFeatures.CUBE),
	TEXTURE_2D_MULTISAMPLE		(GL32.GL_TEXTURE_2D_MULTISAMPLE,		GLGlobalParameter.TEXTURE_2D_MULTISAMPLE_BINDING,		GLTextureLevelParameter.INVALID,		-1,	2, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MULTISAMPLED),
	TEXTURE_2D_MULTISAMPLE_ARRAY(GL32.GL_TEXTURE_2D_MULTISAMPLE_ARRAY,	GLGlobalParameter.TEXTURE_2D_MULTISAMPLE_ARRAY_BINDING,	GLTextureLevelParameter.INVALID,		-1,	3, GLTextureFeatures.COMPLETE |	GLTextureFeatures.MULTISAMPLED	| GLTextureFeatures.ARRAY),
	CUBE_MAP_POSITIVE_X			(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X,	GLGlobalParameter.TEXTURE_CUBE_MAP_BINDING,				GLTextureLevelParameter.INVALID,		-1,	2,								GLTextureFeatures.MIPMAP),
	CUBE_MAP_NEGATIVE_X			(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,	GLGlobalParameter.TEXTURE_CUBE_MAP_BINDING,				GLTextureLevelParameter.INVALID,		-1,	2,								GLTextureFeatures.MIPMAP),
	CUBE_MAP_POSITIVE_Y			(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,	GLGlobalParameter.TEXTURE_CUBE_MAP_BINDING,				GLTextureLevelParameter.INVALID,		-1,	2,								GLTextureFeatures.MIPMAP),
	CUBE_MAP_NEGATIVE_Y			(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,	GLGlobalParameter.TEXTURE_CUBE_MAP_BINDING,				GLTextureLevelParameter.INVALID,		-1,	2,								GLTextureFeatures.MIPMAP),
	CUBE_MAP_POSITIVE_Z			(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,	GLGlobalParameter.TEXTURE_CUBE_MAP_BINDING,				GLTextureLevelParameter.INVALID,		-1,	2,								GLTextureFeatures.MIPMAP),
	CUBE_MAP_NEGATIVE_Z			(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z,	GLGlobalParameter.TEXTURE_CUBE_MAP_BINDING,				GLTextureLevelParameter.INVALID,		-1,	2,								GLTextureFeatures.MIPMAP);

	public static final GLTextureType[] CUBE_MAP_FACES = new GLTextureType[] {
			GLTextureType.CUBE_MAP_POSITIVE_X, GLTextureType.CUBE_MAP_NEGATIVE_X,
			GLTextureType.CUBE_MAP_POSITIVE_Y, GLTextureType.CUBE_MAP_NEGATIVE_Y,
			GLTextureType.CUBE_MAP_POSITIVE_Z, GLTextureType.CUBE_MAP_NEGATIVE_Z
	};

	private static final Int2ReferenceMap		<GLTextureType>						TABLE_TYPE_CONSTANT;
	private static final Int2ReferenceMap		<GLTextureType>						TABLE_BINDING_CONSTANT;
	private static final Reference2ReferenceMap	<GLTextureType, Set<GLTextureType>>	TABLE_VIEW;

	static {
		TABLE_TYPE_CONSTANT		= new Int2ReferenceOpenHashMap		<>();
		TABLE_BINDING_CONSTANT	= new Int2ReferenceOpenHashMap		<>();
		TABLE_VIEW				= new Reference2ReferenceOpenHashMap<>();

		for (var textureType : GLTextureType.values()) {
			TABLE_TYPE_CONSTANT		.put(textureType.getConstant		(),	textureType);
			TABLE_BINDING_CONSTANT	.put(textureType.getBindingConstant	(), textureType);
			TABLE_VIEW				.put(textureType,						new ReferenceOpenHashSet<>());
		}

		TABLE_VIEW.get(TEXTURE_1D)					.addAll(ReferenceSet.of(TEXTURE_1D, TEXTURE_1D_ARRAY));
		TABLE_VIEW.get(TEXTURE_2D)					.addAll(ReferenceSet.of(TEXTURE_2D, TEXTURE_2D_ARRAY));
		TABLE_VIEW.get(TEXTURE_3D)					.addAll(ReferenceSet.of(TEXTURE_3D));
		TABLE_VIEW.get(TEXTURE_RECTANGLE)			.addAll(ReferenceSet.of(TEXTURE_RECTANGLE));
		TABLE_VIEW.get(TEXTURE_CUBE_MAP)			.addAll(ReferenceSet.of(TEXTURE_CUBE_MAP, TEXTURE_CUBE_MAP_ARRAY, TEXTURE_2D, TEXTURE_2D_ARRAY));
		TABLE_VIEW.get(TEXTURE_CUBE_MAP_ARRAY)		.addAll(ReferenceSet.of(TEXTURE_CUBE_MAP, TEXTURE_CUBE_MAP_ARRAY, TEXTURE_2D, TEXTURE_2D_ARRAY));
		TABLE_VIEW.get(TEXTURE_1D_ARRAY)			.addAll(ReferenceSet.of(TEXTURE_1D, TEXTURE_1D_ARRAY));
		TABLE_VIEW.get(TEXTURE_2D_ARRAY)			.addAll(ReferenceSet.of(TEXTURE_2D, TEXTURE_2D_ARRAY));
		TABLE_VIEW.get(TEXTURE_2D_MULTISAMPLE)		.addAll(ReferenceSet.of(TEXTURE_2D_MULTISAMPLE, TEXTURE_2D_MULTISAMPLE_ARRAY));
		TABLE_VIEW.get(TEXTURE_2D_MULTISAMPLE_ARRAY).addAll(ReferenceSet.of(TEXTURE_2D_MULTISAMPLE, TEXTURE_2D_MULTISAMPLE_ARRAY));
	}

	private final int						constant;
	private final IGLParameter				bindingParameter;
	private final GLTextureLevelParameter	layerParameter;
	private final int						layerIndex;
	private final int						dimensions;
	private final int						featureFlags;

	GLTextureType(
			int						constant,
			IGLParameter			bindingParameter,
			GLTextureLevelParameter	layerParameter,
			int						layerIndex,
			int						dimensions,
			int						featureFlags
	) {
		this.constant			= constant;
		this.bindingParameter	= bindingParameter;
		this.layerParameter		= layerParameter;
		this.layerIndex			= layerIndex;
		this.dimensions			= dimensions;
		this.featureFlags		= featureFlags;
	}

	public int getConstant() {
		return constant;
	}

	public int getLayerIndex() {
		return layerIndex;
	}

	public int getBindingConstant() {
		return bindingParameter.getConstant();
	}

	public IGLParameter getParameter() {
		return bindingParameter;
	}

	public GLTextureLevelParameter getLayerParameter() {
		return layerParameter;
	}

	@Override
	public int getDimensions() {
		return dimensions;
	}

	@Override
	public boolean isArray() {
		return (featureFlags & GLTextureFeatures.ARRAY) != 0;
	}

	@Override
	public boolean isComplete() {
		return (featureFlags & GLTextureFeatures.COMPLETE) != 0;
	}

	@Override
	public boolean isMultisampled() {
		return (featureFlags & GLTextureFeatures.MULTISAMPLED) != 0;
	}

	@Override
	public boolean isCube() {
		return (featureFlags & GLTextureFeatures.CUBE) != 0;
	}

	@Override
	public boolean hasMipmap() {
		return (featureFlags & GLTextureFeatures.MIPMAP) != 0;
	}

	@Override
	public boolean canDepth() {
		return (featureFlags & GLTextureFeatures.DEPTH) != 0;
	}

	public boolean isCompatible(GLTextureType textureType) {
		return TABLE_VIEW.get(this).contains(textureType);
	}

	public static GLTextureType fromConstant(int constant) {
		return TABLE_TYPE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLTextureType fromBindingConstant(int constant) {
		return TABLE_BINDING_CONSTANT.getOrDefault(constant, INVALID);
	}
}
