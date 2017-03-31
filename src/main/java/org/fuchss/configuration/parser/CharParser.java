package org.fuchss.configuration.parser;

import org.fuchss.configuration.Messages;

/**
 * This {@link Parser} is used for parsing {@link Character Characters}.
 *
 * @author Dominik Fuchss
 *
 */
public final class CharParser implements Parser {
	@Override
	public Object parseIt(String definition, String[] path) throws Exception {
		if (definition.length() != 1) {
			Parser.LOGGER.error(Messages.getString("CharParser.0")); //$NON-NLS-1$
			return null;
		}
		return definition.charAt(0);
	}
}