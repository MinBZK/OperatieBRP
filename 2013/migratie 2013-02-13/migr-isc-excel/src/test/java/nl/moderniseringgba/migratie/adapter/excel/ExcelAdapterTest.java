/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.adapter.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;

import org.junit.Ignore;
import org.junit.Test;

public class ExcelAdapterTest {
    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

    @Test
    public void testMaakLo3Persoonslijsten() throws Exception {
        final InputStream excelBestand = ExcelAdapter.class.getResourceAsStream("/exceladapter/Test.xls");
        assertNotNull(excelBestand);

        // Lees excel
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(excelBestand);

        // Parsen input *ZONDER* syntax en precondite controles
        final List<Lo3Persoonslijst> persoonslijsten = new ArrayList<Lo3Persoonslijst>();
        for (final ExcelData excelData : excelDatas) {
            persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
        }

        assertEquals(1, persoonslijsten.size());
        final Lo3Persoonslijst persoonslijst = persoonslijsten.get(0);
        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = persoonslijst.getPersoonStapel();
        assertEquals(3, persoonStapel.size());
        final Lo3Categorie<Lo3PersoonInhoud> actueel = persoonStapel.get(2);
        assertEquals(Lo3GeslachtsaanduidingEnum.MAN.asElement(), actueel.getInhoud().getGeslachtsaanduiding());
        final Lo3Categorie<Lo3PersoonInhoud> historischNieuwst = persoonStapel.get(1);
        assertEquals(Lo3GeslachtsaanduidingEnum.MAN.asElement(), historischNieuwst.getInhoud()
                .getGeslachtsaanduiding());
        final Lo3Categorie<Lo3PersoonInhoud> historischOudst = persoonStapel.get(0);
        assertEquals(Lo3GeslachtsaanduidingEnum.VROUW.asElement(), historischOudst.getInhoud()
                .getGeslachtsaanduiding());

        final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels = persoonslijst.getNationaliteitStapels();
        assertEquals(2, nationaliteitStapels.size());

        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel0 = nationaliteitStapels.get(0);
        assertEquals(1, nationaliteitStapel0.size());
        final Lo3Categorie<Lo3NationaliteitInhoud> nat0 = nationaliteitStapel0.get(0);
        assertEquals("0001", nat0.getInhoud().getNationaliteitCode().getCode());
        assertEquals(null, nat0.getInhoud().getAanduidingBijzonderNederlandschap());
        assertEquals("000", nat0.getInhoud().getRedenVerkrijgingNederlandschapCode().getCode());
        assertNull(nat0.getInhoud().getRedenVerliesNederlandschapCode());

        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel1 = nationaliteitStapels.get(1);
        assertEquals(1, nationaliteitStapel1.size());
        final Lo3Categorie<Lo3NationaliteitInhoud> nat1 = nationaliteitStapel1.get(0);
        assertEquals("0002", nat1.getInhoud().getNationaliteitCode().getCode());
        assertNull(nat1.getInhoud().getAanduidingBijzonderNederlandschap());
        assertEquals("000", nat1.getInhoud().getRedenVerkrijgingNederlandschapCode().getCode());
        assertNull(nat1.getInhoud().getRedenVerliesNederlandschapCode());

        // 05: Huwelijk of Geregistreerd Partnerschap
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfStapels = persoonslijst.getHuwelijkOfGpStapels();
        assertEquals(2, huwelijkOfStapels.size());

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel0 = huwelijkOfStapels.get(0);
        assertEquals(2, huwelijkStapel0.size());
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk00 = huwelijkStapel0.get(0);
        assertEquals(new Lo3Datum(20040505), huwelijk00.getInhoud().getDatumSluitingHuwelijkOfAangaanGp());

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk01 = huwelijkStapel0.get(1);
        assertEquals(new Lo3Datum(20060101), huwelijk01.getInhoud().getDatumOntbindingHuwelijkOfGp());

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel1 = huwelijkOfStapels.get(1);
        assertEquals(1, huwelijkStapel1.size());
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk10 = huwelijkStapel1.get(0);
        assertEquals(new Lo3Datum(20100505), huwelijk10.getInhoud().getDatumSluitingHuwelijkOfAangaanGp());

        //
        assertEquals(3, persoonslijst.getReisdocumentStapels().size());
        assertEquals(1, persoonslijst.getOverlijdenStapel().size());
        assertEquals(1, persoonslijst.getVerblijfstitelStapel().size());
        assertEquals(1, persoonslijst.getKindStapels().size());
    }

    // @Test
    // public void testOntbrekendVeld() throws Exception {
    // final InputStream excelBestand =
    // ExcelAdapter.class.getResourceAsStream("/exceladapter/TestOntbrekendeGeslachtsaanduiding.xls");
    // try {
    //
    // final List<Lo3Persoonslijst> lo3s = ExcelAdapter.maakLo3Persoonslijsten(excelBestand);
    // for (final Lo3Persoonslijst lo3 : lo3s) {
    // lo3.valideer();
    // }
    // } catch (final NullPointerException exception) {
    // // assertTrue(exception.getMessage().contains("kolom C"));
    // assertTrue(exception.getMessage().contains("Geslachtsaanduiding"));
    // return;
    // }
    // fail("Exceptie verwacht voor ontbrekend veld geslachtsaanduiding");
    // }

    @Test
    @Ignore
    public void testVerkeerdGevuldVeld() throws Exception {
        final InputStream excelBestand =
                ExcelAdapterTest.class.getResourceAsStream("/exceladapter/TestInvalideGeslachtsaanduiding.xls");
        try {
            // Lees excel
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(excelBestand);

            // Parsen input *ZONDER* syntax en precondite controles
            final List<Lo3Persoonslijst> persoonslijsten = new ArrayList<Lo3Persoonslijst>();
            for (final ExcelData excelData : excelDatas) {
                persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
            }

        } catch (final ExcelAdapterException exception) {
            assertTrue(exception.getMessage().contains("kolom C"));
            assertTrue(exception.getMessage().contains("N"));
            assertTrue(exception.getMessage().contains("geslachtsaanduiding"));
            return;
        }
        fail("Exceptie verwacht voor invalide veld geslachtsaanduiding");
    }

    @Test
    public void testVerliesNationaliteit() throws Exception {
        final InputStream excelBestand =
                ExcelAdapterTest.class.getResourceAsStream("/exceladapter/TestVerliesNationaliteit.xls");
        // Lees excel
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(excelBestand);

        // Parsen input *ZONDER* syntax en precondite controles
        final List<Lo3Persoonslijst> persoonslijsten = new ArrayList<Lo3Persoonslijst>();
        for (final ExcelData excelData : excelDatas) {
            persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
        }

        assertEquals(1, persoonslijsten.size());
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels =
                persoonslijsten.get(0).getNationaliteitStapels();
        assertEquals(2, nationaliteitStapels.size());
        final Lo3NationaliteitCode codeNietNl = new Lo3NationaliteitCode("0104");
        final Lo3NationaliteitCode codeNl = new Lo3NationaliteitCode("0001");
        Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitNietNlStapel = null;
        Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitNlStapel = null;
        for (final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel : nationaliteitStapels) {
            if (codeNietNl.equals(nationaliteitStapel.get(0).getInhoud().getNationaliteitCode())) {
                nationaliteitNietNlStapel = nationaliteitStapel;
            } else if (codeNl.equals(nationaliteitStapel.get(0).getInhoud().getNationaliteitCode())) {
                nationaliteitNlStapel = nationaliteitStapel;
            }
        }
        assertNotNull(nationaliteitNlStapel);
        assertNotNull(nationaliteitNietNlStapel);
        assertEquals(2, nationaliteitNietNlStapel.size());
        assertEquals(1, nationaliteitNlStapel.size());
        assertNull(nationaliteitNietNlStapel.get(1).getInhoud().getNationaliteitCode());
        assertEquals(codeNl, nationaliteitNlStapel.get(0).getInhoud().getNationaliteitCode());
    }

    /**
     * Ging fout tijdens issue MIG-166
     * 
     * @throws Exception
     */
    @Test
    public void testOnjuistIndicatie() throws Exception {
        final InputStream excelBestand =
                ExcelAdapterTest.class.getResourceAsStream("/exceladapter/TestMetOnjuistIndicatieOK.xls");
        // Lees excel
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(excelBestand);

        // Parsen input *ZONDER* syntax en precondite controles
        final List<Lo3Persoonslijst> persoonslijsten = new ArrayList<Lo3Persoonslijst>();
        for (final ExcelData excelData : excelDatas) {
            persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
        }
    }

    @Test
    public void testMissendeCategorieAanduiding() throws Exception {
        final InputStream excelBestand =
                ExcelAdapterTest.class.getResourceAsStream("/exceladapter/TestZonderCategorieAanduiding.xls");
        try {
            // Lees excel
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(excelBestand);

            // Parsen input *ZONDER* syntax en precondite controles
            final List<Lo3Persoonslijst> persoonslijsten = new ArrayList<Lo3Persoonslijst>();
            for (final ExcelData excelData : excelDatas) {
                persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
            }

            fail("Exception verwacht omdat Categorie 1 element ontbreek");
        } catch (final ExcelAdapterException e) {
            assertTrue(e.getMessage()
                    .contains("Aanduiding voor een nieuwe categorie ontbreekt, verwacht op regel: 3"));
        }
    }

}
