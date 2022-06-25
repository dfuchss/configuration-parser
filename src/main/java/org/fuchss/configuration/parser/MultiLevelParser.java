package org.fuchss.configuration.parser;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.setters.RecursiveSetter;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * This class realizes a parser for more complex objects, which have to be
 * parsed recursive.
 *
 * @author Dominik Fuchss
 * @see RecursiveSetter
 */
public final class MultiLevelParser {
	/**
	 * The parsers parent setter.
	 */
	private final Setter parent;

	/**
	 * Create the parser.
	 *
	 * @param parent the parent setter, where the child parsers get their info from
	 */
	public MultiLevelParser(Setter parent) {
		this.parent = parent;
	}

	/**
	 * Parse the definition to the specific class.
	 *
	 * @param obj        the configurable Object or {@code null} if static setting
	 *                   (class attributes)
	 * @param field      the current field
	 * @param definition the String definition
	 * @param path       the recursive path to this element (field). For TopLevel: Use
	 *                   {@code new String[0]}
	 * @return {@code true} if successful, {@code false} otherwise
	 * @throws Exception will thrown by any error while parsing if no {@code false}
	 *                   can be returned
	 */
	public final boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		// No definition needed here, as we will parse recursive.
		if (field == null || path == null) {
			return false;
		}
		if (path.length > Parser.MAX_DEPTH) {
			Parser.LOGGER.error("MAX_DEPTH reached. Parsing aborted.");
			return false;
		}
		Object instance = field.getType().getDeclaredConstructor().newInstance();
		if (!(instance instanceof Configurable)) {
			Parser.LOGGER.error("MultiLevelParser: Cannot parse " //
					+ field.getName() + " in " + (obj == null ? "unknown class" : obj.getClass().getSimpleName()) + " The field could not be instantiated as Configurable.");
			return false;
		}
		String[] newPath = Arrays.copyOf(path, path.length + 1);
		newPath[path.length] = field.getName();
		Setter v = new RecursiveSetter(newPath, this.parent.getParent());
		v.setAttributes((Configurable) instance);
		if (instance != null) {
			field.set(obj, instance);
		}
		return instance != null;
	}
}
