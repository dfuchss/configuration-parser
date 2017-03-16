package org.fuchss.configuration;

import org.apache.log4j.Level;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.annotations.AfterSetting;
import org.fuchss.configuration.dummy.DummyForAfterSet;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class tests {@link AfterSetting} functionality.
 *
 * @author Dominik Fuchss
 *
 */
public class AfterSetTest {
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
		Assert.assertFalse(DummyForAfterSet.afterStaticVisited);
		AfterSetTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForAfterSet.class);
		Assert.assertTrue(DummyForAfterSet.afterStaticVisited);
	}

	/**
	 * Test for object.
	 */
	@Test
	public void testObject() {
		DummyForAfterSet v = new DummyForAfterSet();
		Assert.assertFalse(v.afterObjectVisited);
		AfterSetTest.RESOURCE_BUNDLE_SETTER.setAttributes(v);
		Assert.assertTrue(v.afterObjectVisited);
	}

}
