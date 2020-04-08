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

package net.mcparkour.octenace.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.condition.FieldCondition;
import net.mcparkour.octenace.mapper.naming.NameConverter;
import net.mcparkour.octenace.model.array.ModelArrayFactory;
import net.mcparkour.octenace.model.object.ModelObject;
import net.mcparkour.octenace.model.object.ModelObjectFactory;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class CommonMapper<O, A, V> implements Mapper<O, A, V> {

	private ModelObjectFactory<O, A, V> objectFactory;
	private ModelArrayFactory<O, A, V> arrayFactory;
	private ModelValueFactory<O, A, V> valueFactory;
	private NameConverter nameConverter;
	private List<FieldCondition> fieldConditions;
	private CodecRegistry codecRegistry;
	private NameAnnotationSupplier<? extends Annotation> nameAnnotationSupplier;

	public CommonMapper(ModelObjectFactory<O, A, V> objectFactory, ModelArrayFactory<O, A, V> arrayFactory, ModelValueFactory<O, A, V> valueFactory, NameConverter nameConverter, List<FieldCondition> fieldConditions, CodecRegistry codecRegistry, NameAnnotationSupplier<? extends Annotation> nameAnnotationSupplier) {
		this.objectFactory = objectFactory;
		this.arrayFactory = arrayFactory;
		this.valueFactory = valueFactory;
		this.nameConverter = nameConverter;
		this.fieldConditions = fieldConditions;
		this.codecRegistry = codecRegistry;
		this.nameAnnotationSupplier = nameAnnotationSupplier;
	}

	@Override
	public ModelObject<O, A, V> fromDocument(Object document) {
		ModelObject<O, A, V> object = this.objectFactory.createEmptyObject();
		Class<?> configurationType = document.getClass();
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Object fieldValue = Reflections.getFieldValue(field, document);
				Type fieldType = field.getGenericType();
				ModelValue<O, A, V> value = fromDocument(fieldValue, fieldType);
				object.set(fieldName, value);
			}
		}
		return object;
	}

	@Override
	public ModelValue<O, A, V> fromDocument(@Nullable Object document, Type type) {
		if (document == null) {
			return this.valueFactory.createNullValue();
		}
		Optional<Codec<O, A, V, Object>> codecOptional = getObjectCodec(type);
		if (codecOptional.isPresent()) {
			Codec<O, A, V, Object> codec = codecOptional.get();
			return codec.encode(document, type, this);
		}
		ModelObject<O, A, V> modelObject = fromDocument(document);
		return this.valueFactory.createObjectValue(modelObject);
	}

	@Override
	public <T> T toDocument(ModelObject<O, A, V> object, Class<T> configurationType) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(configurationType);
		T instance = Reflections.newInstance(constructor);
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				ModelValue<O, A, V> value = object.get(fieldName);
				Type fieldType = field.getGenericType();
				Object rawObject = toDocument(value, fieldType);
				Reflections.setFieldValue(field, instance, rawObject);
			}
		}
		return instance;
	}

	@Override
	@Nullable
	public Object toDocument(ModelValue<O, A, V> value, Type type) {
		if (value.isNull()) {
			return null;
		}
		Optional<Codec<O, A, V, Object>> codecOptional = getObjectCodec(type);
		if (codecOptional.isPresent()) {
			Codec<O, A, V, Object> codec = codecOptional.get();
			return codec.decode(value, type, this);
		}
		if (!value.isObject()) {
			throw new CodecNotFoundException(type);
		}
		O rawObject = value.asObject();
		ModelObject<O, A, V> object = this.objectFactory.createObject(rawObject);
		Type rawType = Types.getRawType(type);
		Class<?> classType = Types.asClassType(rawType);
		return toDocument(object, classType);
	}

	@Override
	public boolean isFieldValid(Field field) {
		return this.fieldConditions.stream()
			.allMatch(condition -> condition.check(field));
	}

	@Override
	public String getFieldName(Field field) {
		Class<? extends Annotation> annotationType = this.nameAnnotationSupplier.getAnnotationType();
		if (field.isAnnotationPresent(annotationType)) {
			return this.nameAnnotationSupplier.supply(field);
		}
		String name = field.getName();
		return this.nameConverter.convert(name);
	}

	@SuppressWarnings("unchecked")
	private Optional<Codec<O, A, V, Object>> getObjectCodec(Type type) {
		Type rawType = Types.getRawType(type);
		Class<?> classType = Types.asClassType(rawType);
		Codec<?, ?, ?, ?> codec = this.codecRegistry.get(classType);
		if (codec == null) {
			return Optional.empty();
		}
		return Optional.of((Codec<O, A, V, Object>) codec);
	}

	@Override
	public ModelObjectFactory<O, A, V> getObjectFactory() {
		return this.objectFactory;
	}

	@Override
	public ModelArrayFactory<O, A, V> getArrayFactory() {
		return this.arrayFactory;
	}

	@Override
	public ModelValueFactory<O, A, V> getValueFactory() {
		return this.valueFactory;
	}

	@Override
	public NameConverter getNameConverter() {
		return this.nameConverter;
	}

	public List<FieldCondition> getFieldConditions() {
		return List.copyOf(this.fieldConditions);
	}

	public CodecRegistry getCodecRegistry() {
		return this.codecRegistry;
	}
}
