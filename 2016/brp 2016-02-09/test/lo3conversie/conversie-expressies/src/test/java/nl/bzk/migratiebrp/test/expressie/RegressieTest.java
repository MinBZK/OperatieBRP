/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.common.util.NotEndsWithFilter;

public class RegressieTest extends ConversieTestConfiguratie {

    @Override
    public boolean useMemoryDS() {
        return true;
    }

    @Override
    public File getInputFolder() {
        return new File("./regressie");
    }

    @Override
    public FilenameFilter getCasusFilter() {
        // return new BaseFilter(FilterType.DIRECTORY);
        return new NotEndsWithFilter("NOK", FilterType.DIRECTORY);
    }
}
