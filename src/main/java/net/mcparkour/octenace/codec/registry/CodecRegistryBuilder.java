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

import java.util.HashMap;
import java.util.Map;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.mapper.metadata.Metadata;

public class CodecRegistryBuilder<O, A, V> {

	private Map<Class<?>, Codec<O, A, V, ? extends Metadata, ?>> codecs;

	public CodecRegistryBuilder() {
		this.codecs = new HashMap<>(0);
	}

	public CodecRegistryBuilder<O, A, V> registry(CodecRegistry<O, A, V> registry) {
		Map<Class<?>, Codec<O, A, V, ? extends Metadata, ?>> codecs = registry.getCodecs();
		this.codecs.putAll(codecs);
		return this;
	}

	public CodecRegistryBuilder<O, A, V> codec(Class<?> type, Codec<O, A, V, ? extends Metadata, ?> codec) {
		this.codecs.put(type, codec);
		return this;
	}

	public CodecRegistry<O, A, V> build() {
		return new CodecRegistry<>(this.codecs);
	}
}
