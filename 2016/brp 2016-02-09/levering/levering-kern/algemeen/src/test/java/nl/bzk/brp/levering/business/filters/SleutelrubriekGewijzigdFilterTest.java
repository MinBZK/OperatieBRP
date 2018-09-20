/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.levering.business.bepalers.SleutelrubriekGewijzigdBepaler;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SleutelrubriekGewijzigdFilterTest extends AbstractFilterTest {

    private static final String ATTENDERINGS_CRITERIUM = "WAAR";
    private static final String WAAR                   = "WAAR";

    @InjectMocks
    private final SleutelrubriekGewijzigdFilter sleutelrubriekGewijzigdFilter = new SleutelrubriekGewijzigdFilter();

    @Mock
    private SleutelrubriekGewijzigdBepaler bepaler;

    @Mock
    private ExpressieService expressieService;

    @Mock
    private AdministratieveHandelingModel administratieveHandelingModel;

    @Mock
    private PersoonHisVolledig persoon;


    final Partij    partij    = TestPartijBuilder.maker().metCode(1).maak();
    final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER, DatumAttribuut.vandaag(), null);

    final Dienst doelbinding             = TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING)
        .metEffectAfnemerindicaties(EffectAfnemerindicaties
            .PLAATSING).maak();
    final Dienst attendering             = TestDienstBuilder.maker().metSoortDienst(SoortDienst.ATTENDERING)
        .metEffectAfnemerindicaties(EffectAfnemerindicaties
            .DUMMY).maak();
    final Dienst attenderingMetPlaatsing = TestDienstBuilder.maker().metAttenderingscriterium(ATTENDERINGS_CRITERIUM).metSoortDienst(SoortDienst
        .ATTENDERING)
        .metEffectAfnemerindicaties(EffectAfnemerindicaties
            .PLAATSING).maak();


    final Dienst attenderingMetPlaatsingZonderCriterium = TestDienstBuilder.maker().metAttenderingscriterium(null).metSoortDienst
        (SoortDienst
            .ATTENDERING)
        .metEffectAfnemerindicaties(EffectAfnemerindicaties
            .PLAATSING).maak();

    private Leveringinformatie leveringAutorisatieAttendering = new Leveringinformatie(TestToegangLeveringautorisatieBuilder.maker()
        .metLeveringsautorisatie(TestLeveringsautorisatieBuilder.maker().metId(123)
            .metDienstbundels
                (TestDienstbundelBuilder
                    .maker()
                    .metDiensten(doelbinding).maak()).maak()).metGeautoriseerde(partijRol).maak(), attendering);

    private Leveringinformatie leveringAutorisatieAndereDienst = new Leveringinformatie(TestToegangLeveringautorisatieBuilder.maker()
        .metLeveringsautorisatie(TestLeveringsautorisatieBuilder.maker().metId(123)
            .metDienstbundels
                (TestDienstbundelBuilder
                    .maker()
                    .metDiensten(doelbinding).maak()).maak()).metGeautoriseerde(partijRol).maak(), doelbinding);

    private Leveringinformatie leveringAutorisatieAttenderingGeenAttCriterium = new Leveringinformatie(TestToegangLeveringautorisatieBuilder.maker()
        .metLeveringsautorisatie(TestLeveringsautorisatieBuilder.maker().metId(123)
            .metDienstbundels
                (TestDienstbundelBuilder
                    .maker()
                    .metDiensten(doelbinding).maak()).maak()).metGeautoriseerde(partijRol).maak(), attendering);


    private Leveringinformatie leveringAutorisatieAttenderingPlaatsing = new Leveringinformatie(TestToegangLeveringautorisatieBuilder.maker()
        .metLeveringsautorisatie(TestLeveringsautorisatieBuilder.maker().metId(456)
            .metDienstbundels
                (TestDienstbundelBuilder
                    .maker()
                    .metDiensten(doelbinding).maak()).maak()).metGeautoriseerde(partijRol).maak(), attenderingMetPlaatsing);

    private Leveringinformatie leveringAutorisatieAttenderingPlaatsingGeenAttCriterium = new Leveringinformatie(
        TestToegangLeveringautorisatieBuilder.maker()
            .metLeveringsautorisatie(TestLeveringsautorisatieBuilder.maker().metId(456)
                .metDienstbundels
                    (TestDienstbundelBuilder
                        .maker()
                        .metDiensten(doelbinding).maak()).maak()).metGeautoriseerde(partijRol).maak(), attenderingMetPlaatsingZonderCriterium);


    @Test
    public final void testMagLeverenDoorgaanTrue() throws ExpressieExceptie {
        when(bepaler.bepaalAttributenGewijzigd(eq(persoon), eq(administratieveHandelingModel), any(Expressie.class), any(Leveringsautorisatie.class)))
            .thenReturn(true);
        when(expressieService.geefAttenderingsCriterium(any(Leveringinformatie.class))).thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final boolean resultaat = sleutelrubriekGewijzigdFilter
            .magLeverenDoorgaan(persoon, null, leveringAutorisatieAttendering, administratieveHandelingModel);

        assertTrue(resultaat);
    }

    @Test
    public final void testMagLeverenDoorgaanTrueAndereDienst() throws ExpressieExceptie {
        when(bepaler.bepaalAttributenGewijzigd(eq(persoon), eq(administratieveHandelingModel), any(Expressie.class), any(Leveringsautorisatie.class)))
            .thenReturn(true);
        when(expressieService.geefAttenderingsCriterium(any(Leveringinformatie.class))).thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final boolean resultaat = sleutelrubriekGewijzigdFilter.magLeverenDoorgaan(persoon, null, leveringAutorisatieAndereDienst,
            administratieveHandelingModel);

        assertTrue(resultaat);
    }

    @Test
    public final void testNietLeverenIndienGeenAttCriteriumVoorDienstAttendering() throws ExpressieExceptie {
        final boolean resultaat =
            sleutelrubriekGewijzigdFilter.magLeverenDoorgaan(persoon, null, leveringAutorisatieAttenderingGeenAttCriterium, administratieveHandelingModel);

        assertFalse(resultaat);
    }

    @Test
    public final void testNietLeverenIndienGeenAttCriteriumVoorDienstAttenderingMetPlaatsing() throws ExpressieExceptie {
        final boolean resultaat =
            sleutelrubriekGewijzigdFilter
                .magLeverenDoorgaan(persoon, null, leveringAutorisatieAttenderingPlaatsingGeenAttCriterium, administratieveHandelingModel);

        assertFalse(resultaat);
    }

    @Test
    public final void testMagLeverenDoorgaanFalseNietsGewijzigd() throws ExpressieExceptie {
        when(bepaler.bepaalAttributenGewijzigd(eq(persoon), eq(administratieveHandelingModel), any(Expressie.class), any(Leveringsautorisatie.class)))
            .thenReturn(false);
        when(expressieService.geefAttenderingsCriterium(any(Leveringinformatie.class))).thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final boolean resultaat = sleutelrubriekGewijzigdFilter.magLeverenDoorgaan(persoon, null, leveringAutorisatieAttendering,
            administratieveHandelingModel);

        assertFalse(resultaat);
    }

    @Test
    public final void testMagLeverenDoorgaanTrueAttenderingMetPlaatsingGeenAfnemerindicatie() throws ExpressieExceptie {
        when(expressieService.geefAttenderingsCriterium(any(Leveringinformatie.class))).thenReturn(BRPExpressies.parse(WAAR).getExpressie());


        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(123).maak();
        final Partij partij = TestPartijBuilder.maker().metId(123).maak();

        when(bepaler
            .bepaalAttributenGewijzigd(eq(persoonHisVolledig), eq(administratieveHandelingModel), any(Expressie.class), any(Leveringsautorisatie.class)))
            .thenReturn(true);

        persoonHisVolledig.getAfnemerindicaties().add(new PersoonAfnemerindicatieHisVolledigImplBuilder(persoonHisVolledig, partij, leveringsautorisatie)
            .nieuwStandaardRecord(leveringAutorisatieAttendering.getDienst())
            .datumAanvangMaterielePeriode(20150101).eindeRecord().build());

        final boolean resultaat = sleutelrubriekGewijzigdFilter.magLeverenDoorgaan(persoonHisVolledig, null, leveringAutorisatieAttenderingPlaatsing,
            administratieveHandelingModel);

        assertTrue(resultaat);
    }

    @Test
    public final void testMagLeverenDoorgaanFalseAttenderingMetPlaatsingWelAfnemerindicatie() throws ExpressieExceptie {
        when(expressieService.geefAttenderingsCriterium(any(Leveringinformatie.class))).thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(456).maak();
        final Partij partij = TestPartijBuilder.maker().maak();

        persoonHisVolledig.getAfnemerindicaties().add(new PersoonAfnemerindicatieHisVolledigImplBuilder(persoonHisVolledig, partij, leveringsautorisatie)
            .nieuwStandaardRecord(leveringAutorisatieAttendering.getDienst())
            .datumAanvangMaterielePeriode(20150101).eindeRecord().build());

        final boolean resultaat =
            sleutelrubriekGewijzigdFilter.magLeverenDoorgaan(persoonHisVolledig, null, leveringAutorisatieAttenderingPlaatsing,
                administratieveHandelingModel);

        assertFalse(resultaat);
    }

    @Test
    public final void testMagLeverenDoorgaanTrueAttenderingMetPlaatsingWelAfnemerindicatieMaarGeenActueleRecord() throws ExpressieExceptie {
        when(expressieService.geefAttenderingsCriterium(any(Leveringinformatie.class))).thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(456).maak();
        final Partij partij = TestPartijBuilder.maker().maak();

        when(bepaler
            .bepaalAttributenGewijzigd(eq(persoonHisVolledig), eq(administratieveHandelingModel), any(Expressie.class), any(Leveringsautorisatie.class)))
            .thenReturn(true);

        persoonHisVolledig.getAfnemerindicaties().add(new PersoonAfnemerindicatieHisVolledigImplBuilder(persoonHisVolledig, partij, leveringsautorisatie)
            .nieuwStandaardRecord(leveringAutorisatieAttendering.getDienst())
            .datumAanvangMaterielePeriode(20150101).eindeRecord().build());

        // Verval alle afnemerindicaties zodat er geen actuele records zijn
        for (final PersoonAfnemerindicatieHisVolledigImpl persoonAfnemerindicatie : persoonHisVolledig.getAfnemerindicaties()) {
            persoonAfnemerindicatie.getPersoonAfnemerindicatieHistorie()
                .verval(TestDienstBuilder.dummy(), DatumTijdAttribuut.terug24Uur());
        }

        final boolean resultaat =
            sleutelrubriekGewijzigdFilter.magLeverenDoorgaan(persoonHisVolledig, null, leveringAutorisatieAttenderingPlaatsing,
                administratieveHandelingModel);

        assertTrue(resultaat);
    }

}
