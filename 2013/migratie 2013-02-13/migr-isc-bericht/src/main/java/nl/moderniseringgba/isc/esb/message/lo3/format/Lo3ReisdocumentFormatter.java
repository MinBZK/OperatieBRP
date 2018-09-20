/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3510;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3520;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3530;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3540;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3550;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3560;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3570;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3580;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3610;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3710;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;

//CHECKSTYLE:ON

/**
 * Format een Lo3 reisdocument.
 * 
 */
public final class Lo3ReisdocumentFormatter implements Lo3CategorieFormatter<Lo3ReisdocumentInhoud> {

    @Override
    public void format(final Lo3ReisdocumentInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_3510, Lo3Format.format(categorie.getSoortNederlandsReisdocument()));
        formatter.element(ELEMENT_3520, Lo3Format.format(categorie.getNummerNederlandsReisdocument()));
        formatter.element(ELEMENT_3530, Lo3Format.format(categorie.getDatumUitgifteNederlandsReisdocument()));
        formatter.element(ELEMENT_3540, Lo3Format.format(categorie.getAutoriteitVanAfgifteNederlandsReisdocument()));
        formatter.element(ELEMENT_3550, Lo3Format.format(categorie.getDatumEindeGeldigheidNederlandsReisdocument()));
        formatter.element(ELEMENT_3560,
                Lo3Format.format(categorie.getDatumInhoudingVermissingNederlandsReisdocument()));
        formatter.element(ELEMENT_3570,
                Lo3Format.format(categorie.getAanduidingInhoudingVermissingNederlandsReisdocument()));
        formatter.element(ELEMENT_3580, Lo3Format.format(categorie.getLengteHouder()));
        formatter.element(ELEMENT_3610, Lo3Format.format(categorie.getSignalering()));
        formatter.element(ELEMENT_3710, Lo3Format.format(categorie.getAanduidingBezitBuitenlandsReisdocument()));
    }

}
