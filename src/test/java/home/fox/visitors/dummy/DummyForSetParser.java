package home.fox.visitors.dummy;

import home.fox.visitors.Visitable;
import home.fox.visitors.annotations.SetParser;
import home.fox.visitors.annotations.VisitInfo;
import home.fox.visitors.parser.AParser;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class will be used to test several functionality of the
 * {@link ResourceBundleVisitor} and {@link SetParser}.
 *
 * @author Dominik Fuchß
 *
 */
@VisitInfo(res = "conf/dummyForResourceBundle", visit = true)
public class DummyForSetParser implements Visitable {

	/**
	 * Shall be ["1","2"].
	 */
	@SetParser(AParser.class)
	public static String[] array;

	/**
	 * Shall be ["2","3"].
	 */
	@SetParser(AParser.class)
	public String[] objArray;
}
