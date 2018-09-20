/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Test de technische flows rondom het deblokkeren.
 */
public class Uc202DeblokkerenTest extends AbstractUc202Test {

    @Inject
    private LockService lockService;

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void startProces() throws Exception {
        Mockito.reset(lockService);
        Mockito.when(lockService.verkrijgLockVoorAnummers(Matchers.anySetOf(Long.class), Matchers.anyLong())).thenReturn(5678L);

        // Start
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        signalSync(
            maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, "proc-123123", "0518"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void afbreken() {
        controleerBerichten(0, 0, 1);
        getBericht(DeblokkeringVerzoekBericht.class);

        // Afbreken
        signalProcess("afbreken");

        // Beheerderskeuze: restart
        signalHumanTask("restartAtDeblokkeren");

        // Nog een keer
        controleerBerichten(0, 0, 1);
        getBericht(DeblokkeringVerzoekBericht.class);

        signalProcess("afbreken");

        // Beheerderskeuze: end
        checkVariabele("foutafhandelingFout", "uc202.deblokkeren.afgebroken");
        signalHumanTask("end");
    }

    @Test
    public void foutiefBericht() {
        controleerBerichten(0, 0, 1);
        final DeblokkeringVerzoekBericht deblokkeringVerzoek = getBericht(DeblokkeringVerzoekBericht.class);

        // Onverwacht bericht
        final SyncBericht foutiefBericht = maakOnverwachtBericht(deblokkeringVerzoek);
        signalSync(foutiefBericht);

        // Beheerderskeuze: end
        checkVariabele("foutafhandelingFout", "uc202.deblokkeren.foutiefbericht");
        signalHumanTask("end");
    }
}
