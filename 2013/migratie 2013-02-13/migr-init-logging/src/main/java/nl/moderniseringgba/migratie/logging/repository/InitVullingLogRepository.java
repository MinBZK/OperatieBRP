/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.repository;

import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;

/**
 * Init vulling log repository.
 */
public interface InitVullingLogRepository {

    /**
     * Maak een log regel.
     * 
     * @param log
     *            de InitVullingLog
     */
    void persistLog(final InitVullingLog log);

    /**
     * Queried een InitVullingLog entiteit voor het meegegeven a-nummer. Deze methode geeft precies 1 InitVullingLog als
     * resultaat. Worden er geen of meerdere InitVullingLog entiteiten voor het meegegeven a-nummer gevonden naar wordt
     * er een runtime exception gegooid.
     * 
     * @param anummer
     *            het anummer
     * @return een InitVullingLog
     */
    InitVullingLog findLog(final Long anummer);

    /**
     * Zoekt een log met datum tijd stempel tussen vanaf en tot.
     * 
     * @param vanaf
     *            Datum waar de datum tijd stempel na moet liggen.
     * @param tot
     *            Datum waar de datum tijd stempel voor moet liggen.
     * @param gemeentecode
     *            Gemeentecode van de inschrijvingsgemeente van de PL-en.
     * @return Lijst van anummers.
     */
    List<Long> findLogs(final Date vanaf, final Date tot, final String gemeentecode);
}
