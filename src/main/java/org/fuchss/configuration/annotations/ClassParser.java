package org.fuchss.configuration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.parser.Parser;

/**
 * This annotation has to be applied to a {@link Configurable} which shall be
 * set by using a specified {@link Parser}. This will override the default
 * parser.
 *
 * @author Dominik Fuchss
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClassParser {
	/**
	 * Get the parser-type.
	 *
	 * @return the parser type
	 */
	Class<? extends Parser> value();
}
