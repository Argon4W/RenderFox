package com.github.argon4w.renderfox.format;

public sealed interface IColor permits ColorFloat, ColorInt {
	ColorFloat	asFloat	();
	ColorInt	asInt	();
}
