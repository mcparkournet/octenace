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
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import net.mcparkour.octenace.codec.common.Codecs;
import net.mcparkour.octenace.codec.common.extra.ExtraCodecs;
import net.mcparkour.octenace.codec.common.extra.UUIDCodec;
import net.mcparkour.octenace.codec.registry.cached.CachedCodecRegistryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CachedCodecRegistryTest {

	@Test
	public void testSetCodecsPriority() {
		CodecRegistry<?, ?, ?> registry = Codecs.createCommonCodecRegistry();
		var codec1 = registry.get(LinkedHashSet.class);
		var codec2 = registry.get(HashSet.class);
		var codec3 = registry.get(Set.class);
		var codec4 = registry.get(TreeSet.class);
		Assertions.assertNotEquals(codec1, codec2);
		Assertions.assertEquals(codec2, codec3);
		Assertions.assertEquals(codec2, codec4);
	}

	@Test
	public void testMapCodecsPriority() {
		CodecRegistry<?, ?, ?> registry = Codecs.createCommonCodecRegistry();
		var codec1 = registry.get(LinkedHashMap.class);
		var codec2 = registry.get(HashMap.class);
		var codec3 = registry.get(Map.class);
		var codec4 = registry.get(TreeMap.class);
		Assertions.assertNotEquals(codec1, codec2);
		Assertions.assertEquals(codec2, codec3);
		Assertions.assertEquals(codec2, codec4);
	}

	@Test
	public void testObjectCodecPriority() {
		CodecRegistry<?, ?, ?> registry = new CachedCodecRegistryBuilder<>()
			.registry(Codecs.createCommonCodecRegistry())
			.codec(UUID.class, new UUIDCodec<>())
			.build();
		var codec1 = ExtraCodecs.createExtraCodecRegistry().get(UUID.class);
		var codec2 = registry.get(UUID.class);
		Assertions.assertSame(Objects.requireNonNull(codec1).getClass(), Objects.requireNonNull(codec2).getClass());
	}
}
