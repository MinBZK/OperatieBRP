/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;

import org.junit.Test;

/**
 * Test cases voor SoortRegisterSoortDocumentConversietabel.
 */
public class SoortRegisterSoortDocumentConversietabelTest {

    private final SoortRegisterSoortDocumentConversietabel tabel =
            new SoortRegisterSoortDocumentConversietabel(
                    new SoortDocument(SoortRegisterSoortDocumentConversietabel.CONVERSIE_SOORT_DOCUMENT, "Test omschrijving"));

    private static final Character LO3 = 'S';
    private static final BrpSoortDocumentCode BRP = new BrpSoortDocumentCode(SoortRegisterSoortDocumentConversietabel.CONVERSIE_SOORT_DOCUMENT);

    @Test
    public void testConverteerNaarBrp() {
        assertEquals(BRP, tabel.converteerNaarBrp(LO3));
    }

    @Test
    public void testValideerLo3() {
        assertTrue(tabel.valideerLo3(null));
        assertTrue(tabel.valideerLo3(LO3));
    }

    @Test
    public void testConverteerNaarLo3() {
        assertNull(tabel.converteerNaarLo3(BRP));
    }

    @Test
    public void testValideerBrp() {
        assertTrue(tabel.valideerBrp(null));
        assertTrue(tabel.valideerBrp(BRP));
    }
}
