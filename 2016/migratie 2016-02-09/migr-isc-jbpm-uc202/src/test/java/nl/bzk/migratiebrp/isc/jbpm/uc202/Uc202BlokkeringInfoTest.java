/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Test de technische flows rondom het opvragen van de blokkerings info.
 */
public class Uc202BlokkeringInfoTest extends AbstractUc202Test {

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
        startProcess(maakLg01(1231231234L, 1231231234L, null, null, "0599", "0599"));
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void afbreken() {
        controleerBerichten(0, 0, 1);
        getBericht(BlokkeringInfoVerzoekBericht.class);

        // Herhalingen
        signalProcess("afbreken");

        // Beheerderskeuze: restart
        signalHumanTask("restartAtBlokkeringInfo");

        // Nog een keer
        controleerBerichten(0, 0, 1);
        getBericht(BlokkeringInfoVerzoekBericht.class);
        signalProcess("afbreken");

        // Beheerderskeuze: end
        checkVariabele("foutafhandelingFout", "uc202.blokkeringinfo.afgebroken");
        signalHumanTask("endWithoutPf03");
    }

    @Test
    public void foutiefBericht() {
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);

        // Onverwacht bericht
        final SyncBericht foutiefBericht = maakOnverwachtBericht(blokkeringInfoVerzoek);
        signalSync(foutiefBericht);

        // Beheerderskeuze: end
        checkVariabele("foutafhandelingFout", "uc202.blokkeringinfo.foutiefbericht");
        signalHumanTask("endWithoutPf03");
    }

    @Test
    public void foutiefBeeindigen() {
        controleerBerichten(0, 0, 1);
        getBericht(BlokkeringInfoVerzoekBericht.class);

        // Forceer timeout om naar beheerderskeuze te gaan
        signalProcess("afbreken");

        // Lock (lock exception)
        signalHumanTask("end");

        // Verwacht twee output berichten (pf03 en vb01) om de lg01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);
    }

}
