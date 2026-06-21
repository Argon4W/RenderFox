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

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public enum GLFramebufferAttachmentParameter {

	INVALID										(-1),
	FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE			(GL30.GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE),
	FRAMEBUFFER_ATTACHMENT_OBJECT_NAME			(GL30.GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME),
	FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL		(GL30.GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL),
	FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE(GL30.GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE),
	FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER		(GL30.GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER),
	FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING		(GL30.GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING),
	FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE		(GL30.GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE),
	FRAMEBUFFER_ATTACHMENT_RED_SIZE				(GL30.GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE),
	FRAMEBUFFER_ATTACHMENT_GREEN_SIZE			(GL30.GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE),
	FRAMEBUFFER_ATTACHMENT_BLUE_SIZE			(GL30.GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE),
	FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE			(GL30.GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE),
	FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE			(GL30.GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE),
	FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE			(GL30.GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE),
	FRAMEBUFFER_ATTACHMENT_LAYERED				(GL32.GL_FRAMEBUFFER_ATTACHMENT_LAYERED);

	private static final Int2ReferenceMap<GLFramebufferAttachmentParameter> TABLE;

	static {
		TABLE = new Int2ReferenceOpenHashMap<>();

		for (var framebufferAttachmentParameter : GLFramebufferAttachmentParameter.values()) {
			TABLE.put(framebufferAttachmentParameter.constant, framebufferAttachmentParameter);
		}
	}

	private final int constant;

	GLFramebufferAttachmentParameter(int constant) {
		this.constant = constant;
	}

	public int getConstant() {
		return constant;
	}

	public static GLFramebufferAttachmentParameter fromConstant(int constant) {
		return TABLE.getOrDefault(constant, INVALID);
	}
}
