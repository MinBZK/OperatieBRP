/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.conversie;

/**
 * Exceptie treedt op als er iets is fout gaat tijdens conversie.
 */
public class ConversieException extends Exception {
    /**
     * Constructor met alleen de cause.
     * @param cause de oorspronkelijke cause
     */
    ConversieException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor voor naast de cause ook een melding te kunnen loggen.
     * @param message het bericht
     * @param cause de oorspronkelijke cause
     */
    ConversieException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
