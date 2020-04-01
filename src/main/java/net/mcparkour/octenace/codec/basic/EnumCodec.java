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

package net.mcparkour.octenace.codec.basic;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.converter.Converter;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class EnumCodec implements Codec<Enum<?>> {

	@Override
	public <O, A, V> ModelValue<O, A, V> encode(Enum<?> object, Type type, Converter<O, A, V> converter) {
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		String name = getEnumName(object, converter);
		return valueFactory.createStringModelValue(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <O, A, V> Enum<?> decode(ModelValue<O, A, V> value, Type type, Converter<O, A, V> converter) {
		Type rawType = Types.getRawType(type);
		Class<?> classType = Types.asClassType(rawType);
		Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) classType;
		Enum<?>[] enumConstants = enumType.getEnumConstants();
		String valueString = value.asString();
		return Arrays.stream(enumConstants)
			.filter(enumConstant -> valueString.equals(getEnumName(enumConstant, converter)))
			.findFirst()
			.orElse(null);
	}

	private <O, A, V> String getEnumName(Enum<?> object, Converter<O, A, V> converter) {
		Class<? extends Enum<?>> type = object.getDeclaringClass();
		String name = object.name();
		Field field = Reflections.getField(type, name);
		return converter.getFieldName(field);
	}
}
