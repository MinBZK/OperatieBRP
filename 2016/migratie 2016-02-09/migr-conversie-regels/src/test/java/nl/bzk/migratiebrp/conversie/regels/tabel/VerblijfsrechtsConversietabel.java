/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;

public class VerblijfsrechtsConversietabel implements Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3AanduidingVerblijfstitelCode LO3_NIET_VALIDE_UITZONDERING = new Lo3AanduidingVerblijfstitelCode("99");

    @Override
    public BrpVerblijfsrechtCode converteerNaarBrp(final Lo3AanduidingVerblijfstitelCode input) {
        return input == null ? null : new BrpVerblijfsrechtCode(Short.valueOf(input.getWaarde()), input.getOnderzoek());
    }

    @Override
    public Lo3AanduidingVerblijfstitelCode converteerNaarLo3(final BrpVerblijfsrechtCode input) {
        return input == null ? null : new Lo3AanduidingVerblijfstitelCode(Short.toString(input.getWaarde()), input.getOnderzoek());
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingVerblijfstitelCode input) {
        return input == null || !LO3_NIET_VALIDE_UITZONDERING.getWaarde().equals(input.getWaarde());
    }

    @Override
    public boolean valideerBrp(final BrpVerblijfsrechtCode input) {
        return true;
    }

}
