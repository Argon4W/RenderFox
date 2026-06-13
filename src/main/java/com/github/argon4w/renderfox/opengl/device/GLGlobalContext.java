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

package com.github.argon4w.renderfox.opengl.device;

import com.github.argon4w.renderfox.opengl.function.*;
import com.github.argon4w.renderfox.opengl.function.helper.GLGlobalFunctionsHelperCached;
import com.github.argon4w.renderfox.opengl.function.helper.GLGlobalFunctionsHelperQuery;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;

public class GLGlobalContext {

	private final OpenGLDevice				device;
	private final IGLGlobalFunctions		globalFunctions;
	private final IGLGlobalFunctionsHelper	globalHelper;

	public GLGlobalContext(OpenGLDevice glContext) {
		this.device				= glContext;
		this.globalFunctions	= createGlobalFunctions	();
		this.globalHelper		= createGlobalHelper	();
	}

	public void initialize() {
		globalFunctions	.initialize(device);
		globalHelper	.initialize(device);
	}

	private IGLGlobalFunctions createGlobalFunctions() {
		var globalFunctions	= GLGLobalFunctionsDirect.of();

		globalFunctions = device.getCreateInfo().useCacheStates	() ? new GLGLobalFunctionsCached	(globalFunctions) : globalFunctions;
		globalFunctions = device.getCreateInfo().useErrorCheck	() ? new GLGLobalFunctionsErrorCheck(globalFunctions) : globalFunctions;
		globalFunctions = device.getCreateInfo().useValidation	() ? new GLGLobalFunctionsValidation(globalFunctions) : globalFunctions;

		return globalFunctions;
	}

	public IGLGlobalFunctionsHelper createGlobalHelper() {
		return device.getCreateInfo().useCacheParameters() ? new GLGlobalFunctionsHelperCached(globalFunctions) : new GLGlobalFunctionsHelperQuery(globalFunctions);
	}

	public IGLGlobalFunctions getGlobalFunctions() {
		return globalFunctions;
	}

	public IGLGlobalFunctionsHelper getGlobalHelper() {
		return globalHelper;
	}
}
