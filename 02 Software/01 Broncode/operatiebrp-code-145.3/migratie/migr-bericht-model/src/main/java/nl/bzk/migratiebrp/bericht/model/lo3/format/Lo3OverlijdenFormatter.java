/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Format een Lo3 overlijden.
 */
public final class Lo3OverlijdenFormatter implements Lo3CategorieFormatter<Lo3OverlijdenInhoud> {

    @Override
    public void format(final Lo3OverlijdenInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(Lo3ElementEnum.ELEMENT_0810, Lo3Format.format(categorie.getDatum()));
        formatter.element(Lo3ElementEnum.ELEMENT_0820, Lo3Format.format(categorie.getGemeenteCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0830, Lo3Format.format(categorie.getLandCode()));
    }

}
