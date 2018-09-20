/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;

public class ReisdocumentAutoriteitVanAfgifteConversietabel implements
        Conversietabel<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3AutoriteitVanAfgifteNederlandsReisdocument LO3_NIET_VALIDE_UITZONDERING =
            new Lo3AutoriteitVanAfgifteNederlandsReisdocument("QQ999");

    @Override
    public BrpReisdocumentAutoriteitVanAfgifte converteerNaarBrp(
            final Lo3AutoriteitVanAfgifteNederlandsReisdocument input) {
        return input == null ? null : new BrpReisdocumentAutoriteitVanAfgifte(input.getAutoriteit());
    }

    @Override
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument converteerNaarLo3(
            final BrpReisdocumentAutoriteitVanAfgifte input) {
        return input == null ? null : new Lo3AutoriteitVanAfgifteNederlandsReisdocument(input.getCode());
    }

    @Override
    public boolean valideerLo3(final Lo3AutoriteitVanAfgifteNederlandsReisdocument input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpReisdocumentAutoriteitVanAfgifte input) {
        return true;
    }

}
