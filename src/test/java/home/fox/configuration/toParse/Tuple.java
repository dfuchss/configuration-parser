package home.fox.configuration.toParse;

import home.fox.configuration.Configurable;
import home.fox.configuration.parser.MultiLevelParser;

/**
 * This class contains two strings, which shall be parsed via
 * {@link MultiLevelParser}.
 *
 * @author Dominik Fuchss
 *
 */
public final class Tuple implements Configurable {
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
