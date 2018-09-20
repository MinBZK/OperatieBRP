/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Format een Lo3 gezagsverhouding.
 *
 */
public final class Lo3GezagsverhoudingFormatter implements Lo3CategorieFormatter<Lo3GezagsverhoudingInhoud> {

    @Override
    public void format(final Lo3GezagsverhoudingInhoud categorie, final Lo3Formatter formatter) {

        formatter.element(Lo3ElementEnum.ELEMENT_3210, Lo3Format.format(categorie.getIndicatieGezagMinderjarige()));
        formatter.element(Lo3ElementEnum.ELEMENT_3310, Lo3Format.format(categorie.getIndicatieCurateleregister()));
    }

}
