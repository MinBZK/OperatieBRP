/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.locking;

import java.util.Set;

/**
 * Locking data access.
 */
public interface LockingDao {

    /**
     * Maak een lock.
     * 
     * @param processInstanceId
     *            process instance id
     * @param aNummerLijst
     *            lijst van te locken a-nummers
     * @return lock id
     */
    Long lock(long processInstanceId, Set<Long> aNummerLijst);

    /**
     * Verwijder een lock.
     * 
     * @param lockId
     *            lock id
     */
    void unlock(long lockId);

}
