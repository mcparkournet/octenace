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

package net.mcparkour.octenace.mapper.property.invalidator;

import java.util.List;
import net.mcparkour.common.reflection.Modifiers;
import net.mcparkour.octenace.annotation.Ignored;

public final class PropertyInvalidators {

	public static final PropertyInvalidator STATIC_PROPERTY_INVALIDATOR = Modifiers::isStatic;
	public static final PropertyInvalidator TRANSIENT_PROPERTY_INVALIDATOR = Modifiers::isTransient;
	public static final PropertyInvalidator IGNORED_ANNOTATION_INVALIDATOR = field -> field.isAnnotationPresent(Ignored.class);

	public static final List<PropertyInvalidator> COMMON_PROPERTY_INVALIDATORS = List.of(
		STATIC_PROPERTY_INVALIDATOR,
		TRANSIENT_PROPERTY_INVALIDATOR,
		IGNORED_ANNOTATION_INVALIDATOR
	);

	private PropertyInvalidators() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}
}
