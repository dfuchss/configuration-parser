package home.fox.visitors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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
import home.fox.visitors.parser.MultiLevelParser;
import home.fox.visitors.parser.Parser;
import home.fox.visitors.parser.StringParser;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class supports the setting of Values and/or Attributes to Classes and
 * Objects which implements the {@link Visitable} interface.
 *
 * @author Dominik Fuchss
 * @see VisitInfo
 * @see AfterVisit
 *
 */
public abstract class Visitor {
	/**
	 * The logger of the Visitor class.
	 */
	public static final Logger LOGGER = Logger.getLogger(Visitor.class);
	static {
		Visitor.LOGGER.setLevel(Level.ERROR);
		BasicConfigurator.configure();
	}

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
	 * The parent of the visitor.
	 *
	 * @see MultiLevelParser
	 */
	protected final Visitor parent;

	/**
	 * Set necessary fields.
	 *
	 * @param parent
	 *            the parent visitor
	 */
	protected Visitor(Visitor parent) {
		this.parent = parent;
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
			this.put(null, new MultiLevelParser(Visitor.this));
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
	public abstract String getValue(String key);

	/**
	 * Visit a visitable (only non-static).
	 *
	 * @param v
	 *            the visitable
	 */
	public final synchronized void visit(Visitable v) {
		if (!this.createSource(v)) {
			Visitor.LOGGER.error("Cannot create source for " + v.getClass());
			return;
		}
		Visitor.LOGGER.info("Visit object of class " + v.getClass().getSimpleName());
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
			Visitor.LOGGER.error("Cannot create source for " + v);
			return;
		}
		Visitor.LOGGER.info("Visit class " + v.getSimpleName());
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
	 * @return the parser or {@link MultiLevelParser} if none suitable found
	 * @throws Exception
	 *             reflect stuff
	 */
	private Parser getParser(Field field) throws Exception {
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
		if (this.parsers.containsKey(field.getType())) {
			return this.parsers.get(field.getType());
		}
		// If none found use MultiLevelParser.
		Visitor.LOGGER.info("Using MultiLevelParser for " + field.getName());
		return this.parsers.get(null);
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
			Visitor.LOGGER.error("Cannot invoke method: " + m.getName() + " because " + e.getMessage());
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
			Visitor.LOGGER.error("Cannot invoke method: " + m.getName() + " because " + e.getMessage());
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
		String val = this.getValue(field.getName());
		int mod = field.getModifiers();
		if (!Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
			Visitor.LOGGER.warn("Field " + field.getName() + " is non-static or is final");
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
		String val = this.getValue(field.getName());
		int mod = field.getModifiers();
		if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
			Visitor.LOGGER.warn("Field " + field.getName() + " is static or is final");
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
			if (!parser.parse(v, field, val, this.getPath()) && val != null) {
				Visitor.LOGGER.warn("Syntax-Error: Parser rejected content for " + field.getName() + " where content was " + val);
			}
		} catch (Exception e) {
			Visitor.LOGGER.error("Cannot apply to field: " + field.getName() + " because " + e.getMessage());
		}

	}

	/**
	 * Get the current path.
	 *
	 * @return the recursive path.
	 */
	protected String[] getPath() {
		return new String[0];
	}

	/**
	 * Get the parent (recursive) visitor of this visitor.
	 *
	 * @return the most parent parser.
	 */
	public Visitor getParent() {
		return this;
	}

}
