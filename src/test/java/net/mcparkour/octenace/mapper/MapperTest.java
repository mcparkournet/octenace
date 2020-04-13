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

import java.util.Map;
import net.mcparkour.octenace.TestObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class MapperTest {

	private TestMapper<TestObject> mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new TestMapper<>(TestObject.class);
	}

	@Test
	@Order(1)
	public void testFromDocument() {
		TestObject object = new TestObject();
		var documentValue = this.mapper.toDocument(object);
		Map<Object, Object> document = documentValue.asObject();
		Assertions.assertEquals(object.toString(), document.toString());
	}

	@Test
	@Order(2)
	public void testToDocument() {
		TestObject originObject = new TestObject();
		var documentValue = this.mapper.toDocument(originObject);
		TestObject object = this.mapper.toObject(documentValue);
		Assertions.assertEquals(originObject.toString(), object.toString());
	}
}
