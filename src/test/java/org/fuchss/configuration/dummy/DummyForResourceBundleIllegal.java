package org.fuchss.configuration.dummy;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.annotations.NoSet;
import org.fuchss.configuration.annotations.SetterInfo;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.fuchss.configuration.toParse.Tuple;

/**
 * This class will be used to test several functionality (error handling) of the
 * {@link ResourceBundleSetter} and {@link SetterInfo}.
 *
 * @author Dominik Fuchss
 *
 */
@SetterInfo(res = "conf/dummyForResourceBundleIllegal", set = true)
public class DummyForResourceBundleIllegal implements Configurable {
	/**
	 * Shall be {@code 1}.
	 */
	public static long LongValueOne;
	/**
	 * Shall be {@code 0}.
	 */
	public static int IntValueZero;
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
