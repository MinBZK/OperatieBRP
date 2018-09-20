/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unittest voor {@link MarkeerAttributenInOnderzoekStap}
 */
@RunWith(MockitoJUnitRunner.class)
public class MarkeerAttributenInOnderzoekStapTest {

    private static final String ID                      = "iD";
    private static final int    ELEMENT_ID              = 10;
    private static final int    GEGEVEN_IN_ONDERZOEK_ID = 1;
    private static final long   VOORKOMENSLEUTEL        = 2L;
    private static final long   OBJECTSLEUTEL           = 1L;

    @InjectMocks
    private MarkeerAttributenInOnderzoekStap stap;

    @Mock
    private Expressie elementExpressieMock;

    @Mock
    private Expressie attribuutExpressieMock;

    @Mock
    private HisPersoonAdresModel hisPersoonAdresModelMock;

    private LeveringautorisatieStappenOnderwerp    onderwerp;
    private LeveringautorisatieVerwerkingResultaat resultaat;
    private LeveringsautorisatieVerwerkingContext  context;
    private PersoonHisVolledigImpl                 johnny;
    private Partij                                 partij;
    private DatumTijdAttribuut                     datumTijdAttribuut;

    @Before
    public void before() throws ExpressieExceptie {
        johnny = TestPersoonJohnnyJordaan.maak();
        bouwOnderzoek(johnny);

        datumTijdAttribuut = new DatumTijdAttribuut();
        datumTijdAttribuut.setGroep(hisPersoonAdresModelMock);

        onderwerp = new LeveringautorisatieStappenOnderwerpImpl(bouwLeveringinformatie(), 1L, Stelsel.BRP);
        final AdministratieveHandelingModel administratieveHandelingModel =
            bouwAdministratieveHandeling(new Date(), SoortAdministratieveHandeling.AANVANG_ONDERZOEK);
        ReflectionTestUtils.setField(administratieveHandelingModel, ID, 5L);
        context = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandelingModel, singletonList((PersoonHisVolledig) johnny), null, null,
            null);

        final Map<Integer, Map<String, List<Attribuut>>> personenAttributenMap = new HashMap<>();
        final Map<String, List<Attribuut>> expressieAttributenMap = mock(HashMap.class);
        personenAttributenMap.put(johnny.getID(), expressieAttributenMap);
        final List<Attribuut> attributen = singletonList((Attribuut) datumTijdAttribuut);
        when(expressieAttributenMap.get(anyString())).thenReturn(attributen);

        context.setPersoonAttributenMap(personenAttributenMap);

        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = new HashMap<>();
        final Map<Integer, List<Attribuut>> onderzoekenMap = new HashMap<>();
        onderzoekenMap.put(GEGEVEN_IN_ONDERZOEK_ID, attributen);
        persoonOnderzoekenMap.put(johnny.getID(), onderzoekenMap);
        context.setPersoonOnderzoekenMap(persoonOnderzoekenMap);

        resultaat = new LeveringautorisatieVerwerkingResultaat();

        when(hisPersoonAdresModelMock.getID()).thenReturn((int) VOORKOMENSLEUTEL);
    }

    @Test
    public void zetMagGeleverdWordenOpVoorkomenInOnderzoek() {
        voegBerichtToeAanContext(SoortSynchronisatie.MUTATIEBERICHT, SoortAdministratieveHandeling.AANVANG_ONDERZOEK);

        stap.voerStapUit(onderwerp, context, resultaat);

        assertThat(datumTijdAttribuut.isInOnderzoek(), is(true));
    }

    @Test
    public void zetMagGeleverdWordenNietAlsGeenMutatieBericht() {
        voegBerichtToeAanContext(SoortSynchronisatie.VOLLEDIGBERICHT, SoortAdministratieveHandeling.BEEINDIGING_ONDERZOEK);

        stap.voerStapUit(onderwerp, context, resultaat);

        assertThat(datumTijdAttribuut.isInOnderzoek(), is(false));
    }

    @Test
    public void zetMagGeleverdWordenNietAlsGeenOnderzoekAdministratieveHandeling() {
        voegBerichtToeAanContext(SoortSynchronisatie.VOLLEDIGBERICHT, SoortAdministratieveHandeling.WIJZIGING_GESLACHTSAANDUIDING);

        stap.voerStapUit(onderwerp, context, resultaat);

        assertThat(datumTijdAttribuut.isInOnderzoek(), is(false));
    }

    private Leveringinformatie bouwLeveringinformatie() {

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("testabo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(new DatumAttribuut(20150101)).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak();


        return new Leveringinformatie(toegangLeveringsautorisatie, dienst);
    }

    private void bouwOnderzoek(final PersoonHisVolledigImpl persoon) {
        final ActieModel actieOnderzoek =
            bouwActie(5L, SoortActie.REGISTRATIE_ONDERZOEK, SoortAdministratieveHandeling.AANVANG_ONDERZOEK, new DatumAttribuut(19600101).toDate());

        final String elementExpressieString = "elementExpressieString";
        final Element element = TestElementBuilder.maker().metId(ELEMENT_ID).metNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER)
            .metElementNaam(
                "Persoon.Identificatie.Bsn").metExpressie(elementExpressieString).maak();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoek = new GegevenInOnderzoekHisVolledigImplBuilder(element,
            new SleutelwaardeAttribuut(
                OBJECTSLEUTEL),
            new SleutelwaardeAttribuut(
                VOORKOMENSLEUTEL), true).build();

        final OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImplBuilder(true).nieuwStandaardRecord(actieOnderzoek).datumAanvang(20150909)
            .omschrijving("is niet pluis").status(StatusOnderzoek.IN_UITVOERING).verwachteAfhandeldatum(20160101).eindeRecord()
            .voegGegevenInOnderzoekToe(gegevenInOnderzoek).build();
        final HisOnderzoekModel actueleRecord = onderzoek.getOnderzoekHistorie().getActueleRecord();
        ReflectionTestUtils.setField(actueleRecord, ID, 5);
        ReflectionTestUtils.setField(gegevenInOnderzoek, ID, GEGEVEN_IN_ONDERZOEK_ID);
        ReflectionTestUtils.setField(onderzoek, ID, 2);

        final Set<PersoonOnderzoekHisVolledigImpl> persoonOnderzoeken = persoon.getOnderzoeken();
        final PersoonOnderzoekHisVolledigImpl persoonOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(persoon, onderzoek, true)
            .nieuwStandaardRecord(actieOnderzoek)
            .rol(SoortPersoonOnderzoek.DIRECT).eindeRecord().build();
        ReflectionTestUtils.setField(persoonOnderzoek, ID, 3);
        persoonOnderzoeken.add(persoonOnderzoek);
    }

    private void voegBerichtToeAanContext(final SoortSynchronisatie soortSynchronisatie, final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
            soortAdministratieveHandeling), new PartijAttribuut(partij), new OntleningstoelichtingAttribuut("ontlening"), new
            DatumTijdAttribuut(new Date()));
        final AdministratieveHandelingSynchronisatie administratieveHandelingSynchronisatie = new AdministratieveHandelingSynchronisatie(
            administratieveHandeling);
        administratieveHandelingSynchronisatie.setBijgehoudenPersonen(singletonList(new PersoonHisVolledigView(johnny, null)));
        ReflectionTestUtils.setField(administratieveHandelingSynchronisatie, ID, 5L);
        final SynchronisatieBericht synchronisatieBericht;
        switch (soortSynchronisatie) {
            case MUTATIEBERICHT:
                synchronisatieBericht = new MutatieBericht(administratieveHandelingSynchronisatie);
                break;
            case VOLLEDIGBERICHT:
                synchronisatieBericht = new VolledigBericht(administratieveHandelingSynchronisatie);
                break;
            default:
                synchronisatieBericht = null;
        }
        final List<SynchronisatieBericht> leveringBerichten = singletonList(synchronisatieBericht);
        context.setLeveringBerichten(leveringBerichten);
    }

    private ActieModel bouwActie(final Long actieId, final SoortActie soortActie, final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final Date tsreg)
    {

        final AdministratieveHandelingModel administratieveHandeling = bouwAdministratieveHandeling(tsreg, soortAdministratieveHandeling);
        ReflectionTestUtils.setField(administratieveHandeling, ID, actieId);

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(soortActie), administratieveHandeling,
                new PartijAttribuut(TestPartijBuilder.maker().metCode(36101).maak()),
                new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(tsreg)), null, new DatumTijdAttribuut(tsreg), null);
        ReflectionTestUtils.setField(actieModel, ID, actieId);
        final SortedSet<ActieModel> acties = new TreeSet<>(new IdComparator());
        acties.add(actieModel);
        ReflectionTestUtils.setField(administratieveHandeling, "acties", acties);

        return actieModel;
    }

    private AdministratieveHandelingModel bouwAdministratieveHandeling(final Date tsReg,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        return
            VerantwoordingTestUtil.bouwAdministratieveHandeling(soortAdministratieveHandeling,
                new PartijAttribuut(TestPartijBuilder.maker().metCode(36101).maak()), null,
                new DatumTijdAttribuut(tsReg));
    }

    private static final class MyElement extends Element {

        private MyElement(final ExpressietekstAttribuut expressietekstAttribuut) {
            super(null, null, null, null, null, null, null, expressietekstAttribuut, null, null, null, null, null, null,
                null, null);
        }
    }
}
