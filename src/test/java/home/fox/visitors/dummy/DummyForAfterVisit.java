package home.fox.visitors.dummy;

import home.fox.visitors.Visitable;
import home.fox.visitors.annotations.AfterVisit;
import home.fox.visitors.annotations.NoVisit;
import home.fox.visitors.annotations.VisitInfo;

/**
 * This class will be used to test {@link AfterVisit}.
 *
 * @author Dominik Fuchß
 *
 */
@VisitInfo(res = "conf/dummyForResourceBundle", visit = true)
public class DummyForAfterVisit implements Visitable {
	/**
	 * Indicates whether {@link #afterStatic()} was invoked.
	 */
	@NoVisit
	public static boolean afterStaticVisited = false;
	/**
	 * Indicates whether {@link #afterObject()} was invoked.
	 */
	@NoVisit
	public boolean afterObjectVisited = false;

	/**
	 * Set {@link #afterStaticVisited}.
	 */
	@AfterVisit
	public static void afterStatic() {
		DummyForAfterVisit.afterStaticVisited = true;
	}

	/**
	 * Set {@link #afterObjectVisited}.
	 */
	@AfterVisit
	public void afterObject() {
		this.afterObjectVisited = true;
	}
}
