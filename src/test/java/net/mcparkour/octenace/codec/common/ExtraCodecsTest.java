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

import java.util.Locale;
import java.util.UUID;
import net.mcparkour.octenace.codec.CommonCodec;
import net.mcparkour.octenace.codec.common.extra.ExtraCodecs;
import net.mcparkour.octenace.mapper.TestMapper;
import net.mcparkour.octenace.model.value.TestModelValue;
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
		CommonCodec<UUID> uuidCodec = ExtraCodecs.UUID_CODEC;
		UUID uuid = UUID.fromString("00000000-1000-0100-0010-000000000001");
		var encoded = uuidCodec.encode(uuid, UUID.class, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(uuid.toString(), value);
	}

	@Test
	public void testUUIDCodecDecode() {
		CommonCodec<UUID> uuidCodec = ExtraCodecs.UUID_CODEC;
		UUID uuid = UUID.fromString("00000000-1000-0100-0010-000000000001");
		TestModelValue value = new TestModelValue(uuid.toString());
		var decoded = uuidCodec.decode(value, UUID.class, this.mapper);
		Assertions.assertEquals(uuid, decoded);
	}

	@Test
	public void testLocaleCodecEncode() {
		CommonCodec<Locale> localeCodec = ExtraCodecs.LOCALE_CODEC;
		Locale locale = Locale.US;
		var encoded = localeCodec.encode(locale, Locale.class, this.mapper);
		Object value = encoded.getValue();
		Assertions.assertEquals(locale.toLanguageTag(), value);
	}

	@Test
	public void testLocaleCodecDecode() {
		CommonCodec<Locale> localeCodec = ExtraCodecs.LOCALE_CODEC;
		Locale locale = Locale.US;
		TestModelValue value = new TestModelValue(locale.toLanguageTag());
		var decoded = localeCodec.decode(value, Locale.class, this.mapper);
		Assertions.assertEquals(locale, decoded);
	}
}
