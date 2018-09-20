/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch;

import java.text.DecimalFormat;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

public abstract class AbstractLandConversietabel implements Conversietabel<Lo3LandCode, BrpLandCode> {

    private static final DecimalFormat LAND_CODE_FORMAT = new DecimalFormat("0000");

    @Override
    public final BrpLandCode converteerNaarBrp(final Lo3LandCode input) {
        return input == null ? null : new BrpLandCode(Integer.valueOf(input.getCode()));
    }

    @Override
    public final Lo3LandCode converteerNaarLo3(final BrpLandCode input) {
        return input == null ? null : new Lo3LandCode(LAND_CODE_FORMAT.format(input.getCode()));
    }
}
