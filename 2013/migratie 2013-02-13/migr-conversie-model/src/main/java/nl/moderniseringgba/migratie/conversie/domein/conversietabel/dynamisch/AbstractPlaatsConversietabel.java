/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;

public abstract class AbstractPlaatsConversietabel implements Conversietabel<String, BrpPlaatsCode> {

    @Override
    public final String converteerNaarLo3(final BrpPlaatsCode input) {
        return input == null ? null : input.getNaam();
    }

    @Override
    public final BrpPlaatsCode converteerNaarBrp(final String input) {
        return input == null ? null : new BrpPlaatsCode(input);
    }
}
