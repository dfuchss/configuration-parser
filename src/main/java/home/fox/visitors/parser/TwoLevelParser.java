package home.fox.visitors.parser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;
import home.fox.visitors.visitors.MapVisitor;

/**
 * This {@link Parser} is able to parse options from a config.<br>
 * Syntax: name1::optionValue1;name2::optionValue2
 *
 * @author Dominik Fuchss
 *
 */
public final class TwoLevelParser implements Parser {
	/**
	 * The mapping from stage-2-key to value.
	 */
	private Map<String, String> mapping = new HashMap<>();
	/**
	 * The pattern for the sub-definitions. (e.g. name1::option1,optionA )
	 */
	private Pattern subPattern = Pattern.compile("(\\w|\\d|_)+::(\\w|\\d|\\+|-|\\.|,)+");
	/**
	 * The delimiter pattern :: .
	 */
	private Pattern delimiter = Pattern.compile("::");

	@Override
	public final boolean parse(Visitable obj, Field field, String definition) throws Exception {
		// reset mapping
		this.mapping.clear();
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}

		String[] parts = definition.split(";");
		for (String def : parts) {
			if (!this.subPattern.matcher(def).matches()) {
				throw new Exception(def + " was rejected because it is no SubPattern (\\w|\\d|_)+::(\\w|\\d|\\+|-|\\.|,)+!");
			}
		}
		for (String kv : parts) {
			String[] split = this.delimiter.split(kv);
			if (this.mapping.put(split[0], split[1]) != null) {
				Visitor.LOGGER.warn("Double definition for " + split[0] + " in " + this.getClass().getSimpleName());
			}
		}
		this.apply(obj, field);
		return true;
	}

	/**
	 * This method will be invoked by {@link #parse(Visitable, Field, String)}
	 * and shall set the field.
	 *
	 * @param obj
	 *            the Visitable Object or {@code null} if static visit (class
	 *            visit)
	 * @param field
	 *            the current field
	 * @throws Exception
	 *             will thrown if field could not be parsed
	 *
	 */
	private final void apply(Visitable obj, Field field) throws Exception {
		Object instance = field.getType().getDeclaredConstructor().newInstance();
		if (!(instance instanceof Visitable)) {
			throw new Exception("TwoLevelParser: " //
					+ "Cannot parse " + field.getName() + " in " + (obj == null ? "unknown class" : obj.getClass().getSimpleName())
					+ ": The field could not be instantiated as Visitable.");
		}
		Visitor v = new MapVisitor(this.mapping);
		v.visit((Visitable) instance);
		field.set(obj, instance);
	}

}
