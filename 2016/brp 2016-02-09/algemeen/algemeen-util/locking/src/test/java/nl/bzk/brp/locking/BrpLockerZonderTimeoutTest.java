/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.locking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.inject.Inject;

import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Category(OverslaanBijInMemoryDatabase.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:locking-beans-test.xml")
public class BrpLockerZonderTimeoutTest {
    private static final Integer DEFAULT_TIMEOUT = 2;

    @Inject
    private BrpLocker brpLocker;

    @After
    public void cleanup() {
        brpLocker.unLock();
    }

    @Test
    public void lockingMetEenTimeout() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, DEFAULT_TIMEOUT));
        assertTrue(brpLocker.isLockNogAanwezig());
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }

    @Test
    public void lockingZonderEenTimeout() throws BrpLockerExceptie {
        assertTrue(brpLocker.lock(Arrays.asList(1), LockingElement.PERSOON, LockingMode.SHARED, null));
        assertTrue(brpLocker.isLockNogAanwezig());
        brpLocker.unLock();
        assertFalse(brpLocker.isLockNogAanwezig());
    }
}
