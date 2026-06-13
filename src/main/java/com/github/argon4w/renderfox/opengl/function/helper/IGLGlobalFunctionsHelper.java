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

import com.github.argon4w.renderfox.opengl.binding.IGLBindingSource;
import com.github.argon4w.renderfox.opengl.buffer.function.IGLBufferHelperFunctions;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.function.IGLFramebufferHelperFunctions;
import com.github.argon4w.renderfox.opengl.framebuffer.function.IGLFramebufferQueryFunctions;
import com.github.argon4w.renderfox.opengl.function.parameter.IGLParameter;
import com.github.argon4w.renderfox.opengl.texture.function.IGLTextureHelperFunctions;
import com.github.argon4w.renderfox.opengl.texture.pixel.function.IGLPixelQueryFunctions;
import com.github.argon4w.renderfox.opengl.texture.sampler.functions.IGLSamplerQueryFunctions;

public interface IGLGlobalFunctionsHelper extends IGLBufferHelperFunctions, IGLTextureHelperFunctions, IGLFramebufferHelperFunctions, IGLPixelQueryFunctions, IGLSamplerQueryFunctions {

	void				initialize		(OpenGLDevice device);
	float				getFloat		(IGLParameter parameter);
	int					getInt			(IGLParameter parameter);
	int					getIntIndexed	(IGLParameter parameter, int index);
	long				getLongIndexed	(IGLParameter parameter, int index);
	IGLBindingSource	getBindingSource();
}
