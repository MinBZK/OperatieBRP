/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.afnemersindicatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpVergelijker;

/**
 * Vergelijk afnemersindicaties.
 */
public final class BrpAfnemersindicatiesVergelijker {

    private BrpAfnemersindicatiesVergelijker() {
    }

    /**
     * Vergelijk brp afnemersindicaties.
     *
     * @param verschillenLog
     *            log
     * @param expected
     *            verwacht
     * @param actual
     *            gevonden
     * @return false, als verschillen worden gevonden, anders true
     */
    public static boolean vergelijk(final StringBuilder verschillenLog, final BrpAfnemersindicaties expected, final BrpAfnemersindicaties actual) {

        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk afnemersindicaties:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de afnemersindicaties is null\n");
                equal = false;
            }
        } else {
            if (!BrpAfnemersindicatiesVergelijker.equals(
                lokaalVerschillenLog,
                "administratienummer",
                expected.getAdministratienummer(),
                actual.getAdministratienummer())
                || !BrpAfnemersindicatiesVergelijker.vergelijkAfnemersindicaties(
                    lokaalVerschillenLog,
                    expected.getAfnemersindicaties(),
                    actual.getAfnemersindicaties()))
            {
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    private static boolean vergelijkAfnemersindicaties(
        final StringBuilder verschillenLog,
        final List<BrpAfnemersindicatie> expected,
        final List<BrpAfnemersindicatie> actual)
    {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk afnemersindicaties lijst:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de afnemersindicaties lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog.append(String.format(
                    "Lijsten bevatten niet even veel afnemersindicaties (expected=%s, actual=%s)%n",
                    expected.size(),
                    actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                final BrpAfnemersindicatie expectedItem = expected.get(index);
                final BrpAfnemersindicatie actualItem = actual.get(index);

                if (!BrpAfnemersindicatiesVergelijker.vergelijkAfnemersindicatie(lokaalVerschillenLog, index, expectedItem, actualItem)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return equal;
    }

    private static boolean vergelijkAfnemersindicatie(
        final StringBuilder verschillenLog,
        final int index,
        final BrpAfnemersindicatie expected,
        final BrpAfnemersindicatie actual)
    {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk afnemersindicatie (").append(index).append("):\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de afnemersindicaties is null\n");
                equal = false;
            }
        } else {
            if (!BrpAfnemersindicatiesVergelijker.equals(lokaalVerschillenLog, "partijCode", expected.getPartijCode(), actual.getPartijCode())
                    || !BrpAfnemersindicatiesVergelijker.equals(
                        lokaalVerschillenLog,
                        "leveringautorisatie",
                        expected.getLeveringautorisatie(),
                        actual.getLeveringautorisatie())
                        || !BrpVergelijker.vergelijkStapels(
                            lokaalVerschillenLog,
                            expected.getAfnemersindicatieStapel(),
                            actual.getAfnemersindicatieStapel(),
                            false,
                            false))
            {
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

    /**
     * Vergelijkt twee partijen inhoudelijk.
     *
     * @param verschillenLog
     *            De log voor de verschillen.
     * @param expected
     *            De verwachte partij.
     * @param actual
     *            De partij uit de test.
     * @return True indien gelijk, false in andere gevallen.
     */
    public static boolean vergelijkPartij(final StringBuilder verschillenLog, final BrpPartij expected, final BrpPartij actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk partijen:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de partijen is null\n");
                equal = false;
            }
        } else {
            if (!BrpAfnemersindicatiesVergelijker.equals(lokaalVerschillenLog, "naam", expected.getNaam(), actual.getNaam())
                    || !BrpAfnemersindicatiesVergelijker.equals(lokaalVerschillenLog, "partijCode", expected.getPartijCode(), actual.getPartijCode())
                    || !BrpVergelijker.vergelijkStapels(lokaalVerschillenLog, expected.getPartijStapel(), actual.getPartijStapel(), true, true))
            {
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
            verschillenLog.append(String.format("vergelijk %s: waarden zijn niet gelijk (expected=%s, actual=%s)%n", naam, expected, actual));
            equal = false;
        }

        return equal;
    }
}
