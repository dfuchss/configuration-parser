package org.fuchss.configuration.parser;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Messages;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.setters.RecursiveSetter;

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
			Parser.LOGGER.error(Messages.getString("MultiLevelParser.1") // //$NON-NLS-1$
					+ Messages.getString("MultiLevelParser.2") + field.getName() + Messages.getString("MultiLevelParser.3") // //$NON-NLS-1$ //$NON-NLS-2$
					+ (obj == null ? Messages.getString("MultiLevelParser.4") : obj.getClass().getSimpleName()) // //$NON-NLS-1$
					+ Messages.getString("MultiLevelParser.5")); //$NON-NLS-1$
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
