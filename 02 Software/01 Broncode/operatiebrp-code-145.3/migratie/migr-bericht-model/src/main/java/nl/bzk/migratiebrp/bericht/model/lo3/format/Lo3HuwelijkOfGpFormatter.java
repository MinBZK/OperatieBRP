/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Format een Lo3 huwelijk of gp.
 */
public final class Lo3HuwelijkOfGpFormatter implements Lo3CategorieFormatter<Lo3HuwelijkOfGpInhoud> {

    @Override
    public void format(final Lo3HuwelijkOfGpInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(categorie.getaNummer()));
        formatter.element(Lo3ElementEnum.ELEMENT_0120, Lo3Format.format(categorie.getBurgerservicenummer()));
        formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(categorie.getVoornamen()));
        formatter.element(Lo3ElementEnum.ELEMENT_0220, Lo3Format.format(categorie.getAdellijkeTitelPredikaatCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0230, Lo3Format.format(categorie.getVoorvoegselGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format(categorie.getGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(categorie.getGeboortedatum()));
        formatter.element(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(categorie.getGeboorteGemeenteCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(categorie.getGeboorteLandCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0410, Lo3Format.format(categorie.getGeslachtsaanduiding()));
        formatter.element(Lo3ElementEnum.ELEMENT_0610, Lo3Format.format(categorie.getDatumSluitingHuwelijkOfAangaanGp()));
        formatter.element(Lo3ElementEnum.ELEMENT_0620, Lo3Format.format(categorie.getGemeenteCodeSluitingHuwelijkOfAangaanGp()));
        formatter.element(Lo3ElementEnum.ELEMENT_0630, Lo3Format.format(categorie.getLandCodeSluitingHuwelijkOfAangaanGp()));
        formatter.element(Lo3ElementEnum.ELEMENT_0710, Lo3Format.format(categorie.getDatumOntbindingHuwelijkOfGp()));
        formatter.element(Lo3ElementEnum.ELEMENT_0720, Lo3Format.format(categorie.getGemeenteCodeOntbindingHuwelijkOfGp()));
        formatter.element(Lo3ElementEnum.ELEMENT_0730, Lo3Format.format(categorie.getLandCodeOntbindingHuwelijkOfGp()));
        formatter.element(Lo3ElementEnum.ELEMENT_0740, Lo3Format.format(categorie.getRedenOntbindingHuwelijkOfGpCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_1510, Lo3Format.format(categorie.getSoortVerbintenis()));
    }

}
