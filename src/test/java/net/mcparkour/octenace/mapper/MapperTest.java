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
import net.mcparkour.octenace.TestSubObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapperTest {

	private TestDocumentMapper<TestObject> objectMapper;
	private TestDocumentMapper<TestSubObject> subObjectMapper;

	@BeforeEach
	public void setUp() {
		this.objectMapper = new TestDocumentMapper<>(TestObject.class);
		this.subObjectMapper = new TestDocumentMapper<>(TestSubObject.class);
	}

	@Test
	public void testSubObjectToDocument() {
		TestSubObject object = new TestSubObject();
		var documentValue = this.subObjectMapper.toDocument(object);
		Map<Object, Object> document = documentValue.asObject();
		Assertions.assertEquals(object.toString(), document.toString());
	}

	@Test
	public void testSubObjectToObject() {
		TestSubObject originObject = new TestSubObject();
		var documentValue = this.subObjectMapper.toDocument(originObject);
		TestSubObject object = this.subObjectMapper.toObject(documentValue);
		Assertions.assertEquals(originObject.toString(), Objects.requireNonNull(object).toString());
	}

	@Test
	public void testObjectToDocument() {
		TestObject object = new TestObject();
		var documentValue = this.objectMapper.toDocument(object);
		Map<Object, Object> document = documentValue.asObject();
		Assertions.assertEquals(object.toString(), document.toString());
	}

	@Test
	public void testObjectToObject() {
		TestObject originObject = new TestObject();
		var documentValue = this.objectMapper.toDocument(originObject);
		TestObject object = this.objectMapper.toObject(documentValue);
		Assertions.assertEquals(originObject.toString(), Objects.requireNonNull(object).toString());
	}
}
