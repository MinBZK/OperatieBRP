/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp.report.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Default report config (uses java temp directory).
 */
public final class DefaultReportConfig implements ReportConfig {

    private final File reportsDirectory;
    private final File imagesDirectory;

    /**
     * Default constructor.
     */
    public DefaultReportConfig() {
        try {
            final Path tempPath = Files.createTempDirectory("birt");
            reportsDirectory = tempPath.toFile();
            imagesDirectory = Files.createDirectory(tempPath.resolve("images")).toFile();
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan report output directory niet aanmaken.", e);
        }
    }

    @Override
    public File getReportDirectory() {
        return reportsDirectory;
    }

    @Override
    public File getImagesDirectory() {
        return imagesDirectory;
    }
}
