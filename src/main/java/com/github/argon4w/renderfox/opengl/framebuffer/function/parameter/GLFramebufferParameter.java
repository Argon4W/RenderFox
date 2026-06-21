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

package com.github.argon4w.renderfox.opengl.framebuffer.function.parameter;

import com.github.argon4w.renderfox.opengl.constant.GLBooleanConstants;
import com.github.argon4w.renderfox.opengl.constant.GLNoneConstants;
import com.github.argon4w.renderfox.opengl.constant.GLQueryConstantsInt;
import com.github.argon4w.renderfox.opengl.constant.IGLDefinedConstants;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL43;

public enum GLFramebufferParameter {

	INVALID										(-1,													false,	GLNoneConstants.of		()),
	FRAMEBUFFER_DEFAULT_WIDTH					(GL43.GL_FRAMEBUFFER_DEFAULT_WIDTH,						false,	new GLQueryConstantsInt	(GLGlobalParameter.MAX_FRAMEBUFFER_WIDTH, 0)),
	FRAMEBUFFER_DEFAULT_HEIGHT					(GL43.GL_FRAMEBUFFER_DEFAULT_HEIGHT,					false,	new GLQueryConstantsInt	(GLGlobalParameter.MAX_FRAMEBUFFER_HEIGHT, 0)),
	FRAMEBUFFER_DEFAULT_LAYERS					(GL43.GL_FRAMEBUFFER_DEFAULT_LAYERS,					false,	new GLQueryConstantsInt	(GLGlobalParameter.MAX_FRAMEBUFFER_LAYERS, 0)),
	FRAMEBUFFER_DEFAULT_SAMPLES					(GL43.GL_FRAMEBUFFER_DEFAULT_SAMPLES,					false,	new GLQueryConstantsInt	(GLGlobalParameter.MAX_FRAMEBUFFER_SAMPLES, 0)),
	FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS	(GL43.GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS,	false,	new GLBooleanConstants<>()),
	SAMPLE_BUFFERS								(GL13.GL_SAMPLE_BUFFERS,								true,	GLNoneConstants.of		()),
	SAMPLES										(GL13.GL_SAMPLES,										true,	GLNoneConstants.of		()),;

	private static final Int2ReferenceMap<GLFramebufferParameter> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var parameter : GLFramebufferParameter.values()) {
			TABLE.put(parameter.constant, parameter);
		}
	}

	private final int											constant;
	private final boolean										readOnly;
	private final IGLDefinedConstants<IGLGlobalFunctionsHelper>	values;

	GLFramebufferParameter(
			int												constant,
			boolean											readOnly,
			IGLDefinedConstants<IGLGlobalFunctionsHelper>	values
	) {
		this.constant	= constant;
		this.readOnly	= readOnly;
		this.values		= values;
	}

	public int getConstant() {
		return constant;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public IGLDefinedConstants<IGLGlobalFunctionsHelper> getValues() {
		return values;
	}

	public static GLFramebufferParameter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
