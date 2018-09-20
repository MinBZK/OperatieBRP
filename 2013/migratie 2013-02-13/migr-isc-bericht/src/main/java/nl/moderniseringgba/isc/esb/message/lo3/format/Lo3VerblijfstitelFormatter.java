/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3910;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3920;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_3930;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;

//CHECKSTYLE:ON

/**
 * Format een Lo3 verblijfstitel.
 */
public final class Lo3VerblijfstitelFormatter implements Lo3CategorieFormatter<Lo3VerblijfstitelInhoud> {

    @Override
    public void format(final Lo3VerblijfstitelInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(ELEMENT_3910, Lo3Format.format(categorie.getAanduidingVerblijfstitelCode()));
        formatter.element(ELEMENT_3920, Lo3Format.format(categorie.getDatumEindeVerblijfstitel()));
        formatter.element(ELEMENT_3930, Lo3Format.format(categorie.getIngangsdatumVerblijfstitel()));
    }

}
