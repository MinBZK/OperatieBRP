/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.mutatielevering.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class HaalBijgehoudenPersonenHisVolledigOpStapTest {

    private static final Integer PERSOON_ID_2 = 654321;
    private static final String IDENTIFICATIENUMMERS_BURGERSERVICENUMMER = "$identificatienummers.burgerservicenummer";

    @InjectMocks
    private final HaalBijgehoudenPersonenHisVolledigOpStap haalBijgehoudenPersonenHisVolledigOpStap = new HaalBijgehoudenPersonenHisVolledigOpStap();

    @Mock
    private BlobifierService blobifierService;

    private AdministratieveHandelingMutatie onderwerp;
    private final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
    private final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();

    private final List<PersoonHisVolledigImpl> personenVolledig = new ArrayList<>();

    private final PersoonHisVolledigImpl persoonHisVolledig1 = TestPersoonJohnnyJordaan.maak();
    private final PersoonHisVolledigImpl persoonHisVolledig3 = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

    @Mock
    private PersoonHisVolledigImpl persoonHisVolledig2;

    @Mock
    private StamTabelService stamTabelService;

    @Mock
    private ExpressieService expressieService;

    private final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

    @Before
    @SuppressWarnings("unchecked")
    public final void setup() throws ExpressieExceptie {
        zetVoorkomensleutelGoedVoorGegevenInOnderzoek(persoonHisVolledig1);

        final Element persoonElement = TestElementBuilder.maker().metId(1).metElementNaam(ElementEnum.PERSOON.getNaam()).maak();
        final Element identificatienummersElement = TestElementBuilder.maker().metId(2).metElementNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS
                                                                                                           .getNaam()).maak();

        final List<Integer> bijgehoudenPersoonIds = new ArrayList<>();
        bijgehoudenPersoonIds.add(persoonHisVolledig1.getID());
        bijgehoudenPersoonIds.add(PERSOON_ID_2);

        context.setBijgehoudenPersoonIds(bijgehoudenPersoonIds);

        when(persoonHisVolledig2.getID()).thenReturn(PERSOON_ID_2);

        personenVolledig.add(persoonHisVolledig1);
        personenVolledig.add(persoonHisVolledig3);

        when(blobifierService.leesBlobs(anyList())).thenReturn(personenVolledig);

        final Collection<Element> allElementen = new ArrayList<>();
        final Element element1 = TestElementBuilder.maker().metExpressie(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER)
            .metSoort(SoortElement.ATTRIBUUT).metElementObjectType(persoonElement).metElementGroep(identificatienummersElement).maak();
        allElementen.add(element1);
        when(stamTabelService.geefAlleElementen()).thenReturn(allElementen);

        final Map<String, ParserResultaat> parserResultaatMap = new HashMap<>();
        final BrpAttribuutReferentieExpressie attrExpressie =
            new BrpAttribuutReferentieExpressie(persoonHisVolledig1.getPersoonIdentificatienummersHistorie().getActueleRecord().getBurgerservicenummer());

        final ParserResultaat testParserResultaat = new ParserResultaat(attrExpressie, null);
        parserResultaatMap.put(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, testParserResultaat);

        when(expressieService.evalueer(attrExpressie, persoonHisVolledig1)).thenReturn(attrExpressie);

        when(stamTabelService.geefAlleExpressieParserResultatenVanAttribuutElementenPerExpressie()).thenReturn(parserResultaatMap);

        onderwerp = new AdministratieveHandelingMutatie(administratieveHandelingModel.getID());

        when(stamTabelService.geefElementById(anyInt())).thenReturn(
            TestElementBuilder.maker().metId(1).metNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER)
                .metExpressie(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).metElementObjectType(persoonElement).metElementGroep(identificatienummersElement)
                .maak());
    }

    @Test
    public final void testVoerStapUitEnControleerSuccesvol() {
        //test
        haalBijgehoudenPersonenHisVolledigOpStap.voerStapUit(onderwerp, context, resultaat);
        //verify
        assertTrue(resultaat.isSuccesvol());
    }

    @Test
    public final void testVoerStapUitEnControleerRepositoryAanroep() {
        //test
        haalBijgehoudenPersonenHisVolledigOpStap.voerStapUit(onderwerp, context, resultaat);

        final ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(blobifierService).leesBlobs(captor.capture());
        assertEquals(context.getBijgehoudenPersoonIds(), captor.getValue());
    }

    @Test
    public final void testVoerStapUitEnControleerContextGevuld() {
        //test
        haalBijgehoudenPersonenHisVolledigOpStap.voerStapUit(onderwerp, context, resultaat);

        assertEquals(personenVolledig, context.getBijgehoudenPersonenVolledig());

        assertNotNull(context.getBijgehoudenPersonenAttributenMap());

        assertNotNull(context.getBijgehoudenPersonenAttributenMap().get(persoonHisVolledig1.getID()));

        final Map<String, List<Attribuut>> attributenMap = context.getBijgehoudenPersonenAttributenMap().get(persoonHisVolledig1.getID());

        assertNotNull(attributenMap);

        final List<Attribuut> attributen = attributenMap.get(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);

        assertNotNull(attributen);

        assertEquals(1, attributen.size());

        assertEquals("Ingeschrevene", persoonHisVolledig1.getSoort().getWaarde().getNaam());
    }

    @Test(expected = DataNietAanwezigExceptie.class)
    public final void testVoerStapUitKanBijgehoudenPersoonHisVolledigNietOphalen() {
        when(blobifierService.leesBlobs(anyListOf(Integer.class))).thenReturn(null);

        haalBijgehoudenPersonenHisVolledigOpStap.voerStapUit(
            onderwerp, context, resultaat);
    }

    @Test(expected = DataNietAanwezigExceptie.class)
    public final void testVoerStapUitBijgehoudenPersonenOngelijkAanBuigehoudenPersonen() {
        // Voeg een extra persoon toe aan betrokken personen
        final List<Integer> bijgehoudenPersoonIds = context.getBijgehoudenPersoonIds();
        bijgehoudenPersoonIds.add(123456);

        //test
        haalBijgehoudenPersonenHisVolledigOpStap.voerStapUit(onderwerp, context, resultaat);
    }

    /**
     * Zet voorkomensleutel goed voor gegeven in onderzoek, vanuit de builder is dit namelijk nog niet synchroon, aangezien daar de Identifiers nog niet
     * bekend zijn.
     *
     * @param persoonHisVolledig persoon his volledig
     */
    private void zetVoorkomensleutelGoedVoorGegevenInOnderzoek(final PersoonHisVolledigImpl persoonHisVolledig) {
        final Integer voorkomensleutel = persoonHisVolledig.getPersoonIdentificatienummersHistorie().getActueleRecord().getID();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoekHisVolledig =
            persoonHisVolledig.getOnderzoeken().iterator().next().getOnderzoek().getGegevensInOnderzoek().iterator().next();
        ReflectionTestUtils.setField(gegevenInOnderzoekHisVolledig, "voorkomenSleutelGegeven",
                                     new SleutelwaardeAttribuut(Long.valueOf(voorkomensleutel)));
    }
}
