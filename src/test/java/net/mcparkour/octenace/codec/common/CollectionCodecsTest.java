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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.mcparkour.octenace.codec.common.collection.ArrayCodec;
import net.mcparkour.octenace.codec.common.collection.ArrayListCodec;
import net.mcparkour.octenace.codec.common.collection.HashMapCodec;
import net.mcparkour.octenace.codec.common.collection.HashSetCodec;
import net.mcparkour.octenace.codec.common.collection.LinkedHashMapCodec;
import net.mcparkour.octenace.codec.common.collection.LinkedHashSetCodec;
import net.mcparkour.octenace.document.value.TestDocumentValue;
import net.mcparkour.octenace.mapper.TestMapper;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectionCodecsTest {

	private static final List<String> TEST_COLLECTION = List.of("foo", "bar", "foobar");
	private static final Map<String, String> TEST_MAP = Map.of("foo", "bar", "bar", "foo", "foobar", "barfoo");

	private TestMapper<?> mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new TestMapper<>(Object.class);
	}

	@Test
	public void testArrayCodecEncode() {
		ArrayCodec<Map<Object, Object>, List<Object>, Object> codec = new ArrayCodec<>();
		String[] array = {"foo", "bar", "foobar"};
		var metadata = codec.getMetadata(new TypeMetadata(String[].class), this.mapper);
		var encoded = codec.toDocument(array, metadata, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(List.of(array), value);
	}

	@Test
	public void testArrayCodecDecode() {
		ArrayCodec<Map<Object, Object>, List<Object>, Object> codec = new ArrayCodec<>();
		String[] array = {"foo", "bar", "foobar"};
		TestDocumentValue value = new TestDocumentValue(List.of(array));
		var metadata = codec.getMetadata(new TypeMetadata(String[].class), this.mapper);
		var decoded = codec.toObject(value, metadata, this.mapper);
		Assertions.assertArrayEquals(array, decoded);
	}

	@Test
	public void testArrayListCodecEncode() {
		ArrayListCodec<Map<Object, Object>, List<Object>, Object> codec = new ArrayListCodec<>();
		ArrayList<String> list = new ArrayList<>(3);
		list.addAll(TEST_COLLECTION);
		var metadata = codec.getMetadata(new TypeMetadata(ArrayList.class, new GenericTypes(String.class)), this.mapper);
		var encoded = codec.toDocument(list, metadata, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(list, value);
	}

	@Test
	public void testArrayListCodecDecode() {
		ArrayListCodec<Map<Object, Object>, List<Object>, Object> codec = new ArrayListCodec<>();
		List<Object> list = new ArrayList<>(3);
		list.addAll(TEST_COLLECTION);
		TestDocumentValue value = new TestDocumentValue(list);
		TypeMetadata type = new TypeMetadata(ArrayList.class, new GenericTypes(String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var decoded = codec.toObject(value, metadata, this.mapper);
		Assertions.assertEquals(list, decoded);
	}

	@Test
	public void testHashSetCodecEncode() {
		HashSetCodec<Map<Object, Object>, List<Object>, Object> codec = new HashSetCodec<>();
		HashSet<String> set = new HashSet<>(3);
		set.addAll(TEST_COLLECTION);
		TypeMetadata type = new TypeMetadata(HashSet.class, new GenericTypes(String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var encoded = codec.toDocument(set, metadata, this.mapper);
		Object value = encoded.getValue();
		Iterable<?> iterable = (Iterable<?>) value;
		Assertions.assertIterableEquals(set, iterable);
	}

	@Test
	public void testHashSetCodecDecode() {
		HashSetCodec<Map<Object, Object>, List<Object>, Object> codec = new HashSetCodec<>();
		Set<Object> set = new HashSet<>(3);
		set.addAll(TEST_COLLECTION);
		List<Object> objects = List.copyOf(set);
		TestDocumentValue value = new TestDocumentValue(objects);
		TypeMetadata type = new TypeMetadata(HashSet.class, new GenericTypes(String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var decoded = codec.toObject(value, metadata, this.mapper);
		Assertions.assertEquals(set, decoded);
	}

	@Test
	public void testLinkedHashSetCodecEncode() {
		LinkedHashSetCodec<Map<Object, Object>, List<Object>, Object> codec = new LinkedHashSetCodec<>();
		LinkedHashSet<String> set = new LinkedHashSet<>(3);
		set.addAll(TEST_COLLECTION);
		TypeMetadata type = new TypeMetadata(LinkedHashSet.class, new GenericTypes(String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var encoded = codec.toDocument(set, metadata, this.mapper);
		Object value = encoded.getValue();
		Iterable<?> iterable = (Iterable<?>) value;
		Assertions.assertIterableEquals(set, iterable);
	}

	@Test
	public void testLinkedHashSetCodecDecode() {
		LinkedHashSetCodec<Map<Object, Object>, List<Object>, Object> codec = new LinkedHashSetCodec<>();
		Set<Object> set = new LinkedHashSet<>(3);
		set.addAll(TEST_COLLECTION);
		List<Object> objects = List.copyOf(set);
		TestDocumentValue value = new TestDocumentValue(objects);
		TypeMetadata type = new TypeMetadata(LinkedHashSet.class, new GenericTypes(String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var decoded = codec.toObject(value, metadata, this.mapper);
		Assertions.assertEquals(set, decoded);
	}

	@Test
	public void testHashMapCodecEncode() {
		HashMapCodec<Map<Object, Object>, List<Object>, Object> codec = new HashMapCodec<>();
		HashMap<String, String> map = new HashMap<>(3);
		map.putAll(TEST_MAP);
		TypeMetadata type = new TypeMetadata(HashMap.class, new GenericTypes(String.class, String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var encoded = codec.toDocument(map, metadata, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(map, value);
	}

	@Test
	public void testHashMapCodecDecode() {
		HashMapCodec<Map<Object, Object>, List<Object>, Object> codec = new HashMapCodec<>();
		Map<Object, Object> map = new HashMap<>(3);
		map.putAll(TEST_MAP);
		TestDocumentValue value = new TestDocumentValue(map);
		TypeMetadata type = new TypeMetadata(HashMap.class, new GenericTypes(String.class, String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var decoded = codec.toObject(value, metadata, this.mapper);
		Assertions.assertEquals(map, decoded);
	}

	@Test
	public void testLinkedHashMapCodecEncode() {
		LinkedHashMapCodec<Map<Object, Object>, List<Object>, Object> codec = new LinkedHashMapCodec<>();
		LinkedHashMap<String, String> map = new LinkedHashMap<>(3);
		map.putAll(TEST_MAP);
		TypeMetadata type = new TypeMetadata(LinkedHashMap.class, new GenericTypes(String.class, String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var encoded = codec.toDocument(map, metadata, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(map, value);
	}

	@Test
	public void testLinkedHashMapCodecDecode() {
		LinkedHashMapCodec<Map<Object, Object>, List<Object>, Object> codec = new LinkedHashMapCodec<>();
		Map<Object, Object> map = new LinkedHashMap<>(3);
		map.putAll(TEST_MAP);
		TestDocumentValue value = new TestDocumentValue(map);
		TypeMetadata type = new TypeMetadata(LinkedHashMap.class, new GenericTypes(String.class, String.class));
		var metadata = codec.getMetadata(type, this.mapper);
		var decoded = codec.toObject(value, metadata, this.mapper);
		Assertions.assertEquals(map, decoded);
	}

	private static final class GenericTypes implements ParameterizedType {

		private Type[] genericTypes;

		private GenericTypes(Type... genericTypes) {
			this.genericTypes = genericTypes;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return this.genericTypes;
		}

		@Override
		public Type getRawType() {
			return void.class;
		}

		@Override
		public Type getOwnerType() {
			return void.class;
		}
	}
}
