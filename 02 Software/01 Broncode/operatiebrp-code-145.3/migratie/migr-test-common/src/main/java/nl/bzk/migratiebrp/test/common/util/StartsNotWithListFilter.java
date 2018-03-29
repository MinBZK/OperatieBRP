/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Simpele filter waarbij je een lijst van bestandsnamen opgeeft om te helpen bij de SingleTest.
 */
public final class StartsNotWithListFilter implements FilenameFilter {

    private final List<String> skip = new ArrayList<>();
    private final FilenameFilter baseFilter;

    /**
     * Constructor.
     * @param accept waarmee lijst van bestandsnamen waarmee de test (hoofdletter ongevoelig) moet beginnen, null accepteert alles
     * @param type type of files to accept
     */
    public StartsNotWithListFilter(final List<String> accept, final FilterType type) {
        if (accept != null) {
            for (final String accepted : accept) {
                this.skip.add(accepted.toUpperCase());
            }
        }
        baseFilter = new BaseFilter(type);
    }

    @Override
    public boolean accept(final File directory, final String filename) {
        boolean result = true;

        if (baseFilter.accept(directory, filename) && !skip.isEmpty()) {
            for (final String accepted : skip) {
                if (filename.toUpperCase().startsWith(accepted)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
