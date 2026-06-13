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

package com.github.argon4w.renderfox.opengl.function.parameter;

import com.github.argon4w.renderfox.opengl.function.IGLGlobalFunctions;

public interface IGLIndexSource {

	int getRange(IGLGlobalFunctions functions);

	static IGLIndexSource one() {
		return fixed(1);
	}

	static IGLIndexSource three() {
		return fixed(3);
	}

	static IGLIndexSource invalid() {
		return new Invalid();
	}

	static IGLIndexSource fixed(int range) {
		return new Fixed(range);
	}

	static IGLIndexSource query(IGLParameter parameter) {
		return new Query(parameter.getConstant());
	}

	class Invalid implements IGLIndexSource {

		@Override
		public int getRange(IGLGlobalFunctions functions) {
			return 0;
		}
	}

	record Fixed(int range) implements IGLIndexSource {

		@Override
		public int getRange(IGLGlobalFunctions functions) {
			return range;
		}
	}

	record Query(int parameter) implements IGLIndexSource {

		@Override
		public int getRange(IGLGlobalFunctions functions) {
			return functions.getInteger(parameter);
		}
	}
}
