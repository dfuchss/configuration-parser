package home.fox.visitors.visitors;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;
import home.fox.visitors.parser.MultiLevelParser;

/**
 * This visitor realizes a recursive visitor for visiting more complex objects.
 *
 * @author Dominik Fuchss
 * @see MultiLevelParser
 *
 */
public final class RecursiveVisitor extends Visitor {
	/**
	 * The actual path so far.
	 */
	private String[] path;
	/**
	 * The id before the real key: e.g. "field1.subfield2."
	 */
	private final String preKey;

	/**
	 * Create a new Recursive Visitor.
	 *
	 * @param path
	 *            the recursive path so far.
	 * @param parent
	 *            the parent visitor where all info will received from.
	 */
	public RecursiveVisitor(String[] path, Visitor parent) {
		super(parent);
		if (parent == null) {
			throw new IllegalArgumentException("Parent visitor is needed by RecursiveParser");
		}
		this.path = path;

		String preKey = "";
		for (String p : this.path) {
			preKey += p + ".";
		}
		this.preKey = preKey;

	}

	@Override
	protected boolean createSource(Visitable v) {
		return true;
	}

	@Override
	protected boolean createSource(Class<? extends Visitable> v) {
		return true;
	}

	@Override
	public String getValue(String key) {
		return this.parent.getValue(this.preKey + key);
	}

	@Override
	protected String[] getPath() {
		return this.path;
	}

	@Override
	public Visitor getParent() {
		return this.parent;
	}

}
