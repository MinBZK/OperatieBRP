/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Simpele filter om te helpen bij de SingleTest.
 */
public final class NotEndsWithFilter implements FilenameFilter {

    private final List<String> accepts;
    private final FilenameFilter baseFilter;

    /**
     * Constructor.
     * @param accept waarmee de bestandsnaam (hoofdletter ongevoelig) niet eindigt, null accepteert alles
     * @param type type of files to accept
     */
    public NotEndsWithFilter(final String accept, final FilterType type) {
        if (accept == null) {
            accepts = null;
        } else {
            accepts = Collections.singletonList(accept.toUpperCase());
        }
        baseFilter = new BaseFilter(type);
    }

    /**
     * Constructor.
     * @param accepts waarmee de bestandsnaam (hoofdletter ongevoelig) niet eindigt, null accepteert alles
     * @param type type of files to accept
     */
    public NotEndsWithFilter(final List<String> accepts, final FilterType type) {
        if (accepts == null) {
            this.accepts = null;
        } else {
            this.accepts = new ArrayList<String>();
            for (final String accept : accepts) {
                this.accepts.add(accept.toUpperCase());
            }
        }
        baseFilter = new BaseFilter(type);
    }

    /**
     * Constructor.
     * @param accepts waarmee de bestandsnaam (hoofdletter ongevoelig) niet eindigt, null accepteert alles
     * @param type type of files to accept
     */
    public NotEndsWithFilter(final FilterType type, final String... accepts) {
        this(Arrays.asList(accepts), type);
    }

    @Override
    public boolean accept(final File directory, final String filename) {
        boolean result;

        if (baseFilter.accept(directory, filename)) {
            if (accepts == null) {
                result = true;
            } else {
                result = true;
                for (final String accept : accepts) {
                    if (filename.toUpperCase().endsWith(accept)) {
                        result = false;
                        break;
                    }
                }
            }
        } else {
            result = false;
        }

        return result;
    }
}
