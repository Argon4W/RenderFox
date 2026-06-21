package com.github.argon4w.renderfox.opengl.framebuffer.object.feature;

import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferAttachmentParameter;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferParameter;

public interface IGLFramebufferParameter {

	void	setParameterInt				(GLFramebufferParameter				parameter, int value);
	int		getParameterInt				(GLFramebufferParameter				parameter);
	int		getAttachmentParameterInt	(GLFramebufferAttachmentParameter	parameter, GLFramebufferAttachment attachment);
}
