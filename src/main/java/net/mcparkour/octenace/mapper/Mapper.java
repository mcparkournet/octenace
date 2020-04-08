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
import net.mcparkour.octenace.mapper.naming.NameConverter;
import net.mcparkour.octenace.model.array.ModelArrayFactory;
import net.mcparkour.octenace.model.object.ModelObject;
import net.mcparkour.octenace.model.object.ModelObjectFactory;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public interface Mapper<O, A, V> {

	ModelObject<O, A, V> fromDocument(Object document);

	ModelValue<O, A, V> fromDocument(@Nullable Object document, Type type);

	<T> T toDocument(ModelObject<O, A, V> object, Class<T> configurationType);

	Object toDocument(ModelValue<O, A, V> value, Type type);

	boolean isFieldValid(Field field);

	String getFieldName(Field field);

	ModelObjectFactory<O, A, V> getObjectFactory();

	ModelArrayFactory<O, A, V> getArrayFactory();

	ModelValueFactory<O, A, V> getValueFactory();

	NameConverter getNameConverter();
}
