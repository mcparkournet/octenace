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

package net.mcparkour.octenace.mapper.metadata;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import net.mcparkour.common.reflection.type.Types;

public class TypeMetadata {

	private Type type;
	private Type genericType;

	public TypeMetadata(Type type) {
		this.type = type;
		this.genericType = type;
	}

	public TypeMetadata(Type type, Type genericType) {
		this.type = type;
		this.genericType = genericType;
	}

	public Class<?> getComponentType() {
		Class<?> classType = getClassType();
		return classType.getComponentType();
	}

	public Class<?> getClassType() {
		Type rawType = getRawType();
		return Types.asClassType(rawType);
	}

	public Type getRawType() {
		return Types.getRawType(this.type);
	}

	public Class<?> getFirstGenericType() {
		Type[] types = getGenericTypes();
		Type type = types[0];
		Class<?> classType = Types.asClassType(type);
		return classType;
	}

	public Entry<Class<?>, Class<?>> getGenericTypesPair() {
		Type[] types = getGenericTypes();
		Type keyType = types[0];
		Class<?> keyClass = Types.asClassType(keyType);
		Type valueType = types[1];
		Class<?> valueClass = Types.asClassType(valueType);
		return Map.entry(keyClass, valueClass);
	}

	public Type[] getGenericTypes() {
		ParameterizedType parameterizedType = Types.asParametrizedType(this.genericType);
		return parameterizedType.getActualTypeArguments();
	}

	public Type getType() {
		return this.type;
	}

	public Type getGenericType() {
		return this.genericType;
	}
}
