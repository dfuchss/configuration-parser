package org.fuchss.configuration.annotations;

import org.fuchss.configuration.setters.ResourceBundleSetter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ResourceBundle;

/**
 * This annotation must be applied to a class for using a
 * {@link ResourceBundleSetter}.
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SetterInfo {
	/**
	 * Indicates whether you want a set the fields of the class.
	 *
	 * @return {@code true} (by default) if setting is allowed, {@code false}
	 * otherwise
	 */
	boolean set() default true;

	/**
	 * Get the path to the corresponding property file.
	 *
	 * @return the path to the property file
	 * @see ResourceBundle
	 */
	String res();
}
