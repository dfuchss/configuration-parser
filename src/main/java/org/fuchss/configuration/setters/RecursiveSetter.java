package org.fuchss.configuration.setters;

import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Messages;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.parser.MultiLevelParser;

/**
 * This setter realizes a recursive setter for setting attributes of more
 * complex objects.
 *
 * @author Dominik Fuchss
 * @see MultiLevelParser
 *
 */
public final class RecursiveSetter extends Setter {
	/**
	 * The actual path so far.
	 */
	private String[] path;
	/**
	 * The id before the real key: e.g. "field1.subfield2."
	 */
	private final String preKey;

	/**
	 * Create a new RecursiveSetter.
	 *
	 * @param path
	 *            the recursive path so far.
	 * @param parent
	 *            the parent setter where all info will received from.
	 */
	public RecursiveSetter(String[] path, Setter parent) {
		super(parent);
		if (parent == null) {
			throw new IllegalArgumentException(Messages.getString("RecursiveSetter.0")); //$NON-NLS-1$
		}
		this.path = path;

		String preKey = "";
		for (String p : this.path) {
			preKey += p + ".";
		}
		this.preKey = preKey;

	}

	@Override
	protected boolean createSource(Configurable v) {
		return true;
	}

	@Override
	protected boolean createSource(Class<? extends Configurable> v) {
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

}
