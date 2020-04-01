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

import net.mcparkour.octenace.model.array.ModelArray;
import net.mcparkour.octenace.model.object.ModelObject;

public interface ModelValueFactory<O, A, V> {

	ModelValue<O, A, V> createNullModelValue();

	ModelValue<O, A, V> createBooleanModelValue(boolean value);

	ModelValue<O, A, V> createNumberModelValue(Number value);

	ModelValue<O, A, V> createStringModelValue(String value);

	default ModelValue<O, A, V> createObjectModelValue(ModelObject<O, A, V> object) {
		O rawObject = object.getObject();
		return createObjectModelValue(rawObject);
	}

	ModelValue<O, A, V> createObjectModelValue(O object);

	default ModelValue<O, A, V> createArrayModelValue(ModelArray<O, A, V> array) {
		A rawArray = array.getArray();
		return createArrayModelValue(rawArray);
	}

	ModelValue<O, A, V> createArrayModelValue(A array);

	ModelValue<O, A, V> createModelValue(V value);
}
