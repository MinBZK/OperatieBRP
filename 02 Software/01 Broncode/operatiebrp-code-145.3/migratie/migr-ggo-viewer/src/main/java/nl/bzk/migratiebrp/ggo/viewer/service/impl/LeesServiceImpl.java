/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.ggo.viewer.OndersteundeFormatenEnum;
import nl.bzk.migratiebrp.ggo.viewer.converter.Lg01Converter;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStap;
import nl.bzk.migratiebrp.ggo.viewer.service.LeesService;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import nl.gba.gbav.am.DataRecord;
import nl.gba.gbav.impl.am.AMReaderFactoryImpl;
import nl.gba.gbav.impl.am.AMReaderImpl;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

/**
 * Verzorgt het inlezen van de ondersteunde bestandsformaten.
 */
@Component
public class LeesServiceImpl implements LeesService {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String VIEWER_CHAR_SET = "UTF-8";
    private static final String EXCEL_ERROR_MSG = "Fout bij het lezen van Excel";
    private static final String PARSE_FOUT_CODE = "Inlezen GBA-PL";
    private static final String PARSE_FOUT_OMSCHRIJVING = "niet geslaagd.";

    @Inject
    private Lo3SyntaxControle syntaxControle;

    @Inject
    private ExcelAdapter excelAdapter;

    @Inject
    private Lg01Converter lg01Converter;

    /**
     * {@inheritDoc}
     */
    @Override
    
    public final List<List<Lo3CategorieWaarde>> leesBestand(final String filename, final byte[] file, final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.LO3);

        List<List<Lo3CategorieWaarde>> persoonslijsten = null;
        try {
            final String bestandsnaam = filename.toLowerCase();
            final int startpuntExtensie = bestandsnaam.lastIndexOf('.') + 1;
            final String extensie = bestandsnaam.substring(startpuntExtensie);

            final OndersteundeFormatenEnum ondersteundFormaat = OndersteundeFormatenEnum.getByExtensie(extensie);
            if (ondersteundFormaat == null) {
                foutMelder.log(
                        LogSeverity.WARNING,
                        "Onbekende bestandsnaam",
                        "Ondersteunde extensies zijn: " + Arrays.toString(OndersteundeFormatenEnum.getBestandExtensies()));
            } else {
                switch (ondersteundFormaat) {
                    case NIC:
                        final byte[] lg01Bestand = convertToLg01Bestand(file, foutMelder);
                        persoonslijsten = leesLg01(lg01Bestand, foutMelder);
                        break;
                    case XLS:
                        persoonslijsten = leesExcel(file, foutMelder);
                        break;
                    case GBA:
                        persoonslijsten = leesAM(file, foutMelder);
                        break;
                    case TXT:
                    case LG01:
                    default:
                        persoonslijsten = leesLg01(file, foutMelder);
                }
            }
        } catch (final BerichtSyntaxException e /* Alle fouten afvangen en een nette melding op het scherm. */) {
            foutMelder.log(LogSeverity.ERROR, "Berichtsyntax", "Bericht syntax fout opgetreden.");
        } catch (final RuntimeException e /* Alle fouten afvangen en een nette melding op het scherm. */) {
            LOG.error("Er is iets fout gegaan bij het lezen van de PL", e);
            foutMelder.log(LogSeverity.ERROR, "Fout bij het lezen van het bestand", e.getMessage());
        }

        return persoonslijsten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    
    public final Lo3Persoonslijst parsePersoonslijstMetSyntaxControle(final List<Lo3CategorieWaarde> lo3CategorieWaarden, final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.LO3);

        Lo3Persoonslijst lo3Persoonslijst = null;
        try {
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3CategorieWaarden);
            lo3Persoonslijst = new Lo3PersoonslijstParser().parse(lo3InhoudNaSyntaxControle);
        } catch (final OngeldigePersoonslijstException ope) {
            foutMelder.log(LogSeverity.ERROR, PARSE_FOUT_CODE, PARSE_FOUT_OMSCHRIJVING);
        } catch (final RuntimeException e /* Alle fouten afvangen en een nette melding op het scherm. */) {
            LOG.error("Er is iets fout gegaan bij het parsen van de PL", e);
            foutMelder.log(LogSeverity.ERROR, PARSE_FOUT_CODE, PARSE_FOUT_OMSCHRIJVING);
            foutMelder.log(LogSeverity.ERROR, "", e.getMessage());
        }
        return lo3Persoonslijst;
    }

    private byte[] convertToLg01Bestand(final byte[] file, final FoutMelder foutMelder) {
        final String lg01Header = "00000000lg01";
        final String bericht = new String(file, Charset.forName(VIEWER_CHAR_SET));

        final StringBuilder result = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new StringReader(bericht));
        try {
            String regel = reader.readLine();
            final StringBuilder lelijkFormaatString = new StringBuilder();
            while (regel != null) {
                regel = regel.replace("_", "");
                lelijkFormaatString.append(regel.trim());
                regel = reader.readLine();
            }

            if (!bericht.toLowerCase().startsWith(lg01Header)) {
                result.append(lg01Header).append("0000000000000000000000000000000000000");
                result.append(String.format("%05d", lelijkFormaatString.length())).append(lelijkFormaatString);
            } else {
                result.append(lelijkFormaatString);
            }
        } catch (final IOException e) {
            foutMelder.log(LogSeverity.ERROR, "Ongeldig bericht", "Fout tijdens inlezen NIC-formaat");
            return null;
        }
        return result.toString().getBytes();
    }

    /**
     * Leest een lg01 formaat in en retourneert een lijst van Lo3CategorieWaarde. Bevat maar 1 PL.
     * @param file byte[]
     * @return List of Lo3CategorieWaarde
     */
    private List<List<Lo3CategorieWaarde>> leesLg01(final byte[] file, final FoutMelder foutMelder) throws BerichtSyntaxException {
        final String berichtString = new String(file, Charset.forName(VIEWER_CHAR_SET));
        final List<Lo3CategorieWaarde> lo3Inhoud = lg01Converter.converteerLg01NaarLo3CategorieWaarde(berichtString, foutMelder);
        if (lo3Inhoud == null) {
            return null;
        }
        return Collections.singletonList(lo3Inhoud);
    }

    /**
     * Leest een excel formaat in en retourneert een lijst van Lo3CategorieWaarde. Kan meerdere PLen bevatten.
     * @param file byte[]
     * @param foutMelder Het object om verwerkingsfouten aan te melden.
     * @return List of Lo3CategorieWaarde
     */
    private List<List<Lo3CategorieWaarde>> leesExcel(final byte[] file, final FoutMelder foutMelder) {
        try {
            // Lees excel
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new ByteArrayInputStream(file));
            if (excelDatas != null && !excelDatas.isEmpty()) {
                final List<List<Lo3CategorieWaarde>> lo3Persoonslijsten = new ArrayList<>();
                for (final ExcelData excelData : excelDatas) {
                    lo3Persoonslijsten.add(excelData.getCategorieLijst());
                }
                return lo3Persoonslijsten;
            } else {
                foutMelder.log(LogSeverity.ERROR, EXCEL_ERROR_MSG, "Bestand is leeg.");
            }
        } catch (final Lo3SyntaxException e) {
            foutMelder.log(LogSeverity.ERROR, EXCEL_ERROR_MSG, "Technische details: Lo3 syntax fout. " + e.getMessage());
        } catch (final ExcelAdapterException e) {
            foutMelder.log(LogSeverity.ERROR, EXCEL_ERROR_MSG, e.getMessage());
        }
        return null;
    }

    /**
     * Leest een AM formaat in en retourneert een lijst van Lo3CategorieWaarde. Kan meerdere PLen bevatten.
     * @param fileData byte[]
     * @param foutMelder Het object om verwerkingsfouten aan te melden.
     * @return List of Lo3CategorieWaarde
     */
    private List<List<Lo3CategorieWaarde>> leesAM(final byte[] fileData, final FoutMelder foutMelder) throws BerichtSyntaxException {
        final List<List<Lo3CategorieWaarde>> result = new ArrayList<>();

        final AMReaderFactoryImpl factory = new AMReaderFactoryImpl();
        final AMReaderImpl amReader = (AMReaderImpl) factory.create();

        File amFile = null;
        try {
            // Kopieer data naar server
            amFile = kopieerBestandNaarServer(fileData);

            // Lees AM bestand in
            amReader.setFiles(new File[]{amFile});
            amReader.open();

            while (amReader.hasData()) {
                // Maak Lg01 bericht van elk datarecord in bestand
                final DataRecord dr = amReader.readData();
                final String lg01 = dr.getBody();
                final List<Lo3CategorieWaarde> lo3Inhoud = lg01Converter.converteerLg01NaarLo3CategorieWaarde(lg01, foutMelder);
                result.add(lo3Inhoud);
            }
            return result;
        } catch (final IOException e) {
            foutMelder.log(LogSeverity.ERROR, "Bestandsfout bij uploaden AM", e.getMessage());
            return null;
        } finally {
            // Close AM bestand
            try {
                amReader.close();
            } catch (final IllegalStateException e) {
                // Trace melding ipv error omdat het niet toe doet of de reader netjes afgesloten wordt.
                // Als de source-code beschikbaar was, dan was dit een AutoClosable geworden en met try-with-resources
                // omgezet.
                LOG.debug("Fout tijdens sluiten van AM-reader", e);
            }

            // Clean up AM bestand(en)
            if (amFile != null) {
                verwijderBestand(amFile, foutMelder);
            }
        }
    }

    /**
     * Kopieert de bytes naar een tijdelijk bestand.
     * @param data byte array
     * @return file
     * @throws IOException als er fouten optreden tijdens het schrijven naar bestand
     */
    private File kopieerBestandNaarServer(final byte[] data) throws IOException {
        final File destFile = File.createTempFile("upload", OndersteundeFormatenEnum.GBA.getBestandExtensie());
        LOG.info("Kopieer data naar " + destFile.getAbsolutePath());

        FileUtils.writeByteArrayToFile(destFile, data);
        return destFile;
    }

    /**
     * Verwijderd de bestanden in de opgegeven directory.
     * @param amFile het AM-bestand dat verwijderd moet worden
     * @param foutMelder {@link FoutMelder} object waarin alle fouten worden gelogd
     * @return true als de verwijder actie geslaagd is
     */
    private boolean verwijderBestand(final File amFile, final FoutMelder foutMelder) {
        boolean success = false;
        try {
            success = amFile.delete();
            if (success) {
                LOG.info("Deleted uploaded file: " + amFile.getAbsolutePath());
            } else {
                LOG.info("Could not delete file: " + amFile.getAbsolutePath());
            }
        } catch (final SecurityException ex) {
            LOG.debug("Exception occured, could not delete file: " + amFile.getAbsolutePath(), ex);
            // schedule it
            try {
                FileUtils.forceDeleteOnExit(amFile);
                LOG.info("File scheduled for delete: " + amFile.getAbsolutePath());
            } catch (final IOException e) {
                foutMelder.log(LogSeverity.WARNING, "Bestandsfout bij verwijderen geuploade AM bestand", e.getMessage());
            }
        }
        return success;
    }
}
