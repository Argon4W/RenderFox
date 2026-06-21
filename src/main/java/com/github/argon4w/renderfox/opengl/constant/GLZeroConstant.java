package com.github.argon4w.renderfox.opengl.constant;

import com.github.argon4w.renderfox.data.UnionValue;

public class GLZeroConstant<T> extends AbstractGLDefinedConstants<T> {

	public static final GLZeroConstant<?> INSTANCE = new GLZeroConstant<>();

	@Override
	public boolean isValidValue(T context, UnionValue value) {
		return value.asInt() == 0;
	}

	@SuppressWarnings("unchecked")
	public static <T> T of() {
		return (T) INSTANCE;
	}
}
