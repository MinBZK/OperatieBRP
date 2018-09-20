/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class IndicatiePKConversietabelTest {
    private static final Lo3IndicatiePKVolledigGeconverteerdCode INDICATIE_PK_VOLLEDIG_GECONVERTEERD_CODE =
            new Lo3IndicatiePKVolledigGeconverteerdCode("P");
    private final IndicatiePKConversietabel subject = new IndicatiePKConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);
    private static final BrpBoolean EXPECTED_TRUE_ZONDER_ONDERZOEK = new BrpBoolean(true, null);
    private static final BrpBoolean EXPECTED_FALSE_ZONDER_ONDERZOEK = new BrpBoolean(false, null);


    @Test
    public void converteerNaarBrp() {
        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK,subject.converteerNaarBrp(null));
        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(new Lo3IndicatiePKVolledigGeconverteerdCode(null, ONDERZOEK)));

        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(INDICATIE_PK_VOLLEDIG_GECONVERTEERD_CODE));
        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(new Lo3IndicatiePKVolledigGeconverteerdCode("P", ONDERZOEK)));
        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(new Lo3IndicatiePKVolledigGeconverteerdCode("X")));
        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(new Lo3IndicatiePKVolledigGeconverteerdCode("X", ONDERZOEK)));
    }

    @Test
    public void converteerNaarLo3() {
        assertNull(subject.converteerNaarLo3(null));
        assertNull(subject.converteerNaarLo3(new BrpBoolean(null, ONDERZOEK)));
        assertNull(subject.converteerNaarLo3(new BrpBoolean(false)));
        assertNull(subject.converteerNaarLo3(new BrpBoolean(false, ONDERZOEK)));
        assertEquals(INDICATIE_PK_VOLLEDIG_GECONVERTEERD_CODE, subject.converteerNaarLo3(new BrpBoolean(true, null)));
        assertEquals(INDICATIE_PK_VOLLEDIG_GECONVERTEERD_CODE, subject.converteerNaarLo3(new BrpBoolean(true, ONDERZOEK)));
    }

    @Test
    public void valideerLo3() {
        assertTrue(subject.valideerLo3(null));
        assertTrue(subject.valideerLo3(new Lo3IndicatiePKVolledigGeconverteerdCode(null, ONDERZOEK)));
        assertTrue(subject.valideerLo3(new Lo3IndicatiePKVolledigGeconverteerdCode("P", ONDERZOEK)));
        assertFalse(subject.valideerLo3(new Lo3IndicatiePKVolledigGeconverteerdCode("X", ONDERZOEK)));
    }

    @Test
    public void valideerBrp() {
        assertTrue(subject.valideerBrp(null));
        assertTrue(subject.valideerBrp(new BrpBoolean(null, ONDERZOEK)));
        assertTrue(subject.valideerBrp(new BrpBoolean(false, ONDERZOEK)));
        assertTrue(subject.valideerBrp(new BrpBoolean(true, ONDERZOEK)));
    }

}
