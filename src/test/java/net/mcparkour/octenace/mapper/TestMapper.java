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

import java.util.List;
import java.util.Map;
import net.mcparkour.octenace.codec.common.Codecs;
import net.mcparkour.octenace.document.array.TestDocumentArrayFactory;
import net.mcparkour.octenace.document.object.TestDocumentObjectFactory;
import net.mcparkour.octenace.document.value.TestDocumentValueFactory;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidators;
import net.mcparkour.octenace.mapper.property.name.NameConverter;

public class TestMapper extends CommonMapper<Map<Object, Object>, List<Object>, Object> {

	public TestMapper() {
		super(new TestDocumentObjectFactory(), new TestDocumentArrayFactory(), new TestDocumentValueFactory(), NameConverter.identity(), PropertyInvalidators.COMMON_PROPERTY_INVALIDATORS, Codecs.createCommonCodecRegistry());
	}
}
