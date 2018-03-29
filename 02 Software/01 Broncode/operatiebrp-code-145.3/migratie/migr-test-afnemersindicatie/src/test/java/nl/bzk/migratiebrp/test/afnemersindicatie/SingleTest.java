/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.afnemersindicatie;

import java.io.File;
import java.io.FilenameFilter;

import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.StartsWithFilter;

public class SingleTest extends ConversieTestConfiguratie {

    @Override
    public boolean useMemoryDS() {
        return true;
    }

    @Override
    public File getInputFolder() {
        return new File("./regressie");
    }

    @Override
    public FilenameFilter getThemaFilter() {
        return new StartsWithFilter("", FilterType.DIRECTORY);
    }

    @Override
    public FilenameFilter getCasusFilter() {
        return new StartsWithFilter("", FilterType.DIRECTORY);
    }
}
