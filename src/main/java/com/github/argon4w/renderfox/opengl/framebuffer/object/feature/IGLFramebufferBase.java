package com.github.argon4w.renderfox.opengl.framebuffer.object.feature;

public interface IGLFramebufferBase {

	int		getFramebufferHandle();
	boolean isDeleted			();
	void	delete				();
}
