package com.github.argon4w.renderfox.opengl.buffer.object.mapped.impl;

import com.github.argon4w.renderfox.data.view.IDataView;
import com.github.argon4w.renderfox.opengl.buffer.object.IGLBuffer;

public interface IGLMappedBufferImpl extends IGLBuffer {

	void			close	();
	IDataView<?>	open	();
}
