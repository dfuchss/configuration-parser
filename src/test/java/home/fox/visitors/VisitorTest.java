package home.fox.visitors;

import java.util.Stack;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import home.fox.visitors.annotations.VisitInfo;
import home.fox.visitors.visitors.RecursiveVisitor;
import home.fox.visitors.visitors.ResourceBundleVisitor;

/**
 * This class shall test functionality (remaining tests) of the {@link Visitor
 * Visitors}.
 *
 * @author Dominik Fuchss
 *
 */
public class VisitorTest {

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

			Visitor.LOGGER.removeAllAppenders();
			Visitor.LOGGER.setLevel(Level.ALL);
			Visitor.LOGGER.addAppender(this.appender);
		}
		this.appender.events.clear();

	}

	/**
	 * Check behavior for missing {@link VisitInfo} for RecursiveVisitor.
	 *
	 * @throws Exception
	 *             test needs to wait
	 */
	@Test
	public void checkNoVisitInfoR() throws Exception {
		RecursiveVisitor visitor = new RecursiveVisitor(new String[0], new ResourceBundleVisitor());
		visitor.visit(MissingVisitInfo.class);
		this.expectLevel(Level.INFO);
		visitor.visit(new MissingVisitInfo());
		this.expectLevel(Level.INFO);
		Assert.assertNull(visitor.getValue("KEY"));

	}

	/**
	 * Test handling of null values.
	 *
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkNullError() {
		new RecursiveVisitor(new String[0], null);
	}

	/**
	 * Check behavior for missing {@link VisitInfo}.
	 *
	 * @throws Exception
	 *             test needs to wait
	 */
	@Test
	public void checkNoVisitInfo() throws Exception {
		ResourceBundleVisitor visitor = new ResourceBundleVisitor();
		visitor.visit(MissingVisitInfo.class);
		this.expectLevel(Level.INFO, Level.INFO);
		visitor.visit(new MissingVisitInfo());
		this.expectLevel(Level.INFO, Level.INFO);
		Assert.assertNull(visitor.getValue("KEY"));

	}

	/**
	 * A class for {@link VisitorTest#checkNoVisitInfo()}.
	 *
	 * @author Dominik Fuchss
	 *
	 */
	public static class MissingVisitInfo implements Visitable {
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
