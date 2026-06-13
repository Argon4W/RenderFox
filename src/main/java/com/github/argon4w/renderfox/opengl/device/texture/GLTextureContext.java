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

package com.github.argon4w.renderfox.opengl.device.texture;

import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.device.texture.sampler.ISamplerCreator;
import com.github.argon4w.renderfox.opengl.device.texture.sampler.SamplerCreatorAnisotropy;
import com.github.argon4w.renderfox.opengl.device.texture.sampler.SamplerCreatorLegacy;
import com.github.argon4w.renderfox.opengl.device.texture.storage.ITextureStorage;
import com.github.argon4w.renderfox.opengl.device.texture.storage.TextureStorageARB;
import com.github.argon4w.renderfox.opengl.device.texture.storage.TextureStorageLegacy;
import com.github.argon4w.renderfox.opengl.device.texture.view.ITextureView;
import com.github.argon4w.renderfox.opengl.device.texture.view.TextureViewARB;
import com.github.argon4w.renderfox.opengl.device.texture.view.TextureViewLegacy;
import com.github.argon4w.renderfox.opengl.texture.GLTextureStateManager;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.function.*;
import com.github.argon4w.renderfox.opengl.texture.object.raw.GLRawTexture;
import com.github.argon4w.renderfox.opengl.texture.pixel.GLPixelStateManager;
import com.github.argon4w.renderfox.opengl.texture.pixel.function.*;
import com.github.argon4w.renderfox.opengl.texture.sampler.GLSamplerStateManager;
import com.github.argon4w.renderfox.opengl.texture.sampler.functions.*;
import com.github.argon4w.renderfox.opengl.texture.sampler.object.GLRawSampler;

public class GLTextureContext {

	private final OpenGLDevice				device;
	private final GLTextureStateManager		textureStateManager;
	private final GLPixelStateManager		pixelStateManager;
	private final GLSamplerStateManager		samplerStateManager;
	private final ITextureStorage			textureStorage;
	private final ITextureView				textureView;
	private final ISamplerCreator			samplerCreator;
	private final IGLTextureFunctions		textureFunctions;
	private final IGLPixelFunctions			pixelFunctions;
	private final IGLSamplerFunctions		samplerFunctions;
	private final GLTextureFunctionsHelper	textureHelper;
	private final GLPixelFunctionsHelper	pixelHelper;
	private final GLSamplerFunctionsHelper	samplerHelper;

	public GLTextureContext(OpenGLDevice device) {
		this.device					= device;
		this.textureStateManager	= new GLTextureStateManager	();
		this.pixelStateManager		= new GLPixelStateManager	();
		this.samplerStateManager	= new GLSamplerStateManager	();
		this.textureStorage			= createTextureStorage		();
		this.textureView			= createTextureView			();
		this.samplerCreator			= createSamplerCreator		();
		this.textureFunctions		= createTextureFunctions	();
		this.pixelFunctions			= createPixelFunctions		();
		this.samplerFunctions		= createSamplerFunctions	();
		this.textureHelper			= createTextureHelper		();
		this.pixelHelper			= createPixelHelper			();
		this.samplerHelper			= createSamplerHelper		();
	}

	public void initialize() {
		textureStateManager	.initialize(device);
		pixelStateManager	.initialize(device);
		samplerStateManager	.initialize(device);
		textureFunctions	.initialize(device);
		pixelFunctions		.initialize(device);
		samplerFunctions	.initialize(device);
	}

	private IGLTextureFunctions createTextureFunctions() {
		var textureFunctions = GLTextureFunctionsDirect.of();

		textureFunctions = device.getCreateInfo().useCacheStates() ? new GLTextureFunctionsCached		(textureFunctions) : textureFunctions;
		textureFunctions = device.getCreateInfo().useErrorCheck	() ? new GLTextureFunctionsErrorCheck	(textureFunctions) : textureFunctions;
		textureFunctions = device.getCreateInfo().useValidation	() ? new GLTextureFunctionsValidation	(textureFunctions) : textureFunctions;

		return textureFunctions;
	}

	public IGLPixelFunctions createPixelFunctions() {
		var pixelFunctions = GLPixelFunctionsDirect.of();

		pixelFunctions = device.getCreateInfo().useCacheStates	() ? new GLPixelFunctionsCached		(pixelFunctions) : pixelFunctions;
		pixelFunctions = device.getCreateInfo().useErrorCheck	() ? new GLPixelFunctionsErrorCheck	(pixelFunctions) : pixelFunctions;
		pixelFunctions = device.getCreateInfo().useValidation	() ? new GLPixelFunctionsValidation	(pixelFunctions) : pixelFunctions;

		return pixelFunctions;
	}

	public IGLSamplerFunctions createSamplerFunctions() {
		var samplerFunctions = GLSamplerFunctionsDirect.of();

		samplerFunctions = device.getCreateInfo().useCacheStates() ? new GLSamplerFunctionsCached		(samplerFunctions) : samplerFunctions;
		samplerFunctions = device.getCreateInfo().useErrorCheck	() ? new GLSamplerFunctionsErrorCheck	(samplerFunctions) : samplerFunctions;
		samplerFunctions = device.getCreateInfo().useValidation	() ? new GLSamplerFunctionsValidation	(samplerFunctions) : samplerFunctions;

		return samplerFunctions;
	}

	private ITextureStorage createTextureStorage() {
		return device.useTextureStorage() ? new TextureStorageARB(this) : new TextureStorageLegacy(this);
	}

	private ITextureView createTextureView() {
		return device.useTextureView() ? new TextureViewARB(this) : new TextureViewLegacy(this);
	}

	public ISamplerCreator createSamplerCreator() {
		return device.useTextureAnisotropy() ? new SamplerCreatorAnisotropy(this) : new SamplerCreatorLegacy(this);
	}

	public int createTextureHandle(GLTextureType textureType) {
		return textureFunctions.createTexture(textureType.getConstant(), textureType);
	}

	public int createSamplerHandle() {
		return samplerFunctions.createSampler();
	}

	public GLTextureFunctionsHelper createTextureHelper() {
		return new GLTextureFunctionsHelper(textureFunctions);
	}

	public GLPixelFunctionsHelper createPixelHelper() {
		return new GLPixelFunctionsHelper(pixelFunctions);
	}

	public GLSamplerFunctionsHelper createSamplerHelper() {
		return new GLSamplerFunctionsHelper(samplerFunctions);
	}

	public GLTextureFunctionsHelper createTextureHelper(GLTextureType textureType, int textureHandle) {
		return new GLTextureFunctionsHelper(textureFunctions).setTexture(textureHandle, textureType);
	}

	public GLSamplerFunctionsHelper createSamplerHelper(int samplerHandle) {
		return new GLSamplerFunctionsHelper(samplerFunctions).setSampler(samplerHandle);
	}

	public GLRawTexture createRawTexture(GLTextureType textureType) {
		return new GLRawTexture(this, textureType);
	}

	public GLRawSampler createRawSampler() {
		return new GLRawSampler(this);
	}

	public GLTextureStateManager getTextureStateManager() {
		return textureStateManager;
	}

	public GLPixelStateManager getPixelStateManager() {
		return pixelStateManager;
	}

	public GLSamplerStateManager getSamplerStateManager() {
		return samplerStateManager;
	}

	public IGLTextureFunctions getTextureFunctions() {
		return textureFunctions;
	}

	public IGLPixelFunctions getPixelFunctions() {
		return pixelFunctions;
	}

	public IGLSamplerFunctions getSamplerFunctions() {
		return samplerFunctions;
	}

	public ITextureStorage getTextureCreator() {
		return textureStorage;
	}

	public ITextureView getTextureViewCreator() {
		return textureView;
	}

	public ISamplerCreator getSamplerCreator() {
		return samplerCreator;
	}

	public GLTextureFunctionsHelper getGlobalTextureHelper() {
		return textureHelper;
	}

	public GLPixelFunctionsHelper getGlobalPixelHelper() {
		return pixelHelper;
	}

	public GLSamplerFunctionsHelper getGlobalSamplerHelper() {
		return samplerHelper;
	}

	public OpenGLDevice getDevice() {
		return device;
	}
}
