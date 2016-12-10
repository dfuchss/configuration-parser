package home.fox.visitors.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ResourceBundle;

import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This annotation must be applied to a Class which shall be visited by a
 * {@link ResourceBundleVisitor}.
 *
 * @author Dominik Fuchss
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VisitInfo {
	/**
	 * Indicates whether you want a visit.
	 *
	 * @return {@code true} (by default) if visit is allowed, {@code false}
	 *         otherwise
	 */
	public boolean visit() default true;

	/**
	 * Get the path to the corresponding property file.
	 *
	 * @return the path to the property file
	 * @see ResourceBundle
	 */
	public String res();
}
