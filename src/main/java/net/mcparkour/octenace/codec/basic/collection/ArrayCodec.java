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

package net.mcparkour.octenace.codec.basic.collection;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.converter.Converter;
import net.mcparkour.octenace.model.array.ModelArray;
import net.mcparkour.octenace.model.array.ModelArrayFactory;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class ArrayCodec implements Codec<Object[]> {

	@Override
	public <O, A, V> ModelValue<O, A, V> encode(Object[] object, Type type, Converter<O, A, V> converter) {
		ModelArrayFactory<O, A, V> arrayFactory = converter.getModelArrayFactory();
		ModelArray<O, A, V> array = arrayFactory.createEmptyModelArray();
		for (Object element : object) {
			Class<?> elementType = element.getClass();
			ModelValue<O, A, V> elementValue = converter.toModelValue(element, elementType);
			array.addValue(elementValue);
		}
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		return valueFactory.createArrayModelValue(array);
	}

	@Override
	@Nullable
	public <O, A, V> Object[] decode(ModelValue<O, A, V> value, Type type, Converter<O, A, V> converter) {
		ModelArrayFactory<O, A, V> arrayFactory = converter.getModelArrayFactory();
		A rawArray = value.asArray();
		ModelArray<O, A, V> array = arrayFactory.createModelArray(rawArray);
		Class<?> classType = Types.asClassType(type);
		Class<?> arrayType = classType.getComponentType();
		int size = array.getSize();
		Object[] resultArray = (Object[]) Array.newInstance(arrayType, size);
		for (int index = 0; index < size; index++) {
			ModelValue<O, A, V> elementValue = array.get(index);
			Object object = converter.toObject(elementValue, arrayType);
			resultArray[index] = object;
		}
		return resultArray;
	}
}
