/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;

public class VerblijfsrechtConversietabel implements
        Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3AanduidingVerblijfstitelCode LO3_NIET_VALIDE_UITZONDERING =
            new Lo3AanduidingVerblijfstitelCode("99");

    @Override
    public BrpVerblijfsrechtCode converteerNaarBrp(final Lo3AanduidingVerblijfstitelCode input) {
        return input == null ? null : new BrpVerblijfsrechtCode(input.getCode());
    }

    @Override
    public Lo3AanduidingVerblijfstitelCode converteerNaarLo3(final BrpVerblijfsrechtCode input) {
        return input == null ? null : new Lo3AanduidingVerblijfstitelCode(input.getOmschrijving());
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingVerblijfstitelCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpVerblijfsrechtCode input) {
        return true;
    }

}
