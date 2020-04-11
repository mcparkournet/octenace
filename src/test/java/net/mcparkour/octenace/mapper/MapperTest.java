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
import java.util.Objects;
import net.mcparkour.octenace.TestObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class MapperTest {

	private TestMapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new TestMapper();
	}

	@Test
	@Order(1)
	public void testFromDocument() {
		TestObject document = new TestObject();
		var modelObject = this.mapper.toDocument(document);
		Map<Object, Object> object = modelObject.getObject();
		Assertions.assertEquals(document.toString(), object.toString());
	}

	@Test
	@Order(2)
	public void testToDocument() {
		TestObject originDocument = new TestObject();
		var modelObject = this.mapper.toDocument(originDocument);
		TestObject document = this.mapper.toObject(modelObject, TestObject.class);
		Assertions.assertEquals(originDocument.toString(), Objects.requireNonNull(document).toString());
	}
}
