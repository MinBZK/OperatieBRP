/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Simpele filter om te helpen bij de SingleTest.
 */
public final class EqualsFilter implements FilenameFilter {

    private static final FilenameFilter BASE_FILTER = new BaseFilter();

    private final String accept;

    /**
     * Constructor.
     * 
     * @param accept
     *            waarde die de bestandsnaam moet hebben (hoofdletter ongevoelig), null accepteert alles
     */
    public EqualsFilter(final String accept) {
        this.accept = accept == null ? null : accept.toUpperCase();
    }

    @Override
    public boolean accept(final File directory, final String filename) {
        final boolean result;

        if (BASE_FILTER.accept(directory, filename)) {
            result = accept == null || (filename != null && filename.toUpperCase().equals(accept));
        } else {
            result = false;
        }

        return result;
    }
}
