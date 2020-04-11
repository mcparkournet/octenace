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

package net.mcparkour.octenace.codec.registry;

import java.lang.reflect.Type;
import java.util.Objects;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.document.value.DocumentValue;

class CommonCodecWrapper<O, A, V, T> implements Codec<O, A, V, T> {

	private CommonCodec<T> codec;

	CommonCodecWrapper(CommonCodec<T> codec) {
		this.codec = codec;
	}

	@Override
	public DocumentValue<O, A, V> toDocument(T object, Type type, Mapper<O, A, V> mapper) {
		return this.codec.toDocument(object, type, mapper);
	}

	@Override
	public T toObject(DocumentValue<O, A, V> document, Type type, Mapper<O, A, V> mapper) {
		return this.codec.toObject(document, type, mapper);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CommonCodecWrapper)) {
			return false;
		}
		CommonCodecWrapper<?, ?, ?, ?> that = (CommonCodecWrapper<?, ?, ?, ?>) obj;
		return Objects.equals(this.codec, that.codec);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.codec);
	}

	@Override
	public String toString() {
		return "CommonCodecWrapper{" +
			"codec=" + this.codec +
			'}';
	}
}
