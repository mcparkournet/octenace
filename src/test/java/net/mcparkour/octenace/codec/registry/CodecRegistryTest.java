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

package net.mcparkour.octenace.codec.registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.common.collection.CollectionCodecs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CodecRegistryTest {

	@Test
	public void testSetCodecsPriority() {
		CodecRegistry registry = CollectionCodecs.COLLECTION_CODEC_REGISTRY;
		Codec<?, ?, ?, ?> codec1 = registry.get(LinkedHashSet.class);
		Codec<?, ?, ?, ?> codec2 = registry.get(HashSet.class);
		Codec<?, ?, ?, ?> codec3 = registry.get(Set.class);
		Codec<?, ?, ?, ?> codec4 = registry.get(TreeSet.class);
		Assertions.assertNotEquals(codec1, codec2);
		Assertions.assertEquals(codec2, codec3);
		Assertions.assertEquals(codec2, codec4);
	}

	@Test
	public void testMapCodecsPriority() {
		CodecRegistry registry = CollectionCodecs.COLLECTION_CODEC_REGISTRY;
		Codec<?, ?, ?, ?> codec1 = registry.get(LinkedHashMap.class);
		Codec<?, ?, ?, ?> codec2 = registry.get(HashMap.class);
		Codec<?, ?, ?, ?> codec3 = registry.get(Map.class);
		Codec<?, ?, ?, ?> codec4 = registry.get(TreeMap.class);
		Assertions.assertNotEquals(codec1, codec2);
		Assertions.assertEquals(codec2, codec3);
		Assertions.assertEquals(codec2, codec4);
	}
}
