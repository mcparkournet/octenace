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
import net.mcparkour.octenace.converter.Converter;
import net.mcparkour.octenace.model.object.ModelObject;
import net.mcparkour.octenace.model.object.ModelObjectFactory;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class MapCodec implements CommonCodec<Map<?, ?>> {

	@Override
	public <O, A, V> ModelValue<O, A, V> encode(Map<?, ?> object, Type type, Converter<O, A, V> converter) {
		ModelObjectFactory<O, A, V> objectFactory = converter.getModelObjectFactory();
		ModelObject<O, A, V> modelObject = objectFactory.createEmptyModelObject();
		for (Map.Entry<?, ?> entry : object.entrySet()) {
			ModelValue<O, A, V> modelKey = getModelKey(entry, converter);
			String key = modelKey.asString();
			ModelValue<O, A, V> modelValue = getModelValue(entry, converter);
			modelObject.setValue(key, modelValue);
		}
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		return valueFactory.createObjectModelValue(modelObject);
	}

	private <O, A, V> ModelValue<O, A, V> getModelKey(Map.Entry<?, ?> entry, Converter<O, A, V> converter) {
		Object key = entry.getKey();
		Class<?> keyType = key.getClass();
		return converter.toModelValue(key, keyType);
	}

	private <O, A, V> ModelValue<O, A, V> getModelValue(Map.Entry<?, ?> entry, Converter<O, A, V> converter) {
		Object value = entry.getValue();
		Class<?> valueType = value.getClass();
		return converter.toModelValue(value, valueType);
	}

	@Override
	@Nullable
	public <O, A, V> Map<?, ?> decode(ModelValue<O, A, V> value, Type type, Converter<O, A, V> converter) {
		ModelObjectFactory<O, A, V> objectFactory = converter.getModelObjectFactory();
		O rawObject = value.asObject();
		ModelObject<O, A, V> object = objectFactory.createModelObject(rawObject);
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		Type[] genericTypes = getGenericTypes(type);
		Type keyType = genericTypes[0];
		Type valueType = genericTypes[1];
		int size = object.getSize();
		Map<Object, Object> map = new LinkedHashMap<>(size);
		for (Map.Entry<String, ModelValue<O, A, V>> entry : object.getEntries()) {
			String key = entry.getKey();
			ModelValue<O, A, V> keyModelValue = valueFactory.createStringModelValue(key);
			Object mapKey = converter.toObject(keyModelValue, keyType);
			ModelValue<O, A, V> entryValue = entry.getValue();
			Object mapValue = converter.toObject(entryValue, valueType);
			map.put(mapKey, mapValue);
		}
		return map;
	}

	private Type[] getGenericTypes(Type type) {
		ParameterizedType parameterizedType = Types.asParametrizedType(type);
		return parameterizedType.getActualTypeArguments();
	}
}
