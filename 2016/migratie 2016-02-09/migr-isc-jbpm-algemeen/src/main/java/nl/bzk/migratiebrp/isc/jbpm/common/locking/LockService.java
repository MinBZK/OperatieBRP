/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.locking;

import java.util.Set;

/**
 * Locking service.
 * 
 * De a-nummer lock wordt gebruikt om afhandelingen van persoonslijsten 'achter elkaar' te doen en niet tegelijkertijd.
 */
public interface LockService {

    /**
     * Verkrijg een lock.
     * 
     * @param aNummers
     *            lijst van anummers
     * @param processInstanceId
     *            Het id van de procesinstantie die de lock aanmaakt.
     * @return lock id
     * @throws LockException
     *             bij onverwachte fouten
     * @throws LockedException
     *             als (een van) de anummers reeds gelocked is
     */
    Long verkrijgLockVoorAnummers(Set<Long> aNummers, Long processInstanceId) throws LockException, LockedException;

    /**
     * Verwijder lock.
     * 
     * @param lockId
     *            lock id
     * @throws LockException
     *             bij onverwachte fouten
     */
    void verwijderLock(Long lockId) throws LockException;
}
