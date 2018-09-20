/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.service;

import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;

/**
 * Service om logging te bewerken.
 */
public interface LoggingService {

    /**
     * Persisteert een init vulling log.
     * 
     * @param log
     *            De log die opgeslagen moet worden.
     */
    void persisteerInitVullingLog(final InitVullingLog log);

    /**
     * Create and store diff with lo3bericht and brpbericht in the log.
     * 
     * @param log
     *            Log with the messages.
     */
    void createAndStoreDiff(final InitVullingLog log);

    /**
     * Find log by anummer.
     * 
     * @param anummer
     *            of the log to find.
     * @return Init vulling log regel.
     */
    InitVullingLog findLog(final Long anummer);

    /**
     * Zoekt logs op tussen vanaf en tot met de opgegeven gemeentecode.
     * 
     * @param vanaf
     *            De datum tijd stempel valt na deze datum.
     * @param tot
     *            De datum tijd stempel valt voor deze datum.
     * @param gemeentecode
     *            De gemeentecode waarop geselecteerd moet worden.
     * @return Lijst met anummers.
     */
    List<Long> findLogs(Date vanaf, Date tot, String gemeentecode);
}
