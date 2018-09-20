/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal.runner;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.dal.TestCasusFactory;

/**
 * Base class to configure the factory and locations.
 */
public abstract class TestConfiguratie {

    /**
     * Geeft aan of de test tegen een in-memory-DB (bv HSQL-db) of tegen een echte database plaats vindt. Default: false
     * (echt database).
     *
     * @return false voor een echte database, true voor een in-memory-DB
     */
    public boolean useMemoryDS() {
        return false;
    }

    /**
     * Geef de waarde van input folder.
     *
     * @return the input folder
     */
    public abstract File getInputFolder();

    /**
     * Geef de waarde van thema filter.
     *
     * @return the thema filter
     */
    public FilenameFilter getThemaFilter() {
        return null;
    }

    /**
     * Geef de waarde van casus filter.
     *
     * @return the casus filter
     */
    public FilenameFilter getCasusFilter() {
        return null;
    }

    /**
     * Geef de waarde van casus factory.
     *
     * @return the casus factory
     */
    public abstract TestCasusFactory getCasusFactory();
}
