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

import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.document.array.DocumentArray;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.mapper.metadata.Metadata;
import org.jetbrains.annotations.Nullable;

public interface DocumentMapper<O, A, V, T> {

	DocumentValue<O, A, V> toDocument(@Nullable T object);

	@Nullable
	T toObject(DocumentObject<O, A, V> document);

	@Nullable
	T toObject(DocumentArray<O, A, V> document);

	@Nullable
	T toObject(DocumentValue<O, A, V> document);

	Mapper<O, A, V> getMapper();

	Class<T> getType();

	Codec<O, A, V, Metadata, T> getCodec();

	Metadata getMetadata();
}
