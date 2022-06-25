package org.fuchss.configuration;

import org.fuchss.configuration.parser.CharParser;
import org.fuchss.configuration.parser.MultiLevelParser;
import org.fuchss.configuration.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * This class shall test functionality (remaining tests) of the {@link Parser
 * Parsers}.
 *
 * @author Dominik Fuchss
 */
class ParsersTest {
	/**
	 * The {@link Field} for {@link #checkCharParser()}.
	 */
	public static char charField;

	/**
	 * Test {@link CharParser} basic error handling functionality.
	 *
	 * @throws Exception parser stuff
	 */
	@Test
	void checkCharParser() throws Exception {
		CharParser parser = new CharParser();
		Assertions.assertNull(parser.parse("TooLong", new String[0]));
	}

	/**
	 * The {@link Field} for {@link #checkMultiLevelParser()}.
	 */
	public static MultiLevelParserTestClass mlpField;

	/**
	 * Test {@link MultiLevelParser} basic error handling functionality.
	 *
	 * @throws Exception parser stuff
	 */
	@Test
	void checkMultiLevelParser() throws Exception {
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
		Assertions.assertFalse(parser.parse(null, field, "TooLong", new String[0]));

		Assertions.assertFalse(parser.parse(new Configurable() {
		}, field, "TooLong", new String[0]));

	}

	/**
	 * A class for {@link ParsersTest#checkMultiLevelParser()}.
	 *
	 * @author Dominik Fuchss
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
	 * @param name the name
	 * @return the field
	 */
	private Field getField(String name) {
		try {
			return this.getClass().getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {
			Assertions.fail();
			return null;
		}
	}
}
