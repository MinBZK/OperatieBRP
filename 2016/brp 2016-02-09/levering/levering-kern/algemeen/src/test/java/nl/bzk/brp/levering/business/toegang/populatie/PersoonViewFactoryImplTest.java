/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.levering.business.bepalers.impl.SoortSynchronisatieBepalerImpl;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.VoorkomenLeveringMutatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class PersoonViewFactoryImplTest {

    private static final String LEVEREN_VANAF_MOMENT = "leverenVanafMoment";

    @InjectMocks
    private final PersoonViewFactory            persoonViewFactory       = new PersoonViewFactoryImpl();

    @Mock
    private AdministratieveHandelingModel administratieveHandeling;

    @Mock
    private Leveringinformatie           leveringAutorisatie;

    @Mock
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Before
    public final void setUp() throws Exception {
        setField(persoonViewFactory, "soortSynchronisatieBepaler", new SoortSynchronisatieBepalerImpl());

        final PartijAttribuut partij = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM;
        when(administratieveHandeling.getPartij()).thenReturn(partij);

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metPopulatiebeperking("WAAR").metNaam("abo")
            .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN).metDatumIngang(new DatumAttribuut(DatumAttribuut.gisteren().getWaarde()))
            .maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(leveringsautorisatie)
            .metGeautoriseerde(new PartijRol(partij.getWaarde(), null, null, null)).maak();
        when(leveringAutorisatie.getToegangLeveringsautorisatie()).thenReturn(tla);
        when(toegangLeveringsautorisatieService.geefLeveringautorisatieZonderControle(anyInt())).thenReturn(tla.getLeveringsautorisatie());
    }

    @Test
    public final void testBetreedtPopulatie() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Populatie populatie = Populatie.BETREEDT;
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        // act
        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(HistorieVanafPredikaat.class));
    }

    @Test
    public final void testBetreedtPopulatieZelfdeTsRegAlsAfnemerIndicatie() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Populatie populatie = Populatie.BETREEDT;
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.datumTijd(2012, 1, 1));
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        // act
        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(HistorieVanafPredikaat.class));
    }

    @Test
    public final void testBinnenPopulatie() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Populatie populatie = Populatie.BINNEN;
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));

        when(leveringAutorisatie.getSoortDienst())
                .thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(AdministratieveHandelingDeltaPredikaat.class));
    }

    @Test
    public final void testVerlaatPopulatie() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Populatie populatie = Populatie.VERLAAT;
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(AdministratieveHandelingDeltaPredikaat.class));
    }

    @Test
    public final void testBuitenPopulatie() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Populatie populatie = Populatie.VERLAAT;

        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        // act
        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(AdministratieveHandelingDeltaPredikaat.class));
    }

    @Test
    public final void testBuitenMetIndicatiePopulatie() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final Populatie populatie = Populatie.BUITEN;
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));
        when(leveringAutorisatie.getSoortDienst())
                .thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        // act
        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(AdministratieveHandelingDeltaPredikaat.class));
    }

    @Test
    public final void testBinnenVoorDienstAttenderenNegeertDatumMaterieelVanaf() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final Populatie populatie = Populatie.BETREEDT;
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort()).thenReturn(new SoortAdministratieveHandelingAttribuut(
            SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.ATTENDERING);

        // act
        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());
        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(HistorieVanafPredikaat.class));

        for (final Predicate predicate : predikaten) {
            if (predicate instanceof HistorieVanafPredikaat) {
                final DatumAttribuut leverenVanafMoment = (DatumAttribuut) ReflectionTestUtils.getField(predicate, LEVEREN_VANAF_MOMENT);
                assertThat(leverenVanafMoment, is(nullValue()));
            }
        }
    }

    @Test
    public final void testMaterieelViewVoorAdmHndSynchronisatiePersoon() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort()).thenReturn(new SoortAdministratieveHandelingAttribuut(
            SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.ATTENDERING);

        // act
        final PersoonHisVolledigView resultaat =
                persoonViewFactory.maakMaterieleHistorieView(persoonHisVolledig, leveringAutorisatie, administratieveHandeling, null);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(HistorieVanafPredikaat.class));
        assertTrue(predikaatKlassen.contains(VoorkomenLeveringMutatiePredikaat.class));
    }

    @Test
    public final void testAdmHndSynchronisatiePersoonVoorAfnemerindicatieZonderMaterieelVanaf() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort()).thenReturn(new SoortAdministratieveHandelingAttribuut(
            SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.ATTENDERING);

        for (final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig : persoonHisVolledig.getAfnemerindicaties()) {
            final HisPersoonAfnemerindicatieModel actueleRecord =
                persoonAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getActueleRecord();
            setField(actueleRecord, "datumAanvangMaterielePeriode", null);
        }

        // act
        final PersoonHisVolledigView resultaat =
            persoonViewFactory.maakMaterieleHistorieView(persoonHisVolledig, leveringAutorisatie, administratieveHandeling, null);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(HistorieVanafPredikaat.class));
        assertTrue(predikaatKlassen.contains(VoorkomenLeveringMutatiePredikaat.class));
    }

    @Test
    public final void testMaterieelViewMetMaterieelVanafMoment() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        persoonHisVolledig.getOnderzoeken().add(new PersoonOnderzoekHisVolledigImplBuilder(persoonHisVolledig,
            new OnderzoekHisVolledigImplBuilder().build(), true).build());

        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
        final DatumAttribuut materieelMoment = DatumAttribuut.gisteren();

        // act
        final PersoonHisVolledigView resultaat =
                persoonViewFactory.maakMaterieleHistorieView(persoonHisVolledig, leveringAutorisatie, administratieveHandeling, materieelMoment);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        assertThat(predikaten.get(0), is(instanceOf(HistorieVanafPredikaat.class)));
        assertThat(predikaten.get(1), is(instanceOf(VoorkomenLeveringMutatiePredikaat.class)));

        final DatumAttribuut leverenVanafMoment = (DatumAttribuut) ReflectionTestUtils.getField(predikaten.get(0), LEVEREN_VANAF_MOMENT);
        assertThat(leverenVanafMoment, equalTo(materieelMoment));
    }

    @Test
    public final void testViewVoorAdmHndSynchronisatiePersoonMetBeperking() {
        final PersoonHisVolledig persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final Populatie populatie = Populatie.BETREEDT;
        when(administratieveHandeling.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.bouwDatumTijd(2013, 12, 15, 12, 0, 0));
        when(administratieveHandeling.getSoort())
                .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.ATTENDERING);

        // act
        final PersoonHisVolledigView resultaat = persoonViewFactory.maakView(persoonHisVolledig, leveringAutorisatie, populatie, administratieveHandeling);

        // assert
        assertThat(resultaat.getPredikaat(), is(instanceOf(AllPredicate.class)));

        final AllPredicate predikaat = (AllPredicate) resultaat.getPredikaat();
        final List<Predicate> predikaten = Arrays.asList(predikaat.getPredicates());

        final List<Class> predikaatKlassen = maakLijstMetPredicaatKlassen(predikaten);
        assertTrue(predikaatKlassen.contains(HistorieVanafPredikaat.class));

        final DatumAttribuut leverenVanafMoment;
        final Predicate predikaatVoorLeverenVanafMoment = geefPredikaatVanType(predikaten, HistorieVanafPredikaat.class);
        if (predikaatVoorLeverenVanafMoment != null) {
            leverenVanafMoment = (DatumAttribuut) ReflectionTestUtils.getField(predikaatVoorLeverenVanafMoment, LEVEREN_VANAF_MOMENT);
        } else {
            leverenVanafMoment = null;
        }

        assertThat(leverenVanafMoment, is(not(nullValue())));
    }

    private Predicate geefPredikaatVanType(final List<Predicate> predikaten, final Class clazz) {
        for (final Predicate predikaat : predikaten) {
            if (predikaat.getClass().isAssignableFrom(clazz)) {
                return predikaat;
            }
        }
        return null;
    }

    @Test
    public void testMaakView() {

    }

    @Test
    public void testMaakLegeView() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView = persoonViewFactory.maakLegeView(persoonHisVolledig);

        // Controleer groepen op attributen
        for (final HistorieEntiteit historieEntiteit : persoonHisVolledigView.getAlleHistorieRecords()) {
            if (historieEntiteit instanceof Groep) {
                final Groep groep = (Groep) historieEntiteit;
                final List<Attribuut> attributen = groep.getAttributen();
                for (final Attribuut attribuut : attributen) {
                    final String foutmelding =
                        "Attribuut (" + attribuut.getClass().getSimpleName() + ") is aan incorrecte groep gekoppeld: " + groep.getClass().getSimpleName();
                    Assert.assertEquals(foutmelding, groep, attribuut.getGroep());
                }
            }
        }
    }

    @Test
    public void testVoegPredikatenToe() {

    }

    @Test
    public void testMaakMaterieleHistorieView() {

    }

    private List<Class> maakLijstMetPredicaatKlassen(final List<Predicate> predikaten) {
        final List<Class> predikaatKlassen = new ArrayList<>();
        for (final Predicate predicate : predikaten) {
            predikaatKlassen.add(predicate.getClass());
        }
        return predikaatKlassen;
    }
}
