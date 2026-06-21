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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public enum GLFramebufferAttachment {
	INVALID				(-1,								-1,	0,	0),
	NONE				(GL11.GL_NONE,						-1,	-1,	GLFramebufferAttachmentFeatures.FRAMEBUFFER	|	GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE),
	FRONT_LEFT			(GL11.GL_FRONT_LEFT,				-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE),
	FRONT_RIGHT			(GL11.GL_FRONT_RIGHT,				-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE),
	BACK_LEFT			(GL11.GL_BACK_LEFT,					-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE),
	BACK_RIGHT			(GL11.GL_BACK_RIGHT,				-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE),
	FRONT				(GL11.GL_FRONT,						-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE | GLFramebufferAttachmentFeatures.MULTIPLE),
	BACK				(GL11.GL_BACK,						-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE | GLFramebufferAttachmentFeatures.MULTIPLE),
	LEFT				(GL11.GL_LEFT,						-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE | GLFramebufferAttachmentFeatures.MULTIPLE),
	RIGHT				(GL11.GL_RIGHT,						-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE | GLFramebufferAttachmentFeatures.MULTIPLE),
	FRONT_AND_BACK		(GL11.GL_FRONT_AND_BACK,			-1, -1,													GLFramebufferAttachmentFeatures.DEFAULT	| GLFramebufferAttachmentFeatures.DRAWABLE | GLFramebufferAttachmentFeatures.MULTIPLE),
	DEPTH				(GL30.GL_DEPTH_ATTACHMENT,			0,	-1,	GLFramebufferAttachmentFeatures.FRAMEBUFFER	|	GLFramebufferAttachmentFeatures.DEFAULT),
	STENCIL				(GL30.GL_STENCIL_ATTACHMENT,		1,	-1,	GLFramebufferAttachmentFeatures.FRAMEBUFFER	|	GLFramebufferAttachmentFeatures.DEFAULT),
	DEPTH_STENCIL		(GL30.GL_DEPTH_STENCIL_ATTACHMENT,	2,	-1,	GLFramebufferAttachmentFeatures.FRAMEBUFFER	|	GLFramebufferAttachmentFeatures.DEFAULT),
	COLOR_ATTACHMENT_0	(GL30.GL_COLOR_ATTACHMENT0,			3,	0,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_1	(GL30.GL_COLOR_ATTACHMENT1,			4,	1,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_2	(GL30.GL_COLOR_ATTACHMENT2,			5,	2,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_3	(GL30.GL_COLOR_ATTACHMENT3,			6,	3,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_4	(GL30.GL_COLOR_ATTACHMENT4,			7,	4,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_5	(GL30.GL_COLOR_ATTACHMENT5,			8,	5,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_6	(GL30.GL_COLOR_ATTACHMENT6,			9,	6,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_7	(GL30.GL_COLOR_ATTACHMENT7,			10,	7,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_8	(GL30.GL_COLOR_ATTACHMENT8,			11,	8,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_9	(GL30.GL_COLOR_ATTACHMENT9,			12,	9,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_10	(GL30.GL_COLOR_ATTACHMENT10,		13,	10,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_11	(GL30.GL_COLOR_ATTACHMENT11,		14,	11,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_12	(GL30.GL_COLOR_ATTACHMENT12,		15,	12,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_13	(GL30.GL_COLOR_ATTACHMENT13,		16,	13,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_14	(GL30.GL_COLOR_ATTACHMENT14,		17,	14,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_15	(GL30.GL_COLOR_ATTACHMENT15,		18,	15,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_16	(GL30.GL_COLOR_ATTACHMENT16,		19,	16,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_17	(GL30.GL_COLOR_ATTACHMENT17,		20,	17,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_18	(GL30.GL_COLOR_ATTACHMENT18,		21,	18,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_19	(GL30.GL_COLOR_ATTACHMENT19,		22,	19,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_20	(GL30.GL_COLOR_ATTACHMENT20,		23,	20,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_21	(GL30.GL_COLOR_ATTACHMENT21,		24,	21,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_22	(GL30.GL_COLOR_ATTACHMENT22,		25,	22,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_23	(GL30.GL_COLOR_ATTACHMENT23,		26,	23,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_24	(GL30.GL_COLOR_ATTACHMENT24,		27,	24,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_25	(GL30.GL_COLOR_ATTACHMENT25,		28,	25,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_26	(GL30.GL_COLOR_ATTACHMENT26,		29,	26,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_27	(GL30.GL_COLOR_ATTACHMENT27,		30,	27,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_28	(GL30.GL_COLOR_ATTACHMENT28,		31,	28,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_29	(GL30.GL_COLOR_ATTACHMENT29,		32,	29,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_30	(GL30.GL_COLOR_ATTACHMENT30,		33,	30,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE),
	COLOR_ATTACHMENT_31	(GL30.GL_COLOR_ATTACHMENT31,		34,	31,	GLFramebufferAttachmentFeatures.FRAMEBUFFER												| GLFramebufferAttachmentFeatures.DRAWABLE);

	private static final Int2ReferenceMap<GLFramebufferAttachment> TABLE_CONSTANT;
	private static final Int2ReferenceMap<GLFramebufferAttachment> TABLE_INDEX;

	static {
		TABLE_CONSTANT	= new Int2ReferenceOpenHashMap<>();
		TABLE_INDEX		= new Int2ReferenceOpenHashMap<>();

		for (var framebufferAttachment : GLFramebufferAttachment.values()) {
			TABLE_CONSTANT	.put(framebufferAttachment.constant,	framebufferAttachment);
			TABLE_INDEX		.put(framebufferAttachment.attachIndex,	framebufferAttachment);
		}
	}

	private final int constant;
	private final int storeIndex;
	private final int attachIndex;
	private final int featureFlags;

	GLFramebufferAttachment(
			int constant,
			int storeIndex,
			int attachIndex,
			int featureFlags
	) {
		this.constant		= constant;
		this.storeIndex		= storeIndex;
		this.attachIndex	= attachIndex;
		this.featureFlags	= featureFlags;
	}

	public int getConstant() {
		return constant;
	}

	public int getStoreIndex() {
		return storeIndex;
	}

	public int getAttachIndex() {
		return attachIndex;
	}

	public boolean isDefaultDrawable() {
		return isDrawable() && isDefault();
	}

	public boolean isFramebufferDrawable() {
		return isDrawable() && isFramebuffer();
	}

	public boolean isDefault() {
		return (featureFlags & GLFramebufferAttachmentFeatures.DEFAULT) != 0;
	}

	public boolean isFramebuffer() {
		return (featureFlags & GLFramebufferAttachmentFeatures.FRAMEBUFFER) != 0;
	}

	public boolean isDrawable() {
		return (featureFlags & GLFramebufferAttachmentFeatures.DRAWABLE) != 0;
	}

	public boolean isMultiple() {
		return (featureFlags & GLFramebufferAttachmentFeatures.MULTIPLE) != 0;
	}

	public boolean hasAttachIndex() {
		return attachIndex >= 0;
	}

	public static GLFramebufferAttachment fromConstant(int constant) {
		return TABLE_CONSTANT.getOrDefault(constant, INVALID);
	}

	public static GLFramebufferAttachment color(int index) {
		return index < 0 ? INVALID : TABLE_INDEX.getOrDefault(index, INVALID);
	}
}
