package home.fox.configuration.parser;

import java.lang.reflect.Field;

import home.fox.configuration.Configurable;
import home.fox.configuration.Messages;

/**
 * This {@link Parser} is used for parsing {@link Character Characters}.
 *
 * @author Dominik Fuchss
 *
 */
public final class CharParser implements Parser {
	@Override
	public boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (!Parser.super.parse(obj, field, definition, path)) {
			return false;
		}
		if (definition.length() != 1) {
			Parser.LOGGER.error(Messages.getString("CharParser.0")); //$NON-NLS-1$
			return false;
		}
		field.set(obj, definition.charAt(0));
		return true;
	}
}