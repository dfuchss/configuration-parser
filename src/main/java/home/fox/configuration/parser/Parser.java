package home.fox.configuration.parser;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import home.fox.configuration.Configurable;
import home.fox.configuration.Messages;
import home.fox.configuration.Setter;
import home.fox.configuration.annotations.SetterInfo;

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
	default boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		if (field == null || definition == null || path == null) {
			Parser.LOGGER.warn(Messages.getString("Parser.0") + field + Messages.getString("Parser.1") + definition + Messages.getString("Parser.2") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ Arrays.toString(path) + Messages.getString("Parser.3")); //$NON-NLS-1$
			return false;
		}
		if (path.length > Parser.MAX_DEPTH) {
			Parser.LOGGER.error(Messages.getString("Parser.4")); //$NON-NLS-1$
			return false;
		}
		return true;
	}

}
