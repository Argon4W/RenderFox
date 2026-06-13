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

package com.github.argon4w.renderfox.opengl.dsa;

import com.github.argon4w.renderfox.opengl.buffer.function.IGLBufferObjectFunctions;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.function.IGLFramebufferObjectFunctions;
import com.github.argon4w.renderfox.opengl.texture.function.IGLTextureObjectFunctions;
import com.github.argon4w.renderfox.opengl.texture.sampler.functions.IGLSamplerObjectFunctions;
import org.lwjgl.PointerBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public interface IDirectStateAccess extends IGLBufferObjectFunctions, IGLTextureObjectFunctions, IGLSamplerObjectFunctions, IGLFramebufferObjectFunctions {

	void initialize(OpenGLDevice context);

	int createVertexArray();

	void disableVertexArrayAttrib(int vertexArrayHandle, int index);

	void enableVertexArrayAttrib(int vertexArrayHandle, int index);

	void vertexArrayElementBuffer(int vertexArrayHandle, int elementBufferHandle);

	void vertexArrayVertexBuffer(
			int				vertexArrayHandle,
			int				vertexBufferBindingIndex,
			int				vertexBufferHandle,
			int				vertexBufferOffset,
			int				vertexBufferStride
	);

	void vertexArrayVertexBuffers(
			int				vertexArrayHandle,
			int				vertexArrayFirstBindingIndex,
			IntBuffer		vertexBufferHandleData,
			PointerBuffer	vertexBufferOffsetData,
			IntBuffer		vertexBufferStrideData
	);

	void vertexArrayVertexBuffers(
			int				vertexArrayHandle,
			int				vertexArrayFirstBindingIndex,
			int				vertexArrayBindingIndexCount,
			long			vertexBufferHandleDataAddress,
			long			vertexBufferOffsetDataAddress,
			long			vertexBufferStrideDataAddress
	);

	void vertexArrayAttribFormat(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeSize,
			int				vertexAttributeInputType,
			boolean			vertexAttributeNormalized,
			int				vertexAttributeOffset
	);

	void vertexArrayAttribIFormat(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeSize,
			int				vertexAttributeInputType,
			int				vertexAttributeOffset
	);

	void vertexArrayAttribLFormat(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeSize,
			int				vertexAttributeInputType,
			int				vertexAttributeOffset
	);

	void vertexArrayAttribBinding(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexBufferBindingIndex
	);

	void vertexArrayBindingDivisor(
			int				vertexArrayHandle,
			int				vertexBufferBindingIndex,
			int				vertexBufferDivisor
	);

	void getVertexArrayiv(
			int				vertexArrayHandle,
			int				vertexArrayParameter,
			IntBuffer		outData
	);

	void getVertexArrayiv(
			int				vertexArrayHandle,
			int				vertexArrayParameter,
			long			outDataAddress
	);

	int getVertexArrayi(int vertexArrayHandle, int vertexArrayParameter);

	void getVertexArrayIndexediv(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeParameter,
			IntBuffer		outData
	);

	void getVertexArrayIndexediv(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeParameter,
			long			outDataAddress
	);

	int getVertexArrayIndexedi(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeParameter
	);

	void getVertexArrayIndexed64iv(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeParameter,
			LongBuffer		outData
	);

	void getVertexArrayIndexed64iv(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeParameter,
			long			outDataAddress
	);

	long getVertexArrayIndexed64i(
			int				vertexArrayHandle,
			int				vertexAttributeIndex,
			int				vertexAttributeParameter
	);
}
