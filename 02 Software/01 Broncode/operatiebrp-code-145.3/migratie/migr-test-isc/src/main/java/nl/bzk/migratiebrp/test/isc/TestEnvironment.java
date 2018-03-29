/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.util.Set;

import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Test omgeving.
 */
public interface TestEnvironment {

    /**
     * Voer stappen voor testcase uit.
     * @param processenTestCasus testcase
     */
    void beforeTestCase(ProcessenTestCasus processenTestCasus);

    /**
     * Verwerk een bericht binnen een testcase.
     * @param processenTestCasus testcase
     * @param bericht bericht
     * @throws TestException bij fouten
     */
    void verwerkBericht(final ProcessenTestCasus processenTestCasus, TestBericht bericht) throws TestException;

    /**
     * Voer stappen na de testcase uit.
     * @param processenTestCasus testcase
     * @return true als testcase goed is afgesloten
     */
    boolean afterTestCase(ProcessenTestCasus processenTestCasus);

    /**
     * Bepaal alle processen die op de laatste testcase betrekking hebben.
     * @return lijst van proces ids
     */
    Set<Long> bepaalAlleProcessen();

    /**
     * Controleer of een proces is beeindigd.
     * @param procesId proces id
     * @return true, als het proces is beeindigd
     */
    boolean controleerProcesBeeindigd(Long procesId);

}
