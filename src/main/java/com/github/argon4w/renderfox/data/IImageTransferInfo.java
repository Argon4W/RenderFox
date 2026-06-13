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

package com.github.argon4w.renderfox.data;

import com.github.argon4w.renderfox.data.view.IDataViewDecorator;
import com.github.argon4w.renderfox.data.view.wrapped.ImageDataView;

public interface IImageTransferInfo extends IDataViewDecorator<ImageDataView> {

	long getRequiredSize	();
	long getComponentCount	();
	long getComponentSize	();
	long getPixelAlign		();
	long getDeviceWidth		();
	long getDeviceHeight	();
	long getHostWidth		();
	long getHostHeight		();
	long getHostOffsetX		();
	long getHostOffsetY		();
	long getHostOffsetZ		();
}
