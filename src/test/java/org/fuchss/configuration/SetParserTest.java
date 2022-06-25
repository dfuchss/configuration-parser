package org.fuchss.configuration;

import org.fuchss.configuration.annotations.SetParser;
import org.fuchss.configuration.dummy.DummyForSetParser;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class tests {@link SetParser} functionality.
 *
 * @author Dominik Fuchss
 */
class SetParserTest {
	/**
	 * An instance of {@link ResourceBundleSetter}.
	 */
	private static final Setter RESOURCE_BUNDLE_SETTER = new ResourceBundleSetter();

	/**
	 * Test for class.
	 */
	@Test
	void testStatic() {
		Assertions.assertNull(DummyForSetParser.array);
		SetParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForSetParser.class);
		Assertions.assertArrayEquals(new String[] { "1", "2" }, DummyForSetParser.array);
	}

	/**
	 * Test for object.
	 */
	@Test
	void testObject() {
		DummyForSetParser v = new DummyForSetParser();
		Assertions.assertNull(v.objArray);
		SetParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(v);
		Assertions.assertArrayEquals(new String[] { "2", "3" }, v.objArray);
	}

}
