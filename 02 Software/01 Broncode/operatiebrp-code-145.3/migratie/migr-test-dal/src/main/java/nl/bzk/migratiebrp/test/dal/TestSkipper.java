/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

/**
 * Test skipper.
 */
@FunctionalInterface
public interface TestSkipper {

    /**
     * Moet de test geskipped worden?
     * @return true, als de test geskipped moet worden, anders false
     */
    boolean skip(String naam);

    /**
     * Never skip tests.
     * @return skipper
     */
    static TestSkipper nooit() {
        return (naam) -> false;
    }

    /**
     * Skip tests ending with NOK, BLAUW-xxxx, ORANJE-xxxx, ROOD-xxxx.
     * @return skipper
     */
    static TestSkipper regressie() {
        return (naam) -> naam.matches("(?i)^.*(([BLAUW|ORANJE|ROOD]-\\d{3,5})|(NOK))$");
    }
}
