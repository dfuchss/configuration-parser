package org.fuchss.configuration;

import java.util.Stack;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.fuchss.configuration.Configurable;
import org.fuchss.configuration.Setter;
import org.fuchss.configuration.annotations.SetterInfo;
import org.fuchss.configuration.setters.RecursiveSetter;
import org.fuchss.configuration.setters.ResourceBundleSetter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class shall test functionality (remaining tests) of the {@link Setter
 * Visitors}.
 *
 * @author Dominik Fuchss
 *
 */
public class SetterTest {

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

			Setter.LOGGER.removeAllAppenders();
			Setter.LOGGER.setLevel(Level.ALL);
			Setter.LOGGER.addAppender(this.appender);
		}
		this.appender.events.clear();

	}

	/**
	 * Check behavior for missing {@link SetterInfo} for RecursiveSetter.
	 *
	 * @throws Exception
	 *             test needs to wait
	 */
	@Test
	public void checkNoVisitInfoR() throws Exception {
		RecursiveSetter visitor = new RecursiveSetter(new String[0], new ResourceBundleSetter());
		visitor.setAttributes(MissingVisitInfo.class);
		this.expectLevel(Level.INFO);
		visitor.setAttributes(new MissingVisitInfo());
		this.expectLevel(Level.INFO);
		Assert.assertNull(visitor.getValue("KEY"));

	}

	/**
	 * Test handling of null values.
	 *
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkNullError() {
		new RecursiveSetter(new String[0], null);
	}

	/**
	 * Check behavior for missing {@link SetterInfo}.
	 *
	 * @throws Exception
	 *             test needs to wait
	 */
	@Test
	public void checkNoVisitInfo() throws Exception {
		ResourceBundleSetter visitor = new ResourceBundleSetter();
		visitor.setAttributes(MissingVisitInfo.class);
		this.expectLevel(Level.INFO, Level.INFO);
		visitor.setAttributes(new MissingVisitInfo());
		this.expectLevel(Level.INFO, Level.INFO);
		Assert.assertNull(visitor.getValue("KEY"));

	}

	/**
	 * A class for {@link SetterTest#checkNoVisitInfo()}.
	 *
	 * @author Dominik Fuchss
	 *
	 */
	public static class MissingVisitInfo implements Configurable {
	}

	/**
	 * Invoke if {@link #appender} shall contain levels (and delete from stack).
	 *
	 * @param levels
	 *            the expected levels
	 */
	private void expectLevel(Level... levels) {
		for (Level level : levels) {
			Assert.assertEquals(level, this.appender.events.pop().getLevel());
		}
	}
}
