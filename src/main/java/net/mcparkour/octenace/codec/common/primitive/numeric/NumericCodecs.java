/*
 * MIT License
 *
 * Copyright (c) 2020 MCParkour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.mcparkour.octenace.codec.common.primitive.numeric;

import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.CodecRegistryBuilder;

public final class NumericCodecs {

	private NumericCodecs() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}

	public static <O, A, V> CodecRegistry<O, A, V> createNumericCodecRegistry() {
		ByteCodec<O, A, V> byteCodec = new ByteCodec<>();
		ShortCodec<O, A, V> shortCodec = new ShortCodec<>();
		IntCodec<O, A, V> intCodec = new IntCodec<>();
		LongCodec<O, A, V> longCodec = new LongCodec<>();
		FloatCodec<O, A, V> floatCodec = new FloatCodec<>();
		DoubleCodec<O, A, V> doubleCodec = new DoubleCodec<>();
		return new CodecRegistryBuilder<O, A, V>()
			.codec(byte.class, byteCodec)
			.codec(Byte.class, byteCodec)
			.codec(short.class, shortCodec)
			.codec(Short.class, shortCodec)
			.codec(int.class, intCodec)
			.codec(Integer.class, intCodec)
			.codec(long.class, longCodec)
			.codec(Long.class, longCodec)
			.codec(float.class, floatCodec)
			.codec(Float.class, floatCodec)
			.codec(double.class, doubleCodec)
			.codec(Double.class, doubleCodec)
			.build();
	}
}
