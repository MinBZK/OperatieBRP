/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

/**
 * Voor het afhandelen van fouten die tijdens het parsen van een persoonslijst kunnen optreden.
 * 
 */
public class ParseException extends RuntimeException {

    private static final long serialVersionUID = -2551179271427383995L;

    /**
     * Maakt een nieuwe ParseException aan die aangeeft dat er iets fout ging tijdens het parsen.
     * 
     * @param message
     *            De omschrijving van de fout
     * @param cause
     *            De onderliggende oorzaak
     */
    public ParseException(final String message, final Exception cause) {
        super(message, cause);

    }

    /**
     * Maakt een nieuwe ParseException aan die aangeeft dat er iets fout ging tijdens het parsen.
     * 
     * @param message
     *            De omschrijving van de fout
     */
    public ParseException(final String message) {
        super(message);
    }
}
