package home.fox.visitors.toParse;

import home.fox.visitors.annotations.ClassParser;
import home.fox.visitors.parser.StringBParser;

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