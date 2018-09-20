/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
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
public class Uc202Test extends AbstractUc202Test {

    @Inject
    private LockService lockService;

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void setupLock() throws Exception {
        Mockito.reset(lockService);
        Mockito.when(lockService.verkrijgLockVoorAnummers(Matchers.anySetOf(Long.class), Matchers.anyLong())).thenReturn(5678L);
    }

    @Test
    public void happyFlowZonderVerhuizing() throws Exception {
        final Long aNummer = 1231231234L;
        // Start
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowMetVerhuizing() throws Exception {
        // Start
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertNotNull(blokkeringInfoVerzoek.getMessageId());
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, "proc-123123", "0518"));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertNotNull(synchroniseerNaarBrpVerzoek.getMessageId());
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null));

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
    public void badFlowOngeldigeInput() {
        // Start
        startProcess(maakLg01(1231231234L, 9879879879L, null, null, "0599", "0599"));

        // Verwacht twee output berichten (pf03 en vb01) om de lg01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowBlokkeringFout() {
        // Start
        startProcess(maakLg01(1231231234L, 1231231234L, null, null, "0599", "0599"));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP, "proc-456456", "0518"));

        // Verwacht twee output berichten (pf03 en vb01) om de lg01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowAfgekeurdDoorSync() {
        // Start
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

        // Blokkering info opvragen
        controleerBerichten(0, 0, 1);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = getBericht(BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        signalSync(maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null));

        // Sync naar BRP
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalSync(maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.AFGEKEURD, null));

        // Verwacht twee output berichten (pf03 en vb01) om de lg01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void beheerderkeuzeNieuw() {
        // Start
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

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
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

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
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

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
        final Long aNummer = 1231231234L;
        startProcess(maakLg01(aNummer, aNummer, null, null, "0599", "0599"));

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

    @Override
    protected Lo3Persoonslijst maakPersoonslijst(final Long aNummerInhoud, final Long vorigANummerInhoud, final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                                                                                                          null,
                                                                                                          "Jan",
                                                                                                          null,
                                                                                                          null,
                                                                                                          "Jansen",
                                                                                                          19700101,
                                                                                                          "0518",
                                                                                                          "6030",
                                                                                                          "M",
                                                                                                          vorigANummerInhoud,
                                                                                                          null,
                                                                                                          "E"),
                                                                               Lo3StapelHelper.lo3Akt(1),
                                                                               Lo3StapelHelper.lo3His(19700101),
                                                                               new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(gemeenteInhoud,
                                                                                                                        1970101,
                                                                                                                        1970101,
                                                                                                                        "Straat",
                                                                                                                        15,
                                                                                                                        "9876AA",
                                                                                                                        "I"),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19700101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }
}
