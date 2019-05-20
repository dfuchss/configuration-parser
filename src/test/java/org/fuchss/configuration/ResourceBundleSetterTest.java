package org.fuchss.configuration;

import org.fuchss.configuration.annotations.NoSet;
import org.fuchss.configuration.dummy.DummyForResourceBundle;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class shall test functionality of the {@link ResourceBundleSetter}.
 *
 * @author Dominik Fuchss
 *
 */
public class ResourceBundleSetterTest {
	/**
	 * An instance of {@link ResourceBundleSetter}.
	 */
	private static final Setter RESOURCE_BUNDLE_SETTER = new ResourceBundleSetter();

	/**
	 * Visit the {@link DummyForResourceBundle}.
	 */
	@BeforeClass
	public static void setUp() {
		ResourceBundleSetterTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForResourceBundle.class);
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
	 * (-> null) -> {@link NoSet}.
	 */
	@Test
	public void testHelloWorld2() {
		Assert.assertNull(DummyForResourceBundle.HelloWorld2);
	}

}
