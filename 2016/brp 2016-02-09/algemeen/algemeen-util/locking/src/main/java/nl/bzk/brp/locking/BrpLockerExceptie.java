/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.locking;

/**
 * Exceptie die aangeeft dat de gevraagde read- of write-locks op de database id niet konden worden verkregen.
 * Er wordt geen onderscheid gemaakt tussen alle mogelijke onderliggende oorzaken; die blijken uit de gewrapte
 * exception en moeten uit de logfiles worden gehaald. De meest voordehandliggende oorzaak is dat een andere service
 * aanroep al een exclusieve lock op de gevraagde id's heeft.
 */
public class BrpLockerExceptie extends Exception {

    private static final long serialVersionUID = -2074913962830965575L;

    /**
     * Construeert een nieuwe BrpLockerExceptie met de meegegeven beschrijving.
     *
     * @param message Een beschrijving van de situatie.
     */
    public BrpLockerExceptie(final String message) {
        super(message);
    }

    /**
     * Construeert een nieuwe BrpLockerExceptie met de meegegeven beschrijving en oorzaak.
     *
     * @param message Een beschrijving van de situatie.
     * @param cause   De onderliggende oorzaak van deze uitzondering.
     */
    public BrpLockerExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }
}
