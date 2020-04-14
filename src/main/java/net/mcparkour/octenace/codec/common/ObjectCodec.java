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
import java.util.ArrayList;
import java.util.List;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.mapper.metadata.Element;
import net.mcparkour.octenace.mapper.metadata.Metadata;
import net.mcparkour.octenace.mapper.metadata.ObjectMetadata;
import net.mcparkour.octenace.mapper.metadata.Property;

public class ObjectCodec<O, A, V> implements Codec<O, A, V, ObjectMetadata<O, A, V>, Object> {

	@Override
	public DocumentValue<O, A, V> toDocument(Object object, ObjectMetadata<O, A, V> metadata, Mapper<O, A, V> mapper) {
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		DocumentObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		DocumentObject<O, A, V> documentObject = objectFactory.createEmptyObject();
		List<Property<O, A, V>> properties = metadata.getProperties();
		for (Property<O, A, V> property : properties) {
			Element<O, A, V> element = property.getElement();
			String propertyName = property.getName();
			DocumentValue<O, A, V> modelFieldName = valueFactory.createValue(propertyName);
			Codec<O, A, V, Metadata, Object> codec = element.getObjectCodec();
			Field field = property.getField();
			Object fieldValue = Reflections.getFieldValue(field, object);
			Metadata propertyMetadata = element.getMetadata();
			DocumentValue<O, A, V> value = mapper.toDocument(codec, fieldValue, propertyMetadata);
			documentObject.set(modelFieldName, value);
		}
		return valueFactory.createObjectValue(documentObject);
	}

	@Override
	public Object toObject(DocumentValue<O, A, V> document, ObjectMetadata<O, A, V> metadata, Mapper<O, A, V> mapper) {
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		DocumentObjectFactory<O, A, V> objectFactory = mapper.getObjectFactory();
		O rawObject = document.asObject();
		DocumentObject<O, A, V> object = objectFactory.createObject(rawObject);
		Class<?> type = metadata.getType();
		Constructor<?> constructor = Reflections.getSerializationConstructor(type);
		Object instance = Reflections.newInstance(constructor);
		List<Property<O, A, V>> properties = metadata.getProperties();
		for (Property<O, A, V> property : properties) {
			String propertyName = property.getName();
			DocumentValue<O, A, V> modelFieldName = valueFactory.createValue(propertyName);
			DocumentValue<O, A, V> documentValue = object.get(modelFieldName);
			Element<O, A, V> element = property.getElement();
			Codec<O, A, V, Metadata, Object> codec = element.getObjectCodec();
			Metadata propertyMetadata = element.getMetadata();
			Field field = property.getField();
			Object value = mapper.toObject(codec, documentValue, propertyMetadata);
			Reflections.setFieldValue(field, instance, value);
		}
		return instance;
	}

	@Override
	public ObjectMetadata<O, A, V> createMetadata(Type type, Mapper<O, A, V> mapper) {
		Class<?> classType = Types.getRawClassType(type);
		Field[] fields = classType.getDeclaredFields();
		int length = fields.length;
		List<Property<O, A, V>> properties = new ArrayList<>(length);
		for (Field field : fields) {
			if (mapper.isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = mapper.getFieldName(field);
				Type fieldGenericType = field.getGenericType();
				Class<?> fieldType = field.getType();
				Class<?> codecType = getCodecType(field);
				var codec = mapper.getObjectCodec(codecType);
				Metadata metadata = mapper.createMetadata(codec, fieldGenericType);
				Element<O, A, V> element = new Element<>(fieldType, codec, metadata);
				Property<O, A, V> property = new Property<>(fieldName, field, element);
				properties.add(property);
			}
		}
		return new ObjectMetadata<>(classType, properties);
	}

	private Class<?> getCodecType(Field field) {
		var codecAnnotation = field.getAnnotation(net.mcparkour.octenace.annotation.Codec.class);
		if (codecAnnotation != null) {
			return codecAnnotation.value();
		}
		return field.getType();
	}
}
