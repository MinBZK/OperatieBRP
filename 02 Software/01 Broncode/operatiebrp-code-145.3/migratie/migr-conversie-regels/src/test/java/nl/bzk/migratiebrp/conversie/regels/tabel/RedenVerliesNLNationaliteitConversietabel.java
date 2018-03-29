/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

public class RedenVerliesNLNationaliteitConversietabel implements
        Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final Lo3RedenNederlandschapCode LO3_NIET_VALIDE_UITZONDERING = new Lo3RedenNederlandschapCode("999");

    @Override
    public BrpRedenVerliesNederlandschapCode converteerNaarBrp(final Lo3RedenNederlandschapCode input) {
        return input == null ? null : new BrpRedenVerliesNederlandschapCode(input.getWaarde(), input.getOnderzoek());
    }

    @Override
    public Lo3RedenNederlandschapCode converteerNaarLo3(final BrpRedenVerliesNederlandschapCode input) {
        Lo3RedenNederlandschapCode result = null;
        if (input != null) {
            result = new Lo3RedenNederlandschapCode(input.getWaarde(), input.getOnderzoek());
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3RedenNederlandschapCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpRedenVerliesNederlandschapCode input) {
        return true;
    }

}
