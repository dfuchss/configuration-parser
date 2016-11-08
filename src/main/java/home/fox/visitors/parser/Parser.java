package home.fox.visitors.parser;

import java.lang.reflect.Field;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;
import home.fox.visitors.annotations.VisitInfo;

/**
 * This interface defines a parser which will be used for parsing a String to a
 * specific Class for a {@link Visitor}.<br>
 * <b>It must be possible to create a parser via a default constructor</b>
 *
 * @author Dominik Fuchss
 * @see VisitInfo
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
		// Will set via Visitor-Parser.
		// BasicConfigurator.configure();
		return logger;

	}

	/**
	 * Parse the definition to the specific class.
	 *
	 * @param obj
	 *            the Visitable Object or {@code null} if static visit (class
	 *            visit)
	 * @param field
	 *            the current field
	 * @param definition
	 *            the String definition
	 * @param path
	 *            the recursive path to this element (field).
	 * @return {@code true} if successful, {@code false} otherwise
	 * @throws Exception
	 *             will thrown by any error while parsing if no {@code false}
	 *             can be returned
	 *
	 */
	default boolean parse(Visitable obj, Field field, String definition, String... path) throws Exception {
		if (field == null || definition == null) {
			Parser.LOGGER.error("Content cannot be parsed: field (" + field + ") or definition (" + definition + ") is null.");
			return false;
		}
		if (path.length > Parser.MAX_DEPTH) {
			Parser.LOGGER.error("Max depth reached. Parsing aborted.");
			return false;
		}
		return true;
	}

}
