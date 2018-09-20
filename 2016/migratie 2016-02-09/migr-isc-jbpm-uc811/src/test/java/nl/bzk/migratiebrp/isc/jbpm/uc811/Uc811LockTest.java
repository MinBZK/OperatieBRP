/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
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
public class Uc811LockTest extends AbstractUc811Test {

    @Inject
    private LockService lockService;

    @Test
    public void herhalingOpReedsGelockedEnFout() throws Exception {
        Mockito.reset(lockService);
        Mockito.when(lockService.verkrijgLockVoorAnummers(Matchers.anySetOf(Long.class), Matchers.anyLong())).thenThrow(
            LockedException.class,
            LockException.class);

        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Lock (locked exception)
        signalProcess("timeout");

        // Lock (lock exception)
        signalHumanTask("endWithoutPf03");

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }
}
