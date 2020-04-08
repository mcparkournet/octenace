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
import java.util.LinkedHashMap;
import java.util.Map;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.model.object.ModelObject;
import net.mcparkour.octenace.model.object.ModelObjectFactory;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class MapCodec implements CommonCodec<Map<?, ?>> {

	@Override
	public <O, A, V> ModelValue<O, A, V> encode(Map<?, ?> value, Type type, Mapper<O, A, V> mapper) {
		ModelObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		ModelObject<O, A, V> modelObject = objectFactory.createEmptyObject();
		for (Map.Entry<?, ?> entry : value.entrySet()) {
			ModelValue<O, A, V> modelKey = getModelKey(entry, mapper);
			String key = modelKey.asString();
			ModelValue<O, A, V> modelValue = getModelValue(entry, mapper);
			modelObject.set(key, modelValue);
		}
		ModelValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		return valueFactory.createObjectValue(modelObject);
	}

	private <O, A, V> ModelValue<O, A, V> getModelKey(Map.Entry<?, ?> entry, Mapper<O, A, V> mapper) {
		Object key = entry.getKey();
		Class<?> keyType = key.getClass();
		return mapper.fromDocument(key, keyType);
	}

	private <O, A, V> ModelValue<O, A, V> getModelValue(Map.Entry<?, ?> entry, Mapper<O, A, V> mapper) {
		Object value = entry.getValue();
		Class<?> valueType = value.getClass();
		return mapper.fromDocument(value, valueType);
	}

	@Override
	@Nullable
	public <O, A, V> Map<?, ?> decode(ModelValue<O, A, V> value, Type type, Mapper<O, A, V> mapper) {
		ModelObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		O rawObject = value.asObject();
		ModelObject<O, A, V> object = objectFactory.createObject(rawObject);
		ModelValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		Type[] genericTypes = getGenericTypes(type);
		Type keyType = genericTypes[0];
		Type valueType = genericTypes[1];
		int size = object.getSize();
		Map<Object, Object> map = new LinkedHashMap<>(size);
		for (Map.Entry<String, ModelValue<O, A, V>> entry : object) {
			String key = entry.getKey();
			ModelValue<O, A, V> keyModelValue = valueFactory.createValue(key);
			Object mapKey = mapper.toDocument(keyModelValue, keyType);
			ModelValue<O, A, V> entryValue = entry.getValue();
			Object mapValue = mapper.toDocument(entryValue, valueType);
			map.put(mapKey, mapValue);
		}
		return map;
	}

	private Type[] getGenericTypes(Type type) {
		ParameterizedType parameterizedType = Types.asParametrizedType(type);
		return parameterizedType.getActualTypeArguments();
	}
}
