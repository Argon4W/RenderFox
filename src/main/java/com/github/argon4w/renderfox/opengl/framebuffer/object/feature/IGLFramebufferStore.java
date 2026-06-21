package com.github.argon4w.renderfox.opengl.framebuffer.object.feature;

import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponentType;

public interface IGLFramebufferStore {

	int						getDefaultWidth					();
	int						getDefaultHeight				();
	int						getDefaultLayers				();
	int						getDefaultSamples				();
	boolean					isDefaultFixedSampleLocation	();
	boolean					isMultisampled					();
	int						getMultisampleBits				();
	int						getAttachmentBinding			(GLFramebufferAttachment attachment);
	int						getAttachmentMipLevel			(GLFramebufferAttachment attachment);
	int						getAttachmentLayer				(GLFramebufferAttachment attachment);
	int						getAttachmentRedSize			(GLFramebufferAttachment attachment);
	int						getAttachmentGreenSize			(GLFramebufferAttachment attachment);
	int						getAttachmentBlueSize			(GLFramebufferAttachment attachment);
	int						getAttachmentAlphaSize			(GLFramebufferAttachment attachment);
	int						getAttachmentDepthSize			(GLFramebufferAttachment attachment);
	int						getAttachmentStencilSize		(GLFramebufferAttachment attachment);
	boolean					isAttachmentLayered				(GLFramebufferAttachment attachment);
	GLTextureComponentType	getAttachmentComponentType		(GLFramebufferAttachment attachment);
}
