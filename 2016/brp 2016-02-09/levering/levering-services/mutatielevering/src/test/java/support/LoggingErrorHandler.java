/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package support;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class LoggingErrorHandler implements ErrorHandler {
    private static final Logger  LOGGER             = LoggerFactory.getLogger();
    private              boolean validatieResultaat = true;

    @Override
    public final void warning(final SAXParseException e) throws SAXException {
        LOGGER.warn("Validatie waarschuwing op positie {}:{}, {}", e.getLineNumber(), e.getColumnNumber(), e.getMessage());
        validatieResultaat = false;
    }

    @Override
    public final void error(final SAXParseException e) throws SAXException {
        LOGGER.warn("Validatie fout op positie {}:{}, {}", e.getLineNumber(), e.getColumnNumber(), e.getMessage());
        validatieResultaat = false;
    }

    @Override
    public final void fatalError(final SAXParseException e) throws SAXException {
        LOGGER.warn("Validatie fatale fout op positie {}:{}, {}", e.getLineNumber(), e.getColumnNumber(), e.getMessage());
        validatieResultaat = false;
    }

    public final boolean isValid() {
        return validatieResultaat;
    }
}
