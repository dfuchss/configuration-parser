package home.fox.configuration.toParse;

import home.fox.configuration.annotations.ClassParser;
import home.fox.configuration.parser.StringBParser;

/**
 * This class contains a String.
 *
 * @author Dominik Fuchss
 *
 */
@ClassParser(StringBParser.class)
public class StringB {
	/**
	 * The string.
	 */
	public String string;
}