/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Test;

public class AanduidingUitgeslotenKiesrechtConversietabelTest {
    private final AanduidingUitgeslotenKiesrechtConversietabel subject = new AanduidingUitgeslotenKiesrechtConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
    private static final BrpBoolean EXPECTED_TRUE_ZONDER_ONDERZOEK = new BrpBoolean(true, null);

    @Test
    public void converteerNaarBrp() {
        assertNull(subject.converteerNaarBrp(null));
        assertNull(subject.converteerNaarBrp(new Lo3AanduidingUitgeslotenKiesrecht(null, ONDERZOEK)));
        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(new Lo3AanduidingUitgeslotenKiesrecht("A", ONDERZOEK)));
    }

    @Test
    public void converteerNaarLo3() {
        assertEquals(null, subject.converteerNaarLo3(null));
        assertEquals(null, subject.converteerNaarLo3(new BrpBoolean(null, ONDERZOEK)));
        assertEquals(null, subject.converteerNaarLo3(new BrpBoolean(false, ONDERZOEK)));
        assertEquals(new Lo3AanduidingUitgeslotenKiesrecht("A", null), subject.converteerNaarLo3(EXPECTED_TRUE_ZONDER_ONDERZOEK));
        assertEquals(new Lo3AanduidingUitgeslotenKiesrecht("A", null), subject.converteerNaarLo3(new BrpBoolean(true, ONDERZOEK)));
    }

    @Test
    public void valideerLo3() {
        assertEquals(true, subject.valideerLo3(null));
        assertEquals(true, subject.valideerLo3(new Lo3AanduidingUitgeslotenKiesrecht(null, ONDERZOEK)));
        assertEquals(true, subject.valideerLo3(new Lo3AanduidingUitgeslotenKiesrecht("A", ONDERZOEK)));
        assertEquals(false, subject.valideerLo3(new Lo3AanduidingUitgeslotenKiesrecht("B", ONDERZOEK)));
    }

    @Test
    public void valideerBrp() {
        assertEquals(true, subject.valideerBrp(null));
        assertEquals(true, subject.valideerBrp(new BrpBoolean(null, ONDERZOEK)));
        assertEquals(true, subject.valideerBrp(new BrpBoolean(false, ONDERZOEK)));
        assertEquals(true, subject.valideerBrp(new BrpBoolean(true, ONDERZOEK)));
    }

}
