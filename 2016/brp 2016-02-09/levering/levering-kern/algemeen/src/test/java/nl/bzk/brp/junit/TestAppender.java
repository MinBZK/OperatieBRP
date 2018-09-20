/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.junit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.logging.log4j.BrpJsonLayout;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * Test appender tbv van validatie in tests.
 * <p>
 * Usage:<br />
 * <code>
 * TestAppender testAppender = new TestAppender();<br />
 * classUnderTest.methodThatWillLog();<br /><br />
 * LoggingEvent loggingEvent = testAppender.getEvents().get(0);<br /><br />
 * assertEquals()...<br /><br />
 * </code>
 *
 * @see <a href="http://www.javacodegeeks.com/2014/09/some-more-unit-test-tips.html">JavaCodeGeeks artikel</a>
 */
public class TestAppender extends AbstractAppender {
    private final List<LogEvent> events = new ArrayList<>();

    public TestAppender() {
        super("TestAppender", null, BrpJsonLayout.createLayout());

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        for (final LoggerConfig lc : config.getLoggers().values()) {
            if ("".equals(lc.getName())) {
                lc.addAppender(this, Level.ALL, null);
            }
        }
        config.addAppender(this);
    }

    @Override
    public void append(final LogEvent logEvent) {
        events.add(logEvent);
    }

    public List<LogEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }

}
