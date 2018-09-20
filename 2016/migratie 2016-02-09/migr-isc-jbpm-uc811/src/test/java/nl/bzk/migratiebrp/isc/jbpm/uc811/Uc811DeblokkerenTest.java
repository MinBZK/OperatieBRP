/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
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
public class Uc811DeblokkerenTest extends AbstractUc811Test {

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
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(
            maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, "proc-123123", "0518"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertTrue(synchroniseerNaarBrpVerzoek.isGezaghebbendBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.VERVANGEN, null));
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

        // Beheerderkeuze: restart
        signalHumanTask("restartAtDeblokkeren");

        // Nog een keer
        controleerBerichten(0, 0, 1);
        getBericht(DeblokkeringVerzoekBericht.class);
        signalProcess("afbreken");

        // Beheerderkeuze: end
        checkVariabele("foutafhandelingFout", "uc811.deblokkeren.afgebroken");
        signalHumanTask("end");
    }

    @Test
    public void foutiefBericht() {
        controleerBerichten(0, 0, 1);
        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht = getBericht(DeblokkeringVerzoekBericht.class);

        // Ongeldig bericht
        final SyncBericht ongeldigBericht = maakOnverwachtBericht(deblokkeringVerzoekBericht);
        signalSync(ongeldigBericht);

        // Beheerderkeuze: end
        checkVariabele("foutafhandelingFout", "uc811.deblokkeren.foutiefbericht");
        signalHumanTask("end");
    }
}
