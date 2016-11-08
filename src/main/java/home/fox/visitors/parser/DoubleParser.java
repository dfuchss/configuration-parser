package home.fox.visitors.parser;

import java.lang.reflect.Field;

import home.fox.visitors.Visitable;

/**
 * This {@link Parser} is used for parsing {@link Double Doubles}.
 *
 * @author Dominik Fuchss
 *
 */
public final class DoubleParser implements Parser {
	@Override
	public boolean parse(Visitable obj, Field field, String definition, String... path) throws Exception {
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}
		field.set(obj, Double.parseDouble(definition));
		return true;
	}
}