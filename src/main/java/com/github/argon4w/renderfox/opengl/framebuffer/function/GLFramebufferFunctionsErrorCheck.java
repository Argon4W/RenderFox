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

package com.github.argon4w.renderfox.opengl.framebuffer.function;

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.error.GLErrorChecker;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;

public class GLFramebufferFunctionsErrorCheck implements IGLFramebufferFunctions {

	private final	IGLFramebufferFunctions	framebufferFunctions;
	private			GLErrorChecker			errorChecker;

	public GLFramebufferFunctionsErrorCheck(IGLFramebufferFunctions framebufferFunctions) {
		this.framebufferFunctions	= framebufferFunctions;
		this.errorChecker			= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		framebufferFunctions.initialize(device);

		errorChecker = device.getErrorChecker();
	}

	@Override
	public int createFramebuffer() {
		return errorChecker.runChecked("createFramebuffer", framebufferFunctions::createFramebuffer);
	}

	@Override
	public void framebufferParameteri(
			int framebufferHandle,
			int framebufferParameter,
			int framebufferParameterValue
	) {
		errorChecker.runChecked("framebufferParameteri", () -> framebufferFunctions.framebufferParameteri(
				framebufferHandle,
				framebufferParameter,
				framebufferParameterValue
		));
	}

	@Override
	public void framebufferTexture(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				textureHandle,
			GLTextureType	textureType,
			int				textureMipLevel
	) {
		errorChecker.runChecked("framebufferTexture", () -> framebufferFunctions.framebufferTexture(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureType,
				textureMipLevel
		));
	}

	@Override
	public void framebufferTextureLayer(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				textureHandle,
			GLTextureType	textureType,
			int				textureMipLevel,
			int				textureLayer
	) {
		errorChecker.runChecked("framebufferTextureLayer", () -> framebufferFunctions.framebufferTextureLayer(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureType,
				textureMipLevel,
				textureLayer
		));
	}

	@Override
	public void framebufferDrawBuffer(int framebufferHandle, int framebufferDrawBuffer) {
		errorChecker.runChecked("framebufferDrawBuffer", () -> framebufferFunctions.framebufferDrawBuffer(framebufferHandle, framebufferDrawBuffer));
	}

	@Override
	public void framebufferReadBuffer(int framebufferHandle, int framebufferReadBuffer) {
		errorChecker.runChecked("framebufferReadBuffer", () -> framebufferFunctions.framebufferReadBuffer(framebufferHandle, framebufferReadBuffer));
	}

	@Override
	public void framebufferDrawBuffers(
			int		framebufferHandle,
			int		framebufferDrawBufferCount,
			long	framebufferDrawBufferDataAddress
	) {
		errorChecker.runChecked("framebufferDrawBuffers", () -> framebufferFunctions.framebufferDrawBuffers(
				framebufferHandle,
				framebufferDrawBufferCount,
				framebufferDrawBufferDataAddress
		));
	}

	@Override
	public void invalidateFramebufferData(
			int		framebufferHandle,
			int		framebufferAttachmentCount,
			long	framebufferAttachmentDataAddress
	) {
		errorChecker.runChecked("invalidateFramebufferData", () -> framebufferFunctions.invalidateFramebufferData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress
		));
	}

	@Override
	public void invalidateFramebufferSubData(
			int		framebufferHandle,
			int		framebufferAttachmentCount,
			long	framebufferAttachmentDataAddress,
			int		invalidatePositionX,
			int		invalidatePositionY,
			int		invalidateWidth,
			int		invalidateHeight
	) {
		errorChecker.runChecked("invalidateFramebufferSubData", () -> framebufferFunctions.invalidateFramebufferSubData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress,
				invalidatePositionX,
				invalidatePositionY,
				invalidateWidth,
				invalidateHeight
		));
	}

	@Override
	public void clearFramebufferiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		errorChecker.runChecked("clearFramebufferiv", () -> framebufferFunctions.clearFramebufferiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		));
	}

	@Override
	public void clearFramebufferuiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		errorChecker.runChecked("clearFramebufferuiv", () -> framebufferFunctions.clearFramebufferuiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		));
	}

	@Override
	public void clearFramebufferfv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		errorChecker.runChecked("clearFramebufferfv", () -> framebufferFunctions.clearFramebufferfv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		));
	}

	@Override
	public void clearFramebufferfi(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			float	clearDepth,
			int		clearStencil
	) {
		errorChecker.runChecked("clearFramebufferfi", () -> framebufferFunctions.clearFramebufferfi(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDepth,
				clearStencil
		));
	}

	@Override
	public void blitFramebuffer(
			int framebufferHandleRead,
			int framebufferHandleWrite,
			int blitReadPositionXStart,
			int blitReadPositionYStart,
			int blitReadPositionXEnd,
			int blitReadPositionYEnd,
			int blitWritePositionXStart,
			int blitWritePositionYStart,
			int blitWritePositionXEnd,
			int blitWritePositionYEnd,
			int blitWriteBufferMask,
			int blitWriteFilter
	) {
		errorChecker.runChecked("blitFramebuffer", () -> framebufferFunctions.blitFramebuffer(
				framebufferHandleRead,
				framebufferHandleWrite,
				blitReadPositionXStart,
				blitReadPositionYStart,
				blitReadPositionXEnd,
				blitReadPositionYEnd,
				blitWritePositionXStart,
				blitWritePositionYStart,
				blitWritePositionXEnd,
				blitWritePositionYEnd,
				blitWriteBufferMask,
				blitWriteFilter
		));
	}

	@Override
	public int checkFramebufferStatus(int framebufferHandle, int framebufferTarget) {
		return errorChecker.runChecked("checkFramebufferStatus", () -> framebufferFunctions.checkFramebufferStatus(framebufferHandle, framebufferTarget));
	}

	@Override
	public void getFramebufferParameteriv(
			int		framebufferHandle,
			int		framebufferParameter,
			long	outDataAddress
	) {
		errorChecker.runChecked("getFramebufferParameteriv", () -> framebufferFunctions.getFramebufferParameteriv(
				framebufferHandle,
				framebufferParameter,
				outDataAddress
		));
	}

	@Override
	public int getFramebufferParameteri(int framebufferHandle, int framebufferParameter) {
		return errorChecker.runChecked("getFramebufferParameteri", () -> framebufferFunctions.getFramebufferParameteri(framebufferHandle, framebufferParameter));
	}

	@Override
	public void getFramebufferAttachmentParameteriv(
			int		framebufferHandle,
			int		framebufferAttachment,
			int		attachmentParameter,
			long	outDataAddress
	) {
		errorChecker.runChecked("getFramebufferAttachmentParameteriv", () -> framebufferFunctions.getFramebufferAttachmentParameteriv(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter,
				outDataAddress
		));
	}

	@Override
	public int getFramebufferAttachmentParameteri(
			int framebufferHandle,
			int framebufferAttachment,
			int attachmentParameter
	) {
		return errorChecker.runChecked("getFramebufferAttachmentParameteri", () -> framebufferFunctions.getFramebufferAttachmentParameteri(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter
		));
	}

	@Override
	public boolean isFramebuffer(int framebufferHandle) {
		return errorChecker.runChecked("isFramebuffer", () -> framebufferFunctions.isFramebuffer(framebufferHandle));
	}

	@Override
	public void deleteFramebuffer(int framebufferHandle) {
		errorChecker.runChecked("deleteFramebuffer", () -> framebufferFunctions.deleteFramebuffer(framebufferHandle));
	}

	@Override
	public void bindFramebuffer(int framebufferTarget, int framebufferHandle) {
		errorChecker.runChecked("bindFramebuffer", () -> framebufferFunctions.bindFramebuffer(framebufferTarget, framebufferHandle));
	}
}
