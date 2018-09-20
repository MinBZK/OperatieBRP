/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.Date;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;

/**
 * CRUD functionaliteit voor Lo3Bericht entities.
 * 
 * @see Lo3Bericht
 */
public interface Lo3BerichtRepository {

    /**
     * Zoekt de laatst toegevoegde Lo3Bericht entiteit en retourneerd dit als resultaat. Als niets gevonden kan worden
     * wordt null geretourneerd.
     * 
     * @param administratienummer
     *            het administratienummer van het LO3 bronbericht wat tot deze Lo3Bericht heeft geleid
     * @return de laatst toegevoegde Lo3Bericht entiteit of null
     */
    Lo3Bericht findLaatsteLo3PersoonslijstBerichtVoorANummer(long administratienummer);

    /**
     * Zoekt de laatst toegevoegde anummers uit de Lo3Bericht welke met de datum tijd stempel tussen vanaf en tot zit.
     * 
     * @param vanaf
     *            De datum tijd stempel is groter dan of gelijk aan vanaf.
     * @param tot
     *            De datum tijd stempel is kleiner dan tot.
     * @return Set met anummers die voldoen aan de criteria.
     */
    Set<Long> findLaatsteBerichtLogAnrs(final Date vanaf, final Date tot);

    /**
     * Slaat de meegegeven BerichtLog op in de database.
     * 
     * @param bericht
     *            de Lo3Bericht entiteit die moet worden opgeslagen in de database
     * @return de Lo3Bericht entiteit die opgeslagen is in de database
     */
    Lo3Bericht save(Lo3Bericht bericht);

    /**
     * Flush alle wijzigingen naar de database. Niet gebruiken tenzij het niet anders kan. Dit is slecht voor de
     * performance.
     */
    void flush();
}
