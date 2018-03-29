/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractLandConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

public class LandConversietabel extends AbstractLandConversietabel implements Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final Lo3LandCode LO3_NIET_VALIDE_UITZONDERING = new Lo3LandCode("8888");

    @Override
    public boolean valideerLo3(final Lo3LandCode input) {
        return !Lo3Validatie.isElementGevuld(input) || !LO3_NIET_VALIDE_UITZONDERING.equals(new Lo3LandCode(input.getWaarde(), null));
    }

    @Override
    public boolean valideerBrp(final BrpLandOfGebiedCode input) {
        return true;
    }

}
