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
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import org.lwjgl.opengl.GL30;

public class GLFramebufferFunctionsCached implements IGLFramebufferFunctions {

	private final	IGLFramebufferFunctions	framebufferFunctions;
	private			IGLBindingSource		bindingSource;

	public GLFramebufferFunctionsCached(IGLFramebufferFunctions framebufferFunctions) {
		this.framebufferFunctions	= framebufferFunctions;
		this.bindingSource			= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		framebufferFunctions.initialize(device);

		bindingSource = device.getBindingSource();
	}

	@Override
	public int createFramebuffer() {
		return framebufferFunctions.createFramebuffer();
	}

	@Override
	public void framebufferParameteri(
			int framebufferHandle,
			int framebufferParameter,
			int framebufferParameterValue
	) {
		framebufferFunctions.framebufferParameteri(
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
		framebufferFunctions.framebufferTexture(
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
		framebufferFunctions.framebufferTextureLayer(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureMipLevel,
				framebufferLayer
		);
	}

	@Override
	public void framebufferDrawBuffer(int framebufferHandle, int framebufferDrawBuffer) {
		framebufferFunctions.framebufferDrawBuffer(framebufferHandle, framebufferDrawBuffer);
	}

	@Override
	public void framebufferReadBuffer(int framebufferHandle, int framebufferReadBuffer) {
		framebufferFunctions.framebufferReadBuffer(framebufferHandle, framebufferReadBuffer);
	}

	@Override
	public void framebufferDrawBuffers(
			int		framebufferHandle,
			int		framebufferDrawBufferCount,
			long	framebufferDrawBufferDataAddress
	) {
		framebufferFunctions.framebufferDrawBuffers(
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
		framebufferFunctions.invalidateFramebufferData(
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
		framebufferFunctions.invalidateFramebufferSubData(
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
		framebufferFunctions.clearFramebufferiv(
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
		framebufferFunctions.clearFramebufferuiv(
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
		framebufferFunctions.clearFramebufferfv(
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
		framebufferFunctions.clearFramebufferfi(
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
		framebufferFunctions.blitFramebuffer(
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
		return framebufferFunctions.checkFramebufferStatus(framebufferHandle, framebufferTarget);
	}

	@Override
	public void getFramebufferParameteriv(
			int		framebufferHandle,
			int		framebufferParameter,
			long	outDataAddress
	) {
		framebufferFunctions.getFramebufferParameteriv(
				framebufferHandle,
				framebufferParameter,
				outDataAddress
		);
	}

	@Override
	public int getFramebufferParameteri(int framebufferHandle, int framebufferParameter) {
		return framebufferFunctions.getFramebufferParameteri(framebufferHandle, framebufferParameter);
	}

	@Override
	public void getFramebufferAttachmentParameteriv(
			int		framebufferHandle,
			int		framebufferAttachment,
			int		attachmentParameter,
			long	outDataAddress
	) {
		framebufferFunctions.getFramebufferAttachmentParameteriv(
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
		return framebufferFunctions.getFramebufferAttachmentParameteri(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter
		);
	}

	@Override
	public boolean isFramebuffer(int framebufferHandle) {
		return framebufferFunctions.isFramebuffer(framebufferHandle);
	}

	@Override
	public void deleteFramebuffer(int framebufferHandle) {
		framebufferFunctions.deleteFramebuffer(framebufferHandle);
	}

	@Override
	public void bindFramebuffer(int framebufferTarget, int framebufferHandle) {
		var framebufferType = GLFramebufferType.fromTypeConstant(framebufferTarget);

		if (framebufferType == GLFramebufferType.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferType.");
		}

		if (bindingSource.getBoundFramebuffer(framebufferType) == framebufferHandle) {
			return;
		}

		framebufferFunctions.bindFramebuffer(framebufferTarget, framebufferHandle);
	}
}
