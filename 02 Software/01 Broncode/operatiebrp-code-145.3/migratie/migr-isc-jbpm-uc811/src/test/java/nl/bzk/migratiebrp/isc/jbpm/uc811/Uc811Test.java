/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BeheerdersKeuzeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.uc202.MaakBeheerderskeuzesAction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de happy flow.
 */
public class Uc811Test extends AbstractUc811Test {

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Test
    public void happyFlow() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0717", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());
        Assert.assertEquals("071701", lq01Bericht.getDoelPartijCode());
        Assert.assertEquals("199902", lq01Bericht.getBronPartijCode());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(TypeSynchronisatieBericht.LA_01, synchroniseerNaarBrpVerzoek.getTypeBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowMetVerwijzing() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());
        Assert.assertEquals("059901", lq01Bericht.getDoelPartijCode());
        Assert.assertEquals("199902", lq01Bericht.getBronPartijCode());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLf01Bericht(lq01Bericht, Lf01Bericht.Foutreden.V, "0429"));

        // Beheerderkeuze: haal op via verwijs gemeente
        signalHumanTask("vraagViaVerwijsGegevens");

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());
        Assert.assertEquals("042901", lq01Bericht.getDoelPartijCode());
        Assert.assertEquals("199902", lq01Bericht.getBronPartijCode());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(TypeSynchronisatieBericht.LA_01, synchroniseerNaarBrpVerzoek.getTypeBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowGenegeerd() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(TypeSynchronisatieBericht.LA_01, synchroniseerNaarBrpVerzoek.getTypeBericht());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.GENEGEERD, null));

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

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Maak een La01 bericht en signal het kanaal VOISC.
        lq01Bericht.setANummer("9876543210");
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Retry
        signalHumanTask("restartAtVragen");
        controleerBerichten(0, 1, 0);
        getBericht(Lq01Bericht.class);
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // End
        signalHumanTask("endWithoutPf03");

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowOngeldigeLa01MetPf03() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Maak een La01 bericht en signal het kanaal VOISC.
        lq01Bericht.setANummer("9876543210");
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Retry
        signalHumanTask("restartAtVragen");
        controleerBerichten(0, 1, 0);
        getBericht(Lq01Bericht.class);
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // End
        signalHumanTask("end");

        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowAfgekeurdDoorSync() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.AFGEKEURD, null));

        // Retry
        signalHumanTask("restartAtLq01");

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

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

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Retry
        signalHumanTask("restartAtLq01");

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Keuze
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_NIEUW);

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(BeheerdersKeuzeType.TOEVOEGEN, synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getKeuze());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeAfbreken() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Beheerderskeuze
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_AFBREKEN);

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeVervangen() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK,
                maakKandidaten("1445556667", "1556667778", "1667778889")));

        // Beheerderskeuze
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_VERVANGEN_PREFIX + "1556667778");

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(BeheerdersKeuzeType.VERVANGEN, synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getKeuze());
        Assert.assertEquals(Long.valueOf(1556667778), synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getTeVervangenPersoonId());
        Assert.assertEquals(3, synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getKandidaat().size());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.VERVANGEN, null));

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeAfKeuren() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Beheerderskeuze
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_AFKEUREN);

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(BeheerdersKeuzeType.AFKEUREN, synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getKeuze());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.AFGEKEURD, null));

        controleerBerichten(0, 0, 0);

        // End
        signalHumanTask("endWithoutPf03");

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeNegeren() {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);
        Assert.assertEquals("1231231234", lq01Bericht.getANummer());

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Beheerderskeuze
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_NEGEREN);

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(BeheerdersKeuzeType.NEGEREN, synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getKeuze());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.GENEGEERD, null));

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

}
