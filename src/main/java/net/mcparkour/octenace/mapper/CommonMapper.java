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
import java.util.List;
import net.mcparkour.octenace.annotation.Property;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.metadata.Metadata;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.name.NameConverter;

public class CommonMapper<O, A, V, T> implements Mapper<O, A, V, T> {

	private DocumentObjectFactory<O, A, V> objectFactory;
	private DocumentArrayFactory<O, A, V> arrayFactory;
	private DocumentValueFactory<O, A, V> valueFactory;
	private NameConverter nameConverter;
	private List<PropertyInvalidator> propertyInvalidators;
	private CodecRegistry<O, A, V> codecRegistry;
	private Class<T> type;
	private Codec<O, A, V, Metadata, T> codec;
	private Metadata metadata;

	public CommonMapper(DocumentObjectFactory<O, A, V> objectFactory, DocumentArrayFactory<O, A, V> arrayFactory, DocumentValueFactory<O, A, V> valueFactory, NameConverter nameConverter, List<PropertyInvalidator> propertyInvalidators, CodecRegistry<O, A, V> codecRegistry, Class<T> type) {
		this.objectFactory = objectFactory;
		this.arrayFactory = arrayFactory;
		this.valueFactory = valueFactory;
		this.nameConverter = nameConverter;
		this.propertyInvalidators = propertyInvalidators;
		this.codecRegistry = codecRegistry;
		this.type = type;
		Codec<O, A, V, Metadata, T> codec = getCodec(type);
		this.codec = codec;
		TypeMetadata typeMetadata = new TypeMetadata(type);
		this.metadata = codec.getMetadata(typeMetadata, this);
	}

	@Override
	public DocumentObject<O, A, V> toDocument(T object) {
		DocumentValue<O, A, V> document = this.codec.toDocument(object, this.metadata, this);
		O rawObject = document.asObject();
		return this.objectFactory.createObject(rawObject);
	}

	@Override
	public T toObject(DocumentObject<O, A, V> document) {
		DocumentValue<O, A, V> documentValue = this.valueFactory.createObjectValue(document);
		return this.codec.toObject(documentValue, this.metadata, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Codec<O, A, V, Metadata, Object> getObjectCodec(Class<?> type) {
		Codec<O, A, V, Metadata, ?> codec = getCodec(type);
		return (Codec<O, A, V, Metadata, Object>) codec;
	}

	@SuppressWarnings("unchecked")
	private <U> Codec<O, A, V, Metadata, U> getCodec(Class<U> type) {
		Codec<O, A, V, ? extends Metadata, U> codec = this.codecRegistry.get(type);
		if (codec == null) {
			throw new CodecNotFoundException(type);
		}
		return (Codec<O, A, V, Metadata, U>) codec;
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
	public CodecRegistry<O, A, V> getCodecRegistry() {
		return this.codecRegistry;
	}

	@Override
	public Class<T> getType() {
		return this.type;
	}

	@Override
	public Metadata getMetadata() {
		return this.metadata;
	}
}
