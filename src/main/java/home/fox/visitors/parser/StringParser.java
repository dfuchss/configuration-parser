package home.fox.visitors.parser;

import java.lang.reflect.Field;

import home.fox.visitors.Visitable;

/**
 * This {@link Parser} is used for parsing {@link String Strings}.
 *
 * @author Dominik Fuchss
 *
 */
public final class StringParser implements Parser {
	@Override
	public boolean parse(Visitable obj, Field field, String definition) throws Exception {
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}
		field.set(obj, definition);
		return true;
	}

}