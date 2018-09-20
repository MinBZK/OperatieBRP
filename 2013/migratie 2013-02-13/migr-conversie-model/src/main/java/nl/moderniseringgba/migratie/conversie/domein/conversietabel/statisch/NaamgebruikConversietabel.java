/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP naamgebruik.
 */
public final class NaamgebruikConversietabel implements
        Conversietabel<Lo3AanduidingNaamgebruikCode, BrpWijzeGebruikGeslachtsnaamCode> {

    @Override
    public Lo3AanduidingNaamgebruikCode converteerNaarLo3(final BrpWijzeGebruikGeslachtsnaamCode input) {
        if (input == null) {
            return null;
        }
        return new Lo3AanduidingNaamgebruikCode(input.toString());
    }

    @Override
    public BrpWijzeGebruikGeslachtsnaamCode converteerNaarBrp(final Lo3AanduidingNaamgebruikCode input) {
        if (input == null) {
            return null;
        }
        return BrpWijzeGebruikGeslachtsnaamCode.valueOf(input.getCode());
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingNaamgebruikCode input) {
        return input == null || Lo3AanduidingNaamgebruikCodeEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpWijzeGebruikGeslachtsnaamCode input) {
        // Enum
        return true;
    }

}
