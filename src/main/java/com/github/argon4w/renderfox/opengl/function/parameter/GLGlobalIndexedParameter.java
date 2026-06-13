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

package com.github.argon4w.renderfox.opengl.function.parameter;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

public enum GLGlobalIndexedParameter implements IGLParameter {

	INVALID									(-1,									IGLIndexSource.invalid	()),
	UNIFORM_BUFFER_START					(GL31.GL_UNIFORM_BUFFER_START,			IGLIndexSource.query	(GLGlobalParameter.MAX_UNIFORM_BUFFER_BINDINGS)),
	SHADER_STORAGE_BUFFER_START				(GL43.GL_SHADER_STORAGE_BUFFER_START,	IGLIndexSource.query	(GLGlobalParameter.MAX_SHADER_STORAGE_BUFFER_BINDINGS)),
	ATOMIC_COUNTER_BUFFER_START				(GL43.GL_ATOMIC_COUNTER_BUFFER_START,	IGLIndexSource.query	(GLGlobalParameter.MAX_ATOMIC_COUNTER_BUFFER_BINDINGS)),
	UNIFORM_BUFFER_SIZE						(GL31.GL_UNIFORM_BUFFER_SIZE,			IGLIndexSource.query	(GLGlobalParameter.MAX_UNIFORM_BUFFER_BINDINGS)),
	SHADER_STORAGE_BUFFER_SIZE				(GL43.GL_SHADER_STORAGE_BUFFER_SIZE,	IGLIndexSource.query	(GLGlobalParameter.MAX_SHADER_STORAGE_BUFFER_BINDINGS)),
	ATOMIC_COUNTER_BUFFER_SIZE				(GL43.GL_ATOMIC_COUNTER_BUFFER_SIZE,	IGLIndexSource.query	(GLGlobalParameter.MAX_ATOMIC_COUNTER_BUFFER_BINDINGS)),
	UNIFORM_BUFFER_BINDING					(GL31.GL_UNIFORM_BUFFER_BINDING,		IGLIndexSource.query	(GLGlobalParameter.MAX_UNIFORM_BUFFER_BINDINGS)),
	SHADER_STORAGE_BUFFER_BINDING			(GL43.GL_SHADER_STORAGE_BUFFER_BINDING,	IGLIndexSource.query	(GLGlobalParameter.MAX_SHADER_STORAGE_BUFFER_BINDINGS)),
	ATOMIC_COUNTER_BUFFER_BINDING			(GL42.GL_ATOMIC_COUNTER_BUFFER_BINDING,	IGLIndexSource.query	(GLGlobalParameter.MAX_ATOMIC_COUNTER_BUFFER_BINDINGS));

	private static final Int2ReferenceMap <GLGlobalIndexedParameter> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var globalConstant : GLGlobalIndexedParameter.values()) {
			TABLE.put(globalConstant.constant, globalConstant);
		}
	}

	private final int				constant;
	private final IGLIndexSource	indexSource;

	GLGlobalIndexedParameter(int constant, IGLIndexSource indexSource) {
		this.constant		= constant;
		this.indexSource	= indexSource;
	}

	public IGLIndexSource getIndexSource() {
		return indexSource;
	}

	@Override
	public int getConstant() {
		return constant;
	}

	public static GLGlobalIndexedParameter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
