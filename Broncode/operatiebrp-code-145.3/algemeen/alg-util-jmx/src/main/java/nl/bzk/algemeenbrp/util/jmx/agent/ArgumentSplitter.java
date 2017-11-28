/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * Splits the arguments
 */
final class ArgumentSplitter {

    private ArgumentSplitter() {
        throw new IllegalStateException("Do not instantiate");
    }

    static Map<String, String> split(final String args) {
        final Map<String, String> result = new HashMap<>();

        if (args != null && !"".equals(args.trim())) {
            final String[] parts = args.trim().split(",");
            for (final String part : parts) {
                final int index = part.indexOf('=');
                if (index == -1) {
                    result.put(part.trim(), "");
                } else {
                    result.put(part.substring(0, index).trim(), part.substring(index + 1).trim());
                }
            }
        }

        return result;
    }
}
