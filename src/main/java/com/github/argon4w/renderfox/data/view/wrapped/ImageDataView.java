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

package com.github.argon4w.renderfox.data.view.wrapped;

import com.github.argon4w.renderfox.data.IImageTransferInfo;
import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.format.ColorABGR;

public class ImageDataView extends DataViewWrapper<ImageDataView> {

	private final IDataView<?>	dataView;
	private final long			pixelBytes;
	private final long			alignedWidthBytes;
	private final long			alignedHeightBytes;
	private final long			offsetX;
	private final long			offsetY;
	private final long			offsetZ;

	public ImageDataView(IDataView<?> dataView, IImageTransferInfo imageData) {
		var componentCount	= imageData.getComponentCount	();
		var componentSize	= imageData.getComponentSize	();
		var pixelAlign		= imageData.getPixelAlign		();
		var width			= imageData.getHostWidth		();
		var height			= imageData.getHostHeight		();

		width	= width		== 0 ? imageData.getDeviceWidth	() : width;
		height	= height	== 0 ? imageData.getDeviceHeight() : height;

		if (width == 0 || height == 0) {
			throw new IllegalArgumentException("Cannot create image data view for a tightly packed device-to-host image transfer info.");
		}

		this.dataView	= dataView;
		this.pixelBytes	= componentSize * componentCount;
		this.offsetX	= imageData.getHostOffsetX();
		this.offsetY	= imageData.getHostOffsetY();
		this.offsetZ	= imageData.getHostOffsetZ();

		if (componentSize >= pixelAlign) {
			this.alignedWidthBytes	= componentSize * (componentCount * width);
			this.alignedHeightBytes	= componentSize * (componentCount * width * height);
		} else {
			this.alignedWidthBytes	= componentSize * (pixelAlign / componentSize * Math.ceilDiv(this.pixelBytes * width,			pixelAlign));
			this.alignedHeightBytes	= componentSize * (pixelAlign / componentSize * Math.ceilDiv(this.pixelBytes * width * height,	pixelAlign));
		}
	}

	@Override
	public IDataView<?> getDataView() {
		return dataView;
	}

	private long getTexelPosition(
			long texelX,
			long texelY,
			long texelZ
	) {
		return		(offsetZ + texelZ) * alignedHeightBytes
				+	(offsetY + texelY) * alignedWidthBytes
				+	(offsetX + texelX) * pixelBytes;
	}

	public byte getTexelByte(
			long texelX,
			long texelY,
			long componentOffset
	) {
		return getTexelByte(
				texelX,
				texelY,
				0,
				componentOffset
		);
	}

	public byte getTexelByte(
			long texelX,
			long texelY,
			long texelZ,
			long componentOffset
	) {
		return getByte(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset);
	}

	public short getTexelShort(
			long texelX,
			long texelY,
			long componentOffset
	) {
		return getTexelShort(
				texelX,
				texelY,
				0,
				componentOffset
		);
	}

	public short getTexelShort(
			long texelX,
			long texelY,
			long texelZ,
			long componentOffset
	) {
		return getShort(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset);
	}

	public int getTexelInt(
			long texelX,
			long texelY,
			long componentOffset
	) {
		return getTexelInt(
				texelX,
				texelY,
				0,
				componentOffset
		);
	}

	public int getTexelInt(
			long texelX,
			long texelY,
			long texelZ,
			long componentOffset
	) {
		return getInt(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset);
	}

	public float getTexelFloat(
			long texelX,
			long texelY,
			long componentOffset
	) {
		return getTexelFloat(
				texelX,
				texelY,
				0,
				componentOffset
		);
	}

	public float getTexelFloat(
			long texelX,
			long texelY,
			long texelZ,
			long componentOffset
	) {
		return getFloat(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset);
	}

	public ImageDataView putTexelByte(
			long texelX,
			long texelY,
			long componentOffset,
			byte value
	) {
		return putTexelByte(
				texelX,
				texelY,
				0,
				componentOffset,
				value
		);
	}

	public ImageDataView putTexelByte(
			long texelX,
			long texelY,
			long texelZ,
			long componentOffset,
			byte value
	) {
		return putByte(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset, value);
	}

	public ImageDataView putTexelShort(
			long	texelX,
			long	texelY,
			long	componentOffset,
			short	value
	) {
		return putTexelShort(
				texelX,
				texelY,
				0,
				componentOffset,
				value
		);
	}

	public ImageDataView putTexelShort(
			long	texelX,
			long	texelY,
			long	texelZ,
			long	componentOffset,
			short	value
	) {
		return putShort(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset, value);
	}

	public ImageDataView putTexelInt(
			long	texelX,
			long	texelY,
			long	componentOffset,
			int		value
	) {
		return putTexelInt(
				texelX,
				texelY,
				0,
				componentOffset,
				value
		);
	}

	public ImageDataView putTexelInt(
			long	texelX,
			long	texelY,
			long	texelZ,
			long	componentOffset,
			int		value
	) {
		return putInt(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset, value);
	}

	public ImageDataView putTexelFloat(
			long	texelX,
			long	texelY,
			long	componentOffset,
			float	value
	) {
		return putTexelFloat(
				texelX,
				texelY,
				0,
				componentOffset,
				value
		);
	}

	public ImageDataView putTexelFloat(
			long	texelX,
			long	texelY,
			long	texelZ,
			long	componentOffset,
			float	value
	) {
		return putFloat(getTexelPosition(
				texelX,
				texelY,
				texelZ
		) + componentOffset, value);
	}

	public ImageDataView putTexelRGBA8(
			long	texelX,
			long	texelY,
			int		colorRed,
			int		colorGreen,
			int		colorBlue,
			int		colorAlpha
	) {
		return putTexelRGBA8(
				texelX,
				texelY,
				0,
				colorRed,
				colorGreen,
				colorBlue,
				colorAlpha
		);
	}

	public ImageDataView putTexelRGBA8(
			long	texelX,
			long	texelY,
			long	texelZ,
			int		colorRed,
			int		colorGreen,
			int		colorBlue,
			int		colorAlpha
	) {
		return putTexelInt(
				texelX,
				texelY,
				texelZ,
				0,
				ColorABGR.of(
						colorAlpha,
						colorBlue,
						colorGreen,
						colorRed
				)
		);
	}
}
