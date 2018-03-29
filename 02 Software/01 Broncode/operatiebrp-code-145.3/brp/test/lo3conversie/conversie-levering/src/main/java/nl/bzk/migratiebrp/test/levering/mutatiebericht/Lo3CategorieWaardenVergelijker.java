/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.test.common.vergelijk.Vergelijk;

/**
 * Class biedt de mogelijkheid om 2 lijsten met
 * {@link nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde} te vergelijken.
 */
public final class Lo3CategorieWaardenVergelijker {

    private static final String WAARDE_LOG_AFSLUITING = "):\n ";

    private Lo3CategorieWaardenVergelijker() {}

    /**
     * Vergelijkt 2 lijsten met
     * {@link nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde} met elkaar.
     * Eventuele verschillen worden in de verschillenlog parameter toegevoegd.
     *
     * @param verschillenLog log met gevonden verschillen.
     * @param expected lijst met verwachte categorie waarden
     * @param actual lijst met huidige categorie waarden
     * @return true als de lijsten gelijk zijn qua inhoud.
     */
    public static boolean vergelijk(final StringBuilder verschillenLog, final List<Lo3CategorieWaarde> expected, final List<Lo3CategorieWaarde> actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk categorie lijst:\n ");

        if ((expected == null) || (actual == null)) {
            if ((expected == null) != (actual == null)) {
                lokaalVerschillenLog.append("Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog
                        .append(String.format("Lijsten bevatten niet even veel categorieen (expected=%s, actual=%s)%n", expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                final Lo3CategorieWaarde expectedItem = expected.get(index);
                final Lo3CategorieWaarde actualItem = actual.get(index);

                if (equal && !vergelijkLo3CategorieWaarde(lokaalVerschillenLog, index, expectedItem, actualItem)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return equal;
    }

    private static boolean vergelijkLo3CategorieWaarde(final StringBuilder verschillenLog, final int index, final Lo3CategorieWaarde expected,
            final Lo3CategorieWaarde actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk categorie waarde (index: ").append(index).append(", expected categorie: ")
                .append(expected == null ? "null" : expected.getCategorie()).append(WAARDE_LOG_AFSLUITING);

        if ((expected == null) || (actual == null)) {
            if ((expected == null) != (actual == null)) {
                lokaalVerschillenLog.append("Een van de categorie waarden is null\n");
                equal = false;
            }
        } else {
            final boolean equalsCategorie = equals(lokaalVerschillenLog, "categorie", expected.getCategorie(), actual.getCategorie());
            // Vergelijking op stapel doen we niet, omdat deze er niet toe doet.
            final boolean equalsVoorkomen = equals(lokaalVerschillenLog, "voorkomen", expected.getVoorkomen(), actual.getVoorkomen());
            final boolean equalsElementen = vergelijkElementen(lokaalVerschillenLog, expected.getElementen(), actual.getElementen());
            if (!equalsCategorie || !equalsVoorkomen || !equalsElementen) {
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    /**
     * Vergelijkt de 2 mappen, bestaande uit een element nummer en zijn waarde, op inhoud. Eventuele
     * verschillen worden aan de parameter verschillenLog toegevoegd.
     *
     * @param verschillenLog log met gevonden verschillen.
     * @param expected map van Lo3 elementen die verwacht worden
     * @param actual map van Lo3 elementen die verkregen zijn
     * @return true als de mappen inhoudelijk overeenkomen
     */
    public static boolean vergelijkElementen(final StringBuilder verschillenLog, final Map<Lo3ElementEnum, String> expected,
            final Map<Lo3ElementEnum, String> actual) {

        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk elementen map:\n ");

        if ((expected == null) || (actual == null)) {
            if ((expected == null) != (actual == null)) {
                lokaalVerschillenLog.append("Een van de maps is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog
                        .append(String.format("Lijsten bevatten niet even veel elementen (expected=%s, actual=%s)%n", expected.size(), actual.size()));
                equal = false;
            }

            for (final Map.Entry<Lo3ElementEnum, String> expectedEntry : expected.entrySet()) {
                final Lo3ElementEnum sleutel = expectedEntry.getKey();
                final String expectedValue = expectedEntry.getValue();
                final String actualValue = actual.get(sleutel);

                if (!vergelijkElementWaarde(lokaalVerschillenLog, sleutel, expectedValue, actualValue)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return equal;
    }

    private static boolean vergelijkElementWaarde(final StringBuilder verschillenLog, final Lo3ElementEnum element, final String expected,
            final String actual) {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk element waarde (").append(element).append(WAARDE_LOG_AFSLUITING);

        if ((expected == null) || (actual == null)) {
            if ((expected == null) != (actual == null)) {
                lokaalVerschillenLog.append("Een van de waarden is null\n");
                equal = false;
            }
        } else {
            if (!equals(lokaalVerschillenLog, "waarde", expected, actual)) {
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */

    private static <T> boolean equals(final StringBuilder verschillenLog, final String naam, final T expected, final T actual) {
        final boolean equal;
        if (expected == null) {
            equal = actual == null;
        } else {
            if ((expected instanceof String) && (actual instanceof String)) {
                equal = Vergelijk.vergelijk((String) expected, (String) actual);
            } else {
                equal = expected.equals(actual);
            }
        }

        if (!equal) {
            verschillenLog.append(String.format("vergelijk %s: waarden zijn niet gelijk (expected=%s, actual=%s)%n", naam, expected, actual));
        }

        return equal;
    }

}
