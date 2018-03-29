/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.jbehave;

import com.google.common.collect.Sets;
import java.util.Set;

/**
 *
 */
public final class WaarOnwaar {
    private static final Set<String> JA_SET  = Sets.newHashSet("ja", "wel", "j", "y", "waar", "true", "ok", "aan");
    private static final Set<String> NEE_SET = Sets.newHashSet("nee", "niet", "n", "onwaar", "false", "nok", "uit");

    private WaarOnwaar() {
        // alleen static methods op deze util klasse
    }

    /**
     * @param input input
     * @return waar
     */
    public static boolean isWaar(final String input) {
        if (JA_SET.contains(input.toLowerCase().trim())) {
            return true;
        }
        if (NEE_SET.contains(input.toLowerCase().trim())) {
            return false;
        }

        throw new IllegalArgumentException(String.format("De waarde %s wordt niet herkend als ja/nee waarde.", input));
    }
}
