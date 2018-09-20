/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.algemeen.service.DienstFilterExpressiesService;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelGroepBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DienstFilterExpressiesServiceImplTest {

    private static final String WAAR = "WAAR";
    @Mock
    StamTabelService stamTabelService;

    @InjectMocks
    private final DienstFilterExpressiesService dienstFilterExpressiesService = new DienstFilterExpressiesServiceImpl();

    private final Element persoonAdresElement = TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_ADRES).maak();


    @Test
    public final void testMaakExpressieMetAlles() throws ExpressieExceptie {
        metElementen(true, true, true);
        final Dienst dienst = maakDienst(persoonAdresElement, true, true, true);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(6, expressie.aantalElementen());
    }

    @Test
    public final void testMaakExpressieLijstMetAlles() throws ExpressieExceptie {
        metElementen(true, true, true);
        final Dienst dienst = maakDienst(persoonAdresElement, true, true, true);
        final List<String> expressieStrings = dienstFilterExpressiesService
            .geefExpressiesVoorHistorieEnVerantwoordingAttributenLijst(dienst);
        assertEquals(6, expressieStrings.size());
    }

    @Test
    public final void testMaakExpressieMetEnkelFormeelAttribuut() throws ExpressieExceptie {
        metElementen(true, false, false);
        final Dienst dienst = maakDienst(persoonAdresElement,
            true, false, false);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(2, expressie.aantalElementen());
    }

    @Test
    public final void testMaakExpressieMetEnkelMaterieelAttribuut() throws ExpressieExceptie {
        metElementen(false, true, false);
        final Element groepElement = TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_ADRES).maak();
        final Dienst dienst = maakDienst(groepElement,
            false, true, false);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(1, expressie.aantalElementen());
    }

    @Test
    public final void testMaakExpressieMetEnkelVerantwoordingAttribuut() throws ExpressieExceptie {
        metElementen(false, false, true);
        final Element groepElement = TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_ADRES).maak();
        final Dienst dienst = maakDienst(groepElement,
            false, false, true);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(3, expressie.aantalElementen());
    }

    @Test
    public final void testLegeExpressieIndienAlleVlaggenUit() throws ExpressieExceptie {
        metElementen(true, true, true);
        final Element groepElement = TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_ADRES).maak();
        final Dienst dienst = maakDienst(groepElement,
            false, false, false);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(0, expressie.aantalElementen());
    }

    @Test
    public final void testLegeExpressieAlsHetGeenFormeelAttribuutBetreft() throws ExpressieExceptie {
        metElementen(false, true, true);
        //DienstFilterExpressiesService.BRP_ACTIE_AANPASSING_GELDIGHEID
        final Element groepElement = TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_ADRES).maak();
        final Dienst dienst = maakDienst(groepElement,
            true, false, false);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(0, expressie.aantalElementen());
    }

    @Test
    public final void testLegeExpressieAlsHetGeenMaterieelAttribuutBetreft() throws ExpressieExceptie {
        metElementen(true, false, true);
        final Element groepElement = TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_ADRES).maak();
        final Dienst dienst = maakDienst(groepElement,
            false, true, false);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(0, expressie.aantalElementen());
    }

    @Test
    public final void testLegeExpressieAlsHetGeenVerantwoordingAttribuutBetreft() throws ExpressieExceptie {
        metElementen(true, true, false);
        final Element groepElement = TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_ADRES).maak();
        final Dienst dienst = maakDienst(groepElement,
            false, false, true);
        final Expressie expressie = dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        assertEquals(0, expressie.aantalElementen());
    }

    @Test
    public void testHaalAlleFormeleMaterieleEnVerantwoordingExpressiesOp() throws ExpressieExceptie {
        metElementen(true, true, true);
        final Expressie expressie = dienstFilterExpressiesService.geefAllExpressiesVoorHistorieEnVerantwoordingAttributen();
        assertEquals(6, expressie.aantalElementen());
    }

    @Test
    public void testHaalAlleFormeleMaterieleEnVerantwoordingExpressiesLijstOp() throws ExpressieExceptie {
        metElementen(true, true, true);
        final List<String> expressieStrings = dienstFilterExpressiesService.geefAllExpressiesVoorHistorieEnVerantwoordingAttributenLijst();
        assertEquals(6, expressieStrings.size());
    }

    private Dienst maakDienst(final Element groepElement,
        final boolean magFormeleHistieZien,
        final boolean magMaterieleHistorieZien,
        final boolean magVerantwoordingZien)
    {

        final DienstbundelGroep dienstbundelGroep = TestDienstbundelGroepBuilder.maker().metGroep(groepElement).
                metIndicatieMaterieleHistorie(magMaterieleHistorieZien).
                metIndicatieFormeleHistorie(magFormeleHistieZien).
                metIndicatieVerantwoording(magVerantwoordingZien).maak();
        final Dienst dienst = TestDienstBuilder.maker().maak();
        TestDienstbundelBuilder.maker().metDiensten(dienst).metGroepen(dienstbundelGroep).maak();
       return dienst;
    }

    public void metElementen(final boolean formeel, final boolean materieel, final boolean verantwoording) {


        final List<Element> elementLijst = new LinkedList<>();

        if (formeel) {

            elementLijst.add(
                TestElementBuilder.maker()
                    .metNaam(ElementEnum.PERSOON_ADRES_TIJDSTIPVERVAL)
                    .metElementNaam(DienstFilterExpressiesService.DATUM_TIJD_VERVAL)
                    .metElementGroep(persoonAdresElement)
                    .metExpressie(WAAR)
                    .maak());
            elementLijst.add(
                TestElementBuilder.maker()
                    .metNaam(ElementEnum.PERSOON_ADRES_TIJDSTIPREGISTRATIE)
                    .metElementNaam(DienstFilterExpressiesService.DATUM_TIJD_REGISTRATIE)
                    .metElementGroep(persoonAdresElement)
                    .metExpressie(WAAR)
                    .maak());
        }

        if (materieel) {

            elementLijst.add(
                TestElementBuilder.maker()
                    .metNaam(ElementEnum.PERSOON_ADRES_DATUMEINDEGELDIGHEID)
                    .metElementNaam(DienstFilterExpressiesService.DATUM_EINDE_GELDIGHEID)
                    .metElementGroep(persoonAdresElement)
                    .metExpressie(WAAR)
                    .maak());
        }

        if (verantwoording) {
            elementLijst.add(
                TestElementBuilder.maker()
                    .metNaam(ElementEnum.PERSOON_ADRES_ACTIEAANPASSINGGELDIGHEID)
                    .metElementNaam(DienstFilterExpressiesService.BRP_ACTIE_AANPASSING_GELDIGHEID)
                    .metElementGroep(persoonAdresElement)
                    .metExpressie(WAAR)
                    .maak());

            elementLijst.add(
                TestElementBuilder.maker()
                    .metNaam(ElementEnum.PERSOON_ADRES_ACTIEINHOUD)
                    .metElementNaam(DienstFilterExpressiesService.BRP_ACTIE_INHOUD)
                    .metElementGroep(persoonAdresElement)
                    .metExpressie(WAAR)
                    .maak());

            elementLijst.add(
                TestElementBuilder.maker()
                    .metNaam(ElementEnum.PERSOON_ADRES_ACTIEVERVAL)
                    .metElementNaam(DienstFilterExpressiesService.BRP_ACTIE_VERVAL)
                    .metElementGroep(persoonAdresElement)
                    .metExpressie(WAAR)
                    .maak());
        }
        Mockito.when(stamTabelService.geefAlleElementen()).thenReturn(elementLijst);
    }


}
