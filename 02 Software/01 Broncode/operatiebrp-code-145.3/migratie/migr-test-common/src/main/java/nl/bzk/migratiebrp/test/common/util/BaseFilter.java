/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Base filter.
 */
public final class BaseFilter implements FilenameFilter {

    private static final Set<String> UNACCEPTABLE_NAMES = new HashSet<>(Arrays.asList(".svn"));

    private final FilterType type;

    /**
     * Constructor.
     * @param type type of files to accept
     */
    public BaseFilter(final FilterType type) {
        this.type = type;
    }

    @Override
    public boolean accept(final File directory, final String filename) {
        if (!checkFileType(new File(directory, filename))) {
            return false;
        }
        return !UNACCEPTABLE_NAMES.contains(filename);
    }

    private boolean checkFileType(final File newFile) {
        boolean result = true;

        if (newFile.exists()) {
            if (type == FilterType.DIRECTORY && !newFile.isDirectory()) {
                result = false;
            }

            if (type == FilterType.FILE && !newFile.isFile()) {
                result = false;
            }
        }

        return result;

    }
}
