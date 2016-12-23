package home.fox.visitors;

import java.lang.reflect.Field;
import java.util.Stack;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import home.fox.visitors.parser.BoolParser;
import home.fox.visitors.parser.ByteParser;
import home.fox.visitors.parser.CharParser;
import home.fox.visitors.parser.DoubleParser;
import home.fox.visitors.parser.FloatParser;
import home.fox.visitors.parser.IntParser;
import home.fox.visitors.parser.LongParser;
import home.fox.visitors.parser.MultiLevelParser;
import home.fox.visitors.parser.Parser;
import home.fox.visitors.parser.StringParser;

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
	 * Test handling of null values.
	 *
	 * @throws Exception
	 *             parser stuff
	 *
	 */
	@Test
	public void checkNullError() throws Exception {
		Parser[] parsers = { //
				new BoolParser(), new ByteParser(), new CharParser(), new DoubleParser(), new FloatParser(), new IntParser(), new LongParser(),
				new StringParser(), new MultiLevelParser(null) };
		for (Parser parser : parsers) {
			Assert.assertFalse(parser.parse(null, null, "NotTrueOrBool", new String[0]));
			this.expectLevel(Level.WARN);
		}
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
		Field field = this.getField("charField");
		Assert.assertFalse(parser.parse(null, field, "TooLong", new String[0]));
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
		MultiLevelParser parser = new MultiLevelParser(new Visitor(null) {

			@Override
			public String getValue(String key) {
				return "Value";
			}

			@Override
			protected boolean createSource(Class<? extends Visitable> v) {
				return true;
			}

			@Override
			protected boolean createSource(Visitable v) {
				return true;
			}
		});
		Field field = this.getField("mlpField");
		Assert.assertFalse(parser.parse(null, field, "TooLong", new String[0]));
		this.expectLevel(Level.ERROR);

		Assert.assertFalse(parser.parse(new Visitable() {
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
