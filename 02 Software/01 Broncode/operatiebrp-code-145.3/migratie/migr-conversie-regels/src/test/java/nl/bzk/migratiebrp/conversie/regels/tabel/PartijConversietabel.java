/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

public class PartijConversietabel implements Conversietabel<Lo3GemeenteCode, BrpPartijCode> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final Lo3GemeenteCode LO3_NIET_VALIDE_UITZONDERING = new Lo3GemeenteCode("8888");

    @Override
    public final BrpPartijCode converteerNaarBrp(final Lo3GemeenteCode input) {
        final BrpPartijCode resultaat;

        if (!Lo3Validatie.isElementGevuld(input)) {
            resultaat = null;
        } else if (Lo3GemeenteCode.RNI.equals(input)) {
            resultaat = new BrpPartijCode(BrpPartijCode.MINISTER_CODE, input.getOnderzoek());
        } else {
            resultaat = new BrpPartijCode(input.getWaarde() + "01", input.getOnderzoek());
        }

        return resultaat;
    }

    @Override
    public final Lo3GemeenteCode converteerNaarLo3(final BrpPartijCode input) {
        final Lo3GemeenteCode resultaat;

        if (input == null) {
            resultaat = null;
        } else if (BrpPartijCode.MINISTER.equals(input)) {
            resultaat = Lo3GemeenteCode.RNI;
        } else {
            resultaat = new Lo3GemeenteCode(input.getWaarde().substring(0, 4));
        }

        return resultaat;
    }

    @Override
    public boolean valideerLo3(final Lo3GemeenteCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equalsWaarde(input);
    }

    @Override
    public boolean valideerBrp(final BrpPartijCode input) {
        return true;
    }

}
