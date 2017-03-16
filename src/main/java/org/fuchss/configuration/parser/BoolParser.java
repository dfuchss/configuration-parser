package org.fuchss.configuration.parser;

import java.lang.reflect.Field;

import org.fuchss.configuration.Configurable;

/**
 * This {@link Parser} is used for parsing {@link Boolean Booleans}.
 *
 * @author Dominik Fuchss
 *
 */
public final class BoolParser implements Parser {
	@Override
	public boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (!Parser.super.parse(obj, field, definition, path)) {
			return false;
		}
		field.set(obj, Boolean.parseBoolean(definition));
		return true;
	}
}