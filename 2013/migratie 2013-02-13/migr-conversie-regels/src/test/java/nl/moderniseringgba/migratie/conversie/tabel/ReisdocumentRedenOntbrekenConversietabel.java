/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;

public class ReisdocumentRedenOntbrekenConversietabel implements
        Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken> {
    @Override
    public BrpReisdocumentRedenOntbreken converteerNaarBrp(
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument input) {
        return input == null ? null : new BrpReisdocumentRedenOntbreken(input.getCode());
    }

    @Override
    public Lo3AanduidingInhoudingVermissingNederlandsReisdocument converteerNaarLo3(
            final BrpReisdocumentRedenOntbreken input) {
        return input == null ? null : new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(input.getCode());
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingInhoudingVermissingNederlandsReisdocument input) {
        return true;
    }

    @Override
    public boolean valideerBrp(final BrpReisdocumentRedenOntbreken input) {
        return true;
    }

}
