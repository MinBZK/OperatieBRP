/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.AbstractOngeldigLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.commons.io.IOUtils;

public class Lo3PersoonslijstTestHelper {
    private static final int LG01_BODY_OFFSET = 49;

    private Lo3PersoonslijstTestHelper() {
    }

    /**
     * Test helper methode om een lo3Persoonslijst te maken adhv een txt bestand.
     * @param filename De filenaam
     * @param foutMelder De foutMelder waar fouten gelogt worden.
     * @return Lijst met Lo3Persoonslijsten.
     * @throws IOException Als het lezen van het bestand niet goed ging.
     * @throws BerichtSyntaxException bij parse fouten
     * @throws Lo3SyntaxException bij syntax fouten
     * @throws ExcelAdapterException bij excel lees fouten
     */
    public static List<List<Lo3CategorieWaarde>> retrieveLo3CategorieWaarden(final String filename, final FoutMelder foutMelder) throws IOException,
            BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException {
        final InputStream inputStream = foutMelder.getClass().getClassLoader().getResourceAsStream(filename);
        List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = null;
        if (filename.toLowerCase().endsWith(".txt")) {
            lo3CategorieWaarde = leesLo3Persoonslijst(IOUtils.toByteArray(inputStream), foutMelder);
        } else if (filename.toLowerCase().endsWith(".xls")) {
            lo3CategorieWaarde = leesLo3PersoonslijstExcel(IOUtils.toByteArray(inputStream));
        } else {
            foutMelder.log(LogSeverity.WARNING, "Onbekende extensie", filename);
        }
        return lo3CategorieWaarde;
    }

    public static List<Lo3Persoonslijst> retrieveLo3Persoonslijsten(final String filename, final FoutMelder foutMelder) throws IOException,
            BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException {
        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = retrieveLo3CategorieWaarden(filename, foutMelder);
        final List<Lo3Persoonslijst> lo3Persoonslijsten = new ArrayList<>();
        final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
        for (final List<Lo3CategorieWaarde> catWaarde : lo3CategorieWaarde) {
            lo3Persoonslijsten.add(parser.parse(catWaarde));
        }
        return lo3Persoonslijsten;
    }

    private static List<List<Lo3CategorieWaarde>> leesLo3PersoonslijstExcel(final byte[] byteArray) throws ExcelAdapterException, Lo3SyntaxException {
        final ExcelAdapter excelAdapter = new ExcelAdapterImpl();
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new ByteArrayInputStream(byteArray));

        final List<List<Lo3CategorieWaarde>> lo3Persoonslijsten = new ArrayList<>();
        for (final ExcelData excelData : excelDatas) {
            lo3Persoonslijsten.add(excelData.getCategorieLijst());
        }
        return lo3Persoonslijsten;
    }

    private static List<List<Lo3CategorieWaarde>> leesLo3Persoonslijst(final byte[] byteArray, final FoutMelder foutMelder) throws BerichtSyntaxException {
        final List<List<Lo3CategorieWaarde>> lijst = new ArrayList<>();
        final String lg01 = new String(byteArray, Charset.forName("UTF8"));
        final Lo3BerichtFactory bf = new Lo3BerichtFactory();
        final Lo3Bericht lo3Bericht = bf.getBericht(lg01);

        if (lo3Bericht instanceof AbstractOngeldigLo3Bericht) {
            foutMelder.log(LogSeverity.ERROR, "Ongeldig bericht", ((AbstractOngeldigLo3Bericht) lo3Bericht).getMelding());
        } else if (lo3Bericht instanceof OnbekendBericht) {
            foutMelder.log(LogSeverity.ERROR, "Obekend bericht", ((OnbekendBericht) lo3Bericht).getMelding());
        } else {
            lijst.add(Lo3Inhoud.parseInhoud(lg01.substring(LG01_BODY_OFFSET)));
        }
        return lijst;
    }

    /**
     * Kopieert de meegegeven Lo3Persoonslijst naar een nieuwe Lo3Persoonslijst. Stapels en Categorien worden nieuw
     * aangemaakt en gevuld met dezelfde inhoud.
     * @param lo3Persoonslijst origineel
     * @param kopieerLo3Herkomst boolean
     * @return gekopieerde lo3Persoonslijst
     */
    public static Lo3Persoonslijst kopieerLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst, final boolean kopieerLo3Herkomst) {
        if (lo3Persoonslijst == null) {
            return null;
        }
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(kopieerStapel(lo3Persoonslijst.getPersoonStapel(), kopieerLo3Herkomst));
        builder.ouder1Stapel(kopieerStapel(lo3Persoonslijst.getOuder1Stapel(), kopieerLo3Herkomst));
        builder.ouder2Stapel(kopieerStapel(lo3Persoonslijst.getOuder2Stapel(), kopieerLo3Herkomst));
        builder.nationaliteitStapels(kopieerStapels(lo3Persoonslijst.getNationaliteitStapels(), kopieerLo3Herkomst));
        builder.huwelijkOfGpStapels(kopieerStapels(lo3Persoonslijst.getHuwelijkOfGpStapels(), kopieerLo3Herkomst));
        builder.overlijdenStapel(kopieerStapel(lo3Persoonslijst.getOverlijdenStapel(), kopieerLo3Herkomst));
        builder.inschrijvingStapel(kopieerStapel(lo3Persoonslijst.getInschrijvingStapel(), kopieerLo3Herkomst));
        builder.verblijfplaatsStapel(kopieerStapel(lo3Persoonslijst.getVerblijfplaatsStapel(), kopieerLo3Herkomst));
        builder.kindStapels(kopieerStapels(lo3Persoonslijst.getKindStapels(), kopieerLo3Herkomst));
        builder.verblijfstitelStapel(kopieerStapel(lo3Persoonslijst.getVerblijfstitelStapel(), kopieerLo3Herkomst));
        builder.gezagsverhoudingStapel(kopieerStapel(lo3Persoonslijst.getGezagsverhoudingStapel(), kopieerLo3Herkomst));
        builder.reisdocumentStapels(kopieerStapels(lo3Persoonslijst.getReisdocumentStapels(), kopieerLo3Herkomst));
        builder.kiesrechtStapel(kopieerStapel(lo3Persoonslijst.getKiesrechtStapel(), kopieerLo3Herkomst));
        return builder.build();
    }

    /**
     * Kopieert de gegevens in de meegegeven lo3Stapels naar een nieuwe List<Lo3Stapel>.
     * @param lo3Stapels List<Lo3Stapel>
     * @param kopieerLo3Herkomst boolean
     * @return gekopieerde lo3Stapels
     */
    private static <T extends Lo3CategorieInhoud> List<Lo3Stapel<T>> kopieerStapels(final List<Lo3Stapel<T>> lo3Stapels, final boolean kopieerLo3Herkomst) {
        final List<Lo3Stapel<T>> stapels = new ArrayList<>();
        for (final Lo3Stapel<T> lo3Stapel : lo3Stapels) {
            stapels.add(kopieerStapel(lo3Stapel, kopieerLo3Herkomst));
        }
        return stapels;
    }

    /**
     * Kopieert de gegevens in de meegegeven lo3Stapel naar een nieuwe Lo3Stapel.
     * @param lo3Stapel Lo3Stapel
     * @param kopieerLo3Herkomst boolean
     * @return gekopieerde lo3Stapel
     */
    private static <T extends Lo3CategorieInhoud> Lo3Stapel<T> kopieerStapel(final Lo3Stapel<T> lo3Stapel, final boolean kopieerLo3Herkomst) {
        if (lo3Stapel == null) {
            return null;
        }

        final List<Lo3Categorie<T>> categorieen = new ArrayList<>();
        for (final Lo3Categorie<T> categorie : lo3Stapel.getCategorieen()) {
            final Lo3Herkomst lo3Herkomst = kopieerLo3Herkomst ? categorie.getLo3Herkomst() : null;
            categorieen.add(new Lo3Categorie<>(categorie.getInhoud(), categorie.getDocumentatie(), categorie.getHistorie(), lo3Herkomst));
        }
        return new Lo3Stapel<>(categorieen);
    }
}
