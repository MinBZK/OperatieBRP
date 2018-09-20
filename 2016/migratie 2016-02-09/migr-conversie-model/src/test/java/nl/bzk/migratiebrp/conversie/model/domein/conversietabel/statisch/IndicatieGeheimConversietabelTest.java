/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class IndicatieGeheimConversietabelTest {
    private static final Lo3IndicatieGeheimCode INDICATIE_GEHEIM_CODE_7 = new Lo3IndicatieGeheimCode(7);
    private static final Lo3IndicatieGeheimCode INDICATIE_GEHEIM_CODE_0 = new Lo3IndicatieGeheimCode(0);
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);
    private static final BrpBoolean EXPECTED_TRUE_ZONDER_ONDERZOEK = new BrpBoolean(true, null);
    private static final BrpBoolean EXPECTED_FALSE_ZONDER_ONDERZOEK = new BrpBoolean(false, null);
    private final IndicatieGeheimConversietabel subject = new IndicatieGeheimConversietabel();

    @Test
    public void converteerNaarBrp() {
        assertNull(subject.converteerNaarBrp(null));
        assertNull(subject.converteerNaarBrp(new Lo3IndicatieGeheimCode(null, ONDERZOEK)));

        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(INDICATIE_GEHEIM_CODE_7));
        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(new Lo3IndicatieGeheimCode("7", ONDERZOEK)));

        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(INDICATIE_GEHEIM_CODE_0));
        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(new Lo3IndicatieGeheimCode("0", ONDERZOEK)));
    }

    @Test
    public void converteerNaarLo3() {
        assertEquals(INDICATIE_GEHEIM_CODE_0, subject.converteerNaarLo3(null));
        assertEquals(INDICATIE_GEHEIM_CODE_0, subject.converteerNaarLo3(new BrpBoolean(null, ONDERZOEK)));

        assertEquals(INDICATIE_GEHEIM_CODE_0, subject.converteerNaarLo3(new BrpBoolean(false, null)));
        assertEquals(INDICATIE_GEHEIM_CODE_0, subject.converteerNaarLo3(new BrpBoolean(false, ONDERZOEK)));

        assertEquals(INDICATIE_GEHEIM_CODE_7, subject.converteerNaarLo3(new BrpBoolean(true, null)));
        assertEquals(INDICATIE_GEHEIM_CODE_7, subject.converteerNaarLo3(new BrpBoolean(true, ONDERZOEK)));
    }

    @Test
    public void valideerLo3() {
        assertTrue(subject.valideerLo3(null));
        assertTrue(subject.valideerLo3(new Lo3IndicatieGeheimCode(null, ONDERZOEK)));

        assertTrue(subject.valideerLo3(new Lo3IndicatieGeheimCode(1)));
        assertTrue(subject.valideerLo3(new Lo3IndicatieGeheimCode("1", ONDERZOEK)));

        assertFalse(subject.valideerLo3(new Lo3IndicatieGeheimCode(8)));
        assertFalse(subject.valideerLo3(new Lo3IndicatieGeheimCode("8", ONDERZOEK)));
    }

    @Test
    public void valideerBrp() {
        assertTrue(subject.valideerBrp(null));
        assertTrue(subject.valideerBrp(new BrpBoolean(null, ONDERZOEK)));

        assertTrue(subject.valideerBrp(new BrpBoolean(false, null)));
        assertTrue(subject.valideerBrp(new BrpBoolean(false, ONDERZOEK)));

        assertTrue(subject.valideerBrp(new BrpBoolean(true, null)));
        assertTrue(subject.valideerBrp(new BrpBoolean(true, ONDERZOEK)));
    }
}
