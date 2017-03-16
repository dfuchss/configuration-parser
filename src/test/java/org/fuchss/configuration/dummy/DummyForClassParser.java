package org.fuchss.configuration.dummy;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.annotations.SetParser;
import org.fuchss.configuration.annotations.SetterInfo;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.fuchss.configuration.toParse.StringB;

/**
 * This class will be used to test several functionality of the
 * {@link ResourceBundleSetter} and {@link SetParser}.
 *
 * @author Dominik Fuchss
 *
 */
@SetterInfo(res = "conf/dummyForResourceBundle", set = true)
public class DummyForClassParser implements Configurable {

	/**
	 * Shall be "Earth".
	 */
	public static StringB classStringB;

	/**
	 * Shall be "Hello".
	 */
	public StringB objStringB;

}
