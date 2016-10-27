package home.fox.visitors.visitors;

import java.util.ResourceBundle;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;
import home.fox.visitors.annotations.VisitInfo;

/**
 * This class realizes the default {@link Visitor} which will use a
 * {@link ResourceBundle} as configured by {@link VisitInfo}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ResourceBundleVisitor extends Visitor {
	/**
	 * The bundle.
	 */
	private ResourceBundle bundle;

	@Override
	protected boolean createSource(Visitable v) {
		return this.createSource(v.getClass());
	}

	@Override
	protected boolean createSource(Class<? extends Visitable> v) {
		VisitInfo info = v.getAnnotation(VisitInfo.class);
		if (info == null || !info.visit()) {
			Visitor.LOGGER.info("No info defined or disabled for Class " + v.getSimpleName());
			return false;
		}
		this.bundle = ResourceBundle.getBundle(info.res());
		return true;
	}

	@Override
	protected String getValue(String key) {
		if (this.bundle == null) {
			return null;
		}
		return this.bundle.containsKey(key) ? this.bundle.getString(key) : null;
	}

}
