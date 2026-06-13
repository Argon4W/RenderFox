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

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.texture.pixel.function.GLPixelFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.pixel.function.IGLPixelQueryFunctions;

import java.util.Stack;
import java.util.function.Supplier;

public class GLPixelStateManager {

	private final	Stack<PixelState>		stack;

	private			IGLPixelQueryFunctions	queryFunctions;
	private			GLPixelFunctionsHelper	helperFunctions;
	private			boolean					manageStates;

	public GLPixelStateManager() {
		this.stack				= new Stack<>();

		this.queryFunctions		= null;
		this.helperFunctions	= null;
		this.manageStates		= false;
	}

	public void initialize(OpenGLDevice device) {
		queryFunctions	= device.getGlobalContext	().getGlobalHelper		();
		helperFunctions	= device.getTextureContext	().getGlobalPixelHelper	();
		manageStates	= device.getCreateInfo		().useManageStates		();
	}

	public void runPackScoped(GLImageTransferInfo info, Runnable runnable) {
		if (runnable == null) {
			throw new NullPointerException("Runnable cannot be null.");
		}

		if (info == null) {
			throw new NullPointerException("Info cannot be null.");
		}

		pushPixelState();

		helperFunctions.setPackSwapBytes	(false);
		helperFunctions.setPackLSBFirst		(false);
		helperFunctions.setPackRowLength	((int) info.getHostWidth	());
		helperFunctions.setPackImageHeight	((int) info.getHostHeight	());
		helperFunctions.setPackSkipPixels	((int) info.getHostOffsetX	());
		helperFunctions.setPackSkipRows		((int) info.getHostOffsetY	());
		helperFunctions.setPackSkipImages	((int) info.getHostOffsetZ	());
		helperFunctions.setPackAlignment	((int) info.getPixelAlign	());

		runnable.run();

		popPixelState();
	}

	public <T> T runPackScoped(GLImageTransferInfo info, Supplier<T> supplier) {
		if (supplier == null) {
			throw new NullPointerException("Supplier cannot be null.");
		}

		if (info == null) {
			throw new NullPointerException("Info cannot be null.");
		}

		pushPixelState();

		helperFunctions.setPackSwapBytes	(false);
		helperFunctions.setPackLSBFirst		(false);
		helperFunctions.setPackRowLength	((int) info.getHostWidth	());
		helperFunctions.setPackImageHeight	((int) info.getHostHeight	());
		helperFunctions.setPackSkipPixels	((int) info.getHostOffsetX	());
		helperFunctions.setPackSkipRows		((int) info.getHostOffsetY	());
		helperFunctions.setPackSkipImages	((int) info.getHostOffsetZ	());
		helperFunctions.setPackAlignment	((int) info.getPixelAlign	());

		var result = supplier.get();

		popPixelState();

		return result;
	}

	public void runUnpackScoped(GLImageTransferInfo info, Runnable runnable) {
		if (runnable == null) {
			throw new NullPointerException("Runnable cannot be null.");
		}

		if (info == null) {
			throw new NullPointerException("Info cannot be null.");
		}

		pushPixelState();

		helperFunctions.setUnpackSwapBytes	(false);
		helperFunctions.setUnpackLSBFirst	(false);
		helperFunctions.setUnpackRowLength	((int) info.getHostWidth	());
		helperFunctions.setUnpackImageHeight((int) info.getHostHeight	());
		helperFunctions.setUnpackSkipPixels	((int) info.getHostOffsetX	());
		helperFunctions.setUnpackSkipRows	((int) info.getHostOffsetY	());
		helperFunctions.setUnpackSkipImages	((int) info.getHostOffsetZ	());
		helperFunctions.setUnpackAlignment	((int) info.getPixelAlign	());

		runnable.run();

		popPixelState();
	}

	public <T> T runUnpackScoped(GLImageTransferInfo info, Supplier<T> supplier) {
		if (supplier == null) {
			throw new NullPointerException("Supplier cannot be null.");
		}

		if (info == null) {
			throw new NullPointerException("Info cannot be null.");
		}

		pushPixelState();

		helperFunctions.setUnpackSwapBytes	(false);
		helperFunctions.setUnpackLSBFirst	(false);
		helperFunctions.setUnpackRowLength	((int) info.getHostWidth	());
		helperFunctions.setUnpackImageHeight((int) info.getHostHeight	());
		helperFunctions.setUnpackSkipPixels	((int) info.getHostOffsetX	());
		helperFunctions.setUnpackSkipRows	((int) info.getHostOffsetY	());
		helperFunctions.setUnpackSkipImages	((int) info.getHostOffsetZ	());
		helperFunctions.setUnpackAlignment	((int) info.getPixelAlign	());

		var result = supplier.get();

		popPixelState();

		return result;
	}

	public PixelState capturePixelState() {
		return new PixelState();
	}

	public void pushPixelState() {
		if (manageStates) {
			stack.push(new PixelState());
		}
	}

	public void popPixelState() {
		if (stack.isEmpty()) {
			throw new IllegalStateException("No pixel states are recorded.");
		}

		if (manageStates) {
			stack.pop().restore();
		}
	}

	public class PixelState {

		private final boolean	packSwapBytes;
		private final boolean	packLSBFirst;
		private final int		packRowLength;
		private final int		packImageHeight;
		private final int		packSkipRows;
		private final int		packSkipPixels;
		private final int		packSkipImages;
		private final int		packAlignment;

		private final boolean	unpackSwapBytes;
		private final boolean	unpackLSBFirst;
		private final int		unpackRowLength;
		private final int		unpackImageHeight;
		private final int		unpackSkipRows;
		private final int		unpackSkipPixels;
		private final int		unpackSkipImages;
		private final int		unpackAlignment;

		public PixelState() {
			packSwapBytes		= queryFunctions.getPackSwapBytes		();
			packLSBFirst		= queryFunctions.getPackLSBFirst		();
			packRowLength		= queryFunctions.getPackRowLength		();
			packImageHeight		= queryFunctions.getPackImageHeight		();
			packSkipRows		= queryFunctions.getPackSkipRows		();
			packSkipPixels		= queryFunctions.getPackSkipPixels		();
			packSkipImages		= queryFunctions.getPackSkipImages		();
			packAlignment		= queryFunctions.getPackAlignment		();

			unpackSwapBytes		= queryFunctions.getUnpackSwapBytes		();
			unpackLSBFirst		= queryFunctions.getUnpackLSBFirst		();
			unpackRowLength		= queryFunctions.getUnpackRowLength		();
			unpackImageHeight	= queryFunctions.getUnpackImageHeight	();
			unpackSkipRows		= queryFunctions.getUnpackSkipRows		();
			unpackSkipPixels	= queryFunctions.getUnpackSkipPixels	();
			unpackSkipImages	= queryFunctions.getUnpackSkipImages	();
			unpackAlignment		= queryFunctions.getUnpackAlignment		();
		}

		public void restore() {
			helperFunctions.setPackSwapBytes	(packSwapBytes);
			helperFunctions.setPackLSBFirst		(packLSBFirst);
			helperFunctions.setPackRowLength	(packRowLength);
			helperFunctions.setPackImageHeight	(packImageHeight);
			helperFunctions.setPackSkipRows		(packSkipRows);
			helperFunctions.setPackSkipPixels	(packSkipPixels);
			helperFunctions.setPackSkipImages	(packSkipImages);
			helperFunctions.setPackAlignment	(packAlignment);

			helperFunctions.setUnpackSwapBytes	(unpackSwapBytes);
			helperFunctions.setUnpackLSBFirst	(unpackLSBFirst);
			helperFunctions.setUnpackRowLength	(unpackRowLength);
			helperFunctions.setUnpackImageHeight(unpackImageHeight);
			helperFunctions.setUnpackSkipRows	(unpackSkipRows);
			helperFunctions.setUnpackSkipPixels	(unpackSkipPixels);
			helperFunctions.setUnpackSkipImages	(unpackSkipImages);
			helperFunctions.setUnpackAlignment	(unpackAlignment);
		}

		public GLImageTransferInfo asUnpackTransfer() {
			return GLImageTransferInfo
					.builder			()
					.withAlign			(unpackAlignment)
					.withHostWidth		(unpackRowLength)
					.withHostHeight		(unpackImageHeight)
					.withHostOffsetX	(unpackSkipPixels)
					.withHostOffsetY	(unpackSkipRows)
					.withHostOffsetZ	(unpackSkipImages)
					.build				();
		}

		public GLImageTransferInfo asPackTransfer() {
			return GLImageTransferInfo
					.builder			()
					.withAlign			(packAlignment)
					.withHostWidth		(packRowLength)
					.withHostHeight		(packImageHeight)
					.withHostOffsetX	(packSkipPixels)
					.withHostOffsetY	(packSkipRows)
					.withHostOffsetZ	(packSkipImages)
					.build				();
		}
	}
}
