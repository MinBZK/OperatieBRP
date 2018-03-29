/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Utility class om foutmeldingen uniform weer te geven.
 */
public final class FoutmeldingUtil {

    private FoutmeldingUtil() {
        throw new AssertionError("FoutmeldingUtil mag niet geinitialiseerd worden");
    }

    /**
     * Gooi een {@link PreconditieException} omdat een validatie niet is geslaagd.
     * @param soortMeldingCode de soort melding die wordt geschonden
     * @param groepInhoud groep waar de preconditie wordt geschonden
     * @throws PreconditieException omdat een validatie niet geslaagd is.
     */
    public static void gooiValidatieExceptie(final SoortMeldingCode soortMeldingCode, final BrpGroepInhoud... groepInhoud) {
        final String[] groepNamen = new String[groepInhoud.length];
        for (int i = 0; i < groepInhoud.length; i++) {
            groepNamen[i] = groepInhoud[i].getClass().getName();
        }
        throw new PreconditieException(soortMeldingCode, groepNamen);
    }

    /**
     * Gooi een {@link PreconditieException} omdat een validatie niet is geslaagd.
     * @param soortMeldingCode de soort melding die wordt geschonden
     * @param clazz Klasse van de inhoud waar de preconditie van geschonden is
     * @throws PreconditieException omdat een validatie niet geslaagd is.
     */
    public static void gooiValidatieExceptie(final SoortMeldingCode soortMeldingCode, final Class<? extends BrpGroepInhoud> clazz) {
        throw new PreconditieException(soortMeldingCode, clazz.getName());
    }

    /**
     * Returned een {@link IllegalArgumentException} omdat een validatie niet is geslaagd.
     * @param foutmelding de specifieke foutmelding van de validatie
     * @param soortMeldingCode de soort melding die wordt geschonden
     * @param cause oorspronkelijke cause, mag null zijn.
     * @return exception
     */
    public static IllegalArgumentException getValidatieExceptie(final String foutmelding, final SoortMeldingCode soortMeldingCode, final Throwable cause) {
        return new IllegalArgumentException(maakFoutmelding(null, foutmelding, soortMeldingCode), cause);
    }

    private static String maakFoutmelding(final Lo3CategorieEnum categorie, final String foutmelding, final SoortMeldingCode soortMeldingCode) {
        final StringBuilder foutBuilder = new StringBuilder(foutmelding);
        if (categorie != null) {
            foutBuilder.insert(0, "Categorie " + categorie.getCategorie() + ": ");
        }
        if (soortMeldingCode != null) {
            if (foutBuilder.charAt(foutBuilder.length() - 1) != '.') {
                foutBuilder.append(".");
            }
            foutBuilder.append(" Deze eis komt voort uit ").append(soortMeldingCode);
        }
        return foutBuilder.toString();
    }

}
