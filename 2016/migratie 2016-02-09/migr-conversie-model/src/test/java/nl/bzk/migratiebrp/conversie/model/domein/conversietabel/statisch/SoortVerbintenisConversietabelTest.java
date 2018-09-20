/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SoortVerbintenisConversietabelTest {
    private final SoortVerbintenisConversietabel subject = new SoortVerbintenisConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);
    private static final BrpSoortRelatieCode LEEG_MET_ONDERZOEK = new BrpSoortRelatieCode(null, ONDERZOEK);
    private static final BrpSoortRelatieCode HUWELIJK_MET_ONDERZOEK = new BrpSoortRelatieCode(BrpSoortRelatieCode.HUWELIJK.getWaarde(), ONDERZOEK);
    private static final BrpSoortRelatieCode GEREGISTREERD_PARTNERSCHAP_MET_ONDERZOEK = new BrpSoortRelatieCode(
        BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.getWaarde(),
        ONDERZOEK);

    @Test
    public void converteerNaarBrp() {
        assertEquals(null, subject.converteerNaarBrp(null));
        assertEquals(LEEG_MET_ONDERZOEK, subject.converteerNaarBrp(new Lo3SoortVerbintenis(null, ONDERZOEK)));

        assertEquals(BrpSoortRelatieCode.HUWELIJK, subject.converteerNaarBrp(new Lo3SoortVerbintenis("H", null)));
        assertEquals(HUWELIJK_MET_ONDERZOEK, subject.converteerNaarBrp(new Lo3SoortVerbintenis("H", ONDERZOEK)));

        assertEquals(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, subject.converteerNaarBrp(new Lo3SoortVerbintenis("P", null)));
        assertEquals(GEREGISTREERD_PARTNERSCHAP_MET_ONDERZOEK, subject.converteerNaarBrp(new Lo3SoortVerbintenis("P", ONDERZOEK)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void converteerNaarBrpIllegal() {
        subject.converteerNaarBrp(new Lo3SoortVerbintenis(".", ONDERZOEK));
    }

    @Test
    public void converteerNaarLo3() {
        assertEquals(null, subject.converteerNaarLo3(null));
        assertEquals(null, subject.converteerNaarLo3(new BrpSoortRelatieCode(null, ONDERZOEK)).getWaarde());
        assertEquals("H", subject.converteerNaarLo3(new BrpSoortRelatieCode("H", ONDERZOEK)).getWaarde());
        assertEquals(ONDERZOEK, subject.converteerNaarLo3(new BrpSoortRelatieCode("H", ONDERZOEK)).getOnderzoek());
        assertEquals("P", subject.converteerNaarLo3(new BrpSoortRelatieCode("G", null)).getWaarde());
        assertEquals(null, subject.converteerNaarLo3(new BrpSoortRelatieCode("G", null)).getOnderzoek());
    }

    @Test(expected = IllegalArgumentException.class)
    public void converteerNaarLo3Illegal() {
        subject.converteerNaarLo3(new BrpSoortRelatieCode("F", ONDERZOEK));
    }

    @Test
    public void valideerLo3() {
        assertTrue(subject.valideerLo3(null));
        assertTrue(subject.valideerLo3(new Lo3SoortVerbintenis(null, ONDERZOEK)));
        assertTrue(subject.valideerLo3(new Lo3SoortVerbintenis("H", ONDERZOEK)));
        assertTrue(subject.valideerLo3(new Lo3SoortVerbintenis("P", null)));
        assertTrue(subject.valideerLo3(new Lo3SoortVerbintenis(".", null)));
        assertFalse(subject.valideerLo3(new Lo3SoortVerbintenis("X", null)));
    }



    @Test
    public void valideerBrp() {
        assertTrue(subject.valideerBrp(null));
        assertTrue(subject.valideerBrp(new BrpSoortRelatieCode(null, ONDERZOEK)));
        assertTrue(subject.valideerBrp(new BrpSoortRelatieCode("H", ONDERZOEK)));
        assertTrue(subject.valideerBrp(new BrpSoortRelatieCode("G", null)));
        assertTrue(subject.valideerBrp(new BrpSoortRelatieCode("F", null)));
        assertTrue(subject.valideerBrp(new BrpSoortRelatieCode("X", null)));

    }

}
