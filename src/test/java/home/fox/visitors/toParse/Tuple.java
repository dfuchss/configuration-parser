package home.fox.visitors.toParse;

import home.fox.visitors.Visitable;
import home.fox.visitors.parser.MultiLevelParser;

/**
 * This class contains two strings, which shall be parsed via
 * {@link MultiLevelParser}.
 *
 * @author Dominik Fuchss
 *
 */
public final class Tuple implements Visitable {
	/**
	 * First string.
	 */
	public String stringA;
	/**
	 * Second string.
	 */
	public String stringB;
	/**
	 * An inner Tuple (one, null).
	 */
	public Tuple innerTuple;
}
