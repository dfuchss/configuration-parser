package org.fuchss.configuration.parser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Messages;

public final class ArrayParser {
	/**
	 * Parse the definition to the specific class.
	 *
	 * @param obj
	 *            the configurable Object or {@code null} if static setting
	 *            (class attributes)
	 * @param field
	 *            the current field
	 * @param definition
	 *            the String definition
	 * @param path
	 *            the recursive path to this element (field). For TopLevel: Use
	 *            {@code new String[0]}
	 * @return {@code true} if successful, {@code false} otherwise
	 * @throws Exception
	 *             will thrown by any error while parsing if no {@code false}
	 *             can be returned
	 *
	 */
	public boolean parse(Configurable obj, Field field, String definition, String[] path, Parser contentParser) throws Exception {
		if (field == null || definition == null || path == null || contentParser == null) {
			return false;
		}
		if (path.length > Parser.MAX_DEPTH) {
			Parser.LOGGER.error(Messages.getString("Parser.1")); //$NON-NLS-1$
			return false;
		}
		if (!field.getType().isArray()) {
			Parser.LOGGER.error(field + " is no array");
			return false;
		}
		String[] defs = definition.split(";");
		Object[] array = (Object[]) Array.newInstance(field.getType().getComponentType(), defs.length);
		for (int i = 0; i < defs.length; i++) {
			array[i] = contentParser.parse(defs[i], path);
		}

		field.set(obj, array);
		return true;
	}
}
