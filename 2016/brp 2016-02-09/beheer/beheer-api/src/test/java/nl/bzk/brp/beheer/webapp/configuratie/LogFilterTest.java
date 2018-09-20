/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LogFilterTest {

    @Mock
    private HttpServletRequest  request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain         chain;

    private TestAppender        testAppender;

    private List<LogEvent>      events = new ArrayList<>();

    /**
     * setup logger.
     */
    @Before
    public final void setupLogger() {
        events.clear();
        MockitoAnnotations.initMocks(this);
        testAppender = new TestAppender("testappender", events);
        final Logger deLogger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
        final org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) deLogger;
        coreLogger.addAppender(testAppender);
        if (coreLogger.getAppenders().containsKey("Logstash")) {
            coreLogger.removeAppender(coreLogger.getAppenders().get("Logstash"));
        }
        if (coreLogger.getAppenders().containsKey("Logstash-Primary")) {
            coreLogger.removeAppender(coreLogger.getAppenders().get("Logstash-Primary"));
        }
        testAppender.start();
    }

    /**
     * Test of doFilterInternal method, of class LogFilter.
     *
     * @throws java.lang.Exception een fout
     */
    @Test
    public final void testDoFilterInternal() throws Exception {
        final LogFilter logFilter = new LogFilter();

        Mockito.when(request.getHeader("Access-Control-Request-Method")).thenReturn("GET");
        Mockito.when(request.getAuthType()).thenReturn("EenAuthMethode");
        Mockito.when(request.getRemoteHost()).thenReturn("123.123.123.123");
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://een.test.url"));
        Mockito.when(request.getParameter("Eerste")).thenReturn("EersteWaarde");
        Mockito.when(request.getParameter("Tweede")).thenReturn("TweedeWaarde");
        final Enumeration<String> enumParameters = new Enumeration<String>() {

            private List<String> waarden = Arrays.asList("Eerste", "Tweede");
            private int          counter;

            @Override
            public boolean hasMoreElements() {
                return counter < 2;
            }

            @Override
            public String nextElement() {
                return waarden.get(counter++);
            }

        };
        Mockito.when(request.getParameterNames()).thenReturn(enumParameters);

        Mockito.when(response.getStatus()).thenReturn(HttpServletResponse.SC_UNAUTHORIZED);
        logFilter.doFilterInternal(request, response, chain);
        assertTrue(testAppender.isStarted());
        assertEquals(3, events.size());
    }

    @Test
    public final void testDoFilterInternalMetExceptie() throws Exception {
        final LogFilter logFilter = new LogFilter();

        Mockito.when(request.getHeader("Access-Control-Request-Method")).thenReturn("GET");
        Mockito.when(request.getAuthType()).thenReturn("EenAuthMethode");
        Mockito.when(request.getRemoteHost()).thenReturn("123.123.123.123");
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://een.test.url"));
        Mockito.when(request.getParameter("Eerste")).thenReturn("EersteWaarde");
        Mockito.when(request.getParameter("Tweede")).thenReturn("TweedeWaarde");
        final Enumeration<String> enumParameters = new Enumeration<String>() {

            private List<String> waarden = Arrays.asList("Eerste", "Tweede");
            private int          counter;

            @Override
            public boolean hasMoreElements() {
                return counter < 2;
            }

            @Override
            public String nextElement() {
                return waarden.get(counter++);
            }

        };
        Mockito.when(request.getParameterNames()).thenReturn(enumParameters);

        Mockito.when(response.getStatus()).thenReturn(HttpServletResponse.SC_UNAUTHORIZED);

        Mockito.doThrow(new IOException("test")).when(chain).doFilter(request, response);

        try {
            logFilter.doFilterInternal(request, response, chain);
            fail();
        } catch (IOException | ServletException ex) {
            assertEquals(3, events.size());
        }

    }

    /**
     * appender voor test.
     */
    public static class TestAppender extends AbstractAppender {

        private final List<LogEvent> events;

        public TestAppender(final String name, final List<LogEvent> events) {
            super(name, null, null);
            this.events = events;
        }

        @Override
        public final void append(final LogEvent event) {
            events.add(event);
        }

    }
}
