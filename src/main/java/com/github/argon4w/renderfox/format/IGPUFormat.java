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

package com.github.argon4w.renderfox.format;

public interface IGPUFormat<T extends IDataType> extends IFormat<T> {

	T				getFormatType	();
	long			getFormatSize	();
	boolean			isNormalized	();
	boolean			isSigned		();
	boolean			isSRGB			();

	interface Lookup {

		Lookup			reset				();
		Lookup			fromCommonGPUFormat	();
		Lookup			fromFormat			(IFormat	<?>	format);
		Lookup			fromGPUFormat		(IGPUFormat	<?>	gpuFormat);
		Lookup			withSize			(int			size);
		Lookup			withSRGB			();
		Lookup			withoutSRGB			();
		Lookup			withSigned			();
		Lookup			withUnsigned		();
		Lookup			withNormalized		();
		Lookup			withUnnormalized	();
		Lookup			withInteger			();
		Lookup			withFloatingPoint	();
		Lookup			withRed				();
		Lookup			withoutRed			();
		Lookup			withGreen			();
		Lookup			withoutGreen		();
		Lookup			withBlue			();
		Lookup			withoutBlue			();
		Lookup			withAlpha			();
		Lookup			withoutAlpha		();
		Lookup			withDepth			();
		Lookup			withoutDepth		();
		Lookup			withStencil			();
		Lookup			withoutStencil		();
		Lookup			withDepthStencil	();
		Lookup			withStencilStencil	();
		Lookup			withRGB				();
		Lookup			withoutRGB			();
		Lookup			withRGBA			();
		Lookup			withoutRGBA			();
		Lookup			withRGBInteger		();
		Lookup			withoutRGBInteger	();
		Lookup			withRGBAInteger		();
		Lookup			withoutRGBAInteger	();
		Lookup			copy				();
		IGPUFormat<?>	find				();
	}
}
