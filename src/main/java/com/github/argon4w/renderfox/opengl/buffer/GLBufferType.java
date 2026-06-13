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
import org.lwjgl.opengl.*;

public enum GLBufferType {

	INVALID					(-1,								GLGlobalParameter		.INVALID,							0,											"Invalid Buffer Type"),
	VERTEX_BUFFER			(GL15.GL_ARRAY_BUFFER,				GLGlobalParameter		.VERTEX_BUFFER_BINDING,				GL42.GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT,	"Vertex Buffer"),
	ELEMENT_BUFFER			(GL15.GL_ELEMENT_ARRAY_BUFFER,		GLGlobalParameter		.ELEMENT_BUFFER_BINDING,			GL42.GL_ELEMENT_ARRAY_BARRIER_BIT,			"Index Buffer"),
	UNIFORM_BUFFER			(GL31.GL_UNIFORM_BUFFER,			GLGlobalIndexedParameter.UNIFORM_BUFFER_BINDING,			GL42.GL_UNIFORM_BUFFER_BINDING,				"Uniform Buffer"),
	SHADER_STORAGE_BUFFER	(GL43.GL_SHADER_STORAGE_BUFFER,		GLGlobalIndexedParameter.SHADER_STORAGE_BUFFER_BINDING,		GL43.GL_SHADER_STORAGE_BARRIER_BIT,			"Shader Storage Buffer"),
	ATOMIC_COUNTER_BUFFER	(GL42.GL_ATOMIC_COUNTER_BUFFER,		GLGlobalIndexedParameter.ATOMIC_COUNTER_BUFFER_BINDING,		GL42.GL_ATOMIC_COUNTER_BARRIER_BIT,			"Atomic Counter Buffer"),
	DISPATCH_INDIRECT_BUFFER(GL43.GL_DISPATCH_INDIRECT_BUFFER,	GLGlobalParameter		.DISPATCH_INDIRECT_BUFFER_BINDING,	GL43.GL_COMMAND_BARRIER_BIT,				"Dispatch Indirect Buffer"),
	PARAMETER_BUFFER		(GL46.GL_PARAMETER_BUFFER,			GLGlobalParameter		.PARAMETER_BUFFER_BINDING,			GL43.GL_COMMAND_BARRIER_BIT,				"Draw Indirect Buffer"),
	DRAW_INDIRECT_BUFFER	(GL40.GL_DRAW_INDIRECT_BUFFER,		GLGlobalParameter		.DRAW_INDIRECT_BUFFER_BINDING,		GL43.GL_COMMAND_BARRIER_BIT,				"Draw Indirect Buffer"),
	COPY_READ_BUFFER		(GL42.GL_COPY_READ_BUFFER,			GLGlobalParameter		.COPY_READ_BUFFER_BINDING,			GL42.GL_BUFFER_UPDATE_BARRIER_BIT,			"Copy Read Buffer"),
	COPY_WRITE_BUFFER		(GL42.GL_COPY_WRITE_BUFFER,			GLGlobalParameter		.COPY_WRITE_BUFFER_BINDING,			GL42.GL_BUFFER_UPDATE_BARRIER_BIT,			"Copy Write Buffer"),
	PIXEL_PACK_BUFFER		(GL21.GL_PIXEL_PACK_BUFFER,			GLGlobalParameter		.PIXEL_PACK_BUFFER_BINDING,			GL42.GL_PIXEL_BUFFER_BARRIER_BIT,			"Pixel Pack Buffer"),
	PIXEL_UNPACK_BUFFER		(GL21.GL_PIXEL_UNPACK_BUFFER,		GLGlobalParameter		.PIXEL_UNPACK_BUFFER_BINDING,		GL42.GL_PIXEL_BUFFER_BARRIER_BIT,			"Pixel Unpack Buffer");

	private static final Int2ReferenceMap<GLBufferType> TABLE_TYPE_CONSTANT;
	private static final Int2ReferenceMap<GLBufferType> TABLE_BINDING_CONSTANT;

	static {
		TABLE_TYPE_CONSTANT		= new Int2ReferenceOpenHashMap	<>();
		TABLE_BINDING_CONSTANT	= new Int2ReferenceOpenHashMap	<>();

		for (var bufferType : GLBufferType.values()) {
			TABLE_TYPE_CONSTANT		.put(bufferType.getConstant			(), bufferType);
			TABLE_BINDING_CONSTANT	.put(bufferType.getBindingConstant	(), bufferType);
		}
	}

	private final int			constant;
	private final IGLParameter	parameter;
	private final int			barrierBitMask;
	private final String		name;

	GLBufferType(
			int				constant,
			IGLParameter	parameter,
			int				barrierBitMask,
			String			name
	) {
		this.constant		= constant;
		this.parameter		= parameter;
		this.barrierBitMask	= barrierBitMask;
		this.name			= name;
	}

	public int getConstant() {
		return constant;
	}

	public IGLParameter getParameter() {
		return parameter;
	}

	public int getBindingConstant() {
		return parameter.getConstant();
	}

	public int getBarrierBitMask() {
		return barrierBitMask;
	}

	@Override
	public String toString() {
		return name;
	}

	public static GLBufferType fromTypeConstant(int constant) {
		return TABLE_TYPE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static  GLBufferType fromBindingConstant(int constant) {
		return TABLE_BINDING_CONSTANT.getOrDefault(constant, INVALID);
	}
}
