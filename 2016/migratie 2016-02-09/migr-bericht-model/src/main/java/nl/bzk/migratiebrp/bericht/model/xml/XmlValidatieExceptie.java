/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Specifieke exceptie voor het geval een xml bericht niet gevalideerd kan worden tegen de xsd schema's.
 * 
 */
public class XmlValidatieExceptie extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Maak een XML Validatie exceptie op basis van een SAXParseException.
     * 
     * @param e
     *            de SAXParseException
     */
    public XmlValidatieExceptie(final SAXParseException e) {
        this("Fout opgetreden bij valideren van xml", e);
    }

    /**
     * Maak een XML Validatie exceptie op basis van een SAXException.
     * 
     * @param message
     *            de foutmelding
     * @param e
     *            de SAXException
     */
    public XmlValidatieExceptie(final String message, final SAXException e) {
        super(message, e);
    }
}
