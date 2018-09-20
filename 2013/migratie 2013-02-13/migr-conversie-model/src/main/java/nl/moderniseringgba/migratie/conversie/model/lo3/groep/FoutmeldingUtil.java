/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.groep;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;

/**
 * Utility class om foutmeldingen uniform weer te geven.
 * 
 */
public final class FoutmeldingUtil {

    private FoutmeldingUtil() {
        throw new AssertionError("FoutmeldingUtil mag niet geinitialiseerd worden");
    }

    /**
     * Gooi een {@link NullPointerException} omdat een attribuut verplicht is, maar ontbreekt.
     * 
     * @param naam
     *            naam van het ontbrekend attribuut
     * @throws NullPointerException
     *             omdat het attribuut onverwacht ontbreekt.
     */
    public static void gooiVerplichtMaarNietGevuldFoutmelding(final String naam) {
        gooiVerplichtMaarNietGevuldFoutmelding(naam, null);
    }

    /**
     * Gooi een {@link NullPointerException} omdat een attribuut verplicht is, maar ontbreekt.
     * 
     * @param naam
     *            naam van het ontbrekend attribuut of groep
     * @param preconditie
     *            de preconditie die wordt geschonden.
     * @throws NullPointerException
     *             omdat het attribuut onverwacht ontbreekt.
     */
    public static void gooiVerplichtMaarNietGevuldFoutmelding(final String naam, final Precondities preconditie) {
        gooiVerplichtMaarNietGevuldFoutmelding(naam, null, preconditie);
    }

    /**
     * Gooi een {@link NullPointerException} omdat een attribuut verplicht is, maar ontbreekt.
     * 
     * @param naam
     *            naam van het ontbrekend attribuut of groep
     * @param categorie
     *            de categorie waarin iets ontbreekt
     * @param preconditie
     *            de preconditie die wordt geschonden.
     * @throws NullPointerException
     *             omdat het attribuut onverwacht ontbreekt.
     */
    public static void gooiVerplichtMaarNietGevuldFoutmelding(
            final String naam,
            final Lo3CategorieEnum categorie,
            final Precondities preconditie) {
        throw new NullPointerException(maakFoutmelding(categorie, naam + " is verplicht maar niet gevuld.",
                preconditie));
    }

    /**
     * Gooi een {@link IllegalArgumentException} omdat een validatie niet is geslaagd.
     * 
     * @param foutmelding
     *            de specifieke foutmelding van de validatie
     * @param preconditie
     *            de preconditie die wordt geschonden
     * @throws IllegalArgumentException
     *             omdat een validatie niet is geslaagd.
     */
    public static void gooiValidatieExceptie(final String foutmelding, final Precondities preconditie) {
        gooiValidatieExceptie(null, foutmelding, preconditie);
    }

    /**
     * Gooi een {@link IllegalArgumentException} omdat een validatie niet is geslaagd.
     * 
     * @param categorie
     *            de categorie waarin de validatiefout is opgetreden
     * @param foutmelding
     *            de specifieke foutmelding van de validatie
     * @param preconditie
     *            de preconditie die wordt geschonden
     * @throws IllegalArgumentException
     *             omdat een validatie niet is geslaagd.
     */
    public static void gooiValidatieExceptie(
            final Lo3CategorieEnum categorie,
            final String foutmelding,
            final Precondities preconditie) {
        throw new IllegalArgumentException(maakFoutmelding(categorie, foutmelding, preconditie));
    }

    /**
     * Returned een {@link IllegalArgumentException} omdat een validatie niet is geslaagd.
     * 
     * @param foutmelding
     *            de specifieke foutmelding van de validatie
     * @param preconditie
     *            de preconditie die wordt geschonden
     * @return exception
     */
    public static IllegalArgumentException getValidatieExceptie(
            final String foutmelding,
            final Precondities preconditie) {
        return new IllegalArgumentException(maakFoutmelding(null, foutmelding, preconditie));
    }

    private static String maakFoutmelding(
            final Lo3CategorieEnum categorie,
            final String foutmelding,
            final Precondities preconditie) {
        final StringBuilder foutBuilder = new StringBuilder(foutmelding);
        if (categorie != null) {
            foutBuilder.insert(0, "Categorie " + categorie.getCategorie() + ": ");
        }
        if (preconditie != null) {
            if (foutBuilder.charAt(foutBuilder.length() - 1) != '.') {
                foutBuilder.append(".");
            }
            foutBuilder.append(" Deze eis komt voort uit ").append(preconditie);
        }
        return foutBuilder.toString();
    }

}
