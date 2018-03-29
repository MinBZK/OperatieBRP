/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;

public final class Lo3AfnemersindicatiesVergelijker {

    private Lo3AfnemersindicatiesVergelijker() {
        // Niet instantieerbaar
    }

    public static boolean vergelijk(final StringBuilder verschillenLog, final Lo3Afnemersindicatie expected, final Lo3Afnemersindicatie actual) {

        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk afnemersindicaties:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de afnemersindicaties is null\n");
                equal = false;
            }
        } else {
            if (!equals(lokaalVerschillenLog, "administratienummer", expected.getANummer(), actual.getANummer())
                    | !Lo3StapelHelper.vergelijkStapels(lokaalVerschillenLog, expected.getAfnemersindicatieStapels(), actual.getAfnemersindicatieStapels())) {
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T> boolean equals(final StringBuilder verschillenLog, final String naam, final T expected, final T actual) {
        boolean equal = true;

        if (!(expected == null && actual == null || expected != null && expected.equals(actual))) {
            verschillenLog.append(String.format("vergelijk %s: waarden zijn niet gelijk (expected=%s, actual=%s)\n", naam, expected, actual));
            equal = false;
        }

        return equal;
    }

}
