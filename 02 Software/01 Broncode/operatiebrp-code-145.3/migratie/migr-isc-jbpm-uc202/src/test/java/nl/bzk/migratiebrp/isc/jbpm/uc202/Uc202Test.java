/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.Collection;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BeheerdersKeuzeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.TaakGerelateerdeInformatieDao;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test de happy flow.
 */
public class Uc202Test extends AbstractUc202Test {

    @Autowired
    private TaakGerelateerdeInformatieDao taakGerelateerdeInformatieDao;

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
//        setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Test
    public void happyFlow() throws Exception {
        final String aNummer = "1231231234";
        // Start
        startProcess(maakLg01(aNummer, aNummer, null, null, "059901", "0599"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowOngeldigeInput() {
        // Start
        startProcess(maakLg01("1231231234", "9879879879", null, null, "0599", "0599"));

        // Verwacht een output berichten (pf03) om de lg01 cyclus netjes af te ronden
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowAfgekeurdDoorSync() {
        // Start
        final String aNummer = "1231231234";
        startProcess(maakLg01(aNummer, aNummer, null, null, "059901", "0599"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.AFGEKEURD, null));

        // Verwacht een output berichten (pf03) om de lg01 cyclus netjes af te ronden
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeNieuw() {
        // Start
        final String aNummer = "1231231234";
        startProcess(maakLg01(aNummer, aNummer, null, null, "059901", "0599"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Beheerderskeuze: NIEUW
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
        final String aNummer = "1231231234";
        startProcess(maakLg01(aNummer, aNummer, null, null, "059901", "0599"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Lock (lock exception)
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_AFBREKEN);

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeAfkeuren() {
        // Start
        final String aNummer = "1231231234";
        startProcess(maakLg01(aNummer, aNummer, null, null, "059901", "0599"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        // Beheerderskeuze: NIEUW
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_AFKEUREN);

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(BeheerdersKeuzeType.AFKEUREN, synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getKeuze());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.AFGEKEURD, null));

        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeNegeren() {
        // Start
        final String aNummer = "1231231234";
        startProcess(maakLg01(aNummer, aNummer, null, null, "059901", "0599"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);

        Collection<TaskInstance> gekoppeldeTaken = taakGerelateerdeInformatieDao.zoekOpAdministratienummers(aNummer);
        Assert.assertTrue(gekoppeldeTaken.isEmpty());

        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null));

        Assert.assertTrue(processHumanTask());

        gekoppeldeTaken = taakGerelateerdeInformatieDao.zoekOpAdministratienummers(aNummer);
        Assert.assertFalse(gekoppeldeTaken.isEmpty());

        // Beheerderskeuze: NIEUW
        signalHumanTask(MaakBeheerderskeuzesAction.KEUZE_NEGEREN);

        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekNaKeuze = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(BeheerdersKeuzeType.NEGEREN, synchroniseerNaarBrpVerzoekNaKeuze.getBeheerderKeuze().getKeuze());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.GENEGEERD, null));

        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeVervangen() {
        // Start
        final String aNummer = "1231231234";
        final String burgerServicenummer = "415121237";
        startProcess(maakLg01(aNummer, aNummer, null, null, burgerServicenummer, "059901", "0599"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(
                synchroniseerNaarBrpVerzoek,
                StatusType.ONDUIDELIJK,
                maakKandidaten("1445556667", "1556667778", "1667778889")));

        // Beheerderskeuze: VERVANG
        // pl id is gelijk aan a-nummer door methode maakKandidaten()
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

}
