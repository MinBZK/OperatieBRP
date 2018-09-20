/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;

public abstract class AbstractGemeenteConversietabel implements Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> {

    private static final DecimalFormat GEMEENTE_CODE_FORMAT = new DecimalFormat("0000");

    @Override
    public final BrpGemeenteCode converteerNaarBrp(final Lo3GemeenteCode input) {
        return input == null ? null : new BrpGemeenteCode(new BigDecimal(input.getCode()));
    }

    @Override
    public final Lo3GemeenteCode converteerNaarLo3(final BrpGemeenteCode input) {
        return input == null ? null : new Lo3GemeenteCode(GEMEENTE_CODE_FORMAT.format(input.getCode()));
    }
}
