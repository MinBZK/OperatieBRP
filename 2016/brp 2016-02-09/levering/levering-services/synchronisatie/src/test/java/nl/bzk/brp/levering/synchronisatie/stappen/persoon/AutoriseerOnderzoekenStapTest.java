/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.OnderzoekAutorisatieService;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.PersoonHisVolledigViewUtil;
import nl.bzk.brp.util.PersoonViewUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AutoriseerOnderzoekenStapTest {
    private static final String HEBBEN_WE_HET_JUISTE_ONDERZOEK_TE_PAKKEN = "Hebben we het juiste onderzoek te pakken?";
    private static final String VLAGGETJE_DIENT_OP_TRUE_TE_STAAN_DOORDAT_GEGEVEN_ZELF_GELEVERD_WORDT =
        "Vlaggetje dient op true te staan doordat gegeven zelf geleverd wordt.";
    private static final String VLAGGETJE_DIENT_OP_FALSE_TE_STAAN_DOORDAT_GEGEVEN_ZELF_GELEVERD_WORDT =
        "Vlaggetje dient op false te staan doordat gegeven zelf geleverd wordt.";

    @InjectMocks
    private final AutoriseerOnderzoekenStap autoriseerOnderzoekenStap = new AutoriseerOnderzoekenStap();

    @Mock
    private OnderzoekAutorisatieService onderzoekAutorisatieService;

    private final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(new ArrayList<Melding>());

    private final PersoonHisVolledig persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

    private SynchronisatieBerichtContext context;
    private BurgerservicenummerAttribuut attribuutInOnderzoek;

    @Before
    public void setUp() throws Exception {
        final List<PersoonHisVolledigView> bijgehoudenPersonenViews = new ArrayList<>();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);
        PersoonHisVolledigViewUtil.initialiseerView(persoonHisVolledigView);
        bijgehoudenPersonenViews.add(persoonHisVolledigView);

        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = new HashMap<>();
        final Map<Integer, List<Attribuut>> mapPersoon1 = new HashMap<>();

        // Johhny Jordaan heeft een onderzoek met gegevenInOnderzoekId = 1 en het gegeven is zijn bsn nummer.
        attribuutInOnderzoek = persoonHisVolledig.getPersoonIdentificatienummersHistorie().getActueleRecord().getBurgerservicenummer();
        mapPersoon1.put(1, Collections.singletonList((Attribuut) attribuutInOnderzoek));
        persoonOnderzoekenMap.put(1, mapPersoon1);

        final BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        context = new SynchronisatieBerichtContext(berichtenIds, null, null, null);
        context.setPersoonOnderzoekenMap(persoonOnderzoekenMap);

        final AdministratieveHandelingModel administratieveHandelingModel =
            maakAdministratieveHandelingModel(5L, SoortAdministratieveHandeling.AANVANG_ONDERZOEK, 36101);
        final AdministratieveHandelingSynchronisatie admHndSynchronisatie = new AdministratieveHandelingSynchronisatie(administratieveHandelingModel);
        admHndSynchronisatie.setBijgehoudenPersonen(bijgehoudenPersonenViews);
        final VolledigBericht volledigBericht = new VolledigBericht(admHndSynchronisatie);
        context.setVolledigBericht(volledigBericht);
    }

    @Test
    public void testVoerStapUitWelAutoriseren() {
        // autoriseer het attribuut dat in onderzoek staat, vervolgens wordt dit vlaggetje overgenomen op het gegevenInOnderzoek door deze stap.
        PersoonViewUtil.zetMagGeleverdWordenVlagOpAttribuut(attribuutInOnderzoek);

        final boolean resultaatStap = autoriseerOnderzoekenStap.voerStapUit(null, context, resultaat);
        Assert.assertTrue(resultaatStap);
        final GegevenInOnderzoekHisVolledig gegevenInOnderzoek = context.getVolledigBericht().getAdministratieveHandeling()
            .getBijgehoudenPersonen().get(0).getOnderzoeken().iterator().next().getOnderzoek().getGegevensInOnderzoek().iterator().next();
        Assert.assertEquals(HEBBEN_WE_HET_JUISTE_ONDERZOEK_TE_PAKKEN, Integer.valueOf(1), gegevenInOnderzoek.getID());
        verify(onderzoekAutorisatieService).autoriseerOnderzoeken(any(PersoonHisVolledigView.class), anyMap());
    }

    @Test
    public void testVoerStapUitNietAutoriseren() {
        final boolean resultaatStap = autoriseerOnderzoekenStap.voerStapUit(null, context, resultaat);
        Assert.assertTrue(resultaatStap);

        // Geen vlaggen gezet op de attributen.
        final GegevenInOnderzoekHisVolledig gegevenInOnderzoek = context.getVolledigBericht().getAdministratieveHandeling()
            .getBijgehoudenPersonen().get(0).getOnderzoeken().iterator().next().getOnderzoek().getGegevensInOnderzoek().iterator().next();
        Assert.assertEquals(HEBBEN_WE_HET_JUISTE_ONDERZOEK_TE_PAKKEN, Integer.valueOf(1), gegevenInOnderzoek.getID());
    }

    @Test
    public void testVoerStapUitGeenOnderzoeken() {
        ReflectionTestUtils.setField(persoonHisVolledig, "onderzoeken", new HashSet<OnderzoekHisVolledig>());

        final boolean resultaatStap = autoriseerOnderzoekenStap.voerStapUit(null, context, resultaat);
        Assert.assertTrue(resultaatStap);

        // Geen vlaggen gezet op de attributen.
        Assert.assertEquals(0, context.getVolledigBericht().getAdministratieveHandeling().getBijgehoudenPersonen().get(0).getOnderzoeken().size());
    }

    @Test
    public void testVoerStapUitGeenGegevenInOnderzoek() {
        ReflectionTestUtils.setField(persoonHisVolledig.getOnderzoeken().iterator().next().getOnderzoek(), "gegevensInOnderzoek",
                                     new HashSet<GegevenInOnderzoekHisVolledigImpl>());

        final boolean resultaatStap = autoriseerOnderzoekenStap.voerStapUit(null, context, resultaat);
        Assert.assertTrue(resultaatStap);

        // Geen vlaggen gezet op de attributen.
        final Set<? extends PersoonOnderzoekHisVolledig> onderzoeken =
            context.getVolledigBericht().getAdministratieveHandeling().getBijgehoudenPersonen().get(0).getOnderzoeken();
        Assert.assertEquals(1, onderzoeken.size());
        Assert.assertEquals(0, onderzoeken.iterator().next().getOnderzoek().getGegevensInOnderzoek().size());
    }

    @Test
    public void testVoerStapUitGeenAttribuutMap() {
        // autoriseer het attribuut dat in onderzoek staat, vervolgens wordt dit vlaggetje overgenomen op het gegevenInOnderzoek door deze stap.
        PersoonViewUtil.zetMagGeleverdWordenVlagOpAttribuut(attribuutInOnderzoek);

        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = new HashMap<>();
        persoonOnderzoekenMap.put(1, null);
        context.setPersoonOnderzoekenMap(persoonOnderzoekenMap);

        final boolean resultaatStap = autoriseerOnderzoekenStap.voerStapUit(null, context, resultaat);
        Assert.assertTrue(resultaatStap);

        // Geen vlaggen gezet op de attributen.
        final GegevenInOnderzoekHisVolledig gegevenInOnderzoek = context.getVolledigBericht().getAdministratieveHandeling()
            .getBijgehoudenPersonen().get(0).getOnderzoeken().iterator().next().getOnderzoek().getGegevensInOnderzoek().iterator().next();
        Assert.assertEquals(HEBBEN_WE_HET_JUISTE_ONDERZOEK_TE_PAKKEN, Integer.valueOf(1), gegevenInOnderzoek.getID());
    }

    @Test
    public void testVoerStapUitLegeAttribuutMap() {
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = new HashMap<>();
        persoonOnderzoekenMap.put(1, new HashMap<Integer, List<Attribuut>>());
        context.setPersoonOnderzoekenMap(persoonOnderzoekenMap);

        final boolean resultaatStap = autoriseerOnderzoekenStap.voerStapUit(null, context, resultaat);
        Assert.assertTrue(resultaatStap);

        // Geen vlaggen gezet op de attributen.
        final GegevenInOnderzoekHisVolledig gegevenInOnderzoek = context.getVolledigBericht().getAdministratieveHandeling()
            .getBijgehoudenPersonen().get(0).getOnderzoeken().iterator().next().getOnderzoek().getGegevensInOnderzoek().iterator().next();
        Assert.assertEquals(HEBBEN_WE_HET_JUISTE_ONDERZOEK_TE_PAKKEN, Integer.valueOf(1), gegevenInOnderzoek.getID());
    }

    private AdministratieveHandelingModel maakAdministratieveHandelingModel(final Long id,
                                                                            final SoortAdministratieveHandeling soortAdministratieveHandeling,
                                                                            final Integer partijCode)
    {
        final AdministratieveHandelingModel administratieveHandeling =
            new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(soortAdministratieveHandeling),
                new PartijAttribuut(TestPartijBuilder.maker().metCode(partijCode).maak()),
                null,
                new DatumTijdAttribuut(new Date()));
        ReflectionTestUtils.setField(administratieveHandeling, "iD", id);
        return administratieveHandeling;
    }
}
