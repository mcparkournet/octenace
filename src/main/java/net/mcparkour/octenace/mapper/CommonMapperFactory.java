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
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.name.NameConverter;

public class CommonMapperFactory<O, A, V> implements MapperFactory<O, A, V> {

	private DocumentObjectFactory<O, A, V> objectFactory;
	private DocumentArrayFactory<O, A, V> arrayFactory;
	private DocumentValueFactory<O, A, V> valueFactory;
	private NameConverter nameConverter;
	private List<PropertyInvalidator> propertyInvalidators;
	private CodecRegistry<O, A, V> codecRegistry;

	public CommonMapperFactory(DocumentObjectFactory<O, A, V> objectFactory, DocumentArrayFactory<O, A, V> arrayFactory, DocumentValueFactory<O, A, V> valueFactory, NameConverter nameConverter, List<PropertyInvalidator> propertyInvalidators, CodecRegistry<O, A, V> codecRegistry) {
		this.objectFactory = objectFactory;
		this.arrayFactory = arrayFactory;
		this.valueFactory = valueFactory;
		this.nameConverter = nameConverter;
		this.propertyInvalidators = propertyInvalidators;
		this.codecRegistry = codecRegistry;
	}

	@Override
	public <T> Mapper<O, A, V, T> createMapper(Class<T> type) {
		return new CommonMapper<>(this.objectFactory, this.arrayFactory, this.valueFactory, this.nameConverter, this.propertyInvalidators, this.codecRegistry, type);
	}

	@Override
	public DocumentObjectFactory<O, A, V> getObjectFactory() {
		return this.objectFactory;
	}

	@Override
	public DocumentArrayFactory<O, A, V> getArrayFactory() {
		return this.arrayFactory;
	}

	@Override
	public DocumentValueFactory<O, A, V> getValueFactory() {
		return this.valueFactory;
	}

	@Override
	public NameConverter getNameConverter() {
		return this.nameConverter;
	}

	@Override
	public List<PropertyInvalidator> getPropertyInvalidators() {
		return List.copyOf(this.propertyInvalidators);
	}

	@Override
	public CodecRegistry<O, A, V> getCodecRegistry() {
		return this.codecRegistry;
	}
}
