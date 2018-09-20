/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Bericht.TYPE;


/**
 * De Interface ScenarioEngine.
 */
public interface ScenarioEngine {

    /**
     * Run scenario.
     *
     * @param bericht de bericht
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    void runScenario(Bericht.BERICHT bericht, Eigenschappen eigenschappen) throws Exception;

    /**
     * Run bericht.
     *
     * @param bericht de bericht
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    void runBericht(BERICHT bericht, Eigenschappen eigenschappen) throws Exception;

    /**
     * Run type.
     *
     * @param type de type
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    void runType(TYPE type, Eigenschappen eigenschappen) throws Exception;
}
