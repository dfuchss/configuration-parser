package org.fuchss.configuration.annotations;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Setter;

import java.lang.annotation.*;

/**
 * This annotation has to be applied to Fields of a {@link Configurable} which
 * shall not be set by a {@link Setter}.
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface NoSet {

}
