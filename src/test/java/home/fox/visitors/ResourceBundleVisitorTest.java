package home.fox.visitors;

import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import home.fox.visitors.annotations.NoVisit;
import home.fox.visitors.dummy.DummyForResourceBundle;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class shall test functionality of the {@link ResourceBundleVisitor}.
 *
 * @author Dominik Fuchss
 *
 */
public class ResourceBundleVisitorTest {
	/**
	 * An instance of {@link ResourceBundleVisitor}.
	 */
	private static final Visitor RESOURCE_BUNDLE_VISITOR = Visitor.getNewVisitor();

	/**
	 * Activate the logger of {@link Visitor}.
	 */
	@BeforeClass
	public static void activateLogger() {
		Visitor.LOGGER.setLevel(Level.ALL);
	}

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

	/**
	 * Test whether {@link DummyForResourceBundle#CharD} is correctly set.
	 */
	@Test
	public void testCharDValue() {
		Assert.assertEquals('D', DummyForResourceBundle.CharD);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#BoolTrue} is correctly set.
	 */
	@Test
	public void testBoolTrueValue() {
		Assert.assertEquals(true, DummyForResourceBundle.BoolTrue);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#Byte127} is correctly set.
	 */
	@Test
	public void testByte127Value() {
		Assert.assertEquals((byte) 127, DummyForResourceBundle.Byte127);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#DoublePi} is correctly set.
	 */
	@Test
	public void testDoublePiValue() {
		Assert.assertEquals(3.141593, DummyForResourceBundle.DoublePi, 1E-8);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#Tuple} is correctly set.
	 */
	@Test
	public void testTuple() {
		Assert.assertNotNull(DummyForResourceBundle.Tuple);
		Assert.assertEquals("Hello", DummyForResourceBundle.Tuple.stringA);
		Assert.assertEquals("World", DummyForResourceBundle.Tuple.stringB);
		// Inner tuple.
		Assert.assertNotNull(DummyForResourceBundle.Tuple.innerTuple);
		Assert.assertEquals("one", DummyForResourceBundle.Tuple.innerTuple.stringA);
		Assert.assertNull(DummyForResourceBundle.Tuple.innerTuple.stringB);

	}

	/**
	 * Test whether {@link DummyForResourceBundle#HelloWorld2} is correctly set
	 * (-> null) -> {@link NoVisit}.
	 */
	@Test
	public void testHelloWorld2() {
		Assert.assertNull(DummyForResourceBundle.HelloWorld2);
	}

}
