/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.Objects;

interface WaardeFormatter {
    /**
     * Formatteert de meegegeven waarde afkomstig uit het conversie model afhankelijk van het runtime type.
     * @param waarde waarde
     * @return geformatteerde waarde
     */
    static String format(final Object waarde) {
        final String result;
        if (waarde instanceof Character) {
            result = String.format("\"%s\"", waarde.toString());
        } else if (waarde instanceof String) {
            result = String.format("\"%s\"", waarde);
        } else if (waarde instanceof Boolean) {
            result = ((Boolean) waarde) ? "WAAR" : "ONWAAR";
        } else if (waarde instanceof Number) {
            result = String.valueOf(waarde);
        } else {
            result = Objects.toString(waarde);
        }
        return result;
    }
}
