/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.test.common.vergelijk.Vergelijk;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatInhoud;

/**
 * Class biedt de mogelijkheid om 2 lijsten met {@link nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde}
 * te vergelijken.
 */
public final class Lo3CategorieWaardenVergelijker {

    private static final String ELEMENT_SCHEIDINGSTEKEN = ".";
    private static final String WAARDE_LOG_AFSLUITING = "):\n ";

    private Lo3CategorieWaardenVergelijker() {
    }

    /**
     * Vergelijkt 2 lijsten met {@link nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde} met elkaar.
     * Eventuele verschillen worden in de verschillenlog parameter toegevoegd.
     *
     * @param verschillenLog
     *            log met gevonden verschillen.
     * @param expected
     *            lijst met verwachte categorie waarden
     * @param actual
     *            lijst met huidige categorie waarden
     * @param vergelijkingResultaten
     *            lijst met vergelijkingresultaten die wijzigingen bevatten.
     * @return true als de lijsten gelijk zijn qua inhoud.
     */
    public static List<LeveringsVergelijkingResultaatInhoud> vergelijk(
        final StringBuilder verschillenLog,
        final List<Lo3CategorieWaarde> expected,
        final List<Lo3CategorieWaarde> actual,
        final List<LeveringsVergelijkingResultaatInhoud> vergelijkingResultaten)
    {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk categorie lijst:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog.append(String.format(
                    "Lijsten bevatten niet even veel categorieen (expected=%s, actual=%s)%n",
                    expected.size(),
                    actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                final Lo3CategorieWaarde expectedItem = expected.get(index);
                final Lo3CategorieWaarde actualItem = actual.get(index);

                if (equal && !vergelijkLo3CategorieWaarde(lokaalVerschillenLog, index, expectedItem, actualItem, vergelijkingResultaten)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return vergelijkingResultaten;
    }

    private static boolean vergelijkLo3CategorieWaarde(
        final StringBuilder verschillenLog,
        final int index,
        final Lo3CategorieWaarde expected,
        final Lo3CategorieWaarde actual,
        final List<LeveringsVergelijkingResultaatInhoud> vergelijkingResultaten)
    {
        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk categorie waarde (").append(index).append(WAARDE_LOG_AFSLUITING);

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de categorie waarden is null\n");
                equal = false;
            }
        } else {
            final List<String> tijdelijkeLijstMetVergelijkingResultaten = new ArrayList<>();

            final boolean equalsCategorie = equals(lokaalVerschillenLog, "categorie", expected.getCategorie(), actual.getCategorie());
            final boolean equalsStapel = equals(lokaalVerschillenLog, "stapel", expected.getStapel(), actual.getStapel());
            final boolean equalsVoorkomen = equals(lokaalVerschillenLog, "voorkomen", expected.getVoorkomen(), actual.getVoorkomen());
            final boolean equalsElementen =
                    vergelijkElementen(lokaalVerschillenLog, expected.getElementen(), actual.getElementen(), tijdelijkeLijstMetVergelijkingResultaten);

            if (!equalsCategorie || !equalsStapel || !equalsVoorkomen || !equalsElementen) {
                for (final String tijdelijkLeveringVergelijkerResultaat : tijdelijkeLijstMetVergelijkingResultaten) {
                    final LeveringsVergelijkingResultaatInhoud leveringsVergelijkingResultaatInhoud = new LeveringsVergelijkingResultaatInhoud();
                    leveringsVergelijkingResultaatInhoud.setStapel(expected.getStapel());
                    leveringsVergelijkingResultaatInhoud.setVoorkomen(expected.getVoorkomen());
                    leveringsVergelijkingResultaatInhoud.setCategorie(expected.getCategorie().getCategorieAsInt());
                    leveringsVergelijkingResultaatInhoud.setElement(expected.getCategorie().getCategorie()
                                                                    + ELEMENT_SCHEIDINGSTEKEN
                                                                    + tijdelijkLeveringVergelijkerResultaat);
                    vergelijkingResultaten.add(leveringsVergelijkingResultaatInhoud);
                }
                equal = false;
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return equal;
    }

    /**
     * Vergelijkt de 2 mappen, bestaande uit een element nummer en zijn waarde, op inhoud. Eventuele verschillen worden
     * aan de parameter verschillenLog toegevoegd.
     *
     * @param verschillenLog
     *            log met gevonden verschillen.
     * @param expected
     *            map van Lo3 elementen die verwacht worden
     * @param actual
     *            map van Lo3 elementen die verkregen zijn
     * @param vergelijkingResultaten
     *            lijst met vergelijkingresultaten die wijzigingen bevatten.
     * @return true als de mappen inhoudelijk overeenkomen
     */
    public static boolean vergelijkElementen(
        final StringBuilder verschillenLog,
        final Map<Lo3ElementEnum, String> expected,
        final Map<Lo3ElementEnum, String> actual,
        final List<String> vergelijkingResultaten)
    {

        boolean equal = true;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk elementen map:\n ");

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de maps is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                lokaalVerschillenLog.append(String.format(
                    "Lijsten bevatten niet even veel elementen (expected=%s, actual=%s)%n",
                    expected.size(),
                    actual.size()));
                vergelijkingResultaten.add("");
                equal = false;
            }

            for (final Map.Entry<Lo3ElementEnum, String> expectedEntry : expected.entrySet()) {
                final Lo3ElementEnum sleutel = expectedEntry.getKey();
                final String expectedValue = expectedEntry.getValue();
                final String actualValue = actual.get(sleutel);

                final String elementWaardeVergelijkResultaat = vergelijkElementWaarde(lokaalVerschillenLog, sleutel, expectedValue, actualValue);

                if (elementWaardeVergelijkResultaat != null) {
                    vergelijkingResultaten.add(elementWaardeVergelijkResultaat);
                    equal = false;
                }
            }
        }

        if (!equal) {
            verschillenLog.append(lokaalVerschillenLog);
        }
        return equal;
    }

    private static String vergelijkElementWaarde(
        final StringBuilder verschillenLog,
        final Lo3ElementEnum element,
        final String expected,
        final String actual)
    {
        String resultaat = null;
        final StringBuilder lokaalVerschillenLog = new StringBuilder();
        lokaalVerschillenLog.append("Vergelijk element waarde (").append(element).append(WAARDE_LOG_AFSLUITING);

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                lokaalVerschillenLog.append("Een van de waarden is null\n");
                resultaat = element.getGroep() + ELEMENT_SCHEIDINGSTEKEN + element.getRubriek();
            }
        } else {
            if (!equals(lokaalVerschillenLog, "waarde", expected, actual)) {
                resultaat = element.getGroep() + ELEMENT_SCHEIDINGSTEKEN + element.getRubriek();
            }
        }

        if (resultaat != null) {
            verschillenLog.append(lokaalVerschillenLog);
        }

        return resultaat;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T> boolean equals(final StringBuilder verschillenLog, final String naam, final T expected, final T actual) {
        final boolean equal;
        if (expected == null) {
            equal = actual == null;
        } else {
            if (expected instanceof String && actual instanceof String) {
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
