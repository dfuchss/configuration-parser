package home.fox.configuration.parser;

import java.lang.reflect.Field;

import home.fox.configuration.Configurable;
import home.fox.configuration.parser.Parser;

/**
 * An internal array parser.
 *
 * @author Dominik Fuchss
 *
 */
public class AParser implements Parser {
	@Override
	public boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (!Parser.super.parse(obj, field, definition, path)) {
			return false;
		}
		if (definition == null || !definition.contains(",")) {
			return false;
		}
		field.set(obj, definition.split(","));
		return true;
	}
}
