package org.fuchss.configuration;

import org.apache.log4j.Level;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.annotations.SetParser;
import org.fuchss.configuration.dummy.DummyForClassParser;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class tests {@link SetParser} functionality.
 *
 * @author Dominik Fuchss
 *
 */
public class ClassParserTest {
	/**
	 * An instance of {@link ResourceBundleSetter}.
	 */
	private static final Setter RESOURCE_BUNDLE_SETTER = new ResourceBundleSetter();

	/**
	 * Activate the logger of {@link Setter}.
	 */
	@BeforeClass
	public static void activateLogger() {
		Setter.LOGGER.setLevel(Level.ALL);
	}

	/**
	 * Test for class.
	 */
	@Test
	public void testStatic() {
		Assert.assertNull(DummyForClassParser.classStringB);
		ClassParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForClassParser.class);
		Assert.assertEquals("Earth", DummyForClassParser.classStringB.string);
	}

	/**
	 * Test for object.
	 */
	@Test
	public void testObject() {
		DummyForClassParser v = new DummyForClassParser();
		Assert.assertNull(v.objStringB);
		ClassParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(v);
		Assert.assertEquals("Hello", v.objStringB.string);
	}

}
