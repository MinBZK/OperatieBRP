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
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_0410;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_6210;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;

//CHECKSTYLE:ON

/**
 * Format een Lo3 ouder.
 * 
 */
public final class Lo3OuderFormatter implements Lo3CategorieFormatter<Lo3OuderInhoud> {

    @Override
    public void format(final Lo3OuderInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_0110, Lo3Format.format(categorie.getaNummer()));
        formatter.element(ELEMENT_0120, Lo3Format.format(categorie.getBurgerservicenummer()));
        formatter.element(ELEMENT_0210, Lo3Format.format(categorie.getVoornamen()));
        formatter.element(ELEMENT_0220, Lo3Format.format(categorie.getAdellijkeTitelPredikaatCode()));
        formatter.element(ELEMENT_0230, Lo3Format.format(categorie.getVoorvoegselGeslachtsnaam()));
        formatter.element(ELEMENT_0240, Lo3Format.format(categorie.getGeslachtsnaam()));
        formatter.element(ELEMENT_0310, Lo3Format.format(categorie.getGeboortedatum()));
        formatter.element(ELEMENT_0320, Lo3Format.format(categorie.getGeboorteGemeenteCode()));
        formatter.element(ELEMENT_0330, Lo3Format.format(categorie.getGeboorteLandCode()));
        formatter.element(ELEMENT_0410, Lo3Format.format(categorie.getGeslachtsaanduiding()));
        formatter.element(ELEMENT_6210, Lo3Format.format(categorie.getFamilierechtelijkeBetrekking()));
    }

}
