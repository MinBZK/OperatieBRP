/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import nl.bzk.migratiebrp.conversie.vragen.ZoekCriterium;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Testen.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/vragen-test.xml")
public class ConversieLo3NaarBrpVragenImplTest {

    @Inject
    private ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen;

    @Test
    public void testConverterenRubriekZonderSpeciaalVragenMapping() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_0240, "Tijn");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.SamengesteldeNaam.Geslachtsnaamstam", zoekcriteria.get(0).getElement());
    }

    @Test
    public void testConverterenAdellijkeTitel() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_0220, "P");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.SamengesteldeNaam.AdellijkeTitelCode", zoekcriteria.get(0).getElement());
    }

    @Test
    public void testConverterenPredicaat() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_0220, "JV");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.SamengesteldeNaam.PredicaatCode", zoekcriteria.get(0).getElement());
    }

    @Test
    public void testConverterenRubriekZonderWaarde() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_0230, null);
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 2 zijn", 2, zoekcriteria.size());
        Assert.assertEquals("Persoon.SamengesteldeNaam.Voorvoegsel", zoekcriteria.get(0).getElement());
        Assert.assertEquals("Persoon.SamengesteldeNaam.Scheidingsteken", zoekcriteria.get(1).getElement());
    }


    @Test
    public void testConverterenGemeenteNaarPartij076910() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_6910, "1901");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.Persoonskaart.PartijCode", zoekcriteria.get(0).getElement());
        Assert.assertEquals("190101", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenGemeenteNaarPartij080910() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_0910, "1901");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.Adres.GemeenteCode", zoekcriteria.get(0).getElement());
        Assert.assertEquals("1901", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenGemeenteNaarPartij() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_0910, "0599");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.Adres.GemeenteCode", zoekcriteria.get(0).getElement());
        Assert.assertEquals("0599", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenUitsluitingKiesrechtA() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_3810, "A");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.UitsluitingKiesrecht.Indicatie", zoekcriteria.get(0).getElement());
        Assert.assertEquals("J", zoekcriteria.get(0).getWaarde());
    }
    @Test
    public void testConverterenUitsluitingKiesrechtLeeg() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_3810, "");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.UitsluitingKiesrecht.Indicatie", zoekcriteria.get(0).getElement());
        Assert.assertEquals(null, zoekcriteria.get(0).getWaarde());
    }


    @Test
    public void testConverterenDeelnameEUKiesrechtUitgesloten() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_3110, "1");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.DeelnameEUVerkiezingen.IndicatieDeelname", zoekcriteria.get(0).getElement());
        Assert.assertEquals("N", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenDeelnameEUKiesrechtOntvangtOproep() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_3110, "2");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.DeelnameEUVerkiezingen.IndicatieDeelname", zoekcriteria.get(0).getElement());
        Assert.assertEquals("J", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenDeelnameEUKiesrechtLeeg() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_3110, "");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.DeelnameEUVerkiezingen.IndicatieDeelname", zoekcriteria.get(0).getElement());
        Assert.assertEquals(null, zoekcriteria.get(0).getWaarde());
    }


    @Test
    public void testConverterenStaatloos() throws Exception {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_0510, "0499");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Persoon.Indicatie.Staatloos.Waarde", zoekcriteria.get(0).getElement());
        Assert.assertEquals("J", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenIdentificatieNummerAdresseerbaarObject() {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_1180, "0001234567891234");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Voorloop nullen moeten blijven staan", "0001234567891234", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenIdentificatieCodeNummerAanduiding() {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_1190, "0001234567891234");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Voorloop nullen moeten blijven staan", "0001234567891234", zoekcriteria.get(0).getWaarde());
    }

    @Test
    public void testConverterenOpschortingLeeg() {
        final List<Lo3CategorieWaarde> waarden = new ArrayList<>();
        final Map<Lo3ElementEnum, String> elementWaarden = new HashMap<>();
        elementWaarden.put(Lo3ElementEnum.ELEMENT_6720, "");
        waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, 0, 0, elementWaarden));
        final List<ZoekCriterium> zoekcriteria = conversieLo3NaarBrpVragen.converteer(waarden);
        Assert.assertEquals("Aantal zoekcriteria moet 1 zijn", 1, zoekcriteria.size());
        Assert.assertEquals("Moet geen lege waarde zijn", "A", zoekcriteria.get(0).getWaarde());
    }
}
