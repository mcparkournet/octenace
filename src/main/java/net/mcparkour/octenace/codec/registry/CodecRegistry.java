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

package net.mcparkour.octenace.codec.registry;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.mapper.metadata.Metadata;
import org.jetbrains.annotations.Nullable;

public class CodecRegistry<O, A, V> {

	private Map<Class<?>, Codec<O, A, V, ? extends Metadata, ?>> codecs;

	public CodecRegistry(Map<Class<?>, Codec<O, A, V, ? extends Metadata, ?>> codecs) {
		var comparator = Comparator.comparingInt(this::getEntryInheritanceDepth).reversed();
		this.codecs = codecs.entrySet().stream()
			.sorted(comparator)
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (codec1, codec2) -> codec1, LinkedHashMap::new));
	}

	private int getEntryInheritanceDepth(Entry<Class<?>, Codec<O, A, V, ? extends Metadata, ?>> entry) {
		Class<?> key = entry.getKey();
		return getInheritanceDepth(key);
	}

	private static int getInheritanceDepth(Class<?> type) {
		if (type == Object.class) {
			return -1;
		}
		int depth = 0;
		Class<?> superclass = type.getSuperclass();
		while (superclass != null) {
			superclass = superclass.getSuperclass();
			depth++;
		}
		return depth;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public <T> Codec<O, A, V, ? extends Metadata, T> get(Class<T> type) {
		return (Codec<O, A, V, ? extends Metadata, T>) this.codecs.computeIfAbsent(type, this::computeCodec);
	}

	@Nullable
	private Codec<O, A, V, ? extends Metadata, ?> computeCodec(Class<?> type) {
		return this.codecs.entrySet().stream()
			.filter(entry -> entry.getKey().isAssignableFrom(type))
			.findFirst()
			.map(Entry::getValue)
			.orElse(null);
	}

	Map<Class<?>, Codec<O, A, V, ? extends Metadata, ?>> getCodecs() {
		return Map.copyOf(this.codecs);
	}
}
