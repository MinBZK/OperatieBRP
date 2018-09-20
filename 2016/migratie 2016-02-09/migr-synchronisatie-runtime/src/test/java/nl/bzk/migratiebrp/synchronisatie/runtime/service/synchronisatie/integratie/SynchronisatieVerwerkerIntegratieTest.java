/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SynchronisatieVerwerkerIntegratieTest extends AbstractSynchronisatieServiceIntegratieTest {

    @Inject
    @Named("synchroniseerNaarBrpService")
    private SynchronisatieBerichtService<SynchroniseerNaarBrpVerzoekBericht, SynchroniseerNaarBrpAntwoordBericht> subject;

    @Inject
    @Named("syncParameters")
    private SyncParameters syncParameters;

    @Before
    public void setupSyncParameters() {
        syncParameters.setInitieleVulling(false);
    }

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    private void resetLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testRegulierToevoegen() throws Exception {
        System.out.println("###\n###\n### testRegulierToevoegen - START\n###\n###");
        // Setup
        final Lo3Persoonslijst lo3Persoonslijst = leesPl("SynchronisatieVerwerkerIntegratieTest-Regulier-Toevoegen.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("sync-test-reg-toevoegen");
        verzoek.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3Persoonslijst)));
        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Verify
        System.out.println("\n\ntestRegulierToevoegen.melding:\n" + antwoord.getMelding() + "\n\n");
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getStatus());

        Assert.assertTrue(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));
        System.out.println("###\n###\n### testRegulierToevoegen - EIND\n###\n###");
    }

    @Test
    public void testRegulierWijzigen() throws Exception {
        System.out.println("###\n###\n### testRegulierWijzigen - START\n###\n###");
        // Setup
        final Lo3Persoonslijst lo3PersoonslijstOud = leesPl("SynchronisatieVerwerkerIntegratieTest-Regulier-Wijzigen-Oud.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));
        persisteerPersoonslijst(lo3PersoonslijstOud);
        Assert.assertTrue(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));

        final Lo3Persoonslijst lo3PersoonslijstNieuw = leesPl("SynchronisatieVerwerkerIntegratieTest-Regulier-Wijzigen-Nieuw.xls");
        Assert.assertEquals(lo3PersoonslijstNieuw.getActueelAdministratienummer(), lo3PersoonslijstOud.getActueelAdministratienummer());

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("sync-test-reg-wijzigen");
        verzoek.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3PersoonslijstNieuw)));
        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Verify
        System.out.println("\n\ntestRegulierWijzigen.melding:\n" + antwoord.getMelding() + "\n\n");
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.VERVANGEN, antwoord.getStatus());

        Assert.assertTrue(komtAnummerActueelVoor(lo3PersoonslijstNieuw.getActueelAdministratienummer()));
        final BrpPersoonslijst result = leesBrpPersoonslijst(lo3PersoonslijstNieuw.getActueelAdministratienummer());
        vergelijkPersoonslijsten(lo3PersoonslijstNieuw, result);
        System.out.println("###\n###\n### testRegulierWijzigen - EIND\n###\n###");
    }

    @Test
    public void testRegulierVerhuizen() throws Exception {
        System.out.println("###\n###\n### testRegulierVerhuizen - START\n###\n###");
        // Setup
        final Lo3Persoonslijst lo3PersoonslijstOud = leesPl("SynchronisatieVerwerkerIntegratieTest-Regulier-Verhuizen-Oud.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));
        persisteerPersoonslijst(lo3PersoonslijstOud);
        Assert.assertTrue(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));

        final Lo3Persoonslijst lo3PersoonslijstNieuw = leesPl("SynchronisatieVerwerkerIntegratieTest-Regulier-Verhuizen-Nieuw.xls");
        Assert.assertEquals(lo3PersoonslijstNieuw.getActueelAdministratienummer(), lo3PersoonslijstOud.getActueelAdministratienummer());

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("sync-test-reg-verhuizen");
        verzoek.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3PersoonslijstNieuw)));
        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Verify
        System.out.println("\n\ntestRegulierVerhuizen.melding:\n" + antwoord.getMelding() + "\n\n");
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.VERVANGEN, antwoord.getStatus());

        Assert.assertTrue(komtAnummerActueelVoor(lo3PersoonslijstNieuw.getActueelAdministratienummer()));
        final BrpPersoonslijst result = leesBrpPersoonslijst(lo3PersoonslijstNieuw.getActueelAdministratienummer());
        vergelijkPersoonslijsten(lo3PersoonslijstNieuw, result);
        System.out.println("###\n###\n### testRegulierVerhuizen - EIND\n###\n###");
    }

    @Test
    public void testAfgekeurd() throws Exception {
        System.out.println("###\n###\n### testAfgekeurd - START\n###\n###");
        // Setup

        final Lo3Persoonslijst lo3Persoonslijst = leesPl("SynchronisatieVerwerkerIntegratieTest-Afgekeurd.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("sync-test-afgekeurd");
        verzoek.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3Persoonslijst)));
        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Verify
        System.out.println("\n\ntestAfgekeurd.melding:\n" + antwoord.getMelding() + "\n\n");
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.AFGEKEURD, antwoord.getStatus());

        Assert.assertFalse(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));
        System.out.println("###\n###\n### testAfgekeurd - EIND\n###\n###");
    }

    @Test
    public void testAnummerWijziging() throws Exception {
        System.out.println("###\n###\n### testAnummerWijziging - START\n###\n###");
        // Setup
        final Lo3Persoonslijst lo3PersoonslijstOud = leesPl("SynchronisatieVerwerkerIntegratieTest-AnummerWijzigen-Oud.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));
        persisteerPersoonslijst(lo3PersoonslijstOud);
        Assert.assertTrue(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));

        final Lo3Persoonslijst lo3PersoonslijstNieuw = leesPl("SynchronisatieVerwerkerIntegratieTest-AnummerWijzigen-Nieuw.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3PersoonslijstNieuw.getActueelAdministratienummer()));

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("sync-test-anummer");
        verzoek.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3PersoonslijstNieuw)));
        verzoek.setAnummerWijziging(true);
        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Verify
        System.out.println("\n\ntestAnummerWijziging.melding:\n" + antwoord.getMelding() + "\n\n");
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.VERVANGEN, antwoord.getStatus());

        Assert.assertFalse(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));
        Assert.assertTrue(komtAnummerActueelVoor(lo3PersoonslijstNieuw.getActueelAdministratienummer()));
        final BrpPersoonslijst result = leesBrpPersoonslijst(lo3PersoonslijstNieuw.getActueelAdministratienummer());
        vergelijkPersoonslijsten(lo3PersoonslijstNieuw, result);
        System.out.println("###\n###\n### testAnummerWijziging - EIND\n###\n###");
    }

    /**
     * Testcase: aangeboden PL wordt als onduidelijk bestempeld (in reguliere toevoeging) omdat er meerdere a-nummers in
     * de historie zitten. Daarna beheerderskeuze om toch op te nemen.
     */
    @Test
    public void testBeheerderNieuw() throws Exception {
        System.out.println("###\n###\n### testBeheerderNieuw - START\n###\n###");
        // Setup
        final Lo3Persoonslijst lo3Persoonslijst = leesPl("SynchronisatieVerwerkerIntegratieTest-Beheerderskeuze-Nieuw.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));

        System.out.println("###\n###\n### testBeheerderNieuw - REGULIER\n###\n###");
        // Aanbieden: regulier
        final SynchroniseerNaarBrpVerzoekBericht verzoek1 = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek1.setMessageId("sync-test-beh-nieuw-1");
        verzoek1.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3Persoonslijst)));
        final SynchroniseerNaarBrpAntwoordBericht antwoord1 = subject.verwerkBericht(verzoek1);

        System.out.println("###\n###\n### testBeheerderNieuw - VERIFY: ONDUIDELIJK\n###\n###");
        // Verify: onduidelijk
        System.out.println("\n\ntestBeheerderNieuw1.melding:\n" + antwoord1.getMelding() + "\n\n");
        Assert.assertEquals(verzoek1.getMessageId(), antwoord1.getCorrelationId());
        Assert.assertEquals(StatusType.ONDUIDELIJK, antwoord1.getStatus());
        Assert.assertFalse(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));

        resetLogging();

        System.out.println("###\n###\n### testBeheerderNieuw - NIEUW\n###\n###");
        // Aanbieden: beheerderskeuze nieuw
        final SynchroniseerNaarBrpVerzoekBericht verzoek2 = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek2.setMessageId("sync-test-beh-nieuw-2");
        verzoek2.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3Persoonslijst)));
        verzoek2.setOpnemenAlsNieuwePl(true);
        final SynchroniseerNaarBrpAntwoordBericht antwoord2 = subject.verwerkBericht(verzoek2);

        System.out.println("###\n###\n### testBeheerderNieuw - VERIFY: TOEGEVOEGD\n###\n###");
        // Verify: toegevoegd
        System.out.println("\n\ntestBeheerderNieuw2.melding:\n" + antwoord2.getMelding() + "\n\n");
        Assert.assertEquals(verzoek2.getMessageId(), antwoord2.getCorrelationId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord2.getStatus());

        Assert.assertTrue(komtAnummerActueelVoor(lo3Persoonslijst.getActueelAdministratienummer()));
        final BrpPersoonslijst result = leesBrpPersoonslijst(lo3Persoonslijst.getActueelAdministratienummer());
        vergelijkPersoonslijsten(lo3Persoonslijst, result);
        System.out.println("###\n###\n### testBeheerderNieuw - EIND\n###\n###");
    }

    /**
     * Testcase: aangeboden PL wordt als onduidelijk bestempeld (in reguliere wijziging) de datumtijdstempel niet is
     * opgehoogd. Daarna beheerderskeuze om toch op te nemen.
     */
    @Test
    public void testBeheerderVervangen() throws Exception {
        System.out.println("###\n###\n### testBeheerderVervangen - START\n###\n###");
        // Setup
        final Lo3Persoonslijst lo3PersoonslijstOud = leesPl("SynchronisatieVerwerkerIntegratieTest-Beheerderskeuze-Vervangen-Oud.xls");
        Assert.assertFalse(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));
        persisteerPersoonslijst(lo3PersoonslijstOud);
        Assert.assertTrue(komtAnummerActueelVoor(lo3PersoonslijstOud.getActueelAdministratienummer()));

        final Lo3Persoonslijst lo3PersoonslijstNieuw = leesPl("SynchronisatieVerwerkerIntegratieTest-Beheerderskeuze-Vervangen-Nieuw.xls");

        System.out.println("###\n###\n### testBeheerderVervangen - REGULIER\n###\n###");
        // Aanbieden: regulier
        final SynchroniseerNaarBrpVerzoekBericht verzoek1 = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek1.setMessageId("sync-test-beh-verv-1");
        verzoek1.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3PersoonslijstNieuw)));
        final SynchroniseerNaarBrpAntwoordBericht antwoord1 = subject.verwerkBericht(verzoek1);

        System.out.println("###\n###\n### testBeheerderVervangen - VERIFY: ONDUIDELIJK\n###\n###");
        // Verify: onduidelijk
        System.out.println("\n\ntestBeheerderVervang1.melding:\n" + antwoord1.getMelding() + "\n\n");
        Assert.assertEquals(verzoek1.getMessageId(), antwoord1.getCorrelationId());
        Assert.assertEquals(StatusType.ONDUIDELIJK, antwoord1.getStatus());
        final BrpPersoonslijst result1 = leesBrpPersoonslijst(lo3PersoonslijstNieuw.getActueelAdministratienummer());
        vergelijkPersoonslijsten(lo3PersoonslijstOud, result1);

        resetLogging();

        System.out.println("###\n###\n### testBeheerderVervangen - VERVANGEN\n###\n###");
        // Aanbieden: beheerderskeuze vervangen
        final SynchroniseerNaarBrpVerzoekBericht verzoek2 = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek2.setMessageId("sync-test-beh-verv-2");
        verzoek2.setLo3BerichtAsTeletexString(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3PersoonslijstNieuw)));
        verzoek2.setANummerTeVervangenPl(lo3PersoonslijstNieuw.getActueelAdministratienummer());
        final SynchroniseerNaarBrpAntwoordBericht antwoord2 = subject.verwerkBericht(verzoek2);

        System.out.println("###\n###\n### testBeheerderVervangen - VERIFY: VERVANGEN\n###\n###");
        // Verify: toegevoegd
        System.out.println("\n\ntestBeheerderVervang2.melding:\n" + antwoord2.getMelding() + "\n\n");
        Assert.assertEquals(verzoek2.getMessageId(), antwoord2.getCorrelationId());
        Assert.assertEquals(StatusType.VERVANGEN, antwoord2.getStatus());

        final BrpPersoonslijst result2 = leesBrpPersoonslijst(lo3PersoonslijstNieuw.getActueelAdministratienummer());
        vergelijkPersoonslijsten(lo3PersoonslijstNieuw, result2);
        System.out.println("###\n###\n### testBeheerderVervangen - EIND\n###\n###");
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void vergelijkPersoonslijsten(final Lo3Persoonslijst lo3, final BrpPersoonslijst brp) {
        assertLo3BrpEquals(lo3.getInschrijvingStapel().getLo3ActueelVoorkomen().getInhoud().getVersienummer(), brp.getInschrijvingStapel()
                                                                                                                  .getActueel()
                                                                                                                  .getInhoud()
                                                                                                                  .getVersienummer());
        // Assert.assertEquals(20130102114500L, brp.getInschrijvingStapel().getActueel().getInhoud()
        // .getDatumtijdstempel().getDatumTijd());
    }

    private void assertLo3BrpEquals(final Lo3Integer lo3Integer, final BrpLong brpLong) {
        Assert.assertEquals(Long.valueOf(lo3Integer.getWaarde()), brpLong.getWaarde());
        Assert.assertEquals(lo3Integer.getOnderzoek(), brpLong.getOnderzoek());
    }
}
