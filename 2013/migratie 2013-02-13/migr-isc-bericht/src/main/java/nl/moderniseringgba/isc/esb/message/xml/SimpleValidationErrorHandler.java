/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.xml;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Klasse die de fouten in de xml validatie kan afhandelen.
 */
public final class SimpleValidationErrorHandler implements ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String VALIDATIE_FOUT = "Fout opgetreden bij valideren van xml";

    @Override
    public void warning(final SAXParseException exception) throws SAXException {
        LOG.warn(VALIDATIE_FOUT, exception);
    }

    @Override
    public void error(final SAXParseException exception) throws SAXException {
        LOG.error(VALIDATIE_FOUT, exception);
        throw new XmlValidatieExceptie(exception);

    }

    @Override
    public void fatalError(final SAXParseException exception) throws SAXException {
        LOG.error(VALIDATIE_FOUT, exception);
        throw new XmlValidatieExceptie(exception);
    }

}
