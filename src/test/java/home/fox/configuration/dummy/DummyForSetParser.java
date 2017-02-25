package home.fox.configuration.dummy;

import home.fox.configuration.Configurable;
import home.fox.configuration.annotations.SetParser;
import home.fox.configuration.annotations.SetterInfo;
import home.fox.configuration.parser.AParser;
import home.fox.configuration.setters.ResourceBundleSetter;

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
