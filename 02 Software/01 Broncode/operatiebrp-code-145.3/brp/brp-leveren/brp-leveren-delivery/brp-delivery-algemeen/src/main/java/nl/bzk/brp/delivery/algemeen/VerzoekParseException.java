/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

/**
 * Exception die gegooid wordt als het parsen van verzoek mislukt.
 */
public class VerzoekParseException extends Exception {

    private static final long serialVersionUID = -6687363812129693808L;

    /**
     * Constructor.
     * @param cause de cause
     */
    public VerzoekParseException(final Throwable cause) {
        super(cause);
    }
}
