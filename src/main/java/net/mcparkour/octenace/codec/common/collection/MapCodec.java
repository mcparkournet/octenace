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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.mapper.metadata.Element;
import net.mcparkour.octenace.mapper.metadata.MapMetadata;
import net.mcparkour.octenace.mapper.metadata.Metadata;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;

public abstract class MapCodec<O, A, V, T extends Map<?, ?>> implements Codec<O, A, V, MapMetadata<O, A, V>, T> {

	@Override
	public DocumentValue<O, A, V> toDocument(T object, MapMetadata<O, A, V> metadata, Mapper<O, A, V, ?> mapper) {
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		DocumentObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		int size = object.size();
		DocumentObject<O, A, V> documentObject = objectFactory.createEmptyObject(size);
		Element<O, A, V> keyElement = metadata.getKeyElement();
		Element<O, A, V> valueElement = metadata.getValueElement();
		Codec<O, A, V, Metadata, Object> keyCodec = keyElement.getObjectCodec();
		Codec<O, A, V, Metadata, Object> valueCodec = valueElement.getObjectCodec();
		Metadata keyElementMetadata = keyElement.getMetadata();
		Metadata valueElementMetadata = valueElement.getMetadata();
		Set<? extends Entry<?, ?>> entries = object.entrySet();
		for (Map.Entry<?, ?> entry : entries) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			DocumentValue<O, A, V> valueKey = keyCodec.toDocument(key, keyElementMetadata, mapper);
			DocumentValue<O, A, V> valueValue = valueCodec.toDocument(value, valueElementMetadata, mapper);
			documentObject.set(valueKey, valueValue);
		}
		return valueFactory.createObjectValue(documentObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T toObject(DocumentValue<O, A, V> document, MapMetadata<O, A, V> metadata, Mapper<O, A, V, ?> mapper) {
		DocumentObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		O rawObject = document.asObject();
		DocumentObject<O, A, V> object = objectFactory.createObject(rawObject);
		Element<O, A, V> keyElement = metadata.getKeyElement();
		Element<O, A, V> valueElement = metadata.getValueElement();
		Codec<O, A, V, Metadata, Object> keyCodec = keyElement.getObjectCodec();
		Codec<O, A, V, Metadata, Object> valueCodec = valueElement.getObjectCodec();
		Metadata keyElementMetadata = keyElement.getMetadata();
		Metadata valueElementMetadata = valueElement.getMetadata();
		int size = object.getSize();
		Map<Object, Object> map = (Map<Object, Object>) createMap(size);
		for (var entry : object) {
			DocumentValue<O, A, V> entryKey = entry.getKey();
			DocumentValue<O, A, V> entryValue = entry.getValue();
			Object key = keyCodec.toObject(entryKey, keyElementMetadata, mapper);
			Object value = valueCodec.toObject(entryValue, valueElementMetadata, mapper);
			map.put(key, value);
		}
		return (T) map;
	}

	public abstract T createMap(int size);

	@Override
	public MapMetadata<O, A, V> getMetadata(TypeMetadata type, Mapper<O, A, V, ?> mapper) {
		Entry<Class<?>, Class<?>> genericTypes = type.getGenericTypesPair();
		Class<?> keyType = genericTypes.getKey();
		Class<?> valueType = genericTypes.getValue();
		var keyCodec = mapper.getObjectCodec(keyType);
		var valueCodec = mapper.getObjectCodec(valueType);
		Metadata keyMetadata = keyCodec.getMetadata(new TypeMetadata(keyType), mapper);
		Metadata valueMetadata = valueCodec.getMetadata(new TypeMetadata(valueType), mapper);
		Element<O, A, V> keyElement = new Element<>(keyType, keyCodec, keyMetadata);
		Element<O, A, V> valueElement = new Element<>(valueType, valueCodec, valueMetadata);
		return new MapMetadata<>(keyElement, valueElement);
	}
}
