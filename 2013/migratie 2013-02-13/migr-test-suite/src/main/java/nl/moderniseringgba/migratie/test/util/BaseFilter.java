/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BaseFilter implements FilenameFilter {

    private static final Set<String> UNACCEPTABLE_NAMES = new HashSet<String>(Arrays.asList(".svn"));

    @Override
    public boolean accept(final File directory, final String filename) {
        return !UNACCEPTABLE_NAMES.contains(filename);
    }

}
