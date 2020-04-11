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

package net.mcparkour.octenace.mapper.property.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NameConvertersTest {

	@Test
	public void testKebabCaseConverter() {
		Assertions.assertEquals("", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert(""));
		Assertions.assertEquals("foo", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert("foo"));
		Assertions.assertEquals("foo", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert("FOO"));
		Assertions.assertEquals("foo-b", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert("FooB"));
		Assertions.assertEquals("foo-bar", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert("FooBAR"));
		Assertions.assertEquals("foo-bar-foo-bar", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert("fooBarFooBar"));
		Assertions.assertEquals("foo-bar-foo-bar", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert("FooBarFooBar"));
		Assertions.assertEquals("foo-bar-foo-bar", NameConverters.KEBAB_CASE_NAME_CONVERTER.convert("FooBarFOOBar"));
	}

	@Test
	public void testSnakeCaseConverter() {
		Assertions.assertEquals("", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert(""));
		Assertions.assertEquals("foo", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert("foo"));
		Assertions.assertEquals("foo", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert("FOO"));
		Assertions.assertEquals("foo_b", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert("FooB"));
		Assertions.assertEquals("foo_bar", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert("FooBAR"));
		Assertions.assertEquals("foo_bar_foo_bar", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert("fooBarFooBar"));
		Assertions.assertEquals("foo_bar_foo_bar", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert("FooBarFooBar"));
		Assertions.assertEquals("foo_bar_foo_bar", NameConverters.SNAKE_CASE_NAME_CONVERTER.convert("FooBarFOOBar"));
	}
}
