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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.codec.common.collection.CollectionCodecs;
import net.mcparkour.octenace.mapper.TestMapper;
import net.mcparkour.octenace.model.value.TestModelValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectionCodecsTest {

	@SuppressWarnings("unused")
	private static final Collection<String> STRING_COLLECTION = List.of();
	@SuppressWarnings("unused")
	private static final Map<String, String> STRING_MAP = Map.of();
	private static final List<String> TEST_COLLECTION = List.of("foo", "bar", "foobar");
	private static final Map<String, String> TEST_MAP = Map.of("foo", "bar", "bar", "foo", "foobar", "barfoo");

	private TestMapper mapper;
	private Type stringCollectionType;
	private Type stringMapType;

	@BeforeEach
	public void setUp() {
		this.mapper = new TestMapper();
		this.stringCollectionType = Reflections.getField(CollectionCodecsTest.class, "STRING_COLLECTION").getGenericType();
		this.stringMapType = Reflections.getField(CollectionCodecsTest.class, "STRING_MAP").getGenericType();
	}

	@Test
	public void testArrayCodecEncode() {
		CommonCodec<Object[]> codec = CollectionCodecs.ARRAY_CODEC;
		String[] array = {"foo", "bar", "foobar"};
		var encoded = codec.encode(array, String[].class, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(List.of(array), value);
	}

	@Test
	public void testArrayCodecDecode() {
		CommonCodec<Object[]> codec = CollectionCodecs.ARRAY_CODEC;
		String[] array = {"foo", "bar", "foobar"};
		TestModelValue value = new TestModelValue(List.of(array));
		var decoded = codec.decode(value, String[].class, this.mapper);
		Assertions.assertArrayEquals(array, decoded);
	}

	@Test
	public void testArrayListCodecEncode() {
		CommonCodec<Collection<?>> codec = CollectionCodecs.ARRAY_LIST_CODEC;
		List<String> list = new ArrayList<>(3);
		list.addAll(TEST_COLLECTION);
		var encoded = codec.encode(list, ArrayList.class, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(list, value);
	}

	@Test
	public void testArrayListCodecDecode() {
		CommonCodec<Collection<?>> codec = CollectionCodecs.ARRAY_LIST_CODEC;
		List<Object> list = new ArrayList<>(3);
		list.addAll(TEST_COLLECTION);
		TestModelValue value = new TestModelValue(list);
		var decoded = codec.decode(value, this.stringCollectionType, this.mapper);
		Assertions.assertEquals(list, decoded);
	}

	@Test
	public void testHashSetCodecEncode() {
		CommonCodec<Collection<?>> codec = CollectionCodecs.HASH_SET_CODEC;
		Set<String> set = new HashSet<>(3);
		set.addAll(TEST_COLLECTION);
		var encoded = codec.encode(set, HashSet.class, this.mapper);
		Object value = encoded.getValue();
		Iterable<?> iterable = (Iterable<?>) value;
		Assertions.assertIterableEquals(set, iterable);
	}

	@Test
	public void testHashSetCodecDecode() {
		CommonCodec<Collection<?>> codec = CollectionCodecs.HASH_SET_CODEC;
		Set<Object> set = new HashSet<>(3);
		set.addAll(TEST_COLLECTION);
		List<Object> objects = List.copyOf(set);
		TestModelValue value = new TestModelValue(objects);
		var decoded = codec.decode(value, this.stringCollectionType, this.mapper);
		Assertions.assertEquals(set, decoded);
	}

	@Test
	public void testLinkedHashSetCodecEncode() {
		CommonCodec<Collection<?>> codec = CollectionCodecs.LINKED_HASH_SET_CODEC;
		Set<String> set = new LinkedHashSet<>(3);
		set.addAll(TEST_COLLECTION);
		var encoded = codec.encode(set, LinkedHashSet.class, this.mapper);
		Object value = encoded.getValue();
		Iterable<?> iterable = (Iterable<?>) value;
		Assertions.assertIterableEquals(set, iterable);
	}

	@Test
	public void testLinkedHashSetCodecDecode() {
		CommonCodec<Collection<?>> codec = CollectionCodecs.LINKED_HASH_SET_CODEC;
		Set<Object> set = new LinkedHashSet<>(3);
		set.addAll(TEST_COLLECTION);
		List<Object> objects = List.copyOf(set);
		TestModelValue value = new TestModelValue(objects);
		var decoded = codec.decode(value, this.stringCollectionType, this.mapper);
		Assertions.assertEquals(set, decoded);
	}

	@Test
	public void testHashMapCodecEncode() {
		CommonCodec<Map<?, ?>> codec = CollectionCodecs.HASH_MAP_CODEC;
		Map<String, String> map = new HashMap<>(3);
		map.putAll(TEST_MAP);
		var encoded = codec.encode(map, HashMap.class, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(map, value);
	}

	@Test
	public void testHashMapCodecDecode() {
		CommonCodec<Map<?, ?>> codec = CollectionCodecs.HASH_MAP_CODEC;
		Map<Object, Object> map = new HashMap<>(3);
		map.putAll(TEST_MAP);
		TestModelValue value = new TestModelValue(map);
		var decoded = codec.decode(value, this.stringMapType, this.mapper);
		Assertions.assertEquals(map, decoded);
	}

	@Test
	public void testLinkedHashMapCodecEncode() {
		CommonCodec<Map<?, ?>> codec = CollectionCodecs.LINKED_HASH_MAP_CODEC;
		Map<String, String> map = new LinkedHashMap<>(3);
		map.putAll(TEST_MAP);
		var encoded = codec.encode(map, LinkedHashMap.class, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(map, value);
	}

	@Test
	public void testLinkedHashMapCodecDecode() {
		CommonCodec<Map<?, ?>> codec = CollectionCodecs.LINKED_HASH_MAP_CODEC;
		Map<Object, Object> map = new LinkedHashMap<>(3);
		map.putAll(TEST_MAP);
		TestModelValue value = new TestModelValue(map);
		var decoded = codec.decode(value, this.stringMapType, this.mapper);
		Assertions.assertEquals(map, decoded);
	}
}
