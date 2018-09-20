/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Test de happy flow.
 */
public class Uc811Test extends AbstractUc811Test {

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
    }

    @Test
    public void happyFlow() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0717", 1231231234L));

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
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertTrue(synchroniseerNaarBrpVerzoek.isGezaghebbendBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowMetVerwijzing() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());
        Assert.assertEquals("0599", lq01Bericht.getDoelGemeente());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLf01Bericht(lq01Bericht, Lf01Bericht.Foutreden.V, "0429"));

        // Beheerderkeuze: haal op via verwijs gemeente
        signalHumanTask("vraagViaVerwijsGegevens");

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());
        Assert.assertEquals("0429", lq01Bericht.getDoelGemeente());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertTrue(synchroniseerNaarBrpVerzoek.isGezaghebbendBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowMetVerhuizing() {
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
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, "proc-123123", "0518"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertTrue(synchroniseerNaarBrpVerzoek.isGezaghebbendBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.VERVANGEN, null));

        // Deblokkeren
        controleerBerichten(0, 0, 1);
        final DeblokkeringVerzoekBericht deblokkeringVerzoek = getBericht(DeblokkeringVerzoekBericht.class);
        Assert.assertNotNull(deblokkeringVerzoek.getMessageId());
        Assert.assertEquals("1231231234", deblokkeringVerzoek.getANummer());
        Assert.assertEquals("0599", deblokkeringVerzoek.getGemeenteRegistratie());
        Assert.assertEquals("proc-123123", deblokkeringVerzoek.getProcessId());
        signalSync(maakDeblokkeringAntwoordBericht(deblokkeringVerzoek));

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowGenegeerd() {
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
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, "proc-123123", "0518"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertTrue(synchroniseerNaarBrpVerzoek.isGezaghebbendBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.GENEGEERD, null));

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowOngeldigVerhuizing() {
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
        BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP, "proc-456456", "0518"));

        // Retry
        signalHumanTask("restartAtBlokkeringInfo");

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP, "proc-456456", "0518"));

        // End
        signalHumanTask("endWithoutPf03");

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowOngeldigeInput() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0123", 1231231234L));

        // Opnieuw proberen
        signalHumanTask("restartAtControleren");

        // End
        signalHumanTask("end");

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowOngeldigeLa01ZonderPf03() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Maak een La01 bericht en signal het kanaal VOSPG.
        lq01Bericht.setANummer("9876543210");
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Retry
        signalHumanTask("restartAtVragen");
        controleerBerichten(0, 1, 0);
        getBericht(Lq01Bericht.class);
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // End
        signalHumanTask("endWithoutPf03");

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowOngeldigeLa01MetPf03() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Maak een La01 bericht en signal het kanaal VOSPG.
        lq01Bericht.setANummer("9876543210");
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Retry
        signalHumanTask("restartAtVragen");
        controleerBerichten(0, 1, 0);
        getBericht(Lq01Bericht.class);
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // End
        signalHumanTask("end");

        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowAfgekeurdDoorSync() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.AFGEKEURD, null));

        // Retry
        signalHumanTask("restartAtLq01");

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.AFGEKEURD, null));

        // End
        signalHumanTask("end");

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeNieuw() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, maakPersoonslijsten()));

        // Retry
        signalHumanTask("restartAtLq01");

        // Lq01 bericht verwacht op kanaal VOSPG.
        controleerBerichten(0, 1, 0);
        lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOSPG.
        signalVospg(maakLa01Bericht(lq01Bericht, true));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, maakPersoonslijsten()));

        // Keuze
        signalHumanTask("opnemenAlsNieuwePl");

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(Boolean.TRUE, synchroniseerNaarBrpVerzoekNaKeuze.getOpnemenAlsNieuwePl());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeNegeren() {
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
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, maakPersoonslijsten()));

        // Lock (lock exception)
        signalHumanTask("negeren");

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeAfkeuren() {
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
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, maakPersoonslijsten()));

        // Lock (lock exception)
        signalHumanTask("afkeuren");
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeVervangen() {
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
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(
            synchroniseerNaarBrpVerzoek,
            StatusType.ONDUIDELIJK,
            maakPersoonslijsten(4445556667L, 5556667778L, 6667778889L)));

        // Lock (lock exception)
        signalHumanTask("vervangAnummer5556667778");

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(Long.valueOf(5556667778L), synchroniseerNaarBrpVerzoekNaKeuze.getANummerTeVervangenPl());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.VERVANGEN, null));

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

}
