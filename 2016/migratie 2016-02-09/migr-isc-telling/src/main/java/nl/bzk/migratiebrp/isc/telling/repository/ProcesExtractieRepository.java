/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository;

import java.sql.Timestamp;
import java.util.List;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesExtractie;

/**
 * De Proces Extractie Repository.
 */
public interface ProcesExtractieRepository {

    /**
     * Slaat de meegegeven proces extractie op in de database.
     * 
     * @param procesExtractie
     *            de procesExtractie entiteit die moet worden opgeslagen in de database
     * @return de procesExtractie entiteit die opgeslagen is in de database
     */
    ProcesExtractie save(ProcesExtractie procesExtractie);

    /**
     * Haalt alle procesExtracties op waarvan het proces is beëindigd, maar die nog niet eerder zijn meegenomen in een
     * telling op 'beëindigd'.
     * 
     * @param datumTot
     *            De datum tot wanneer er wordt gekeken.
     * @param limit
     *            Maximaal aantal op te halen proces extracties.
     * @return De lijst met opgehaalde procesExtracties.
     */
    List<ProcesExtractie> selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(Timestamp datumTot, Integer limit);

    /**
     * Haalt alle procesExtracties op waarvan het proces is beëindigd, maar die nog niet eerder zijn meegenomen in een
     * telling op 'gestart'.
     * 
     * @param datumTot
     *            De datum tot wanneer er wordt gekeken.
     * @param limit
     *            Maximaal aantal op te halen proces extracties.
     * @return De lijst met opgehaalde procesExtracties.
     */
    List<ProcesExtractie> selecteerInTellingTeVerwerkenGestarteProcesInstanties(Timestamp datumTot, Integer limit);

    /**
     * Markeert de meegegeven proces extracties als zijnde geteld voor de 'gestart' telling.
     * 
     * @param teUpdatenIds
     *            De ID's van de te markeren proces extracties.
     * @return True indien alle meegegeven proces extracties gemarkeerd konden worden, false in alle andere gevallen.
     */
    boolean updateIndicatieGestartGeteldProcesExtracties(List<Long> teUpdatenIds);

    /**
     * Markeert de meegegeven proces extracties als zijnde geteld voor de 'beëindigd' telling.
     * 
     * @param teUpdatenIds
     *            De ID's van de te markeren proces extracties.
     * @return True indien alle meegegeven proces extracties gemarkeerd konden worden, false in alle andere gevallen.
     */
    boolean updateIndicatieBeeindigdGeteldProcesExtracties(List<Long> teUpdatenIds);

    /**
     * Telt het aantal te verwerken proces extracties voor 'gestart'.
     * 
     * @param datumTot
     *            De datum tot wanneer er wordt geteld.
     * @return Het aantal te verwerken proces extracties.
     */
    Long telInTellingTeVerwerkenGestarteProcessen(Timestamp datumTot);

    /**
     * Telt het aantal te verwerken proces extracties voor 'beeindigd'.
     * 
     * @param datumTot
     *            De datum tot wanneer er wordt geteld.
     * @return Het aantal te verwerken proces extracties.
     */
    Long telInTellingTeVerwerkenBeeindigdeProcessen(Timestamp datumTot);

}
