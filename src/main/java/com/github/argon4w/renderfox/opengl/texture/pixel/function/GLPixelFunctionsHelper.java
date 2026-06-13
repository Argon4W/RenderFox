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

package com.github.argon4w.renderfox.opengl.texture.pixel.function;

import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelParameter;
import org.lwjgl.opengl.GL11;

public class GLPixelFunctionsHelper {

	private final IGLPixelFunctions pixelFunctions;

	public GLPixelFunctionsHelper(IGLPixelFunctions pixelFunctions) {
		this.pixelFunctions = pixelFunctions;
	}

	public void setStorageMode(GLPixelParameter parameter, int value) {
		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if (parameter == GLPixelParameter.INVALID) {
			throw new IllegalArgumentException("Invalid parameter.");
		}

		pixelFunctions.pixelStore(parameter.getConstant(), value);
	}

	public void setPackSwapBytes(boolean value) {
		setStorageMode(GLPixelParameter.PACK_SWAP_BYTES, value ? GL11.GL_TRUE : GL11.GL_FALSE);
	}

	public void setPackLSBFirst(boolean value) {
		setStorageMode(GLPixelParameter.PACK_LSB_FIRST, value ? GL11.GL_TRUE : GL11.GL_FALSE);
	}

	public void setPackRowLength(int value) {
		setStorageMode(GLPixelParameter.PACK_ROW_LENGTH, value);
	}

	public void setPackImageHeight(int value) {
		setStorageMode(GLPixelParameter.PACK_IMAGE_HEIGHT, value);
	}

	public void setPackSkipRows(int value) {
		setStorageMode(GLPixelParameter.PACK_SKIP_ROWS, value);
	}

	public void setPackSkipPixels(int value) {
		setStorageMode(GLPixelParameter.PACK_SKIP_PIXELS, value);
	}

	public void setPackSkipImages(int value) {
		setStorageMode(GLPixelParameter.PACK_SKIP_IMAGES, value);
	}

	public void setPackAlignment(int value) {
		setStorageMode(GLPixelParameter.PACK_ALIGNMENT, value);
	}

	public void setUnpackSwapBytes(boolean value) {
		setStorageMode(GLPixelParameter.UNPACK_SWAP_BYTES, value ? GL11.GL_TRUE : GL11.GL_FALSE);
	}

	public void setUnpackLSBFirst(boolean value) {
		setStorageMode(GLPixelParameter.UNPACK_LSB_FIRST, value ? GL11.GL_TRUE : GL11.GL_FALSE);
	}

	public void setUnpackRowLength(int value) {
		setStorageMode(GLPixelParameter.UNPACK_ROW_LENGTH, value);
	}

	public void setUnpackImageHeight(int value) {
		setStorageMode(GLPixelParameter.UNPACK_IMAGE_HEIGHT, value);
	}

	public void setUnpackSkipRows(int value) {
		setStorageMode(GLPixelParameter.UNPACK_SKIP_ROWS, value);
	}

	public void setUnpackSkipPixels(int value) {
		setStorageMode(GLPixelParameter.UNPACK_SKIP_PIXELS, value);
	}

	public void setUnpackSkipImages(int value) {
		setStorageMode(GLPixelParameter.UNPACK_SKIP_IMAGES, value);
	}

	public void setUnpackAlignment(int value) {
		setStorageMode(GLPixelParameter.UNPACK_ALIGNMENT, value);
	}
}
