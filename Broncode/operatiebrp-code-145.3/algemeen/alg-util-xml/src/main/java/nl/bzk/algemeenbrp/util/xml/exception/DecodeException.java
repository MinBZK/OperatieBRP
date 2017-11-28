/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.exception;

/**
 * Decodeer probleem.
 */
public final class DecodeException extends XmlException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param elementStack element stack (uit context)
     * @param message melding
     */
    public DecodeException(final ElementStack elementStack, final String message) {
        super(elementStack, message);
    }

    /**
     * Constructor.
     *
     * @param elementStack element stack (uit context)
     * @param cause oorzaak
     */
    public DecodeException(final ElementStack elementStack, final Exception cause) {
        super(elementStack, "Fout tijdens decoderen", cause);
    }

    /**
     * Constructor.
     *
     * @param elementStack element stack (uit context)
     * @param message melding
     * @param cause oorzaak
     */
    public DecodeException(final ElementStack elementStack, final String message, final Exception cause) {
        super(elementStack, message, cause);
    }
}
