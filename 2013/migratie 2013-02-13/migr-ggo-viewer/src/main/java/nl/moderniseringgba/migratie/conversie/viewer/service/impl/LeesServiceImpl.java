/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.gba.gbav.am.DataRecord;
import nl.gba.gbav.impl.am.AMReaderFactoryImpl;
import nl.gba.gbav.impl.am.AMReaderImpl;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OnbekendBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterException;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.service.LeesService;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

/**
 * Verzorgt het inlezen van de ondersteunde bestandsformaten.
 */
@Component
public class LeesServiceImpl implements LeesService {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String GBA_FILE_EXT = ".gba";
    private static final String XLS_FILE_EXT = ".xls";
    private static final String TXT_FILE_EXT = ".txt";

    @Inject
    private ExcelAdapter excelAdapter;

    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

    /**
     * Leest de Lo3Persoonslijst in uit een geupload bestand. Bestandstype is "Lg01 of AM" (Alternatieve Media; lo3.7
     * blz. 612) Volgens mij moet het inlezen van dit formaat nog vanaf 0 worden geschreven. Voor nu kunnen we ook het
     * test Excel formaat ondersteunen, dan hebben we alvast iets.
     * 
     * @param filename
     *            De naam van het bestand.
     * @param file
     *            De file zelf in een byte array.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return De lijst met Lo3Persoonslijsten.
     */
    @Override
    public final List<Lo3Persoonslijst> leesLo3Persoonslijst(
            final String filename,
            final byte[] file,
            final FoutMelder foutMelder) {
        List<Lo3Persoonslijst> persoonslijsten = null;

        try {
            if (filename.toLowerCase().endsWith(TXT_FILE_EXT)) {
                persoonslijsten = leesLg01(file, foutMelder);
            } else if (filename.toLowerCase().endsWith(XLS_FILE_EXT)) {
                persoonslijsten = leesExcel(file, foutMelder);
            } else if (filename.toLowerCase().endsWith(GBA_FILE_EXT)) {
                persoonslijsten = leesAM(filename, file, foutMelder);
            } else {
                foutMelder.log(LogSeverity.WARNING, "Onbekende extensie", filename);
            }
            // CHECKSTYLE:OFF - Alle fouten afvangen en een nette melding op het scherm.
        } catch (final RuntimeException e) { // NOSONAR
            // CHECKSTYLE:ON
            foutMelder.log(LogSeverity.ERROR, "Fout bij inlezen Lo3 persoonslijst", e);
        }

        // Maak lege lijst aan in geval van errors
        if (persoonslijsten == null) {
            persoonslijsten = new ArrayList<Lo3Persoonslijst>();
        }
        return persoonslijsten;
    }

    /**
     * Leest een lg01 formaat in en retourneert een lijst van Lo3Persoonslijst.
     * 
     * @param file
     *            byte[]
     * @return List of Lo3Persoonslijst
     */
    private List<Lo3Persoonslijst> leesLg01(final byte[] file, final FoutMelder foutMelder) {
        final String berichtString = new String(file);
        return Collections.singletonList(converteerLg01NaarLo3Persoonslijst(berichtString, foutMelder));
    }

    /**
     * Leest een excel formaat in en retourneert een lijst van Lo3Persoonslijst.
     * 
     * @param file
     *            byte[]
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return List of Lo3Persoonslijst
     */
    private List<Lo3Persoonslijst> leesExcel(final byte[] file, final FoutMelder foutMelder) {
        try {
            // Lees excel
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new ByteArrayInputStream(file));

            // Parsen input *ZONDER* syntax en precondite controles
            final List<Lo3Persoonslijst> lo3Persoonslijsten = new ArrayList<Lo3Persoonslijst>();
            for (final ExcelData excelData : excelDatas) {
                lo3Persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
            }

            return lo3Persoonslijsten;
        } catch (final IOException e) {
            foutMelder.log(LogSeverity.ERROR, "Bestandsfout bij uploaden Excel", e.getMessage());
        } catch (final ExcelAdapterException e) {
            foutMelder.log(LogSeverity.ERROR, "Fout bij het lezen van Excel", e.getMessage());
        }
        return null;
    }

    /**
     * Leest een AM formaat in en retourneert een lijst van Lo3Persoonslijst.
     * 
     * @param file
     *            byte[]
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return List of Lo3Persoonslijst
     */
    private List<Lo3Persoonslijst> leesAM(final String filename, final byte[] fileData, final FoutMelder foutMelder) {
        final List<Lo3Persoonslijst> result = new ArrayList<Lo3Persoonslijst>();

        final AMReaderFactoryImpl factory = new AMReaderFactoryImpl();
        final AMReaderImpl amReader = (AMReaderImpl) factory.create();

        File amFile = null;
        try {
            // Kopieer data naar server
            amFile = kopieerBestandNaarServer(filename, fileData);

            // Lees AM bestand in
            amReader.setFiles(new File[] { amFile });
            amReader.open();

            while (amReader.hasData()) {
                // Maak Lg01 bericht van elk datarecord in bestand
                final DataRecord dr = amReader.readData();
                final String lg01 = dr.getBody();
                result.add(converteerLg01NaarLo3Persoonslijst(lg01, foutMelder));
            }
            return result;
        } catch (final IOException e) {
            foutMelder.log(LogSeverity.ERROR, "Bestandsfout bij uploaden AM", e.getMessage());
            return null;
        } finally {
            // Close AM bestand
            amReader.close();

            // Clean up AM bestand(en)
            if (amFile != null) {
                verwijderBestand(amFile, foutMelder);
            }
        }
    }

    /**
     * Kopieert de bytes naar een Bestand met de opgegeven naam in de opgegeven upload directory.
     * 
     * @param uploadDirectory
     *            String
     * @param filename
     *            String
     * @param data
     *            byte array
     * @return file
     * @throws IOException
     *             als er fouten optreden tijdens het schrijven naar bestand
     */
    private File kopieerBestandNaarServer(final String filename, final byte[] data) throws IOException {

        final File destFile = File.createTempFile("upload", GBA_FILE_EXT);
        LOG.info("Kopieer data naar " + destFile.getAbsolutePath());

        FileUtils.writeByteArrayToFile(destFile, data);
        return destFile;
    }

    /**
     * Verwijderd de bestanden in de opgegeven directory.
     * 
     * @param uploadDirectory
     *            De upload directory
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
                foutMelder.log(LogSeverity.WARNING, "Bestandsfout bij verwijderen geuploade AM bestand",
                        e.getMessage());
            }
        }
        return success;
    }

    /**
     * Converteert de Lg01 body naar een Lo3Persoonslijst.
     * 
     * @param lg01
     *            String
     * @return lo3Persoonslijst
     */
    private Lo3Persoonslijst converteerLg01NaarLo3Persoonslijst(final String lg01, final FoutMelder foutMelder) {
        if (lg01 == null || "".equals(lg01)) {
            foutMelder.log(LogSeverity.ERROR, "Fout bij het lezen van lg01", "Bestand is leeg");
        } else {
            final Lo3BerichtFactory bf = new Lo3BerichtFactory();
            final Lo3Bericht lo3Bericht = bf.getBericht(lg01);

            if (lo3Bericht instanceof OngeldigBericht) {
                foutMelder.log(LogSeverity.ERROR, "Ongeldig bericht", ((OngeldigBericht) lo3Bericht).getMelding());
            } else if (lo3Bericht instanceof OnbekendBericht) {
                foutMelder.log(LogSeverity.ERROR, "Obekend bericht", ((OnbekendBericht) lo3Bericht).getMelding());
            } else {
                return ((Lg01Bericht) lo3Bericht).getLo3Persoonslijst();
            }
        }
        return null;
    }

}
