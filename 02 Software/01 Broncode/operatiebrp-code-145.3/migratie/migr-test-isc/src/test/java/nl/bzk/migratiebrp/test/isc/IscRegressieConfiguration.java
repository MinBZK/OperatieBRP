/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.NotEndsWithFilter;
import nl.bzk.migratiebrp.test.dal.TestSkipper;
import nl.bzk.migratiebrp.test.dal.runner.TestRunner;
import org.junit.runner.RunWith;

/**
 * ISC Regressie configuratie.
 */
@RunWith(TestRunner.class)
public abstract class IscRegressieConfiguration extends IscTestConfiguration {

    @Override
    public FilenameFilter getThemaFilter() {
        return new NotEndsWithFilter(FilterType.DIRECTORY, "NO TEST");
    }

    @Override
    public FilenameFilter getCasusFilter() {
        return new NotEndsWithFilter(FilterType.DIRECTORY, "NO TEST");
    }

    @Override
    public TestSkipper getTestSkipper() {
        return TestSkipper.regressie();
    }
}
