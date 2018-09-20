/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.job;

/**
 * Service to execute timers and jobs in JBPM.
 */
public interface ExecuteService {

    /**
     * Timer uitvoeren.
     * 
     * @param timerId
     *            timer id
     */
    void executeTimer(long timerId);

    /**
     * Job uitvoeren.
     * 
     * @param jobId
     *            job id
     */
    void executeJob(long jobId);
}
