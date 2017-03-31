package org.fuchss.configuration;

import java.lang.reflect.Field;
import java.util.Stack;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.fuchss.configuration.parser.CharParser;
import org.fuchss.configuration.parser.MultiLevelParser;
import org.fuchss.configuration.parser.Parser;
import org.junit.Assert;
import org.junit.Before;
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
	 * An internal appender which logs all logging events.
	 *
	 * @author Dominik Fuchss
	 *
	 */
	private static class InternalAppender extends AppenderSkeleton {
		/**
		 * A stack, holding current events.
		 */
		private Stack<LoggingEvent> events = new Stack<>();

		@Override
		public void close() {
		}

		@Override
		public boolean requiresLayout() {
			return false;
		}

		@Override
		protected void append(LoggingEvent event) {
			this.events.push(event);
		}

	}

	/**
	 * The internal appender.
	 */
	private InternalAppender appender;

	/**
	 * Setup appender for checking.
	 */
	@Before
	public void addAppender() {
		if (this.appender == null) {
			this.appender = new InternalAppender();

			Parser.LOGGER.removeAllAppenders();
			Parser.LOGGER.setLevel(Level.ALL);
			Parser.LOGGER.addAppender(this.appender);
		}
		this.appender.events.clear();
	}

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
		this.expectLevel(Level.ERROR);

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
		this.expectLevel(Level.ERROR);

		Assert.assertFalse(parser.parse(new Configurable() {
		}, field, "TooLong", new String[0]));
		this.expectLevel(Level.ERROR);

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
	 * Invoke if {@link #appender} shall contain an level (and delete from
	 * stack).
	 *
	 * @param level
	 *            the expected level
	 */
	private void expectLevel(Level level) {
		Assert.assertTrue(this.appender.events.pop().getLevel() == level);
		Assert.assertTrue(this.appender.events.isEmpty());
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
