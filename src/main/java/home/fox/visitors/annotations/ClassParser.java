package home.fox.visitors.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import home.fox.visitors.Visitable;
import home.fox.visitors.parser.Parser;

/**
 * This annotation has to be applied to Fields of a {@link Visitable} which
 * shall be visited by a specified {@link Parser}. This will override the
 * default parser.
 *
 * @author Dominik Fuchss
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassParser {
	/**
	 * Get the parser-type.
	 *
	 * @return the parser type
	 */
	Class<? extends Parser> value();
}
