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

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.texture.function.IGLTextureHelperFunctions;
import com.github.argon4w.renderfox.opengl.texture.function.IGLTextureStatelessFunctions;
import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public class GLTextureStateManager {

	private final	Map				<GLTextureType, IntStack>		textureStates;
	private final	Int2ReferenceMap<Stack<TextureUnitSet>>			textureUnitSetStates;
	private final	IntStack										textureUnitStates;

	private			IGLTextureStatelessFunctions					statelessFunctions;
	private			IGLTextureHelperFunctions						helperFunctions;
	private			boolean											manageStates;
	private			int												maxTextureUnits;

	public GLTextureStateManager() {
		this.textureStates			= new Reference2ReferenceOpenHashMap<>	();
		this.textureUnitSetStates	= new Int2ReferenceOpenHashMap		<>	();
		this.textureUnitStates		= new IntArrayList						();

		this.statelessFunctions	= null;
		this.helperFunctions	= null;
		this.manageStates		= false;
		this.maxTextureUnits	= 0;
	}

	public void initialize(OpenGLDevice device) {
		statelessFunctions	= device.getTextureContext	()	.getTextureFunctions();
		helperFunctions		= device.getGlobalContext	()	.getGlobalHelper	();
		manageStates		= device.getCreateInfo		()	.useManageStates	();
		maxTextureUnits		= helperFunctions				.getMaxTextureUnits	();

		for (var type : GLTextureType.values()) {
			textureStates.put(type, new IntArrayList());
		}

		for (var unit = 0; unit < maxTextureUnits; unit ++) {
			textureUnitSetStates.put(unit, new ReferenceArrayList<>());
		}
	}

	public void runTextureScoped(Collection<GLTextureType> textureTypes, Runnable runnable) {
		if (textureTypes == null) {
			throw new IllegalArgumentException("TextureTypes cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (textureTypes.isEmpty()) {
			throw new IllegalArgumentException("TextureTypes cannot be empty.");
		}

		for (var type : textureTypes) {
			pushTextureBinding(type, -1);
		}

		runnable.run();

		for (var type : textureTypes) {
			popTextureBinding(type);
		}
	}

	public <T> T runTextureScoped(Collection<GLTextureType> textureTypes, Supplier<T> supplier) {
		if (textureTypes == null) {
			throw new IllegalArgumentException("TextureTypes cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (textureTypes.isEmpty()) {
			throw new IllegalArgumentException("TextureTypes cannot be empty.");
		}

		for (var type : textureTypes) {
			pushTextureBinding(type, -1);
		}

		var result = supplier.get();

		for (var type : textureTypes) {
			popTextureBinding(type);
		}

		return result;
	}

	public void runTextureScoped(Map<GLTextureType, Integer> textures, Runnable runnable) {
		if (textures == null) {
			throw new IllegalArgumentException("Textures cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (textures.isEmpty()) {
			throw new IllegalArgumentException("Textures cannot be empty.");
		}

		for (var type : textures.keySet()) {
			pushTextureBinding(type, textures.get(type));
		}

		runnable.run();

		for (var type : textures.keySet()) {
			popTextureBinding(type);
		}
	}

	public <T> T runTextureScoped(Map<GLTextureType, Integer> textures, Supplier<T> supplier) {
		if (textures == null) {
			throw new IllegalArgumentException("Textures cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (textures.isEmpty()) {
			throw new IllegalArgumentException("Textures cannot be empty.");
		}

		for (var type : textures.keySet()) {
			pushTextureBinding(type, textures.get(type));
		}

		var result = supplier.get();

		for (var type : textures.keySet()) {
			popTextureBinding(type);
		}

		return result;
	}

	public void runUnitSetScoped(Collection<Integer> textureUnits, Runnable runnable) {
		if (textureUnits == null) {
			throw new IllegalArgumentException("TextureUnits cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (textureUnits.isEmpty()) {
			throw new IllegalArgumentException("TextureUnits cannot be empty.");
		}

		for (var unit : textureUnits) {
			pushTextureUnitSet(unit);
		}

		runnable.run();

		for (var unit : textureUnits) {
			pushTextureUnitSet(unit);
		}
	}

	public <T> T runUnitSetScoped(Collection<Integer> textureUnits, Supplier<T> supplier) {
		if (textureUnits == null) {
			throw new IllegalArgumentException("TextureUnits cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (textureUnits.isEmpty()) {
			throw new IllegalArgumentException("TextureUnits cannot be empty.");
		}

		for (var unit : textureUnits) {
			pushTextureUnitSet(unit);
		}

		var result = supplier.get();

		for (var unit : textureUnits) {
			pushTextureUnitSet(unit);
		}

		return result;
	}

	public void runUnitScoped(int textureUnit, Runnable runnable) {
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		pushTextureUnit();

		helperFunctions.activeTextureUnit(textureUnit);

		runnable.run();

		popTextureUnit();
	}

	public <T> T runUnitScoped(int textureUnit, Supplier<T> supplier) {
		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		pushTextureUnit();

		helperFunctions.activeTextureUnit(textureUnit);

		var result = supplier.get();

		popTextureUnit();

		return result;
	}

	public void pushTextureBinding(GLTextureType textureType, int textureHandle) {
		var stack = textureStates.get(textureType);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		if (stack == null) {
			throw new IllegalStateException("Invalid textureType.");
		}

		if (manageStates) {
			stack.push(helperFunctions.getBoundTexture(textureType));
		}

		if (textureHandle != -1) {
			statelessFunctions.bindTexture(textureType.getConstant(), textureHandle);
		}
	}

	public void popTextureBinding(GLTextureType textureType) {
		var stack = textureStates.get(textureType);

		if (textureType == null) {
			throw new IllegalArgumentException("TextureType cannot be null.");
		}

		if (textureType == GLTextureType.INVALID) {
			throw new IllegalArgumentException("Invalid textureType.");
		}

		if (stack.isEmpty()) {
			throw new IllegalStateException("No texture bound to: %s".formatted(textureType.toString()));
		}

		if (manageStates) {
			statelessFunctions.bindTexture(textureType.getConstant(), stack.popInt());
		}
	}

	public void pushTextureUnitSet(int textureUnit) {
		var stack = textureUnitSetStates.get(textureUnit);

		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit cannot be negative.");
		}

		if (textureUnit >= maxTextureUnits) {
			throw new IllegalArgumentException("TextureUnit cannot be greater or equal to the value of GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS minus one.");
		}

		if (stack == null) {
			throw new IllegalStateException("Invalid textureUnit.");
		}

		if (manageStates) {
			stack.push(new TextureUnitSet(textureUnit));
		}
	}

	public void popTextureUnitSet(int textureUnit) {
		var stack = textureUnitSetStates.get(textureUnit);

		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit cannot be negative.");
		}

		if (textureUnit >= maxTextureUnits) {
			throw new IllegalArgumentException("TextureUnit cannot be greater or equal to the value of GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS minus one.");
		}

		if (stack == null) {
			throw new IllegalStateException("Invalid textureUnit.");
		}

		if (manageStates) {
			stack.pop().restore();
		}
	}

	public void pushTextureUnit() {
		if (manageStates) {
			textureUnitStates.push(helperFunctions.getActiveTextureUnit());
		}
	}

	public void popTextureUnit() {
		if (textureUnitStates.isEmpty()) {
			throw new IllegalStateException("No texture unit is active.");
		}

		if (manageStates) {
			helperFunctions.activeTextureUnit(textureUnitStates.popInt());
		}
	}

	public class TextureUnitSet {

		private final int textureUnit;
		private final int texture1DBinding;
		private final int texture2DBinding;
		private final int texture3DBinding;
		private final int texture1DArrayBinding;
		private final int texture2DArrayBinding;
		private final int textureRectangleBinding;
		private final int textureCubeMapBinding;
		private final int textureCubeMapArrayBinding;
		private final int texture2DMultisampleBinding;
		private final int texture2DMultisampleArrayBinding;

		private TextureUnitSet(int textureUnit) {
			this.textureUnit						= textureUnit;
			this.texture1DBinding					= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_1D);
			this.texture2DBinding					= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_2D);
			this.texture3DBinding					= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_3D);
			this.texture1DArrayBinding				= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_1D_ARRAY);
			this.texture2DArrayBinding				= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_2D_ARRAY);
			this.textureRectangleBinding			= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_RECTANGLE);
			this.textureCubeMapBinding				= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_CUBE_MAP);
			this.textureCubeMapArrayBinding			= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_CUBE_MAP_ARRAY);
			this.texture2DMultisampleBinding		= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_2D_MULTISAMPLE);
			this.texture2DMultisampleArrayBinding	= helperFunctions.getBoundTexture(GLTextureType.TEXTURE_2D_MULTISAMPLE_ARRAY);
		}

		public void restore() {
			runUnitScoped(textureUnit, () -> {
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_1D						.getConstant(), texture1DBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_2D						.getConstant(), texture2DBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_3D						.getConstant(), texture3DBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_1D_ARRAY				.getConstant(), texture1DArrayBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_2D_ARRAY				.getConstant(), texture2DArrayBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_RECTANGLE				.getConstant(), textureRectangleBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_CUBE_MAP				.getConstant(), textureCubeMapBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_CUBE_MAP_ARRAY			.getConstant(), textureCubeMapArrayBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_2D_MULTISAMPLE			.getConstant(), texture2DMultisampleBinding);
				statelessFunctions.bindTexture(GLTextureType.TEXTURE_2D_MULTISAMPLE_ARRAY	.getConstant(), texture2DMultisampleArrayBinding);
			});
		}
	}
}
