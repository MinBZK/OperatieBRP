/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

public class SoortReisdocumentConversietabel implements Conversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final Lo3SoortNederlandsReisdocument LO3_NIET_VALIDE_UITZONDERING = new Lo3SoortNederlandsReisdocument("QQ");

    @Override
    public BrpSoortNederlandsReisdocumentCode converteerNaarBrp(final Lo3SoortNederlandsReisdocument input) {
        return input == null ? null : new BrpSoortNederlandsReisdocumentCode(input.getWaarde());
    }

    @Override
    public Lo3SoortNederlandsReisdocument converteerNaarLo3(final BrpSoortNederlandsReisdocumentCode input) {
        return input == null ? null : new Lo3SoortNederlandsReisdocument(input.getWaarde());
    }

    @Override
    public boolean valideerLo3(final Lo3SoortNederlandsReisdocument input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpSoortNederlandsReisdocumentCode input) {
        return true;
    }

}
