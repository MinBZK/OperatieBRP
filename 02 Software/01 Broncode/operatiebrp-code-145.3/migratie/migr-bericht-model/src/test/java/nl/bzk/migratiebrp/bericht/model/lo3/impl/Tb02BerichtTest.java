/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractCategorieGebaseerdParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

/**
 * tbo2 bericht test.
 */
public class Tb02BerichtTest {

    // private static final Logger LOGGER = LoggerFactory.getLogger();

    private Tb02Bericht maakBericht(final List<Lo3CategorieWaarde> waarden, final String aktenummer) {
        final Tb02Bericht bericht = new Tb02Bericht();
        try {
            final Method parseInhoud = AbstractCategorieGebaseerdParsedLo3Bericht.class.getDeclaredMethod("parseCategorieen", List.class);
            parseInhoud.setAccessible(true);
            parseInhoud.invoke(bericht, waarden);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, aktenummer);
        bericht.setBronPartijCode("3333");
        bericht.setDoelPartijCode("5555");
        return bericht;
    }

    @Test
    public void testGetGerelateerdeAnummers() throws Exception {
        Tb02Bericht bericht = new Tb02Bericht();
        assertEquals(0, bericht.getGerelateerdeInformatie().getaNummers().size());

        bericht = maakBericht(maakLijst3ASluiting(), "3QA1234");
        assertEquals(1, bericht.getGerelateerdeInformatie().getaNummers().size());
        assertEquals("1234567890", bericht.getGerelateerdeInformatie().getaNummers().get(0));
    }

    @Test
    public void testGetWaarde() throws Exception {
        final Tb02Bericht bericht = maakBericht(maakLijst3ASluiting(), "3QA1234");

        assertNotNull("Element moet aanwezig zijn", bericht.get(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110));
    }

    @Test
    public void testParseFormat() throws Exception {
        final List<Lo3CategorieWaarde> waarden = maakLijst3ASluiting();
        final Tb02Bericht bericht = maakBericht(waarden, "3QA1234");

        final Method formatInhoud = AbstractCategorieGebaseerdParsedLo3Bericht.class.getDeclaredMethod("formatInhoud");
        formatInhoud.setAccessible(true);
        final List<Lo3CategorieWaarde> resultaat = (List<Lo3CategorieWaarde>) formatInhoud.invoke(bericht);
        assertEquals("De lijst die parse zet moet hetzelfde zijn als die format teruggeeft", waarden, resultaat);
    }

    //
    // @Test
    // public void testOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3ASluiting(), "3QA1234");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testNokOriginator() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst3ASluiting();
    // waarden.get(1).addElement(Lo3ElementEnum.ELEMENT_8110, "5555");
    // final Tb02Bericht bericht = maakBericht(waarden, "3QA1234");
    // bericht.setBronPartijCode("5555");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // LOGGER.info("Overtredingen: {}", overtredingen);
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals("Verzendende partij is geen GBA gemeente.", overtredingen.get(0));
    // }
    //
    // @Test
    // public void testNokRecipient() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3ASluiting(), "3QA1234");
    // bericht.setDoelPartijCode("4444");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // LOGGER.info("Overtredingen: {}", overtredingen);
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals("Ontvangende partij is geen BRP gemeente.", overtredingen.get(0));
    // }
    //
    // @Test
    // public void testNokGemeenteMoetOvereenkomen() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3ASluiting(), "3QA1234");
    // bericht.setBronPartijCode("4444");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // LOGGER.info("Overtredingen: {}", overtredingen);
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Niet alle voorkomens van 81.10 Registergemeente akte zijn gelijk (met de verzendende gemeente van het
    // bericht).",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testNokAktenummerMoetOvereenkomen() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3ASluiting(), "3QA1235");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // LOGGER.info("Overtredingen: {}", overtredingen);
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals("Niet alle voorkomens van 81.20 Aktenummer zijn gelijk (met het aktenummer in de kop van het
    // bericht).", overtredingen.get(0));
    // }
    //
    // @Test
    // public void testNokIngangsdatumInconsistent() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1CErkenning();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_8510, "20040102");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QC1234");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // for (final String overtreding : overtredingen) {
    // LOGGER.info("Overtreding: {}", overtreding);
    // }
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals("Niet alle voorkomens van 85.10 Ingangsdatum geldigheid zijn gelijk.", overtredingen.get(0));
    // }
    //
    // @Test
    // public void testNokOnbekendeAkte() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3ASluiting(), "4*X****");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // for (final String overtreding : overtredingen) {
    // LOGGER.info("Overtreding: {}", overtreding);
    // }
    // Assert.assertEquals(2, overtredingen.size());
    // Assert.assertEquals("Aktenummer 4*X**** (in de kop van het bericht) is ongeldig voor een Tb02-bericht.",
    // overtredingen.get(1));
    // }
    //
    // @Test
    // public void testNokSoortAkteControleNietOk() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = new ArrayList<>(maakLijst3ASluiting());
    // waarden.add(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_35, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "");
    // put(Lo3ElementEnum.ELEMENT_0240, "");
    // put(Lo3ElementEnum.ELEMENT_0310, "");
    // put(Lo3ElementEnum.ELEMENT_0320, "");
    // put(Lo3ElementEnum.ELEMENT_0330, "");
    // put(Lo3ElementEnum.ELEMENT_0410, "");
    // }
    // }));
    //
    // final Tb02Bericht bericht = maakBericht(waarden, "3QA1234");
    //
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // for (final String overtreding : overtredingen) {
    // LOGGER.info("Overtreding: {}", overtreding);
    // }
    // Assert.assertEquals(1, overtredingen.size());
    // }
    //
    // @Test
    // public void testControleerGroepen1HOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst1HGeslachtsnaamwijziging(), "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen1HNok0210Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1HGeslachtsnaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0210, "Clara");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QH1234 moet(en) element 02.30 en/of 02.40 gewijzigd zijn en mogen elementen 02.10 en 02.20 niet
    // gewijzigd zijn ten opzichte van categorie 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1HNok0220Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1HGeslachtsnaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0220, "P");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QH1234 moet(en) element 02.30 en/of 02.40 gewijzigd zijn en mogen elementen 02.10 en 02.20 niet
    // gewijzigd zijn ten opzichte van categorie 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1HNok0230en0240Ongewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1HGeslachtsnaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0230, "van");
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QH1234 moet(en) element 02.30 en/of 02.40 gewijzigd zijn en mogen elementen 02.10 en 02.20 niet
    // gewijzigd zijn ten opzichte van categorie 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1HNok0310Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1HGeslachtsnaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0310, "19700102");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QH1234 mogen elementen 03.10, 03.20 en 03.30 niet gewijzigd zijn ten opzichte van categorie
    // 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1HNok0320Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1HGeslachtsnaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0320, "5555");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QH1234 mogen elementen 03.10, 03.20 en 03.30 niet gewijzigd zijn ten opzichte van categorie
    // 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1HNok0330Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1HGeslachtsnaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0330, "6030");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QH1234 mogen elementen 03.10, 03.20 en 03.30 niet gewijzigd zijn ten opzichte van categorie
    // 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1HNok0410Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1HGeslachtsnaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0410, "M");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals("Voor aktenummer 1QH1234 mag element 04.10 niet gewijzigd zijn ten opzichte van categorie
    // 51.", overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1MOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst1MVoornaamwijziging(), "1QM1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen1MNok0210Ongewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1MVoornaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0210, "Truus");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QM1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QM1234 moet element 02.10 gewijzigd zijn en mogen elementen 02.20, 02.30 en 02.40 niet
    // gewijzigd zijn ten opzichte van categorie 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1MNok0220Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1MVoornaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0220, "P");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QM1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QM1234 moet element 02.10 gewijzigd zijn en mogen elementen 02.20, 02.30 en 02.40 niet
    // gewijzigd zijn ten opzichte van categorie 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1MNok0230Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1MVoornaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0230, "van der");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QM1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QM1234 moet element 02.10 gewijzigd zijn en mogen elementen 02.20, 02.30 en 02.40 niet
    // gewijzigd zijn ten opzichte van categorie 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1MNok0240Gewijzigd() throws Exception {
    // final List<Lo3CategorieWaarde> waarden = maakLijst1MVoornaamwijziging();
    // waarden.get(0).addElement(Lo3ElementEnum.ELEMENT_0240, "Plons");
    // final Tb02Bericht bericht = maakBericht(waarden, "1QM1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertEquals(1, overtredingen.size());
    // Assert.assertEquals(
    // "Voor aktenummer 1QM1234 moet element 02.10 gewijzigd zijn en mogen elementen 02.20, 02.30 en 02.40 niet
    // gewijzigd zijn ten opzichte van categorie 51.",
    // overtredingen.get(0));
    // }
    //
    // @Test
    // public void testControleerGroepen1SOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst1SGeslachtswijziging(), "1QS1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen1COk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst1CErkenning(), "1QC1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // LOGGER.info("Overtredingen: {}", overtredingen);
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen2AOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst2AOverlijden(), "2QA1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen3AOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3ASluiting(), "3QA1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen5AOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst5ASluiting(), "5QA1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen3BOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3BBeeindiging(), "3QB1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // LOGGER.info("Overtredingen: {}", overtredingen);
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen5BOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst5BBeeindiging(), "5QB1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen3HOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst3HOmzetting(), "3QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // LOGGER.info("Overtredingen: {}", overtredingen);
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // @Test
    // public void testControleerGroepen5HOk() throws Exception {
    // final Tb02Bericht bericht = maakBericht(maakLijst5HOmzetting(), "5QH1234");
    // final List<String> overtredingen = bericht.controleerTb02(maakPartijRegister());
    // Assert.assertTrue(overtredingen.isEmpty());
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst1HGeslachtsnaamwijziging() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "123456789");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van der");
    // put(Lo3ElementEnum.ELEMENT_0240, "Waterkering");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "1QH1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20150917");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst1SGeslachtswijziging() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "123456789");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20150917");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "M");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst1MVoornaamwijziging() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "1QM1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20150917");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Truus");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }));
    // }
    //
    private List<Lo3CategorieWaarde> maakLijst3ASluiting() {
        return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
                put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                put(Lo3ElementEnum.ELEMENT_0230, "van");
                put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                put(Lo3ElementEnum.ELEMENT_0310, "1970101");
                put(Lo3ElementEnum.ELEMENT_0320, "0599");
                put(Lo3ElementEnum.ELEMENT_0330, "6030");
                put(Lo3ElementEnum.ELEMENT_0410, "V");
            }
        }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0210, "Karel");
                put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
                put(Lo3ElementEnum.ELEMENT_0310, "19800101");
                put(Lo3ElementEnum.ELEMENT_0320, "0626");
                put(Lo3ElementEnum.ELEMENT_0330, "6030");
                put(Lo3ElementEnum.ELEMENT_0410, "M");
                put(Lo3ElementEnum.ELEMENT_0610, "20160101");
                put(Lo3ElementEnum.ELEMENT_0620, "0626");
                put(Lo3ElementEnum.ELEMENT_0630, "6030");
                put(Lo3ElementEnum.ELEMENT_1510, "H");
                put(Lo3ElementEnum.ELEMENT_8110, "3333");
                put(Lo3ElementEnum.ELEMENT_8120, "3QA1234");
                put(Lo3ElementEnum.ELEMENT_8510, "20150917");
            }
        }));
    }
    //
    // private List<Lo3CategorieWaarde> maakLijst5ASluiting() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "1970101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0599");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "M");
    // put(Lo3ElementEnum.ELEMENT_0610, "20160101");
    // put(Lo3ElementEnum.ELEMENT_0620, "0626");
    // put(Lo3ElementEnum.ELEMENT_0630, "6030");
    // put(Lo3ElementEnum.ELEMENT_1510, "P");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "5QA1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20150917");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst3BBeeindiging() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "1970101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0599");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "M");
    // put(Lo3ElementEnum.ELEMENT_0710, "20180101");
    // put(Lo3ElementEnum.ELEMENT_0720, "0626");
    // put(Lo3ElementEnum.ELEMENT_0730, "6030");
    // put(Lo3ElementEnum.ELEMENT_0740, "S");
    // put(Lo3ElementEnum.ELEMENT_1510, "H");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "3QB1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20150917");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0610, "20160101");
    // put(Lo3ElementEnum.ELEMENT_0620, "0626");
    // put(Lo3ElementEnum.ELEMENT_0630, "6030");
    // put(Lo3ElementEnum.ELEMENT_1510, "H");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst5BBeeindiging() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "1970101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0599");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "M");
    // put(Lo3ElementEnum.ELEMENT_0710, "20180101");
    // put(Lo3ElementEnum.ELEMENT_0720, "0626");
    // put(Lo3ElementEnum.ELEMENT_0730, "6030");
    // put(Lo3ElementEnum.ELEMENT_0740, "S");
    // put(Lo3ElementEnum.ELEMENT_1510, "P");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "5QB1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20150917");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0610, "20160101");
    // put(Lo3ElementEnum.ELEMENT_0620, "0626");
    // put(Lo3ElementEnum.ELEMENT_0630, "6030");
    // put(Lo3ElementEnum.ELEMENT_1510, "P");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst3HOmzetting() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "1970101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0599");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "M");
    // put(Lo3ElementEnum.ELEMENT_0610, "20180101");
    // put(Lo3ElementEnum.ELEMENT_0620, "0626");
    // put(Lo3ElementEnum.ELEMENT_0630, "6030");
    // put(Lo3ElementEnum.ELEMENT_1510, "H");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "3QH1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20190917");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0610, "20160101");
    // put(Lo3ElementEnum.ELEMENT_0620, "0626");
    // put(Lo3ElementEnum.ELEMENT_0630, "6030");
    // put(Lo3ElementEnum.ELEMENT_1510, "P");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst5HOmzetting() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "1970101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0599");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "M");
    // put(Lo3ElementEnum.ELEMENT_0610, "20180101");
    // put(Lo3ElementEnum.ELEMENT_0620, "0626");
    // put(Lo3ElementEnum.ELEMENT_0630, "6030");
    // put(Lo3ElementEnum.ELEMENT_1510, "P");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "5QH1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20190917");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Karel");
    // put(Lo3ElementEnum.ELEMENT_0240, "Sluis");
    // put(Lo3ElementEnum.ELEMENT_0310, "19800101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0610, "20160101");
    // put(Lo3ElementEnum.ELEMENT_0620, "0626");
    // put(Lo3ElementEnum.ELEMENT_0630, "6030");
    // put(Lo3ElementEnum.ELEMENT_1510, "H");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst2AOverlijden() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "1970101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0599");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.OVERLIJDEN, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0810, "20160917");
    // put(Lo3ElementEnum.ELEMENT_0820, "2222");
    // put(Lo3ElementEnum.ELEMENT_0830, "6030");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "2QA1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20160917");
    // }
    // }));
    // }
    //
    // private List<Lo3CategorieWaarde> maakLijst1CErkenning() {
    // return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>()
    // {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "123456789");
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van der");
    // put(Lo3ElementEnum.ELEMENT_0240, "Waterkering");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "1QC1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20040101");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
    // put(Lo3ElementEnum.ELEMENT_0230, "van");
    // put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
    // put(Lo3ElementEnum.ELEMENT_0310, "19700101");
    // put(Lo3ElementEnum.ELEMENT_0320, "3333");
    // put(Lo3ElementEnum.ELEMENT_0330, "1");
    // put(Lo3ElementEnum.ELEMENT_0410, "V");
    // }
    // }), new Lo3CategorieWaarde(Lo3CategorieEnum.OUDER_1, 0, 0, new HashMap<Lo3ElementEnum, String>() {
    // {
    // put(Lo3ElementEnum.ELEMENT_0110, "3457725543");
    // put(Lo3ElementEnum.ELEMENT_0120, "987654321");
    // put(Lo3ElementEnum.ELEMENT_0210, "Peter");
    // put(Lo3ElementEnum.ELEMENT_0230, "van der");
    // put(Lo3ElementEnum.ELEMENT_0240, "Waterkering");
    // put(Lo3ElementEnum.ELEMENT_0310, "19400101");
    // put(Lo3ElementEnum.ELEMENT_0320, "0626");
    // put(Lo3ElementEnum.ELEMENT_0330, "6030");
    // put(Lo3ElementEnum.ELEMENT_0410, "M");
    // put(Lo3ElementEnum.ELEMENT_6210, "20040101");
    // put(Lo3ElementEnum.ELEMENT_8110, "3333");
    // put(Lo3ElementEnum.ELEMENT_8120, "1QC1234");
    // put(Lo3ElementEnum.ELEMENT_8510, "20040101");
    // }
    // }));
    // }
}
