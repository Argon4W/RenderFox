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

package com.github.argon4w.renderfox.opengl.texture.pixel;

import com.github.argon4w.renderfox.opengl.constant.*;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public enum GLPixelParameter {

	INVALID				(-1,							GLGlobalParameter.INVALID,				GLNoneConstants			.of	()),
	PACK_SWAP_BYTES		(GL11.GL_PACK_SWAP_BYTES,		GLGlobalParameter.PACK_SWAP_BYTES,		GLBooleanConstants		.of	()),
	PACK_LSB_FIRST		(GL11.GL_PACK_LSB_FIRST,		GLGlobalParameter.PACK_LSB_FIRST,		GLBooleanConstants		.of	()),
	PACK_ROW_LENGTH		(GL11.GL_PACK_ROW_LENGTH,		GLGlobalParameter.PACK_ROW_LENGTH,		GLNonNegativeConstants	.of	()),
	PACK_IMAGE_HEIGHT	(GL12.GL_PACK_IMAGE_HEIGHT,		GLGlobalParameter.PACK_IMAGE_HEIGHT,	GLNonNegativeConstants	.of	()),
	PACK_SKIP_ROWS		(GL11.GL_PACK_SKIP_ROWS,		GLGlobalParameter.PACK_SKIP_ROWS,		GLNonNegativeConstants	.of	()),
	PACK_SKIP_PIXELS	(GL11.GL_PACK_SKIP_PIXELS,		GLGlobalParameter.PACK_SKIP_PIXELS,		GLNonNegativeConstants	.of	()),
	PACK_SKIP_IMAGES	(GL12.GL_PACK_SKIP_IMAGES,		GLGlobalParameter.PACK_SKIP_IMAGES,		GLNonNegativeConstants	.of	()),
	PACK_ALIGNMENT		(GL11.GL_PACK_ALIGNMENT,		GLGlobalParameter.PACK_ALIGNMENT,		new GLRangeConstants<>		(1, 5)),
	UNPACK_SWAP_BYTES	(GL11.GL_UNPACK_SWAP_BYTES,		GLGlobalParameter.UNPACK_SWAP_BYTES,	GLBooleanConstants		.of	()),
	UNPACK_LSB_FIRST	(GL11.GL_UNPACK_LSB_FIRST,		GLGlobalParameter.UNPACK_LSB_FIRST,		GLBooleanConstants		.of	()),
	UNPACK_ROW_LENGTH	(GL11.GL_UNPACK_ROW_LENGTH,		GLGlobalParameter.UNPACK_ROW_LENGTH,	GLNonNegativeConstants	.of	()),
	UNPACK_IMAGE_HEIGHT	(GL12.GL_UNPACK_IMAGE_HEIGHT,	GLGlobalParameter.UNPACK_IMAGE_HEIGHT,	GLNonNegativeConstants	.of	()),
	UNPACK_SKIP_ROWS	(GL11.GL_UNPACK_SKIP_ROWS,		GLGlobalParameter.UNPACK_SKIP_ROWS,		GLNonNegativeConstants	.of	()),
	UNPACK_SKIP_PIXELS	(GL11.GL_UNPACK_SKIP_PIXELS,	GLGlobalParameter.UNPACK_SKIP_PIXELS,	GLNonNegativeConstants	.of	()),
	UNPACK_SKIP_IMAGES	(GL12.GL_UNPACK_SKIP_IMAGES,	GLGlobalParameter.UNPACK_SKIP_IMAGES,	GLNonNegativeConstants	.of	()),
	UNPACK_ALIGNMENT	(GL11.GL_UNPACK_ALIGNMENT,		GLGlobalParameter.UNPACK_ALIGNMENT,		new GLRangeConstants<>		(1, 5));

	private static final Int2ReferenceMap<GLPixelParameter> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var parameter : GLPixelParameter.values()) {
			TABLE.put(parameter.constant, parameter);
		}
	}

	private final int						constant;
	private final GLGlobalParameter			parameter;
	private final IGLDefinedConstants<Void>	allowedValues;

	GLPixelParameter(
			int							constant,
			GLGlobalParameter			parameter,
			IGLDefinedConstants<Void>	allowedValues
	) {
		this.constant		= constant;
		this.parameter		= parameter;
		this.allowedValues	= allowedValues;
	}

	public int getConstant() {
		return constant;
	}

	public GLGlobalParameter getParameter() {
		return parameter;
	}

	public IGLDefinedConstants<Void> getAllowedValues() {
		return allowedValues;
	}

	public static GLPixelParameter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, GLPixelParameter.INVALID);
	}
}
