/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3110;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3120;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3130;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3810;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3820;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;

//CHECKSTYLE:ON

/**
 * Format een Lo3 kiesrecht.
 * 
 */
public final class Lo3KiesrechtFormatter implements Lo3CategorieFormatter<Lo3KiesrechtInhoud> {

    @Override
    public void format(final Lo3KiesrechtInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_3110, Lo3Format.format(categorie.getAanduidingEuropeesKiesrecht()));
        formatter.element(ELEMENT_3120, Lo3Format.format(categorie.getDatumEuropeesKiesrecht()));
        formatter.element(ELEMENT_3130, Lo3Format.format(categorie.getEinddatumUitsluitingEuropeesKiesrecht()));
        formatter.element(ELEMENT_3810, Lo3Format.format(categorie.getAanduidingUitgeslotenKiesrecht()));
        formatter.element(ELEMENT_3820, Lo3Format.format(categorie.getEinddatumUitsluitingKiesrecht()));
    }
}
