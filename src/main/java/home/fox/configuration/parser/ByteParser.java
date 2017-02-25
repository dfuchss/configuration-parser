package home.fox.configuration.parser;

import java.lang.reflect.Field;

import home.fox.configuration.Configurable;

/**
 * This {@link Parser} is used for parsing {@link Byte Bytes}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ByteParser implements Parser {
	@Override
	public boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (!Parser.super.parse(obj, field, definition, path)) {
			return false;
		}
		field.set(obj, Byte.parseByte(definition));
		return true;
	}
}