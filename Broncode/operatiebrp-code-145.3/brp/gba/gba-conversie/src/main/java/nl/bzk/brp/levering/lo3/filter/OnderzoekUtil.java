/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;

/**
 * Onderzoek utilities.
 */
public final class OnderzoekUtil {

    private static final String SEPARATOR = ".";
    private static final String PADDING = "0";

    private static final int LENGTE_8310 = 6;

    private static final int LENGTE_CATEGORIE = 2;
    private static final int LENGTE_GROEP = 2;
    private static final int LENGTE_ELEMENT = 2;

    private static final int START_CATEGORIE = 0;
    private static final int EIND_CATEGORIE = START_CATEGORIE + LENGTE_CATEGORIE;
    private static final int START_GROEP = EIND_CATEGORIE;
    private static final int EIND_GROEP = START_GROEP + LENGTE_GROEP;
    private static final int START_ELEMENT = EIND_GROEP;
    private static final int EIND_ELEMENT = START_ELEMENT + LENGTE_ELEMENT;

    private OnderzoekUtil() {
        // Niet instantieerbaar
    }

    /**
     * Doordat tijdens de heen- en weerconversie dit element ook als nummer wordt gekenmerkt kan de voorloop nul
     * wegvallen.
     * @param deEchteCategorie de categorie waaruit het onderzoek komt (override de categorie waarde in 83.10)
     * @param element 83.10
     * @return 83.10 met overschreven categorie, met voorloop nullen als de lengte minder dan 6 was
     */
    private static String fix8310Format(final String deEchteCategorie, final String element) {
        final String result;
        if (element == null) {
            result = null;
        } else {
            final StringBuilder padded = new StringBuilder(element);
            while (padded.length() < LENGTE_8310) {
                padded.insert(0, PADDING);
            }
            final String zonderCategorie = padded.substring(START_GROEP);

            final StringBuilder paddedOverridden = new StringBuilder(deEchteCategorie);
            paddedOverridden.append(zonderCategorie);
            while (paddedOverridden.length() < LENGTE_8310) {
                paddedOverridden.insert(0, PADDING);
            }

            result = paddedOverridden.toString();
        }
        return result;
    }

    /**
     * Bepaal of het gegeven onderzoek geleverd mag worden obv de gegeven filter rubrieken.
     * @param deEchteCategorie de categorie waaruit het onderzoek komt (override de categorie waarde in 83.10)
     * @param elementOnderzoek gegevens in onderzoek (83.10)
     * @param lo3Filterrubrieken filter rubrieken
     * @return true, als het onderzoek geleverd mag worden.
     */
    public static boolean magOnderzoekWordenGeleverd(final String deEchteCategorie, final String elementOnderzoek, final List<String> lo3Filterrubrieken) {
        final String onderzoek = fix8310Format(deEchteCategorie, elementOnderzoek);
        if (onderzoek == null || "".equals(onderzoek)) {
            return false;
        }

        final boolean result;
        if (onderzoek.endsWith("0000")) {
            // Hele categorie in onderzoek
            final String categorie = onderzoek.substring(START_CATEGORIE, EIND_CATEGORIE);
            result = lo3Filterrubrieken.stream().anyMatch(rubriek -> rubriek.startsWith(categorie));
        } else if (onderzoek.endsWith("00")) {
            // Groep in onderzoek

            // RNI onderzoek moet altijd geleverd worden.
            final String groepZonderCategorie = onderzoek.substring(START_GROEP, EIND_GROEP);
            if (Lo3GroepEnum.GROEP88.toString().equals(groepZonderCategorie) || Lo3GroepEnum.GROEP71.toString().equals(groepZonderCategorie)) {
                result = true;
            } else {
                final String groep = onderzoek.substring(START_CATEGORIE, EIND_CATEGORIE) + SEPARATOR + groepZonderCategorie;
                result = lo3Filterrubrieken.stream().anyMatch(rubriek -> rubriek.startsWith(groep));
            }
        } else {
            // Specifiek element in onderzoek

            // RNI onderzoek moet altijd geleverd worden.
            final String groepZonderCategorie = onderzoek.substring(START_GROEP, EIND_GROEP);
            if (Lo3GroepEnum.GROEP88.toString().equals(groepZonderCategorie)) {
                result = true;
            } else {
                final String element =
                        onderzoek.substring(START_CATEGORIE, EIND_CATEGORIE)
                                + SEPARATOR
                                + onderzoek.substring(START_GROEP, EIND_GROEP)
                                + SEPARATOR
                                + onderzoek.substring(START_ELEMENT, EIND_ELEMENT);
                result = lo3Filterrubrieken.contains(element);
            }
        }

        return result;
    }

    /**
     * Bepaal of het onderzoek slaat op een direct geautoriseerde rubriek.
     * @param deEchteCategorie categorie
     * @param elementOnderzoek element onderzoek
     * @param lo3Filterrubrieken lo3 filter rubrieken
     * @return true, als onderzoek slaat op direct geautoriseerde rubriek
     */
    public static boolean onderzoekSlaatOpDirectGeautoriseerdeRubriek(
            final Lo3CategorieEnum deEchteCategorie,
            final String elementOnderzoek,
            final List<String> lo3Filterrubrieken) {
        final String onderzoek = fix8310Format(deEchteCategorie.getCategorie(), elementOnderzoek);
        if (onderzoek == null || "".equals(onderzoek)) {
            return false;
        }

        final String toSearch;
        if (onderzoek.endsWith("0000")) {
            // Hele categorie in onderzoek
            toSearch = onderzoek.substring(START_CATEGORIE, EIND_CATEGORIE);
        } else if (onderzoek.endsWith("00")) {
            // Groep in onderzoek
            toSearch = onderzoek.substring(START_CATEGORIE, EIND_CATEGORIE) + SEPARATOR + onderzoek.substring(START_GROEP, EIND_GROEP);
        } else {
            // Specifiek element in onderzoek
            toSearch =
                    onderzoek.substring(START_CATEGORIE, EIND_CATEGORIE)
                            + SEPARATOR
                            + onderzoek.substring(START_GROEP, EIND_GROEP)
                            + SEPARATOR
                            + onderzoek.substring(START_ELEMENT, EIND_ELEMENT);
        }

        boolean result = false;
        for (final String lo3Filterrubriek : lo3Filterrubrieken) {
            if (lo3Filterrubriek.startsWith(toSearch)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
