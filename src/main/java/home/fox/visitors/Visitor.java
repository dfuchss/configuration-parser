package home.fox.visitors;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import home.fox.visitors.annotations.AfterVisit;
import home.fox.visitors.annotations.ClassParser;
import home.fox.visitors.annotations.NoVisit;
import home.fox.visitors.annotations.SetParser;
import home.fox.visitors.annotations.VisitInfo;
import home.fox.visitors.parser.BoolParser;
import home.fox.visitors.parser.ByteParser;
import home.fox.visitors.parser.CharParser;
import home.fox.visitors.parser.DoubleParser;
import home.fox.visitors.parser.FloatParser;
import home.fox.visitors.parser.IntParser;
import home.fox.visitors.parser.LongParser;
import home.fox.visitors.parser.Parser;
import home.fox.visitors.parser.StringParser;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class supports the setting of Values and/or Attributes to Classes and
 * Objects which implements the {@link Visitable} interface.
 *
 * @author Dominik Fuchß
 * @see VisitInfo
 * @see AfterVisit
 *
 */
public abstract class Visitor {

	/**
	 * Get a new modifiable visitor (default visitor:
	 * {@link ResourceBundleVisitor}).
	 *
	 * @return the new visior
	 */
	public static final Visitor getNewVisitor() {
		return new ResourceBundleVisitor();
	}

	/**
	 * Prevent illegal instantiation.
	 */
	protected Visitor() {
	}

	/**
	 * A map of parsers for the visit.
	 */
	private final Map<Class<?>, Parser> parsers = new HashMap<Class<?>, Parser>() {
		/**
		 * SUID
		 */
		private static final long serialVersionUID = -1233333524870450644L;
		{
			this.putMore(new BoolParser(), Boolean.class, Boolean.TYPE);
			this.putMore(new ByteParser(), Byte.class, Byte.TYPE);
			this.putMore(new CharParser(), Character.class, Character.TYPE);
			this.putMore(new DoubleParser(), Double.class, Double.TYPE);
			this.putMore(new FloatParser(), Float.class, Float.TYPE);
			this.putMore(new IntParser(), Integer.class, Integer.TYPE);
			this.putMore(new LongParser(), Long.class, Long.TYPE);
			this.putMore(new StringParser(), String.class);
		}

		/**
		 * Set parser for more than one type.
		 *
		 * @param parser
		 *            the parser
		 * @param clazzes
		 *            the classes
		 */
		private void putMore(Parser parser, Class<?>... clazzes) {
			for (Class<?> c : clazzes) {
				this.put(c, parser);
			}
		}
	};

	/**
	 * This method will be invoked before visiting by {@link #visit(Visitable)}.
	 *
	 * @param v
	 *            the visitable
	 * @return {@code true} if the source for KV-Mapping established,
	 *         {@code false} if failed
	 */
	protected abstract boolean createSource(Visitable v);

	/**
	 * This method will be invoked before visiting by {@link #visit(Class)}.
	 *
	 * @param v
	 *            the visitable
	 * @return {@code true} if the source for KV-Mapping established,
	 *         {@code false} if failed
	 */
	protected abstract boolean createSource(Class<? extends Visitable> v);

	/**
	 * Get value by key.
	 *
	 * @param key
	 *            the key ({@link Field#getName()})
	 * @return {@code null} if no value found, the value otherwise
	 */
	protected abstract String getValue(String key);

	/**
	 * Visit a visitable (only non-static).
	 *
	 * @param v
	 *            the visitable
	 */
	public final synchronized void visit(Visitable v) {
		if (!this.createSource(v)) {
			System.err.println("Cannot create source for " + v.getClass());
			return;
		}
		System.out.println("INFO: Visit object of class " + v.getClass().getSimpleName());
		for (Field field : v.getClass().getDeclaredFields()) {
			this.applyObject(v, field);
		}
		for (Method m : v.getClass().getDeclaredMethods()) {
			this.afterObject(v, m);
		}
	}

	/**
	 * Visit a visitable (only static).
	 *
	 * @param v
	 *            the visitable
	 */
	public final synchronized void visit(Class<? extends Visitable> v) {
		if (!this.createSource(v)) {
			System.err.println("Cannot create source for " + v);
			return;
		}
		System.out.println("INFO: Visit class " + v.getSimpleName());
		for (Field field : v.getDeclaredFields()) {
			this.applyStatic(field);
		}
		for (Method m : v.getDeclaredMethods()) {
			this.afterStatic(m);
		}
	}

	/**
	 * Get the specified parser for a field.
	 *
	 * @param field
	 *            the field
	 * @return the parser or {@code null} if none suitable found
	 * @throws SecurityException
	 *             reflect stuff
	 * @throws NoSuchMethodException
	 *             reflect stuff
	 * @throws InvocationTargetException
	 *             reflect stuff
	 * @throws IllegalArgumentException
	 *             reflect stuff
	 * @throws IllegalAccessException
	 *             reflect stuff
	 * @throws InstantiationException
	 *             reflect stuff
	 */
	private Parser getParser(Field field) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		// First the field specific.
		SetParser manual = field.getDeclaredAnnotation(SetParser.class);
		if (manual != null) {
			return manual.value().getDeclaredConstructor().newInstance();
		}
		// Then the class specific.
		ClassParser clazzParser = field.getType().getDeclaredAnnotation(ClassParser.class);
		if (clazzParser != null) {
			return clazzParser.value().getDeclaredConstructor().newInstance();
		}
		// Then the default.
		return this.parsers.get(field.getType());
	}

	/**
	 * Invoke method of a visitable (only static).
	 *
	 * @param m
	 *            the method
	 */
	private void afterStatic(Method m) {
		try {
			if (!Modifier.isStatic(m.getModifiers())) {
				return;
			}
			AfterVisit afterVisit = m.getAnnotation(AfterVisit.class);
			if (afterVisit == null) {
				return;
			}
			m.setAccessible(true);
			m.invoke(null);
		} catch (Exception e) {
			System.err.println("\tCannot invoke method: " + m.getName() + " because " + e.getMessage());
		}

	}

	/**
	 * Invoke method of a visitable (only non-static).
	 *
	 * @param v
	 *            the visitable
	 * @param m
	 *            the method
	 */
	private void afterObject(Visitable v, Method m) {
		try {
			if (Modifier.isStatic(m.getModifiers())) {
				return;
			}
			AfterVisit afterVisit = m.getDeclaredAnnotation(AfterVisit.class);
			if (afterVisit == null) {
				return;
			}
			m.setAccessible(true);
			m.invoke(v);
		} catch (Exception e) {
			System.err.println("\tCannot invoke method: " + m.getName() + " because " + e.getMessage());
		}

	}

	/**
	 * Apply a value to a field (only static).
	 *
	 * @param field
	 *            the field
	 */
	private void applyStatic(Field field) {
		if (field.getAnnotation(NoVisit.class) != null) {
			return;
		}
		String val = null;
		int mod = field.getModifiers();
		if (!Modifier.isStatic(mod) || Modifier.isFinal(mod) || (val = this.getValue(field.getName())) == null) {
			System.out.println("\tWarning: Field " + field.getName() + " is non-static or is final or has no value");
			return;
		}
		this.applyToField(null, field, val);
	}

	/**
	 * Apply a value to a field (only non-static).
	 *
	 * @param v
	 *            the visitable
	 * @param field
	 *            the field
	 *
	 */
	private void applyObject(Visitable v, Field field) {
		if (field.getAnnotation(NoVisit.class) != null) {
			return;
		}
		int mod = field.getModifiers();
		String val = null;
		if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || (val = this.getValue(field.getName())) == null) {
			System.out.println("\tWarning: Field " + field.getName() + " is static or is final or has no value");
			return;
		}
		this.applyToField(v, field, val);
	}

	/**
	 * Apply a value to a field.
	 *
	 * @param v
	 *            the visitable object or {@code null} to visit class
	 * @param field
	 *            the field
	 * @param val
	 *            the value to be set / parsed to the field
	 *
	 */
	private void applyToField(Visitable v, Field field, String val) {
		try {
			field.setAccessible(true);
			Parser parser = this.getParser(field);
			if (parser == null) {
				System.err.println("\tNo parser found for " + field.getName());
				return;
			}
			if (!parser.parse(v, field, val)) {
				System.err.println("\tSyntax-Error: Parser rejected content for " + field.getName() + " where content was " + val);
			}
		} catch (Exception e) {
			System.err.println("\tCannot apply to field: " + field.getName() + " because " + e.getMessage());
		}

	}

}
