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

package net.mcparkour.octenace.mapper.metadata;

import java.util.Map;

public class EnumMetadata extends Metadata {

	private Class<? extends Enum<?>> type;
	private Map<? extends Enum<?>, String> enumToNameMap;
	private Map<String, ? extends Enum<?>> nameToEnumMap;

	public EnumMetadata(Class<? extends Enum<?>> type, Map<? extends Enum<?>, String> enumToNameMap, Map<String, ? extends Enum<?>> nameToEnumMap) {
		this.type = type;
		this.enumToNameMap = enumToNameMap;
		this.nameToEnumMap = nameToEnumMap;
	}

	public Enum<?> getEnum(String name) {
		return this.nameToEnumMap.get(name);
	}

	public String getName(Enum<?> enumConstant) {
		return this.enumToNameMap.get(enumConstant);
	}

	public Class<? extends Enum<?>> getType() {
		return this.type;
	}
}
