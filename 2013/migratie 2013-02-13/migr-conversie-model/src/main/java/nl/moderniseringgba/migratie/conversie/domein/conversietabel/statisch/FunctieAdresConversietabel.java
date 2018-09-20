/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP functie adres.
 */
public final class FunctieAdresConversietabel implements Conversietabel<Lo3FunctieAdres, BrpFunctieAdresCode> {

    @Override
    public BrpFunctieAdresCode converteerNaarBrp(final Lo3FunctieAdres input) {
        if (input == null) {
            return null;
        }

        return BrpFunctieAdresCode.valueOf(input.getCode());
    }

    @Override
    public Lo3FunctieAdres converteerNaarLo3(final BrpFunctieAdresCode input) {
        if (input == null) {
            return null;
        }

        return new Lo3FunctieAdres(input.toString());
    }

    @Override
    public boolean valideerLo3(final Lo3FunctieAdres input) {
        return input == null || Lo3FunctieAdresEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpFunctieAdresCode input) {
        // Enum
        return true;
    }
}
