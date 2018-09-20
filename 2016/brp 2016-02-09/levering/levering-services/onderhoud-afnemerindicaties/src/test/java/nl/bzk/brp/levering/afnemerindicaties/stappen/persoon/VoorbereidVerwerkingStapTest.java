/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContextImpl;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VoorbereidVerwerkingStapTest {

    private static final String IDENTIFICATIENUMMERS_BURGERSERVICENUMMER = "$identificatienummers.burgerservicenummer";

    @InjectMocks
    private final VoorbereidVerwerkingStap voorbereidVerwerkingStap = new VoorbereidVerwerkingStap();

    @Mock
    private StamTabelService stamTabelService;

    @Mock
    private ExpressieService expressieService;


    private final OnderhoudAfnemerindicatiesBerichtContext context = new OnderhoudAfnemerindicatiesBerichtContextImpl(new BerichtenIds(1L, 2L), null,
                                                                                                                      null, null);

    @Before
    public void setUp() throws Exception {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        zetVoorkomensleutelGoedVoorGegevenInOnderzoek(persoonHisVolledig);
        context.setPersoonHisVolledig(persoonHisVolledig);

        final Element elementPersoon = TestElementBuilder.maker().metId(1).metSoort(SoortElement.OBJECTTYPE)
            .metElementNaam(ElementEnum.PERSOON.getNaam()).maak();

        final Element elementPersoonIdentificatienummers = TestElementBuilder.maker().metId(2).metSoort(SoortElement.GROEP)
            .metElementNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS.getNaam()).maak();

        final Collection<Element> allElementen = new ArrayList<>();
        final Element element1 = TestElementBuilder.maker().metExpressie(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).metSoort(SoortElement.ATTRIBUUT)
            .metElementObjectType(elementPersoon).metElementGroep(elementPersoonIdentificatienummers).maak();
        allElementen.add(element1);
        when(stamTabelService.geefAlleElementen()).thenReturn(allElementen);

        final Map<String, ParserResultaat> parserResultaatMap = new HashMap<>();
        final BrpAttribuutReferentieExpressie attrExpressie =
            new BrpAttribuutReferentieExpressie(persoonHisVolledig.getPersoonIdentificatienummersHistorie().getActueleRecord().getBurgerservicenummer());

        final ParserResultaat testParserResultaat = new ParserResultaat(attrExpressie, null);
        parserResultaatMap.put(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, testParserResultaat);

        when(expressieService.evalueer(Mockito.any(Expressie.class), Mockito.any(PersoonHisVolledig.class))).thenReturn(attrExpressie);
        when(stamTabelService.geefAlleExpressieParserResultatenVanAttribuutElementenPerExpressie()).thenReturn(parserResultaatMap);
        when(stamTabelService.geefElementById(anyInt())).thenReturn(
            TestElementBuilder.maker().metId(1).metNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER)
                .metExpressie(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).metElementObjectType(elementPersoon)
                .metElementGroep(elementPersoonIdentificatienummers).maak());
    }

    @Test
    public void testVoerStapUit() {
        final OnderhoudAfnemerindicatiesResultaat resultaat = new OnderhoudAfnemerindicatiesResultaat(new ArrayList<Melding>());

        final boolean stapResultaat = voorbereidVerwerkingStap.voerStapUit(null, context, resultaat);

        Assert.assertTrue(stapResultaat);
    }

    private void zetVoorkomensleutelGoedVoorGegevenInOnderzoek(final PersoonHisVolledigImpl persoonHisVolledig) {
        final Integer voorkomensleutel = persoonHisVolledig.getPersoonIdentificatienummersHistorie().getActueleRecord().getID();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoekHisVolledig =
            persoonHisVolledig.getOnderzoeken().iterator().next().getOnderzoek().getGegevensInOnderzoek().iterator().next();
        ReflectionTestUtils.setField(gegevenInOnderzoekHisVolledig, "voorkomenSleutelGegeven" ,
                                     new SleutelwaardeAttribuut(Long.valueOf(voorkomensleutel)));
    }
}
