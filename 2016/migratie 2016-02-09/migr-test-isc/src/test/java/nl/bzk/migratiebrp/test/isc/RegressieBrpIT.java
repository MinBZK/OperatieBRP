/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.StartsWithFilter;

public class RegressieBrpIT extends IscTestConfiguration {

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        return new File("./regressie-brp");
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getThemaFilter()
     */
    @Override
    public FilenameFilter getThemaFilter() {
        //return new BaseFilter(FilterType.DIRECTORY);
        return new StartsWithFilter("beheer-stamgegevens-statisch", FilterType.DIRECTORY);
        //return new EqualsFilter("beheer-stamgegevens-statisch", FilterType.DIRECTORY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFilter()
     */
    @Override
    public FilenameFilter getCasusFilter() {
        return new BaseFilter(FilterType.DIRECTORY);
        //return new EqualsFilter("01M02EffectAfnemerindicatie", FilterType.DIRECTORY);
        //return new StartsWithFilter("03", FilterType.DIRECTORY);
//        return new CompositeFilter(
//                CompositeFilter.Type.AND,
//                new StartsWithFilter("01AanduidingMedium", FilterType.DIRECTORY),
//                new NotEndsWithFilter("NOK", FilterType.DIRECTORY),
//                new NotEndsWithFilter("NO TEST", FilterType.DIRECTORY));
        
        //return new NotEndsWithFilter("NOK", FilterType.DIRECTORY);
    }
}
