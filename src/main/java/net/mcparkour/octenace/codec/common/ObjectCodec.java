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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.mapper.Mapper;

public class ObjectCodec implements CommonCodec<Object> {

	@Override
	public <O, A, V> DocumentValue<O, A, V> toDocument(Object object, Type type, Mapper<O, A, V> mapper) {
		DocumentObject<O, A, V> documentObject = toDocument(object, mapper);
		return mapper.getValueFactory().createObjectValue(documentObject);
	}

	public <O, A, V> DocumentObject<O, A, V> toDocument(Object object, Mapper<O, A, V> mapper) {
		DocumentObject<O, A, V> emptyObject = mapper.getObjectFactory().createEmptyObject();
		Class<?> configurationType = object.getClass();
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (mapper.isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = mapper.getFieldName(field);
				DocumentValue<O, A, V> modelFieldName = mapper.getValueFactory().createValue(fieldName);
				Object fieldValue = Reflections.getFieldValue(field, object);
				Type fieldType = field.getGenericType();
				DocumentValue<O, A, V> value = mapper.toDocument(fieldValue, fieldType);
				emptyObject.set(modelFieldName, value);
			}
		}
		return emptyObject;
	}

	@Override
	public <O, A, V> Object toObject(DocumentValue<O, A, V> document, Type type, Mapper<O, A, V> mapper) {
		O rawObject = document.asObject();
		DocumentObject<O, A, V> object = mapper.getObjectFactory().createObject(rawObject);
		Type rawType = Types.getRawType(type);
		Class<?> classType = Types.asClassType(rawType);
		return toObject(object, classType, mapper);
	}

	public <O, A, V, T> T toObject(DocumentObject<O, A, V> document, Class<T> objectType, Mapper<O, A, V> mapper) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(objectType);
		T instance = Reflections.newInstance(constructor);
		Field[] fields = objectType.getDeclaredFields();
		for (Field field : fields) {
			if (mapper.isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = mapper.getFieldName(field);
				DocumentValue<O, A, V> modelFieldName = mapper.getValueFactory().createValue(fieldName);
				DocumentValue<O, A, V> value = document.get(modelFieldName);
				Type fieldType = field.getGenericType();
				var codecAnnotation = field.getAnnotation(net.mcparkour.octenace.annotation.Codec.class);
				Object rawObject = codecAnnotation == null ?
					mapper.toObject(value, fieldType) :
					mapper.toObject(value, fieldType, codecAnnotation.value());
				Reflections.setFieldValue(field, instance, rawObject);
			}
		}
		return instance;
	}
}
