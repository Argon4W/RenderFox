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

package com.github.argon4w.renderfox.opengl.buffer;

import com.github.argon4w.renderfox.opengl.buffer.function.IGLBufferHelperFunctions;
import com.github.argon4w.renderfox.opengl.buffer.function.IGLBufferStatelessFunctions;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.ints.IntStack;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class GLBufferStateManager {

	private final	Map<GLBufferType,		IntStack>				bufferStates;
	private final	Map<GLBufferBlockType,	Stack<GLBufferBlock>>	blockStates;

	private			IGLBufferStatelessFunctions						statelessFunctions;
	private			IGLBufferHelperFunctions						helperFunctions;
	private			boolean											manageStates;

	public GLBufferStateManager() {
		this.bufferStates		= new Reference2ReferenceOpenHashMap<>	();
		this.blockStates		= new Reference2ReferenceOpenHashMap<>	();

		this.statelessFunctions	= null;
		this.helperFunctions	= null;
	}

	public void initialize(OpenGLDevice device) {
		statelessFunctions	= device.getBufferContext	().getBufferFunctions	();
		helperFunctions		= device.getGlobalContext	().getGlobalHelper		();
		manageStates		= device.getCreateInfo		().useManageStates		();

		for (var type : GLBufferType.values()) {
			this.bufferStates.put(type, new IntArrayList());
		}

		for (var type : GLBufferBlockType.values()) {
			this.blockStates.put(type, new ReferenceArrayList<>());
		}
	}

	public void runScoped(List<GLBufferType> bufferTypes, Runnable runnable) {
		if (bufferTypes == null) {
			throw new IllegalArgumentException("BufferTypes cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (bufferTypes.isEmpty()) {
			throw new IllegalArgumentException("BufferTypes cannot be empty.");
		}

		for (var type : bufferTypes) {
			pushBufferBinding(type, -1);
		}

		runnable.run();

		for (var type : bufferTypes) {
			popBufferBinding(type);
		}
	}

	public <T> T runScoped(List<GLBufferType> bufferTypes, Supplier<T> supplier) {
		if (bufferTypes == null) {
			throw new IllegalArgumentException("BufferTypes cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (bufferTypes.isEmpty()) {
			throw new IllegalArgumentException("BufferTypes cannot be empty.");
		}

		for (var type : bufferTypes) {
			pushBufferBinding(type, -1);
		}

		var result = supplier.get();

		for (var type : bufferTypes) {
			popBufferBinding(type);
		}

		return result;
	}

	public void runScoped(Map<GLBufferType, Integer> buffers, Runnable runnable) {
		if (buffers == null) {
			throw new IllegalArgumentException("Buffers cannot be null.");
		}

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (buffers.isEmpty()) {
			throw new IllegalArgumentException("Buffers cannot be empty.");
		}

		for (var type : buffers.keySet()) {
			pushBufferBinding(type, buffers.get(type));
		}

		runnable.run();

		for (var type : buffers.keySet()) {
			popBufferBinding(type);
		}
	}

	public <T> T runScoped(Map<GLBufferType, Integer> buffers, Supplier<T> supplier) {
		if (buffers == null) {
			throw new IllegalArgumentException("Buffers cannot be null.");
		}

		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (buffers.isEmpty()) {
			throw new IllegalArgumentException("Buffers cannot be empty.");
		}

		for (var type : buffers.keySet()) {
			pushBufferBinding(type, buffers.get(type));
		}

		var result = supplier.get();

		for (var type : buffers.keySet()) {
			popBufferBinding(type);
		}

		return result;
	}

	public void runBlockScoped(Map<GLBufferBlockType, IntIntPair> bufferBlockTypes, Runnable runnable) {
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable cannot be null.");
		}

		if (bufferBlockTypes == null) {
			throw new IllegalArgumentException("BufferBlockTypes cannot be null.");
		}

		for (var entry : bufferBlockTypes.entrySet()) {
			pushBufferBlockBindings(
					entry.getKey(),
					entry.getValue().firstInt(),
					entry.getValue().secondInt()
			);
		}

		runnable.run();
		for (var blockType : bufferBlockTypes.keySet()) {
			popBufferBlockBindings(blockType);
		}
	}

	public <T> T runBlockScoped(Map<GLBufferBlockType, IntIntPair> bufferBlockTypes, Supplier<T> supplier) {
		if (supplier == null) {
			throw new IllegalArgumentException("Supplier cannot be null.");
		}

		if (bufferBlockTypes == null) {
			throw new IllegalArgumentException("BufferBlockTypes cannot be null.");
		}

		for (var entry : bufferBlockTypes.entrySet()) {
			pushBufferBlockBindings(
					entry.getKey(),
					entry.getValue().firstInt(),
					entry.getValue().secondInt()
			);
		}

		T result = supplier.get();

		for (var blockType : bufferBlockTypes.keySet()) {
			popBufferBlockBindings(blockType);
		}

		return result;
	}

	public void pushBufferBinding(GLBufferType bufferType, int bufferHandle) {
		var stack = bufferStates.get(bufferType);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (stack == null) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (manageStates) {
			stack.push(helperFunctions.getBoundBuffer(bufferType));
		}

		if (bufferHandle != -1) {
			statelessFunctions.bindBuffer(bufferType.getConstant(), bufferHandle);
		}
	}

	public void popBufferBinding(GLBufferType bufferType) {
		var stack = bufferStates.get(bufferType);

		if (bufferType == null) {
			throw new IllegalArgumentException("BufferType cannot be null.");
		}

		if (bufferType == GLBufferType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferType.");
		}

		if (stack.isEmpty()) {
			throw new IllegalStateException("No buffer bound to: %s".formatted(bufferType.toString()));
		}

		if (manageStates) {
			statelessFunctions.bindBuffer(bufferType.getConstant(), stack.popInt());
		}
	}

	public void pushBufferBlockBindings(
			GLBufferBlockType	bufferBlockType,
			int					fromBindingPoint,
			int					toBindingPoint
	) {
		var stack = blockStates.get(bufferBlockType);

		if (bufferBlockType == null) {
			throw new IllegalArgumentException("BufferBlockType cannot be null.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferBlockType.");
		}

		if (stack == null) {
			throw new IllegalArgumentException("Invalid bufferBlockType.");
		}

		if (manageStates) {
			stack.push(new GLBufferBlock(
					bufferBlockType,
					fromBindingPoint,
					toBindingPoint
			));
		}
	}

	public void popBufferBlockBindings(GLBufferBlockType bufferBlockType) {
		var stack = blockStates.get(bufferBlockType);

		if (bufferBlockType == null) {
			throw new IllegalArgumentException("BufferBlockType cannot be null.");
		}

		if (bufferBlockType == GLBufferBlockType.INVALID) {
			throw new IllegalArgumentException("Invalid bufferBlockType.");
		}

		if (stack.isEmpty()) {
			throw new IllegalStateException("No buffer block bound to: %s".formatted(bufferBlockType.toString()));
		}

		if (manageStates) {
			stack.pop().restore();
		}
	}

	public class GLBufferBlock {

		private final GLBufferBlockType	bufferBlockType;
		private final int				bufferBlockFromBinding;
		private final int				bufferBlockToBinding;
		private final int	[]			bufferHandles;
		private final long	[]			bufferBindingOffsets;
		private final long	[]			bufferBindingLengths;

		public GLBufferBlock(
				GLBufferBlockType	bufferBlockType,
				int					fromBindingPoint,
				int					toBindingPoint
		) {
			if (fromBindingPoint < 0) {
				throw new IllegalArgumentException("FromBindingPoint cannot be negative.");
			}

			if (toBindingPoint < 0) {
				throw new IllegalArgumentException("FromBindingPoint cannot be negative.");
			}

			if (fromBindingPoint >= toBindingPoint) {
				throw new IllegalArgumentException("FromBindingPoint must be less than toBindingPoint.");
			}

			if (toBindingPoint > helperFunctions.getBufferMaxBindings(bufferBlockType)) {
				throw new IllegalArgumentException("ToBindingPoint cannot be greater than the maximum index of binding points allowed of the given bufferBlockType.");
			}

			this.bufferBlockType		= bufferBlockType;
			this.bufferBlockFromBinding	= fromBindingPoint;
			this.bufferBlockToBinding	= toBindingPoint;

			var bindingPoints = toBindingPoint - fromBindingPoint;

			this.bufferHandles			= new int	[bindingPoints];
			this.bufferBindingOffsets	= new long	[bindingPoints];
			this.bufferBindingLengths	= new long	[bindingPoints];

			for (var bindingPoint = this.bufferBlockFromBinding; bindingPoint < this.bufferBlockToBinding; bindingPoint ++) {
				this.bufferHandles			[bindingPoint] = helperFunctions.getBoundBuffer			(bufferBlockType, bindingPoint);
				this.bufferBindingOffsets	[bindingPoint] = helperFunctions.getBoundBufferOffset	(bufferBlockType, bindingPoint);
				this.bufferBindingLengths	[bindingPoint] = helperFunctions.getBoundBufferLength	(bufferBlockType, bindingPoint);
			}
		}

		public void restore() {
			for (var bindingPoint = this.bufferBlockFromBinding; bindingPoint < this.bufferBlockToBinding; bindingPoint ++) {
				var bufferHandle		= bufferHandles			[bindingPoint];
				var bufferBindingOffset	= bufferBindingOffsets	[bindingPoint];
				var bufferBindingSize	= bufferBindingLengths	[bindingPoint];

				var bufferType			= bufferBlockType.getBufferType		();
				var bufferTypeCode		= bufferBlockType.getTypeConstant	();

				if (		bufferBindingOffset	== 0
						&&	bufferBindingSize	== 0
				) {
					statelessFunctions.bindBufferBase(
							bufferTypeCode,
							bindingPoint,
							bufferHandle,
							bufferType
					);
				} else {
					statelessFunctions.bindBufferRange(
							bufferTypeCode,
							bindingPoint,
							bufferHandle,
							bufferBindingOffset,
							bufferBindingSize,
							bufferType
					);
				}
			}
		}
	}
}
