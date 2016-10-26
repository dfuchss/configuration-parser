package home.fox.visitors.visitors;

import java.util.Map;

import home.fox.visitors.Visitable;
import home.fox.visitors.Visitor;

/**
 * This class realizes a {@link Visitor} which will use a {@link Map}.
 *
 * @author Dominik Fuchss
 *
 */
public final class MapVisitor extends Visitor {
	/**
	 * The KV-Store.
	 */
	private final Map<String, String> kv;

	/**
	 * Create Visitor by Map.
	 *
	 * @param kv
	 *            the map
	 */
	public MapVisitor(Map<String, String> kv) {
		this.kv = kv;
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
	protected String getValue(String key) {
		if (this.kv == null || !this.kv.containsKey(key)) {
			return null;
		}
		return this.kv.get(key);
	}

}
