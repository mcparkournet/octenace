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

package net.mcparkour.octenace.codec.common;

import net.mcparkour.octenace.codec.common.collection.CollectionCodecs;
import net.mcparkour.octenace.codec.common.primitive.PrimitiveCodecs;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.cached.CachedCodecRegistryBuilder;

public final class Codecs {

	private Codecs() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}

	public static <O, A, V> CodecRegistry<O, A, V> createCommonCodecRegistry() {
		EnumCodec<O, A, V> enumCodec = new EnumCodec<>();
		StringCodec<O, A, V> stringCodec = new StringCodec<>();
		ObjectCodec<O, A, V> objectCodec = new ObjectCodec<>();
		return new CachedCodecRegistryBuilder<O, A, V>()
			.registry(CollectionCodecs.createCollectionCodecRegistry())
			.registry(PrimitiveCodecs.createPrimitiveCodecRegistry())
			.codec(Enum.class, enumCodec)
			.codec(String.class, stringCodec)
			.codec(Object.class, objectCodec)
			.build();
	}
}
