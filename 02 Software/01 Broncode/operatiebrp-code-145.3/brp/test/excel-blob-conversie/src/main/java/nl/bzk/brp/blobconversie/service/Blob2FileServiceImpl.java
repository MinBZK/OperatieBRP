/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobconversie.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Service voor het creeren vn blobs (a.h.v. persoonsbeelden uit DB) en wegschrijven naar files in JSON en .txt formaat.
 */
@Service
public class Blob2FileServiceImpl implements Blob2FileService {


    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private PersoonslijstService persoonslijstService;
    @Inject
    private PersoonBlobService persoonBlobService;
    @Inject
    @Named("myTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public void maakFileBlobs(final File excelFile, final boolean isGbaInitieleVulling) throws IOException, BlobException {
        LOG.info("Start log persoonsbeelden");

        //maak blob dir. aan
        final File blobDir = bepaalBlobDir(excelFile, isGbaInitieleVulling);
        blobDir.mkdirs();

        //maak blobs aan en sla op
        maakBlobsVanAllePersonen();
        slaBlobBestandenOp(blobDir);
    }

    private void maakBlobsVanAllePersonen() throws BlobException {
        final List<Map<String, Object>> persIdList = jdbcTemplate
                .queryForList("select id from kern.pers");
        LOG.info("Aantal gevonden personen {}", persIdList.size());
        for (final Map<String, Object> persIdMap : persIdList) {
            final Number id = (Number) persIdMap.get("id");
            LOG.info("Blob persoon: {}", id);
            persoonBlobService.blobify(id.intValue());
        }
    }

    private void slaBlobBestandenOp(final File blobDir) throws IOException {
        final String filePrefix = "conv";
        final List<Map<String, Object>> persCacheList = jdbcTemplate
                .queryForList("select pers, pershistorievollediggegevens, afnemerindicatiegegevens from kern.perscache");
        LOG.info("Aantal gevonden blobs {}", persCacheList.size());
        for (final Map<String, Object> persCacheMap : persCacheList) {

            final Number persId = (Number) persCacheMap.get("pers");
            final byte[] persoonBytes = (byte[]) persCacheMap.get("pershistorievollediggegevens");
            final byte[] afnemerindicatieBytes = (byte[]) persCacheMap.get("afnemerindicatiegegevens");

            final File persoonBlobFile = new File(blobDir, String.format("%d-%s-persoon.blob.json", persId.intValue(), filePrefix));

            LOG.info("Scrijft blob naar: " + persoonBlobFile.getAbsolutePath());
            try (final FileOutputStream fos = new FileOutputStream(persoonBlobFile)) {
                IOUtils.write(persoonBytes, fos);
            }

            final File afnemerindicatiesBlobFile = new File(blobDir, String.format("%d-%s-afnemerindicatie.blob.json", persId.intValue(), filePrefix));
            try (final FileOutputStream fos = new FileOutputStream(afnemerindicatiesBlobFile)) {
                IOUtils.write(afnemerindicatieBytes, fos);
            }

            final Persoonslijst
                    persoonslijst =
                    persoonslijstService.maak(persoonBytes, afnemerindicatieBytes != null ? afnemerindicatieBytes : new byte[]{}, 0L);
            final File afdrukFile = new File(blobDir, String.format("%d-%s-persoonsbeeld.txt", persId.intValue(), filePrefix));

            try (final FileOutputStream fos = new FileOutputStream(afdrukFile)) {
                final String afdruk = ModelAfdruk.maakAfdruk(persoonslijst.getMetaObject());
                IOUtils.write(afdruk, fos);
                IOUtils.write(String.format("%nVerantwoording:%n%n"), fos);
                for (AdministratieveHandeling administratieveHandeling : persoonslijst.getAdministratieveHandelingen()) {
                    final String ahAfdruk = ModelAfdruk.maakAfdruk(administratieveHandeling.getMetaObject());
                    IOUtils.write(ahAfdruk, fos);
                }
            }
        }
    }


    private File bepaalBlobDir(final File excelFile, final boolean isGbaInitieleVulling) {
        final String bloboutputdir = System.getProperty("blobOutputDir");
        LOG.info("Blob output dir: {}", bloboutputdir);

        String subDir;
        if (isGbaInitieleVulling) {
            subDir = excelFile.getName().replace(excelFile.getParentFile().getName(), excelFile.getParentFile().getName() + "_INITVULLING_");
        } else {
            subDir = excelFile.getName();
        }
        subDir = subDir.replace(".", "_");

        return new File(new File(bloboutputdir, excelFile.getParentFile().getName()), subDir);
    }

    @Override
    public void leegDb() {
        LOG.info("Start leegmaken db");
        jdbcTemplate.update("DROP SCHEMA PUBLIC CASCADE");
    }

}
