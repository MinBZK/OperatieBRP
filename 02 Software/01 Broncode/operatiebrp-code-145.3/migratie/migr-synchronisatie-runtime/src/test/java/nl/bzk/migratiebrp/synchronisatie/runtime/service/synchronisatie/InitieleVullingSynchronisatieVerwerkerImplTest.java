/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie;

import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class InitieleVullingSynchronisatieVerwerkerImplTest {

    @Mock
    private Lo3SyntaxControle syntaxControle;
    @Mock
    private PreconditiesService preconditieService;
    @Mock
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Mock
    private BrpPersoonslijstService brpPersoonslijstService;

    @InjectMocks
    private InitieleVullingSynchronisatieVerwerkerImpl subject;

    @Mock
    private Lo3Bericht loggingBericht;

    @Before
    public void setupLogging() {
        Logging.initContext();
        SynchronisatieLogging.init();
    }

    @After
    public void teardownLogging() {
        Logging.destroyContext();
    }

    @Test
    public void testOk() throws Exception {
        // Setup
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-message-id");
        verzoek.setLo3PersoonslijstAlsTeletexString(maakLo3Pl());

        Mockito.when(syntaxControle.controleer(Matchers.anyListOf(Lo3CategorieWaarde.class))).thenAnswer(new SimpleSyntaxAnswer());
        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Persoonslijst>any())).thenAnswer(new SimplePreconditieAnswer());
        Mockito.when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any())).thenReturn(maakBrpPl(false));
        Mockito.when(brpPersoonslijstService.zoekPersoonOpAnummer("3076980641")).thenReturn(null);
        Mockito.when(brpPersoonslijstService.zoekPersoonOpBsnFoutiefOpgeschortUitsluiten("307980641")).thenReturn(null);

        // Execute
        final SynchroniseerNaarBrpAntwoordBericht result = subject.verwerk(verzoek, loggingBericht);

        // Verify
        Assert.assertNotNull(result);
        Assert.assertEquals(StatusType.TOEGEVOEGD, result.getStatus());
        Assert.assertEquals("verzoek-message-id", result.getCorrelationId());

        Mockito.verify(syntaxControle).controleer(Matchers.anyListOf(Lo3CategorieWaarde.class));
        Mockito.verify(preconditieService).verwerk(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(converteerLo3NaarBrpService).converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(brpPersoonslijstService).zoekPersoonOpAnummer("3076980641");
        Mockito.verify(brpPersoonslijstService).zoekPersoonOpBsnFoutiefOpgeschortUitsluiten("307980641");
        Mockito.verify(brpPersoonslijstService).persisteerPersoonslijst(Matchers.<BrpPersoonslijst>any(), Matchers.<Lo3Bericht>any());

        Mockito.verifyNoMoreInteractions(syntaxControle, preconditieService, converteerLo3NaarBrpService, brpPersoonslijstService);
    }

    @Test
    public void testGeenGroep80() throws OngeldigePersoonslijstException, SynchronisatieVerwerkerException {
        // Setup
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-message-id");
        verzoek.setLo3PersoonslijstAlsTeletexString(maakLo3PlZonderGroep80());

        Mockito.when(syntaxControle.controleer(Matchers.anyListOf(Lo3CategorieWaarde.class))).thenAnswer(new SimpleSyntaxAnswer());
        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Persoonslijst>any())).thenAnswer(new SimplePreconditieAnswer());
        Mockito.when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any())).thenReturn(maakBrpPl(false));
        Mockito.when(brpPersoonslijstService.zoekPersoonOpAnummer("3076980641")).thenReturn(null);
        Mockito.when(brpPersoonslijstService.zoekPersoonOpBsnFoutiefOpgeschortUitsluiten("307980641")).thenReturn(null);

        // Execute
        final SynchroniseerNaarBrpAntwoordBericht result = subject.verwerk(verzoek, loggingBericht);

        // Verify
        Assert.assertNotNull(result);
        Assert.assertEquals(StatusType.TOEGEVOEGD, result.getStatus());
        Assert.assertEquals("verzoek-message-id", result.getCorrelationId());

        Mockito.verify(syntaxControle).controleer(Matchers.anyListOf(Lo3CategorieWaarde.class));
        Mockito.verify(preconditieService).verwerk(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(converteerLo3NaarBrpService).converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(brpPersoonslijstService).zoekPersoonOpAnummer("3076980641");
        Mockito.verify(brpPersoonslijstService).zoekPersoonOpBsnFoutiefOpgeschortUitsluiten("307980641");
        Mockito.verify(brpPersoonslijstService).persisteerPersoonslijst(Matchers.<BrpPersoonslijst>any(), Matchers.<Lo3Bericht>any());

        Mockito.verifyNoMoreInteractions(syntaxControle, preconditieService, converteerLo3NaarBrpService, brpPersoonslijstService);
    }

    @Test
    public void testPersoonslijstBestaatAl() throws Exception {
        // Setup
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-message-id");
        verzoek.setLo3PersoonslijstAlsTeletexString(maakLo3Pl());

        Mockito.when(syntaxControle.controleer(Matchers.anyListOf(Lo3CategorieWaarde.class))).thenAnswer(new SimpleSyntaxAnswer());
        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Persoonslijst>any())).thenAnswer(new SimplePreconditieAnswer());
        Mockito.when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any())).thenReturn(maakBrpPl(false));
        Mockito.when(brpPersoonslijstService.zoekPersoonOpAnummer("3076980641")).thenReturn(maakBrpPl(false));

        // Execute
        try {
            subject.verwerk(verzoek, loggingBericht);
            Assert.fail("SynchronisatieVerwerkerException verwacht");
        } catch (final SynchronisatieVerwerkerException e) {
            Assert.assertEquals(StatusType.FOUT, e.getStatus());
        }

        // Verify
        Mockito.verify(syntaxControle).controleer(Matchers.anyListOf(Lo3CategorieWaarde.class));
        Mockito.verify(preconditieService).verwerk(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(converteerLo3NaarBrpService).converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(brpPersoonslijstService).zoekPersoonOpAnummer("3076980641");

        Mockito.verifyNoMoreInteractions(syntaxControle, preconditieService, converteerLo3NaarBrpService, brpPersoonslijstService);
    }

    @Test
    public void testPersoonslijstBsnBestaatAlFoutiefOpgeschort() throws Exception {
        // Setup
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-message-id");
        verzoek.setLo3PersoonslijstAlsTeletexString(maakLo3Pl());

        Mockito.when(syntaxControle.controleer(Matchers.anyListOf(Lo3CategorieWaarde.class))).thenAnswer(new SimpleSyntaxAnswer());
        Mockito.when(preconditieService.verwerk(Matchers.<Lo3Persoonslijst>any())).thenAnswer(new SimplePreconditieAnswer());
        Mockito.when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any())).thenReturn(maakBrpPl(true));
        Mockito.when(brpPersoonslijstService.zoekPersoonOpAnummer("3076980641")).thenReturn(null);
        Mockito.when(brpPersoonslijstService.zoekPersoonOpBsnFoutiefOpgeschortUitsluiten("307980641")).thenReturn(maakBrpPl(true));

        // Execute
        SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerk(verzoek, loggingBericht);
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getStatus());

        // Verify
        Mockito.verify(syntaxControle).controleer(Matchers.anyListOf(Lo3CategorieWaarde.class));
        Mockito.verify(preconditieService).verwerk(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(converteerLo3NaarBrpService).converteerLo3Persoonslijst(Matchers.<Lo3Persoonslijst>any());
        Mockito.verify(brpPersoonslijstService).zoekPersoonOpAnummer("3076980641");
        Mockito.verify(brpPersoonslijstService).persisteerPersoonslijst(Matchers.<BrpPersoonslijst>any(), Matchers.<Lo3Bericht>any());

        Mockito.verifyNoMoreInteractions(syntaxControle, preconditieService, converteerLo3NaarBrpService, brpPersoonslijstService);
    }

    private BrpPersoonslijst maakBrpPl(boolean isFoutiefOpgeschort) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.identificatienummersStapel(
                BrpStapelHelper.stapel(
                        BrpStapelHelper
                                .groep(BrpStapelHelper.identificatie("3076980641", "307980641"), BrpStapelHelper.his(1970101),
                                        BrpStapelHelper.act(1, 1970101))));
        builder.bijhoudingStapel(BrpStapelHelper.stapel(
                BrpStapelHelper
                        .groep(BrpStapelHelper.bijhouding("000516", BrpBijhoudingsaardCode.INGEZETENE,
                                isFoutiefOpgeschort ? BrpNadereBijhoudingsaardCode.FOUT : BrpNadereBijhoudingsaardCode.ACTUEEL),
                                BrpStapelHelper.his(1970101),
                                BrpStapelHelper.act(1, 1970101))));
        return builder.build();
    }

    private Lo3PersoonslijstBuilder maakBasisBuilder() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Persoon("3076980641", "Piet", "Klaassen", 19770101, "0516", "0001", "M"),
                                Lo3CategorieEnum.PERSOON)));

        builder.ouder1Stapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Ouder("9806953249", "Betsy", "Pietersen", 19130101, "0516", "0001", "V", 19770101),
                                Lo3CategorieEnum.OUDER_1)));
        builder.ouder2Stapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Ouder("9806953249", "Johan", "Klaassen", 19130101, "0516", "0001", "M", 19770101),
                                Lo3CategorieEnum.OUDER_2)));

        builder.verblijfplaatsStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Verblijfplaats("0516", 19770101, 19770101, "Langstraat", 14, "1234RE", "A"),
                                null,
                                new Lo3Historie(null, null, null),
                                new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));

        return builder;
    }

    private String maakLo3Pl() {
        final Lo3PersoonslijstBuilder builder = maakBasisBuilder();

        builder.inschrijvingStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Inschrijving(null, null, null, 19770101, "0516", null, 1, 20130101000000000L, null),
                                null,
                                new Lo3Historie(null, null, null),
                                new Lo3Herkomst(Lo3CategorieEnum.INSCHRIJVING, 0, 0))));

        final Lo3Persoonslijst lo3Pl = builder.build();
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(lo3Pl);
        return Lo3Inhoud.formatInhoud(categorieen);
    }

    private String maakLo3PlZonderGroep80() {
        final Lo3PersoonslijstBuilder builder = maakBasisBuilder();

        final Lo3InschrijvingInhoud inhoud = new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(19770101), null, null, null, null, null, null, null);

        builder.inschrijvingStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(inhoud, null, new Lo3Historie(null, null, null), new Lo3Herkomst(Lo3CategorieEnum.INSCHRIJVING, 0, 0))));

        final Lo3Persoonslijst lo3Pl = builder.build();
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(lo3Pl);
        return Lo3Inhoud.formatInhoud(categorieen);
    }

    public static class SimpleSyntaxAnswer implements Answer<List<Lo3CategorieWaarde>> {
        @Override
        public List<Lo3CategorieWaarde> answer(final InvocationOnMock invocation) {
            return (List<Lo3CategorieWaarde>) invocation.getArguments()[0];
        }
    }

    public static class SimplePreconditieAnswer implements Answer<Lo3Persoonslijst> {
        @Override
        public Lo3Persoonslijst answer(final InvocationOnMock invocation) {
            return (Lo3Persoonslijst) invocation.getArguments()[0];
        }
    }

}
