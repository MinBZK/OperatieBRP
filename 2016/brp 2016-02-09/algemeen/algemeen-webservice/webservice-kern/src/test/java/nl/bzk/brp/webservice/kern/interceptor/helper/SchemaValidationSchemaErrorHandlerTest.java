/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor.helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class SchemaValidationSchemaErrorHandlerTest {

    private static Logger logger;

    private SchemaValidationSchemaErrorHandler schemaValidationSchemaErrorHandler;

    @BeforeClass
    public static void initAbstractBedrijfsregelsTest() {
        PowerMockito.mockStatic(LoggerFactory.class);
        logger = PowerMockito.mock(Logger.class);
        PowerMockito.when(LoggerFactory.getLogger(Matchers.any(Class.class))).thenReturn(logger);
    }

    @Before
    public final void initTest() {
        Mockito.reset(logger);
        schemaValidationSchemaErrorHandler = new SchemaValidationSchemaErrorHandler();
    }

    @Test
    public void testWarning() throws SAXException {
        SAXParseException exception = new SAXParseException("iets niet goed", "publicId", "systemId", 1, 2);
        schemaValidationSchemaErrorHandler.warning(exception);
        Assert.assertTrue(schemaValidationSchemaErrorHandler.isWarningsOpgetreden());
        Assert.assertFalse(schemaValidationSchemaErrorHandler.isErrorsOpgetreden());
        Mockito.verify(logger).warn(
            "Waarschuwing opgetreden tijdens initialisatie XSD validatie: XSD: systemId, regel: 1. iets niet goed",
            exception);
    }

    @Test
    public void testError() throws SAXException {
        SAXParseException exception = new SAXParseException("iets niet goed", "publicId", "systemId", 1, 2);
        schemaValidationSchemaErrorHandler.error(exception);
        Assert.assertFalse(schemaValidationSchemaErrorHandler.isWarningsOpgetreden());
        Assert.assertTrue(schemaValidationSchemaErrorHandler.isErrorsOpgetreden());
        Mockito.verify(logger).error(
            "Fout opgetreden tijdens initialisatie XSD validatie: XSD: systemId, regel: 1. iets niet goed",
            exception);
    }

    @Test
    public void testFatalError() throws SAXException {
        SAXParseException exception = new SAXParseException("iets niet goed", "publicId", "systemId", 1, 2);
        schemaValidationSchemaErrorHandler.fatalError(exception);
        Assert.assertFalse(schemaValidationSchemaErrorHandler.isWarningsOpgetreden());
        Assert.assertTrue(schemaValidationSchemaErrorHandler.isErrorsOpgetreden());
        Mockito.verify(logger).error(
            "Fatale fout opgetreden tijdens initialisatie XSD validatie: XSD: systemId, regel: 1. iets niet goed",
            exception);
    }
}
