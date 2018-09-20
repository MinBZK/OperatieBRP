/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Not filter.
 */
public final class NotFilter implements FilenameFilter {

    private final FilenameFilter delegate;

    /**
     * Constructor.
     * 
     * @param delegate
     *            delegate
     */
    public NotFilter(final FilenameFilter delegate) {
        this.delegate = delegate;
    }

    /**
     * @param dir
     *            dir
     * @param name
     *            name
     * @return true, als name null is of delegate.accept(dir,name) false teruggeeft
     */
    @Override
    public boolean accept(final File dir, final String name) {
        return name == null || !delegate.accept(dir, name);
    }

}
