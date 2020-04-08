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

package net.mcparkour.octenace.codec.common;

import java.lang.reflect.Type;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.converter.Converter;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class StringCodec implements CommonCodec<String> {

	@Override
	public <O, A, V> ModelValue<O, A, V> encode(String object, Type type, Converter<O, A, V> converter) {
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		return valueFactory.createStringModelValue(object);
	}

	@Override
	@Nullable
	public <O, A, V> String decode(ModelValue<O, A, V> value, Type type, Converter<O, A, V> converter) {
		return value.asString();
	}
}