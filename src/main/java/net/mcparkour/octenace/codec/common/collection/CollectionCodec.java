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
import java.util.Collection;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.document.array.DocumentArray;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;

public class CollectionCodec implements CommonCodec<Collection<?>> {

	private CollectionSupplier<? extends Collection<Object>> collectionSupplier;

	public CollectionCodec(CollectionSupplier<? extends Collection<Object>> collectionSupplier) {
		this.collectionSupplier = collectionSupplier;
	}

	@Override
	public <O, A, V> DocumentValue<O, A, V> toDocument(Collection<?> object, Type type, Mapper<O, A, V> mapper) {
		DocumentArrayFactory<O, A, V> arrayFactory = mapper.getArrayFactory();
		DocumentArray<O, A, V> array = arrayFactory.createEmptyArray();
		for (Object element : object) {
			Class<?> elementType = element.getClass();
			DocumentValue<O, A, V> elementValue = mapper.toDocument(element, elementType);
			array.add(elementValue);
		}
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		return valueFactory.createArrayValue(array);
	}

	@Override
	public <O, A, V> Collection<?> toObject(DocumentValue<O, A, V> document, Type type, Mapper<O, A, V> mapper) {
		DocumentArrayFactory<O, A, V> arrayFactory = mapper.getArrayFactory();
		A rawArray = document.asArray();
		DocumentArray<O, A, V> array = arrayFactory.createArray(rawArray);
		Type genericType = getGenericType(type);
		int size = array.getSize();
		Collection<Object> collection = this.collectionSupplier.supply(size);
		for (DocumentValue<O, A, V> elementValue : array) {
			Object object = mapper.toObject(elementValue, genericType);
			collection.add(object);
		}
		return collection;
	}

	private Type getGenericType(Type type) {
		ParameterizedType parameterizedType = Types.asParametrizedType(type);
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		return typeArguments[0];
	}
}
