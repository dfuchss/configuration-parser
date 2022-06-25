package org.fuchss.configuration.parser;

import org.fuchss.configuration.Setter;
import org.fuchss.configuration.annotations.SetterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interface defines a parser which will be used for parsing a String to a
 * specific Class for a {@link Setter}.<br>
 * <b>It must be possible to create a parser via a default constructor</b>
 *
 * @author Dominik Fuchss
 * @see SetterInfo
 */
public interface Parser {
	/**
	 * The logger of the Parser class.
	 */
	Logger LOGGER = LoggerFactory.getLogger(Parser.class);

	/**
	 * The maximum recursion depth.
	 */
	int MAX_DEPTH = 5;

	/**
	 * Parse the definition.
	 *
	 * @param definition the String definition
	 * @param path       the recursive path to this element (field). For TopLevel: Use
	 *                   {@code new String[0]}
	 * @return {@code Object} if successful, {@code null} otherwise
	 * @throws Exception will thrown by any error while parsing if no {@code null} can
	 *                   be returned
	 */
	default Object parse(String definition, String[] path) throws Exception {
		if (definition == null || path == null) {
			return null;
		}
		if (path.length > Parser.MAX_DEPTH) {
			Parser.LOGGER.error("MAX_DEPTH reached. Parsing aborted.");
			return null;
		}
		return this.parseIt(definition, path);
	}

	/**
	 * Internal implementation of the parser. Will be invoked by
	 * {@link #parse(String, String[])}.
	 *
	 * @param definition the String definition
	 * @param path       the recursive path to this element (field). For TopLevel: Use
	 *                   {@code new String[0]}
	 * @return {@code Object} if successful, {@code null} otherwise
	 * @throws Exception will thrown by any error while parsing if no {@code null} can
	 *                   be returned
	 */
	Object parseIt(String definition, String[] path) throws Exception;

}
