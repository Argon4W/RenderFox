package com.github.argon4w.renderfox.opengl.framebuffer.object;

import com.github.argon4w.renderfox.data.coordinate.extent.IExtent3D;
import com.github.argon4w.renderfox.data.view.AddressDataView;
import com.github.argon4w.renderfox.data.view.DataViews;
import com.github.argon4w.renderfox.opengl.framebuffer.constant.GLFramebufferAttachment;
import com.github.argon4w.renderfox.opengl.framebuffer.object.raw.IGLRawFramebuffer;
import com.github.argon4w.renderfox.opengl.texture.object.feature.IGLRawTextureBase;

import java.util.Arrays;

public class GLFramebufferCreateInfo implements AutoCloseable {

	private final GLFramebufferAttachment	readAttachment;
	private final AddressDataView			drawAttachments;
	private final Attachment[]				colorAttachments;
	private final Attachment				depthAttachment;
	private final Attachment				stencilAttachment;
	private final int						drawAttachmentCount;
	private final int						defaultWidth;
	private final int						defaultHeight;
	private final int						defaultLayers;
	private final int						defaultSamples;
	private final boolean					defaultFixedSampleLocations;

	public GLFramebufferCreateInfo(
			GLFramebufferAttachment	readAttachment,
			AddressDataView			drawAttachments,
			Attachment[]			colorAttachments,
			Attachment				depthAttachment,
			Attachment				stencilAttachment,
			int						drawAttachmentCount,
			int						defaultWidth,
			int						defaultHeight,
			int						defaultLayers,
			int						defaultSamples,
			boolean					defaultFixedSampleLocations
	) {
		this.readAttachment					= readAttachment;
		this.drawAttachments				= drawAttachments;
		this.colorAttachments				= colorAttachments;
		this.depthAttachment				= depthAttachment;
		this.stencilAttachment				= stencilAttachment;
		this.drawAttachmentCount			= drawAttachmentCount;
		this.defaultWidth					= defaultWidth;
		this.defaultHeight					= defaultHeight;
		this.defaultLayers					= defaultLayers;
		this.defaultSamples					= defaultSamples;
		this.defaultFixedSampleLocations	= defaultFixedSampleLocations;
	}

	public GLFramebufferAttachment getReadAttachment() {
		return readAttachment;
	}

	public AddressDataView getDrawAttachments() {
		return drawAttachments;
	}

	public Attachment[] getColorAttachments() {
		return colorAttachments;
	}

	public Attachment getDepthAttachment() {
		return depthAttachment;
	}

	public Attachment getStencilAttachment() {
		return stencilAttachment;
	}

	public int getDrawAttachmentCount() {
		return drawAttachmentCount;
	}

	public int getDefaultWidth() {
		return defaultWidth;
	}

	public int getDefaultHeight() {
		return defaultHeight;
	}

	public int getDefaultLayers() {
		return defaultLayers;
	}

	public int getDefaultSamples() {
		return defaultSamples;
	}

	public boolean isDefaultFixedSampleLocations() {
		return defaultFixedSampleLocations;
	}

	@Override
	public void close() {
		DataViews.free(drawAttachments);
	}

	public static class Builder {

		private GLFramebufferAttachment	readAttachment;
		private int			[]			drawAttachments;
		private Attachment	[]			colorAttachments;
		private Attachment				depthAttachment;
		private Attachment				stencilAttachment;
		private int						drawAttachmentCount;
		private int						defaultWidth;
		private int						defaultHeight;
		private int						defaultLayers;
		private int						defaultSamples;
		private boolean					defaultFixedSampleLocations;

		public Builder() {
			this.readAttachment					= GLFramebufferAttachment.COLOR_ATTACHMENT_0;
			this.drawAttachments				= new int		[1];
			this.colorAttachments				= new Attachment[1];
			this.depthAttachment				= ZeroAttachment.INSTANCE;
			this.stencilAttachment				= ZeroAttachment.INSTANCE;
			this.drawAttachmentCount			= 1;
			this.defaultWidth					= 0;
			this.defaultHeight					= 0;
			this.defaultLayers					= 0;
			this.defaultSamples					= 0;
			this.defaultFixedSampleLocations	= false;
		}

		public Builder colorAttachment(
				IGLRawTextureBase	attachedTexture,
				int					attachedMipLevel,
				int					attachedIndex
		) {
			if (this.drawAttachmentCount <= attachedIndex) {
				this.drawAttachmentCount	= attachedIndex + 1;
				this.drawAttachments		= Arrays.copyOf(this.drawAttachments,	this.drawAttachmentCount);
				this.colorAttachments		= Arrays.copyOf(this.colorAttachments,	this.drawAttachmentCount);
			}

			if (attachedIndex == 0) {
				defaultWidth	= attachedTexture.getWidth	(attachedMipLevel);
				defaultHeight	= attachedTexture.getHeight	(attachedMipLevel);

				defaultLayers = switch (attachedTexture.getTextureType().getLayerIndex()) {
					case 0	-> attachedTexture.getWidth	(attachedMipLevel);
					case 1	-> attachedTexture.getHeight(attachedMipLevel);
					case 2	-> attachedTexture.getDepth	(attachedMipLevel);
					default	-> 0;
				};
			}

			drawAttachments	[attachedIndex] = GLFramebufferAttachment.color(attachedIndex).getConstant();
			colorAttachments[attachedIndex] = new SimpleAttachment(
					attachedTexture,
					attachedMipLevel,
					attachedIndex
			);

			return this;
		}

		public Builder colorAttachment(
				IGLRawTextureBase	attachedTexture,
				int					attachedMipLevel,
				int					attachedLayer,
				int					attachedIndex
		) {
			if (this.drawAttachmentCount <= attachedIndex) {
				this.drawAttachmentCount	= attachedIndex + 1;
				this.drawAttachments		= Arrays.copyOf(this.drawAttachments,	this.drawAttachmentCount);
				this.colorAttachments		= Arrays.copyOf(this.colorAttachments,	this.drawAttachmentCount);
			}

			if (attachedIndex == 0) {
				defaultWidth	= attachedTexture.getWidth	(attachedMipLevel);
				defaultHeight	= attachedTexture.getHeight	(attachedMipLevel);
				defaultLayers	= 0;
			}

			drawAttachments	[attachedIndex] = GLFramebufferAttachment.color(attachedIndex).getConstant();
			colorAttachments[attachedIndex] = new LayeredAttachment(
					attachedTexture,
					attachedMipLevel,
					attachedLayer,
					attachedIndex
			);

			return this;
		}

		public Builder depthAttachment(IGLRawTextureBase attachedTexture, int attachedMipLevel) {
			depthAttachment = new SimpleAttachment(
					attachedTexture,
					attachedMipLevel,
					0
			);

			return this;
		}

		public Builder depthAttachment(
				IGLRawTextureBase	attachedTexture,
				int					attachedMipLevel,
				int					attachedLayer
		) {
			depthAttachment = new LayeredAttachment(
					attachedTexture,
					attachedMipLevel,
					attachedLayer,
					0
			);

			return this;
		}

		public Builder stencilAttachment(IGLRawTextureBase attachedTexture, int attachedMipLevel) {
			stencilAttachment = new SimpleAttachment(
					attachedTexture,
					attachedMipLevel,
					0
			);

			return this;
		}

		public Builder stencilAttachment(
				IGLRawTextureBase	attachedTexture,
				int					attachedMipLevel,
				int					attachedLayer
		) {
			stencilAttachment = new LayeredAttachment(
					attachedTexture,
					attachedMipLevel,
					attachedLayer,
					0
			);

			return this;
		}

		public Builder readColorAttachment(int readColorAttachmentIndex) {
			this.readAttachment = GLFramebufferAttachment.color(readColorAttachmentIndex);
			return this;
		}

		public Builder defaultWidth(int defaultWidth) {
			this.defaultWidth = defaultWidth;
			return this;
		}

		public Builder defaultHeight(int defaultHeight) {
			this.defaultHeight = defaultHeight;
			return this;
		}

		public Builder defaultLayers(int defaultLayers) {
			this.defaultLayers = defaultLayers;
			return this;
		}

		public Builder defaultSamples(int defaultSamples) {
			this.defaultSamples = defaultSamples;
			return this;
		}

		public Builder defaultFixedSampleLocations(boolean defaultFixedSampleLocations) {
			this.defaultFixedSampleLocations = defaultFixedSampleLocations;
			return this;
		}

		public Builder defaultExtent(IExtent3D defaultExtent) {
			this.defaultWidth	= (int) defaultExtent.getWidth	();
			this.defaultHeight	= (int) defaultExtent.getHeight	();
			this.defaultLayers	= (int) defaultExtent.getDepth	();

			return this;
		}

		public GLFramebufferCreateInfo build() {
			return new GLFramebufferCreateInfo(
					readAttachment,
					DataViews.ofInts(drawAttachments),
					colorAttachments,
					depthAttachment,
					stencilAttachment,
					drawAttachmentCount,
					defaultWidth,
					defaultHeight,
					defaultLayers,
					defaultSamples,
					defaultFixedSampleLocations
			);
		}
	}

	public interface Attachment {

		void attach(IGLRawFramebuffer framebuffer, GLFramebufferAttachment attachment);
	}

	public static class ZeroAttachment implements Attachment {

		public static final ZeroAttachment INSTANCE = new ZeroAttachment();

		@Override
		public void attach(IGLRawFramebuffer framebuffer, GLFramebufferAttachment attachment) {
			framebuffer.detach(attachment);
		}
	}

	public record SimpleAttachment(
			IGLRawTextureBase	attachedTexture,
			int					attachedMipLevel,
			int					attachedIndex
	) implements Attachment {

		@Override
		public void attach(IGLRawFramebuffer framebuffer, GLFramebufferAttachment attachment) {
			framebuffer.attachTexture(
					attachment,
					this.attachedTexture,
					this.attachedMipLevel
			);
		}
	}

	public record LayeredAttachment(
			IGLRawTextureBase	attachedTexture,
			int					attachedMipLevel,
			int					attachedLayer,
			int					attachedIndex
	) implements Attachment {

		@Override
		public void attach(IGLRawFramebuffer framebuffer, GLFramebufferAttachment attachment) {
			framebuffer.attachTextureLayer(
					attachment,
					this.attachedTexture,
					this.attachedMipLevel,
					this.attachedLayer
			);
		}
	}
}
