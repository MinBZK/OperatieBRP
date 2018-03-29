/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 */
public final class ResourceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private ResourceUtils() {

    }

    /**
     *
     * @param path
     * @return
     */
    public static String classPathFileToString(String path) {
        return resourceToString(new ClassPathResource(path));
    }

    /**
     *
     * @param resource de naar string te converteren resource
     * @return de geconverteerde string
     */
    public static String resourceToString(Resource resource) {

        try (final InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new TestclientExceptie(e);
        }
    }

    /**
     * Schrijft een bericht weg.
     * @param bericht het weg te schrijven bericht
     * @param dir directory
     * @param subdir subdirectory
     * @param fileNaam bestandsnaam
     */
    public static void schrijfBerichtNaarBestand(final String bericht, final String dir, final String subdir, final String fileNaam) {
        try {
            final Path path = maakPad(dir, subdir, fileNaam, false);
            Files.write(path, bericht.getBytes(Charset.defaultCharset()));
        } catch (IOException e) {
            LOGGER.error("Fout bij wegschrijven bestand: " + e);
        }
    }

    /**
     * Schrijft een blob weg.
     * @param blob weg te schrijven blob
     * @param dir directory
     * @param subdir subdirectory
     * @param fileNaam bestandsnaam
     */
    public static void schrijfBlobNaarBestand(final String blob, final String dir, final String subdir, final String fileNaam) {
        try {
            final Path path = maakPad(dir, subdir, fileNaam, true);
            Files.write(path, blob.getBytes(Charset.defaultCharset()));
        } catch (IOException e) {
            LOGGER.error("Fout bij wegschrijven blob: " + e);
        }
    }

    /**
     * Schrijft een modelafdruk weg.
     * @param afdruk weg te schrijven afdruk
     * @param dir directory
     * @param subdir subdirectory
     * @param fileNaam bestandsnaam
     */
    public static void schrijfModelAfdrukNaarTxtBestand(final String afdruk, final String dir, final String subdir, final String fileNaam) {
        try {
            final Path path = maakPad(dir, subdir, fileNaam, true);
            Files.write(path, afdruk.getBytes(Charset.defaultCharset()));
        } catch (IOException e) {
            LOGGER.error("Fout bij wegschrijven modelafdruk: " + e);
        }

    }

    private static Path maakPad(final String dir, final String subdir, String fileNaam, final boolean isBlob) throws IOException {
        final String replaceRegex = "\\r?\\n|\\r| |:";
        final String newDir = dir.replaceAll(replaceRegex, "_").trim();
        String newSubdir = subdir.replaceAll(replaceRegex, "_").trim();
        if (newSubdir.length() > 50) {
            newSubdir = newSubdir.substring(0, 50);
        }
        final String targetDir = isBlob ? "target/blobs" : "target/actuals";
        final Path path = Paths.get(System.getProperty("user.dir") + String.format("/%s/%s/%s/%s", targetDir, newDir, newSubdir, fileNaam));
        Files.createDirectories(path.getParent());
        return path;
    }

    public static Resource resolveResource(final String resourceString) {
        //TODO resolve ook relatief van story
        return new ClassPathResource(resourceString);
    }
}
