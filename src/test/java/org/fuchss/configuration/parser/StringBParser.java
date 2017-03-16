package org.fuchss.configuration.parser;

import java.lang.reflect.Field;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.parser.Parser;
import org.fuchss.configuration.toParse.StringB;

/**
 * An internal StringB parser.
 *
 * @author Dominik Fuchss
 *
 */
public class StringBParser implements Parser {
	@Override
	public boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (!Parser.super.parse(obj, field, definition, path)) {
			return false;
		}
		StringB string = new StringB();
		string.string = "" + definition;
		field.set(obj, string);
		return true;
	}
}
