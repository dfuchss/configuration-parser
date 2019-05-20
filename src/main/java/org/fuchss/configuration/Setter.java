package org.fuchss.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fuchss.configuration.annotations.AfterSetting;
import org.fuchss.configuration.annotations.ClassParser;
import org.fuchss.configuration.annotations.NoSet;
import org.fuchss.configuration.annotations.SetParser;
import org.fuchss.configuration.annotations.SetterInfo;
import org.fuchss.configuration.parser.ArrayParser;
import org.fuchss.configuration.parser.CharParser;
import org.fuchss.configuration.parser.MultiLevelParser;
import org.fuchss.configuration.parser.Parser;
import org.fuchss.configuration.setters.ResourceBundleSetter;

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
	public static final Logger LOGGER = LogManager.getLogger(Setter.class);

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
	private final Map<Class<?>, Parser> parsers = new HashMap<>() {
		/**
		 * SUID
		 */
		private static final long serialVersionUID = -1233333524870450644L;
		{
			this.putMore((in, path) -> Boolean.parseBoolean(in), Boolean.class, Boolean.TYPE);
			this.putMore((in, path) -> Byte.parseByte(in), Byte.class, Byte.TYPE);
			this.putMore(new CharParser(), Character.class, Character.TYPE);
			this.putMore((in, path) -> Double.parseDouble(in), Double.class, Double.TYPE);
			this.putMore((in, path) -> Float.parseFloat(in), Float.class, Float.TYPE);
			this.putMore((in, path) -> Integer.parseInt(in), Integer.class, Integer.TYPE);
			this.putMore((in, path) -> Long.parseLong(in), Long.class, Long.TYPE);
			this.putMore((in, path) -> in, String.class);
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
			Setter.LOGGER.info("Cannot create source for " + configurable); //$NON-NLS-1$
			return;
		}
		Setter.LOGGER.info("Setting attributes in class" + configurable.getSimpleName()); //$NON-NLS-1$
		Arrays.stream(configurable.getDeclaredFields()).forEach(this::applyStatic);
		Arrays.stream(configurable.getDeclaredMethods()).forEach(this::afterStatic);
	}

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
		return null;
	}

	private void afterStatic(Method m) {
		try {
			if (!Modifier.isStatic(m.getModifiers()) || m.getAnnotation(AfterSetting.class) == null) {
				return;
			}
			m.setAccessible(true);
			m.invoke(null);
		} catch (Exception e) {
			Setter.LOGGER.error("Cannot invoke method: " + m.getName() + " because " + e.getMessage());
		}

	}

	private void afterObject(Configurable configurable, Method m) {
		try {
			if (Modifier.isStatic(m.getModifiers()) || m.getDeclaredAnnotation(AfterSetting.class) == null) {
				return;
			}
			m.setAccessible(true);
			m.invoke(configurable);
		} catch (Exception e) {
			Setter.LOGGER.error("Cannot invoke method: " + m.getName() + " because " + e.getMessage());
		}

	}

	private void applyStatic(Field field) {
		if (field.getAnnotation(NoSet.class) != null) {
			return;
		}
		int mod = field.getModifiers();
		if (!Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
			Setter.LOGGER.info("Field " + field.getName() + " is non-static or is final");
			return;
		}
		this.applyToField(null, field, this.getValue(field.getName()));
	}

	private void applyObject(Configurable configurable, Field field) {
		if (field.getAnnotation(NoSet.class) != null) {
			return;
		}
		int mod = field.getModifiers();
		if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
			Setter.LOGGER.info("Field " + field.getName() + " is static or is final");
			return;
		}
		this.applyToField(configurable, field, this.getValue(field.getName()));
	}

	private void applyToField(Configurable configurable, Field field, String val) {
		try {
			field.setAccessible(true);
			Parser parser = this.getParser(field);

			Object parsed = null;
			if (parser != null) {
				parsed = parser.parse(val, this.getPath());
			} else {
				this.parseField(configurable, field, val);
				return;
			}
			if (parsed == null && val != null) {
				Setter.LOGGER.warn("Syntax-Error: Parser rejected content for " + field.getName() + " where content was " + val);
				return;
			}
			if (parsed != null) {
				field.set(configurable, parsed);
			}
		} catch (Exception e) {
			Setter.LOGGER.error("Cannot apply to field: " + field.getName() + " because " + e.getMessage());
		}

	}

	private void parseField(Configurable configurable, Field field, String val) throws Exception {
		if (field.getType().isArray()) {
			this.parseArray(configurable, field, val);
		} else {
			this.parseMultiLevel(configurable, field, val);
		}
	}

	private void parseMultiLevel(Configurable configurable, Field field, String val) throws Exception {
		MultiLevelParser parser = new MultiLevelParser(this);
		parser.parse(configurable, field, val, this.getPath());
	}

	private void parseArray(Configurable configurable, Field field, String val) throws Exception {
		ArrayParser parser = new ArrayParser();
		Parser contentParser = this.parsers.get(field.getType().getComponentType());
		if (contentParser == null) {
			Setter.LOGGER.error("No parser defined for " + field.getType().getComponentType());
			return;
		}
		parser.parse(configurable, field, val, this.getPath(), contentParser);

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
			throw new Error("MAX_DEPTH reached. Abort");
		}
		if (this.parent == null || this.parent == this) {
			return this;
		}
		return this.parent.getParent(depth + 1);

	}

}
