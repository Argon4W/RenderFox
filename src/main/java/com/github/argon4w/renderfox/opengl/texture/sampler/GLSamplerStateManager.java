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

package com.github.argon4w.renderfox.opengl.texture.sampler;

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.sampler.functions.IGLSamplerStatelessFunctions;
import it.unimi.dsi.fastutil.ints.*;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public class GLSamplerStateManager {

	private final	Int2ReferenceMap<IntStack>		samplerStates;

	private			IGLSamplerStatelessFunctions	statelessFunctions;
	private			IGLGlobalFunctionsHelper		helperFunctions;
	private			boolean							manageStates;
	private			int								maxTextureUnits;

	public GLSamplerStateManager() {
		this.samplerStates		= new Int2ReferenceOpenHashMap<>();
		this.statelessFunctions	= null;
		this.helperFunctions	= null;
		this.manageStates		= false;
		this.maxTextureUnits	= 0;
	}

	public void initialize(OpenGLDevice device) {
		statelessFunctions	= device.getTextureContext	()	.getSamplerFunctions	();
		helperFunctions		= device.getGlobalContext	()	.getGlobalHelper		();
		manageStates		= device.getCreateInfo		()	.useManageStates		();
		maxTextureUnits		= helperFunctions				.getMaxTextureUnits		();

		for (var unit = 0; unit < maxTextureUnits; unit ++) {
			samplerStates.put(unit, new IntArrayList());
		}
	}

	public void runScoped(Collection<Integer> textureUnits, Runnable runnable) {
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
			pushSamplerBinding(unit, -1);
		}

		runnable.run();

		for (var unit : textureUnits) {
			popSamplerBinding(unit);
		}
	}

	public <T> T runScoped(Collection<Integer> textureUnits, Supplier<T> supplier) {
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
			pushSamplerBinding(unit, -1);
		}

		var result = supplier.get();

		for (var unit : textureUnits) {
			popSamplerBinding(unit);
		}

		return result;
	}

	public void runScoped(Map<Integer, Integer> samplers, Runnable runnable) {
		if (samplers == null) {
			throw new IllegalArgumentException("Samplers cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (samplers.isEmpty()) {
			throw new IllegalArgumentException("Samplers cannot be empty.");
		}

		for (var unit : samplers.keySet()) {
			pushSamplerBinding(unit, samplers.get(unit));
		}

		runnable.run();

		for (var unit : samplers.keySet()) {
			popSamplerBinding(unit);
		}
	}

	public <T> T runScoped(Map<Integer, Integer> samplers, Supplier<T> supplier) {
		if (samplers == null) {
			throw new IllegalArgumentException("Samplers cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (samplers.isEmpty()) {
			throw new IllegalArgumentException("Samplers cannot be empty.");
		}

		for (var unit : samplers.keySet()) {
			pushSamplerBinding(unit, samplers.get(unit));
		}

		var result = supplier.get();

		for (var unit : samplers.keySet()) {
			popSamplerBinding(unit);
		}

		return result;
	}

	public void pushSamplerBinding(int textureUnit, int samplerHandle) {
		var stack = samplerStates.get(textureUnit);

		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit cannot be null.");
		}

		if (textureUnit >= maxTextureUnits) {
			throw new IllegalArgumentException("TextureUnit is greater than or equal to the value of GL_MAX_COMBINED_TEXTURE_UNITS.");
		}

		if (stack == null) {
			throw new IllegalStateException("Invalid textureUnit.");
		}

		if (manageStates) {
			stack.push(helperFunctions.getBoundSampler(textureUnit));
		}

		if (samplerHandle != -1) {
			statelessFunctions.bindSampler(textureUnit, samplerHandle);
		}
	}

	public void popSamplerBinding(int textureUnit) {
		var stack = samplerStates.get(textureUnit);

		if (textureUnit < 0) {
			throw new IllegalArgumentException("TextureUnit cannot be null.");
		}

		if (textureUnit >= maxTextureUnits) {
			throw new IllegalArgumentException("TextureUnit is greater than or equal to the value of GL_MAX_COMBINED_TEXTURE_UNITS.");
		}

		if (stack.isEmpty()) {
			throw new IllegalStateException("Invalid textureUnit.");
		}

		if (manageStates) {
			throw new IllegalStateException("No sampler bound to: %d".formatted(textureUnit));
		}
	}
}
