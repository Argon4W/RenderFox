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

package com.github.argon4w.renderfox.opengl.texture.function.parameter;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.*;

public enum GLTextureLevelParameter {

	INVALID					(-1),
	TEXTURE_WIDTH			(GL11.GL_TEXTURE_WIDTH),
	TEXTURE_HEIGHT			(GL11.GL_TEXTURE_HEIGHT),
	TEXTURE_DEPTH			(GL12.GL_TEXTURE_DEPTH),
	TEXTURE_INTERNAL_FORMAT	(GL11.GL_TEXTURE_INTERNAL_FORMAT),
	TEXTURE_RED_TYPE		(GL30.GL_TEXTURE_RED_TYPE),
	TEXTURE_GREEN_TYPE		(GL30.GL_TEXTURE_GREEN_TYPE),
	TEXTURE_BLUE_TYPE		(GL30.GL_TEXTURE_BLUE_TYPE),
	TEXTURE_ALPHA_TYPE		(GL30.GL_TEXTURE_ALPHA_TYPE),
	TEXTURE_DEPTH_TYPE		(GL30.GL_TEXTURE_DEPTH_TYPE),
	TEXTURE_RED_SIZE		(GL11.GL_TEXTURE_RED_SIZE),
	TEXTURE_GREEN_SIZE		(GL11.GL_TEXTURE_GREEN_SIZE),
	TEXTURE_BLUE_SIZE		(GL11.GL_TEXTURE_BLUE_SIZE),
	TEXTURE_ALPHA_SIZE		(GL11.GL_TEXTURE_ALPHA_SIZE),
	TEXTURE_DEPTH_SIZE		(GL14.GL_TEXTURE_DEPTH_SIZE),
	TEXTURE_BUFFER_OFFSET	(GL43.GL_TEXTURE_BUFFER_OFFSET),
	TEXTURE_BUFFER_SIZE		(GL43.GL_TEXTURE_BUFFER_SIZE);

	private static final Int2ReferenceMap<GLTextureLevelParameter> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var parameter : GLTextureLevelParameter.values()) {
			TABLE.put(parameter.constant, parameter);
		}
	}

	private final int constant;

	GLTextureLevelParameter(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLTextureLevelParameter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
