/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.bzk.migratiebrp.ggo.viewer.service.BcmService;
import nl.bzk.migratiebrp.ggo.viewer.service.LeesService;
import nl.bzk.migratiebrp.ggo.viewer.service.ViewerService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class FileUploadVerwerkerTest {
    @Mock
    private ViewerService viewerService;
    @Mock
    private LeesService leesService;
    @Mock
    private BcmService bcmService;
    @Inject
    @InjectMocks
    private FileUploadVerwerker fileUploadVerwerker;
    @Inject
    private JsonFormatter jsonFormatter;

    private final FoutMelder foutMelder = new FoutMelder();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVerwerkFileUploadSucces() throws Exception {
        final String filename = "Omzetting.txt";
        final List<List<Lo3CategorieWaarde>> lo3CatWaarde = Lo3PersoonslijstTestHelper.retrieveLo3CategorieWaarden(filename, new FoutMelder());
        final List<Lo3Persoonslijst> lo3Persoonslijsten = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten(filename, new FoutMelder());
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst(null);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final List<GgoVoorkomenVergelijking> matches = new ArrayList<>();

        Mockito.when(leesService.leesBestand(Matchers.anyString(), Matchers.any(byte[].class), Matchers.any(FoutMelder.class))).thenReturn(lo3CatWaarde);
        Mockito.when(leesService.parsePersoonslijstMetSyntaxControle(Matchers.any(List.class), Matchers.any(FoutMelder.class))).thenReturn(
            lo3Persoonslijsten.get(0));
        Mockito.when(viewerService.verwerkPrecondities(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(
            lo3Persoonslijsten.get(0));
        Mockito.when(viewerService.converteerNaarBrp(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(brpPersoonslijst);
        Mockito.when(viewerService.converteerNaarEntityModel(Matchers.any(BrpPersoonslijst.class))).thenReturn(persoon);
        Mockito.when(viewerService.converteerTerug(Matchers.any(BrpPersoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(
            lo3Persoonslijsten.get(0));
        Mockito.when(bcmService.controleerDoorBCM(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(
            new ArrayList<GgoFoutRegel>());
        Mockito.when(
            viewerService.vergelijkLo3OrigineelMetTerugconversie(
                Matchers.any(Lo3Persoonslijst.class),
                Matchers.any(Lo3Persoonslijst.class),
                Matchers.any(FoutMelder.class))).thenReturn(matches);
        Mockito.when(
                   viewerService.voegLo3HerkomstToe(
                       Matchers.any(Lo3Persoonslijst.class),
                       Matchers.any(Lo3Persoonslijst.class),
                       Matchers.any(FoutMelder.class)))
               .thenReturn(lo3Persoonslijsten.get(0));

        final List<GgoPersoonslijstGroep> persoonslijstGroepen = fileUploadVerwerker.verwerkFileUpload("asdf", new byte[] {}, new FoutMelder());

        final ViewerResponse response = new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());
        final ResponseEntity<String> result = jsonFormatter.format(response);
        assertStructuurResultaat(result.getBody());
    }

    @Test
    public void testVerwerkFileUploadGeenPl() throws Exception {
        final String filename = "Omzetting01.xls";
        final byte[] file = IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream(filename));
        Mockito.when(leesService.leesBestand(Matchers.eq(filename), Matchers.eq(file), Matchers.any(FoutMelder.class))).thenReturn(null);

        final List<GgoPersoonslijstGroep> pls = fileUploadVerwerker.verwerkFileUpload(filename, file, foutMelder);
        Assert.assertEquals(0, pls.size());
    }

    @Test
    public void testVerwerkFileUploadSyntaxFail() throws Exception {
        final String filename = "Omzetting01.xls";
        final byte[] file = IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream(filename));
        final List<List<Lo3CategorieWaarde>> lo3CatWaarde = Lo3PersoonslijstTestHelper.retrieveLo3CategorieWaarden(filename, foutMelder);

        Mockito.when(leesService.leesBestand(Matchers.eq(filename), Matchers.eq(file), Matchers.any(FoutMelder.class))).thenReturn(lo3CatWaarde);
        Mockito.when(leesService.parsePersoonslijstMetSyntaxControle(lo3CatWaarde.get(0), foutMelder)).thenAnswer(new Answer<Lo3Persoonslijst>() {

            @Override
            public Lo3Persoonslijst answer(final InvocationOnMock invocation) {
                Logging.log(new LogRegel(
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0),
                    LogSeverity.ERROR,
                    SoortMeldingCode.LENGTE,
                    Lo3ElementEnum.ELEMENT_0110));
                return null;
            }
        });
        final List<GgoPersoonslijstGroep> persoonslijstGroepen = fileUploadVerwerker.verwerkFileUpload(filename, file, foutMelder);
        Assert.assertEquals(1, persoonslijstGroepen.size());
        Assert.assertNull(persoonslijstGroepen.get(0).getGgoLo3PL());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getMeldingen());
        Assert.assertNull(persoonslijstGroepen.get(0).getGgoBrpPL());
        Assert.assertNull(persoonslijstGroepen.get(0).getGgoLo3PLTerugConversie());
        Assert.assertNull(persoonslijstGroepen.get(0).getVergelijking());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getFoutLog());
    }

    @Test
    public void testVerwerkFileUploadPreconditieFail() throws Exception {
        final String filename = "Omzetting.txt";
        final List<List<Lo3CategorieWaarde>> lo3CatWaarde = Lo3PersoonslijstTestHelper.retrieveLo3CategorieWaarden(filename, new FoutMelder());
        final List<Lo3Persoonslijst> lo3Persoonslijsten = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten(filename, new FoutMelder());

        Mockito.when(leesService.leesBestand(Matchers.anyString(), Matchers.any(byte[].class), Matchers.any(FoutMelder.class))).thenReturn(lo3CatWaarde);
        Mockito.when(leesService.parsePersoonslijstMetSyntaxControle(Matchers.any(List.class), Matchers.any(FoutMelder.class))).thenReturn(
            lo3Persoonslijsten.get(0));
        Mockito.when(viewerService.verwerkPrecondities(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(null);
        Mockito.when(bcmService.controleerDoorBCM(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(
            new ArrayList<GgoFoutRegel>());

        final List<GgoPersoonslijstGroep> persoonslijstGroepen = fileUploadVerwerker.verwerkFileUpload("asdf", new byte[] {}, new FoutMelder());

        Assert.assertEquals(1, persoonslijstGroepen.size());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getGgoLo3PL());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getMeldingen());
        Assert.assertNull(persoonslijstGroepen.get(0).getGgoBrpPL());
        Assert.assertNull(persoonslijstGroepen.get(0).getGgoLo3PLTerugConversie());
        Assert.assertNull(persoonslijstGroepen.get(0).getVergelijking());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getFoutLog());
    }

    @Test
    public void testVerwerkFileUploadConverteerFail() throws Exception {
        final String filename = "Omzetting.txt";
        final List<List<Lo3CategorieWaarde>> lo3CatWaarde = Lo3PersoonslijstTestHelper.retrieveLo3CategorieWaarden(filename, new FoutMelder());
        final List<Lo3Persoonslijst> lo3Persoonslijsten = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten(filename, new FoutMelder());

        Mockito.when(leesService.leesBestand(Matchers.anyString(), Matchers.any(byte[].class), Matchers.any(FoutMelder.class))).thenReturn(lo3CatWaarde);
        Mockito.when(leesService.parsePersoonslijstMetSyntaxControle(Matchers.any(List.class), Matchers.any(FoutMelder.class))).thenReturn(
            lo3Persoonslijsten.get(0));
        Mockito.when(viewerService.verwerkPrecondities(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(null);
        Mockito.when(bcmService.controleerDoorBCM(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(
            new ArrayList<GgoFoutRegel>());
        Mockito.when(viewerService.converteerNaarBrp(Matchers.any(Lo3Persoonslijst.class), Matchers.any(FoutMelder.class))).thenReturn(null);

        final List<GgoPersoonslijstGroep> persoonslijstGroepen = fileUploadVerwerker.verwerkFileUpload("asdf", new byte[] {}, new FoutMelder());

        Assert.assertEquals(1, persoonslijstGroepen.size());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getGgoLo3PL());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getMeldingen());
        Assert.assertNull(persoonslijstGroepen.get(0).getGgoBrpPL());
        Assert.assertNull(persoonslijstGroepen.get(0).getGgoLo3PLTerugConversie());
        Assert.assertNull(persoonslijstGroepen.get(0).getVergelijking());
        Assert.assertNotNull(persoonslijstGroepen.get(0).getFoutLog());
    }

    private BrpPersoonslijst createBrpPersoonslijst(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep = createIdentificatie(12345L, 54321);
        builder.identificatienummersStapel(new BrpStapel<>(idBrpGroep));
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();
        groepen.add(new BrpGroep<>(new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)), new BrpHistorie(new BrpDatum(
            20000101,
            null), new BrpDatum(20110101, null), new BrpDatumTijd(new Date()), new BrpDatumTijd(new Date()), null), null, null, null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));
        // final BrpRelatie relatie = new BrpRelatie(new BrpSoortRelatieCode(), new BrpSoortBetrokkenheidCode, new
        // List<BrpBetrokkenheid>(), new BrpStapel<BrpRelatieInhoud>());
        final List<BrpBetrokkenheid> betrokkenheid = new ArrayList<>();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroepBetrokkenheid = createIdentificatie(43L, 43);
        betrokkenheid.add(new BrpBetrokkenheid(null, new BrpStapel<>(idBrpGroepBetrokkenheid), null, null, null, null, null, null));
        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                    BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                    BrpSoortBetrokkenheidCode.OUDER,
                    new LinkedHashMap<Long, BrpActie>());
        relatieBuilder.betrokkenheden(betrokkenheid);

        builder.relatie(relatieBuilder.build());

        return builder.build();
    }

    private List<BrpGroep<BrpIdentificatienummersInhoud>> createIdentificatie(final Long aNummer, final Integer bsn) {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep = new ArrayList<>();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpLong(aNummer), new BrpInteger(bsn));
        final BrpHistorie historie =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        final BrpGroep<BrpIdentificatienummersInhoud> brpIdGroep = new BrpGroep<>(inhoud, historie, null, null, null);
        idBrpGroep.add(brpIdGroep);
        return idBrpGroep;
    }

    private void assertStructuurResultaat(final String result) {
        Assert.assertTrue(result.contains("\"ggoLo3PL\" : ["));
        Assert.assertTrue(result.contains("\"ggoBrpPL\" : ["));
        Assert.assertTrue(result.contains("\"meldingen\" : ["));
        Assert.assertTrue(result.contains("\"ggoLo3PLTerugConversie\" : ["));
        Assert.assertTrue(result.contains("\"vergelijking\" : ["));
        Assert.assertTrue(result.contains("\"foutLog\" : "));
    }
}
