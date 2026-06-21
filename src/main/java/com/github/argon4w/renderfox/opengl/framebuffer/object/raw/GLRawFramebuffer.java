package com.github.argon4w.renderfox.opengl.framebuffer.object.raw;

import com.github.argon4w.renderfox.opengl.device.framebuffer.GLFramebufferContext;
import com.github.argon4w.renderfox.opengl.format.GLInternalFormat;
import com.github.argon4w.renderfox.opengl.framebuffer.GLFramebufferType;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferClearBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferStatus;
import com.github.argon4w.renderfox.opengl.framebuffer.function.GLFramebufferFunctionsHelper;
import com.github.argon4w.renderfox.opengl.framebuffer.function.parameter.flag.GLFramebufferWriteBuffer;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.AbstractGLFramebufferStore;
import com.github.argon4w.renderfox.opengl.framebuffer.object.feature.IGLFramebufferBase;
import com.github.argon4w.renderfox.opengl.texture.constant.GLTextureComponentType;
import com.github.argon4w.renderfox.opengl.texture.constant.filter.GLFilterMode;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTextureBase;

import java.util.Arrays;

public class GLRawFramebuffer extends AbstractGLFramebufferStore implements IGLRawFramebuffer {

	private final	GLFramebufferFunctionsHelper	framebufferHelper;
	private			int								defaultWidth;
	private			int								defaultHeight;
	private			int								defaultLayers;
	private			int								defaultSamples;
	private			boolean							defaultFixedSampleLocation;
	private			int								attachmentCount;
	private			int						[]		attachmentBindings;
	private			int						[]		attachmentMipLevels;
	private			int						[]		attachmentLayers;
	private			int						[]		attachmentRedSizes;
	private			int						[]		attachmentGreenSizes;
	private			int						[]		attachmentBlueSizes;
	private			int						[]		attachmentAlphaSizes;
	private			int						[]		attachmentDepthSizes;
	private			int						[]		attachmentStencilSizes;
	private			boolean					[]		attachmentLayered;
	private			GLTextureComponentType	[]		attachmentComponentTypes;
	private			boolean							deleted;

	public GLRawFramebuffer(GLFramebufferContext framebufferContext) {
		this.framebufferHelper			= framebufferContext.createFramebufferHelper(framebufferContext.createFramebufferHandle());
		this.defaultWidth				= 0;
		this.defaultHeight				= 0;
		this.defaultLayers				= 0;
		this.defaultSamples				= 0;
		this.defaultFixedSampleLocation	= false;
		this.attachmentCount			= 0;
		this.attachmentBindings			= new int					[0];
		this.attachmentMipLevels		= new int					[0];
		this.attachmentLayers			= new int					[0];
		this.attachmentRedSizes			= new int					[0];
		this.attachmentGreenSizes		= new int					[0];
		this.attachmentBlueSizes		= new int					[0];
		this.attachmentAlphaSizes		= new int					[0];
		this.attachmentDepthSizes		= new int					[0];
		this.attachmentStencilSizes		= new int					[0];
		this.attachmentLayered			= new boolean				[0];
		this.attachmentComponentTypes	= new GLTextureComponentType[0];
		this.deleted					= false;
	}

	private void resize(int index) {
		attachmentCount = index + 1;

		attachmentBindings			= Arrays.copyOf(attachmentBindings,			attachmentCount);
		attachmentMipLevels			= Arrays.copyOf(attachmentMipLevels,		attachmentCount);
		attachmentLayers			= Arrays.copyOf(attachmentLayers,			attachmentCount);
		attachmentRedSizes			= Arrays.copyOf(attachmentRedSizes,			attachmentCount);
		attachmentGreenSizes		= Arrays.copyOf(attachmentGreenSizes,		attachmentCount);
		attachmentBlueSizes			= Arrays.copyOf(attachmentBlueSizes,		attachmentCount);
		attachmentAlphaSizes		= Arrays.copyOf(attachmentAlphaSizes,		attachmentCount);
		attachmentDepthSizes		= Arrays.copyOf(attachmentDepthSizes,		attachmentCount);
		attachmentStencilSizes		= Arrays.copyOf(attachmentStencilSizes,		attachmentCount);
		attachmentLayered			= Arrays.copyOf(attachmentLayered,			attachmentCount);
		attachmentComponentTypes	= Arrays.copyOf(attachmentComponentTypes,	attachmentCount);
	}

	@Override
	public void detach(GLFramebufferAttachment attachment) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.detach(attachment);

		var index = attachment.getStoreIndex();

		if (index >= attachmentCount) {
			return;
		}

		attachmentBindings	[index] = 0;
		attachmentMipLevels	[index] = 0;
		attachmentLayers	[index] = 0;
		attachmentLayered	[index] = false;

		attachmentDepthSizes	[index] = 0;
		attachmentStencilSizes	[index] = 0;

		attachmentRedSizes		[index] = 0;
		attachmentGreenSizes	[index] = 0;
		attachmentBlueSizes		[index] = 0;
		attachmentAlphaSizes	[index] = 0;
		attachmentDepthSizes	[index] = 0;
		attachmentStencilSizes	[index] = 0;
	}

	@Override
	public void attachTexture(
			GLFramebufferAttachment	attachment,
			IGLRawTextureBase		attachmentTexture,
			int						attachmentTextureMipLevel
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (attachmentTexture == null) {
			throw new IllegalArgumentException("AttachmentTexture cannot be null.");
		}

		var index = attachment.getStoreIndex();

		if (index < 0) {
			throw new IllegalArgumentException("Attachment is not one of the accepted values with getStoreIndex() >= 0.");
		}

		if (index >= attachmentCount) {
			resize(index);
		}

		var internalFormat = attachmentTexture.getInternalFormat(attachmentTextureMipLevel);

		attachmentComponentTypes[index] = GLTextureComponentType.fromInternalFormat(internalFormat);

		attachmentLayered	[index] = attachmentTexture.getTextureType	().isLayered();
		attachmentBindings	[index] = attachmentTexture.getTextureHandle();
		attachmentMipLevels	[index] = attachmentTextureMipLevel;

		if (internalFormat == GLInternalFormat.DEPTH24_STENCIL8) {
			attachmentDepthSizes	[index] = 24;
			attachmentStencilSizes	[index] = 8;
		} else if (internalFormat == GLInternalFormat.DEPTH32F_STENCIL8) {
			attachmentDepthSizes	[index] = 32;
			attachmentStencilSizes	[index] = 8;
		} else {
			var formatTypeBits = internalFormat.getFormatType().getSize() * 8L;

			attachmentRedSizes		[index] = internalFormat.hasRed		() ? (int) formatTypeBits : 0;
			attachmentGreenSizes	[index] = internalFormat.hasGreen	() ? (int) formatTypeBits : 0;
			attachmentBlueSizes		[index] = internalFormat.hasBlue	() ? (int) formatTypeBits : 0;
			attachmentAlphaSizes	[index] = internalFormat.hasAlpha	() ? (int) formatTypeBits : 0;
			attachmentDepthSizes	[index] = internalFormat.hasDepth	() ? (int) formatTypeBits : 0;
			attachmentStencilSizes	[index] = internalFormat.hasStencil	() ? (int) formatTypeBits : 0;
		}

		framebufferHelper.attachTexture(
				attachment,
				attachmentTexture,
				attachmentTextureMipLevel
		);
	}

	@Override
	public void attachTextureLayer(
			GLFramebufferAttachment	attachment,
			IGLRawTextureBase		attachmentTexture,
			int						attachmentTextureMipLevel,
			int						attachmentTextureLayer
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		if (attachment == null) {
			throw new IllegalArgumentException("Attachment cannot be null.");
		}

		if (attachmentTexture == null) {
			throw new IllegalArgumentException("AttachmentTexture cannot be null.");
		}

		var index = attachment.getStoreIndex();

		if (index < 0) {
			throw new IllegalArgumentException("Attachment is not one of the accepted values with getStoreIndex() >= 0.");
		}

		if (index >= attachmentCount) {
			resize(index);
		}

		var internalFormat = attachmentTexture.getInternalFormat(attachmentTextureMipLevel);

		attachmentComponentTypes[index] = GLTextureComponentType.fromInternalFormat(internalFormat);

		attachmentBindings	[index] = attachmentTexture.getTextureHandle();
		attachmentMipLevels	[index] = attachmentTextureMipLevel;
		attachmentLayers	[index] = attachmentTextureLayer;
		attachmentLayered	[index] = false;

		if (internalFormat == GLInternalFormat.DEPTH24_STENCIL8) {
			attachmentDepthSizes	[index] = 24;
			attachmentStencilSizes	[index] = 8;
		} else if (internalFormat == GLInternalFormat.DEPTH32F_STENCIL8) {
			attachmentDepthSizes	[index] = 32;
			attachmentStencilSizes	[index] = 8;
		} else {
			var formatTypeBits = internalFormat.getFormatType().getSize() * 8L;

			attachmentRedSizes		[index] = internalFormat.hasRed		() ? (int) formatTypeBits : 0;
			attachmentGreenSizes	[index] = internalFormat.hasGreen	() ? (int) formatTypeBits : 0;
			attachmentBlueSizes		[index] = internalFormat.hasBlue	() ? (int) formatTypeBits : 0;
			attachmentAlphaSizes	[index] = internalFormat.hasAlpha	() ? (int) formatTypeBits : 0;
			attachmentDepthSizes	[index] = internalFormat.hasDepth	() ? (int) formatTypeBits : 0;
			attachmentStencilSizes	[index] = internalFormat.hasStencil	() ? (int) formatTypeBits : 0;
		}

		framebufferHelper.attachTextureLayer(
				attachment,
				attachmentTexture,
				attachmentTextureMipLevel,
				attachmentTextureLayer
		);
	}

	@Override
	public void setDrawAttachment(GLFramebufferAttachment attachment) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.setDrawAttachment(attachment);
	}

	@Override
	public void setDrawAttachments(int attachmentCount, long attachmentDataAddress) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.setDrawAttachments(attachmentCount, attachmentDataAddress);
	}

	@Override
	public void setReadAttachment(GLFramebufferAttachment attachment) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.setReadAttachment(attachment);
	}

	@Override
	public void invalidateAllData(int attachmentCount, long attachmentDataAddress) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.invalidateAllData(attachmentCount, attachmentDataAddress);
	}

	@Override
	public void invalidateRangeData(
			int		attachmentCount,
			long	attachmentDataAddress,
			int		invalidatePositionX,
			int		invalidatePositionY,
			int		invalidateWidth,
			int		invalidateHeight
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.invalidateRangeData(
				attachmentCount,
				attachmentDataAddress,
				invalidatePositionX,
				invalidatePositionY,
				invalidateWidth,
				invalidateHeight
		);
	}

	@Override
	public void clearAllDataInt(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.clearAllDataInt(
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearAllDataUnsignedInt(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.clearAllDataUnsignedInt(
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearAllDataFloat(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			long						clearDataAddress
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.clearAllDataFloat(
				clearBuffer,
				clearDrawBuffer,
				clearDataAddress
		);
	}

	@Override
	public void clearAllDataDepthStencil(
			GLFramebufferClearBuffer	clearBuffer,
			int							clearDrawBuffer,
			float						clearDepth,
			int							clearStencil
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.clearAllDataDepthStencil(
				clearBuffer,
				clearDrawBuffer,
				clearDepth,
				clearStencil
		);
	}

	@Override
	public GLFramebufferStatus checkStatus(GLFramebufferType framebufferType) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		return framebufferHelper.checkStatus(framebufferType);
	}

	@Override
	public void bind(GLFramebufferType framebufferType) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.bind(framebufferType);
	}

	@Override
	public void blitDataTo(
			IGLFramebufferBase			framebufferWrite,
			int							blitReadPositionXStart,
			int							blitReadPositionYStart,
			int							blitReadPositionXEnd,
			int							blitReadPositionYEnd,
			int							blitWritePositionXStart,
			int							blitWritePositionYStart,
			int							blitWritePositionXEnd,
			int							blitWritePositionYEnd,
			GLFramebufferWriteBuffer	blitWriteBuffer,
			GLFilterMode				blitFilterMode
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.blitDataTo(
				framebufferWrite,
				blitReadPositionXStart,
				blitReadPositionYStart,
				blitReadPositionXEnd,
				blitReadPositionYEnd,
				blitWritePositionXStart,
				blitWritePositionYStart,
				blitWritePositionXEnd,
				blitWritePositionYEnd,
				blitWriteBuffer,
				blitFilterMode
		);
	}

	@Override
	public void blitDataFrom(
			IGLFramebufferBase			framebufferRead,
			int							blitReadPositionXStart,
			int							blitReadPositionYStart,
			int							blitReadPositionXEnd,
			int							blitReadPositionYEnd,
			int							blitWritePositionXStart,
			int							blitWritePositionYStart,
			int							blitWritePositionXEnd,
			int							blitWritePositionYEnd,
			GLFramebufferWriteBuffer	blitWriteBuffer,
			GLFilterMode				blitFilterMode
	) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		framebufferHelper.blitDataFrom(
				framebufferRead,
				blitReadPositionXStart,
				blitReadPositionYStart,
				blitReadPositionXEnd,
				blitReadPositionYEnd,
				blitWritePositionXStart,
				blitWritePositionYStart,
				blitWritePositionXEnd,
				blitWritePositionYEnd,
				blitWriteBuffer,
				blitFilterMode
		);
	}

	@Override
	public void delete() {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		this.deleted = true;
		this.framebufferHelper.delete();
	}

	@Override
	public void setDefaultWidth(int width) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		super.setDefaultWidth(width);

		this.defaultWidth = width;
	}

	@Override
	public void setDefaultHeight(int height) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		super.setDefaultHeight(height);

		this.defaultHeight = height;
	}

	@Override
	public void setDefaultLayers(int layers) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		super.setDefaultLayers(layers);

		this.defaultLayers = layers;
	}

	@Override
	public void setDefaultSamples(int samples) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		super.setDefaultSamples(samples);

		this.defaultSamples = samples;
	}

	@Override
	public void setDefaultFixedSampleLocation(boolean fixedSampleLocation) {
		if (deleted) {
			throw new IllegalStateException("The framebuffer has been deleted.");
		}

		super.setDefaultFixedSampleLocation(fixedSampleLocation);

		this.defaultFixedSampleLocation = fixedSampleLocation;
	}

	@Override
	public int getFramebufferHandle() {
		return framebufferHelper.getFramebufferHandle();
	}

	@Override
	public int getAttachmentBinding(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentBindings[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentMipLevel(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentMipLevels[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentLayer(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentLayers[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentRedSize(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentRedSizes[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentGreenSize(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentGreenSizes[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentBlueSize(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentBlueSizes[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentAlphaSize(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentAlphaSizes[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentDepthSize(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentDepthSizes[attachmentCount] : 0;
	}

	@Override
	public int getAttachmentStencilSize(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentStencilSizes[attachmentCount] : 0;
	}

	@Override
	public boolean isAttachmentLayered(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount && attachmentLayered[attachmentCount];
	}

	@Override
	public GLTextureComponentType getAttachmentComponentType(GLFramebufferAttachment attachment) {
		return attachment.getStoreIndex() >= 0 && attachment.getStoreIndex() < attachmentCount ? attachmentComponentTypes[attachmentCount] : GLTextureComponentType.NONE;
	}

	@Override
	public int getDefaultWidth() {
		return defaultWidth;
	}

	@Override
	public int getDefaultHeight() {
		return defaultHeight;
	}

	@Override
	public int getDefaultLayers() {
		return defaultLayers;
	}

	@Override
	public int getDefaultSamples() {
		return defaultSamples;
	}

	@Override
	public boolean isDefaultFixedSampleLocation() {
		return defaultFixedSampleLocation;
	}

	@Override
	public boolean isFramebuffer() {
		return !deleted;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}
}
