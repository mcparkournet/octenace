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

package net.mcparkour.octenace.document.value;

import net.mcparkour.octenace.document.array.DocumentArray;
import net.mcparkour.octenace.document.object.DocumentObject;

public interface DocumentValueFactory<O, A, V> {

	DocumentValue<O, A, V> createNullValue();

	DocumentValue<O, A, V> createValue(boolean value);

	DocumentValue<O, A, V> createValue(int value);

	DocumentValue<O, A, V> createValue(long value);

	DocumentValue<O, A, V> createValue(float value);

	DocumentValue<O, A, V> createValue(double value);

	DocumentValue<O, A, V> createValue(String value);

	default DocumentValue<O, A, V> createObjectValue(DocumentObject<O, A, V> object) {
		O rawObject = object.getObject();
		return createObjectValue(rawObject);
	}

	DocumentValue<O, A, V> createObjectValue(O object);

	default DocumentValue<O, A, V> createArrayValue(DocumentArray<O, A, V> array) {
		A rawArray = array.getArray();
		return createArrayValue(rawArray);
	}

	DocumentValue<O, A, V> createArrayValue(A array);

	DocumentValue<O, A, V> createValue(V value);
}
