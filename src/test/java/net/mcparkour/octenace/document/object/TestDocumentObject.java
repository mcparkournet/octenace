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

package net.mcparkour.octenace.document.object;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.TestDocumentValue;

public class TestDocumentObject implements DocumentObject<Map<Object, Object>, List<Object>, Object> {

	private Map<Object, Object> object;

	public TestDocumentObject(Map<Object, Object> object) {
		this.object = object;
	}

	@Override
	public DocumentValue<Map<Object, Object>, List<Object>, Object> get(DocumentValue<Map<Object, Object>, List<Object>, Object> key) {
		Object rawKey = key.getValue();
		Object value = this.object.get(rawKey);
		return new TestDocumentValue(value);
	}

	@Override
	public void set(DocumentValue<Map<Object, Object>, List<Object>, Object> key, DocumentValue<Map<Object, Object>, List<Object>, Object> value) {
		Object rawKey = key.getValue();
		Object rawValue = value.getValue();
		this.object.put(rawKey, rawValue);
	}

	@Override
	public int getSize() {
		return this.object.size();
	}

	@Override
	public Map<Object, Object> getObject() {
		return this.object;
	}

	@Override
	public Iterator<Entry<DocumentValue<Map<Object, Object>, List<Object>, Object>, DocumentValue<Map<Object, Object>, List<Object>, Object>>> iterator() {
		Set<Entry<Object, Object>> entries = this.object.entrySet();
		Iterator<Entry<Object, Object>> iterator = entries.iterator();
		return new TestModelObjectIterator(iterator);
	}

	public static final class TestModelObjectIterator implements Iterator<Entry<DocumentValue<Map<Object, Object>, List<Object>, Object>, DocumentValue<Map<Object, Object>, List<Object>, Object>>> {

		private Iterator<Entry<Object, Object>> iterator;

		public TestModelObjectIterator(Iterator<Entry<Object, Object>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Override
		public Entry<DocumentValue<Map<Object, Object>, List<Object>, Object>, DocumentValue<Map<Object, Object>, List<Object>, Object>> next() {
			Entry<Object, Object> next = this.iterator.next();
			Object key = next.getKey();
			Object value = next.getValue();
			TestDocumentValue modelKey = new TestDocumentValue(key);
			TestDocumentValue modelValue = new TestDocumentValue(value);
			return Map.entry(modelKey, modelValue);
		}
	}
}
