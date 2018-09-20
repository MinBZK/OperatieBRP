/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockException;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockService;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockedException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Test de technische flows rondom de a-nummer lock.
 *
 * Nota: herhalingen van de lock kunnen niet los getest worden omdat deze eindeloos herhalen.
 */
public class Uc202LockTest extends AbstractUc202Test {

    @Inject
    private LockService lockService;

    @Test
    public void herhalingOpReedsGelockedEnFout() throws Exception {
        Mockito.reset(lockService);
        Mockito.when(lockService.verkrijgLockVoorAnummers(Matchers.anySetOf(Long.class), Matchers.anyLong())).thenThrow(
            LockedException.class,
            LockException.class);

        // Start
        startProcess(maakLg01(1231231234L, 1231231234L, null, null, "0599", "0599"));

        // Lock (locked exception)
        signalProcess("timeout");

        // Lock (lock exception)
        signalHumanTask("end");

        // Verwacht twee output berichten (pf03 en vb01) om de lg01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);

        Assert.assertTrue(processEnded());
    }
}
