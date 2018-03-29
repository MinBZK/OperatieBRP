/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Simpele filter om te helpen bij de SingleTest.
 */
public final class StartsWithFilter implements FilenameFilter {

    private final String accept;
    private final FilenameFilter baseFilter;

    /**
     * Constructor.
     * @param accept waarmee de bestandsnaam (hoofdletter ongevoelig) moet beginnen, null accepteert alles
     * @param type type of files to accept
     */
    public StartsWithFilter(final String accept, final FilterType type) {
        this.accept = accept == null ? null : accept.toUpperCase();
        baseFilter = new BaseFilter(type);
    }

    @Override
    public boolean accept(final File directory, final String filename) {
        final boolean result;

        if (baseFilter.accept(directory, filename)) {
            result = accept == null || filename.toUpperCase().startsWith(accept);
        } else {
            result = false;
        }

        return result;
    }
}
