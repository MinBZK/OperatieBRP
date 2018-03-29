/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Format een Lo3 inschrijving.
 */
public final class Lo3InschrijvingFormatter implements Lo3CategorieFormatter<Lo3InschrijvingInhoud> {

    @Override
    public void format(final Lo3InschrijvingInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(Lo3ElementEnum.ELEMENT_6620, Lo3Format.format(categorie.getDatumIngangBlokkering()));
        formatter.element(Lo3ElementEnum.ELEMENT_6710, Lo3Format.format(categorie.getDatumOpschortingBijhouding()));
        formatter.element(Lo3ElementEnum.ELEMENT_6720, Lo3Format.format(categorie.getRedenOpschortingBijhoudingCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_6810, Lo3Format.format(categorie.getDatumEersteInschrijving()));
        formatter.element(Lo3ElementEnum.ELEMENT_6910, Lo3Format.format(categorie.getGemeentePKCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_7010, Lo3Format.format(categorie.getIndicatieGeheimCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_7110, Lo3Format.format(categorie.getDatumVerificatie()));
        formatter.element(Lo3ElementEnum.ELEMENT_7120, Lo3Format.format(categorie.getOmschrijvingVerificatie()));
        formatter.element(Lo3ElementEnum.ELEMENT_8010, Lo3Format.format(categorie.getVersienummer()));
        formatter.element(Lo3ElementEnum.ELEMENT_8020, Lo3Format.format(categorie.getDatumtijdstempel()));
        formatter.element(Lo3ElementEnum.ELEMENT_8710, Lo3Format.format(categorie.getIndicatiePKVolledigGeconverteerdCode()));
    }

}
