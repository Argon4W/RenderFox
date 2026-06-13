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
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

public class GLGlobalFunctionsHelperCached extends GLGlobalFunctionsHelperQuery {

	private final Reference2IntMap<GLBufferBlockType> offsetAligns;
	private final Reference2IntMap<GLBufferBlockType> maxBindings;

	private int maxTextureUnits;
	private int maxTextureSize;
	private int maxArrayTextureLayers;
	private int maxDepthTextureSamples;
	private int maxColorTextureSamples;
	private int maxIntegerSamples;

	public GLGlobalFunctionsHelperCached(IGLGlobalFunctions globalFunctions) {
		super(globalFunctions);

		this.offsetAligns		= new Reference2IntOpenHashMap<>();
		this.maxBindings		= new Reference2IntOpenHashMap<>();

		this.maxTextureUnits		= 0;
		this.maxTextureSize			= 0;
		this.maxArrayTextureLayers	= 0;
		this.maxDepthTextureSamples	= 0;
		this.maxColorTextureSamples	= 0;
		this.maxIntegerSamples		= 0;
	}

	public void initialize(OpenGLDevice device) {
		for (var bufferBlockType : GLBufferBlockType.values()) {
			this.offsetAligns	.put(bufferBlockType, super.getBufferOffsetAlign(bufferBlockType));
			this.maxBindings	.put(bufferBlockType, super.getBufferMaxBindings(bufferBlockType));
		}

		bindingSource			= device.getBindingSource();

		maxTextureUnits			= getInt(GLGlobalParameter.MAX_COMBINED_TEXTURE_IMAGE_UNITS);
		maxTextureSize			= getInt(GLGlobalParameter.MAX_TEXTURE_SIZE);
		maxArrayTextureLayers	= getInt(GLGlobalParameter.MAX_ARRAY_TEXTURE_LAYERS);
		maxDepthTextureSamples	= getInt(GLGlobalParameter.MAX_DEPTH_TEXTURE_SAMPLES);
		maxColorTextureSamples	= getInt(GLGlobalParameter.MAX_COLOR_TEXTURE_SAMPLES);
		maxIntegerSamples		= getInt(GLGlobalParameter.MAX_INTEGER_SAMPLES);
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
}
