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

package net.mcparkour.octenace;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.mcparkour.octenace.annotation.Codec;
import net.mcparkour.octenace.annotation.Ignored;
import net.mcparkour.octenace.annotation.Property;
import org.jetbrains.annotations.Nullable;

public class TestDocument {

	private boolean primitiveBoolean;
	private Boolean wrapperBoolean;

	private char primitiveCharacter;
	private Character wrapperCharacter;

	private byte primitiveByte;
	private Byte wrapperByte;

	private short primitiveShort;
	private Short wrapperShort;

	private int primitiveInteger;
	private Integer wrapperInteger;

	private long primitiveLong;
	private Long wrapperLong;

	private float primitiveFloat;
	private Float wrapperFloat;

	private double primitiveDouble;
	private Double wrapperDouble;

	private String string;

	@Nullable
	private String nullString;

	@Property("bar")
	private String foo;

	@Ignored
	private String ignored;

	private TestSubDocument subConfiguration;

	private TestEnum testEnum;
	private TestEnum testEnum2;

	private String[] stringArray;
	private TestSubDocument[] objectArray;

	@Codec(LinkedHashSet.class)
	private Set<TestEnum> enumSet;

	@Codec(LinkedHashSet.class)
	private Set<String> stringSet;
	@Codec(LinkedHashSet.class)
	private Set<TestSubDocument> objectSet;

	private List<String> stringList;
	private List<TestSubDocument> objectList;

	@Codec(LinkedHashMap.class)
	private Map<String, String> stringMap;
	@Codec(LinkedHashMap.class)
	private Map<String, TestSubDocument> objectMap;

	public TestDocument() {
		this(true, true, 'c', 'c', (byte) 1, (byte) 1, (short) 1, (short) 1, 1, 1, 1L, 1L, 0.1F, 0.1F, 0.1, 0.1, "string", null, "foobar", new TestSubDocument(), TestEnum.TWO, TestEnum.THREE, new String[] {"1", "2", "3"}, new TestSubDocument[] {new TestSubDocument(), new TestSubDocument(), new TestSubDocument()}, Collections.createLinkedSet(TestEnum.ONE, TestEnum.TWO, TestEnum.THREE), Collections.createLinkedSet("1", "2", "3"), Collections.createLinkedSet(new TestSubDocument(), new TestSubDocument(), new TestSubDocument()), List.of("1", "2", "3"), List.of(new TestSubDocument(), new TestSubDocument(), new TestSubDocument()), Collections.createLinkedMap("foo", "1", "bar", "2", "foobar", "3"), Collections.createLinkedMap("foo", new TestSubDocument(), "bar", new TestSubDocument(), "foobar", new TestSubDocument()));
	}

	public TestDocument(boolean primitiveBoolean, Boolean wrapperBoolean, char primitiveCharacter, Character wrapperCharacter, byte primitiveByte, Byte wrapperByte, short primitiveShort, Short wrapperShort, int primitiveInteger, Integer wrapperInteger, long primitiveLong, Long wrapperLong, float primitiveFloat, Float wrapperFloat, double primitiveDouble, Double wrapperDouble, String string, @Nullable String nullString, String foo, TestSubDocument subConfiguration, TestEnum testEnum, TestEnum testEnum2, String[] stringArray, TestSubDocument[] objectArray, Set<TestEnum> enumSet, Set<String> stringSet, Set<TestSubDocument> objectSet, List<String> stringList, List<TestSubDocument> objectList, Map<String, String> stringMap, Map<String, TestSubDocument> objectMap) {
		this.primitiveBoolean = primitiveBoolean;
		this.wrapperBoolean = wrapperBoolean;
		this.primitiveCharacter = primitiveCharacter;
		this.wrapperCharacter = wrapperCharacter;
		this.primitiveByte = primitiveByte;
		this.wrapperByte = wrapperByte;
		this.primitiveShort = primitiveShort;
		this.wrapperShort = wrapperShort;
		this.primitiveInteger = primitiveInteger;
		this.wrapperInteger = wrapperInteger;
		this.primitiveLong = primitiveLong;
		this.wrapperLong = wrapperLong;
		this.primitiveFloat = primitiveFloat;
		this.wrapperFloat = wrapperFloat;
		this.primitiveDouble = primitiveDouble;
		this.wrapperDouble = wrapperDouble;
		this.string = string;
		this.nullString = nullString;
		this.foo = foo;
		this.subConfiguration = subConfiguration;
		this.testEnum = testEnum;
		this.testEnum2 = testEnum2;
		this.stringArray = stringArray;
		this.objectArray = objectArray;
		this.enumSet = enumSet;
		this.stringSet = stringSet;
		this.objectSet = objectSet;
		this.stringList = stringList;
		this.objectList = objectList;
		this.stringMap = stringMap;
		this.objectMap = objectMap;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TestDocument)) {
			return false;
		}
		TestDocument that = (TestDocument) obj;
		return this.primitiveBoolean == that.primitiveBoolean &&
			this.primitiveCharacter == that.primitiveCharacter &&
			this.primitiveByte == that.primitiveByte &&
			this.primitiveShort == that.primitiveShort &&
			this.primitiveInteger == that.primitiveInteger &&
			this.primitiveLong == that.primitiveLong &&
			Float.compare(that.primitiveFloat, this.primitiveFloat) == 0 &&
			Double.compare(that.primitiveDouble, this.primitiveDouble) == 0 &&
			Objects.equals(this.wrapperBoolean, that.wrapperBoolean) &&
			Objects.equals(this.wrapperCharacter, that.wrapperCharacter) &&
			Objects.equals(this.wrapperByte, that.wrapperByte) &&
			Objects.equals(this.wrapperShort, that.wrapperShort) &&
			Objects.equals(this.wrapperInteger, that.wrapperInteger) &&
			Objects.equals(this.wrapperLong, that.wrapperLong) &&
			Objects.equals(this.wrapperFloat, that.wrapperFloat) &&
			Objects.equals(this.wrapperDouble, that.wrapperDouble) &&
			Objects.equals(this.string, that.string) &&
			Objects.equals(this.nullString, that.nullString) &&
			Objects.equals(this.foo, that.foo) &&
			Objects.equals(this.ignored, that.ignored) &&
			Objects.equals(this.subConfiguration, that.subConfiguration) &&
			this.testEnum == that.testEnum &&
			this.testEnum2 == that.testEnum2 &&
			Arrays.equals(this.stringArray, that.stringArray) &&
			Arrays.equals(this.objectArray, that.objectArray) &&
			Objects.equals(this.enumSet, that.enumSet) &&
			Objects.equals(this.stringSet, that.stringSet) &&
			Objects.equals(this.objectSet, that.objectSet) &&
			Objects.equals(this.stringList, that.stringList) &&
			Objects.equals(this.objectList, that.objectList) &&
			Objects.equals(this.stringMap, that.stringMap) &&
			Objects.equals(this.objectMap, that.objectMap);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(this.primitiveBoolean, this.wrapperBoolean, this.primitiveCharacter, this.wrapperCharacter, this.primitiveByte, this.wrapperByte, this.primitiveShort, this.wrapperShort, this.primitiveInteger, this.wrapperInteger, this.primitiveLong, this.wrapperLong, this.primitiveFloat, this.wrapperFloat, this.primitiveDouble, this.wrapperDouble, this.string, this.nullString, this.foo, this.ignored, this.subConfiguration, this.testEnum, this.testEnum2, this.enumSet, this.stringSet, this.objectSet, this.stringList, this.objectList, this.stringMap, this.objectMap);
		result = 31 * result + Arrays.hashCode(this.stringArray);
		result = 31 * result + Arrays.hashCode(this.objectArray);
		return result;
	}

	@Override
	public String toString() {
		return "{" +
			"primitiveBoolean=" + this.primitiveBoolean +
			", wrapperBoolean=" + this.wrapperBoolean +
			", primitiveCharacter=" + this.primitiveCharacter +
			", wrapperCharacter=" + this.wrapperCharacter +
			", primitiveByte=" + this.primitiveByte +
			", wrapperByte=" + this.wrapperByte +
			", primitiveShort=" + this.primitiveShort +
			", wrapperShort=" + this.wrapperShort +
			", primitiveInteger=" + this.primitiveInteger +
			", wrapperInteger=" + this.wrapperInteger +
			", primitiveLong=" + this.primitiveLong +
			", wrapperLong=" + this.wrapperLong +
			", primitiveFloat=" + this.primitiveFloat +
			", wrapperFloat=" + this.wrapperFloat +
			", primitiveDouble=" + this.primitiveDouble +
			", wrapperDouble=" + this.wrapperDouble +
			", string=" + this.string +
			", nullString=" + this.nullString +
			", bar=" + this.foo +
			// ", ignored=" + this.ignored +
			", subConfiguration=" + this.subConfiguration +
			", testEnum=" + this.testEnum +
			", testEnum2=" + this.testEnum2 +
			", stringArray=" + Arrays.toString(this.stringArray) +
			", objectArray=" + Arrays.toString(this.objectArray) +
			", enumSet=" + this.enumSet +
			", stringSet=" + this.stringSet +
			", objectSet=" + this.objectSet +
			", stringList=" + this.stringList +
			", objectList=" + this.objectList +
			", stringMap=" + this.stringMap +
			", objectMap=" + this.objectMap +
			'}';
	}

	public boolean isPrimitiveBoolean() {
		return this.primitiveBoolean;
	}

	public Boolean getWrapperBoolean() {
		return this.wrapperBoolean;
	}

	public char getPrimitiveCharacter() {
		return this.primitiveCharacter;
	}

	public Character getWrapperCharacter() {
		return this.wrapperCharacter;
	}

	public byte getPrimitiveByte() {
		return this.primitiveByte;
	}

	public Byte getWrapperByte() {
		return this.wrapperByte;
	}

	public short getPrimitiveShort() {
		return this.primitiveShort;
	}

	public Short getWrapperShort() {
		return this.wrapperShort;
	}

	public int getPrimitiveInteger() {
		return this.primitiveInteger;
	}

	public Integer getWrapperInteger() {
		return this.wrapperInteger;
	}

	public long getPrimitiveLong() {
		return this.primitiveLong;
	}

	public Long getWrapperLong() {
		return this.wrapperLong;
	}

	public float getPrimitiveFloat() {
		return this.primitiveFloat;
	}

	public Float getWrapperFloat() {
		return this.wrapperFloat;
	}

	public double getPrimitiveDouble() {
		return this.primitiveDouble;
	}

	public Double getWrapperDouble() {
		return this.wrapperDouble;
	}

	public String getString() {
		return this.string;
	}

	@Nullable
	public String getNullString() {
		return this.nullString;
	}

	public String getFoo() {
		return this.foo;
	}

	public String getIgnored() {
		return this.ignored;
	}

	public TestSubDocument getSubConfiguration() {
		return this.subConfiguration;
	}

	public TestEnum getTestEnum() {
		return this.testEnum;
	}

	public TestEnum getTestEnum2() {
		return this.testEnum2;
	}

	public String[] getStringArray() {
		return this.stringArray;
	}

	public TestSubDocument[] getObjectArray() {
		return this.objectArray;
	}

	public Set<TestEnum> getEnumSet() {
		return this.enumSet;
	}

	public Set<String> getStringSet() {
		return this.stringSet;
	}

	public Set<TestSubDocument> getObjectSet() {
		return this.objectSet;
	}

	public List<String> getStringList() {
		return this.stringList;
	}

	public List<TestSubDocument> getObjectList() {
		return this.objectList;
	}

	public Map<String, String> getStringMap() {
		return this.stringMap;
	}

	public Map<String, TestSubDocument> getObjectMap() {
		return this.objectMap;
	}
}
