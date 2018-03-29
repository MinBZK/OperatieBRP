/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.check;

import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Check. Controleert voorwaarden.
 */
public interface Check {

    /**
     * Voer een controle uit.
     * @param context context
     * @param bericht ontvangen bericht
     * @param testbericht test bericht
     * @param config config (uit configuratie)
     * @return true, als de controle is geslaagd, anders false
     * @throws TestException bij fouten
     */
    boolean check(CheckContext context, Bericht bericht, TestBericht testbericht, String config) throws TestException;
}
