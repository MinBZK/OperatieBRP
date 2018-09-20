/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Formatter utilities.
 */
public final class FormatterUtil {

    /**
     * Niet instantieerbaar.
     */
    private FormatterUtil() {
        // Niet instantieerbaar
    }

    /**
     * Geef de waarde van een gegeven element in het eerste voorkomen van een gegeven categorie.
     *
     * @param categorieen categorieen
     * @param categorie categorie
     * @param element element
     * @return waarde
     */
    public static String geefElementWaarde(final List<Lo3CategorieWaarde> categorieen, final Lo3CategorieEnum categorie, final Lo3ElementEnum element) {
        for (final Lo3CategorieWaarde categorieWaarde : categorieen) {
            if (categorieWaarde.getCategorie().equals(categorie)) {
                return categorieWaarde.getElement(element);
            }
        }
        return null;
    }
}
