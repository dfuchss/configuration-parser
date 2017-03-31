package org.fuchss.configuration.parser;

import org.fuchss.configuration.toParse.StringB;

/**
 * An internal StringB parser.
 *
 * @author Dominik Fuchss
 *
 */
public class StringBParser implements Parser {
	@Override
	public Object parseIt(String definition, String[] path) throws Exception {
		StringB string = new StringB();
		string.string = "" + definition;
		return string;
	}
}
