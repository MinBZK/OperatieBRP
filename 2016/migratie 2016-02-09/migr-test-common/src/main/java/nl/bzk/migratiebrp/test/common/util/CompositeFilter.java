/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Util klasse voor samengesteld filter.
 */
public class CompositeFilter implements FilenameFilter {

    private final Type type;
    private final FilenameFilter[] filters;

    /**
     * Default constructor.
     *
     * @param type
     *            Het type waarop de filtering wordt toegepast.
     * @param filters
     *            De toe te passen filters.
     */
    public CompositeFilter(final Type type, final FilenameFilter... filters) {
        this.type = type;
        this.filters = filters;
    }

    @Override
    public final boolean accept(final File directory, final String filename) {
        Boolean resultaat = null;
        for (final FilenameFilter filter : filters) {
            final boolean result = filter.accept(directory, filename);

            if (type == Type.OR && result) {
                resultaat = true;
                break;
            }

            if (type == Type.AND && !result) {
                resultaat = false;
                break;
            }
        }

        return resultaat != null ? resultaat : type == Type.AND;
    }

    /**
     * Enum voor boolean operands.
     */
    public static enum Type {
        /**
         * "EN".
         */
        AND,
        /**
         * "OF".
         */
        OR;
    }

}
