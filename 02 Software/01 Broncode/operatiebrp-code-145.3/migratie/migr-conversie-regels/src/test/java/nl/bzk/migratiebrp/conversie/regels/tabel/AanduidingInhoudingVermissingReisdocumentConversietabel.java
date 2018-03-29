/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

public class AanduidingInhoudingVermissingReisdocumentConversietabel implements
        Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode> {
    @Override
    public BrpAanduidingInhoudingOfVermissingReisdocumentCode converteerNaarBrp(final Lo3AanduidingInhoudingVermissingNederlandsReisdocument input) {
        return !Lo3Validatie.isElementGevuld(input) ? null : new BrpAanduidingInhoudingOfVermissingReisdocumentCode(input.getWaarde().charAt(0));
    }

    @Override
    public Lo3AanduidingInhoudingVermissingNederlandsReisdocument converteerNaarLo3(final BrpAanduidingInhoudingOfVermissingReisdocumentCode input) {
        return input == null ? null : new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(Character.toString(input.getWaarde()));
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingInhoudingVermissingNederlandsReisdocument input) {
        return true;
    }

    @Override
    public boolean valideerBrp(final BrpAanduidingInhoudingOfVermissingReisdocumentCode input) {
        return true;
    }

}
