/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.RedenVerkrijgingVerliesPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

public class RedenVerkrijgingVerliesNlNationaliteitConversietabel implements
        Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3RedenNederlandschapCode LO3_NIET_VALIDE_UITZONDERING = new Lo3RedenNederlandschapCode(
            "999");

    private static final DecimalFormat REDEN_VERKRIJGING_FORMAT = new DecimalFormat("000");
    private static final DecimalFormat REDEN_VERLIES_FORMAT = new DecimalFormat("000");

    @Override
    public RedenVerkrijgingVerliesPaar converteerNaarBrp(final Lo3RedenNederlandschapCode input) {
        final BrpRedenVerkrijgingNederlandschapCode verkrijging =
                input == null ? null : new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal(input.getCode()));
        final BrpRedenVerliesNederlandschapCode verlies =
                input == null ? null : new BrpRedenVerliesNederlandschapCode(new BigDecimal(input.getCode()));
        if (verkrijging == null && verlies == null) {
            return null;
        }
        final RedenVerkrijgingVerliesPaar paar = new RedenVerkrijgingVerliesPaar(verkrijging, verlies);
        return paar;
    }

    @Override
    public Lo3RedenNederlandschapCode converteerNaarLo3(final RedenVerkrijgingVerliesPaar input) {
        Lo3RedenNederlandschapCode result = null;
        if (input != null) {
            if (input.getVerkrijging() != null) {
                result =
                        new Lo3RedenNederlandschapCode(REDEN_VERKRIJGING_FORMAT.format(input.getVerkrijging()
                                .getNaam()));
            } else if (input.getVerlies() != null) {
                result = new Lo3RedenNederlandschapCode(REDEN_VERLIES_FORMAT.format(input.getVerlies().getNaam()));
            }
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3RedenNederlandschapCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final RedenVerkrijgingVerliesPaar input) {
        return true;
    }

}
