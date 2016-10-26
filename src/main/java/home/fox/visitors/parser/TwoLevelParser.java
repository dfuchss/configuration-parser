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

	@Override
	public final boolean parse(Visitable obj, Field field, String definition) throws Exception {
		// reset mapping
		this.mapping.clear();
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}

		String[] parts = definition.split(";");
		Pattern pat = Pattern.compile("(\\w|\\d|_)+::(\\w|\\d|\\+|-|\\.|,)+");
		for (String def : parts) {
			if (!pat.matcher(def).matches()) {
				System.err.println(def + " does not match!");
				return false;
			}
		}
		Pattern p = Pattern.compile("::");
		for (String kv : parts) {
			String[] split = p.split(kv);
			if (this.mapping.put(split[0], split[1]) != null) {
				System.err.println("WARING: Double definition for " + split[0]);
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
	 *             will thrown by Reflect stuff
	 *
	 */
	private final void apply(Visitable obj, Field field) throws Exception {
		Object instance = field.getType().getDeclaredConstructor().newInstance();
		if (!(instance instanceof Visitable)) {
			System.err.println("TwoLevelParser: Cannot parse " + field.getName() + " in " + obj.getClass().getSimpleName());
			return;
		}
		Visitor v = new MapVisitor(this.mapping);
		v.visit((Visitable) instance);
		field.set(obj, instance);
	}

}
