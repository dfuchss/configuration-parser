package home.fox.visitors;

import java.util.logging.Level;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import home.fox.visitors.annotations.AfterVisit;
import home.fox.visitors.dummy.DummyForAfterVisit;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class tests {@link AfterVisit} functionality.
 *
 * @author Dominik Fuchss
 *
 */
public class AfterVisitTest {
	/**
	 * An instance of {@link ResourceBundleVisitor}.
	 */
	private static final Visitor RESOURCE_BUNDLE_VISITOR = new ResourceBundleVisitor();

	/**
	 * Activate the logger of {@link Visitor}.
	 */
	@BeforeClass
	public static void activateLogger() {
		Visitor.LOGGER.setLevel(Level.ALL);
	}

	/**
	 * Test for class.
	 */
	@Test
	public void testStatic() {
		Assert.assertFalse(DummyForAfterVisit.afterStaticVisited);
		AfterVisitTest.RESOURCE_BUNDLE_VISITOR.visit(DummyForAfterVisit.class);
		Assert.assertTrue(DummyForAfterVisit.afterStaticVisited);
	}

	/**
	 * Test for object.
	 */
	@Test
	public void testObject() {
		DummyForAfterVisit v = new DummyForAfterVisit();
		Assert.assertFalse(v.afterObjectVisited);
		AfterVisitTest.RESOURCE_BUNDLE_VISITOR.visit(v);
		Assert.assertTrue(v.afterObjectVisited);
	}

}
