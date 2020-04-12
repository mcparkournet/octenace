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

import net.mcparkour.octenace.codec.Codec;

public class Element<O, A, V> {

	private Class<?> type;
	private Codec<O, A, V, Metadata, ?> codec;
	private Metadata metadata;

	public Element(Class<?> type, Codec<O, A, V, Metadata, ?> codec, Metadata metadata) {
		this.type = type;
		this.codec = codec;
		this.metadata = metadata;
	}

	@SuppressWarnings("unchecked")
	public Codec<O, A, V, Metadata, Object> getObjectCodec() {
		return (Codec<O, A, V, Metadata, Object>) this.codec;
	}

	public Class<?> getType() {
		return this.type;
	}

	public Codec<O, A, V, Metadata, ?> getCodec() {
		return this.codec;
	}

	public Metadata getMetadata() {
		return this.metadata;
	}
}
