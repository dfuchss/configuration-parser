package org.fuchss.configuration.parser;

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
			Parser.LOGGER.error("Length \\!= 1 => No Character");
			return null;
		}
		return definition.charAt(0);
	}
}