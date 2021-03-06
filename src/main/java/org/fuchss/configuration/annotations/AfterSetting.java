package org.fuchss.configuration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Setter;

/**
 * This annotation has to be applied to Methods which shall be executed after a
 * {@link Setter} has set the values of an Object or class to a
 * {@link Configurable}.
 *
 * @author Dominik Fuchss
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AfterSetting {

}
