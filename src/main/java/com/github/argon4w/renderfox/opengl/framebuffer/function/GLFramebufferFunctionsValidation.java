package com.github.argon4w.renderfox.opengl.framebuffer.function;

import com.github.argon4w.renderfox.data.UnionValue;
import com.github.argon4w.renderfox.data.view.DataViews;
import com.github.argon4w.renderfox.opengl.device.OpenGLDevice;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferClearBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferStatus;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferAttachmentParameter;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.GLFramebufferParameter;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag.GLFramebufferWriteBufferBit;
import com.github.argon4w.renderfox.opengl.function.helper.IGLGlobalFunctionsHelper;
import com.github.argon4w.renderfox.opengl.texture.GLTextureType;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponentType;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLTextureFilter;
import com.github.argon4w.renderfox.opengl.texture.function.GLTextureFunctionsHelper;
import com.github.argon4w.renderfox.util.MathUtils;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;

public class GLFramebufferFunctionsValidation implements IGLFramebufferFunctions {

	private final	IGLFramebufferFunctions			framebufferFunctions;
	private			IGLGlobalFunctionsHelper		helperFunctions;
	private			GLFramebufferFunctionsHelper	framebufferHelper;
	private			GLTextureFunctionsHelper		textureHelper;

	public GLFramebufferFunctionsValidation(IGLFramebufferFunctions framebufferFunctions) {
		this.framebufferFunctions	= framebufferFunctions;

		this.helperFunctions		= null;
		this.framebufferHelper		= null;
		this.textureHelper			= null;
	}

	@Override
	public void initialize(OpenGLDevice device) {
		framebufferFunctions.initialize(device);

		helperFunctions		= device.getGlobalContext		().getGlobalHelper				();
		framebufferHelper	= device.getFramebufferContext	().getGlobalFramebufferHelper	();
		textureHelper		= device.getTextureContext		().getGlobalTextureHelper		();
	}

	@Override
	public int createFramebuffer() {
		return framebufferFunctions.createFramebuffer();
	}

	@Override
	public void framebufferParameteri(
			int framebufferHandle,
			int framebufferParameter,
			int framebufferParameterValue
	) {
		var parameter = GLFramebufferParameter.fromConstant(framebufferParameter);

		if (framebufferHandle == 0) {
			throw new IllegalArgumentException("Zero the default framebuffer is not an acceptable framebuffer object.");
		}

		if (!framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not the name of an existing framebuffer object.");
		}

		if (parameter == GLFramebufferParameter.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferParameter.");
		}

		if (parameter.isReadOnly()) {
			throw new IllegalArgumentException("FramebufferParameter is read-only.");
		}

		if (!parameter.getValues().isValidValue(helperFunctions, new UnionValue(framebufferParameterValue))) {
			throw new IllegalArgumentException("FramebufferParameterValue should have a defined constant value (base on teh value of framebufferParameter) and does not.");
		}

		framebufferFunctions.framebufferParameteri(
				framebufferHandle,
				framebufferParameter,
				framebufferParameterValue
		);
	}

	@Override
	public void framebufferTexture(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				textureHandle,
			GLTextureType	textureType,
			int				textureMipLevel
	) {
		var attachment = GLFramebufferAttachment.fromConstant(framebufferAttachment);

		if (framebufferHandle == 0) {
			throw new IllegalArgumentException("Zero the default framebuffer is not an acceptable framebuffer object.");
		}

		if (!framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not the name of an existing framebuffer object.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferAttachment.");
		}

		if (attachment == GLFramebufferAttachment.NONE) {
			throw new IllegalArgumentException("FramebufferAttachment is not one of the accepted attachment points.");
		}

		if (!attachment.isFramebuffer()) {
			throw new IllegalArgumentException("FramebufferAttachment is not one of the accepted attachment points.");
		}

		if (textureHandle != 0) {
			if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
				throw new IllegalArgumentException("TextureHandle is not zero or the name of an existing texture object.");
			}

			if (textureType.hasMipmap() && textureMipLevel != 0) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (!textureType.isComplete()) {
				throw new IllegalArgumentException("TextureHandle is not zero and the effective target of the textureHandle is not complete.");
			}

			if (textureMipLevel < 0) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureMipLevel > MathUtils.log2(helperFunctions.getMax3DTextureSize()) && textureType == GLTextureType.TEXTURE_3D) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxCubeMapTextureSize()) && textureType.isCube()) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}
		}

		framebufferFunctions.framebufferTexture(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureType,
				textureMipLevel
		);
	}

	@Override
	public void framebufferTextureLayer(
			int				framebufferHandle,
			int				framebufferAttachment,
			int				textureHandle,
			GLTextureType	textureType,
			int				textureMipLevel,
			int				textureLayer
	) {
		var attachment = GLFramebufferAttachment.fromConstant(framebufferAttachment);

		if (framebufferHandle == 0) {
			throw new IllegalArgumentException("Zero the default framebuffer is not an acceptable framebuffer object.");
		}

		if (!framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not the name of an existing framebuffer object.");
		}

		if (attachment == GLFramebufferAttachment.NONE) {
			throw new IllegalArgumentException("Invalid framebufferAttachment.");
		}

		if (!attachment.isFramebuffer()) {
			throw new IllegalArgumentException("FramebufferAttachment is not one of the accepted attachment points.");
		}

		if (textureHandle != 0) {
			if (!textureHelper.setTexture(textureHandle, textureType).isTexture()) {
				throw new IllegalArgumentException("TextureHandle is not zero or the name of an existing texture object.");
			}

			if (!textureType.isComplete()) {
				throw new IllegalArgumentException("TextureHandle is not zero and the effective target of the textureHandle is not complete.");
			}

			if (textureType != GLTextureType.TEXTURE_3D && !textureType.isArray() && !textureType.isCube()) {
				throw new IllegalArgumentException("TextureHandle is not zero and the effective target of the textureHandle is not one of the acceptable texture target.");
			}

			if (textureType.hasMipmap() && textureMipLevel != 0) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureMipLevel < 0) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureMipLevel > MathUtils.log2(helperFunctions.getMax3DTextureSize()) && textureType == GLTextureType.TEXTURE_3D) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxCubeMapTextureSize()) && textureType.isCube()) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureMipLevel > MathUtils.log2(helperFunctions.getMaxTextureSize())) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureMipLevel is not a support texture level for textureHandle.");
			}

			if (textureLayer < 0) {
				throw new IllegalArgumentException("TextureHandle is not zero and the framebufferLayer is negative.");
			}

			if (textureLayer > helperFunctions.getMax3DTextureSize() - 1 && textureType == GLTextureType.TEXTURE_3D) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureLayer is larger than the value of GL_MAX_3D_TEXTURE_SIZE minus one for three-dimensional texture objects.");
			}

			if (textureLayer > helperFunctions.getMaxArrayTextureLayers() - 1 && (textureType.isArray() || textureType.isCube())) {
				throw new IllegalArgumentException("TextureHandle is not zero and textureLayer is larger than the value of GL_MAX_ARRAY_TEXTURE_LAYERS minus one for array and cube map texture objects.");
			}
		}

		framebufferFunctions.framebufferTextureLayer(
				framebufferHandle,
				framebufferAttachment,
				textureHandle,
				textureType,
				textureMipLevel,
				textureLayer
		);
	}

	@Override
	public void framebufferDrawBuffer(int framebufferHandle, int framebufferDrawBuffer) {
		var attachment = GLFramebufferAttachment.fromConstant(framebufferDrawBuffer);

		if (framebufferHandle != 0 & !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferAttachment.");
		}

		if (framebufferHandle == 0 && !attachment.isDefaultDrawable()) {
			throw new IllegalArgumentException("FramebufferDrawBuffer is not an accepted value.");
		}

		if (framebufferHandle != 0 && !attachment.isFramebufferDrawable()) {
			throw new IllegalArgumentException("FramebufferDrawBuffer is not an accepted value.");
		}

		if (framebufferHandle != 0 && attachment.hasAttachIndex() && attachment.getAttachIndex() >= helperFunctions.getMaxColorAttachments()) {
			throw new IllegalArgumentException("A framebuffer object is affected and the framebufferDrawBuffer is not equal to GL_NONE or GL_COLOR_ATTACHMENTi, where i is a value between 0 and GL_MAX_COLOR_ATTACHMENTS.");
		}

		framebufferFunctions.framebufferDrawBuffer(framebufferHandle, framebufferDrawBuffer);
	}

	@Override
	public void framebufferReadBuffer(int framebufferHandle, int framebufferReadBuffer) {
		var attachment = GLFramebufferAttachment.fromConstant(framebufferReadBuffer);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferAttachment.");
		}

		if (framebufferHandle == 0 && !attachment.isDefaultDrawable()) {
			throw new IllegalArgumentException("framebufferReadBuffer is not an accepted value.");
		}

		if (framebufferHandle != 0 && !attachment.isFramebufferDrawable()) {
			throw new IllegalArgumentException("framebufferReadBuffer is not an accepted value.");
		}

		if (framebufferHandle != 0 && attachment.hasAttachIndex() && attachment.getAttachIndex() >= helperFunctions.getMaxColorAttachments()) {
			throw new IllegalArgumentException("A framebuffer object is affected and the framebufferReadBuffer is not equal to GL_NONE or GL_COLOR_ATTACHMENTi, where i is a value between 0 and GL_MAX_COLOR_ATTACHMENTS.");
		}

		framebufferFunctions.framebufferReadBuffer(framebufferHandle, framebufferReadBuffer);
	}

	@Override
	public void framebufferDrawBuffers(
			int		framebufferHandle,
			int		framebufferDrawBufferCount,
			long	framebufferDrawBufferDataAddress
	) {
		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (framebufferDrawBufferCount > helperFunctions.getMaxDrawBuffers()) {
			throw new IllegalArgumentException("FramebufferDrawBufferCount is greater than GL_MAX_DRAW_BUFFERS.");
		}

		if (framebufferDrawBufferCount < 0) {
			throw new IllegalArgumentException("FramebufferDrawBufferCount is less than 0.");
		}

		var dataView = DataViews.wrapInts(framebufferDrawBufferDataAddress, framebufferDrawBufferCount);

		var drawBuffers = new ReferenceOpenHashSet<GLFramebufferAttachment>();

		for (var index = 0; index < framebufferDrawBufferCount; index ++) {
			var attachment = GLFramebufferAttachment.fromConstant(dataView.getInt(index));

			if (attachment == GLFramebufferAttachment.INVALID) {
				throw new IllegalArgumentException("Invalid framebufferAttachment.");
			}

			if (framebufferHandle == 0 && !attachment.isDefaultDrawable()) {
				throw new IllegalArgumentException("The API call refers to the default framebuffer and one or more of the values in the framebufferDrawBufferDataAddress is one of the GL_COLOR_ATTACHMENTn tokens.");
			}

			if (framebufferHandle != 0 && !attachment.isFramebufferDrawable()) {
				throw new IllegalArgumentException("The API call refers to a framebuffer object and one or more of the values in the framebufferDrawBufferDataAddress is anything other than GL_NONE or one of the GL_COLOR_ATTACHMENTn tokens.");
			}

			if (framebufferHandle != 0 && attachment.hasAttachIndex() && attachment.getAttachIndex() >= helperFunctions.getMaxColorAttachments()) {
				throw new IllegalArgumentException("The API call refers to a framebuffer object and one or more of the values in the framebufferDrawBufferDataAddress is anything other than GL_NONE or one of the GL_COLOR_ATTACHMENTn tokens.");
			}

			if (drawBuffers.contains(attachment)) {
				throw new IllegalArgumentException("A symbolic constant other than GL_NONE appears more than once in the framebufferDrawBufferDataAddress.");
			}

			if (attachment != GLFramebufferAttachment.NONE) {
				drawBuffers.add(attachment);
			}
		}

		framebufferFunctions.framebufferDrawBuffers(
				framebufferHandle,
				framebufferDrawBufferCount,
				framebufferDrawBufferDataAddress
		);
	}

	@Override
	public void invalidateFramebufferData(
			int		framebufferHandle,
			int		framebufferAttachmentCount,
			long	framebufferAttachmentDataAddress
	) {
		var view = DataViews.wrapInts(framebufferAttachmentCount, framebufferAttachmentDataAddress);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (framebufferAttachmentCount < 0) {
			throw new IllegalArgumentException("framebufferAttachmentCount is less than 0.");
		}

		for (var index = 0; index < framebufferAttachmentCount; index ++) {
			var attachment = GLFramebufferAttachment.fromConstant(view.getInt(index));

			if (attachment == GLFramebufferAttachment.INVALID) {
				throw new IllegalArgumentException("Invalid framebufferAttachment.");
			}

			if (framebufferHandle == 0 && !attachment.isDefault()) {
				throw new IllegalArgumentException("The API call refers to the default framebuffer and one or more of the values in the framebufferDrawBufferDataAddress is not acceptable.");
			}

			if (framebufferHandle != 0 && !attachment.isFramebuffer()) {
				throw new IllegalArgumentException("The API call refers to a framebuffer object and one or more of the values in the framebufferDrawBufferDataAddress is not acceptable.");
			}

			if (framebufferHandle != 0 && attachment.hasAttachIndex() && attachment.getAttachIndex() >= helperFunctions.getMaxColorAttachments()) {
				throw new IllegalArgumentException("The API call refers to a framebuffer object and one or more of the values in the framebufferDrawBufferDataAddress is not acceptable.");
			}
		}

		framebufferFunctions.invalidateFramebufferData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress
		);
	}

	@Override
	public void invalidateFramebufferSubData(
			int		framebufferHandle,
			int		framebufferAttachmentCount,
			long	framebufferAttachmentDataAddress,
			int		invalidatePositionX,
			int		invalidatePositionY,
			int		invalidateWidth,
			int		invalidateHeight
	) {
		var view = DataViews.wrapInts(framebufferAttachmentCount, framebufferAttachmentDataAddress);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (framebufferAttachmentCount < 0) {
			throw new IllegalArgumentException("framebufferAttachmentCount is less than 0.");
		}

		if (invalidateWidth < 0) {
			throw new IllegalArgumentException("invalidatePositionX is less than 0.");
		}

		if (invalidateHeight < 0) {
			throw new IllegalArgumentException("invalidatePositionY is less than 0.");
		}

		for (var index = 0; index < framebufferAttachmentCount; index ++) {
			var attachment = GLFramebufferAttachment.fromConstant(view.getInt(index));

			if (attachment == GLFramebufferAttachment.INVALID) {
				throw new IllegalArgumentException("Invalid framebufferAttachment.");
			}

			if (framebufferHandle == 0 && !attachment.isDefault()) {
				throw new IllegalArgumentException("The API call refers to the default framebuffer and one or more of the values in the framebufferDrawBufferDataAddress is not acceptable.");
			}

			if (framebufferHandle != 0 && !attachment.isFramebuffer()) {
				throw new IllegalArgumentException("The API call refers to a framebuffer object and one or more of the values in the framebufferDrawBufferDataAddress is not acceptable.");
			}

			if (framebufferHandle != 0 && attachment.hasAttachIndex() && attachment.getAttachIndex() >= helperFunctions.getMaxColorAttachments()) {
				throw new IllegalArgumentException("The API call refers to a framebuffer object and one or more of the values in the framebufferDrawBufferDataAddress is not acceptable.");
			}
		}

		framebufferFunctions.invalidateFramebufferSubData(
				framebufferHandle,
				framebufferAttachmentCount,
				framebufferAttachmentDataAddress,
				invalidatePositionX,
				invalidatePositionY,
				invalidateWidth,
				invalidateHeight
		);
	}

	@Override
	public void clearFramebufferiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		var buffer = GLFramebufferClearBuffer.fromConstant(clearBuffer);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (buffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		if (buffer != GLFramebufferClearBuffer.COLOR && buffer != GLFramebufferClearBuffer.STENCIL) {
			throw new IllegalArgumentException("clearBuffer is not GL_COLOR or GL_STENCIL.");
		}

		if (!buffer.getValues().isValidValue(helperFunctions, new UnionValue(clearDrawBuffer))) {
			throw new IllegalArgumentException("ClearBuffer is GL_COLOR and clearDrawBuffer is negative or greater than the value of GL_MAX_DRAW_BUFFERS minus one, or clearBuffer is GL_STENCIL and clearDrawBuffer is not zero.");
		}

		framebufferFunctions.clearFramebufferiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferuiv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		var buffer = GLFramebufferClearBuffer.fromConstant(clearBuffer);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (buffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		if (buffer != GLFramebufferClearBuffer.COLOR) {
			throw new IllegalArgumentException("clearBuffer is not GL_COLOR.");
		}

		if (!buffer.getValues().isValidValue(helperFunctions, new UnionValue(clearDrawBuffer))) {
			throw new IllegalArgumentException("ClearBuffer is GL_COLOR and clearDrawBuffer is negative or greater than the value of GL_MAX_DRAW_BUFFERS minus one.");
		}

		framebufferFunctions.clearFramebufferuiv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferfv(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			long	clearDataAddress
	) {
		var buffer = GLFramebufferClearBuffer.fromConstant(clearBuffer);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (buffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		if (buffer != GLFramebufferClearBuffer.COLOR && buffer != GLFramebufferClearBuffer.DEPTH) {
			throw new IllegalArgumentException("clearBuffer is not GL_COLOR or GL_STENCIL.");
		}

		if (!buffer.getValues().isValidValue(helperFunctions, new UnionValue(clearDrawBuffer))) {
			throw new IllegalArgumentException("ClearBuffer is GL_COLOR and clearDrawBuffer is negative or greater than the value of GL_MAX_DRAW_BUFFERS minus one, or clearBuffer is GL_DEPTH and clearDrawBuffer is not zero.");
		}

		framebufferFunctions.clearFramebufferfv(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearFramebufferfi(
			int		framebufferHandle,
			int		clearBuffer,
			int		clearDrawBuffer,
			float	clearDepth,
			int		clearStencil
	) {
		var buffer = GLFramebufferClearBuffer.fromConstant(clearBuffer);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (buffer == GLFramebufferClearBuffer.INVALID) {
			throw new IllegalArgumentException("Invalid clearBuffer.");
		}

		if (buffer != GLFramebufferClearBuffer.DEPTH_STENCIL) {
			throw new IllegalArgumentException("clearBuffer is not GL_DEPTH_STENCIL.");
		}

		if (clearDrawBuffer != 0) {
			throw new IllegalArgumentException("ClearDrawBuffer is not zero.");
		}

		framebufferFunctions.clearFramebufferfi(
				framebufferHandle,
				clearBuffer,
				clearDrawBuffer,
				clearDepth,
				clearStencil
		);
	}

	@Override
	public void blitFramebuffer(
			int framebufferHandleRead,
			int framebufferHandleWrite,
			int blitReadPositionXStart,
			int blitReadPositionYStart,
			int blitReadPositionXEnd,
			int blitReadPositionYEnd,
			int blitWritePositionXStart,
			int blitWritePositionYStart,
			int blitWritePositionXEnd,
			int blitWritePositionYEnd,
			int blitWriteBufferMask,
			int blitWriteFilter
	) {
		var filter			= GLTextureFilter						.fromConstant	(blitWriteFilter);
		var writeMask		= GLFramebufferWriteBufferBit			.toFlags		(blitWriteBufferMask);
		var readFramebuffer	= new GLFramebufferFunctionsHelper(this).setFramebuffer	(framebufferHandleRead);
		var drawFramebuffer	= new GLFramebufferFunctionsHelper(this).setFramebuffer	(framebufferHandleWrite);
		var readWidth		= blitReadPositionXEnd	- blitReadPositionXStart;
		var readHeight		= blitReadPositionYEnd	- blitReadPositionYStart;
		var drawWidth		= blitWritePositionXEnd	- blitWritePositionXStart;
		var drawHeight		= blitWritePositionYEnd	- blitWritePositionYStart;

		if (framebufferHandleRead != 0 && !readFramebuffer.isFramebuffer()) {
			throw new IllegalArgumentException("FramebufferHandleRead is not zero or the name of an existing framebuffer object.");
		}

		if (framebufferHandleWrite != 0 && !drawFramebuffer.isFramebuffer()) {
			throw new IllegalArgumentException("framebufferHandleWrite is not zero or the name of an existing framebuffer object.");
		}

		if (readFramebuffer.checkStatus(GLFramebufferType.READ_FRAMEBUFFER) != GLFramebufferStatus.FRAMEBUFFER_COMPLETE) {
			throw new IllegalArgumentException("The specified read framebuffer is not framebuffer complete.");
		}

		if (drawFramebuffer.checkStatus(GLFramebufferType.DRAW_FRAMEBUFFER) != GLFramebufferStatus.FRAMEBUFFER_COMPLETE) {
			throw new IllegalArgumentException("The specified draw framebuffer is not framebuffer complete.");
		}

		if (readFramebuffer.isMultisampled() && drawFramebuffer.isMultisampled() && readFramebuffer.getMultisampleBits() != drawFramebuffer.getMultisampleBits()) {
			throw new IllegalArgumentException("Both read and draw framebuffers are multisampled, and their effective value of GL_SAMPLES are not identical.");
		}

		if ((readFramebuffer.isMultisampled() || drawFramebuffer.isMultisampled()) && (readWidth != drawWidth || readHeight != drawHeight)) {
			throw new IllegalArgumentException("The value of GL_SAMPLE_BUFFERS for either read or draw buffers is greater than zero and the dimensions of the source and destination rectangles is not identical.");
		}

		if (writeMask.writeDepthOrStencil() && filter != GLTextureFilter.NEAREST) {
			throw new IllegalArgumentException("BlitWriteBufferMask contains any of the GL_DEPTH_BUFFER_BIT or GL_STENCIL_BUFFER_BIT and filter is not GL_NEAREST.");
		}

		if (writeMask.writeDepth() && readFramebuffer.getAttachmentComponentType(GLFramebufferAttachment.DEPTH) != drawFramebuffer.getAttachmentComponentType(GLFramebufferAttachment.DEPTH)) {
			throw new IllegalArgumentException("blitWriteBufferMask contains GL_DEPTH_BUFFER_BIT and the source and destination depth format do not match");
		}

		if (writeMask.writeStencil() && readFramebuffer.getAttachmentComponentType(GLFramebufferAttachment.STENCIL) != drawFramebuffer.getAttachmentComponentType(GLFramebufferAttachment.STENCIL)) {
			throw new IllegalArgumentException("blitWriteBufferMask contains GL_STENCIL_BUFFER_BIT and the source and destination stencil format do not match");
		}

		if (writeMask.writeDepth() && readFramebuffer.getAttachmentDepthSize(GLFramebufferAttachment.DEPTH) != drawFramebuffer.getAttachmentDepthSize(GLFramebufferAttachment.DEPTH)) {
			throw new IllegalArgumentException("blitWriteBufferMask contains GL_DEPTH_BUFFER_BIT and the source and destination depth format do not match");
		}

		if (writeMask.writeStencil() && readFramebuffer.getAttachmentStencilSize(GLFramebufferAttachment.STENCIL) != drawFramebuffer.getAttachmentStencilSize(GLFramebufferAttachment.STENCIL)) {
			throw new IllegalArgumentException("blitWriteBufferMask contains GL_STENCIL_BUFFER_BIT and the source and destination stencil format do not match");
		}

		if (writeMask.writeColor()) {
			var readAttachmentType = readFramebuffer.getAttachmentComponentType(helperFunctions.getReadBuffer(readFramebuffer.getFramebufferHandle()));

			if (readAttachmentType == GLTextureComponentType.INVALID) {
				throw new IllegalArgumentException("Invalid readAttachmentType.");
			}

			if (readAttachmentType.isInteger() && filter == GLTextureFilter.LINEAR) {
				throw new IllegalArgumentException("blitWriteFilter is GL_LINEAR and the read buffer contains integer data.");
			}

			for (var index = 0; index < helperFunctions.getMaxDrawBuffers(); index ++) {
				var drawAttachment = helperFunctions.getDrawBuffer(framebufferHandleWrite, index);

				if (drawAttachment == GLFramebufferAttachment.NONE) {
					continue;
				}

				var drawAttachmentType = drawFramebuffer.getAttachmentComponentType(drawAttachment);

				if (drawAttachmentType == GLTextureComponentType.INVALID) {
					throw new IllegalArgumentException("Invalid readAttachmentType.");
				}

				if (!readAttachmentType.isInteger() && drawAttachmentType.isInteger()) {
					throw new IllegalArgumentException("The read buffer contains fixed-point or floating-point values and any draw buffer contains neither fixed-point nor floating-point values.");
				}

				if (readAttachmentType == GLTextureComponentType.UNSIGNED_INT && drawAttachmentType != GLTextureComponentType.UNSIGNED_INT) {
					throw new IllegalArgumentException("The read buffer contains unsigned integer values and any draw buffer does not contain unsigned integer values.");
				}

				if (readAttachmentType == GLTextureComponentType.SIGNED_INT && drawAttachmentType != GLTextureComponentType.SIGNED_INT) {
					throw new IllegalArgumentException("The read buffer contains signed integer values and any draw buffer does not contain signed integer values.");
				}
			}
		}

		framebufferFunctions.blitFramebuffer(
				framebufferHandleRead,
				framebufferHandleWrite,
				blitReadPositionXStart,
				blitReadPositionYStart,
				blitReadPositionXEnd,
				blitReadPositionYEnd,
				blitWritePositionXStart,
				blitWritePositionYStart,
				blitWritePositionXEnd,
				blitWritePositionYEnd,
				blitWriteBufferMask,
				blitWriteFilter
		);
	}

	@Override
	public int checkFramebufferStatus(int framebufferHandle, int framebufferTarget) {
		if (GLFramebufferType.fromTypeConstant(framebufferTarget) == GLFramebufferType.INVALID) {
			throw new IllegalArgumentException("FramebufferTarget is not GL_DRAW_FRAMEBUFFER, GL_READ_FRAMEBUFFER or GL_FRAMEBUFFER.");
		}

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		return framebufferFunctions.checkFramebufferStatus(framebufferHandle, framebufferTarget);
	}

	@Override
	public void getFramebufferParameteriv(
			int		framebufferHandle,
			int		framebufferParameter,
			long	outDataAddress
	) {
		var parameter = GLFramebufferParameter.fromConstant(framebufferParameter);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (parameter == GLFramebufferParameter.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferParameter.");
		}

		if (framebufferHandle == 0 && !parameter.isReadOnly()) {
			throw new IllegalArgumentException("A default framebuffer is queried, and framebufferParameter is not GL_SAMPLE_BUFFERS or GL_SAMPLES.");
		}

		framebufferFunctions.getFramebufferParameteriv(
				framebufferHandle,
				framebufferParameter,
				outDataAddress
		);
	}

	@Override
	public int getFramebufferParameteri(int framebufferHandle, int framebufferParameter) {
		var parameter = GLFramebufferParameter.fromConstant(framebufferParameter);

		if (framebufferHandle != 0 && !framebufferFunctions.isFramebuffer(framebufferHandle)) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (parameter == GLFramebufferParameter.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferParameter.");
		}

		if (framebufferHandle == 0 && !parameter.isReadOnly()) {
			throw new IllegalArgumentException("A default framebuffer is queried, and framebufferParameter is not GL_SAMPLE_BUFFERS or GL_SAMPLES.");
		}

		return framebufferFunctions.getFramebufferParameteri(framebufferHandle, framebufferParameter);
	}

	@Override
	public void getFramebufferAttachmentParameteriv(
			int		framebufferHandle,
			int		framebufferAttachment,
			int		attachmentParameter,
			long	outDataAddress
	) {
		var attachment	= GLFramebufferAttachment			.fromConstant(framebufferAttachment);
		var parameter	= GLFramebufferAttachmentParameter	.fromConstant(attachmentParameter);

		if (framebufferHandle != 0 & !framebufferHelper.setFramebuffer(framebufferHandle).isFramebuffer()) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferAttachment.");
		}

		if (parameter == GLFramebufferAttachmentParameter.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferAttachmentParameter.");
		}

		if (framebufferHandle == 0 && !attachment.isDefault()) {
			throw new IllegalArgumentException("FramebufferAttachment is not one of the accepted framebuffer attachment points, as described in the documentation.");
		}

		if (framebufferHandle != 0 && !attachment.isFramebuffer()) {
			throw new IllegalArgumentException("FramebufferAttachment is not one of the accepted framebuffer attachment points, as described in the documentation.");
		}

		if (attachment == GLFramebufferAttachment.DEPTH_STENCIL && framebufferHelper.getAttachmentBinding(GLFramebufferAttachment.DEPTH) != framebufferHelper.getAttachmentBinding(GLFramebufferAttachment.STENCIL)) {
			throw new IllegalArgumentException("FramebufferAttachment is GL_DEPTH_STENCIL_ATTACHMENT and different objects are bound to the depth and stencil attachment points of framebufferHandle.");
		}

		if (attachment == GLFramebufferAttachment.DEPTH_STENCIL && parameter == GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE) {
			throw new IllegalArgumentException("FramebufferAttachment is GL_DEPTH_STENCIL_ATTACHMENT and attachmentParameter is GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE.");
		}

		framebufferFunctions.getFramebufferAttachmentParameteriv(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter,
				outDataAddress
		);
	}

	@Override
	public int getFramebufferAttachmentParameteri(
			int framebufferHandle,
			int framebufferAttachment,
			int attachmentParameter
	) {
		var attachment	= GLFramebufferAttachment			.fromConstant(framebufferAttachment);
		var parameter	= GLFramebufferAttachmentParameter	.fromConstant(attachmentParameter);

		if (framebufferHandle != 0 & !framebufferHelper.setFramebuffer(framebufferHandle).isFramebuffer()) {
			throw new IllegalArgumentException("FramebufferHandle is not zero or the name of an existing framebuffer object.");
		}

		if (attachment == GLFramebufferAttachment.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferAttachment.");
		}

		if (parameter == GLFramebufferAttachmentParameter.INVALID) {
			throw new IllegalArgumentException("Invalid framebufferAttachmentParameter.");
		}

		if (framebufferHandle == 0 && !attachment.isDefault()) {
			throw new IllegalArgumentException("FramebufferAttachment is not one of the accepted framebuffer attachment points, as described in the documentation.");
		}

		if (framebufferHandle != 0 && !attachment.isFramebuffer()) {
			throw new IllegalArgumentException("FramebufferAttachment is not one of the accepted framebuffer attachment points, as described in the documentation.");
		}

		if (attachment == GLFramebufferAttachment.DEPTH_STENCIL && framebufferHelper.getAttachmentBinding(GLFramebufferAttachment.DEPTH) != framebufferHelper.getAttachmentBinding(GLFramebufferAttachment.STENCIL)) {
			throw new IllegalArgumentException("FramebufferAttachment is GL_DEPTH_STENCIL_ATTACHMENT and different objects are bound to the depth and stencil attachment points of framebufferHandle.");
		}

		if (attachment == GLFramebufferAttachment.DEPTH_STENCIL && parameter == GLFramebufferAttachmentParameter.FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE) {
			throw new IllegalArgumentException("FramebufferAttachment is GL_DEPTH_STENCIL_ATTACHMENT and attachmentParameter is GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE.");
		}

		return framebufferFunctions.getFramebufferAttachmentParameteri(
				framebufferHandle,
				framebufferAttachment,
				attachmentParameter
		);
	}

	@Override
	public void bindFramebuffer(int framebufferTarget, int framebufferHandle) {
		if (GLFramebufferType.fromTypeConstant(framebufferTarget) == GLFramebufferType.INVALID) {
			throw new IllegalArgumentException("FramebufferTarget is not GL_DRAW_FRAMEBUFFER, GL_READ_FRAMEBUFFER or GL_FRAMEBUFFER.");
		}

		framebufferFunctions.bindFramebuffer(framebufferTarget, framebufferHandle);
	}

	@Override
	public boolean isFramebuffer(int framebufferHandle) {
		return framebufferFunctions.isFramebuffer(framebufferHandle);
	}

	@Override
	public void deleteFramebuffer(int framebufferHandle) {
		framebufferFunctions.deleteFramebuffer(framebufferHandle);
	}
}
