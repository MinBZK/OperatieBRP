/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

public class SoortReisdocumentConversietabel implements
        Conversietabel<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3SoortNederlandsReisdocument LO3_NIET_VALIDE_UITZONDERING =
            new Lo3SoortNederlandsReisdocument("QQ");

    @Override
    public BrpReisdocumentSoort converteerNaarBrp(final Lo3SoortNederlandsReisdocument input) {
        return input == null ? null : new BrpReisdocumentSoort(input.getSoort());
    }

    @Override
    public Lo3SoortNederlandsReisdocument converteerNaarLo3(final BrpReisdocumentSoort input) {
        return input == null ? null : new Lo3SoortNederlandsReisdocument(input.getCode());
    }

    @Override
    public boolean valideerLo3(final Lo3SoortNederlandsReisdocument input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpReisdocumentSoort input) {
        return true;
    }

}
