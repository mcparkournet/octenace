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

package net.mcparkour.octenace.codec.basic.primitive.numeric;

import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.CodecRegistryBuilder;

public final class NumericCodecs {

	public static final Codec<Byte> BYTE_CODEC = new ByteCodec();
	public static final Codec<Short> SHORT_CODEC = new ShortCodec();
	public static final Codec<Integer> INTEGER_CODEC = new IntegerCodec();
	public static final Codec<Long> LONG_CODEC = new LongCodec();
	public static final Codec<Float> FLOAT_CODEC = new FloatCodec();
	public static final Codec<Double> DOUBLE_CODEC = new DoubleCodec();

	public static final CodecRegistry NUMERIC_CODEC_REGISTRY = new CodecRegistryBuilder()
		.codec(BYTE_CODEC, byte.class)
		.build();

	private NumericCodecs() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}
}
