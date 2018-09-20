/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.db;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.StartsNotWithListFilter;
import nl.bzk.migratiebrp.test.common.util.StartsWithListFilter;

public class CoverageTest extends DbConversieTestConfiguratie {

    @Override
    public boolean useMemoryDS() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        return new File("./deltabepaling");
    }

    /**
     * Geef de waarde van casus filter.
     *
     * @return the casus filter
     */
    public FilenameFilter getCasusFilter() {

        List<String> files = new ArrayList<>();
        files.add("DELTABIJH");
        files.add("ELTAINFRAC10T70.xls");
        files.add("ELTAINFRAC10T80.xls");
        files.add("ELTAINFRAC10T90.xls");
        files.add("Mini");
        StartsNotWithListFilter filter = new StartsNotWithListFilter(files, FilterType.FILE);
        return filter;

    }
}
