package org.fuchss.configuration;

import org.fuchss.configuration.annotations.SetterInfo;
import org.fuchss.configuration.setters.RecursiveSetter;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class shall test functionality (remaining tests) of the {@link Setter
 * Visitors}.
 *
 * @author Dominik Fuchss
 */
public class SetterTest {

	/**
	 * Check behavior for missing {@link SetterInfo} for RecursiveSetter.
	 *
	 * @throws Exception test needs to wait
	 */
	@Test
	public void checkNoVisitInfoR() throws Exception {
		RecursiveSetter visitor = new RecursiveSetter(new String[0], new ResourceBundleSetter());
		visitor.setAttributes(MissingVisitInfo.class);
		visitor.setAttributes(new MissingVisitInfo());
		Assertions.assertNull(visitor.getValue("KEY"));
	}

	/**
	 * Test handling of null values.
	 */
	@Test
	void checkNullError() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new RecursiveSetter(new String[0], null));
	}

	/**
	 * Check behavior for missing {@link SetterInfo}.
	 *
	 * @throws Exception test needs to wait
	 */
	@Test
	void checkNoVisitInfo() throws Exception {
		ResourceBundleSetter visitor = new ResourceBundleSetter();
		visitor.setAttributes(MissingVisitInfo.class);
		visitor.setAttributes(new MissingVisitInfo());
		Assertions.assertNull(visitor.getValue("KEY"));
	}

	/**
	 * A class for {@link SetterTest#checkNoVisitInfo()}.
	 *
	 * @author Dominik Fuchss
	 */
	public static class MissingVisitInfo implements Configurable {
	}

}
