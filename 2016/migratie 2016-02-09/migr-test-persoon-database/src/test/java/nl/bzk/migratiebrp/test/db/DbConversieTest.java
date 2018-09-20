/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.db;

import java.io.File;

/**
 * Conversie test met SQL-controle zonder relateren en delta-bepaling aan. Deze test maakt standaard gebruik van een
 * PostgreSQL-database.
 */
public class DbConversieTest extends DbConversieTestConfiguratie {

    /*
     * @Override public FilenameFilter getThemaFilter() { // return new BaseFilter(FilterType.DIRECTORY); return new
     * EqualsFilter("Familie", FilterType.DIRECTORY); }
     */

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        return new File("./test");
    }
}
