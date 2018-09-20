/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0510;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6310;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6410;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6510;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;

//CHECKSTYLE:ON

/**
 * Format een Lo3 nationaliteit.
 * 
 */
public final class Lo3NationaliteitFormatter implements Lo3CategorieFormatter<Lo3NationaliteitInhoud> {

    @Override
    public void format(final Lo3NationaliteitInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_0510, Lo3Format.format(categorie.getNationaliteitCode()));
        formatter.element(ELEMENT_6310, Lo3Format.format(categorie.getRedenVerkrijgingNederlandschapCode()));
        formatter.element(ELEMENT_6410, Lo3Format.format(categorie.getRedenVerliesNederlandschapCode()));
        formatter.element(ELEMENT_6510, Lo3Format.format(categorie.getAanduidingBijzonderNederlandschap()));
    }

}
