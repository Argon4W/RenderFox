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

package com.github.argon4w.renderfox.opengl.buffer.object.feature;

import com.github.argon4w.renderfox.data.coordinate.DataRange;
import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferMapAccessLegacy;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferParameter;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.GLBufferUsage;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferAccessBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import org.lwjgl.opengl.GL11;

public abstract class AbstractGLBufferStore implements IGLBufferStore {

	public int getParameterInt(GLBufferParameter parameter) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	public long getParameterLong(GLBufferParameter parameter) {
		throw new UnsupportedOperationException("Unsupported Operation.");
	}

	@Override
	public int getBufferSize() {
		return getParameterInt(GLBufferParameter.BUFFER_SIZE);
	}

	@Override
	public boolean isImmutable() {
		return getParameterInt(GLBufferParameter.BUFFER_IMMUTABLE_STORAGE) == GL11.GL_TRUE;
	}

	@Override
	public boolean isMapped() {
		return getParameterInt(GLBufferParameter.BUFFER_MAPPED) == GL11.GL_TRUE;
	}

	@Override
	public long getMapOffset() {
		return getParameterLong(GLBufferParameter.BUFFER_MAP_OFFSET);
	}

	@Override
	public long getMapLength() {
		return getParameterLong(GLBufferParameter.BUFFER_MAP_LENGTH);
	}

	@Override
	public IDataRange getMapRange() {
		return new DataRange(getMapOffset(), getMapLength());
	}

	@Override
	public GLBufferStorageFlag getStorageFlag() {
		return GLBufferStorageBit.toFlag(getParameterInt(GLBufferParameter.BUFFER_STORAGE_FLAGS));
	}

	@Override
	public GLBufferMapAccess getAccessFlag() {
		return GLBufferAccessBit.toAccess(getParameterInt(GLBufferParameter.BUFFER_ACCESS_FLAGS));
	}

	@Override
	public GLBufferUsage getBufferUsage() {
		return GLBufferUsage.fromConstant(getParameterInt(GLBufferParameter.GL_BUFFER_USAGE));
	}

	@Override
	public GLBufferMapAccessLegacy getAccess() {
		return GLBufferMapAccessLegacy.fromConstant(getParameterInt(GLBufferParameter.BUFFER_ACCESS));
	}

	@Override
	public boolean isPersistent() {
		return getAccessFlag().isPersistent();
	}

	@Override
	public boolean isDynamic() {
		return getStorageFlag().isDynamic();
	}
}
