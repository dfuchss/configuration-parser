package home.fox.visitors.parser;

import java.lang.reflect.Field;

import home.fox.visitors.Visitable;

/**
 * This {@link Parser} is used for parsing {@link Character Characters}.
 *
 * @author Dominik Fuchss
 *
 */
public final class CharParser implements Parser {
	@Override
	public boolean parse(Visitable obj, Field field, String definition) throws Exception {
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}
		if (definition.length() != 1) {
			throw new Exception("Length != 1 => No Character");
		}
		field.set(obj, definition.charAt(0));
		return true;
	}
}