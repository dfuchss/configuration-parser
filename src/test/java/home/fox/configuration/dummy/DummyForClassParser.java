package home.fox.configuration.dummy;

import home.fox.configuration.Configurable;
import home.fox.configuration.annotations.SetParser;
import home.fox.configuration.annotations.SetterInfo;
import home.fox.configuration.setters.ResourceBundleSetter;
import home.fox.configuration.toParse.StringB;

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
