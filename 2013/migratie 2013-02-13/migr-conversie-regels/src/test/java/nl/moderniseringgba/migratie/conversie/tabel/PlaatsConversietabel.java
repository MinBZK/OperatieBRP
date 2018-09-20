/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch.AbstractPlaatsConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;

public class PlaatsConversietabel extends AbstractPlaatsConversietabel implements
        Conversietabel<String, BrpPlaatsCode> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final String LO3_NIET_VALIDE_UITZONDERING = "qqq";

    @Override
    public boolean valideerLo3(final String input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpPlaatsCode input) {
        return true;
    }

}
