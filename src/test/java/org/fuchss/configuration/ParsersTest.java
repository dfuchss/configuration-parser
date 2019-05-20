package org.fuchss.configuration;

import java.lang.reflect.Field;

import org.fuchss.configuration.parser.CharParser;
import org.fuchss.configuration.parser.MultiLevelParser;
import org.fuchss.configuration.parser.Parser;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class shall test functionality (remaining tests) of the {@link Parser
 * Parsers}.
 *
 * @author Dominik Fuchss
 *
 */
public class ParsersTest {
	/**
	 * The {@link Field} for {@link #checkCharParser()}.
	 */
	public static char charField;

	/**
	 * Test {@link CharParser} basic error handling functionality.
	 *
	 * @throws Exception
	 *             parser stuff
	 *
	 */
	@Test
	public void checkCharParser() throws Exception {
		CharParser parser = new CharParser();
		Assert.assertNull(parser.parse("TooLong", new String[0]));

	}

	/**
	 * The {@link Field} for {@link #checkMultiLevelParser()}.
	 */
	public static MultiLevelParserTestClass mlpField;

	/**
	 * Test {@link MultiLevelParser} basic error handling functionality.
	 *
	 * @throws Exception
	 *             parser stuff
	 *
	 */
	@Test
	public void checkMultiLevelParser() throws Exception {
		MultiLevelParser parser = new MultiLevelParser(new Setter(null) {

			@Override
			public String getValue(String key) {
				return "Value";
			}

			@Override
			protected boolean createSource(Class<? extends Configurable> v) {
				return true;
			}

			@Override
			protected boolean createSource(Configurable v) {
				return true;
			}
		});
		Field field = this.getField("mlpField");
		Assert.assertFalse(parser.parse(null, field, "TooLong", new String[0]));

		Assert.assertFalse(parser.parse(new Configurable() {
		}, field, "TooLong", new String[0]));

	}

	/**
	 * A class for {@link ParsersTest#checkMultiLevelParser()}.
	 *
	 * @author Dominik Fuchss
	 *
	 */
	public static class MultiLevelParserTestClass {
		/**
		 * An attribute.
		 */
		public String attribute;
	}

	/**
	 * Get field by name.
	 *
	 * @param name
	 *            the name
	 * @return the field
	 *
	 */
	private Field getField(String name) {
		try {
			return this.getClass().getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {
			Assert.fail();
			return null;
		}
	}
}
