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

package net.mcparkour.octenace.codec.common;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.mcparkour.octenace.codec.common.extra.LocaleCodec;
import net.mcparkour.octenace.codec.common.extra.UUIDCodec;
import net.mcparkour.octenace.document.value.TestDocumentValue;
import net.mcparkour.octenace.mapper.TestMapper;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraCodecsTest {

	private TestMapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new TestMapper();
	}

	@Test
	public void testUUIDCodecEncode() {
		UUIDCodec<Map<Object, Object>, List<Object>, Object> codec = new UUIDCodec<>();
		UUID uuid = UUID.fromString("00000000-1000-0100-0010-000000000001");
		var metadata = this.mapper.createMetadata(codec, new TypeMetadata(UUID.class));
		var encoded = this.mapper.toDocument(codec, uuid, metadata);
		Object value = encoded.getValue();
		Assertions.assertEquals(uuid.toString(), value);
	}

	@Test
	public void testUUIDCodecDecode() {
		UUIDCodec<Map<Object, Object>, List<Object>, Object> codec = new UUIDCodec<>();
		UUID uuid = UUID.fromString("00000000-1000-0100-0010-000000000001");
		TestDocumentValue value = new TestDocumentValue(uuid.toString());
		var metadata = this.mapper.createMetadata(codec, new TypeMetadata(UUID.class));
		var decoded = this.mapper.toObject(codec, value, metadata);
		Assertions.assertEquals(uuid, decoded);
	}

	@Test
	public void testLocaleCodecEncode() {
		LocaleCodec<Map<Object, Object>, List<Object>, Object> codec = new LocaleCodec<>();
		Locale locale = Locale.US;
		var metadata = this.mapper.createMetadata(codec, new TypeMetadata(Locale.class));
		var encoded = this.mapper.toDocument(codec, locale, metadata);
		Object value = encoded.getValue();
		Assertions.assertEquals(locale.toLanguageTag(), value);
	}

	@Test
	public void testLocaleCodecDecode() {
		LocaleCodec<Map<Object, Object>, List<Object>, Object> codec = new LocaleCodec<>();
		Locale locale = Locale.US;
		TestDocumentValue value = new TestDocumentValue(locale.toLanguageTag());
		var metadata = this.mapper.createMetadata(codec, new TypeMetadata(Locale.class));
		var decoded = this.mapper.toObject(codec, value, metadata);
		Assertions.assertEquals(locale, decoded);
	}
}
