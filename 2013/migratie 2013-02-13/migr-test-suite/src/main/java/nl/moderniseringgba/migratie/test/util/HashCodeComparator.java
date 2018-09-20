/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.util;

import java.util.Comparator;

/**
 * Comparator obv hashcode.
 */
public final class HashCodeComparator implements Comparator<Object> {

    @Override
    public int compare(final Object arg0, final Object arg1) {
        final int result;

        if (arg0 == null) {
            result = arg1 == null ? 0 : -1;
        } else if (arg1 == null) {
            result = -1;
        } else {

            final int int0 = arg0.hashCode();
            final int int1 = arg1.hashCode();

            result = int0 == int1 ? 0 : int0 < int1 ? -1 : 1;
        }
        return result;
    }
}
