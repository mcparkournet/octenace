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
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.model.value.ModelValue;
import org.jetbrains.annotations.Nullable;

class CommonCodecWrapper<O, A, V, T> implements Codec<O, A, V, T> {

	private CommonCodec<T> codec;

	CommonCodecWrapper(CommonCodec<T> codec) {
		this.codec = codec;
	}

	@Override
	public ModelValue<O, A, V> encode(T value, Type type, Mapper<O, A, V> mapper) {
		return this.codec.encode(value, type, mapper);
	}

	@Override
	@Nullable
	public T decode(ModelValue<O, A, V> value, Type type, Mapper<O, A, V> mapper) {
		return this.codec.decode(value, type, mapper);
	}
}
