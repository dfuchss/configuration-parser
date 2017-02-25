package home.fox.configuration.parser;

import java.lang.reflect.Field;

import home.fox.configuration.Configurable;
import home.fox.configuration.parser.Parser;
import home.fox.configuration.toParse.StringB;

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
