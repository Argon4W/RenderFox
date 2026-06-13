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

package com.github.argon4w.renderfox.opengl.framebuffer;

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.function.IGLFramebufferHelperFunctions;
import com.github.argon4w.renderfox.opengl.framebuffer.function.IGLFramebufferStatelessFunctions;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class GLFramebufferStateManager {

	private final	Map<GLFramebufferType,	IntStack>	framebufferStates;
	private			IGLFramebufferStatelessFunctions	statelessFunctions;
	private			IGLFramebufferHelperFunctions		helperFunctions;
	private			boolean								manageStates;

	public GLFramebufferStateManager() {
		this.framebufferStates	= new Reference2ReferenceOpenHashMap<>();
		this.statelessFunctions	= null;
		this.helperFunctions	= null;
	}

	public void initialize(OpenGLDevice device) {
		statelessFunctions	= device.getFramebufferContext	().getFramebufferFunctions	();
		helperFunctions		= device.getGlobalContext		().getGlobalHelper			();
		manageStates		= device.getCreateInfo			().useManageStates			();

		for (var type : GLFramebufferType.values()) {
			this.framebufferStates.put(type, new IntArrayList());
		}
	}

	public void runScoped(List<GLFramebufferType> framebufferTypes, Runnable runnable) {
		if (framebufferTypes == null) {
			throw new IllegalArgumentException("FramebufferTypes cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (framebufferTypes.isEmpty()) {
			throw new IllegalArgumentException("FramebufferTypes cannot be empty.");
		}

		for (var type : framebufferTypes) {
			pushFramebufferBinding(type, -1);
		}

		runnable.run();

		for (var type : framebufferTypes) {
			popFramebufferBinding(type);
		}
	}

	public <T> T runScoped(List<GLFramebufferType> framebufferTypes, Supplier<T> supplier) {
		if (framebufferTypes == null) {
			throw new IllegalArgumentException("FramebufferTypes cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (framebufferTypes.isEmpty()) {
			throw new IllegalArgumentException("FramebufferTypes cannot be empty.");
		}

		for (var type : framebufferTypes) {
			pushFramebufferBinding(type, -1);
		}

		var result = supplier.get();

		for (var type : framebufferTypes) {
			popFramebufferBinding(type);
		}

		return result;
	}

	public void runScoped(Map<GLFramebufferType, Integer> framebuffers, Runnable runnable) {
		if (framebuffers == null) {
			throw new IllegalArgumentException("Framebuffers cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (framebuffers.isEmpty()) {
			throw new IllegalArgumentException("Framebuffers cannot be empty.");
		}

		for (var type : framebuffers.keySet()) {
			pushFramebufferBinding(type, framebuffers.get(type));
		}

		runnable.run();

		for (var type : framebuffers.keySet()) {
			popFramebufferBinding(type);
		}
	}

	public <T> T runScoped(Map<GLFramebufferType, Integer> framebuffers, Supplier<T> supplier) {
		if (framebuffers == null) {
			throw new IllegalArgumentException("Framebuffers cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (framebuffers.isEmpty()) {
			throw new IllegalArgumentException("Framebuffers cannot be empty.");
		}

		for (var type : framebuffers.keySet()) {
			pushFramebufferBinding(type, framebuffers.get(type));
		}

		var result = supplier.get();

		for (var type : framebuffers.keySet()) {
			popFramebufferBinding(type);
		}

		return result;
	}

	public void pushFramebufferBinding(GLFramebufferType framebufferType, int framebufferHandle) {
		var stack = framebufferStates.get(framebufferType);

		if (framebufferType == null) {
			throw new IllegalArgumentException("FramebufferType cannot be null.");
		}

		if (framebufferType == GLFramebufferType.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferType.");
		}

		if (stack == null) {
			throw new IllegalArgumentException("Invalid framebufferType.");
		}

		if (manageStates) {
			stack.push(helperFunctions.getBoundFramebuffer(framebufferType));
		}

		if (framebufferHandle != -1) {
			statelessFunctions.bindFramebuffer(framebufferType.getConstant(), framebufferHandle);
		}
	}

	public void popFramebufferBinding(GLFramebufferType framebufferType) {
		var stack = framebufferStates.get(framebufferType);

		if (framebufferType == null) {
			throw new IllegalArgumentException("FramebufferType cannot be null.");
		}

		if (framebufferType == GLFramebufferType.INVALID) {
			throw  new IllegalArgumentException("Invalid framebufferType.");
		}

		if (stack.isEmpty()) {
			throw new IllegalStateException("No framebuffer bound to: %s".formatted(framebufferType.toString()));
		}

		if (manageStates) {
			statelessFunctions.bindFramebuffer(framebufferType.getConstant(), stack.popInt());
		}
	}
}
