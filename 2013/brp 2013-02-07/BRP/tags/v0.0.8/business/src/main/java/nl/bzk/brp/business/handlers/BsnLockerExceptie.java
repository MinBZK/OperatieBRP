/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

/**
 * Exceptie die aangeeft dat de gevraagde read- of write-locks op de burgerservicenummers niet konden worden verkregen.
 * Er wordt geen onderscheid gemaakt tussen alle mogelijke onderliggende oorzaken; die blijken uit de gewrappede
 * exception en moeten uit de logfiles worden gehaald. De meest voordehandliggende oorzaak is dat een andere service
 * aanroep al een exclusieve lock op de gevraagde BSN's heeft.
 */
public class BsnLockerExceptie extends RuntimeException {

    /**
     * Contrueert een nieuwe BsnLockerExceptie met de meegegeven beschrijving en oorzaak.
     *
     * @param message Een beschrijving van de situatie.
     * @param cause De onderliggende oorzaak van deze uitzondering.
     */
    public BsnLockerExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }
}
