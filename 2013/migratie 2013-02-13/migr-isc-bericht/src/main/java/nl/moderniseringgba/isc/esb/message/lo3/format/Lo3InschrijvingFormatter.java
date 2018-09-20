/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6620;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6710;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6720;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6810;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6910;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_7010;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8010;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8020;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8710;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;

//CHECKSTYLE:ON

/**
 * Format een Lo3 inschrijving.
 * 
 */
public final class Lo3InschrijvingFormatter implements Lo3CategorieFormatter<Lo3InschrijvingInhoud> {

    @Override
    public void format(final Lo3InschrijvingInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_6620, Lo3Format.format(categorie.getDatumIngangBlokkering()));
        formatter.element(ELEMENT_6710, Lo3Format.format(categorie.getDatumOpschortingBijhouding()));
        formatter.element(ELEMENT_6720, Lo3Format.format(categorie.getRedenOpschortingBijhoudingCode()));
        formatter.element(ELEMENT_6810, Lo3Format.format(categorie.getDatumEersteInschrijving()));
        formatter.element(ELEMENT_6910, Lo3Format.format(categorie.getGemeentePKCode()));
        formatter.element(ELEMENT_7010, Lo3Format.format(categorie.getIndicatieGeheimCode()));
        formatter.element(ELEMENT_8010, Lo3Format.format(categorie.getVersienummer()));
        formatter.element(ELEMENT_8020, Lo3Format.format(categorie.getDatumtijdstempel()));
        formatter.element(ELEMENT_8710, Lo3Format.format(categorie.getIndicatiePKVolledigGeconverteerdCode()));
    }

}
