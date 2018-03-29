/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.levering;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.EqualsFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;

public class SingleTest extends ProcessenTestConfiguration {

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getThemaFilter()
     */
    @Override
    public FilenameFilter getThemaFilter() {
        return new EqualsFilter("levering-1000", FilterType.DIRECTORY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFilter()
     */
    @Override
    public FilenameFilter getCasusFilter() {
        return new EqualsFilter("gemengd", FilterType.DIRECTORY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        return new File("./test");
    }
}
