/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockException;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Test de technische flows rondom het unlocken.
 */
public class Uc202UnlockTest extends AbstractUc202Test {

    @Inject
    private LockService lockService;

    @Test
    public void fout() throws Exception {
        Mockito.reset(lockService);
        Mockito.when(lockService.verkrijgLockVoorAnummers(Matchers.anySetOf(Long.class), Matchers.anyLong())).thenReturn(42L);
        Mockito.doThrow(LockException.class).when(lockService).verwijderLock(42L);

        // Start
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        // Unlock (exception)

        // Lock (lock exception)
        signalHumanTask("end");
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }
}
