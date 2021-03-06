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
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.metadata.Metadata;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.name.NameConverter;
import org.jetbrains.annotations.Nullable;

public interface Mapper<O, A, V> {

	<T, M extends Metadata> DocumentValue<O, A, V> toDocument(Codec<O, A, V, M, T> codec, @Nullable T object, M metadata);

	@Nullable <T, M extends Metadata> T toObject(Codec<O, A, V, M, T> codec, DocumentValue<O, A, V> document, M metadata);

	<T, M extends Metadata> M createMetadata(Codec<O, A, V, M, T> codec, Type type);

	Codec<O, A, V, Metadata, Object> getObjectCodec(Class<?> type);

	<T, M extends Metadata> Codec<O, A, V, M, T> getCodec(Class<T> type);

	boolean isFieldValid(Field field);

	String getFieldName(Field field);

	DocumentObjectFactory<O, A, V> getObjectFactory();

	DocumentArrayFactory<O, A, V> getArrayFactory();

	DocumentValueFactory<O, A, V> getValueFactory();

	NameConverter getNameConverter();

	List<PropertyInvalidator> getPropertyInvalidators();

	CodecRegistry<O, A, V> getCodecRegistry();
}
