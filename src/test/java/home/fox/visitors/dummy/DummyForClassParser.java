package home.fox.visitors.dummy;

import home.fox.visitors.Visitable;
import home.fox.visitors.annotations.SetParser;
import home.fox.visitors.annotations.VisitInfo;
import home.fox.visitors.toParse.StringB;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class will be used to test several functionality of the
 * {@link ResourceBundleVisitor} and {@link SetParser}.
 *
 * @author Dominik Fuchss
 *
 */
@VisitInfo(res = "conf/dummyForResourceBundle", visit = true)
public class DummyForClassParser implements Visitable {

	/**
	 * Shall be "Earth".
	 */
	public static StringB classStringB;

	/**
	 * Shall be "Hello".
	 */
	public StringB objStringB;

}
