package home.fox.visitors.parser;

import java.lang.reflect.Field;

import home.fox.visitors.Visitable;

/**
 * An internal array parser.
 *
 * @author Dominik Fuchß
 *
 */
public class AParser implements Parser {
	@Override
	public boolean parse(Visitable obj, Field field, String definition) throws Exception {
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}
		if (definition == null || !definition.contains(",")) {
			return false;
		}
		field.set(obj, definition.split(","));
		return true;
	}
}
