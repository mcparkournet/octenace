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

package net.mcparkour.octenace.model.object;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.mcparkour.octenace.model.value.ModelValue;
import net.mcparkour.octenace.model.value.TestModelValue;

public class TestModelObject implements ModelObject<Map<String, Object>, List<Object>, Object> {

	private Map<String, Object> object;

	public TestModelObject(Map<String, Object> object) {
		this.object = object;
	}

	@Override
	public ModelValue<Map<String, Object>, List<Object>, Object> get(String key) {
		Object value = this.object.get(key);
		return new TestModelValue(value);
	}

	@Override
	public void set(String key, ModelValue<Map<String, Object>, List<Object>, Object> value) {
		Object rawValue = value.getValue();
		this.object.put(key, rawValue);
	}

	@Override
	public int getSize() {
		return this.object.size();
	}

	@Override
	public Map<String, Object> getObject() {
		return this.object;
	}

	@Override
	public Iterator<Entry<String, ModelValue<Map<String, Object>, List<Object>, Object>>> iterator() {
		Set<Entry<String, Object>> entries = this.object.entrySet();
		Iterator<Entry<String, Object>> iterator = entries.iterator();
		return new TestModelObjectIterator(iterator);
	}

	public static final class TestModelObjectIterator implements Iterator<Entry<String, ModelValue<Map<String, Object>, List<Object>, Object>>> {

		private Iterator<Entry<String, Object>> iterator;

		public TestModelObjectIterator(Iterator<Entry<String, Object>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Override
		public Entry<String, ModelValue<Map<String, Object>, List<Object>, Object>> next() {
			Entry<String, Object> next = this.iterator.next();
			String key = next.getKey();
			Object value = next.getValue();
			TestModelValue modelValue = new TestModelValue(value);
			return Map.entry(key, modelValue);
		}
	}
}
