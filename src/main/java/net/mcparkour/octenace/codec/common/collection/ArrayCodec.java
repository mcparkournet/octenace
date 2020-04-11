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
import java.lang.reflect.Type;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.document.array.DocumentArray;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;

public class ArrayCodec implements CommonCodec<Object[]> {

	@Override
	public <O, A, V> DocumentValue<O, A, V> toDocument(Object[] object, Type type, Mapper<O, A, V> mapper) {
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
	public <O, A, V> Object[] toObject(DocumentValue<O, A, V> document, Type type, Mapper<O, A, V> mapper) {
		DocumentArrayFactory<O, A, V> arrayFactory = mapper.getArrayFactory();
		A rawArray = document.asArray();
		DocumentArray<O, A, V> array = arrayFactory.createArray(rawArray);
		Class<?> classType = Types.asClassType(type);
		Class<?> arrayType = classType.getComponentType();
		int size = array.getSize();
		Object[] resultArray = (Object[]) Array.newInstance(arrayType, size);
		for (int index = 0; index < size; index++) {
			DocumentValue<O, A, V> elementValue = array.get(index);
			Object object = mapper.toObject(elementValue, arrayType);
			resultArray[index] = object;
		}
		return resultArray;
	}
}
