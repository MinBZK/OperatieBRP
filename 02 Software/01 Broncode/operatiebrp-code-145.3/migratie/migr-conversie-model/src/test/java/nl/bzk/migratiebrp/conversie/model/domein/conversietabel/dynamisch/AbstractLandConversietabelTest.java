/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Test;

public class AbstractLandConversietabelTest {
    private final AbstractLandConversietabel subject = new AbstractLandConversietabel() {

        @Override
        public boolean valideerLo3(final Lo3LandCode input) {
            return false;
        }

        @Override
        public boolean valideerBrp(final BrpLandOfGebiedCode input) {
            return false;
        }
    };

    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
    private static final Lo3LandCode LO3 = new Lo3LandCode("0012", ONDERZOEK);
    private static final Lo3LandCode LO3_ALLEEN_ONDERZOEK = new Lo3LandCode(null, ONDERZOEK);
    private static final BrpLandOfGebiedCode BRP = new BrpLandOfGebiedCode("0024", ONDERZOEK);
    private static final BrpLandOfGebiedCode BRP_ALLEEN_ONDERZOEK = new BrpLandOfGebiedCode(null, ONDERZOEK);

    @Test
    public void converteerNaarBrp() {
        assertEquals(null, subject.converteerNaarBrp(null));
        assertEquals("0012", subject.converteerNaarBrp(LO3).getWaarde());
        assertEquals(ONDERZOEK, subject.converteerNaarBrp(LO3).getOnderzoek());
        assertEquals(null, subject.converteerNaarBrp(LO3_ALLEEN_ONDERZOEK).getWaarde());
        assertEquals(ONDERZOEK, subject.converteerNaarBrp(LO3_ALLEEN_ONDERZOEK).getOnderzoek());
    }

    @Test
    public void converteerNaarLo3() {
        assertEquals(null, subject.converteerNaarLo3(null));
        assertEquals("0024", subject.converteerNaarLo3(BRP).getWaarde());
        assertEquals(ONDERZOEK, subject.converteerNaarLo3(BRP).getOnderzoek());
        assertEquals(null, subject.converteerNaarLo3(BRP_ALLEEN_ONDERZOEK).getWaarde());
        assertEquals(ONDERZOEK, subject.converteerNaarLo3(BRP_ALLEEN_ONDERZOEK).getOnderzoek());
    }
}
