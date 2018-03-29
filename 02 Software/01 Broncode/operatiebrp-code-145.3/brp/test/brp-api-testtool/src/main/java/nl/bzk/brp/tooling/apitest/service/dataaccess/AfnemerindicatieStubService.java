/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * Stub interface voor het doen van asserts mbt afnemerindicaties.
 */
public interface AfnemerindicatieStubService extends Stateful {

    /**
     * Assert dat de gegeven afnemerindicatie is geplaatst.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleerd wordt
     * @param leveringsautorisatieId id van leveringsautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijId id van de partij waarvoor een afnemerindicatie is geplaatst
     */
    void assertAfnemerindicatieGeplaatst(String bsn, Integer leveringsautorisatieId, Short partijId);

    /**
     * Assert dat de gegeven afnemerindicatie niet is geplaatst.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleerd wordt
     * @param leveringsautorisatieId id van leveringsautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijId id van de partij waarvoor een afnemerindicatie is geplaatst
     */
    void assertAfnemerindicatieNietGeplaatst(String bsn, Integer leveringsautorisatieId, Short partijId);

    /**
     * Assert dat de gegeven afnemerindicatie is verwijderd.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleerd wordt
     * @param leveringsautorisatieId id van leveringsautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijId id van de partij waarvoor een afnemerindicatie is geplaatst
     */
    void assertAfnemerindicatieVerwijderd(String bsn, Integer leveringsautorisatieId, Short partijId);

    /**
     * Assert dat de gegeven afnemerindicatie niet is verwijderd.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleerd wordt
     * @param leveringsautorisatieId id van leveringsautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijId id van de partij waarvoor een afnemerindicatie is geplaatst
     */
    void assertAfnemerindicatieNietVerwijderd(String bsn, Integer leveringsautorisatieId, Short partijId);

}
