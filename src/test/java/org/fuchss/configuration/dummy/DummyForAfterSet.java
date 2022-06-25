package org.fuchss.configuration.dummy;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.annotations.AfterSetting;
import org.fuchss.configuration.annotations.NoSet;
import org.fuchss.configuration.annotations.SetterInfo;

/**
 * This class will be used to test {@link AfterSetting}.
 *
 * @author Dominik Fuchss
 */
@SetterInfo(res = "conf/dummyForResourceBundle", set = true)
public class DummyForAfterSet implements Configurable {
	/**
	 * Indicates whether {@link #afterStatic()} was invoked.
	 */
	@NoSet
	public static boolean afterStaticVisited = false;
	/**
	 * Indicates whether {@link #afterObject()} was invoked.
	 */
	@NoSet
	public boolean afterObjectVisited = false;

	/**
	 * Set {@link #afterStaticVisited}.
	 */
	@AfterSetting
	public static void afterStatic() {
		DummyForAfterSet.afterStaticVisited = true;
	}

	/**
	 * Set {@link #afterObjectVisited}.
	 */
	@AfterSetting
	public void afterObject() {
		this.afterObjectVisited = true;
	}
}
