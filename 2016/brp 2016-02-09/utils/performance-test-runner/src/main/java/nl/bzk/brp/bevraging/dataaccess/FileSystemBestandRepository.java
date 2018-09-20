/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import static java.lang.String.format;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

/**
 * {@link BestandRepository} implementatie die naar het filesystem schrijft.
 */
@Repository
public class FileSystemBestandRepository implements BestandRepository {
    private final Logger LOGGER = LoggerFactory.getLogger(FileSystemBestandRepository.class);

    private String folder;

    /**
     * De constructor van de repository.
     */
    public FileSystemBestandRepository() {
        this.setFolder(System.getProperty("app.home"));
    }

    /**
     * Zet de folder waarin geschreven wordt.
     * @param path het pad naar de folder
     */
    public void setFolder(final String path) {
        this.folder = path + "/report";

        if (!verifyFolder(this.folder)) {
            this.folder = System.getProperty("java.io.tmpdir");
            LOGGER.warn("Rapport folder '{}/report' is niet schrijfbaar, reset naar java.io.tmpdir: '{}'", path,
                        this.folder);
        }
    }

    /**
     * Verifieer dat een folder bestaat.
     * @param map de folder om te verifieren
     * @return <code>true</code> als de folder bestaat, anders <code>false</code>
     */
    private boolean verifyFolder(final String map) {
        try {
            File file = ResourceUtils.getFile(map);
            return file.exists() && file.canWrite();
        } catch (FileNotFoundException e) {
            LOGGER.error("Kan rapport folder '{}' niet schrijven", map, e);
            return false;
        }

    }

    @Override
    public void schrijfRegels(final String naam, final List<String> regels) {
        try {
            File reportDir = ResourceUtils.getFile(maakFolder(naam));
            String datumTijdStempel = DateFormatUtils.format(new Date(), "yyyyMMdd-HHmm");
            File report = new File(reportDir, format("%s-%s.txt", naam, datumTijdStempel));
            boolean isCreated = report.createNewFile();

            if (isCreated) {
                /*
                RandomAccessFile raf = new RandomAccessFile(report, "rw");
                FileOutputStream fos = new FileOutputStream(raf.getFD());
                */
                FileOutputStream fos = new FileOutputStream(report);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                OutputStreamWriter out = new OutputStreamWriter(bos, "UTF-8");

                for (String line : regels) {
                    out.write(line);
                    out.write("\n");
                }

                out.close();
            }
        } catch (IOException e) {
            LOGGER.error("fout tijdens schrijven", e);
        }
    }

    /**
     * Maakt de folder.
     * @param naam de naam van de folder
     * @return de gemaakte folder
     */
    private String maakFolder(final String naam) {

        String path = this.folder;

        if (naam.matches("[0-9]+")) {
            String tijdelijk = StringUtils.leftPad(naam, 8, '0');
            StringBuilder sb = new StringBuilder(path);
            sb.append("/").append(tijdelijk.substring(0, 2));
            sb.append("/").append(tijdelijk.substring(0, 4));

            path = sb.toString();
        }

        boolean mapGemaakt = false;
        try {
            File reportDir = ResourceUtils.getFile(path);
            mapGemaakt = reportDir.mkdirs();

            return mapGemaakt || reportDir.exists() ? path : this.folder;
        } catch (IOException e) {
            LOGGER.error("fout tijdens maken van folder: {}", path);
        }

        return this.folder;
    }

}
