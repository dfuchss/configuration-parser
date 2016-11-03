package home.fox.visitors.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;

/**
 * This annotation has to be applied to Methods which shall be executed after a
 * visit of a {@link Visitor} to a {@link Visitable}.
 *
 * @author Dominik Fuchss
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AfterVisit {

}
