/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Test;

public class IndicatieDocumentConversietabelTest {
    private final IndicatieDocumentConversietabel subject = new IndicatieDocumentConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    private static final BrpBoolean BRPBOOLEAN_TRUE_ZONDER_ONDERZOEK = new BrpBoolean(true, null);
    private static final BrpBoolean BRPBOOLEAN_FALSE_ZONDER_ONDERZOEK = new BrpBoolean(false, null);
    private static final BrpBoolean BRPBOOLEAN_TRUE_MET_ONDERZOEK = new BrpBoolean(true, ONDERZOEK);
    private static final BrpBoolean BRPBOOLEAN_FALSE_MET_ONDERZOEK = new BrpBoolean(false, ONDERZOEK);
    private static final Lo3IndicatieDocument LO3_INDICATIE_DOCUMENT_ZONDER_ONDERZOEK = new Lo3IndicatieDocument("1", null);
    private static final Lo3IndicatieDocument LO3_INDICATIE_DOCUMENT_MET_ONDERZOEK = new Lo3IndicatieDocument("1", ONDERZOEK);
    private static final Lo3IndicatieDocument LO3_NULL_MET_ONDERZOEK = new Lo3IndicatieDocument(null, ONDERZOEK);

    @Test
    public void converteerNaarBrp() {
        assertEquals(BRPBOOLEAN_FALSE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(null));
        assertEquals(BRPBOOLEAN_FALSE_MET_ONDERZOEK, subject.converteerNaarBrp(LO3_NULL_MET_ONDERZOEK));
        assertEquals(BRPBOOLEAN_TRUE_ZONDER_ONDERZOEK, subject.converteerNaarBrp(LO3_INDICATIE_DOCUMENT_ZONDER_ONDERZOEK));
        assertEquals(BRPBOOLEAN_TRUE_MET_ONDERZOEK, subject.converteerNaarBrp(LO3_INDICATIE_DOCUMENT_MET_ONDERZOEK));
        assertEquals(BRPBOOLEAN_FALSE_MET_ONDERZOEK, subject.converteerNaarBrp(new Lo3IndicatieDocument("2", ONDERZOEK)));
    }

    @Test
    public void converteerNaarLo3() {
        assertNull(subject.converteerNaarLo3(null));
        assertEquals(LO3_NULL_MET_ONDERZOEK, subject.converteerNaarLo3(new BrpBoolean(null, ONDERZOEK)));
        assertEquals(LO3_NULL_MET_ONDERZOEK, subject.converteerNaarLo3(BRPBOOLEAN_FALSE_MET_ONDERZOEK));
        assertNull(subject.converteerNaarLo3(BRPBOOLEAN_FALSE_ZONDER_ONDERZOEK));
        assertEquals(LO3_INDICATIE_DOCUMENT_MET_ONDERZOEK, subject.converteerNaarLo3(BRPBOOLEAN_TRUE_MET_ONDERZOEK));
        assertEquals(LO3_INDICATIE_DOCUMENT_ZONDER_ONDERZOEK, subject.converteerNaarLo3(BRPBOOLEAN_TRUE_ZONDER_ONDERZOEK));
    }

    @Test
    public void valideerLo3() {
        assertTrue(subject.valideerLo3(null));
        assertTrue(subject.valideerLo3(LO3_NULL_MET_ONDERZOEK));
        assertTrue(subject.valideerLo3(LO3_INDICATIE_DOCUMENT_MET_ONDERZOEK));
        assertTrue(subject.valideerLo3(LO3_INDICATIE_DOCUMENT_ZONDER_ONDERZOEK));
        assertFalse(subject.valideerLo3(new Lo3IndicatieDocument("2", null)));
        assertFalse(subject.valideerLo3(new Lo3IndicatieDocument("2", ONDERZOEK)));
    }

    @Test
    public void valideerBrp() {
        assertTrue(subject.valideerBrp(null));
        assertTrue(subject.valideerBrp(new BrpBoolean(null, ONDERZOEK)));
        assertTrue(subject.valideerBrp(BRPBOOLEAN_FALSE_MET_ONDERZOEK));
        assertTrue(subject.valideerBrp(BRPBOOLEAN_FALSE_ZONDER_ONDERZOEK));
        assertTrue(subject.valideerBrp(BRPBOOLEAN_TRUE_MET_ONDERZOEK));
        assertTrue(subject.valideerBrp(BRPBOOLEAN_TRUE_ZONDER_ONDERZOEK));
    }

}
