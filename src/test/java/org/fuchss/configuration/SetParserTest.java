package org.fuchss.configuration;

import org.apache.log4j.Level;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.annotations.SetParser;
import org.fuchss.configuration.dummy.DummyForSetParser;
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
public class SetParserTest {
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
		Assert.assertNull(DummyForSetParser.array);
		SetParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForSetParser.class);
		Assert.assertArrayEquals(new String[] { "1", "2" }, DummyForSetParser.array);
	}

	/**
	 * Test for object.
	 */
	@Test
	public void testObject() {
		DummyForSetParser v = new DummyForSetParser();
		Assert.assertNull(v.objArray);
		SetParserTest.RESOURCE_BUNDLE_SETTER.setAttributes(v);
		Assert.assertArrayEquals(new String[] { "2", "3" }, v.objArray);
	}

}
