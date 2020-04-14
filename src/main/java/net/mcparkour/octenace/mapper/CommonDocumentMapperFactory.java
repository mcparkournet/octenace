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
import net.mcparkour.octenace.mapper.metadata.Metadata;

public class CommonDocumentMapperFactory<O, A, V> implements DocumentMapperFactory<O, A, V> {

	private Mapper<O, A, V> mapper;

	public CommonDocumentMapperFactory(Mapper<O, A, V> mapper) {
		this.mapper = mapper;
	}

	@Override
	public <T> DocumentMapper<O, A, V, T> createMapper(Class<T> type) {
		return new CommonDocumentMapper<>(this.mapper, type);
	}

	@Override
	public <T> DocumentMapper<O, A, V, T> createMapper(Class<T> type, Codec<O, A, V, Metadata, T> codec) {
		return new CommonDocumentMapper<>(this.mapper, type, codec);
	}

	@Override
	public <T> DocumentMapper<O, A, V, T> createMapper(Class<T> type, Codec<O, A, V, Metadata, T> codec, Metadata metadata) {
		return new CommonDocumentMapper<>(this.mapper, type, codec, metadata);
	}

	@Override
	public Mapper<O, A, V> getMapper() {
		return this.mapper;
	}
}
