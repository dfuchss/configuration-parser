package home.fox.visitors.parser;

import java.lang.reflect.Field;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;
import home.fox.visitors.annotations.VisitInfo;

/**
 * This interface defines a parser which will be used for parsing a String to a
 * specific Class for a {@link Visitor}.<br>
 * <b>It must be possible to create a parser via a default constructor</b>
 *
 * @author Dominik Fuchß
 * @see VisitInfo
 *
 */
public interface Parser {
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
	 * @return {@code true} if successful, {@code false} otherwise
	 * @throws Exception
	 *             will thrown by any error while parsing if no {@code false}
	 *             can be returned
	 *
	 */
	default boolean parse(Visitable obj, Field field, String definition) throws Exception {
		if (field == null || definition == null) {
			return false;
		}
		return true;
	}

}
