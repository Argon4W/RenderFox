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
