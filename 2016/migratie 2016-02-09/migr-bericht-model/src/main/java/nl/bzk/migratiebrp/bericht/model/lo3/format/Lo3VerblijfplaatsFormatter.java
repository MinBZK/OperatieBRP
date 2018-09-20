/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Format een Lo3 verblijfplaats.
 *
 */
public final class Lo3VerblijfplaatsFormatter implements Lo3CategorieFormatter<Lo3VerblijfplaatsInhoud> {

    @Override
    public void format(final Lo3VerblijfplaatsInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(Lo3ElementEnum.ELEMENT_0910, Lo3Format.format(categorie.getGemeenteInschrijving()));
        formatter.element(Lo3ElementEnum.ELEMENT_0920, Lo3Format.format(categorie.getDatumInschrijving()));
        formatter.element(Lo3ElementEnum.ELEMENT_1010, Lo3Format.format(categorie.getFunctieAdres()));
        formatter.element(Lo3ElementEnum.ELEMENT_1020, Lo3Format.format(categorie.getGemeenteDeel()));
        formatter.element(Lo3ElementEnum.ELEMENT_1030, Lo3Format.format(categorie.getAanvangAdreshouding()));
        formatter.element(Lo3ElementEnum.ELEMENT_1110, Lo3Format.format(categorie.getStraatnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_1115, Lo3Format.format(categorie.getNaamOpenbareRuimte()));
        formatter.element(Lo3ElementEnum.ELEMENT_1120, Lo3Format.format(categorie.getHuisnummer()));
        formatter.element(Lo3ElementEnum.ELEMENT_1130, Lo3Format.format(categorie.getHuisletter()));
        formatter.element(Lo3ElementEnum.ELEMENT_1140, Lo3Format.format(categorie.getHuisnummertoevoeging()));
        formatter.element(Lo3ElementEnum.ELEMENT_1150, Lo3Format.format(categorie.getAanduidingHuisnummer()));
        formatter.element(Lo3ElementEnum.ELEMENT_1160, Lo3Format.format(categorie.getPostcode()));
        formatter.element(Lo3ElementEnum.ELEMENT_1170, Lo3Format.format(categorie.getWoonplaatsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_1180, Lo3Format.format(categorie.getIdentificatiecodeVerblijfplaats()));
        formatter.element(Lo3ElementEnum.ELEMENT_1190, Lo3Format.format(categorie.getIdentificatiecodeNummeraanduiding()));
        formatter.element(Lo3ElementEnum.ELEMENT_1210, Lo3Format.format(categorie.getLocatieBeschrijving()));
        formatter.element(Lo3ElementEnum.ELEMENT_1310, Lo3Format.format(categorie.getLandAdresBuitenland()));
        formatter.element(Lo3ElementEnum.ELEMENT_1320, Lo3Format.format(categorie.getDatumVertrekUitNederland()));
        formatter.element(Lo3ElementEnum.ELEMENT_1330, Lo3Format.format(categorie.getAdresBuitenland1()));
        formatter.element(Lo3ElementEnum.ELEMENT_1340, Lo3Format.format(categorie.getAdresBuitenland2()));
        formatter.element(Lo3ElementEnum.ELEMENT_1350, Lo3Format.format(categorie.getAdresBuitenland3()));
        formatter.element(Lo3ElementEnum.ELEMENT_1410, Lo3Format.format(categorie.getLandVanwaarIngeschreven()));
        formatter.element(Lo3ElementEnum.ELEMENT_1420, Lo3Format.format(categorie.getVestigingInNederland()));
        formatter.element(Lo3ElementEnum.ELEMENT_7210, Lo3Format.format(categorie.getAangifteAdreshouding()));
        formatter.element(Lo3ElementEnum.ELEMENT_7510, Lo3Format.format(categorie.getIndicatieDocument()));
    }

}
