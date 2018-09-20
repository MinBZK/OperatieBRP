/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.EqualsFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;

public class SingleTest extends ConversieTestConfiguratie {

    @Override
    public boolean useMemoryDS() {
        return true;
    }

    @Override
    public FilenameFilter getThemaFilter() {
        // return new BaseFilter(FilterType.DIRECTORY);
        return new EqualsFilter("bouw", FilterType.DIRECTORY);
    }

    @Override
    public FilenameFilter getCasusFilter() {
        return new BaseFilter(FilterType.DIRECTORY);
        // return new EqualsFilter("bzmBijhouding", FilterType.DIRECTORY);

    }

    @Override
    public File getInputFolder() {
        return new File("./test");
    }
}
