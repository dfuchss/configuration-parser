package org.fuchss.configuration;

import org.fuchss.configuration.annotations.AfterSetting;
import org.fuchss.configuration.dummy.DummyForAfterSet;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class tests {@link AfterSetting} functionality.
 *
 * @author Dominik Fuchss
 */
class AfterSetTest {
	/**
	 * An instance of {@link ResourceBundleSetter}.
	 */
	private static final Setter RESOURCE_BUNDLE_SETTER = new ResourceBundleSetter();

	/**
	 * Test for class.
	 */
	@Test
	void testStatic() {
		Assertions.assertFalse(DummyForAfterSet.afterStaticVisited);
		AfterSetTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForAfterSet.class);
		Assertions.assertTrue(DummyForAfterSet.afterStaticVisited);
	}

	/**
	 * Test for object.
	 */
	@Test
	void testObject() {
		DummyForAfterSet v = new DummyForAfterSet();
		Assertions.assertFalse(v.afterObjectVisited);
		AfterSetTest.RESOURCE_BUNDLE_SETTER.setAttributes(v);
		Assertions.assertTrue(v.afterObjectVisited);
	}

}
