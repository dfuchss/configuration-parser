package org.fuchss.configuration.dummy;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.annotations.SetParser;
import org.fuchss.configuration.annotations.SetterInfo;
import org.fuchss.configuration.parser.AParser;
import org.fuchss.configuration.setters.ResourceBundleSetter;

/**
 * This class will be used to test several functionality of the
 * {@link ResourceBundleSetter} and {@link SetParser}.
 *
 * @author Dominik Fuchss
 *
 */
@SetterInfo(res = "conf/dummyForResourceBundle", set = true)
public class DummyForSetParser implements Configurable {

	/**
	 * Shall be ["1","2"].
	 */
	@SetParser(AParser.class)
	public static String[] array;

	/**
	 * Shall be ["2","3"].
	 */
	@SetParser(AParser.class)
	public String[] objArray;
}
