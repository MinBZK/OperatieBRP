/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Test;

public class AbstractGemeenteConversietabelTest {
    private final AbstractGemeenteConversietabel subject = new AbstractGemeenteConversietabel() {

        @Override
        public boolean valideerLo3(final Lo3GemeenteCode input) {
            return false;
        }

        @Override
        public boolean valideerBrp(final BrpGemeenteCode input) {
            return false;
        }
    };

    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
    private static final Lo3GemeenteCode LO3 = new Lo3GemeenteCode("0012", ONDERZOEK);
    private static final Lo3GemeenteCode LO3_ALLEEN_ONDERZOEK = new Lo3GemeenteCode(null, ONDERZOEK);
    private static final BrpGemeenteCode BRP = new BrpGemeenteCode("0024", ONDERZOEK);
    private static final BrpGemeenteCode BRP_ALLEEN_ONDERZOEK = new BrpGemeenteCode(null, ONDERZOEK);

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
