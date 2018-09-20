/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractGemeenteConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;

public class GemeenteConversietabel extends AbstractGemeenteConversietabel implements Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3GemeenteCode LO3_NIET_VALIDE_UITZONDERING = new Lo3GemeenteCode("8888");
    /**
     * Test waarde voor een niet geldige RNI waarde (niet wijzigen, tests zijn hiervan afhankelijk). Waarde wordt wel
     * geaccepteerd door de partij conversietabel.
     */
    public static final Lo3GemeenteCode LO3_RNI_NIET_VALIDE_UITZONDERING = new Lo3GemeenteCode("8889");

    @Override
    public boolean valideerLo3(final Lo3GemeenteCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equalsWaarde(input)
               && !LO3_RNI_NIET_VALIDE_UITZONDERING.equalsWaarde(input);
    }

    @Override
    public boolean valideerBrp(final BrpGemeenteCode input) {
        return true;
    }

}
