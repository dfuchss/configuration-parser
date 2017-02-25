package home.fox.configuration.dummy;

import home.fox.configuration.Configurable;
import home.fox.configuration.annotations.NoSet;
import home.fox.configuration.annotations.SetterInfo;
import home.fox.configuration.setters.ResourceBundleSetter;
import home.fox.configuration.toParse.Tuple;

/**
 * This class will be used to test several functionality of the
 * {@link ResourceBundleSetter} and {@link SetterInfo}.
 *
 * @author Dominik Fuchss
 *
 */
@SetterInfo(res = "conf/dummyForResourceBundle", set = true)
public class DummyForResourceBundle implements Configurable {
	/**
	 * Shall be {@code 1}.
	 */
	public static long LongValueOne;
	/**
	 * Shall be {@code 0}.
	 */
	public static int IntValueZero = 100;
	/**
	 * Shall be {@code 0.5F}.
	 */
	public static float FloatValueHalf;
	/**
	 * Shall be {@code "HelloWorld"}.
	 */
	public static String HelloWorld;

	/**
	 * Shall be {@code 'D'}.
	 */
	public static char CharD;

	/**
	 * Shall be {@code true}.
	 */
	public static boolean BoolTrue;

	/**
	 * Shall be {@code 127}.
	 */
	public static byte Byte127;

	/**
	 * Shall be {@code 3.141593}.
	 */
	public static double DoublePi;
	/**
	 * Shall be (Hello,World) Tuple.
	 */
	public static Tuple Tuple;

	/**
	 * Shall be {@code null}.
	 */
	@NoSet
	public static String HelloWorld2;
}
