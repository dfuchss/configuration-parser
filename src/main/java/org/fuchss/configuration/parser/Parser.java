package org.fuchss.configuration.parser;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.fuchss.configuration.Messages;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.annotations.SetterInfo;

/**
 * This interface defines a parser which will be used for parsing a String to a
 * specific Class for a {@link Setter}.<br>
 * <b>It must be possible to create a parser via a default constructor</b>
 *
 * @author Dominik Fuchss
 * @see SetterInfo
 *
 */
public interface Parser {
	/**
	 * The logger of the Parser class.
	 */
	final Logger LOGGER = Parser.getLogger();

	/**
	 * The maximum recursion depth.
	 */
	final int MAX_DEPTH = 5;

	/**
	 * Do not invoke. Will be used to set {@link #LOGGER}
	 *
	 * @return the logger instance
	 */
	static Logger getLogger() {
		Logger logger = Logger.getLogger(Parser.class);
		logger.setLevel(Level.ERROR);
		// Will set via Setter-Logger
		// BasicConfigurator.configure();
		return logger;

	}

	/**
	 * Parse the definition.
	 *
	 * @param definition
	 *            the String definition
	 * @param path
	 *            the recursive path to this element (field). For TopLevel: Use
	 *            {@code new String[0]}
	 * @return {@code Object} if successful, {@code null} otherwise
	 * @throws Exception
	 *             will thrown by any error while parsing if no {@code null} can
	 *             be returned
	 *
	 */
	default Object parse(String definition, String[] path) throws Exception {
		if (definition == null || path == null) {
			return null;
		}
		if (path.length > Parser.MAX_DEPTH) {
			Parser.LOGGER.error(Messages.getString("Parser.1")); //$NON-NLS-1$
			return null;
		}
		return this.parseIt(definition, path);
	}

	/**
	 * Internal implementation of the parser. Will be invoked by
	 * {@link #parse(String, String[])}.
	 *
	 * @param definition
	 *            the String definition
	 * @param path
	 *            the recursive path to this element (field). For TopLevel: Use
	 *            {@code new String[0]}
	 * @return {@code Object} if successful, {@code null} otherwise
	 * @throws Exception
	 *             will thrown by any error while parsing if no {@code null} can
	 *             be returned
	 *
	 */

	Object parseIt(String definition, String[] path) throws Exception;

}
