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

package net.mcparkour.octenace.codec.common.primitive;

import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;
import net.mcparkour.octenace.mapper.metadata.ValueMetadata;

public class CharCodec<O, A, V> implements Codec<O, A, V, ValueMetadata, Character> {

	@Override
	public DocumentValue<O, A, V> toDocument(Character object, ValueMetadata metadata, Mapper<O, A, V> mapper) {
		DocumentValueFactory<O, A, V> valueFactory = mapper.getValueFactory();
		return valueFactory.createValue(object);
	}

	@Override
	public Character toObject(DocumentValue<O, A, V> document, ValueMetadata metadata, Mapper<O, A, V> mapper) {
		return document.asChar();
	}

	@Override
	public ValueMetadata getMetadata(TypeMetadata type, Mapper<O, A, V> mapper) {
		return new ValueMetadata();
	}
}
