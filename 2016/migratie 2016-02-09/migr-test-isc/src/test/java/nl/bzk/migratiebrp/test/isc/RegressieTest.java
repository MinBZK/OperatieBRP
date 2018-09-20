/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.CompositeFilter;
import nl.bzk.migratiebrp.test.common.util.CompositeFilter.Type;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.NotEndsWithFilter;
import nl.bzk.migratiebrp.test.common.util.StartsWithFilter;

public class RegressieTest extends IscTestConfiguration {

    @Override
    public File getInputFolder() {
        return new File("./regressie-isc");
    }

    @Override
    public FilenameFilter getThemaFilter() {
        // return new BaseFilter(FilterType.DIRECTORY);
        return new StartsWithFilter("", FilterType.DIRECTORY);
    }

    @Override
    public FilenameFilter getCasusFilter() {
        // return new BaseFilter(FilterType.DIRECTORY);
        return new CompositeFilter(
            Type.AND,
            new StartsWithFilter("", FilterType.DIRECTORY),
            new NotEndsWithFilter("NOK", FilterType.DIRECTORY),
            new NotEndsWithFilter("NO TEST", FilterType.DIRECTORY));
    }
}
