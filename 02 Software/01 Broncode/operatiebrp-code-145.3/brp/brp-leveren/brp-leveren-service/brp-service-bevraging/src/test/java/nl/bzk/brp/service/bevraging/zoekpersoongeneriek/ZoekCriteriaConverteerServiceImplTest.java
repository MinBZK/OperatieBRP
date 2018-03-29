/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * ZoekCriteriaConverteerServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZoekCriteriaConverteerServiceImplTest {

    @InjectMocks
    private ZoekCriteriaWaardeConverteerServiceImpl zoekCriteriaConverteerService;
    @Mock
    private StamTabelService stamTabelService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testConverteerStamgegevenWaarde() throws StapException {
        final ObjectElement gemeenteObjectElement = getObjectElement(Element.GEMEENTE);
        final AttribuutElement attribuutElement = getAttribuutElement(Element.HUWELIJK_GEMEENTEAANVANGCODE);
        final Short code = 5;
        final Short id = 5;

        final Map<String, Object> stamgegevensMap = new HashMap<>();
        stamgegevensMap.put("id", id);
        stamgegevensMap.put("code", code);
        final List<Map<String, Object>> lijst = new ArrayList<>();
        lijst.add(stamgegevensMap);

        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(gemeenteObjectElement, Lists.newArrayList(attribuutElement),
                Lists.newArrayList(attribuutElement));
        final StamtabelGegevens stamgegevens = new StamtabelGegevens(stamgegevenTabel, lijst);

        Mockito.when(stamTabelService.geefStamgegevens("GemeenteTabel")).thenReturn(stamgegevens);

        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, code.toString());
        Assert.assertEquals(id, waardeGeconverteerd);
    }

    @Test
    public void testConverteerStamgegevenWaardePartijMetExtraVoorloopNullen() throws StapException {
        final ObjectElement partijObjectElement = getObjectElement(Element.PARTIJ);
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE);
        final Short code = 5;
        final Short id = 5;

        final Map<String, Object> stamgegevensMap = new HashMap<>();
        stamgegevensMap.put("id", id);
        stamgegevensMap.put("code", code);
        final List<Map<String, Object>> lijst = new ArrayList<>();
        lijst.add(stamgegevensMap);

        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(partijObjectElement, Lists.newArrayList(attribuutElement),
                Lists.newArrayList(attribuutElement));
        final StamtabelGegevens stamgegevens = new StamtabelGegevens(stamgegevenTabel, lijst);

        Mockito.when(stamTabelService.geefStamgegevens("PartijTabel")).thenReturn(stamgegevens);

        expectedException.expect(StapMeldingException.class);

        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, "00" + code.toString());


    }

    @Test(expected = StapMeldingException.class)
    public void testConverteerStamgegevenWaardeGeenCode() throws StapException {
        final ObjectElement gemeenteObjectElement = getObjectElement(Element.GEMEENTE);
        final AttribuutElement attribuutElement = getAttribuutElement(Element.HUWELIJK_GEMEENTEAANVANGCODE);
        final Short code = 5;
        final Short id = 5;
        final Map<String, Object> stamgegevensMap = new HashMap<>();
        stamgegevensMap.put("id", id);
        stamgegevensMap.put("geencode", code);
        final List<Map<String, Object>> lijst = new ArrayList<>();
        lijst.add(stamgegevensMap);

        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(gemeenteObjectElement, Lists.newArrayList(attribuutElement),
                Lists.newArrayList(attribuutElement));
        final StamtabelGegevens stamgegevens = new StamtabelGegevens(stamgegevenTabel, lijst);

        Mockito.when(stamTabelService.geefStamgegevens("GemeenteTabel")).thenReturn(stamgegevens);

        zoekCriteriaConverteerService.converteerWaarde(attribuutElement, code.toString());
    }

    @Test
    public void testBepalingStamgegevensgegevensNull() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.HUWELIJK_GEMEENTEAANVANGCODE);
        final Short code = 5;
        Mockito.when(stamTabelService.geefStamgegevens(Mockito.anyString())).thenReturn(null);

        expectedException.expect(StapException.class);
        expectedException.expectMessage("kan stamgegevens niet bepalen");

        zoekCriteriaConverteerService.converteerWaarde(attribuutElement, code.toString());
    }

    @Test
    public void testConverteerWaardeNull() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOAANVANG);
        final String waarde = null;
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, waarde);
        Assert.assertNull(waardeGeconverteerd);
    }

    @Test
    public void testConverteerXsdDatumWaarde() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM);
        final String waarde = "1978-06-26";
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, waarde);
        Assert.assertEquals(19780626, waardeGeconverteerd);
    }

    @Test
    public void testConverteerXsdOnbekendeDatumWaarde() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM);
        final String waarde = "1978-00-00";
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, waarde);
        Assert.assertEquals(19780000, waardeGeconverteerd);
    }

    @Test
    public void testConverteerBooleanWaarde() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG);
        final String waarde = "J";
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, waarde);
        Assert.assertEquals(Boolean.TRUE, waardeGeconverteerd);
    }

    @Test
    public void testConverteerStringWaarde() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM);
        final String waarde = "jansen";
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, waarde);
        Assert.assertEquals(waarde, waardeGeconverteerd);
    }

    @Test
    public void testConverteerBSNIntegerWaarde() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        final String waarde = "2301342693";
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, waarde);
        Assert.assertEquals(waarde, waardeGeconverteerd);
    }

    @Test
    public void testConverteerAnrLongWaarde() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        final String waarde = "2301342693";
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, waarde);
        Assert.assertEquals(waarde, waardeGeconverteerd);
    }

    @Test
    public void testConverteerDatumTijdWaarde() throws StapException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL);
        final String datumTijdStr = "2011-12-03T10:15:30+01:00";
        final Date datum = DatumUtil.vanXsdDatumTijdNaarDate(datumTijdStr);
        final Object waardeGeconverteerd = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, datumTijdStr);
        Assert.assertEquals(datum, waardeGeconverteerd);
    }

}
