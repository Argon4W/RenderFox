package com.github.argon4w.renderfox.opengl.framebuffer.object.feature;

import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferClearBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferStatus;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTextureBase;

public interface IGLFramebufferOperation extends IGLFramebufferBinding, IGLFramebufferTransfer {

	void detach(GLFramebufferAttachment attachment);

	void attachTexture(
			GLFramebufferAttachment		attachment,
			IGLRawTextureBase			attachmentTexture,
			int							attachmentTextureMipLevel
	);

	void attachTextureLayer(
			GLFramebufferAttachment		attachment,
			IGLRawTextureBase			attachmentTexture,
			int							attachmentTextureMipLevel,
			int							attachmentTextureLayer
	);

	void setDrawAttachment(GLFramebufferAttachment attachment);

	void setDrawAttachments(int attachmentCount, long attachmentDataAddress);

	void setReadAttachment(GLFramebufferAttachment attachment);

	void invalidateAllData(int attachmentCount, long attachmentDataAddress);

	void invalidateRangeData(
			int							attachmentCount,
			long						attachmentDataAddress,
			int							invalidatePositionX,
			int							invalidatePositionY,
			int							invalidateWidth,
			int							invalidateHeight
	);

	void clearAllDataInt(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	);

	void clearAllDataUnsignedInt(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	);

	void clearAllDataFloat(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	);

	void clearAllDataDepthStencil(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			float						clearDepth,
			int							clearStencil
	);

	GLFramebufferStatus checkStatus(GLFramebufferType framebufferType);

	boolean isFramebuffer();
}
