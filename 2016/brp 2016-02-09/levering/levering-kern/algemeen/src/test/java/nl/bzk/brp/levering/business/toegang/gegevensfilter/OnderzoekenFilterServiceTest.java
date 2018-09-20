/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class OnderzoekenFilterServiceTest {

    private static final String                   ID                       = "iD";
    private final              OnderzoekenFilterService onderzoekenFilterService = new OnderzoekenFilterServiceImpl();

    @Test
    public void testOntbrekendeGegevensFilter() {
        //bouw persoonslijst
        final List<PersoonHisVolledig> persoonHisVolledigViews = new ArrayList<>();
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        persoonHisVolledigViews.add(persoon);
        //filter eerste keer
        onderzoekenFilterService.filterOnderzoekGegevensNaarOntbrekendeGegevens(persoonHisVolledigViews);
        //tel huidige gegevens in onderzoek
        int huidigeGegevensInOnderzoek = 0;
        final Set<PersoonOnderzoekHisVolledigImpl> onderzoeken = persoon.getOnderzoeken();
        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoekHisVolledig : onderzoeken) {
            huidigeGegevensInOnderzoek += persoonOnderzoekHisVolledig.getOnderzoek().getGegevensInOnderzoek().size();
        }
        //voeg nog een onderzoek toe met gegeven in onderzoek met ontbrekende gegevens (geen objectsleutel en voorkomensleutel)
        final Element element = TestElementBuilder.maker().metId(10).metNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).metElementNaam(
            "Persoon.Identificatie.Bsn").maak();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoek = new GegevenInOnderzoekHisVolledigImplBuilder(element, null,
            null, true).build();
        final OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImplBuilder(true).nieuwStandaardRecord(20150909).datumAanvang(20150909)
            .omschrijving("is niet pluis").status(StatusOnderzoek.IN_UITVOERING).verwachteAfhandeldatum(20160101).eindeRecord()
            .voegGegevenInOnderzoekToe(gegevenInOnderzoek).build();
        final HisOnderzoekModel actueleRecord = onderzoek.getOnderzoekHistorie().getActueleRecord();
        ReflectionTestUtils.setField(actueleRecord, ID, 4);
        ReflectionTestUtils.setField(gegevenInOnderzoek, ID, 1);
        ReflectionTestUtils.setField(onderzoek, ID, 2);

        final Set<PersoonOnderzoekHisVolledigImpl> persoonOnderzoeken = persoon.getOnderzoeken();
        final PersoonOnderzoekHisVolledigImpl persoonOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(persoon, onderzoek, true)
            .nieuwStandaardRecord(20150909)
            .rol(SoortPersoonOnderzoek.DIRECT).eindeRecord().build();

        ReflectionTestUtils.setField(persoonOnderzoek, ID, 3);
        persoonOnderzoeken.add(persoonOnderzoek);
        //filter nog een keer
        onderzoekenFilterService.filterOnderzoekGegevensNaarOntbrekendeGegevens(persoonHisVolledigViews);
        //gegevens in onderzoek zou gelijk gebleven moeten zijn
        int nieuweGegevensInOnderzoek = 0;
        final Set<PersoonOnderzoekHisVolledigImpl> nieuweOnderzoeken = persoon.getOnderzoeken();
        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoekHisVolledig : nieuweOnderzoeken) {
            nieuweGegevensInOnderzoek += persoonOnderzoekHisVolledig.getOnderzoek().getGegevensInOnderzoek().size();
        }
        Assert.assertEquals(huidigeGegevensInOnderzoek, nieuweGegevensInOnderzoek);
    }
}
