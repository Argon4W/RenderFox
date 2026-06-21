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

package com.github.argon4w.renderfox.opengl.function.helper;

import com.github.argon4w.renderfox.opengl.buffer.GLBufferBlockType;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.function.IGLGlobalFunctions;
import com.github.argon4w.renderfox.opengl.function.parameter.GLGlobalParameter;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

public class GLGlobalFunctionsHelperCached extends GLGlobalFunctionsHelperQuery {

	private final Reference2IntMap<GLBufferBlockType> offsetAligns;
	private final Reference2IntMap<GLBufferBlockType> maxBindings;

	private int maxTextureUnits;
	private int maxTextureSize;
	private int max3DTextureSize;
	private int maxCubeMapTextureSize;
	private int maxArrayTextureLayers;
	private int maxDepthTextureSamples;
	private int maxColorTextureSamples;
	private int maxIntegerSamples;
	private int maxDrawBuffers;
	private int maxColorAttachments;
	private int maxFramebufferWidth;
	private int maxFramebufferHeight;
	private int maxFramebufferLayers;
	private int maxFramebufferSamples;

	public GLGlobalFunctionsHelperCached(IGLGlobalFunctions globalFunctions) {
		super(globalFunctions);

		this.offsetAligns		= new Reference2IntOpenHashMap<>();
		this.maxBindings		= new Reference2IntOpenHashMap<>();

		this.maxTextureUnits		= 0;
		this.maxTextureSize			= 0;
		this.max3DTextureSize		= 0;
		this.maxCubeMapTextureSize	= 0;
		this.maxArrayTextureLayers	= 0;
		this.maxDepthTextureSamples	= 0;
		this.maxColorTextureSamples	= 0;
		this.maxIntegerSamples		= 0;
		this.maxDrawBuffers			= 0;
		this.maxColorAttachments	= 0;
		this.maxFramebufferWidth	= 0;
		this.maxFramebufferHeight	= 0;
		this.maxFramebufferLayers	= 0;
		this.maxFramebufferSamples	= 0;
	}

	public void initialize(OpenGLDevice device) {
		super.initialize(device);

		for (var bufferBlockType : GLBufferBlockType.values()) {
			this.offsetAligns	.put(bufferBlockType, super.getBufferOffsetAlign(bufferBlockType));
			this.maxBindings	.put(bufferBlockType, super.getBufferMaxBindings(bufferBlockType));
		}

		maxTextureUnits			= super.getInt(GLGlobalParameter.MAX_COMBINED_TEXTURE_IMAGE_UNITS);
		maxTextureSize			= super.getInt(GLGlobalParameter.MAX_TEXTURE_SIZE);
		max3DTextureSize		= super.getInt(GLGlobalParameter.MAX_3D_TEXTURE_SIZE);
		maxCubeMapTextureSize	= super.getInt(GLGlobalParameter.MAX_CUBE_MAP_TEXTURE_SIZE);
		maxArrayTextureLayers	= super.getInt(GLGlobalParameter.MAX_ARRAY_TEXTURE_LAYERS);
		maxDepthTextureSamples	= super.getInt(GLGlobalParameter.MAX_DEPTH_TEXTURE_SAMPLES);
		maxColorTextureSamples	= super.getInt(GLGlobalParameter.MAX_COLOR_TEXTURE_SAMPLES);
		maxIntegerSamples		= super.getInt(GLGlobalParameter.MAX_INTEGER_SAMPLES);
		maxDrawBuffers			= super.getInt(GLGlobalParameter.MAX_DRAW_BUFFERS);
		maxColorAttachments		= super.getInt(GLGlobalParameter.MAX_COLOR_ATTACHMENTS);
		maxFramebufferWidth		= super.getInt(GLGlobalParameter.MAX_FRAMEBUFFER_WIDTH);
		maxFramebufferHeight	= super.getInt(GLGlobalParameter.MAX_FRAMEBUFFER_HEIGHT);
		maxFramebufferLayers	= super.getInt(GLGlobalParameter.MAX_FRAMEBUFFER_LAYERS);
		maxFramebufferSamples	= super.getInt(GLGlobalParameter.MAX_FRAMEBUFFER_SAMPLES);
	}

	@Override
	public int getInt(IGLParameter parameter) {
		return switch (parameter) {
			case GLGlobalParameter.MAX_COMBINED_TEXTURE_IMAGE_UNITS	-> maxTextureUnits;
			case GLGlobalParameter.MAX_TEXTURE_SIZE					-> maxTextureSize;
			case GLGlobalParameter.MAX_3D_TEXTURE_SIZE				-> max3DTextureSize;
			case GLGlobalParameter.MAX_CUBE_MAP_TEXTURE_SIZE		-> maxCubeMapTextureSize;
			case GLGlobalParameter.MAX_ARRAY_TEXTURE_LAYERS			-> maxArrayTextureLayers;
			case GLGlobalParameter.MAX_DEPTH_TEXTURE_SAMPLES		-> maxDepthTextureSamples;
			case GLGlobalParameter.MAX_COLOR_TEXTURE_SAMPLES		-> maxColorTextureSamples;
			case GLGlobalParameter.MAX_INTEGER_SAMPLES				-> maxIntegerSamples;
			case GLGlobalParameter.MAX_DRAW_BUFFERS					-> maxDrawBuffers;
			case GLGlobalParameter.MAX_COLOR_ATTACHMENTS			-> maxColorAttachments;
			case GLGlobalParameter.MAX_FRAMEBUFFER_WIDTH			-> maxFramebufferWidth;
			case GLGlobalParameter.MAX_FRAMEBUFFER_HEIGHT			-> maxFramebufferHeight;
			case GLGlobalParameter.MAX_FRAMEBUFFER_LAYERS			-> maxFramebufferLayers;
			case GLGlobalParameter.MAX_FRAMEBUFFER_SAMPLES			-> maxFramebufferSamples;
			default													-> super.getInt(parameter);
		};
	}

	@Override
	public int getBufferOffsetAlign(GLBufferBlockType bufferBlockType) {
		return offsetAligns.getOrDefault(bufferBlockType, 1);
	}

	@Override
	public int getBufferMaxBindings(GLBufferBlockType bufferBlockType) {
		return maxBindings.getOrDefault(bufferBlockType, 0);
	}

	@Override
	public int getMaxTextureUnits() {
		return maxTextureUnits;
	}

	@Override
	public int getMaxTextureSize() {
		return maxTextureSize;
	}

	@Override
	public int getMax3DTextureSize() {
		return max3DTextureSize;
	}

	@Override
	public int getMaxCubeMapTextureSize() {
		return maxCubeMapTextureSize;
	}

	@Override
	public int getMaxArrayTextureLayers() {
		return maxArrayTextureLayers;
	}

	@Override
	public int getMaxDepthTextureSamples() {
		return maxDepthTextureSamples;
	}

	@Override
	public int getMaxColorTextureSamples() {
		return maxColorTextureSamples;
	}

	@Override
	public int getMaxIntegerSamples() {
		return maxIntegerSamples;
	}

	@Override
	public int getMaxDrawBuffers() {
		return maxDrawBuffers;
	}

	@Override
	public int getMaxColorAttachments() {
		return maxColorAttachments;
	}

	@Override
	public int getMaxFramebufferWidth() {
		return maxFramebufferWidth;
	}

	@Override
	public int getMaxFramebufferHeight() {
		return maxFramebufferHeight;
	}

	@Override
	public int getMaxFramebufferLayers() {
		return maxFramebufferLayers;
	}

	@Override
	public int getMaxFramebufferSamples() {
		return maxFramebufferSamples;
	}
}
