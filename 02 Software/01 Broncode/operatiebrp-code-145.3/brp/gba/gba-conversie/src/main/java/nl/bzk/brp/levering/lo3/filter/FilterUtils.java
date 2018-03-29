/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Filter utility methods.
 */
public final class FilterUtils {

    /**
     * Niet instantieerbaar.
     */
    private FilterUtils() {
        // Niet instantieerbaar
    }

    /**
     * Geeft aan of persoon is opgeshort met reden F, oftewel een nadereBijhoudingsAard.FOUT heeft.
     * @param categorieen categorieen
     * @return true als persoon is opgeschort met reden F, anders false
     */
    public static boolean isAfgevoerd(final List<Lo3CategorieWaarde> categorieen) {
        for (final Lo3CategorieWaarde categorie : categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_07.equals(categorie.getCategorie())) {
                if ("F".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_6720))) {
                    return true;
                }
                break;
            }
        }

        return false;

    }
}
