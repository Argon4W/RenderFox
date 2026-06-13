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

package com.github.argon4w.renderfox.opengl.texture.pixel;

import com.github.argon4w.renderfox.data.IImageTransferInfo;
import com.github.argon4w.renderfox.data.coordinate.IOffset3D;
import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.data.view.wrapped.ImageDataView;
import com.github.argon4w.renderfox.opengl.format.GLDataType;
import com.github.argon4w.renderfox.opengl.format.GLFormat;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;

public class GLImageTransferInfo implements IImageTransferInfo {

	private final GLFormat			format;
	private final GLDataType		dataType;
	private final long				dataSize;
	private final long				pixelAlign;
	private final long				hostWidth;
	private final long				hostHeight;
	private final long				hostOffsetX;
	private final long				hostOffsetY;
	private final long				hostOffsetZ;
	private final long				deviceWidth;
	private final long				deviceHeight;
	private final long				deviceDepth;
	private final long				deviceOffsetX;
	private final long				deviceOffsetY;
	private final long				deviceOffsetZ;
	private final int				deviceMipLevel;

	public GLImageTransferInfo(
			GLFormat	format,
			GLDataType	dataType,
			long		pixelAlign,
			long		hostWidth,
			long		hostHeight,
			long		hostOffsetX,
			long		hostOffsetY,
			long		hostOffsetZ,
			long		deviceWidth,
			long		deviceHeight,
			long		deviceDepth,
			long		deviceOffsetX,
			long		deviceOffsetY,
			long		deviceOffsetZ,
			int			deviceMipLevel
	) {
		this.format			= format;
		this.dataType		= dataType;
		this.dataSize		= dataType.getSize();
		this.pixelAlign		= pixelAlign;
		this.hostWidth		= hostWidth;
		this.hostHeight		= hostHeight;
		this.hostOffsetX	= hostOffsetX;
		this.hostOffsetY	= hostOffsetY;
		this.hostOffsetZ	= hostOffsetZ;
		this.deviceWidth	= deviceWidth;
		this.deviceHeight	= deviceHeight;
		this.deviceDepth	= deviceDepth;
		this.deviceOffsetX	= deviceOffsetX;
		this.deviceOffsetY	= deviceOffsetY;
		this.deviceOffsetZ	= deviceOffsetZ;
		this.deviceMipLevel	= deviceMipLevel;
	}

	public Builder asBuilder() {
		return new Builder(
				format,
				dataType,
				pixelAlign,
				hostWidth,
				hostHeight,
				hostOffsetX,
				hostOffsetY,
				hostOffsetZ,
				deviceWidth,
				deviceHeight,
				deviceDepth,
				deviceOffsetX,
				deviceOffsetY,
				deviceOffsetZ,
				deviceMipLevel
		);
	}

	private long getHostSizePad() {
		return dataSize * (pixelAlign / dataSize * Math.ceilDiv(getHostSizeFit(), pixelAlign));
	}

	private long getHostSizeFit() {
		return dataSize * format.getComponentCount() * getWidth() * getHeight();
	}

	public long getHostSize() {
		return dataSize >= pixelAlign ? getHostSizeFit() : getHostSizePad();
	}

	public long getWidth() {
		return hostWidth == 0 ? deviceWidth : hostWidth;
	}

	public long getHeight() {
		return hostHeight == 0 ? deviceHeight : hostHeight;
	}

	@Override
	public long getRequiredSize() {
		return getHostSize() * (hostOffsetZ + deviceDepth);
	}

	@Override
	public ImageDataView decorate(IDataView<?> data) {
		return new ImageDataView(data, this);
	}

	@Override
	public long getComponentCount() {
		return format.getComponentCount();
	}

	@Override
	public long getComponentSize() {
		return dataSize;
	}

	@Override
	public long getPixelAlign() {
		return pixelAlign;
	}

	@Override
	public long getHostWidth() {
		return hostWidth;
	}

	@Override
	public long getHostHeight() {
		return hostHeight;
	}

	@Override
	public long getHostOffsetX() {
		return hostOffsetX;
	}

	@Override
	public long getHostOffsetY() {
		return hostOffsetY;
	}

	@Override
	public long getHostOffsetZ() {
		return hostOffsetZ;
	}

	@Override
	public long getDeviceWidth() {
		return deviceWidth;
	}

	@Override
	public long getDeviceHeight() {
		return deviceHeight;
	}

	public GLFormat getFormat() {
		return format;
	}

	public GLDataType getDataType() {
		return dataType;
	}

	public long getDeviceDepth() {
		return deviceDepth;
	}

	public long getDeviceOffsetX() {
		return deviceOffsetX;
	}

	public long getDeviceOffsetY() {
		return deviceOffsetY;
	}

	public long getDeviceOffsetZ() {
		return deviceOffsetZ;
	}

	public int getDeviceMipLevel() {
		return deviceMipLevel;
	}

	public long getDevicePixels() {
		return deviceWidth * deviceHeight * deviceDepth;
	}

	public long getPixelSize() {
		return getComponentCount() * getComponentSize();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private GLFormat	format;
		private GLDataType	dataType;
		private long		pixelAlign;
		private long		hostWidth;
		private long		hostHeight;
		private long		hostOffsetX;
		private long		hostOffsetY;
		private long		hostOffsetZ;
		private long		deviceWidth;
		private long		deviceHeight;
		private long		deviceDepth;
		private long		deviceOffsetX;
		private long		deviceOffsetY;
		private long		deviceOffsetZ;
		private int			deviceMipLevel;

		public Builder(
				GLFormat	format,
				GLDataType	dataType,
				long		pixelAlign,
				long		hostWidth,
				long		hostHeight,
				long		hostOffsetX,
				long		hostOffsetY,
				long		hostOffsetZ,
				long		deviceWidth,
				long		deviceHeight,
				long		deviceDepth,
				long		deviceOffsetX,
				long		deviceOffsetY,
				long		deviceOffsetZ,
				int			deviceMipLevel
		) {
			this.format			= format;
			this.dataType		= dataType;
			this.pixelAlign		= pixelAlign;
			this.hostWidth		= hostWidth;
			this.hostHeight		= hostHeight;
			this.hostOffsetX	= hostOffsetX;
			this.hostOffsetY	= hostOffsetY;
			this.hostOffsetZ	= hostOffsetZ;
			this.deviceWidth	= deviceWidth;
			this.deviceHeight	= deviceHeight;
			this.deviceDepth	= deviceDepth;
			this.deviceOffsetX	= deviceOffsetX;
			this.deviceOffsetY	= deviceOffsetY;
			this.deviceOffsetZ	= deviceOffsetZ;
			this.deviceMipLevel	= deviceMipLevel;
		}

		private Builder() {
			this.format			= GLFormat	.INVALID;
			this.dataType		= GLDataType.INVALID;
			this.pixelAlign		= 4;
			this.hostWidth		= 0;
			this.hostHeight		= 0;
			this.hostOffsetX	= 0;
			this.hostOffsetY	= 0;
			this.hostOffsetZ	= 0;
			this.deviceWidth	= 0;
			this.deviceHeight	= 0;
			this.deviceDepth	= 0;
			this.deviceOffsetX	= 0;
			this.deviceOffsetY	= 0;
			this.deviceOffsetZ	= 0;
			this.deviceMipLevel	= 0;
		}

		public Builder withDefault(GLInternalFormat format) {
			this.format		= format.getFormat		();
			this.dataType	= format.getFormatType	();
			this.pixelAlign	= format.getFormatSize	() % 4 == 0 ? 4 : 1;

			return this;
		}

		public Builder withFormat(GLInternalFormat format) {
			this.format = format.getFormat();
			return this;
		}

		public Builder withFormat(GLFormat format) {
			this.format = format;
			return this;
		}

		public Builder withType(GLDataType dataType) {
			this.dataType = dataType;
			return this;
		}

		public Builder withAlign(long pixelAlign) {
			this.pixelAlign = pixelAlign;
			return this;
		}

		public Builder withHostWidth(long hostWidth) {
			this.hostWidth = hostWidth;
			return this;
		}

		public Builder withHostHeight(long hostHeight) {
			this.hostHeight = hostHeight;
			return this;
		}

		public Builder withHostOffsetX(long hostOffsetX) {
			this.hostOffsetX = hostOffsetX;
			return this;
		}

		public Builder withHostOffsetY(long hostOffsetY) {
			this.hostOffsetY = hostOffsetY;
			return this;
		}

		public Builder withHostOffsetZ(long hostOffsetZ) {
			this.hostOffsetZ = hostOffsetZ;
			return this;
		}

		public Builder withDeviceWidth(long deviceWidth) {
			this.deviceWidth = deviceWidth;
			return this;
		}

		public Builder withDeviceHeight(long deviceHeight) {
			this.deviceHeight = deviceHeight;
			return this;
		}

		public Builder withDepth(long deviceDepth) {
			this.deviceDepth = deviceDepth;
			return this;
		}

		public Builder withDeviceOffsetX(long deviceOffsetX) {
			this.deviceOffsetX = deviceOffsetX;
			return this;
		}

		public Builder withDeviceOffsetY(long deviceOffsetY) {
			this.deviceOffsetY = deviceOffsetY;
			return this;
		}

		public Builder withDeviceOffsetZ(long deviceOffsetZ) {
			this.deviceOffsetZ = deviceOffsetZ;
			return this;
		}

		public Builder withMipLevel(int deviceMipLevel) {
			this.deviceMipLevel = deviceMipLevel;
			return this;
		}

		public Builder withWidth(long width) {
			this.hostWidth		= width;
			this.deviceWidth	= width;

			return this;
		}

		public Builder withHeight(long height) {
			this.hostHeight		= height;
			this.deviceHeight	= height;

			return this;
		}

		public Builder withHostExtent(IExtent3D extent) {
			this.hostWidth	= extent.getWidth	();
			this.hostHeight	= extent.getHeight	();

			return this;
		}

		public Builder withDeviceExtent(IExtent3D extent) {
			this.deviceWidth	= extent.getWidth	();
			this.deviceHeight	= extent.getHeight	();
			this.deviceDepth	= extent.getDepth	();

			return this;
		}

		public Builder withHostOffset(IOffset3D offset) {
			this.hostOffsetX = offset.getOffsetX();
			this.hostOffsetY = offset.getOffsetY();
			this.hostOffsetZ = offset.getOffsetZ();

			return this;
		}

		public Builder withDeviceOffset(IOffset3D offset) {
			this.deviceOffsetX = offset.getOffsetX();
			this.deviceOffsetY = offset.getOffsetY();
			this.deviceOffsetZ = offset.getOffsetZ();

			return this;
		}

		public Builder withExtent(IExtent3D extent) {
			this.deviceWidth	= extent.getWidth	();
			this.deviceHeight	= extent.getHeight	();
			this.deviceDepth	= extent.getDepth	();

			this.hostWidth		= extent.getWidth	();
			this.hostHeight		= extent.getHeight	();

			return this;
		}

		public Builder withOffset(IOffset3D offset) {
			this.hostOffsetX	= offset.getOffsetX();
			this.hostOffsetY	= offset.getOffsetY();
			this.hostOffsetZ	= offset.getOffsetZ();

			this.deviceOffsetX	= offset.getOffsetX();
			this.deviceOffsetY	= offset.getOffsetY();
			this.deviceOffsetZ	= offset.getOffsetZ();

			return this;
		}

		public GLImageTransferInfo build() {
			return new GLImageTransferInfo(
					format,
					dataType,
					pixelAlign,
					hostWidth,
					hostHeight,
					hostOffsetX,
					hostOffsetY,
					hostOffsetZ,
					deviceWidth,
					deviceHeight,
					deviceDepth,
					deviceOffsetX,
					deviceOffsetY,
					deviceOffsetZ,
					deviceMipLevel
			);
		}
	}
}
