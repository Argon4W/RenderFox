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

package com.github.argon4w.renderfox.opengl.framebuffer.constant;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public enum GLFramebufferStatus {

	INVALID										(-1),
	FRAMEBUFFER_COMPLETE						(GL30.GL_FRAMEBUFFER_COMPLETE),
	FRAMEBUFFER_UNDEFINED						(GL30.GL_FRAMEBUFFER_UNDEFINED),
	FRAMEBUFFER_INCOMPLETE_ATTACHMENT			(GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT),
	FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT	(GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT),
	FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER			(GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER),
	FRAMEBUFFER_INCOMPLETE_READ_BUFFER			(GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER),
	FRAMEBUFFER_UNSUPPORTED						(GL30.GL_FRAMEBUFFER_UNSUPPORTED),
	FRAMEBUFFER_INCOMPLETE_MULTISAMPLE			(GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE),
	FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS		(GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS);

	private static final Int2ReferenceMap<GLFramebufferStatus> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var framebufferStatus : GLFramebufferStatus.values()) {
			TABLE.put(framebufferStatus.constant, framebufferStatus);
		}
	}

	private final int constant;

	GLFramebufferStatus(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLFramebufferStatus fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
