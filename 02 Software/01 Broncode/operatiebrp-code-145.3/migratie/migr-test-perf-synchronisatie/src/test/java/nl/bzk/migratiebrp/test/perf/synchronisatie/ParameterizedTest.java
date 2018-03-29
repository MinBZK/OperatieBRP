/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.EqualsFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;

/**
 * Geparameteriseerde test om makkelijk tests te kunnen runnen vanuit de assembly.
 */
public class ParameterizedTest extends ProcessenTestConfiguration {

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getThemaFilter()
     */
    @Override
    public FilenameFilter getThemaFilter() {
        return new EqualsFilter(getProperty("test.thema", null), FilterType.DIRECTORY);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFilter()
     */
    @Override
    public FilenameFilter getCasusFilter() {
        return new EqualsFilter(getProperty("test.casus", null), FilterType.DIRECTORY);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        return new File(getProperty("test.directory", "./test"));
    }

    private String getProperty(final String name, final String defaultValue) {
        String value = System.getenv(name);

        if (value == null || value.isEmpty()) {
            value = System.getProperty(name);
        }

        if (value == null || value.isEmpty()) {
            return defaultValue;
        } else {
            return value;
        }

    }
}
