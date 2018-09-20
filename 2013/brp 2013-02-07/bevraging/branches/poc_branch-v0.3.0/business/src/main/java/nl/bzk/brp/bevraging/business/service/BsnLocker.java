/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;

import java.util.Collection;


/**
 * Creeert shared en exclusive locks op basis van BSN's.
 */

public interface BsnLocker {

    /**
     * Creert locks.
     *
     * @param berichtId Het bericht waarvoor de locks worden gecreeert
     * @param readBSNLocks De BSN's die een shared-lock moeten hebben
     * @param writeBSNLocks De BSN's die een exclusive-lock moeten hebben
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */

    boolean getLocks(Long berichtId, Collection<Long> readBSNLocks, Collection<Long> writeBSNLocks);

    /**
     * Geeft de gemaakte locks weer vrij.
     */
    void unLock();
}
