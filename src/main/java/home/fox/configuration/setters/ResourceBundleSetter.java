package home.fox.configuration.setters;

import java.util.ResourceBundle;

import home.fox.configuration.Configurable;
import home.fox.configuration.Messages;
import home.fox.configuration.Setter;
import home.fox.configuration.annotations.SetterInfo;

/**
 * This class realizes the default {@link Setter} which will use a
 * {@link ResourceBundle} as configured by {@link SetterInfo}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ResourceBundleSetter extends Setter {
	/**
	 * Create a new Setter.
	 */
	public ResourceBundleSetter() {
		super(null);
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
			Setter.LOGGER.info(Messages.getString("ResourceBundleSetter.0") + v.getSimpleName()); //$NON-NLS-1$
			return false;
		}
		this.bundle = ResourceBundle.getBundle(info.res());
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
