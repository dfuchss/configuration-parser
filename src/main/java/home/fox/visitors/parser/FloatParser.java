package home.fox.visitors.parser;

import java.lang.reflect.Field;

import home.fox.visitors.Visitable;

/**
 * This {@link Parser} is used for parsing {@link Float Floats}.
 *
 * @author Dominik Fuchß
 *
 */
public final class FloatParser implements Parser {
	@Override
	public boolean parse(Visitable obj, Field field, String definition) throws Exception {
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}
		if (!definition.matches("(-|\\+)?[0-9]+\\.[0-9]+(f|F)")) {
			return false;
		}
		field.set(obj, Float.parseFloat(definition));
		return true;
	}
}