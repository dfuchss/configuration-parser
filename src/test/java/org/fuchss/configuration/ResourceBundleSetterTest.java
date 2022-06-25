package org.fuchss.configuration;

import org.fuchss.configuration.annotations.NoSet;
import org.fuchss.configuration.dummy.DummyForResourceBundle;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This class shall test functionality of the {@link ResourceBundleSetter}.
 *
 * @author Dominik Fuchss
 */
public class ResourceBundleSetterTest {
	/**
	 * An instance of {@link ResourceBundleSetter}.
	 */
	private static final Setter RESOURCE_BUNDLE_SETTER = new ResourceBundleSetter();

	/**
	 * Visit the {@link DummyForResourceBundle}.
	 */
	@BeforeAll
	public static void setUp() {
		ResourceBundleSetterTest.RESOURCE_BUNDLE_SETTER.setAttributes(DummyForResourceBundle.class);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#LongValueOne} is correctly
	 * set.
	 */
	@Test
	void testLongOneValue() {
		Assertions.assertEquals(1, DummyForResourceBundle.LongValueOne);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#FloatValueHalf} is correctly
	 * set.
	 */
	@Test
	void testFloatHalfValue() {
		Assertions.assertEquals(0.5F, DummyForResourceBundle.FloatValueHalf, 1E-8);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#HelloWorld} is correctly set.
	 */
	@Test
	void testHelloWorldValue() {
		Assertions.assertEquals("HelloWorld", DummyForResourceBundle.HelloWorld);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#IntValueZero} is correctly
	 * set.
	 */
	@Test
	void testIntZeroValue() {
		Assertions.assertEquals(0, DummyForResourceBundle.IntValueZero);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#CharD} is correctly set.
	 */
	@Test
	void testCharDValue() {
		Assertions.assertEquals('D', DummyForResourceBundle.CharD);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#BoolTrue} is correctly set.
	 */
	@Test
	void testBoolTrueValue() {
		Assertions.assertTrue(DummyForResourceBundle.BoolTrue);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#Byte127} is correctly set.
	 */
	@Test
	void testByte127Value() {
		Assertions.assertEquals((byte) 127, DummyForResourceBundle.Byte127);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#DoublePi} is correctly set.
	 */
	@Test
	void testDoublePiValue() {
		Assertions.assertEquals(3.141593, DummyForResourceBundle.DoublePi, 1E-8);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#Tuple} is correctly set.
	 */
	@Test
	void testTuple() {
		Assertions.assertNotNull(DummyForResourceBundle.Tuple);
		Assertions.assertEquals("Hello", DummyForResourceBundle.Tuple.stringA);
		Assertions.assertEquals("World", DummyForResourceBundle.Tuple.stringB);
		// Inner tuple.
		Assertions.assertNotNull(DummyForResourceBundle.Tuple.innerTuple);
		Assertions.assertEquals("one", DummyForResourceBundle.Tuple.innerTuple.stringA);
		Assertions.assertNull(DummyForResourceBundle.Tuple.innerTuple.stringB);
	}

	/**
	 * Test whether {@link DummyForResourceBundle#HelloWorld2} is correctly set
	 * (-> null) -> {@link NoSet}.
	 */
	@Test
	void testHelloWorld2() {
		Assertions.assertNull(DummyForResourceBundle.HelloWorld2);
	}

}
