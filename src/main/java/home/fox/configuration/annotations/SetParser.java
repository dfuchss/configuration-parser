package home.fox.configuration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import home.fox.configuration.Configurable;
import home.fox.configuration.parser.Parser;

/**
 * This annotation has to be applied to Fields of a {@link Configurable} which
 * shall be set by a specified {@link Parser}. This will override the default
 * and the {@link ClassParser}
 *
 * @author Dominik Fuchss
 *
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
