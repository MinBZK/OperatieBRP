/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging.log4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.MessageFormatMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.DefaultThreadContextStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BrpJsonLayoutTest {

    private static final Logger LOGGER           = LoggerFactory.getLogger();
    private static final String LOG_SERIALIZABLE = "Serializable: ";
    private static final String MARKER           = "marker";
    private static final String MESSAGE          = "Dit is een message!";
    private static final String LOGGER_FQCN      = "loggerFQCN";

    private BrpJsonLayout       brpJsonLayout;

    @Before
    public void init() {
        brpJsonLayout = BrpJsonLayout.createLayout();
    }

    @Test
    public void testCreateLayout() {
        Assert.assertNotNull(brpJsonLayout);
    }

    @Test
    public void testToSerializable() {
        final Marker marker = MarkerManager.getMarker(MARKER);
        final Log4jLogEvent log4jLogEvent =
            new Log4jLogEvent("LOGGERTEST", marker, LOGGER_FQCN, Level.ERROR, new SimpleMessage(MESSAGE), null);
        final StackTraceElement stackTraceElement = new StackTraceElement("Nep", "methodName", "Nep.class", 123);
        ReflectionTestUtils.setField(log4jLogEvent, "source", stackTraceElement);

        final String serializable = brpJsonLayout.toSerializable(log4jLogEvent);
        final String serializable2 = brpJsonLayout.toSerializable(log4jLogEvent);
        final String serializable3 = brpJsonLayout.toSerializable(log4jLogEvent);

        LOGGER.info(LOG_SERIALIZABLE + serializable);
        LOGGER.info(LOG_SERIALIZABLE + serializable2);
        LOGGER.info(LOG_SERIALIZABLE + serializable3);
        Assert.assertNotNull(serializable);
    }

    @Test
    public void testZonderLoggerNameEnMarkerEnMessage() {
        final Log4jLogEvent log4jLogEvent = new Log4jLogEvent("", null, LOGGER_FQCN, Level.ERROR, null, null, null);

        final String serializable = brpJsonLayout.toSerializable(log4jLogEvent);
        LOGGER.info(LOG_SERIALIZABLE + serializable);
        Assert.assertNotNull(serializable);
    }

    @Test
    public void testGeenTekst() {
        final Log4jLogEvent log4jLogEvent = new Log4jLogEvent("", null, LOGGER_FQCN, Level.ERROR, null, null, null);

        final String serializable = brpJsonLayout.toSerializable(log4jLogEvent);
        LOGGER.info(LOG_SERIALIZABLE + serializable);
        Assert.assertNotNull(serializable);
    }

    @Test
    public void testMDC() {
        final Marker marker = MarkerManager.getMarker(MARKER);
        final List<Property> properties = new ArrayList<>();
        properties.add(Property.createProperty("foo", "bar"));
        properties.add(Property.createProperty("boo", "far"));
        final Log4jLogEvent log4jLogEvent =
            new Log4jLogEvent("LOGGER", marker, LOGGER_FQCN, Level.ERROR, new MessageFormatMessage(MESSAGE),
                    properties, null);

        final String serializable = brpJsonLayout.toSerializable(log4jLogEvent);
        LOGGER.info(LOG_SERIALIZABLE + serializable);
        Assert.assertNotNull(brpJsonLayout);
        Assert.assertNotNull(serializable);
    }

    @Test
    public void testNDC() {
        final Marker marker = MarkerManager.getMarker(MARKER);

        final DefaultThreadContextStack stack = new DefaultThreadContextStack(true);
        stack.add("stack1");
        stack.add("stack2");
        stack.add("stack3");

        final Log4jLogEvent log4jLogEvent =
            Log4jLogEvent.createEvent("name", marker, LOGGER_FQCN, Level.ERROR, new MessageFormatMessage(MESSAGE),
                    null, null, null, stack, "threadName", null, new Date().getTime());

        final String serializable = brpJsonLayout.toSerializable(log4jLogEvent);
        LOGGER.info(LOG_SERIALIZABLE + serializable);
        Assert.assertNotNull(brpJsonLayout);
        Assert.assertNotNull(serializable);
    }

    @Test
    public void testThrown() {
        final Marker marker = MarkerManager.getMarker(MARKER);
        final Log4jLogEvent log4jLogEvent =
            new Log4jLogEvent("", marker, LOGGER_FQCN, Level.ERROR, new MessageFormatMessage(MESSAGE), null,
                    new LoggingException("Alarm alarm!"));

        final String serializable = brpJsonLayout.toSerializable(log4jLogEvent);
        LOGGER.info(LOG_SERIALIZABLE + serializable);
        Assert.assertNotNull(brpJsonLayout);
        Assert.assertNotNull(serializable);
    }

    @Test
    public void testGetHeader() {
        final byte[] header = brpJsonLayout.getHeader();
        Assert.assertNull(header);
    }

    @Test
    public void testGetFooter() {
        final byte[] footer = brpJsonLayout.getFooter();
        Assert.assertNull(footer);
    }

    @Test
    public void testGetContentFormat() {
        final Map<String, String> contentFormat = brpJsonLayout.getContentFormat();
        Assert.assertNotNull(contentFormat);
        Assert.assertEquals("2.0", contentFormat.get("version"));
    }

    @Test
    public void testGetContentType() {
        final String contentType = brpJsonLayout.getContentType();
        Assert.assertNotNull(contentType);
        Assert.assertEquals("application/json; charset=UTF-8", contentType);
    }
}
