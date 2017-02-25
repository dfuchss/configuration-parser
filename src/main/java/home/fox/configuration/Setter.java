package home.fox.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import home.fox.configuration.annotations.AfterSetting;
import home.fox.configuration.annotations.ClassParser;
import home.fox.configuration.annotations.NoSet;
import home.fox.configuration.annotations.SetParser;
import home.fox.configuration.annotations.SetterInfo;
import home.fox.configuration.parser.BoolParser;
import home.fox.configuration.parser.ByteParser;
import home.fox.configuration.parser.CharParser;
import home.fox.configuration.parser.DoubleParser;
import home.fox.configuration.parser.FloatParser;
import home.fox.configuration.parser.IntParser;
import home.fox.configuration.parser.LongParser;
import home.fox.configuration.parser.MultiLevelParser;
import home.fox.configuration.parser.Parser;
import home.fox.configuration.parser.StringParser;
import home.fox.configuration.setters.ResourceBundleSetter;

/**
 * This class supports the setting of Values and/or Attributes to classes and
 * Objects which implements the {@link Configurable} interface.
 *
 * @author Dominik Fuchss
 * @see SetterInfo
 * @see AfterSetting
 * @see ResourceBundleSetter
 *
 */
public abstract class Setter {
	/**
	 * The logger of the this class.
	 */
	public static final Logger LOGGER = Logger.getLogger(Setter.class);

	static {
		Setter.LOGGER.setLevel(Level.ERROR);
		BasicConfigurator.configure();
	}

	/**
	 * Maximum recursion-depth of {@link #getParent()}.
	 */
	private static final int MAX_DEPTH = 10;

	/**
	 * The parent of the setter.
	 *
	 * @see MultiLevelParser
	 */
	protected final Setter parent;

	/**
	 * Set necessary fields.
	 *
	 * @param parent
	 *            the parent setter
	 */
	protected Setter(Setter parent) {
		this.parent = parent;
	}

	/**
	 * A map of parsers for the setter.
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
			this.put(null, new MultiLevelParser(Setter.this));
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
	 * This method will be invoked before setting attributes by
	 * {@link #setAttributes(Configurable)}.
	 *
	 * @param configurable
	 *            the configurable
	 * @return {@code true} if the source for KV-Mapping established,
	 *         {@code false} if failed
	 */
	protected abstract boolean createSource(Configurable configurable);

	/**
	 * This method will be invoked before setting attributes by
	 * {@link #setAttributes(Class)}.
	 *
	 * @param configurable
	 *            the configurable
	 * @return {@code true} if the source for KV-Mapping established,
	 *         {@code false} if failed
	 */
	protected abstract boolean createSource(Class<? extends Configurable> configurable);

	/**
	 * Get value by key.
	 *
	 * @param key
	 *            the key ({@link Field#getName()})
	 * @return {@code null} if no value found, the value otherwise
	 */
	public abstract String getValue(String key);

	/**
	 * Set attributes of a configurable (only non-static).
	 *
	 * @param configurable
	 *            the configurable
	 */
	public final synchronized void setAttributes(Configurable configurable) {
		if (!this.createSource(configurable)) {
			Setter.LOGGER.info("Cannot create source for " + configurable.getClass());
			return;
		}
		Setter.LOGGER.info("Setting attributes in object of class " + configurable.getClass().getSimpleName());
		Arrays.stream(configurable.getClass().getDeclaredFields()).forEach(field -> this.applyObject(configurable, field));
		Arrays.stream(configurable.getClass().getDeclaredMethods()).forEach(method -> this.afterObject(configurable, method));
	}

	/**
	 * Set attributes of a configurable (only static).
	 *
	 * @param configurable
	 *            the configurable
	 */
	public final synchronized void setAttributes(Class<? extends Configurable> configurable) {
		if (!this.createSource(configurable)) {
			Setter.LOGGER.info("Cannot create source for " + configurable);
			return;
		}
		Setter.LOGGER.info("Setting attributes in class " + configurable.getSimpleName());
		Arrays.stream(configurable.getDeclaredFields()).forEach(this::applyStatic);
		Arrays.stream(configurable.getDeclaredMethods()).forEach(this::afterStatic);
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
		Setter.LOGGER.info("Using MultiLevelParser for " + field.getName());
		return this.parsers.get(null);
	}

	/**
	 * Invoke method of a configurable (only static).
	 *
	 * @param m
	 *            the method
	 */
	private void afterStatic(Method m) {
		try {
			if (!Modifier.isStatic(m.getModifiers())) {
				return;
			}
			AfterSetting afterSetting = m.getAnnotation(AfterSetting.class);
			if (afterSetting == null) {
				return;
			}
			m.setAccessible(true);
			m.invoke(null);
		} catch (Exception e) {
			Setter.LOGGER.error("Cannot invoke method: " + m.getName() + " because " + e.getMessage());
		}

	}

	/**
	 * Invoke method of a configurable (only non-static).
	 *
	 * @param configurable
	 *            the configurable
	 * @param m
	 *            the method
	 */
	private void afterObject(Configurable configurable, Method m) {
		try {
			if (Modifier.isStatic(m.getModifiers())) {
				return;
			}
			AfterSetting afterSetting = m.getDeclaredAnnotation(AfterSetting.class);
			if (afterSetting == null) {
				return;
			}
			m.setAccessible(true);
			m.invoke(configurable);
		} catch (Exception e) {
			Setter.LOGGER.error("Cannot invoke method: " + m.getName() + " because " + e.getMessage());
		}

	}

	/**
	 * Apply a value to a field (only static).
	 *
	 * @param field
	 *            the field
	 */
	private void applyStatic(Field field) {
		if (field.getAnnotation(NoSet.class) != null) {
			return;
		}
		String val = this.getValue(field.getName());
		int mod = field.getModifiers();
		if (!Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
			Setter.LOGGER.info("Field " + field.getName() + " is non-static or is final");
			return;
		}
		this.applyToField(null, field, val);
	}

	/**
	 * Apply a value to a field (only non-static).
	 *
	 * @param configurable
	 *            the configurable
	 * @param field
	 *            the field
	 *
	 */
	private void applyObject(Configurable configurable, Field field) {
		if (field.getAnnotation(NoSet.class) != null) {
			return;
		}
		String val = this.getValue(field.getName());
		int mod = field.getModifiers();
		if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
			Setter.LOGGER.info("Field " + field.getName() + " is static or is final");
			return;
		}
		this.applyToField(configurable, field, val);
	}

	/**
	 * Apply a value to a field.
	 *
	 * @param configurable
	 *            the configurable object or {@code null} to set attributes of
	 *            classes
	 * @param field
	 *            the field
	 * @param val
	 *            the value to be set / parsed to the field
	 *
	 */
	private void applyToField(Configurable configurable, Field field, String val) {
		try {
			field.setAccessible(true);
			Parser parser = this.getParser(field);
			if (!parser.parse(configurable, field, val, this.getPath()) && val != null) {
				Setter.LOGGER.warn("Syntax-Error: Parser rejected content for " + field.getName() + " where content was " + val);
			}
		} catch (Exception e) {
			Setter.LOGGER.error("Cannot apply to field: " + field.getName() + " because " + e.getMessage());
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
	 * Get the parent (recursive) setter of this setter.
	 *
	 * @return the most parent setter.
	 */
	public final Setter getParent() {
		return this.getParent(0);
	}

	/**
	 * Same as {@link #getParent()}.
	 *
	 * @param depth
	 *            the recursion depth
	 * @return the most parent setter.
	 * @throws Error
	 *             if {@link #MAX_DEPTH} has been reached
	 */
	private Setter getParent(int depth) {
		if (depth > Setter.MAX_DEPTH) {
			throw new Error("MAX_DEPTH has been reached. Abort");
		}
		if (this.parent == null || this.parent == this) {
			return this;
		}
		return this.parent.getParent(depth + 1);

	}

}
