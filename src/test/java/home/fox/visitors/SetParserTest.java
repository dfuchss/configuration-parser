package home.fox.visitors;

import java.util.logging.Level;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import home.fox.visitors.annotations.SetParser;
import home.fox.visitors.dummy.DummyForSetParser;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class tests {@link SetParser} functionality.
 *
 * @author Dominik Fuchss
 *
 */
public class SetParserTest {
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
		Assert.assertNull(DummyForSetParser.array);
		SetParserTest.RESOURCE_BUNDLE_VISITOR.visit(DummyForSetParser.class);
		Assert.assertArrayEquals(new String[] { "1", "2" }, DummyForSetParser.array);
	}

	/**
	 * Test for object.
	 */
	@Test
	public void testObject() {
		DummyForSetParser v = new DummyForSetParser();
		Assert.assertNull(v.objArray);
		SetParserTest.RESOURCE_BUNDLE_VISITOR.visit(v);
		Assert.assertArrayEquals(new String[] { "2", "3" }, v.objArray);
	}

}
