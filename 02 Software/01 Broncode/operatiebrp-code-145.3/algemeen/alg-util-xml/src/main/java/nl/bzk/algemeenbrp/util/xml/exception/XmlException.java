/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.exception;

/**
 * Basis exception.
 */
public class XmlException extends Exception {

    private static final long serialVersionUID = 1L;

    private static final String POSITIE_FORMAT = " (positie %s)";

    /**
     * Constructor.
     *
     * @param elementStack element stack (uit context)
     * @param message melding
     */
    protected XmlException(final ElementStack elementStack, final String message) {
        super(message + String.format(POSITIE_FORMAT, elementStack.asString()));
    }

    /**
     * Constructor.
     *
     * @param elementStack element stack (uit context)
     * @param message melding
     * @param cause oorzaak
     */
    protected XmlException(final ElementStack elementStack, final String message, final Throwable cause) {
        super(message + String.format(POSITIE_FORMAT, elementStack.asString()), cause);
    }

}
