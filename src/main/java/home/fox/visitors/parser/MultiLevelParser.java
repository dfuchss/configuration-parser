package home.fox.visitors.parser;

import java.lang.reflect.Field;
import java.util.Arrays;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;
import home.fox.visitors.visitors.RecursiveVisitor;

/**
 * This class realizes a parser for more complex objects, which have to be
 * parsed recursive.
 *
 * @author Dominik Fuchss
 * @see RecursiveVisitor
 */
public final class MultiLevelParser implements Parser {
	/**
	 * The parsers parent visitor.
	 */
	private final Visitor parent;

	/**
	 * Create the parser.
	 *
	 * @param parent
	 *            the parent visitor, where the child parsers get their info
	 *            from
	 */
	public MultiLevelParser(Visitor parent) {
		this.parent = parent;
	}

	@Override
	public final boolean parse(Visitable obj, Field field, String definition, String[] path) throws Exception {
		// No definition needed here, as we will parse recursive.
		if (!Parser.super.parse(obj, field, definition == null ? "" : definition, path)) {
			return false;
		}

		Object instance = field.getType().getDeclaredConstructor().newInstance();
		if (!(instance instanceof Visitable)) {
			Parser.LOGGER.error("MultiLevelParser: " //
					+ "Cannot parse " + field.getName() + " in " //
					+ (obj == null ? "unknown class" : obj.getClass().getSimpleName()) //
					+ ": The field could not be instantiated as Visitable.");
			return false;
		}
		String[] newPath = Arrays.copyOf(path, path.length + 1);
		newPath[path.length] = field.getName();
		Visitor v = new RecursiveVisitor(newPath, this.parent.getParent());
		v.visit((Visitable) instance);
		field.set(obj, instance);

		return true;
	}
}
