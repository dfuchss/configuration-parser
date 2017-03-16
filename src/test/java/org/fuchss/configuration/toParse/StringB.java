package org.fuchss.configuration.toParse;

import org.fuchss.configuration.annotations.ClassParser;
import org.fuchss.configuration.parser.StringBParser;

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