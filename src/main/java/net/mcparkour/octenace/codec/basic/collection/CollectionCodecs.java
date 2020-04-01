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

package net.mcparkour.octenace.codec.basic.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.CodecRegistryBuilder;

public final class CollectionCodecs {

	public static final Codec<Object[]> ARRAY_CODEC = new ArrayCodec();
	public static final Codec<List<?>> LIST_CODEC = new ListCodec();
	public static final Codec<Set<?>> SET_CODEC = new SetCodec();
	public static final Codec<Map<?, ?>> MAP_CODEC = new MapCodec();

	public static final CodecRegistry COLLECTION_CODEC_REGISTRY = new CodecRegistryBuilder()
		.codec(ARRAY_CODEC, Object[].class)
		.codec(LIST_CODEC, List.class)
		.codec(SET_CODEC, Set.class)
		.codec(MAP_CODEC, Map.class)
		.build();

	private CollectionCodecs() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}
}
