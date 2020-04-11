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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;

public class MapCodec implements CommonCodec<Map<?, ?>> {

	private CollectionSupplier<? extends Map<Object, Object>> mapSupplier;

	public MapCodec(CollectionSupplier<? extends Map<Object, Object>> mapSupplier) {
		this.mapSupplier = mapSupplier;
	}

	@Override
	public <O, A, V> DocumentValue<O, A, V> toDocument(Map<?, ?> object, Type type, Mapper<O, A, V> mapper) {
		DocumentObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		DocumentObject<O, A, V> documentObject = objectFactory.createEmptyObject();
		for (Map.Entry<?, ?> entry : object.entrySet()) {
			DocumentValue<O, A, V> modelKey = getModelKey(entry, mapper);
			DocumentValue<O, A, V> documentValue = getModelValue(entry, mapper);
			documentObject.set(modelKey, documentValue);
		}
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		return valueFactory.createObjectValue(documentObject);
	}

	private <O, A, V> DocumentValue<O, A, V> getModelKey(Map.Entry<?, ?> entry, Mapper<O, A, V> mapper) {
		Object key = entry.getKey();
		Class<?> keyType = key.getClass();
		return mapper.toDocument(key, keyType);
	}

	private <O, A, V> DocumentValue<O, A, V> getModelValue(Map.Entry<?, ?> entry, Mapper<O, A, V> mapper) {
		Object value = entry.getValue();
		Class<?> valueType = value.getClass();
		return mapper.toDocument(value, valueType);
	}

	@Override
	public <O, A, V> Map<?, ?> toObject(DocumentValue<O, A, V> document, Type type, Mapper<O, A, V> mapper) {
		DocumentObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		O rawObject = document.asObject();
		DocumentObject<O, A, V> object = objectFactory.createObject(rawObject);
		Type[] genericTypes = getGenericTypes(type);
		Type keyType = genericTypes[0];
		Type valueType = genericTypes[1];
		int size = object.getSize();
		Map<Object, Object> map = this.mapSupplier.supply(size);
		for (Map.Entry<DocumentValue<O, A, V>, DocumentValue<O, A, V>> entry : object) {
			DocumentValue<O, A, V> entryKey = entry.getKey();
			Object mapKey = mapper.toObject(entryKey, keyType);
			DocumentValue<O, A, V> entryValue = entry.getValue();
			Object mapValue = mapper.toObject(entryValue, valueType);
			map.put(mapKey, mapValue);
		}
		return map;
	}

	private Type[] getGenericTypes(Type type) {
		ParameterizedType parameterizedType = Types.asParametrizedType(type);
		return parameterizedType.getActualTypeArguments();
	}
}
