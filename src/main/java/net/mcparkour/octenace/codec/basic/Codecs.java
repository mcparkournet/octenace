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

package net.mcparkour.octenace.codec.basic;

import java.util.Locale;
import java.util.UUID;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.codec.basic.collection.CollectionCodecs;
import net.mcparkour.octenace.codec.basic.primitive.PrimitiveCodecs;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.CodecRegistryBuilder;

public final class Codecs {

	public static final Codec<Enum<?>> ENUM_CODEC = new EnumCodec();
	public static final Codec<String> STRING_CODEC = new StringCodec();
	public static final Codec<UUID> UUID_CODEC = new UUIDCodec();
	public static final Codec<Locale> LOCALE_CODEC = new LocaleCodec();

	public static final CodecRegistry BASIC_CODEC_REGISTRY = new CodecRegistryBuilder()
		.registry(CollectionCodecs.COLLECTION_CODEC_REGISTRY)
		.registry(PrimitiveCodecs.PRIMITIVE_CODEC_REGISTRY)
		.codec(ENUM_CODEC, Enum.class)
		.codec(STRING_CODEC, String.class)
		.codec(UUID_CODEC, UUID.class)
		.codec(LOCALE_CODEC, Locale.class)
		.build();

	private Codecs() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}
}
