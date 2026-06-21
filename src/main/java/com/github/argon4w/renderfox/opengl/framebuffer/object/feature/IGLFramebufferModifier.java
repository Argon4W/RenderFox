package com.github.argon4w.renderfox.opengl.framebuffer.object.feature;

public interface IGLFramebufferModifier {

	void setDefaultWidth				(int		width);
	void setDefaultHeight				(int		height);
	void setDefaultLayers				(int		layers);
	void setDefaultSamples				(int		samples);
	void setDefaultFixedSampleLocation	(boolean	fixedSampleLocation);
}
