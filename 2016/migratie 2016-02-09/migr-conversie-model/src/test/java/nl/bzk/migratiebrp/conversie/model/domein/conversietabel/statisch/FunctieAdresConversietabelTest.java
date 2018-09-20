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

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Test;

public class FunctieAdresConversietabelTest {
    private final FunctieAdresConversietabel tabel = new FunctieAdresConversietabel();
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(0), Lo3Datum.NULL_DATUM, null);

    @Test public void testLo3NaarBrp() {
        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(new BrpSoortAdresCode(null, ONDERZOEK), tabel.converteerNaarBrp(new Lo3FunctieAdres(null, ONDERZOEK)));
        assertEquals(BrpSoortAdresCode.B, tabel.converteerNaarBrp(Lo3FunctieAdresEnum.BRIEFADRES.asElement()));
        assertEquals(BrpSoortAdresCode.W, tabel.converteerNaarBrp(Lo3FunctieAdresEnum.WOONADRES.asElement()));

        assertEquals(new BrpSoortAdresCode("B", ONDERZOEK),
                tabel.converteerNaarBrp(new Lo3FunctieAdres(Lo3FunctieAdresEnum.BRIEFADRES.getCode(), ONDERZOEK)));
        assertEquals(new BrpSoortAdresCode("W", ONDERZOEK),
                tabel.converteerNaarBrp(new Lo3FunctieAdres(Lo3FunctieAdresEnum.WOONADRES.getCode(), ONDERZOEK)));
    }

    @Test public void testBrpNaarLo3() {
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(Lo3FunctieAdresEnum.BRIEFADRES.asElement(), tabel.converteerNaarLo3(BrpSoortAdresCode.B));
        assertEquals(Lo3FunctieAdresEnum.WOONADRES.asElement(), tabel.converteerNaarLo3(BrpSoortAdresCode.W));

        assertEquals(new Lo3FunctieAdres(Lo3FunctieAdresEnum.BRIEFADRES.getCode(), ONDERZOEK),
                tabel.converteerNaarLo3(new BrpSoortAdresCode("B", ONDERZOEK)));
        assertEquals(new Lo3FunctieAdres(Lo3FunctieAdresEnum.WOONADRES.getCode(), ONDERZOEK),
                tabel.converteerNaarLo3(new BrpSoortAdresCode("W", ONDERZOEK)));
    }

    @Test public void valideerLo3() {
        assertTrue(tabel.valideerLo3(null));
        assertTrue(tabel.valideerLo3(new Lo3FunctieAdres(null, ONDERZOEK)));
        assertTrue(tabel.valideerLo3(Lo3FunctieAdresEnum.WOONADRES.asElement()));
        assertTrue(tabel.valideerLo3(new Lo3FunctieAdres(Lo3FunctieAdresEnum.WOONADRES.getCode(), ONDERZOEK)));
        assertTrue(tabel.valideerLo3(Lo3FunctieAdresEnum.BRIEFADRES.asElement()));
        assertTrue(tabel.valideerLo3(new Lo3FunctieAdres(Lo3FunctieAdresEnum.BRIEFADRES.getCode(), ONDERZOEK)));
        assertFalse(tabel.valideerLo3(new Lo3FunctieAdres("NEPADRES")));
    }

    @Test public void valideerBrp() {
        assertTrue(tabel.valideerBrp(null));
        assertTrue(tabel.valideerBrp(BrpSoortAdresCode.B));
        assertTrue(tabel.valideerBrp(new BrpSoortAdresCode("FAKE")));
    }

}
