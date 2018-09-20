/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactory;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactoryImpl;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import org.apache.commons.collections.functors.TruePredicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class MaakLegeViewOpPersonenStapTest {

    private static final String ID = "iD";

    @Spy
    private final PersoonViewFactory persoonViewFactory = new PersoonViewFactoryImpl();

    @InjectMocks
    private final MaakLegeViewOpPersonenStap maakLegeViewOpPersonenStap = new MaakLegeViewOpPersonenStap();

    private LeveringautorisatieStappenOnderwerp   onderwerp;
    private LeveringsautorisatieVerwerkingContext context;
    private final LeveringautorisatieVerwerkingResultaat resultaat        = new LeveringautorisatieVerwerkingResultaat();
    private final Map<Integer, Populatie>                persoonPopulatie = new HashMap<>();

    private final DatumTijdAttribuut tijdstipRegistratie = new DatumTijdAttribuut(new Date());

    @Before
    public final void setup() {
        final AdministratieveHandelingModel administratieveHandelingModel = getTestAdministratieveHandeling();

        persoonPopulatie.put(1, Populatie.BINNEN);
        persoonPopulatie.put(2, Populatie.BINNEN);

        context = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandelingModel, getBijgehoudenTestPersonen(), persoonPopulatie, null, null);

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.DUMMY);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(leveringsautorisatie).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, leveringsautorisatie.geefDienst(SoortDienst.DUMMY));
        onderwerp = new LeveringautorisatieStappenOnderwerpImpl(leveringinformatie, administratieveHandelingModel.getID(), Stelsel.BRP);
    }

    @Test
    public final void testVoerStapUit() {
        // when
        final boolean uitkomst = maakLegeViewOpPersonenStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(2, context.getBijgehoudenPersoonViews().size());
        assertEquals(Integer.valueOf(1), context.getBijgehoudenPersoonViews().get(0).getID());
        assertEquals(Integer.valueOf(2), context.getBijgehoudenPersoonViews().get(1).getID());

        final Predicate predikaat = context.getBijgehoudenPersoonViews().get(0).getPredikaat();
        assertTrue(predikaat instanceof AllPredicate);
        final List<Predicate> predikaten = Arrays.asList(((AllPredicate) predikaat).getPredicates());
        assertTrue(predikaatVanTypeIsAanwezig(predikaten, TruePredicate.class));
        assertTrue(uitkomst);
    }

    @Test(expected = NullPointerException.class)
    public final void testVoerStapUitMetExceptie() {
        context = new LeveringsautorisatieVerwerkingContextImpl(null, null, context.getTeLeverenPersoonIds(), null, null);

        maakLegeViewOpPersonenStap.voerStapUit(onderwerp, context, resultaat);
    }

    @Test
    public final void testVoerStapUitZonderDatAllePersonenGeleverdMoetenWorden() {
        // given
        persoonPopulatie.remove(2);

        // when
        maakLegeViewOpPersonenStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(1, context.getBijgehoudenPersoonViews().size());
    }

    @Test
    public final void testVoerNaBewerkingStapUit() {
        // when
        maakLegeViewOpPersonenStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(2, context.getBijgehoudenPersoonViews().size());

        // when
        maakLegeViewOpPersonenStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        // then
        assertNotNull(context.getBijgehoudenPersoonViews());
        assertEquals(0, context.getBijgehoudenPersoonViews().size());
    }

    private AdministratieveHandelingModel getTestAdministratieveHandeling() {
        final AdministratieveHandelingModel administratieveHandelingModel = mock(AdministratieveHandelingModel.class);
        when(administratieveHandelingModel.getTijdstipRegistratie()).thenReturn(tijdstipRegistratie);
        when(administratieveHandelingModel.getSoort()).thenReturn(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE));
        return administratieveHandelingModel;
    }

    private List<PersoonHisVolledig> getBijgehoudenTestPersonen() {
        final List<PersoonHisVolledig> persoonHisVolledigs = new ArrayList<>();

        final Dienst mockDienst = mock(Dienst.class);

        final Leveringsautorisatie leveringsautorisatie1 = TestLeveringsautorisatieBuilder.maker().metNaam("Foo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();

        final Leveringsautorisatie leveringsautorisatie2 = TestLeveringsautorisatieBuilder.maker().metNaam("TEST_LEVERINGSAUTORISATIE_NAAM")
            .metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();

        final PersoonHisVolledig persoon1 =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).voegPersoonAfnemerindicatieToe(
                new PersoonAfnemerindicatieHisVolledigImplBuilder(null, leveringsautorisatie2)
                    .nieuwStandaardRecord(mockDienst)
                    .datumAanvangMaterielePeriode(20010101).eindeRecord().build()).build();
        ReflectionTestUtils.setField(persoon1, ID, 1);

        final PersoonHisVolledig persoon3 =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).voegPersoonAfnemerindicatieToe(
                new PersoonAfnemerindicatieHisVolledigImplBuilder(null, leveringsautorisatie1)
                    .nieuwStandaardRecord(mockDienst)
                    .datumAanvangMaterielePeriode(20010101).eindeRecord().build()).build();
        ReflectionTestUtils.setField(persoon3, ID, 2);

        persoonHisVolledigs.add(persoon1);
        persoonHisVolledigs.add(persoon3);

        return persoonHisVolledigs;
    }

    private boolean predikaatVanTypeIsAanwezig(final List<Predicate> predikaten, final Class clazz) {
        for (final Predicate predikaat : predikaten) {
            if (predikaat.getClass().isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }
}
