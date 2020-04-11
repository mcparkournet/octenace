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

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.annotation.Property;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.name.NameConverter;
import org.jetbrains.annotations.Nullable;

public class CommonMapper<O, A, V> implements Mapper<O, A, V> {

	private DocumentObjectFactory<O, A, V> objectFactory;
	private DocumentArrayFactory<O, A, V> arrayFactory;
	private DocumentValueFactory<O, A, V> valueFactory;
	private NameConverter nameConverter;
	private List<PropertyInvalidator> propertyInvalidators;
	private CodecRegistry codecRegistry;

	public CommonMapper(DocumentObjectFactory<O, A, V> objectFactory, DocumentArrayFactory<O, A, V> arrayFactory, DocumentValueFactory<O, A, V> valueFactory, NameConverter nameConverter, List<PropertyInvalidator> propertyInvalidators, CodecRegistry codecRegistry) {
		this.objectFactory = objectFactory;
		this.arrayFactory = arrayFactory;
		this.valueFactory = valueFactory;
		this.nameConverter = nameConverter;
		this.propertyInvalidators = propertyInvalidators;
		this.codecRegistry = codecRegistry;
	}

	@Override
	public DocumentObject<O, A, V> toDocument(Object object) {
		DocumentValue<O, A, V> document = toDocument(object, object.getClass());
		return this.objectFactory.createObject(document.asObject());
	}

	@Override
	public DocumentValue<O, A, V> toDocument(@Nullable Object object, Type type) {
		if (object == null) {
			return this.valueFactory.createNullValue();
		}
		Optional<Codec<O, A, V, Object>> codecOptional = getObjectCodec(type);
		if (codecOptional.isPresent()) {
			Codec<O, A, V, Object> codec = codecOptional.get();
			return codec.toDocument(object, type, this);
		}
		throw new CodecNotFoundException(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T toObject(DocumentObject<O, A, V> document, Class<T> objectType) {
		DocumentValue<O, A, V> documentValue = this.valueFactory.createObjectValue(document);
		return (T) toObject(documentValue, objectType);
	}

	@Override
	@Nullable
	public Object toObject(DocumentValue<O, A, V> document, Type type) {
		return toObject(document, type, type);
	}

	@Override
	@Nullable
	public Object toObject(DocumentValue<O, A, V> value, Type type, Type codecType) {
		if (value.isNull()) {
			return null;
		}
		Optional<Codec<O, A, V, Object>> codecOptional = getObjectCodec(codecType);
		if (codecOptional.isPresent()) {
			Codec<O, A, V, Object> codec = codecOptional.get();
			return codec.toObject(value, type, this);
		}
		throw new CodecNotFoundException(codecType);
	}

	@Override
	public boolean isFieldValid(Field field) {
		return this.propertyInvalidators.stream()
			.noneMatch(filter -> filter.invalid(field));
	}

	@Override
	public String getFieldName(Field field) {
		Property property = field.getAnnotation(Property.class);
		if (property != null) {
			return property.value();
		}
		String name = field.getName();
		return this.nameConverter.convert(name);
	}

	@SuppressWarnings("unchecked")
	private Optional<Codec<O, A, V, Object>> getObjectCodec(Type type) {
		Type rawType = Types.getRawType(type);
		Class<?> classType = Types.asClassType(rawType);
		Codec<?, ?, ?, ?> found = this.codecRegistry.get(classType);
		return Optional.ofNullable(found)
			.map(codec -> (Codec<O, A, V, Object>) codec);
	}

	@Override
	public DocumentObjectFactory<O, A, V> getObjectFactory() {
		return this.objectFactory;
	}

	@Override
	public DocumentArrayFactory<O, A, V> getArrayFactory() {
		return this.arrayFactory;
	}

	@Override
	public DocumentValueFactory<O, A, V> getValueFactory() {
		return this.valueFactory;
	}

	@Override
	public NameConverter getNameConverter() {
		return this.nameConverter;
	}

	@Override
	public List<PropertyInvalidator> getPropertyInvalidators() {
		return List.copyOf(this.propertyInvalidators);
	}

	@Override
	public CodecRegistry getCodecRegistry() {
		return this.codecRegistry;
	}
}
