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

package com.github.argon4w.renderfox.opengl.buffer.object.mapped;

import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferAccessBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferMapAccess;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;

public class GLMappedBufferCreateInfo {

	private final int					bufferSize;
	private final GLBufferType			bufferType;
	private final GLBufferStorageFlag	storageFlag;
	private final GLBufferMapAccess		mapAccess;

	public GLMappedBufferCreateInfo(
			int					bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag,
			GLBufferMapAccess	mapAccess
	) {
		this.bufferSize		= bufferSize;
		this.bufferType		= bufferType;
		this.storageFlag	= storageFlag;
		this.mapAccess		= mapAccess;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public GLBufferType getBufferType() {
		return bufferType;
	}

	public GLBufferStorageFlag getStorageFlag() {
		return storageFlag;
	}

	public GLBufferMapAccess getMapAccess() {
		return mapAccess;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private int					bufferSize;
		private GLBufferType		bufferType;
		private GLBufferStorageFlag	storageFlag;
		private GLBufferMapAccess	mapAccess;

		private Builder() {
			this.bufferSize		= 64;
			this.bufferType		= GLBufferType			.VERTEX_BUFFER;
			this.storageFlag	= GLBufferStorageFlag	.of();
			this.mapAccess		= GLBufferMapAccess		.of();
		}

		private Builder(
				int					bufferSize,
				GLBufferType		bufferType,
				GLBufferStorageFlag	storageFlag,
				GLBufferMapAccess	mapAccess
		) {
			this.bufferSize		= bufferSize;
			this.bufferType		= bufferType;
			this.storageFlag	= storageFlag	.copy();
			this.mapAccess		= mapAccess		.copy();
		}

		public Builder withBufferSize(int bufferSize) {
			this.bufferSize = bufferSize;
			return this;
		}

		public Builder withBufferType(GLBufferType bufferType) {
			this.bufferType = bufferType;
			return this;
		}

		public Builder withStorageFlag(GLBufferStorageFlag storageFlag) {
			this.storageFlag = storageFlag.copy();
			return this;
		}

		public Builder withAccess(GLBufferMapAccess mapAccess) {
			this.mapAccess = mapAccess.copy();
			return this;
		}

		public Builder addStorageBit(GLBufferStorageBit flagBit) {
			this.storageFlag.add(flagBit);
			return this;
		}

		public Builder addAccessBit(GLBufferAccessBit accessBit) {
			this.mapAccess.add(accessBit);
			return this;
		}

		public Builder copy() {
			return new Builder(
					bufferSize,
					bufferType,
					storageFlag,
					mapAccess
			);
		}

		public GLMappedBufferCreateInfo build() {
			return new GLMappedBufferCreateInfo(
					bufferSize,
					bufferType,
					storageFlag,
					mapAccess
			);
		}
	}
}
