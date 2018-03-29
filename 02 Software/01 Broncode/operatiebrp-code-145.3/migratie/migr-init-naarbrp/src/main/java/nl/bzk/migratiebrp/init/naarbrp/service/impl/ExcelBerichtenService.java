/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.PersoonRepository;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

/**
 * Vult berichten tabel aan de hand van Excel bestanden.
 */
@Service("excelBerichtenService")
public class ExcelBerichtenService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

    private final ExcelAdapter excelAdapter;
    private final PersoonRepository persoonRepository;

    /**
     * Constructueert nieuwe ExcelBerichtenService.
     * @param excelAdapter Excel adapter
     * @param persoonRepository GBAV repository
     */
    @Inject
    public ExcelBerichtenService(final ExcelAdapter excelAdapter, final PersoonRepository persoonRepository) {
        this.excelAdapter = excelAdapter;
        this.persoonRepository = persoonRepository;
    }

    /**
     * Verwerk alle Excel bestanden in de folder.
     * @param excelFolder de folder met Excel bestanden
     */
    public final void verwerkFolder(final String excelFolder) {
        final List<File> files = bepaalExcelBestanden(excelFolder);
        LOG.info("Aantal gevonden excel files: " + files.size());
        verwerkExcelBestanden(files);
    }

    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *** INITIELE VULLING PERSONEN (EXCEL)
     * ***********************************************************************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    private void verwerkExcelBestanden(final List<File> files) {
        for (final File file : files) {
            try {
                // Lees excel
                final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new FileInputStream(file));

                // Parsen input *ZONDER* syntax en precondite controles
                final List<Lo3Persoonslijst> lo3Persoonslijsten =
                        excelDatas.stream().map(excelData -> parser.parse(excelData.getCategorieLijst())).collect(Collectors.toList());

                verwerkPersoonslijsten(lo3Persoonslijsten);
            } catch (final FileNotFoundException | ExcelAdapterException | Lo3SyntaxException | NumberFormatException e) {
                LOG.error("Probleem bij het inlezen van van de persoonslijst in file: " + file + ". Inlezen wordt voortgezet.", e);
            }
        }
    }

    private void verwerkPersoonslijsten(final List<Lo3Persoonslijst> lo3Persoonslijsten) {
        for (final Lo3Persoonslijst pl : lo3Persoonslijsten) {
            final String lg01 = formateerAlsLg01(pl);
            final Lo3GemeenteCode gemeenteVanInschrijving = pl.getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving();
            final String gemeenteVanInschrijvingCode;
            if (gemeenteVanInschrijving.isValideNederlandseGemeenteCode()) {
                gemeenteVanInschrijvingCode = gemeenteVanInschrijving.getWaarde();
            } else {
                gemeenteVanInschrijvingCode = null;
            }
            persoonRepository.saveLg01(lg01, pl.getActueelAdministratienummer(), gemeenteVanInschrijvingCode, ConversieResultaat.TE_VERZENDEN);
        }
    }

    private List<File> bepaalExcelBestanden(final String excelFolder) {
        final File inputFolder = new File(excelFolder);
        LOG.info(String.format("Input folder %s %s gevonden", inputFolder, inputFolder.exists() ? "is" : "niet"));
        final List<File> files = new ArrayList<>(FileUtils.listFiles(inputFolder, new String[]{"xls"}, true));
        Collections.sort(files);
        return files;
    }

    private String formateerAlsLg01(final Lo3Persoonslijst lo3) {
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(lo3);
        return Lo3Inhoud.formatInhoud(lg01.formatInhoud());
    }

}
