/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch.AbstractLandConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

public class LandConversietabel extends AbstractLandConversietabel implements
        Conversietabel<Lo3LandCode, BrpLandCode> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final Lo3LandCode LO3_NIET_VALIDE_UITZONDERING = new Lo3LandCode("8888");

    @Override
    public boolean valideerLo3(final Lo3LandCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpLandCode input) {
        return true;
    }

}
