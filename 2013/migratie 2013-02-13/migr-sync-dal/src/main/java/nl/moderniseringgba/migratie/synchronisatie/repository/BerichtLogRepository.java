/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;

/**
 * CRUD functionaliteit voor BerichtLog entities.
 * 
 * @see BerichtLog
 */
public interface BerichtLogRepository {

    /**
     * Zoekt de laatst toegevoegde BerichtLog entiteit en retourneerd dit als resultaat. Als niets gevonden kan worden
     * wordt null geretourneerd.
     * 
     * @param administratienummer
     *            het administratienummer van het LO3 bronbericht wat tot deze BerichtLog heeft geleid
     * @return de laatst toegevoegde BerichtLog entiteit of null
     */
    BerichtLog findLaatsteBerichtLogVoorANummer(BigDecimal administratienummer);

    /**
     * Zoekt de laatsts toegevoegde anummers uit de BerichtLog welke met de datum tijd stempel tussen vanaf en tot zit
     * en als de gemeentecode meegegeven is uit die gemeente komt.
     * 
     * @param vanaf
     *            De datum tijd stempel is groter dan vanaf.
     * @param tot
     *            De datum tijd stempel is kleiner dan tot.
     * @param gemeentecode
     *            De gemeentecode als deze null is wordt dit niet meegenomen in de selectie.
     * @return Lijst met BerichtLogs die voldoen aan de criteria.
     */
    List<Long> findLaatsteBerichtLogAnrs(final Date vanaf, final Date tot, final String gemeentecode);

    /**
     * Slaat de meegegeven BerichtLog op in de database.
     * 
     * @param berichtLog
     *            de BerichtLog entiteit die moet worden opgeslagen in de database
     * @return de BerichtLog entiteit die opgeslagen is in de database
     */
    BerichtLog save(BerichtLog berichtLog);
}
