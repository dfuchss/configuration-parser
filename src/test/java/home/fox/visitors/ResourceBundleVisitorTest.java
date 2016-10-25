package home.fox.visitors;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import home.fox.visitors.Visitor;
import home.fox.visitors.dummy.DummyForResourceBundle;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class shall test functionality of the {@link ResourceBundleVisitor}.
 *
 * @author Dominik Fuchß
 *
 */
public class ResourceBundleVisitorTest {
	/**
	 * An instance of {@link ResourceBundleVisitor}.
	 */
	private static final Visitor RESOURCE_BUNDLE_VISITOR = new ResourceBundleVisitor();

	/**
	 * Visit the {@link DummyForResourceBundle}.
	 */
	@BeforeClass
	public static void setUp() {
		ResourceBundleVisitorTest.RESOURCE_BUNDLE_VISITOR.visit(DummyForResourceBundle.class);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#LongValueOne} is correctly
	 * set.
	 */
	@Test
	public void testLongOneValue() {
		Assert.assertEquals(1, DummyForResourceBundle.LongValueOne);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#FloatValueHalf} is correctly
	 * set.
	 */
	@Test
	public void testFloatHalfValue() {
		Assert.assertEquals(0.5F, DummyForResourceBundle.FloatValueHalf, 1E-8);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#HelloWorld} is correctly set.
	 */
	@Test
	public void testHelloWorldValue() {
		Assert.assertEquals("HelloWorld", DummyForResourceBundle.HelloWorld);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#IntValueZero} is correctly
	 * set.
	 */
	@Test
	public void testIntZeroValue() {
		Assert.assertEquals(0, DummyForResourceBundle.IntValueZero);
	}
}
