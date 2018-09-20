/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0110;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0120;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0210;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0220;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0230;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0240;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0310;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0320;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0330;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0910;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0920;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1110;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1115;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1120;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1130;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1140;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1150;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1160;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1170;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1180;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1190;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_1210;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_7010;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;

//CHECKSTYLE:ON 

/**
 * Format een Lo3 Verwijzing.
 */
public final class Lo3VerwijzingFormatter implements Lo3CategorieFormatter<Lo3VerwijzingInhoud> {

    @Override
    public void format(final Lo3VerwijzingInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_0110, Lo3Format.format(categorie.getANummer()));
        formatter.element(ELEMENT_0120, Lo3Format.format(categorie.getBurgerservicenummer()));
        formatter.element(ELEMENT_0210, Lo3Format.format(categorie.getVoornamen()));
        formatter.element(ELEMENT_0220, Lo3Format.format(categorie.getAdellijkeTitelPredikaatCode()));
        formatter.element(ELEMENT_0230, Lo3Format.format(categorie.getVoorvoegselGeslachtsnaam()));
        formatter.element(ELEMENT_0240, Lo3Format.format(categorie.getGeslachtsnaam()));
        formatter.element(ELEMENT_0310, Lo3Format.format(categorie.getGeboortedatum()));
        formatter.element(ELEMENT_0320, Lo3Format.format(categorie.getGeboorteGemeenteCode()));
        formatter.element(ELEMENT_0330, Lo3Format.format(categorie.getGeboorteLandCode()));
        formatter.element(ELEMENT_0910, Lo3Format.format(categorie.getGemeenteInschrijving()));
        formatter.element(ELEMENT_0920, Lo3Format.format(categorie.getDatumInschrijving()));
        formatter.element(ELEMENT_1110, Lo3Format.format(categorie.getStraatnaam()));
        formatter.element(ELEMENT_1115, Lo3Format.format(categorie.getNaamOpenbareRuimte()));
        formatter.element(ELEMENT_1120, Lo3Format.format(categorie.getHuisnummer()));
        formatter.element(ELEMENT_1130, Lo3Format.format(categorie.getHuisletter()));
        formatter.element(ELEMENT_1140, Lo3Format.format(categorie.getHuisnummertoevoeging()));
        formatter.element(ELEMENT_1150, Lo3Format.format(categorie.getAanduidingHuisnummer()));
        formatter.element(ELEMENT_1160, Lo3Format.format(categorie.getPostcode()));
        formatter.element(ELEMENT_1170, Lo3Format.format(categorie.getWoonplaatsnaam()));
        formatter.element(ELEMENT_1180, Lo3Format.format(categorie.getIdentificatiecodeVerblijfplaats()));
        formatter.element(ELEMENT_1190, Lo3Format.format(categorie.getIdentificatiecodeNummeraanduiding()));
        formatter.element(ELEMENT_1210, Lo3Format.format(categorie.getLocatieBeschrijving()));
        formatter.element(ELEMENT_7010, Lo3Format.format(categorie.getIndicatieGeheimCode()));
    }

}
