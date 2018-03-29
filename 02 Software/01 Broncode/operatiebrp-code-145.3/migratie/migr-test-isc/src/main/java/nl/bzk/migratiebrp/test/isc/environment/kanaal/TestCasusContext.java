/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal;

import java.io.File;
import java.util.Properties;

/**
 * Test casus context.
 */
public class TestCasusContext {

    private final Properties testcaseConfiguratie;
    private final File inputDirectory;
    private final File outputDirectory;

    /**
     * Maak een nieuwe test casus context.
     * @param testcaseConfiguratie the testcase configuratie
     * @param inputDirectory the input directory
     * @param outputDirectory the output directory
     */
    public TestCasusContext(final Properties testcaseConfiguratie, final File inputDirectory, final File outputDirectory) {
        super();
        this.testcaseConfiguratie = testcaseConfiguratie;
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
    }

    /**
     * Geef de waarde van testcase configuratie.
     * @return testcase configuratie
     */
    public Properties getTestcaseConfiguratie() {
        return testcaseConfiguratie;
    }

    /**
     * Geef de waarde van input directory.
     * @return input directory
     */
    public File getInputDirectory() {
        return inputDirectory;
    }

    /**
     * Geef de waarde van output directory.
     * @return output directory
     */
    public File getOutputDirectory() {
        return outputDirectory;
    }

}
