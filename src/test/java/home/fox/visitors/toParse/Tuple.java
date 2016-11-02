package home.fox.visitors.toParse;

import home.fox.visitors.Visitable;
import home.fox.visitors.annotations.ClassParser;
import home.fox.visitors.parser.TwoLevelParser;

/**
 * This class contains two strings, which shall be parsed via
 * {@link TwoLevelParser}.
 *
 * @author Dominik Fuchss
 *
 */
@ClassParser(TwoLevelParser.class)
public final class Tuple implements Visitable {
	/**
	 * First string.
	 */
	public String stringA;
	/**
	 * Second string.
	 */
	public String stringB;
}
