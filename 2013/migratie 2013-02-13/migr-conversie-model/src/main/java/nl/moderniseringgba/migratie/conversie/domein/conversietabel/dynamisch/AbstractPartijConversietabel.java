/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch;

import java.text.DecimalFormat;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;

public abstract class AbstractPartijConversietabel implements Conversietabel<Lo3GemeenteCode, BrpPartijCode> {
    private static final DecimalFormat GEMEENTE_CODE_FORMAT = new DecimalFormat("0000");

    @Override
    public final BrpPartijCode converteerNaarBrp(final Lo3GemeenteCode input) {
        return input == null ? null : new BrpPartijCode(null, Integer.valueOf(input.getCode()));
    }

    @Override
    public final Lo3GemeenteCode converteerNaarLo3(final BrpPartijCode input) {
        return input == null ? null : new Lo3GemeenteCode(GEMEENTE_CODE_FORMAT.format(input.getGemeenteCode()));
    }
}
