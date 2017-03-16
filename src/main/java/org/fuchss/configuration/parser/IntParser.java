package org.fuchss.configuration.parser;

import java.lang.reflect.Field;

import org.fuchss.configuration.Configurable;

/**
 * This {@link Parser} is used for parsing {@link Integer Integers}.
 *
 * @author Dominik Fuchss
 *
 */
public final class IntParser implements Parser {
	@Override
	public boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (!Parser.super.parse(obj, field, definition, path)) {
			return false;
		}
		field.set(obj, Integer.parseInt(definition));
		return true;
	}
}
