package org.fuchss.configuration.annotations;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Setter;

import java.lang.annotation.*;

/**
 * This annotation has to be applied to Methods which shall be executed after a
 * {@link Setter} has set the values of an Object or class to a
 * {@link Configurable}.
 *
 * @author Dominik Fuchss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AfterSetting {

}
