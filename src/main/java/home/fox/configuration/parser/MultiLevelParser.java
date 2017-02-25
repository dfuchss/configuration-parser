package home.fox.configuration.parser;

import java.lang.reflect.Field;
import java.util.Arrays;

import home.fox.configuration.Configurable;
import home.fox.configuration.Setter;
import home.fox.configuration.setters.RecursiveSetter;

/**
 * This class realizes a parser for more complex objects, which have to be
 * parsed recursive.
 *
 * @author Dominik Fuchss
 * @see RecursiveSetter
 */
public final class MultiLevelParser implements Parser {
	/**
	 * The parsers parent setter.
	 */
	private final Setter parent;

	/**
	 * Create the parser.
	 *
	 * @param parent
	 *            the parent setter, where the child parsers get their info from
	 */
	public MultiLevelParser(Setter parent) {
		this.parent = parent;
	}

	@Override
	public final boolean parse(Configurable obj, Field field, String definition, String[] path) throws Exception {
		// No definition needed here, as we will parse recursive.
		if (!Parser.super.parse(obj, field, definition == null ? "" : definition, path)) {
			return false;
		}

		Object instance = field.getType().getDeclaredConstructor().newInstance();
		if (!(instance instanceof Configurable)) {
			Parser.LOGGER.error("MultiLevelParser: " //
					+ "Cannot parse " + field.getName() + " in " //
					+ (obj == null ? "unknown class" : obj.getClass().getSimpleName()) //
					+ ": The field could not be instantiated as Configurable.");
			return false;
		}
		String[] newPath = Arrays.copyOf(path, path.length + 1);
		newPath[path.length] = field.getName();
		Setter v = new RecursiveSetter(newPath, this.parent.getParent());
		v.setAttributes((Configurable) instance);
		field.set(obj, instance);

		return true;
	}
}
