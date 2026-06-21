package com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag;

import com.github.argon4w.renderfox.util.IBitMask;
import org.lwjgl.opengl.GL11;

public enum GLFramebufferWriteBufferBit implements IBitMask {

	INVALID				(-1),
	COLOR_BUFFER_BIT	(GL11.GL_COLOR_BUFFER_BIT),
	DEPTH_BUFFER_BIT	(GL11.GL_DEPTH_BUFFER_BIT),
	STENCIL_BUFFER_BIT	(GL11.GL_STENCIL_BUFFER_BIT);

	public static final int ALL_BITS =	GL11.GL_COLOR_BUFFER_BIT
			|							GL11.GL_DEPTH_BUFFER_BIT
			|							GL11.GL_STENCIL_BUFFER_BIT;

	private final int bitMask;

	GLFramebufferWriteBufferBit(int bitMask) {
		this.bitMask = bitMask;
	}

	@Override
	public int getBitMask() {
		return bitMask;
	}

	public boolean isIn(int mapAccess) {
		return (mapAccess & bitMask) != 0;
	}

	public static boolean hasInvalidBits(int flags) {
		return (flags & ~ALL_BITS) != 0;
	}

	public static GLFramebufferWriteBuffer toFlags(int flags) {
		return new GLFramebufferWriteBuffer(flags);
	}
}
