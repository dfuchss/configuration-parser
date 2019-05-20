package org.fuchss.configuration.setters;

import java.util.Locale;
import java.util.ResourceBundle;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.annotations.SetterInfo;

/**
 * This class realizes the default {@link Setter} which will use a
 * {@link ResourceBundle} as configured by {@link SetterInfo}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ResourceBundleSetter extends Setter {
	/**
	 * The class loader for the resource bundle.
	 */
	private final ClassLoader cl;

	/**
	 * Create a new Setter.
	 */
	public ResourceBundleSetter() {
		this(null);
	}

	/**
	 * Create a new Setter.
	 *
	 * @param cl
	 *            the class loader for the {@link ResourceBundle}.
	 */
	public ResourceBundleSetter(ClassLoader cl) {
		super(null);
		this.cl = cl;
	}

	/**
	 * The bundle.
	 */
	private ResourceBundle bundle;

	@Override
	protected boolean createSource(Configurable v) {
		return this.createSource(v.getClass());
	}

	@Override
	protected boolean createSource(Class<? extends Configurable> v) {
		SetterInfo info = v.getAnnotation(SetterInfo.class);
		if (info == null || !info.set()) {
			Setter.LOGGER.info("No info defined or disabled for class " + v.getSimpleName());
			return false;
		}
		if (this.cl == null) {
			this.bundle = ResourceBundle.getBundle(info.res());
		} else {
			this.bundle = ResourceBundle.getBundle(info.res(), Locale.getDefault(), this.cl);
		}
		return true;
	}

	@Override
	public String getValue(String key) {
		if (this.bundle == null) {
			return null;
		}
		return this.bundle.containsKey(key) ? this.bundle.getString(key) : null;
	}

}
