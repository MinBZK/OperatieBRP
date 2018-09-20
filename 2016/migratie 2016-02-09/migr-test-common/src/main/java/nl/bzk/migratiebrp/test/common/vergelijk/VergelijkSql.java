/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Vergelijkt actuele en verwachte SQL met elkaar.
 */
public final class VergelijkSql {
    private static final String EOF = "\n";

    private VergelijkSql() {
    }

    /**
     * Werkwijze:
     * <UL>
     * <LI>Aantal entries in lijsten vergelijken.</LI>
     * <LI>Alle lijsten veranderen in lijsten met strings, zodat de map een komma-gescheiden regel wordt (makkelijker
     * voor 1 op 1 vergelijken).</LI>
     * </UL>
     * 
     * @param verschillenLog
     *            log waar de verschillen in bijgehouden worden
     * @param actualSqlResults
     *            SQL Results uit de huidige run
     * @param expectedSqlResults
     *            SQL Results die verwacht worden
     * @return true als de huidige resultaten overeenkomen met de verwachte resultaten
     */
    public static boolean vergelijkSqlResultaten(
        final StringBuilder verschillenLog,
        final List<Map<String, Object>> actualSqlResults,
        final List<Map<String, Object>> expectedSqlResults)
    {
        // Eerst aantal rijen in de lijsten vergelijken
        if (actualSqlResults.size() != expectedSqlResults.size()) {
            verschillenLog.append("Aantal verkregen rijen(")
                          .append(actualSqlResults.size())
                          .append(") ongelijk aan aantal verwachte rijen (")
                          .append(expectedSqlResults.size())
                          .append(")\n");
        } else {
            // Aantal rijen is gelijk, nu de map naar strings omzetten en vergelijken.
            final List<String> actualRows = convertMapToString(actualSqlResults);
            final List<String> expectedRows = convertMapToString(expectedSqlResults);
            crosscheckRows(actualRows, expectedRows, verschillenLog);
        }
        return verschillenLog.length() == 0;
    }

    private static List<String> convertMapToString(final List<Map<String, Object>> listOfMaps) {
        final List<String> result = new ArrayList<>();
        for (final Map<String, Object> listEntries : listOfMaps) {
            final StringBuilder sb = new StringBuilder();
            for (final Map.Entry<String, Object> entry : listEntries.entrySet()) {
                sb.append(entry.getKey().toLowerCase()).append("=").append(entry.getValue()).append(";");
            }
            result.add(sb.toString());
        }
        return result;
    }

    private static void crosscheckRows(final List<String> actual, final List<String> expected, final StringBuilder verschillenLog) {

        for (final String entry : actual) {
            if (!expected.contains(entry)) {
                verschillenLog.append("Actuele rij (").append(entry).append(") komt niet voor in het verwachte resultaat").append(EOF);
            }
        }

        for (final String entry : expected) {
            if (!actual.contains(entry)) {
                verschillenLog.append("Verwachte rij (").append(entry).append(") komt niet voor in het actuele resultaat").append(EOF);
            }
        }
    }

}
