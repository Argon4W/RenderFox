package com.github.argon4w.renderfox.opengl.framebuffer.object.feature;

import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag.GLFramebufferWriteBuffer;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;

public interface IGLFramebufferTransfer {

	void blitDataTo(
			IGLFramebufferBase			framebufferWrite,
			int							blitReadPositionXStart,
			int							blitReadPositionYStart,
			int							blitReadPositionXEnd,
			int							blitReadPositionYEnd,
			int							blitWritePositionXStart,
			int							blitWritePositionYStart,
			int							blitWritePositionXEnd,
			int							blitWritePositionYEnd,
			GLFramebufferWriteBuffer	blitWriteBuffer,
			GLFilterMode				blitFilterMode
	);

	void blitDataFrom(
			IGLFramebufferBase			framebufferRead,
			int							blitReadPositionXStart,
			int							blitReadPositionYStart,
			int							blitReadPositionXEnd,
			int							blitReadPositionYEnd,
			int							blitWritePositionXStart,
			int							blitWritePositionYStart,
			int							blitWritePositionXEnd,
			int							blitWritePositionYEnd,
			GLFramebufferWriteBuffer	blitWriteBuffer,
			GLFilterMode				blitFilterMode
	);
}
