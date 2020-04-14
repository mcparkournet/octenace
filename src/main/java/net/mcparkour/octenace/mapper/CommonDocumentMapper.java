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
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;
import org.jetbrains.annotations.Nullable;

public class CommonDocumentMapper<O, A, V, T> implements DocumentMapper<O, A, V, T> {

	private Mapper<O, A, V> mapper;
	private Class<T> type;
	private Codec<O, A, V, Metadata, T> codec;
	private Metadata metadata;

	public CommonDocumentMapper(Mapper<O, A, V> mapper, Class<T> type) {
		this(mapper, type, mapper.getCodec(type));
	}

	public CommonDocumentMapper(Mapper<O, A, V> mapper, Class<T> type, Codec<O, A, V, Metadata, T> codec) {
		this(mapper, type, codec, mapper.createMetadata(codec, new TypeMetadata(type)));
	}

	public CommonDocumentMapper(Mapper<O, A, V> mapper, Class<T> type, Codec<O, A, V, Metadata, T> codec, Metadata metadata) {
		this.mapper = mapper;
		this.type = type;
		this.codec = codec;
		this.metadata = metadata;
	}

	@Override
	public DocumentValue<O, A, V> toDocument(@Nullable T object) {
		return this.mapper.toDocument(this.codec, object, this.metadata);
	}

	@Override
	@Nullable
	public T toObject(DocumentObject<O, A, V> document) {
		var valueFactory = this.mapper.getValueFactory();
		DocumentValue<O, A, V> documentValue = valueFactory.createObjectValue(document);
		return toObject(documentValue);
	}

	@Override
	@Nullable
	public T toObject(DocumentArray<O, A, V> document) {
		var valueFactory = this.mapper.getValueFactory();
		DocumentValue<O, A, V> documentValue = valueFactory.createArrayValue(document);
		return toObject(documentValue);
	}

	@Override
	@Nullable
	public T toObject(DocumentValue<O, A, V> document) {
		return this.mapper.toObject(this.codec, document, this.metadata);
	}

	@Override
	public Mapper<O, A, V> getMapper() {
		return this.mapper;
	}

	@Override
	public Class<T> getType() {
		return this.type;
	}

	@Override
	public Codec<O, A, V, Metadata, T> getCodec() {
		return this.codec;
	}

	@Override
	public Metadata getMetadata() {
		return this.metadata;
	}
}
