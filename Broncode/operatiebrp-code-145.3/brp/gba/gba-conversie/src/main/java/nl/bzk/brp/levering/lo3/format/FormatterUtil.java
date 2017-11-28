/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Formatter utilities.
 */
interface FormatterUtil {

    /**
     * Geef de waarde van een gegeven element in het eerste voorkomen van een gegeven categorie.
     * @param categorieen categorieen
     * @param categorie categorie
     * @param element element
     * @return waarde
     */
    static String geefElementWaarde(final List<Lo3CategorieWaarde> categorieen, final Lo3CategorieEnum categorie, final Lo3ElementEnum element) {
        if (categorieen == null || categorieen.isEmpty()) {
            return null;
        }
        return categorieen.stream()
                .filter(categorieWaarde -> categorieWaarde.getCategorie().equals(categorie))
                .findFirst()
                .map(waarde -> waarde.getElement(element))
                .orElse(null);
    }
}
