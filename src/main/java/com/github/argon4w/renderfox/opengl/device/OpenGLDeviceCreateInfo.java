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

package com.github.argon4w.renderfox.opengl.device;

import com.github.argon4w.renderfox.data.size.IResizeMethod;
import com.github.argon4w.renderfox.opengl.binding.GLBindingSourceType;

public class OpenGLDeviceCreateInfo {

	private final boolean				useDirectStateAccess;
	private final boolean				useMultiBind;
	private final boolean				useBufferStorage;
	private final boolean				useTextureStorage;
	private final boolean				useTextureView;
	private final boolean				useTextureAnisotropy;
	private final boolean				useAttribBinding;
	private final boolean				useValidation;
	private final boolean				useErrorCheck;
	private final boolean				cacheParameters;
	private final boolean				cacheStates;
	private final boolean				manageStates;
	private final boolean				cacheProperties;
	private final IResizeMethod			bufferResizeMethod;
	private final GLBindingSourceType	bindingSourceType;

	public OpenGLDeviceCreateInfo(
			boolean				useDirectStateAccess,
			boolean				useMultiBind,
			boolean				useBufferStorage,
			boolean				useTextureStorage,
			boolean				useTextureView,
			boolean				useTextureAnisotropy,
			boolean				useAttribBinding,
			boolean				useValidation,
			boolean				useErrorCheck,
			boolean				cacheParameters,
			boolean				cacheStates,
			boolean				manageStates,
			boolean				cacheProperties,
			IResizeMethod		bufferResizeMethod,
			GLBindingSourceType	bindingSourceType
	) {
		this.useDirectStateAccess	= useDirectStateAccess;
		this.useMultiBind			= useMultiBind;
		this.useBufferStorage		= useBufferStorage;
		this.useTextureStorage		= useTextureStorage;
		this.useTextureView			= useTextureView;
		this.useTextureAnisotropy	= useTextureAnisotropy;
		this.useAttribBinding		= useAttribBinding;
		this.useValidation			= useValidation;
		this.useErrorCheck			= useErrorCheck;
		this.cacheParameters		= cacheParameters;
		this.cacheStates			= cacheStates;
		this.manageStates			= manageStates;
		this.cacheProperties		= cacheProperties;
		this.bufferResizeMethod		= bufferResizeMethod;
		this.bindingSourceType		= bindingSourceType;
	}

	public boolean useDirectStateAccess() {
		return useDirectStateAccess;
	}

	public boolean useMultiBind() {
		return useMultiBind;
	}

	public boolean useBufferStorage() {
		return useBufferStorage;
	}

	public boolean useTextureStorage() {
		return useTextureStorage;
	}

	public boolean useTextureView() {
		return useTextureView;
	}

	public boolean useTextureAnisotropy() {
		return useTextureAnisotropy;
	}

	public boolean useAttribBinding() {
		return useAttribBinding;
	}

	public boolean useValidation() {
		return useValidation;
	}

	public boolean useErrorCheck() {
		return useErrorCheck;
	}

	public boolean useCacheParameters() {
		return cacheParameters;
	}

	public boolean useCacheStates() {
		return cacheStates;
	}

	public boolean useManageStates() {
		return manageStates;
	}

	public boolean useCacheProperties() {
		return cacheProperties;
	}

	public IResizeMethod getBufferResizeMethod() {
		return bufferResizeMethod;
	}

	public GLBindingSourceType getBindingSourceType() {
		return bindingSourceType;
	}

	public OpenGLDevice buildDevice() {
		return new OpenGLDevice(this);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private boolean				useDirectStateAccess;
		private boolean				useMultiBind;
		private boolean				useBufferStorage;
		private boolean				useTextureStorage;
		private boolean				useTextureView;
		private boolean				useTextureAnisotropy;
		private boolean				useAttribBinding;
		private boolean				useValidation;
		private boolean				useErrorCheck;
		private boolean				cacheParameters;
		private boolean				cacheStates;
		private boolean				manageStates;
		private boolean				cacheProperties;
		private IResizeMethod		bufferResizeMethod;
		private GLBindingSourceType	bindingSourceType;

		private Builder() {
			this.useDirectStateAccess	= true;
			this.useMultiBind			= true;
			this.useBufferStorage		= true;
			this.useTextureStorage		= true;
			this.useTextureView			= true;
			this.useTextureAnisotropy	= true;
			this.useAttribBinding		= true;
			this.useValidation			= false;
			this.useErrorCheck			= false;
			this.cacheParameters		= true;
			this.manageStates			= true;
			this.cacheStates			= true;
			this.cacheProperties		= true;
			this.bufferResizeMethod		= IResizeMethod			.POWER;
			this.bindingSourceType		= GLBindingSourceType	.CACHED;
		}

		public Builder useDirectStateAccess(boolean value) {
			this.useDirectStateAccess = value;
			return this;
		}

		public Builder useMultiBind(boolean value) {
			this.useMultiBind = value;
			return this;
		}

		public Builder useBufferStorage(boolean value) {
			this.useBufferStorage = value;
			return this;
		}

		public Builder useTextureStorage(boolean value) {
			this.useTextureStorage = value;
			return this;
		}

		public Builder useTextureView(boolean value) {
			this.useTextureView = value;
			return this;
		}

		public Builder useTextureAnisotropy(boolean value) {
			this.useTextureAnisotropy = value;
			return this;
		}

		public Builder useAttribBinding(boolean value) {
			this.useAttribBinding = value;
			return this;
		}

		public Builder useValidation(boolean value) {
			this.useValidation = value;
			return this;
		}

		public Builder useErrorCheck(boolean value) {
			this.useErrorCheck = value;
			return this;
		}

		public Builder useCacheParameters(boolean value) {
			this.cacheParameters = value;
			return this;
		}

		public Builder useCacheStates(boolean value) {
			this.cacheStates = value;
			return this;
		}

		public Builder useManageStates(boolean value) {
			this.manageStates = value;
			return this;
		}

		public Builder useCacheProperties(boolean value) {
			this.cacheProperties = value;
			return this;
		}

		public Builder withBufferResizeMethod(IResizeMethod value) {
			this.bufferResizeMethod = value;
			return this;
		}

		public Builder withBindingSourceType(GLBindingSourceType value) {
			this.bindingSourceType = value;
			return this;
		}

		public OpenGLDeviceCreateInfo build() {
			return new OpenGLDeviceCreateInfo(
					useDirectStateAccess,
					useMultiBind,
					useBufferStorage,
					useTextureStorage,
					useTextureView,
					useTextureAnisotropy,
					useAttribBinding,
					useValidation,
					useErrorCheck,
					cacheParameters,
					cacheStates,
					manageStates,
					cacheProperties,
					bufferResizeMethod,
					bindingSourceType
			);
		}
	}
}
