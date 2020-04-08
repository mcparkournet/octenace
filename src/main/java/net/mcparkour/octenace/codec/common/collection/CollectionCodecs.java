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

package net.mcparkour.octenace.codec.common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.CodecRegistryBuilder;

public final class CollectionCodecs {

	public static final CommonCodec<Object[]> ARRAY_CODEC = new ArrayCodec();
	public static final CommonCodec<Collection<?>> ARRAY_LIST_CODEC = new CollectionCodec(ArrayList::new);
	public static final CommonCodec<Collection<?>> HASH_SET_CODEC = new CollectionCodec(HashSet::new);
	public static final CommonCodec<Collection<?>> LINKED_HASH_SET_CODEC = new CollectionCodec(LinkedHashSet::new);
	public static final CommonCodec<Map<?, ?>> HASH_MAP_CODEC = new MapCodec(HashMap::new);
	public static final CommonCodec<Map<?, ?>> LINKED_HASH_MAP_CODEC = new MapCodec(LinkedHashMap::new);

	public static final CodecRegistry COLLECTION_CODEC_REGISTRY = new CodecRegistryBuilder()
		.codec(ARRAY_CODEC, Object[].class)
		.codec(ARRAY_LIST_CODEC, ArrayList.class)
		.codec(ARRAY_LIST_CODEC, List.class)
		.codec(LINKED_HASH_SET_CODEC, LinkedHashSet.class)
		.codec(HASH_SET_CODEC, HashSet.class)
		.codec(HASH_SET_CODEC, Set.class)
		.codec(LINKED_HASH_MAP_CODEC, LinkedHashMap.class)
		.codec(HASH_MAP_CODEC, HashMap.class)
		.codec(HASH_MAP_CODEC, Map.class)
		.build();

	private CollectionCodecs() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}
}
