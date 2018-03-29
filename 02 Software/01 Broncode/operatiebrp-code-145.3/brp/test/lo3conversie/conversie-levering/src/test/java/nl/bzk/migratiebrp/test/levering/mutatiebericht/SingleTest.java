/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.CompositeFilter;
import nl.bzk.migratiebrp.test.common.util.CompositeFilter.Type;
import nl.bzk.migratiebrp.test.common.util.Filters.DirectoryFilters;

public class SingleTest extends LeveringMutatieberichtTestConfiguratie {

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
        final FilenameFilter startsWith = new CompositeFilter(Type.OR, DirectoryFilters.startsWith(""));
        final FilenameFilter notEndsWith = DirectoryFilters.notEndsWith("NO TEST");

        return new CompositeFilter(Type.AND, startsWith, notEndsWith);
    }

    @Override
    public FilenameFilter getCasusFilter() {
        final FilenameFilter startsWith = new CompositeFilter(Type.OR, DirectoryFilters.startsWith(""));
        final FilenameFilter notEndsWith = DirectoryFilters.notEndsWith("NO TEST");

        return new CompositeFilter(Type.AND, startsWith, notEndsWith);
    }
}
