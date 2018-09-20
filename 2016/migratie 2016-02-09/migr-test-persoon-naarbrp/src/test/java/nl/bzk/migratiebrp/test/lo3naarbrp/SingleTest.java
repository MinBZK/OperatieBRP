/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.lo3naarbrp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.StartsNotWithListFilter;
import nl.bzk.migratiebrp.test.common.util.StartsWithListFilter;

public class SingleTest extends Lo3NaarBrpConversieTestConfiguratie {

    @Override
    public boolean useMemoryDS() {
        return true;
        // return false;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getThemaFilter()
     */
    @Override
    public FilenameFilter getThemaFilter() {
        return new BaseFilter(FilterType.DIRECTORY);
    }

/*
 * (non-Javadoc)
 *
 * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFilter()
 */
    @Override
    public FilenameFilter getCasusFilter() {
        final List<String> fileList = new ArrayList<>();
        if (false) {
//            fileList.add("ONDCAT0404C10T80.xls");
//            fileList.add("ONDCAT0404C10T100.xls");
//            fileList.add("ONDCAT0404C10T30");
//            fileList.add("ONDCAT0404C10T50");
//            fileList.add("ONDCAT0404C10T60");
            fileList.add("ONDCAT04_ORANJE_3759");
//            fileList.add("ONDCAT04_ORANJE_3803");

            return new StartsWithListFilter(fileList, FilterType.FILE);

        } else if(false) {
            fileList.add("DELTAOND");
            return new StartsNotWithListFilter(fileList,FilterType.FILE);
        }else{
            return new BaseFilter(FilterType.FILE);
        }
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        // return new File("./test");
        return new File("./regressie");
        // return new File("./relateren2");
    }
}
