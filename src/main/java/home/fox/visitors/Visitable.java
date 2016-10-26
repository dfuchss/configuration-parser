package home.fox.visitors;

import home.fox.visitors.annotations.AfterVisit;
import home.fox.visitors.annotations.NoVisit;
import home.fox.visitors.annotations.VisitInfo;
import home.fox.visitors.visitors.MapVisitor;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This interface has to be implemented by classes which shall be visited by a
 * {@link Visitor}.
 *
 * @author Dominik Fuchss
 * @see VisitInfo
 * @see AfterVisit
 * @see NoVisit
 * @see ResourceBundleVisitor
 * @see MapVisitor
 *
 */
public interface Visitable {
}
