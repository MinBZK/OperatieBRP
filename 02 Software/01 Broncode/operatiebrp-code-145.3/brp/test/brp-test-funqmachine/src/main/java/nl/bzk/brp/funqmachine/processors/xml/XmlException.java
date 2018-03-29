/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.xml;

/**
 * Exception mbt XML bewerkingen.
 */
public class XmlException extends Exception {
    /**
     * Constructor met alleen de oorspronkelijke oorzaak.
     * @param cause de oorspronkelijke oorzaak
     */
    public XmlException(final Throwable cause) {
        super(cause);
    }
}
