package com.github.argon4w.renderfox.opengl.framebuffer.function;

import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferClearBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferStatus;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferAttachmentParameter;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferParameter;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag.GLFramebufferWriteBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.object.raw.IGLRawFramebuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.AbstractGLFramebufferStore;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.IGLFramebufferBase;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLTextureFilter;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTextureBase;
import com.github.argon4w.renderfox.opengl.texture.object.raw.IGLRawTextureView;

public class GLFramebufferFunctionsHelper extends AbstractGLFramebufferStore implements IGLRawFramebuffer {

	private final	IGLFramebufferFunctions	framebufferFunctions;
	private			int						framebufferHandle;

	public GLFramebufferFunctionsHelper(IGLFramebufferFunctions framebufferFunctions) {
		this.framebufferFunctions	= framebufferFunctions;
		this.framebufferHandle		= -1;
	}

	public GLFramebufferFunctionsHelper setFramebuffer(int framebufferHandle) {
		this.framebufferHandle = framebufferHandle;
		return this;
	}

	@Override
	public void setParameterInt(GLFramebufferParameter parameter, int value) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLFramebufferParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		framebufferFunctions.framebufferParameteri(
				framebufferHandle,
				parameter.getConstant(),
				value
		);
	}

	@Override
	public int getParameterInt(GLFramebufferParameter parameter) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLFramebufferParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return framebufferFunctions.getFramebufferParameteri(framebufferHandle, parameter.getConstant());
	}

	@Override
	public int getAttachmentParameterInt(GLFramebufferAttachmentParameter parameter, GLFramebufferAttachment attachment) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLFramebufferAttachmentParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		return framebufferFunctions.getFramebufferAttachmentParameteri(
				framebufferHandle,
				attachment	.getConstant(),
				parameter	.getConstant()
		);
	}

	@Override
	public void detach(GLFramebufferAttachment attachment) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid attachment.");
		}

		framebufferFunctions.framebufferTexture(
				framebufferHandle,
				attachment.getConstant(),
				0,
				GLTextureType.TEXTURE_2D,
				0
		);
	}

	@Override
	public void attachTexture(
			GLFramebufferAttachment	attachment,
			IGLRawTextureBase		attachmentTexture,
			int						attachmentTextureMipLevel
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid attachment.");
		}

		framebufferFunctions.framebufferTexture(
				framebufferHandle,
				attachment			.getConstant		(),
				attachmentTexture	.getTextureHandle	(),
				attachmentTexture	.getTextureType		(),
				attachmentTexture	.getRealLevel		(attachmentTextureMipLevel)
		);
	}

	@Override
	public void attachTextureLayer(
			GLFramebufferAttachment	attachment,
			IGLRawTextureBase		attachmentTexture,
			int						attachmentTextureMipLevel,
			int						attachmentTextureLayer
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (attachmentTexture == null) {
			throw new IllegalArgumentException("Attachment texture cannot be null.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid attachment.");
		}

		framebufferFunctions.framebufferTextureLayer(
				framebufferHandle,
				attachment			.getConstant		(),
				attachmentTexture	.getTextureHandle	(),
				attachmentTexture	.getTextureType		(),
				attachmentTextureMipLevel,
				attachmentTextureLayer
		);
	}

	@Override
	public void setDrawAttachment(GLFramebufferAttachment attachment) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid attachment.");
		}

		framebufferFunctions.framebufferDrawBuffer(framebufferHandle, attachment.getConstant());
	}

	@Override
	public void setDrawAttachments(int attachmentCount, long attachmentDataAddress) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		framebufferFunctions.framebufferDrawBuffers(
				framebufferHandle,
				attachmentCount,
				attachmentDataAddress
		);
	}

	@Override
	public void setReadAttachment(GLFramebufferAttachment attachment) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid attachment.");
		}

		framebufferFunctions.framebufferReadBuffer(framebufferHandle, attachment.getConstant());
	}

	@Override
	public void invalidateAllData(int attachmentCount, long attachmentDataAddress) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		framebufferFunctions.invalidateFramebufferData(
				framebufferHandle,
				attachmentCount,
				attachmentDataAddress
		);
	}

	@Override
	public void invalidateRangeData(
			int		attachmentCount,
			long	attachmentDataAddress,
			int		invalidatePositionX,
			int		invalidatePositionY,
			int		invalidateWidth,
			int		invalidateHeight
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		framebufferFunctions.invalidateFramebufferSubData(
				framebufferHandle,
				attachmentCount,
				attachmentDataAddress,
				invalidatePositionX,
				invalidatePositionY,
				invalidateWidth,
				invalidateHeight
		);
	}

	@Override
	public void clearAllDataInt(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (clearBuffer == null) {
			throw new IllegalArgumentException("ClearBuffer texture cannot be null.");
		}

		if (clearBuffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		framebufferFunctions.clearFramebufferiv(
				framebufferHandle,
				clearBuffer.getConstant(),
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearAllDataUnsignedInt(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (clearBuffer == null) {
			throw new IllegalArgumentException("ClearBuffer texture cannot be null.");
		}

		if (clearBuffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		framebufferFunctions.clearFramebufferuiv(
				framebufferHandle,
				clearBuffer.getConstant(),
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearAllDataFloat(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (clearBuffer == null) {
			throw new IllegalArgumentException("ClearBuffer texture cannot be null.");
		}

		if (clearBuffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		framebufferFunctions.clearFramebufferfv(
				framebufferHandle,
				clearBuffer.getConstant(),
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearAllDataDepthStencil(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			float						clearDepth,
			int							clearStencil
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (clearBuffer == null) {
			throw new IllegalArgumentException("ClearBuffer texture cannot be null.");
		}

		if (clearBuffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		framebufferFunctions.clearFramebufferfi(
				framebufferHandle,
				clearBuffer.getConstant(),
				clearDrawBuffer,
				clearDepth,
				clearStencil
		);
	}

	@Override
	public GLFramebufferStatus checkStatus(GLFramebufferType framebufferType) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (framebufferType == null) {
			throw new IllegalArgumentException("framebufferType cannot be null.");
		}

		if (framebufferType == GLFramebufferType.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferType.");
		}

		return GLFramebufferStatus.fromConstant(framebufferFunctions.checkFramebufferStatus(framebufferHandle, framebufferType.getConstant()));
	}

	@Override
	public void bind(GLFramebufferType framebufferType) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (framebufferType == null) {
			throw new IllegalArgumentException("FramebufferType cannot be null.");
		}

		if (framebufferType == GLFramebufferType.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferType.");
		}

		framebufferFunctions.bindFramebuffer(framebufferHandle, framebufferType.getConstant());
	}

	@Override
	public void blitDataTo(
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
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (framebufferWrite == null) {
			throw new IllegalArgumentException("FramebufferWrite cannot be null.");
		}

		if (blitWriteBuffer == null) {
			throw new IllegalArgumentException("BlitWriteBuffer cannot be null.");
		}

		if (blitFilterMode == null) {
			throw new IllegalArgumentException("BlitFilterMode cannot be null.");
		}

		if (blitFilterMode == GLFilterMode.INVALID) {
			throw new IllegalArgumentException("Invalid blitFilterMode.");
		}

		framebufferFunctions.blitFramebuffer(
				framebufferHandle,
				framebufferWrite.getFramebufferHandle(),
				blitReadPositionXStart,
				blitReadPositionYStart,
				blitReadPositionXEnd,
				blitReadPositionYEnd,
				blitWritePositionXStart,
				blitWritePositionYStart,
				blitWritePositionXEnd,
				blitWritePositionYEnd,
				blitWriteBuffer						.getRawFlags(),
				GLTextureFilter.from(blitFilterMode).getConstant()
		);
	}

	@Override
	public void blitDataFrom(
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
	) {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		if (framebufferRead == null) {
			throw new IllegalArgumentException("FramebufferRead cannot be null.");
		}

		if (blitWriteBuffer == null) {
			throw new IllegalArgumentException("BlitWriteBuffer cannot be null.");
		}

		if (blitFilterMode == null) {
			throw new IllegalArgumentException("BlitFilterMode cannot be null.");
		}

		if (blitFilterMode == GLFilterMode.INVALID) {
			throw new IllegalArgumentException("Invalid blitFilterMode.");
		}

		framebufferFunctions.blitFramebuffer(
				framebufferRead.getFramebufferHandle(),
				framebufferHandle,
				blitReadPositionXStart,
				blitReadPositionYStart,
				blitReadPositionXEnd,
				blitReadPositionYEnd,
				blitWritePositionXStart,
				blitWritePositionYStart,
				blitWritePositionXEnd,
				blitWritePositionYEnd,
				blitWriteBuffer						.getRawFlags(),
				GLTextureFilter.from(blitFilterMode).getConstant()
		);
	}

	@Override
	public boolean isFramebuffer() {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		return framebufferFunctions.isFramebuffer(framebufferHandle);
	}

	@Override
	public void delete() {
		if (framebufferHandle == -1) {
			throw new IllegalStateException("FramebufferHandle has not yet been set.");
		}

		framebufferFunctions.deleteFramebuffer(framebufferHandle);
	}

	@Override
	public int getFramebufferHandle() {
		return framebufferHandle;
	}

	@Override
	public boolean isDeleted() {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}
}
