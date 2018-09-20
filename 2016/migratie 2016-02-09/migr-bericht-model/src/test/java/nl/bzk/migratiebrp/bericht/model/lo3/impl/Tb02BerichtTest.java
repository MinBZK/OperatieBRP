/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import nl.bzk.migratiebrp.bericht.model.AkteOnbekendException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * tbo2 bericht test.
 */
public class Tb02BerichtTest {

    @Test
    public void testGetGerelateerdeAnummers() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        assertNull(bericht.getGerelateerdeAnummers());
    }

    @Test
    public void testGetWaarde() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        assertNotNull("Element moet aanwezig zijn", bericht.getWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110));
    }

    @Test
    public void testParseInhoud() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);

        Method formatInhoud = bericht.getClass().getDeclaredMethod("formatInhoud");
        formatInhoud.setAccessible(true);
        final List<Lo3CategorieWaarde> resultaat = (List<Lo3CategorieWaarde>) formatInhoud.invoke(bericht);
        assertEquals("De lijst die parse zet moet hetzelfde zijn als die format teruggeeft", waarden, resultaat);
    }

    @Test
    public void testFormatInhoud() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);

        Method formatInhoud = bericht.getClass().getDeclaredMethod("formatInhoud");
        formatInhoud.setAccessible(true);
        final List<Lo3CategorieWaarde> resultaat = (List<Lo3CategorieWaarde>) formatInhoud.invoke(bericht);
        assertEquals("De lijst die parse zet moet hetzelfde zijn als die format teruggeeft", waarden, resultaat);
    }

    @Test
    public void testControleerBerichtOpCorrectheidMetCorrectBericht() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        bericht.controleerBerichtOpCorrectheid();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testControleerBerichtOpCorrectheidMetInCorrectBericht() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluitingFoutief();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        bericht.controleerBerichtOpCorrectheid();
    }

    @Test(expected = AkteOnbekendException.class)
    public void testControleerBerichtOpCorrectheidMetFouteAkteInBericht() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3AQ1234");
        bericht.controleerBerichtOpCorrectheid();
    }

    @Test
    public void testGetSoortAkteCorrect() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        bericht.getSoortAkte();
    }

    @Test(expected = AkteOnbekendException.class)
    public void testGetSoortAkteInCorrect() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3AQ1234");
        bericht.getSoortAkte();
    }

    @Test
    public void testControleerOfRegisterGemeenteOvereenkomtMetVerzendendeGemeente() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        bericht.setBronGemeente("3333");
        bericht.controleerOfRegisterGemeenteOvereenkomtMetVerzendendeGemeente();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testControleerOfRegisterGemeenteOvereenkomtMetVerzendendeGemeenteMetFoutieveGemeente() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        bericht.setBronGemeente("4444");
        bericht.controleerOfRegisterGemeenteOvereenkomtMetVerzendendeGemeente();
    }

    @Test
    public void testIsAktenummerHetzelfdeAlsAktenummerInHeader() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        assertTrue(bericht.isAktenummerHetzelfdeAlsAktenummerInHeader());
    }

    @Test
    public void testIsAktenummerHetzelfdeAlsAktenummerInHeaderAnderNummer() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA6543");
        assertFalse(bericht.isAktenummerHetzelfdeAlsAktenummerInHeader());
    }

    @Test
    public void testIsAktenummerHetzelfdeAlsAktenummerInHeaderAnderSoortAkte() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QH1234");
        assertFalse(bericht.isAktenummerHetzelfdeAlsAktenummerInHeader());
    }

    @Test
    public void testIsIngangsdatumGelijkInMeegegevenAkte() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        parseInhoud.invoke(bericht, waarden);
        assertTrue(bericht.isIngangsdatumGelijkInMeegegevenAkten());
    }

    @Test
    public void testIsIngangsdatumGelijkInMeegegevenAkten() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstBetrokkenOuderMetNaamwijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QC1234");
        parseInhoud.invoke(bericht, waarden);
        assertTrue(bericht.isIngangsdatumGelijkInMeegegevenAkten());
    }

    @Test
    public void testIsIngangsdatumOngelijkInMeegegevenAkten() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstBetrokkenOuderMetNaamwijzigingVerkeerdeAkten();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QC1234");
        parseInhoud.invoke(bericht, waarden);
        assertFalse(bericht.isIngangsdatumGelijkInMeegegevenAkten());
    }

    @Test
    public void testParseInhoudOverbodigeCategorieen() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakOverbodigeCategorieen();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        parseInhoud.invoke(bericht, waarden);
    }

    @Test
    public void testParseInhoudOnbekendeAkte() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakOverbodigeCategorieen();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QC1234");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test
    public void testControleerGroep03OpWijzigingen3ACorrect() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstSluiting();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test
    public void testControleerOntbinding() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstOntbinding();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB1234");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testControleerOntbindingMetGeboortewijziging() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstOntbindingMetGeboortewijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testControleerOntbindingMetNaamwijziging() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstOntbindingMetNaamwijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testOntbindingMetVerschillendeSoortVerbintenissen() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstOntbindingVerschilSoortVerbintenis();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testOmzettingZonderOmzetting() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstOmzettingZonderVerschil();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QH1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test
    public void testNaamwijziging1H() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstNaamwijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QH1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testNaamwijziging1HFoutiefVoornaamGewijzigd() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstNaamwijzigingMetgewijzigdeVoornnaam();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QH1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testNaamwijziging1HFoutiefgewijzigdeGeboorte() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstNaamwijzigingMetGewijzigdeGeboorteGegevens();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QH1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test
    public void testVooraamwijziging1M() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstVoornaamwijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QM1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testVoornaamwijziging1MMetGeslachtswijziging() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstVoornaamwijzigingMetGeslachtswijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QM1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testVoornaamwijziging1MMetGeslachtsnaamwijziging() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstVoornaamwijzigingMetGeslachtsnaamwijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QM1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test
    public void testGeslachtswijziging1S() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstGeslachtswijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QS1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test
    public void testGeslachtswijziging1SMetVoornaamwijziging() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstGeslachtswijzigingMetVoornaamwijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QS1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testGeslachtswijziging1SMetGeslachtsnaamwijziging() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstGeslachtswijzigingMetGeslachtsnaamwijziging();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QS1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testGeslachtswijziging1SOngewijzigd() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden = maakLijstGeslachtswijzigingOngewijzigd();
        Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
        parseInhoud.setAccessible(true);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QS1234");
        bericht.setBronGemeente("3333");
        parseInhoud.invoke(bericht, waarden);
        bericht.controleerGroepenOpWijzigingen();
    }

    private List<Lo3CategorieWaarde> maakLijstSluiting() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_1510, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QA1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstOntbinding() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0710, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0720, "2222");
                        put(Lo3ElementEnum.ELEMENT_0730, "1");
                        put(Lo3ElementEnum.ELEMENT_0740, "GZ");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QB1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstOntbindingVerschilSoortVerbintenis() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0710, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0720, "2222");
                        put(Lo3ElementEnum.ELEMENT_0730, "1");
                        put(Lo3ElementEnum.ELEMENT_0740, "GZ");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QB1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "P");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstOmzettingZonderVerschil() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0610, "20140101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QB1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstSluitingFoutief() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0120, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "");
                        put(Lo3ElementEnum.ELEMENT_8120, "");
                        put(Lo3ElementEnum.ELEMENT_8510, "");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstOntbindingMetGeboortewijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0710, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0720, "2222");
                        put(Lo3ElementEnum.ELEMENT_0730, "1");
                        put(Lo3ElementEnum.ELEMENT_0740, "GZ");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QB1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900202");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstOntbindingMetNaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0710, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0720, "2222");
                        put(Lo3ElementEnum.ELEMENT_0730, "1");
                        put(Lo3ElementEnum.ELEMENT_0740, "GZ");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QB1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Hartog");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstNaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QH1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "in het");
                        put(Lo3ElementEnum.ELEMENT_0240, "Veld");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstNaamwijzigingMetgewijzigdeVoornnaam() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QH1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Truus");
                        put(Lo3ElementEnum.ELEMENT_0230, "in het");
                        put(Lo3ElementEnum.ELEMENT_0240, "Veld");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstNaamwijzigingMetGewijzigdeGeboorteGegevens() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QH1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "in het");
                        put(Lo3ElementEnum.ELEMENT_0240, "Veld");
                        put(Lo3ElementEnum.ELEMENT_0310, "19800101");
                        put(Lo3ElementEnum.ELEMENT_0320, "4444");
                        put(Lo3ElementEnum.ELEMENT_0330, "2");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstVoornaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QM1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Truus");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstVoornaamwijzigingMetGeslachtswijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0210, "Henk");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "M");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Truus");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstVoornaamwijzigingMetGeslachtsnaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0210, "Henk");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "M");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Truus");
                        put(Lo3ElementEnum.ELEMENT_0230, "de");
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstGeslachtswijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "M");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstGeslachtswijzigingOngewijzigd() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstGeslachtswijzigingMetVoornaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Henk");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "M");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstGeslachtswijzigingMetGeslachtsnaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Henk");
                        put(Lo3ElementEnum.ELEMENT_0230, "de");
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "M");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstBetrokkenOuderMetNaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QC1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.OUDER_1, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_6210, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QC1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstBetrokkenOuderMetNaamwijzigingVerkeerdeAkten() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QC1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150918");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.OUDER_1, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_6210, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QC1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakOverbodigeCategorieen() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_35, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_1510, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QA1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }));
    }
}