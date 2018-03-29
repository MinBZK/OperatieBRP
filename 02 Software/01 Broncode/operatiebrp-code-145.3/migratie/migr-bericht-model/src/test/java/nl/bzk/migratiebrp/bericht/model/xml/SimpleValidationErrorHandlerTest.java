/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

import org.junit.Test;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleValidationErrorHandlerTest {

    @Test
    public void testWarningAfhandeling() throws SAXException {

        final SimpleValidationErrorHandler simpleValidationErrorHandler = new SimpleValidationErrorHandler();
        final Locator saxLocator = new Locator() {

            @Override
            public String getSystemId() {
                return null;
            }

            @Override
            public String getPublicId() {
                return null;
            }

            @Override
            public int getLineNumber() {
                return 0;
            }

            @Override
            public int getColumnNumber() {
                return 0;
            }
        };
        final SAXParseException exceptie = new SAXParseException("Fout tijdens parsen XML", saxLocator);

        simpleValidationErrorHandler.warning(exceptie);
    }

    @Test(expected = XmlValidatieExceptie.class)
    public void testErrorAfhandeling() throws SAXException {

        final SimpleValidationErrorHandler simpleValidationErrorHandler = new SimpleValidationErrorHandler();
        final Locator saxLocator = new Locator() {

            @Override
            public String getSystemId() {
                return null;
            }

            @Override
            public String getPublicId() {
                return null;
            }

            @Override
            public int getLineNumber() {
                return 0;
            }

            @Override
            public int getColumnNumber() {
                return 0;
            }
        };
        final SAXParseException exceptie = new SAXParseException("Fout tijdens parsen XML", saxLocator);

        simpleValidationErrorHandler.error(exceptie);
    }

    @Test(expected = XmlValidatieExceptie.class)
    public void testFataleErrorAfhandeling() throws SAXException {

        final SimpleValidationErrorHandler simpleValidationErrorHandler = new SimpleValidationErrorHandler();
        final Locator saxLocator = new Locator() {

            @Override
            public String getSystemId() {
                return null;
            }

            @Override
            public String getPublicId() {
                return null;
            }

            @Override
            public int getLineNumber() {
                return 0;
            }

            @Override
            public int getColumnNumber() {
                return 0;
            }
        };
        final SAXParseException exceptie = new SAXParseException("Fout tijdens parsen XML", saxLocator);

        simpleValidationErrorHandler.fatalError(exceptie);
    }
}
