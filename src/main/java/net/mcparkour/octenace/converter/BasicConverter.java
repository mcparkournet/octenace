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

package net.mcparkour.octenace.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.condition.FieldCondition;
import net.mcparkour.octenace.model.array.ModelArrayFactory;
import net.mcparkour.octenace.model.object.ModelObject;
import net.mcparkour.octenace.model.object.ModelObjectFactory;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import net.mcparkour.octenace.util.LetterCaseTransformer;
import org.jetbrains.annotations.Nullable;

public class BasicConverter<O, A, V> implements Converter<O, A, V> {

	private ModelObjectFactory<O, A, V> modelObjectFactory;
	private ModelArrayFactory<O, A, V> modelArrayFactory;
	private ModelValueFactory<O, A, V> modelValueFactory;
	private LetterCase defaultKeysLetterCase;
	private List<FieldCondition> fieldConditions;
	private CodecRegistry codecRegistry;
	private NameAnnotationSupplier<Annotation> nameAnnotationSupplier;

	public BasicConverter(ModelObjectFactory<O, A, V> modelObjectFactory, ModelArrayFactory<O, A, V> modelArrayFactory, ModelValueFactory<O, A, V> modelValueFactory, LetterCase defaultKeysLetterCase, List<FieldCondition> fieldConditions, CodecRegistry codecRegistry, NameAnnotationSupplier<Annotation> nameAnnotationSupplier) {
		this.modelObjectFactory = modelObjectFactory;
		this.modelArrayFactory = modelArrayFactory;
		this.modelValueFactory = modelValueFactory;
		this.defaultKeysLetterCase = defaultKeysLetterCase;
		this.fieldConditions = fieldConditions;
		this.codecRegistry = codecRegistry;
		this.nameAnnotationSupplier = nameAnnotationSupplier;
	}

	@Override
	public ModelObject<O, A, V> fromConfiguration(Object configuration) {
		ModelObject<O, A, V> object = this.modelObjectFactory.createEmptyModelObject();
		Class<?> configurationType = configuration.getClass();
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Object fieldValue = Reflections.getFieldValue(field, configuration);
				Type fieldType = field.getGenericType();
				ModelValue<O, A, V> value = toModelValue(fieldValue, fieldType);
				object.setValue(fieldName, value);
			}
		}
		return object;
	}

	@Override
	public ModelValue<O, A, V> toModelValue(@Nullable Object object, Type type) {
		if (object == null) {
			return this.modelValueFactory.createNullModelValue();
		}
		Codec<Object> codec = getObjectCodec(type);
		if (codec == null) {
			ModelObject<O, A, V> modelObject = fromConfiguration(object);
			return this.modelValueFactory.createObjectModelValue(modelObject);
		}
		return codec.encode(object, type, this);
	}

	@Override
	public <T> T toConfiguration(ModelObject<O, A, V> object, Class<T> configurationType) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(configurationType);
		T instance = Reflections.newInstance(constructor);
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				ModelValue<O, A, V> value = object.getValue(fieldName);
				Type fieldType = field.getGenericType();
				Object rawObject = toObject(value, fieldType);
				Reflections.setFieldValue(field, instance, rawObject);
			}
		}
		return instance;
	}

	@Override
	@Nullable
	public Object toObject(ModelValue<O, A, V> value, Type type) {
		if (value.isNull()) {
			return null;
		}
		Codec<Object> codec = getObjectCodec(type);
		if (codec == null) {
			if (!value.isObject()) {
				throw new CodecNotFoundException(type);
			}
			O rawObject = value.asObject();
			ModelObject<O, A, V> object = this.modelObjectFactory.createModelObject(rawObject);
			Type rawType = Types.getRawType(type);
			Class<?> classType = Types.asClassType(rawType);
			return toConfiguration(object, classType);
		}
		return codec.decode(value, type, this);
	}

	@Override
	public boolean isFieldValid(Field field) {
		return this.fieldConditions.stream()
			.allMatch(condition -> condition.check(field));
	}

	@Override
	public String getFieldName(Field field) {
		Class<Annotation> annotationType = this.nameAnnotationSupplier.getAnnotationType();
		Annotation annotation = field.getAnnotation(annotationType);
		if (annotation != null) {
			return this.nameAnnotationSupplier.supply(annotation);
		}
		String name = field.getName();
		if (this.defaultKeysLetterCase == LetterCase.KEBAB) {
			return LetterCaseTransformer.toKebabCase(name);
		}
		if (this.defaultKeysLetterCase == LetterCase.SNAKE) {
			return LetterCaseTransformer.toSnakeCase(name);
		}
		return name;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	private Codec<Object> getObjectCodec(Type type) {
		Type rawType = Types.getRawType(type);
		Class<?> classType = Types.asClassType(rawType);
		Codec<?> codec = this.codecRegistry.get(classType);
		if (codec == null) {
			return null;
		}
		return (Codec<Object>) codec;
	}

	@Override
	public ModelObjectFactory<O, A, V> getModelObjectFactory() {
		return this.modelObjectFactory;
	}

	@Override
	public ModelArrayFactory<O, A, V> getModelArrayFactory() {
		return this.modelArrayFactory;
	}

	@Override
	public ModelValueFactory<O, A, V> getModelValueFactory() {
		return this.modelValueFactory;
	}

	public LetterCase getDefaultKeysLetterCase() {
		return this.defaultKeysLetterCase;
	}

	public List<FieldCondition> getFieldConditions() {
		return List.copyOf(this.fieldConditions);
	}

	public CodecRegistry getCodecRegistry() {
		return this.codecRegistry;
	}
}
