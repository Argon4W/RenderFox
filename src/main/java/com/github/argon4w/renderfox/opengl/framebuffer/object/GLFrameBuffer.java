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

package com.github.argon4w.renderfox.opengl.framebuffer.object;

import com.github.argon4w.renderfox.data.coordinate.IOffset3D;
import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.data.view.wrapped.StackDataView;
import com.github.argon4w.renderfox.format.ColorInt;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferClearBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag.GLFramebufferWriteBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.IGLFramebufferBase;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.IGLFramebufferBinding;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.IGLFramebufferStore;
import com.github.argon4w.renderfox.opengl.framebuffer.object.raw.IGLRawFramebuffer;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponentType;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTextureBase;

public class GLFrameBuffer implements IGLFramebufferBase, IGLFramebufferBinding, IGLFramebufferStore {

	private final IGLRawFramebuffer framebuffer;

	public GLFrameBuffer(IGLRawFramebuffer framebuffer) {
		this.framebuffer = framebuffer;
	}

	public void updateColorAttachment(
			IGLRawTextureBase	attachTexture,
			int					attachMipLevel,
			int					attachIndex
	) {
		framebuffer.attachTexture(
				GLFramebufferAttachment.color(attachIndex),
				attachTexture,
				attachMipLevel
		);
	}

	public void updateColorAttachmentLayer(
			IGLRawTextureBase	attachTexture,
			int					attachMipLevel,
			int					attachLayer,
			int					attachIndex
	) {
		framebuffer.attachTextureLayer(
				GLFramebufferAttachment.color(attachIndex),
				attachTexture,
				attachMipLevel,
				attachLayer
		);
	}

	public void updateDepthAttachment(IGLRawTextureBase attachTexture, int attachMipLevel) {
		framebuffer.attachTexture(
				GLFramebufferAttachment.DEPTH,
				attachTexture,
				attachMipLevel
		);
	}

	public void updateDepthAttachmentLayer(
			IGLRawTextureBase	attachTexture,
			int					attachMipLevel,
			int					attachLayer
	) {
		framebuffer.attachTextureLayer(
				GLFramebufferAttachment.DEPTH,
				attachTexture,
				attachMipLevel,
				attachLayer
		);
	}

	public void updateStencilAttachment(IGLRawTextureBase attachTexture, int attachMipLevel) {
		framebuffer.attachTexture(
				GLFramebufferAttachment.STENCIL,
				attachTexture,
				attachMipLevel
		);
	}

	public void updateStencilAttachmentLayer(
			IGLRawTextureBase	attachTexture,
			int					attachMipLevel,
			int					attachLayer
	) {
		framebuffer.attachTextureLayer(
				GLFramebufferAttachment.STENCIL,
				attachTexture,
				attachMipLevel,
				attachLayer
		);
	}

	public void copyRangeRangeDataFrom(
			IGLFramebufferBase			blitFramebufferWrite,
			IOffset3D					blitOffsetRead,
			IOffset3D					blitOffsetWrite,
			IExtent3D					blitExtent,
			GLFramebufferWriteBuffer	blitWriteBuffer,
			GLFilterMode				blitFilterMode
	) {
		if (blitOffsetRead == null) {
			throw new IllegalArgumentException("BlitOffsetRead cannot be null.");
		}

		if (blitOffsetWrite == null) {
			throw new IllegalArgumentException("BlitOffsetWrite cannot be null.");
		}

		if (blitExtent == null) {
			throw new IllegalArgumentException("BlitExtent cannot be null.");
		}

		framebuffer.blitDataTo(
				blitFramebufferWrite,
				(int) blitOffsetRead	.getOffsetX(),
				(int) blitOffsetRead	.getOffsetY(),
				(int) blitOffsetRead	.getOffsetX() + (int) blitExtent.getWidth	(),
				(int) blitOffsetRead	.getOffsetY() + (int) blitExtent.getHeight	(),
				(int) blitOffsetWrite	.getOffsetX(),
				(int) blitOffsetWrite	.getOffsetY(),
				(int) blitOffsetWrite	.getOffsetX() + (int) blitExtent.getWidth	(),
				(int) blitOffsetWrite	.getOffsetY() + (int) blitExtent.getHeight	(),
				blitWriteBuffer,
				blitFilterMode
		);
	}

	public void copyRangeDataFrom(
			IGLFramebufferBase			blitFramebufferRead,
			IOffset3D					blitOffsetRead,
			IOffset3D					blitOffsetWrite,
			IExtent3D					blitExtent,
			GLFramebufferWriteBuffer	blitWriteBuffer,
			GLFilterMode				blitFilterMode
	) {
		if (blitOffsetRead == null) {
			throw new IllegalArgumentException("BlitOffsetRead cannot be null.");
		}

		if (blitOffsetWrite == null) {
			throw new IllegalArgumentException("BlitOffsetWrite cannot be null.");
		}

		if (blitExtent == null) {
			throw new IllegalArgumentException("BlitExtent cannot be null.");
		}

		framebuffer.blitDataTo(
				blitFramebufferRead,
				(int) blitOffsetRead	.getOffsetX(),
				(int) blitOffsetRead	.getOffsetY(),
				(int) blitOffsetRead	.getOffsetX() + (int) blitExtent.getWidth	(),
				(int) blitOffsetRead	.getOffsetY() + (int) blitExtent.getHeight	(),
				(int) blitOffsetWrite	.getOffsetX(),
				(int) blitOffsetWrite	.getOffsetY(),
				(int) blitOffsetWrite	.getOffsetX() + (int) blitExtent.getWidth	(),
				(int) blitOffsetWrite	.getOffsetY() + (int) blitExtent.getHeight	(),
				blitWriteBuffer,
				blitFilterMode
		);
	}

	public void clearColorInt(ColorInt clearColor, int clearDrawBuffer) {
		if (clearColor == null) {
			throw new IllegalArgumentException("ClearColor cannot be null.");
		}

		try (var dataView = StackDataView.ofInts(4)) {
			framebuffer.clearAllDataInt(
					GLFramebufferClearBuffer.COLOR,
					clearDrawBuffer,
					dataView
							.putInt	(0L,	clearColor.getRed	())
							.putInt	(4L,	clearColor.getGreen	())
							.putInt	(8L,	clearColor.getBlue	())
							.putInt	(12L,	clearColor.getAlpha	())
							.address()
			);
		}
	}

	public void clearColorUnsignedInt(ColorInt clearColor, int clearDrawBuffer) {
		if (clearColor == null) {
			throw new IllegalArgumentException("ClearColor cannot be null.");
		}

		try (var dataView = StackDataView.ofInts(4)) {
			framebuffer.clearAllDataUnsignedInt(
					GLFramebufferClearBuffer.COLOR,
					clearDrawBuffer,
					dataView
							.putInt	(0L,	clearColor.getRed	())
							.putInt	(4L,	clearColor.getGreen	())
							.putInt	(8L,	clearColor.getBlue	())
							.putInt	(12L,	clearColor.getAlpha	())
							.address()
			);
		}
	}

	public void clearColorFloat(ColorInt clearColor, int clearDrawBuffer) {
		if (clearColor == null) {
			throw new IllegalArgumentException("ClearColor cannot be null.");
		}

		try (var dataView = StackDataView.ofFloats(4)) {
			framebuffer.clearAllDataFloat(
					GLFramebufferClearBuffer.COLOR,
					clearDrawBuffer,
					dataView
							.putFloat	(0L,	clearColor.getRed	())
							.putFloat	(4L,	clearColor.getGreen	())
							.putFloat	(8L,	clearColor.getBlue	())
							.putFloat	(12L,	clearColor.getAlpha	())
							.address	()
			);
		}
	}

	public void clearDepth(float depth) {
		try (var dataView = StackDataView.aFloat(depth)) {
			framebuffer.clearAllDataFloat(
					GLFramebufferClearBuffer.DEPTH,
					GLFramebufferClearBuffer.DEPTH.getDrawBuffer(),
					dataView.address()
			);
		}
	}

	public void clearStencil(int stencil) {
		try (var dataView = StackDataView.aInt(stencil)) {
			framebuffer.clearAllDataInt(
					GLFramebufferClearBuffer.STENCIL,
					GLFramebufferClearBuffer.STENCIL.getDrawBuffer(),
					dataView.address()
			);
		}
	}

	public void clearDepthStencil(float clearDepth, int clearStencil) {
		framebuffer.clearAllDataDepthStencil(
				GLFramebufferClearBuffer.DEPTH_STENCIL,
				GLFramebufferClearBuffer.DEPTH_STENCIL.getDrawBuffer(),
				clearDepth,
				clearStencil
		);
	}

	@Override
	public void bind(GLFramebufferType framebufferType) {
		framebuffer.bind(framebufferType);
	}

	@Override
	public void delete() {
		framebuffer.delete();
	}

	@Override
	public int getFramebufferHandle() {
		return framebuffer.getFramebufferHandle();
	}

	@Override
	public boolean isDeleted() {
		return framebuffer.isDeleted();
	}

	@Override
	public int getAttachmentBinding(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentBinding(attachment);
	}

	@Override
	public int getAttachmentMipLevel(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentMipLevel(attachment);
	}

	@Override
	public int getAttachmentLayer(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentLayer(attachment);
	}

	@Override
	public int getAttachmentRedSize(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentRedSize(attachment);
	}

	@Override
	public int getAttachmentGreenSize(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentGreenSize(attachment);
	}

	@Override
	public int getAttachmentBlueSize(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentBlueSize(attachment);
	}

	@Override
	public int getAttachmentAlphaSize(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentAlphaSize(attachment);
	}

	@Override
	public int getAttachmentDepthSize(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentDepthSize(attachment);
	}

	@Override
	public int getAttachmentStencilSize(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentStencilSize(attachment);
	}

	@Override
	public boolean isAttachmentLayered(GLFramebufferAttachment attachment) {
		return framebuffer.isAttachmentLayered(attachment);
	}

	@Override
	public GLTextureComponentType getAttachmentComponentType(GLFramebufferAttachment attachment) {
		return framebuffer.getAttachmentComponentType(attachment);
	}

	@Override
	public int getDefaultWidth() {
		return framebuffer.getDefaultWidth();
	}

	@Override
	public int getDefaultHeight() {
		return framebuffer.getDefaultHeight();
	}

	@Override
	public int getDefaultLayers() {
		return framebuffer.getDefaultLayers();
	}

	@Override
	public int getDefaultSamples() {
		return framebuffer.getDefaultSamples();
	}

	@Override
	public boolean isDefaultFixedSampleLocation() {
		return framebuffer.isDefaultFixedSampleLocation();
	}

	@Override
	public boolean isMultisampled() {
		return framebuffer.isMultisampled();
	}

	@Override
	public int getMultisampleBits() {
		return framebuffer.getMultisampleBits();
	}

	public IGLRawFramebuffer getRawFramebuffer() {
		return framebuffer;
	}
}
