/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import java.util.Collection;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.locking.LockingElement;
import nl.bzk.brp.locking.LockingMode;

/**
 * Dummy locker.
 */
public final class DummyLocker implements BrpLocker {

    @Override
    public boolean lock(
        final Collection<Integer> dbIds,
        final LockingElement lockingElement,
        final LockingMode lockingMode,
        final Integer timeoutInSeconden) throws BrpLockerExceptie
    {
        return true;
    }

    @Override
    public boolean isLockNogAanwezig() {
        return true;
    }

    @Override
    public void unLock() {
    }

}
