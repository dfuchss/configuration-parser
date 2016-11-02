package home.fox.visitors.dummy;

import home.fox.visitors.Visitable;
import home.fox.visitors.annotations.VisitInfo;
import home.fox.visitors.toParse.Tuple;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class will be used to test several functionality of the
 * {@link ResourceBundleVisitor} and {@link VisitInfo}.
 *
 * @author Dominik Fuchss
 *
 */
@VisitInfo(res = "conf/dummyForResourceBundle", visit = true)
public class DummyForResourceBundle implements Visitable {
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
}
