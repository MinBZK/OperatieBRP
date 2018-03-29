/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.FilenameFilter;

/**
 * Starts with one of filter.
 */
public final class StartsWithOneOfFilter extends CompositeFilter {

    /**
     * Constructor.
     * @param filenames lijst van filenamen waarmee een geaccepteerde filename moet beginnen
     */
    public StartsWithOneOfFilter(final String... filenames) {
        super(Type.OR, createFilenameFilters(filenames));
    }

    private static FilenameFilter[] createFilenameFilters(final String[] filenames) {
        final FilenameFilter[] filters = new FilenameFilter[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            filters[i] = new StartsWithFilter(filenames[i], FilterType.ANY);
        }
        return filters;
    }

}
