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

import java.lang.reflect.Array;
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

public class ArrayCodec<O, A, V> implements Codec<O, A, V, CollectionMetadata<O, A, V>, Object[]> {

	@Override
	public DocumentValue<O, A, V> toDocument(Object[] object, CollectionMetadata<O, A, V> metadata, Mapper<O, A, V, ?> mapper) {
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		DocumentArrayFactory<O, A, V> arrayFactory = mapper.getArrayFactory();
		int length = object.length;
		DocumentArray<O, A, V> array = arrayFactory.createEmptyArray(length);
		Element<O, A, V> elementMetadata = metadata.getElement();
		var codec = elementMetadata.getObjectCodec();
		Metadata elementMetadataMetadata = elementMetadata.getMetadata();
		for (Object element : object) {
			DocumentValue<O, A, V> documentValue = codec.toDocument(element, elementMetadataMetadata, mapper);
			array.add(documentValue);
		}
		return valueFactory.createArrayValue(array);
	}

	@Override
	public Object[] toObject(DocumentValue<O, A, V> document, CollectionMetadata<O, A, V> metadata, Mapper<O, A, V, ?> mapper) {
		DocumentArrayFactory<O, A, V> arrayFactory = mapper.getArrayFactory();
		A rawArray = document.asArray();
		DocumentArray<O, A, V> array = arrayFactory.createArray(rawArray);
		int size = array.getSize();
		Element<O, A, V> element = metadata.getElement();
		Class<?> elementType = element.getType();
		Object[] resultArray = (Object[]) Array.newInstance(elementType, size);
		var elementCodec = element.getObjectCodec();
		Metadata elementMetadata = element.getMetadata();
		for (int index = 0; index < size; index++) {
			DocumentValue<O, A, V> elementValue = array.get(index);
			resultArray[index] = elementCodec.toObject(elementValue, elementMetadata, mapper);
		}
		return resultArray;
	}

	@Override
	public CollectionMetadata<O, A, V> getMetadata(TypeMetadata type, Mapper<O, A, V, ?> mapper) {
		Class<?> elementType = type.getComponentType();
		var codec = mapper.getObjectCodec(elementType);
		Metadata metadata = codec.getMetadata(new TypeMetadata(elementType), mapper);
		Element<O, A, V> element = new Element<>(elementType, codec, metadata);
		return new CollectionMetadata<>(element);
	}
}
