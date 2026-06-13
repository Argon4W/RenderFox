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

package com.github.argon4w.renderfox.opengl.texture.sampler.object.feature;

import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.texture.function.parameter.GLTextureParameter;

public interface IGLSamplerParameter {

	void						setParameterInt		(GLTextureParameter parameter, int			value);
	void						setParameterFloat	(GLTextureParameter parameter, float		value);
	void						setParameterFloats	(GLTextureParameter parameter, IDataView<?>	dataView);
	void						setParameterRawInts	(GLTextureParameter parameter, IDataView<?>	dataView);
	int							getParameterInt		(GLTextureParameter parameter);
	float						getParameterFloat	(GLTextureParameter parameter);
	<T extends IDataView<?>> T	getParameterFloats	(GLTextureParameter parameter, T outDataView);
	<T extends IDataView<?>> T	getParameterRawInts	(GLTextureParameter parameter, T outDataView);
}
