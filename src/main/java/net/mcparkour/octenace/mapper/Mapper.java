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
import java.util.List;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.name.NameConverter;
import org.jetbrains.annotations.Nullable;

public interface Mapper<O, A, V> {

	DocumentObject<O, A, V> toDocument(Object object);

	DocumentValue<O, A, V> toDocument(@Nullable Object object, Type type);

	@Nullable
	<T> T toObject(DocumentObject<O, A, V> document, Class<T> objectType);

	@Nullable
	Object toObject(DocumentValue<O, A, V> document, Type type);

	@Nullable
	Object toObject(DocumentValue<O, A, V> value, Type type, Type codecType);

	boolean isFieldValid(Field field);

	String getFieldName(Field field);

	DocumentObjectFactory<O, A, V> getObjectFactory();

	DocumentArrayFactory<O, A, V> getArrayFactory();

	DocumentValueFactory<O, A, V> getValueFactory();

	NameConverter getNameConverter();

	List<PropertyInvalidator> getPropertyInvalidators();

	CodecRegistry getCodecRegistry();
}
