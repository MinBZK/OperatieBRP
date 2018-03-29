/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.SoortVerbintenisConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;

import org.junit.Test;

public class SoortVerbintenisConversietabelTest {

    @Test
    public void testNaarBrp() {
        final SoortVerbintenisConversietabel tabel = new SoortVerbintenisConversietabel();
        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                tabel.converteerNaarBrp(Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.asElement()));
        assertEquals(BrpSoortRelatieCode.HUWELIJK, tabel.converteerNaarBrp(Lo3SoortVerbintenisEnum.HUWELIJK.asElement()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarBrpIAE() {
        new SoortVerbintenisConversietabel().converteerNaarBrp(Lo3SoortVerbintenisEnum.ONBEKEND.asElement());
    }

    @Test
    public void testNaarLo3() {
        final SoortVerbintenisConversietabel tabel = new SoortVerbintenisConversietabel();
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(
                Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.asElement(),
                tabel.converteerNaarLo3(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP));
        assertEquals(Lo3SoortVerbintenisEnum.HUWELIJK.asElement(), tabel.converteerNaarLo3(BrpSoortRelatieCode.HUWELIJK));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE() {
        new SoortVerbintenisConversietabel().converteerNaarLo3(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING);
    }

}
