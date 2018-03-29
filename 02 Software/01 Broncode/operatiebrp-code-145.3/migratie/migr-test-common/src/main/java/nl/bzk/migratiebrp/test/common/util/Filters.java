/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * Filters.
 */
public final class Filters {
    private Filters() {
        // Niet instantieerbaar
    }

    /**
     * Maakt een NotFilter.
     * @param filter filter
     * @return NotFilter
     */
    public static FilenameFilter not(final FilenameFilter filter) {
        return new NotFilter(filter);
    }

    /**
     * Maakt een BaseFilter.
     * @param type type of files to accept
     * @return BaseFilter
     */
    public static FilenameFilter all(final FilterType type) {
        return new BaseFilter(type);
    }

    /**
     * Maakt een StartsWithFilter.
     * @param accept waarmee de bestandsnaam (hoofdletter ongevoelig) moet beginnen, null accepteert alles
     * @param type type of files to accept
     * @return StartsWithFilter
     */
    public static FilenameFilter startsWith(final String accept, final FilterType type) {
        return new StartsWithFilter(accept, type);
    }

    /**
     * Maakt een EndsWithFilter.
     * @param accept waarmee de bestandsnaam (hoofdletter ongevoelig) moet eindigen, null accepteert alles
     * @param type type of files to accept
     * @return EndsWithFilter
     */
    public static FilenameFilter endsWith(final String accept, final FilterType type) {
        return new EndsWithFilter(accept, type);
    }

    /**
     * Directory filters.
     */
    public static final class DirectoryFilters {
        private DirectoryFilters() {
        }

        /**
         * Maakt een BaseFilter.
         * @return BaseFilter
         */
        public static FilenameFilter all() {
            return Filters.all(FilterType.DIRECTORY);
        }

        /**
         * Maakt een StartsWithFilter.
         * @param accept waarmee de directorynaam (hoofdletter ongevoelig) moet beginnen, null accepteert alles
         * @return StartsWithFilter
         */
        public static FilenameFilter startsWith(final String accept) {
            return Filters.startsWith(accept, FilterType.DIRECTORY);
        }

        /**
         * Maakt een EndsWithFilter.
         * @param accept waarmee de directorynaam (hoofdletter ongevoelig) moet eindigen, null accepteert alles
         * @return EndsWithFilter
         */
        public static FilenameFilter endsWith(final String accept) {
            return Filters.endsWith(accept, FilterType.DIRECTORY);
        }

        /**
         * Maakt een NotEndsWithFilter.
         * @param accept waarmee de directorynaam (hoofdletter ongevoelig) niet moet eindigen, null accepteert alles
         * @return NotEndsWithFilter
         */
        public static FilenameFilter notEndsWith(final String... accept) {
            if (accept == null) {
                return new NotEndsWithFilter((String) null, FilterType.DIRECTORY);
            } else {
                return new NotEndsWithFilter(Arrays.asList(accept), FilterType.DIRECTORY);
            }

        }
    }
}
