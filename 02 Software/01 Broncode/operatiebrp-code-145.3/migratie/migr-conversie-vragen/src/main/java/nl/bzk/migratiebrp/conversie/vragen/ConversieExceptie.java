/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen;

/**
 * Treedt op indien vraag niet geconverteerd kan worden.
 */
public class ConversieExceptie extends RuntimeException {

    /**
     * Constructor met specifiek bericht.
     * @param msg detail bericht
     */
    public ConversieExceptie(final String msg) {
        super(msg);
    }

    /**
     * Constructor met bericht en reden.
     * @param msg detail bericht.
     * @param cause reden van exceptie
     */
    public ConversieExceptie(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
