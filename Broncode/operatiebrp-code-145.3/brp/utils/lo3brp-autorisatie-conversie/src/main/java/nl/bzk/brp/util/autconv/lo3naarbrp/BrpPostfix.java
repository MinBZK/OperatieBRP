/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import org.apache.commons.lang3.StringUtils;

/**
 * Appender voor BRP postfix
 */
final class BrpPostfix {

    private static final int MAX_LENGTE_PREFIX = 75;

    private BrpPostfix() {
    }

    static String appendTo(final String naam, Number id) {
        final String idwaarde = id.toString();
        return StringUtils.substring(naam, 0, MAX_LENGTE_PREFIX - idwaarde.length()) + "BRP" + idwaarde;

    }
}
