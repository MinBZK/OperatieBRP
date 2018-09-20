/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository;

import java.sql.Timestamp;
import java.util.List;
import nl.bzk.migratiebrp.isc.telling.entiteit.Bericht;

/**
 * De Bericht Repository.
 */
public interface BerichtRepository {

    /**
     * Slaat het meegegeven bericht op in de database.
     * 
     * @param bericht
     *            de bericht entiteit die moet worden opgeslagen in de database
     * @return de bijgewerkte bericht entiteit die opgeslagen is in de database
     */
    Bericht save(Bericht bericht);

    /**
     * Haalt alle berichten op die nog niet eerder zijn meegenomen in een telling.
     * 
     * @param datumTot
     *            De datum tot wanneer de berichten worden meegenomen voor de telling.
     * @param limit
     *            Limiet aantal resultaten.
     * @return De lijst met opgehaalde berichten.
     */
    List<Bericht> selecteerInTellingTeVerwerkenBerichten(Timestamp datumTot, Integer limit);

    /**
     * Markeert de meegegeven berichten als zijnde geteld voor de telling.
     * 
     * @param teUpdatenIds
     *            De ID's van de te markeren berichten.
     * @return True indien alle meegegeven berichten gemarkeerd konden worden, false in alle andere gevallen.
     */
    boolean updateIndicatieGeteldBerichten(List<Long> teUpdatenIds);

    /**
     * Telt het aantal berichten dat meegenomen wordt voor de tellingen.
     * 
     * @param datumTot
     *            De datum tot wanneer berichten worden meegenomen.
     * @return Het aantal berichten dat in de tellingen wordt meegenomen.
     */
    Long telInTellingTeVerwerkenBerichten(Timestamp datumTot);

}
