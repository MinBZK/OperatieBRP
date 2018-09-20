/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;

public class RedenEindeRelatieConversietabel implements
        Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3RedenOntbindingHuwelijkOfGpCode LO3_NIET_VALIDE_UITZONDERING =
            new Lo3RedenOntbindingHuwelijkOfGpCode("X");

    @Override
    public BrpRedenEindeRelatieCode converteerNaarBrp(final Lo3RedenOntbindingHuwelijkOfGpCode input) {
        return input == null ? null : new BrpRedenEindeRelatieCode(input.getCode());
    }

    @Override
    public Lo3RedenOntbindingHuwelijkOfGpCode converteerNaarLo3(final BrpRedenEindeRelatieCode input) {
        return input == null ? null : new Lo3RedenOntbindingHuwelijkOfGpCode(input.getCode());
    }

    @Override
    public boolean valideerLo3(final Lo3RedenOntbindingHuwelijkOfGpCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpRedenEindeRelatieCode input) {
        return true;
    }
}
