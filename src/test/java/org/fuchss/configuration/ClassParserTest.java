package org.fuchss.configuration;

import org.fuchss.configuration.annotations.SetParser;
import org.fuchss.configuration.dummy.DummyForClassParser;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class tests {@link SetParser} functionality.
 *
 * @author Dominik Fuchss
 */
class ClassParserTest {
	/**
	 * An instance of {@link ResourceBundleSetter}.
	 */
	private static final Setter RESOURCE_BUNDLE_SETTER = new ResourceBundleSetter();

	/**
	 * Test for class.
	 */
	@Test
	void testStatic() {
		Assertions.assertNull(DummyForClassParser.classStringB);
		ClassParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForClassParser.class);
		Assertions.assertEquals("Earth", DummyForClassParser.classStringB.string);
	}

	/**
	 * Test for object.
	 */
	@Test
	void testObject() {
		DummyForClassParser v = new DummyForClassParser();
		Assertions.assertNull(v.objStringB);
		ClassParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(v);
		Assertions.assertEquals("Hello", v.objStringB.string);
	}

}
