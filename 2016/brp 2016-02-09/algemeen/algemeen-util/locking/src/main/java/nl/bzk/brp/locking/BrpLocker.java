/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.locking;

import java.util.Collection;


/**
 * Cre&euml;ert een shared en exclusive lock op basis van database id.
 */
public interface BrpLocker {

    /**
     * Cre&euml;ert een lock.
     * Het cre&euml;eren van meerdere locks wordt ondersteund. Het is mogelijk om bijv. eerst persoon 1 te locken
     * en vervolgens persoon 1 & 2. De unlock methode zal alle gemaakte locks weer vrijgeven.
     *
     * @param dbIds             De database id's van elementen die een lock moeten krijgen
     * @param lockingElement    Het soort element waarvoor locking gewenst is
     * @param lockingMode       De locking mode die gewenst is
     * @param timeoutInSeconden geeft aan hoe lang er max. gewacht moet worden om het lock te verkrijgen
     * @return een boolean die aangeeft of het cre&euml;eren van het lock succesvol is uitgevoerd (true) of niet (false)
     * @throws BrpLockerExceptie wanneer er binnen de gestelde timeoutInSeconden periode geen lock verkregen kon worden
     */
    boolean lock(Collection<Integer> dbIds, LockingElement lockingElement, LockingMode lockingMode,
            Integer timeoutInSeconden)
        throws BrpLockerExceptie;

    /**
     * Controleer of het lock nog aanwezig is.
     *
     * @return een boolean die aangeeft of het lock aanwezig is (true) of niet (false)
     */
    boolean isLockNogAanwezig();

    /**
     * Geeft de gemaakte locks weer vrij.
     */
    void unLock();
}
