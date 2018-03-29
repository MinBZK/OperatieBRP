/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobconversie;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.blobconversie.service.Blob2FileService;
import nl.bzk.brp.blobconversie.service.Excel2DatabaseService;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;

/**
 * Excel naar blob converter. Excel-sheets met persoonsbeelden vanuit GBA/migratie worden ingelezen en voor elke kolom wordt een corresponderend
 * persoonsbeeld opgeslagen in DB. De persoonsbeelden worden vervolgens uitgelezen en verblobt en in diverse formaten weggeschreven naar bestand.
 */
public class Excel2BlobConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * main.
     *
     * @param args args.
     * @throws Exception uitzondering.
     */
    public static void main(final String[] args) throws Exception {
        new Excel2BlobConverter().converteerMigratieExcelbestanden();
    }

    private void converteerMigratieExcelbestanden() throws Exception {
        final File excelInputDir = new File(System.getProperty("excelInputDir"));
        LOGGER.info("Excel testgevallen worden gelezen van: " + excelInputDir);
        final Collection<File> excelLijst = FileUtils.listFiles(excelInputDir, new String[]{ "xls" }, true);
        LOGGER.info("Aantal bestanden gevonden: " + excelLijst.size());
        final int noOfThreads = Integer.parseInt(System.getProperty("threads", "2"));
        final ExecutorService executorService = Executors.newFixedThreadPool(noOfThreads);
        for (final File excelFile : excelLijst) {
            executorService.submit(() -> {
                try {
                    LOGGER.info("Start verwerken excel: " + excelFile);
                    final ConfiguratieHelper helper = ConfiguratieHelper.get();
                    Excel2DatabaseService excel2DatabaseService = helper.getExcel2DatabaseService();
                    Blob2FileService blob2FileService = helper.getBlob2FileService();
                    helper.prepareNextRun();
                    LOGGER.info("Start converteren excel naar db");
                    excel2DatabaseService.converteer(new FileSystemResource(excelFile), blob2FileService);
                    LOGGER.info("Start genereren blobs uit db");
                    blob2FileService.maakFileBlobs(excelFile, false);
                    LOGGER.info("Conversie geslaagd: " + excelFile.getName());
                } catch (Exception e) {
                    LOGGER.error("Conversie mislukt: " + excelFile.getName(), e);
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            LOGGER.info("Nog niet klaar met converteren...");
            if (executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                break;
            }
        }
        LOGGER.info("Klaar met converteren!");
        ConfiguratieHelper.cleanup();
    }
}