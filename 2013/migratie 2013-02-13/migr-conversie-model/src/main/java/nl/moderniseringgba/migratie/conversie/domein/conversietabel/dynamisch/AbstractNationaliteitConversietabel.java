/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch;

import java.text.DecimalFormat;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;

public abstract class AbstractNationaliteitConversietabel implements
        Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> {

    private static final DecimalFormat NATIONALITEIT_CODE_FORMAT = new DecimalFormat("0000");

    @Override
    public final BrpNationaliteitCode converteerNaarBrp(final Lo3NationaliteitCode input) {
        return input == null ? null : new BrpNationaliteitCode(Integer.valueOf(input.getCode()));
    }

    @Override
    public final Lo3NationaliteitCode converteerNaarLo3(final BrpNationaliteitCode input) {
        return input == null ? null : new Lo3NationaliteitCode(NATIONALITEIT_CODE_FORMAT.format(input.getCode()));
    }
}
