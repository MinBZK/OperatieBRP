/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Format een Lo3 Verwijzing.
 */
public final class Lo3VerwijzingFormatter implements Lo3CategorieFormatter<Lo3VerwijzingInhoud> {

    @Override
    public void format(final Lo3VerwijzingInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(categorie.getANummer()));
        formatter.element(Lo3ElementEnum.ELEMENT_0120, Lo3Format.format(categorie.getBurgerservicenummer()));
        formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(categorie.getVoornamen()));
        formatter.element(Lo3ElementEnum.ELEMENT_0220, Lo3Format.format(categorie.getAdellijkeTitelPredikaatCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0230, Lo3Format.format(categorie.getVoorvoegselGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format(categorie.getGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(categorie.getGeboortedatum()));
        formatter.element(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(categorie.getGeboorteGemeenteCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(categorie.getGeboorteLandCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0910, Lo3Format.format(categorie.getGemeenteInschrijving()));
        formatter.element(Lo3ElementEnum.ELEMENT_0920, Lo3Format.format(categorie.getDatumInschrijving()));
        formatter.element(Lo3ElementEnum.ELEMENT_7010, Lo3Format.format(categorie.getIndicatieGeheimCode()));
    }

}
