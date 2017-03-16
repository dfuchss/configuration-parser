package org.fuchss.configuration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Setter;

/**
 * This annotation has to be applied to Fields of a {@link Configurable} which
 * shall not be set by a {@link Setter}.
 *
 * @author Dominik Fuchss
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface NoSet {

}
