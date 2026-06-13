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

import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalIndexedParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;

public enum GLBufferBlockType {

	INVALID					(GLBufferType.INVALID,					GLGlobalIndexedParameter.INVALID,						GLGlobalIndexedParameter.INVALID,						GLGlobalParameter.INVALID,								GLGlobalParameter.INVALID),
	UNIFORM_BUFFER			(GLBufferType.UNIFORM_BUFFER,			GLGlobalIndexedParameter.UNIFORM_BUFFER_START,			GLGlobalIndexedParameter.UNIFORM_BUFFER_SIZE,			GLGlobalParameter.MAX_UNIFORM_BUFFER_BINDINGS,			GLGlobalParameter.UNIFORM_BUFFER_OFFSET_ALIGNMENT),
	SHADER_STORAGE_BUFFER	(GLBufferType.SHADER_STORAGE_BUFFER,	GLGlobalIndexedParameter.SHADER_STORAGE_BUFFER_START,	GLGlobalIndexedParameter.SHADER_STORAGE_BUFFER_SIZE,	GLGlobalParameter.MAX_SHADER_STORAGE_BUFFER_BINDINGS,	GLGlobalParameter.SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT),
	ATOMIC_COUNTER_BUFFER	(GLBufferType.ATOMIC_COUNTER_BUFFER,	GLGlobalIndexedParameter.ATOMIC_COUNTER_BUFFER_START,	GLGlobalIndexedParameter.ATOMIC_COUNTER_BUFFER_SIZE,	GLGlobalParameter.MAX_ATOMIC_COUNTER_BUFFER_BINDINGS,	GLGlobalParameter.INVALID);

	private static final Reference2ReferenceMap	<GLBufferType, GLBufferBlockType>	TABLE_BUFFER_TYPE;
	private static final Int2ReferenceMap		<GLBufferBlockType>					TABLE_TYPE_CONSTANT;
	private static final Int2ReferenceMap		<GLBufferBlockType>					TABLE_BINDING_CONSTANT;
	private static final Int2ReferenceMap		<GLBufferBlockType>					TABLE_MAX_BINDINGS_CONSTANT;
	private static final Int2ReferenceMap		<GLBufferBlockType>					TABLE_OFFSET_ALIGN_CONSTANT;

	static {
		TABLE_BUFFER_TYPE			= new Reference2ReferenceOpenHashMap<>();
		TABLE_TYPE_CONSTANT			= new Int2ReferenceOpenHashMap		<>();
		TABLE_BINDING_CONSTANT		= new Int2ReferenceOpenHashMap		<>();
		TABLE_MAX_BINDINGS_CONSTANT	= new Int2ReferenceOpenHashMap		<>();
		TABLE_OFFSET_ALIGN_CONSTANT	= new Int2ReferenceOpenHashMap		<>();

		for (var bufferBlockType : GLBufferBlockType.values()) {
			TABLE_BUFFER_TYPE			.put(bufferBlockType.bufferType,				bufferBlockType);
			TABLE_TYPE_CONSTANT			.put(bufferBlockType.getTypeConstant		(),	bufferBlockType);
			TABLE_BINDING_CONSTANT		.put(bufferBlockType.getBindingConstant		(),	bufferBlockType);
			TABLE_MAX_BINDINGS_CONSTANT	.put(bufferBlockType.getMaxBindingsConstant	(),	bufferBlockType);
			TABLE_OFFSET_ALIGN_CONSTANT	.put(bufferBlockType.getOffsetConstant		(),	bufferBlockType);
		}
	}

	private final GLBufferType				bufferType;
	private final GLGlobalIndexedParameter	offsetParameter;
	private final GLGlobalIndexedParameter	sizeParameter;
	private final GLGlobalParameter			maxBindingsParameter;
	private final GLGlobalParameter			offsetAlignParameter;

	GLBufferBlockType(
			GLBufferType				bufferType,
			GLGlobalIndexedParameter	offsetParameter,
			GLGlobalIndexedParameter	sizeParameter,
			GLGlobalParameter			maxBindingsParameter,
			GLGlobalParameter			offsetAlignParameter
	) {
		this.bufferType				= bufferType;
		this.offsetParameter		= offsetParameter;
		this.sizeParameter			= sizeParameter;
		this.maxBindingsParameter	= maxBindingsParameter;
		this.offsetAlignParameter	= offsetAlignParameter;
	}

	public IGLParameter getBindingParameter() {
		return bufferType.getParameter();
	}

	public GLGlobalIndexedParameter getOffsetParameter() {
		return offsetParameter;
	}

	public GLGlobalIndexedParameter getSizeParameter() {
		return sizeParameter;
	}

	public GLGlobalParameter getMaxBindingsParameter() {
		return maxBindingsParameter;
	}

	public GLGlobalParameter getOffsetAlignParameter() {
		return offsetAlignParameter;
	}

	public GLBufferType getBufferType() {
		return bufferType;
	}

	public int getTypeConstant() {
		return bufferType.getConstant();
	}

	public int getBindingConstant() {
		return bufferType.getBindingConstant();
	}

	public int getOffsetConstant() {
		return offsetParameter.getConstant();
	}

	public int getMaxBindingsConstant() {
		return maxBindingsParameter.getConstant();
	}

	public static GLBufferBlockType fromBufferType(GLBufferType bufferType) {
		return TABLE_BUFFER_TYPE.getOrDefault(bufferType, INVALID);
	}

	public static GLBufferBlockType fromTypeConstant(int constant) {
		return TABLE_TYPE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLBufferBlockType fromBindingConstant(int constant) {
		return TABLE_BINDING_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLBufferBlockType fromMaxBindingsConstant(int constant) {
		return TABLE_MAX_BINDINGS_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLBufferBlockType fromOffsetAlignConstant(int constant) {
		return TABLE_OFFSET_ALIGN_CONSTANT.getOrDefault(constant, INVALID);
	}
}
