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

package net.mcparkour.octenace.mapper.property.name;

public class SeparatedCaseNameConverter implements NameConverter {

	private String separator;

	public SeparatedCaseNameConverter(String separator) {
		this.separator = separator;
	}

	@Override
	public String convert(String name) {
		int length = name.length();
		StringBuilder builder = new StringBuilder(length);
		for (int index = 0; index < length; index++) {
			char character = name.charAt(index);
			if (Character.isUpperCase(character)) {
				character = Character.toLowerCase(character);
				if (isPreviousCharacterLowerCase(name, index) || isPreviousCharacterUpperCase(name, index) && isNextCharacterLowerCase(name, index)) {
					builder.append(this.separator);
				}
			}
			builder.append(character);
		}
		return builder.toString();
	}

	private static boolean isPreviousCharacterLowerCase(String text, int index) {
		if (index == 0) {
			return false;
		}
		char previousCharacter = text.charAt(index - 1);
		return Character.isLowerCase(previousCharacter);
	}

	private static boolean isPreviousCharacterUpperCase(String text, int index) {
		if (index == 0) {
			return false;
		}
		char previousCharacter = text.charAt(index - 1);
		return Character.isUpperCase(previousCharacter);
	}

	private static boolean isNextCharacterLowerCase(String text, int index) {
		int nextIndex = index + 1;
		if (nextIndex >= text.length()) {
			return false;
		}
		char nextCharacter = text.charAt(nextIndex);
		return Character.isLowerCase(nextCharacter);
	}
}
