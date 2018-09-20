/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FilenameFilter;

import nl.bzk.migratiebrp.test.common.util.EqualsFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.Filters.DirectoryFilters;

public class SingleTest extends IscTestConfiguration {

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getThemaFilter()
     */
    @Override
    public FilenameFilter getThemaFilter() {
        // return new EqualsOneOfFilter("uc801", "uc802", "uc807-opschonen-procesinformatie");
        // return new StartsWithFilter("uc1003-oud", FilterType.DIRECTORY);
        // return new EqualsOneOfFilter("algemene-bericht-verwerking", "BLAUW-1894", "BLAUW-1895", "BLAUW-1897",
        // "smoketest");
        // return new BaseFilter(FilterType.DIRECTORY);
        return new EqualsFilter("BZM", FilterType.DIRECTORY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFilter()
     */
    @Override
    public FilenameFilter getCasusFilter() {
        // return new BaseFilter(FilterType.DIRECTORY);
        // return new EqualsFilter("uc1001-1003-Ag11-03C10T010", FilterType.DIRECTORY);
        // return new StartsWithFilter("uc-voisc-03C", FilterType.DIRECTORY);
        // return new EqualsOneOfFilter("UC1003-VAIP-01C10T010", "UC1003-VAIP-01C20T010", "UC1003-VAIP-01C20T020a",
        // "UC1003-VAIP-04C20T010c");
        // "uc220-04C20T020",
        // "uc220-06C30T010",
        // "uc220-06C30T020",
        // "uc220-06C40T010",
        // "uc220-09C10T050",
        // "uc220-09C20T010",
        // "uc220-09C20T020",
        // "uc220-09C20T030",
        // "uc220-09C20T040",
        // "uc220-09C20T050",
        // "uc220-09C20T070",
        // "uc220-09C20T080",
        // "uc220-09C20T090",
        // "uc220-09C20T100",
        // "uc220-11C30T030",
        // "uc220-11C50T090",
        // "uc220-11C50T100",
        // "uc220-11C50T120",
        // "uc220-11C50T130",
        // "uc220-13C10T020",
        // "uc220-13C10T030",
        // "uc220-13C10T040");
        // return new StartsWithFilter("uc1001-1003-Ag11-03C10T010", FilterType.DIRECTORY);
        return DirectoryFilters.notEndsWith("NOK", "NO TEST");
        // return new CompositeFilter(Type.AND, DirectoryFilters.startsWith("UC1003PPROC01C30"),
        // return DirectoryFilters.notEndsWith("NOK", "NO TEST");
        // "jbpm-console",
        // "uc1001-1003-01C10T010",
        // "uc1001-1003-01C10T020",
        // "uc1001-1003-01C10T030",
        // "uc1001-1003-01C10T040",
        // "uc1001-1003-01C10T050");
        // return new EqualsFilter("UC1003VPROC01C50T40", FilterType.DIRECTORY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        // return new File("./regressie-isc");
        return new File("./integratie");
    }
}
