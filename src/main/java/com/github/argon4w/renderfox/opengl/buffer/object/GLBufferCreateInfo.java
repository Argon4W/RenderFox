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

package com.github.argon4w.renderfox.opengl.buffer.object;

import com.github.argon4w.renderfox.opengl.buffer.GLBufferType;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageBit;
import com.github.argon4w.renderfox.opengl.buffer.function.parameter.flag.GLBufferStorageFlag;
import com.github.argon4w.renderfox.opengl.buffer.object.mapped.GLMappedBufferCreateInfo;

public class GLBufferCreateInfo {

	private final int					bufferSize;
	private final GLBufferType			bufferType;
	private final GLBufferStorageFlag	storageFlag;

	public GLBufferCreateInfo(
			int					bufferSize,
			GLBufferType		bufferType,
			GLBufferStorageFlag	storageFlag
	) {
		this.bufferSize		= bufferSize;
		this.bufferType		= bufferType;
		this.storageFlag	= storageFlag;
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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private int					bufferSize;
		private GLBufferType		bufferType;
		private GLBufferStorageFlag	storageFlag;

		private Builder() {
			this.bufferSize		= 64;
			this.bufferType		= GLBufferType			.VERTEX_BUFFER;
			this.storageFlag	= GLBufferStorageFlag	.of();
		}

		private Builder(
				int					bufferSize,
				GLBufferType		bufferType,
				GLBufferStorageFlag	storageFlag
		) {
			this.bufferSize		= bufferSize;
			this.bufferType		= bufferType;
			this.storageFlag	= storageFlag.copy();
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

		public Builder addStorageBit(GLBufferStorageBit flagBit) {
			this.storageFlag.add(flagBit);
			return this;
		}

		public Builder copy() {
			return new Builder(
					bufferSize,
					bufferType,
					storageFlag
			);
		}

		public GLMappedBufferCreateInfo.Builder asMapped() {
			return GLMappedBufferCreateInfo
					.builder()
					.withBufferSize	(bufferSize)
					.withBufferType	(bufferType)
					.withStorageFlag(storageFlag);
		}

		public GLBufferCreateInfo build() {
			return new GLBufferCreateInfo(
					bufferSize,
					bufferType,
					storageFlag
			);
		}
	}
}
