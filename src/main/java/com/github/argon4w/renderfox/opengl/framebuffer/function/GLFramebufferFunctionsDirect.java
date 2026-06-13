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

import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.dsa.IDirectStateAccess;
import org.lwjgl.opengl.GL30;

public class GLFramebufferFunctionsDirect implements IGLFramebufferFunctions {

	private IDirectStateAccess	directStateAccess;
	private IGLBindingSource	bindingSource;

	public GLFramebufferFunctionsDirect() {
		this.directStateAccess	= null;
		this.bindingSource		= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		this.directStateAccess	= device.getDirectStateAccess	();
		this.bindingSource		= device.getBindingSource		();
	}

	@Override
	public int createFramebuffer() {
		return directStateAccess.createFramebuffer();
	}

	@Override
	public void framebufferParameteri(
			int framebufferHandle,
			int framebufferParameter,
			int framebufferParameterValue
	) {
		directStateAccess.framebufferParameteri(
				framebufferHandle,
				framebufferParameter,
				framebufferParameterValue
		);
	}

	@Override
	public void framebufferTexture(
			int framebufferHandle,
			int framebufferAttachment,
			int textureHandle,
			int textureMipLevel
	) {
		directStateAccess.framebufferTexture(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureMipLevel
		);
	}

	@Override
	public void framebufferTextureLayer(
			int framebufferHandle,
			int framebufferAttachment,
			int textureHandle,
			int textureMipLevel,
			int framebufferLayer
	) {
		directStateAccess.framebufferTextureLayer(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureMipLevel,
				framebufferLayer
		);
	}

	@Override
	public void framebufferDrawBuffer(int framebufferHandle, int framebufferDrawBuffer) {
		directStateAccess.framebufferDrawBuffer(framebufferHandle, framebufferDrawBuffer);
	}

	@Override
	public void framebufferReadBuffer(int framebufferHandle, int framebufferReadBuffer) {
		directStateAccess.framebufferReadBuffer(framebufferHandle, framebufferReadBuffer);
	}

	@Override
	public void framebufferDrawBuffers(
			int		framebufferHandle,
			int		framebufferDrawBufferCount,
			long	framebufferDrawBufferDataAddress
	) {
		directStateAccess.framebufferDrawBuffers(
				framebufferHandle,
				framebufferDrawBufferCount,
				framebufferDrawBufferDataAddress
		);
	}

	@Override
	public void invalidateFramebufferData(
			int		framebufferHandle,
			int		framebufferAttachmentCount,
			long	framebufferAttachmentDataAddress
	) {
		directStateAccess.invalidateFramebufferData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress
		);
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
		directStateAccess.invalidateFramebufferSubData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress,
				invalidatePositionX,
				invalidatePositionY,
				invalidateWidth,
				invalidateHeight
		);
	}

	@Override
	public void clearFramebufferiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		directStateAccess.clearFramebufferiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferuiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		directStateAccess.clearFramebufferuiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferfv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		directStateAccess.clearFramebufferfv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferfi(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			float	clearDepth,
			int		clearStencil
	) {
		directStateAccess.clearFramebufferfi(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDepth,
				clearStencil
		);
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
		directStateAccess.blitFramebuffer(
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
		);
	}

	@Override
	public int checkFramebufferStatus(int framebufferHandle, int framebufferTarget) {
		return directStateAccess.checkFramebufferStatus(framebufferHandle, framebufferTarget);
	}

	@Override
	public void getFramebufferParameteriv(
			int		framebufferHandle,
			int		framebufferParameter,
			long	outDataAddress
	) {
		directStateAccess.getFramebufferParameteriv(
				framebufferHandle,
				framebufferParameter,
				outDataAddress
		);
	}

	@Override
	public int getFramebufferParameteri(int framebufferHandle, int framebufferParameter) {
		return directStateAccess.getFramebufferParameteri(framebufferHandle, framebufferParameter);
	}

	@Override
	public void getFramebufferAttachmentParameteriv(
			int		framebufferHandle,
			int		framebufferAttachment,
			int		attachmentParameter,
			long	outDataAddress
	) {
		directStateAccess.getFramebufferAttachmentParameteriv(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter,
				outDataAddress
		);
	}

	@Override
	public int getFramebufferAttachmentParameteri(
			int framebufferHandle,
			int framebufferAttachment,
			int attachmentParameter
	) {
		return directStateAccess.getFramebufferAttachmentParameteri(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter
		);
	}

	@Override
	public boolean isFramebuffer(int framebufferHandle) {
		return GL30.glIsFramebuffer(framebufferHandle);
	}

	@Override
	public void deleteFramebuffer(int framebufferHandle) {
		GL30.glDeleteFramebuffers(framebufferHandle);
	}

	@Override
	public void bindFramebuffer(int framebufferTarget, int framebufferHandle) {
		GL30.glBindFramebuffer(framebufferTarget, framebufferHandle);

		bindingSource.bindFramebuffer(framebufferTarget, framebufferHandle);
	}

	public static IGLFramebufferFunctions of() {
		return new GLFramebufferFunctionsDirect();
	}
}
