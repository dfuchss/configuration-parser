package home.fox.visitors.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import home.fox.visitors.Visitable;
import home.fox.visitors.parser.Parser;

/**
 * This annotation has to be applied to Fields of a {@link Visitable} which
 * shall be visited by a specified {@link Parser}. This will override the
 * default and the {@link ClassParser}
 *
 * @author Dominik Fuchss
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SetParser {
	/**
	 * Get the parser-type.
	 *
	 * @return the parser type
	 */
	Class<? extends Parser> value();
}
