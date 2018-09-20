/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0810;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0820;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0830;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;

//CHECKSTYLE:ON

/**
 * Format een Lo3 overlijden.
 * 
 */
public final class Lo3OverlijdenFormatter implements Lo3CategorieFormatter<Lo3OverlijdenInhoud> {

    @Override
    public void format(final Lo3OverlijdenInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_0810, Lo3Format.format(categorie.getDatum()));
        formatter.element(ELEMENT_0820, Lo3Format.format(categorie.getGemeenteCode()));
        formatter.element(ELEMENT_0830, Lo3Format.format(categorie.getLandCode()));
    }

}
