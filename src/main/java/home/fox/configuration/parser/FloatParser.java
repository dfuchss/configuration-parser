package home.fox.configuration.parser;

import java.lang.reflect.Field;

import home.fox.configuration.Configurable;

/**
 * This {@link Parser} is used for parsing {@link Float Floats}.
 *
 * @author Dominik Fuchss
 *
 */
public final class FloatParser implements Parser {
	@Override
	public boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (!Parser.super.parse(obj, field, definition, path)) {
			return false;
		}
		field.set(obj, Float.parseFloat(definition));
		return true;
	}
}