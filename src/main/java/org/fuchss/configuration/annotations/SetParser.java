package org.fuchss.configuration.annotations;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.parser.Parser;

import java.lang.annotation.*;

/**
 * This annotation has to be applied to Fields of a {@link Configurable} which
 * shall be set by a specified {@link Parser}. This will override the default
 * and the {@link ClassParser}
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface SetParser {
	/**
	 * Get the parser-type.
	 *
	 * @return the parser type
	 */
	Class<? extends Parser> value();
}
