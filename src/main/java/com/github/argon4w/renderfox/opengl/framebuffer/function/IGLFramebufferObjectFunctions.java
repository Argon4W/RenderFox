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

public interface IGLFramebufferObjectFunctions {

	int createFramebuffer();

	void framebufferParameteri(
			int				framebufferHandle,
			int				framebufferParameter,
			int				framebufferParameterValue
	);

	void framebufferTexture(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				textureHandle,
			int				textureMipLevel
	);

	void framebufferTextureLayer(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				textureHandle,
			int				textureMipLevel,
			int				framebufferLayer
	);

	void framebufferDrawBuffer(int framebufferHandle, int framebufferDrawBuffer);

	void framebufferDrawBuffers(
			int				framebufferHandle,
			int				framebufferDrawBufferCount,
			long			framebufferDrawBufferDataAddress
	);

	void framebufferReadBuffer(int framebufferHandle, int framebufferReadBuffer);

	void invalidateFramebufferData(
			int				framebufferHandle,
			int				framebufferAttachmentCount,
			long			framebufferAttachmentDataAddress
	);

	void invalidateFramebufferSubData(
			int				framebufferHandle,
			int				framebufferAttachmentCount,
			long			framebufferAttachmentDataAddress,
			int				invalidatePositionX,
			int				invalidatePositionY,
			int				invalidateWidth,
			int				invalidateHeight
	);

	void clearFramebufferiv(
			int				framebufferHandle,
			int				clearBuffer,
			int				clearDrawBuffer,
			long			clearDataAddress
	);

	void clearFramebufferuiv(
			int				framebufferHandle,
			int				clearBuffer,
			int				clearDrawBuffer,
			long			clearDataAddress
	);

	void clearFramebufferfv(
			int				framebufferHandle,
			int				clearBuffer,
			int				clearDrawBuffer,
			long			clearDataAddress
	);

	void clearFramebufferfi(
			int				framebufferHandle,
			int				clearBuffer,
			int				clearDrawBuffer,
			float			clearDepth,
			int				clearStencil
	);

	void blitFramebuffer(
			int				framebufferHandleRead,
			int				framebufferHandleWrite,
			int				blitReadPositionXStart,
			int				blitReadPositionYStart,
			int				blitReadPositionXEnd,
			int				blitReadPositionYEnd,
			int				blitWritePositionXStart,
			int				blitWritePositionYStart,
			int				blitWritePositionXEnd,
			int				blitWritePositionYEnd,
			int				blitWriteBufferMask,
			int				blitWriteFilter
	);

	int checkFramebufferStatus(int framebufferHandle, int framebufferTarget);

	void getFramebufferParameteriv(
			int				framebufferHandle,
			int				framebufferParameter,
			long			outDataAddress
	);

	int getFramebufferParameteri(int framebufferHandle, int framebufferParameter);

	void getFramebufferAttachmentParameteriv(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				attachmentParameter,
			long			outDataAddress
	);

	int getFramebufferAttachmentParameteri(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				attachmentParameter
	);
}
