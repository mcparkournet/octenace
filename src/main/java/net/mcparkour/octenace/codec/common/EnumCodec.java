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

package net.mcparkour.octenace.codec.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.mapper.metadata.EnumMetadata;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;

public class EnumCodec<O, A, V> implements Codec<O, A, V, EnumMetadata, Enum<?>> {

	@Override
	public DocumentValue<O, A, V> toDocument(Enum<?> object, EnumMetadata metadata, Mapper<O, A, V> mapper) {
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		String name = metadata.getName(object);
		return valueFactory.createValue(name);
	}

	@Override
	public Enum<?> toObject(DocumentValue<O, A, V> document, EnumMetadata metadata, Mapper<O, A, V> mapper) {
		String valueString = document.asString();
		return metadata.getEnum(valueString);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EnumMetadata getMetadata(TypeMetadata type, Mapper<O, A, V> mapper) {
		Class<?> classType = type.getClassType();
		Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) classType;
		Enum<?>[] enumConstants = enumType.getEnumConstants();
		int length = enumConstants.length;
		Map<Enum<?>, String> enumToNameMap = new HashMap<>(length);
		Map<String, Enum<?>> nameToEnumMap = new HashMap<>(length);
		for (Enum<?> enumConstant : enumConstants) {
			String enumName = getEnumName(enumType, enumConstant, mapper);
			enumToNameMap.put(enumConstant, enumName);
			nameToEnumMap.put(enumName, enumConstant);
		}
		return new EnumMetadata(enumType, enumToNameMap, nameToEnumMap);
	}

	private String getEnumName(Class<? extends Enum<?>> type, Enum<?> enumConstant, Mapper<O, A, V> mapper) {
		String name = enumConstant.name();
		Field field = Reflections.getField(type, name);
		return mapper.getFieldName(field);
	}
}
