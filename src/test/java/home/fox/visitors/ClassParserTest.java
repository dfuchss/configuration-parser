package home.fox.visitors;

import org.junit.Assert;
import org.junit.Test;

import home.fox.visitors.annotations.SetParser;
import home.fox.visitors.dummy.DummyForClassParser;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class tests {@link SetParser} functionality.
 *
 * @author Dominik Fuchß
 *
 */
public class ClassParserTest {
	/**
	 * An instance of {@link ResourceBundleVisitor}.
	 */
	private static final Visitor RESOURCE_BUNDLE_VISITOR = new ResourceBundleVisitor();

	/**
	 * Test for class.
	 */
	@Test
	public void testStatic() {
		Assert.assertNull(DummyForClassParser.classStringB);
		ClassParserTest.RESOURCE_BUNDLE_VISITOR.visit(DummyForClassParser.class);
		Assert.assertEquals("Earth", DummyForClassParser.classStringB.string);
	}

	/**
	 * Test for object.
	 */
	@Test
	public void testObject() {
		DummyForClassParser v = new DummyForClassParser();
		Assert.assertNull(v.objStringB);
		ClassParserTest.RESOURCE_BUNDLE_VISITOR.visit(v);
		Assert.assertEquals("Hello", v.objStringB.string);
	}

}
