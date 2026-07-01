package com.github.argon4w.renderfox.buffer;

import com.github.argon4w.renderfox.data.coordinate.IDataRange;
import com.github.argon4w.renderfox.data.view.IMappedDataView;

public interface IMappedBuffer {

	IMappedDataView<?> data();
	IMappedDataView<?> data(IDataRange dataRange);
}
