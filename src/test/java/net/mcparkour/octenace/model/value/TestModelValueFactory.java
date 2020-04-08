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

package net.mcparkour.octenace.model.value;

import java.util.List;
import java.util.Map;

public class TestModelValueFactory implements ModelValueFactory<Map<String, Object>, List<Object>, Object> {

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createNullValue() {
		return new TestModelValue(null);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createValue(boolean value) {
		Object rawValue = value;
		return createValue(rawValue);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createValue(int value) {
		Object rawValue = value;
		return createValue(rawValue);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createValue(long value) {
		Object rawValue = value;
		return createValue(rawValue);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createValue(double value) {
		Object rawValue = value;
		return createValue(rawValue);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createValue(String value) {
		Object rawValue = value;
		return createValue(rawValue);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createObjectValue(Map<String, Object> object) {
		return createValue(object);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createArrayValue(List<Object> array) {
		return createValue(array);
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> createValue(Object value) {
		return new TestModelValue(value);
	}
}