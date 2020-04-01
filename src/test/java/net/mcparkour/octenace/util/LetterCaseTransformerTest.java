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

package net.mcparkour.octenace.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LetterCaseTransformerTest {

	@Test
	public void testToKebabCase() {
		Assertions.assertEquals("", LetterCaseTransformer.toKebabCase(""));
		Assertions.assertEquals("foo", LetterCaseTransformer.toKebabCase("foo"));
		Assertions.assertEquals("foo", LetterCaseTransformer.toKebabCase("FOO"));
		Assertions.assertEquals("foo-b", LetterCaseTransformer.toKebabCase("FooB"));
		Assertions.assertEquals("foo-bar", LetterCaseTransformer.toKebabCase("FooBAR"));
		Assertions.assertEquals("foo-bar-foo-bar", LetterCaseTransformer.toKebabCase("fooBarFooBar"));
		Assertions.assertEquals("foo-bar-foo-bar", LetterCaseTransformer.toKebabCase("FooBarFooBar"));
		Assertions.assertEquals("foo-bar-foo-bar", LetterCaseTransformer.toKebabCase("FooBarFOOBar"));
	}

	@Test
	public void testToSnakeCase() {
		Assertions.assertEquals("", LetterCaseTransformer.toSnakeCase(""));
		Assertions.assertEquals("foo", LetterCaseTransformer.toSnakeCase("foo"));
		Assertions.assertEquals("foo", LetterCaseTransformer.toSnakeCase("FOO"));
		Assertions.assertEquals("foo_b", LetterCaseTransformer.toSnakeCase("FooB"));
		Assertions.assertEquals("foo_bar", LetterCaseTransformer.toSnakeCase("FooBAR"));
		Assertions.assertEquals("foo_bar_foo_bar", LetterCaseTransformer.toSnakeCase("fooBarFooBar"));
		Assertions.assertEquals("foo_bar_foo_bar", LetterCaseTransformer.toSnakeCase("FooBarFooBar"));
		Assertions.assertEquals("foo_bar_foo_bar", LetterCaseTransformer.toSnakeCase("FooBarFOOBar"));
	}
}
