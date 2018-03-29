/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP code. Er wordt geen onderzoek
 * geconverteerd aangezien cat07 geen onderzoek kan bevatten in LO3.
 */
public final class IndicatiePKConversietabel implements Conversietabel<Lo3IndicatiePKVolledigGeconverteerdCode, BrpBoolean> {

    @Override
    public BrpBoolean converteerNaarBrp(final Lo3IndicatiePKVolledigGeconverteerdCode input) {
        if (!Lo3Validatie.isElementGevuld(input)) {
            return new BrpBoolean(false);
        }

        return new BrpBoolean(Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.getCode().equals(input.getWaarde()));

    }

    @Override
    public Lo3IndicatiePKVolledigGeconverteerdCode converteerNaarLo3(final BrpBoolean input) {
        if (!BrpValidatie.isAttribuutGevuld(input)) {
            return null;
        }

        return input.getWaarde() ? new Lo3IndicatiePKVolledigGeconverteerdCode(
                Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.getCode()) : null;
    }

    @Override
    public boolean valideerLo3(final Lo3IndicatiePKVolledigGeconverteerdCode input) {
        return !Lo3Validatie.isElementGevuld(input) || Lo3IndicatiePKVolledigGeconverteerdCodeEnum.getByCode(input.getWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpBoolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
