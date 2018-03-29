/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.validation;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Een error handler voor schema errors.
 */
public final class SchemaValidationSchemaErrorHandler implements ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public void warning(final SAXParseException exception) throws SAXException {
        LOGGER.warn(
            "Waarschuwing opgetreden tijdens initialisatie XSD validatie: "
                    + maakGedetailleerdMessage(exception)
                    + exception.getLocalizedMessage(),
            exception);
    }

    @Override
    public void error(final SAXParseException exception) throws SAXException {
        LOGGER.error(
            "Fout opgetreden tijdens initialisatie XSD validatie: " + maakGedetailleerdMessage(exception) + exception.getLocalizedMessage(), exception);
    }

    @Override
    public void fatalError(final SAXParseException exception) throws SAXException {
        LOGGER.error(
            "Fatale fout opgetreden tijdens initialisatie XSD validatie: "
                     + maakGedetailleerdMessage(exception)
                     + exception.getLocalizedMessage(),
            exception);
    }

    /**
     * Maakt een detail message aan voor een SaxParseException.
     * 
     * @param exception De exception.
     * @return Gedetaileerd message.
     */
    private String maakGedetailleerdMessage(final SAXParseException exception) {
        return "XSD: " + exception.getSystemId() + ", regel: " + exception.getLineNumber() + ". ";
    }
}
