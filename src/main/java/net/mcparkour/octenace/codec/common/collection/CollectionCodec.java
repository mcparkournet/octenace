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

import java.util.Collection;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.document.array.DocumentArray;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.mapper.metadata.CollectionMetadata;
import net.mcparkour.octenace.mapper.metadata.Element;
import net.mcparkour.octenace.mapper.metadata.Metadata;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;

public abstract class CollectionCodec<O, A, V, T extends Collection<?>> implements Codec<O, A, V, CollectionMetadata<O, A, V>, T> {

	@Override
	public DocumentValue<O, A, V> toDocument(T object, CollectionMetadata<O, A, V> metadata, Mapper<O, A, V, ?> mapper) {
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		DocumentArrayFactory<O, A, V> arrayFactory = mapper.getArrayFactory();
		int size = object.size();
		DocumentArray<O, A, V> arrayDocument = arrayFactory.createEmptyArray(size);
		Element<O, A, V> metadataElement = metadata.getElement();
		var codec = metadataElement.getObjectCodec();
		Metadata elementMetadata = metadataElement.getMetadata();
		for (Object objectElement : object) {
			DocumentValue<O, A, V> elementValue = codec.toDocument(objectElement, elementMetadata, mapper);
			arrayDocument.add(elementValue);
		}
		return valueFactory.createArrayValue(arrayDocument);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T toObject(DocumentValue<O, A, V> document, CollectionMetadata<O, A, V> metadata, Mapper<O, A, V, ?> mapper) {
		DocumentArrayFactory<O, A, V> arrayFactory = mapper.getArrayFactory();
		A rawArray = document.asArray();
		DocumentArray<O, A, V> array = arrayFactory.createArray(rawArray);
		int size = array.getSize();
		Element<O, A, V> element = metadata.getElement();
		var elementCodec = element.getObjectCodec();
		Collection<Object> collection = (Collection<Object>) createCollection(size);
		for (DocumentValue<O, A, V> elementValue : array) {
			Object object = elementCodec.toObject(elementValue, element.getMetadata(), mapper);
			collection.add(object);
		}
		return (T) collection;
	}

	public abstract T createCollection(int size);

	@Override
	public CollectionMetadata<O, A, V> getMetadata(TypeMetadata type, Mapper<O, A, V, ?> mapper) {
		Class<?> elementType = type.getFirstGenericType();
		var codec = mapper.getObjectCodec(elementType);
		Metadata metadata = codec.getMetadata(new TypeMetadata(elementType), mapper);
		Class<?> elementClassType = Types.asClassType(elementType);
		Element<O, A, V> element = new Element<>(elementClassType, codec, metadata);
		return new CollectionMetadata<>(element);
	}
}
