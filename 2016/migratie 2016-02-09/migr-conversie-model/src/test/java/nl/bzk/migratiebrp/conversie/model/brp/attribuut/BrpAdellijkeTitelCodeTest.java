/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Test;

public class BrpAdellijkeTitelCodeTest {
    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), Lo3Datum.NULL_DATUM, null);

    @Test
    public void testGetWaarde() throws Exception {
        BrpAdellijkeTitelCode titel = new BrpAdellijkeTitelCode("B", null);
        titel.setGeslachtsaanduiding(BrpGeslachtsaanduidingCode.MAN);
        assertEquals("B", titel.getWaarde());
        assertEquals("M", titel.getGeslachtsaanduiding().getWaarde());
    }

    @Test
    public void testVerwijderOnderzoekMetWaarde() throws Exception {
        BrpAdellijkeTitelCode titel = new BrpAdellijkeTitelCode("B", onderzoek);
        assertNotNull(titel.verwijderOnderzoek());
    }

    @Test
    public void testVerwijderOnderzoekZonderWaarde() throws Exception {
        BrpAdellijkeTitelCode titel = new BrpAdellijkeTitelCode(null, onderzoek);
        assertNull(titel.verwijderOnderzoek());
    }

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpAdellijkeTitelCode.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        BrpAdellijkeTitelCode titel = BrpAdellijkeTitelCode.wrap("B", null);
        assertNull(titel.getOnderzoek());
        assertEquals("B", titel.getWaarde());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        BrpAdellijkeTitelCode titel = BrpAdellijkeTitelCode.wrap(null, onderzoek);
        assertNotNull(titel.getOnderzoek());
        assertNull(titel.getWaarde());
    }

}
