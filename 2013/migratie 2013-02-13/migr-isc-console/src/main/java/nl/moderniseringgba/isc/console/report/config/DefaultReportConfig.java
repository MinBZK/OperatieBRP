/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report.config;

import java.io.File;
import java.io.IOException;

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
        // TODO: Als we over zijn naar JDK 7 is hier een methode voor in de Files class
        try {
            reportsDirectory = File.createTempFile("birt-", null);
        } catch (final IOException e) {
            throw new RuntimeException("Could not create temporary output directory:", e);
        }
        if (!(reportsDirectory.delete()) || !(reportsDirectory.mkdir())) {
            throw new RuntimeException("Could not create report output directory: "
                    + reportsDirectory.getAbsolutePath());
        }

        imagesDirectory = new File(reportsDirectory, "images");
        imagesDirectory.mkdir();
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
