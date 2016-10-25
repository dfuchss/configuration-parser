package home.fox.visitors.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;

/**
 * This annotation has to be applied to Fields of a {@link Visitable} which
 * shall not be visited by a {@link Visitor}.
 *
 * @author Dominik Fuchß
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NoVisit {

}
