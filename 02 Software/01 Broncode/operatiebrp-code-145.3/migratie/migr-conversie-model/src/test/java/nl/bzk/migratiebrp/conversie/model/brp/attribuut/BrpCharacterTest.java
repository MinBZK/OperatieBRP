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

public class BrpCharacterTest {

    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);

    @Test
    public void testVerwijderOnderzoekMetWaarde() throws Exception {
        final BrpCharacter titel = new BrpCharacter('B', onderzoek);
        assertNotNull(titel.verwijderOnderzoek());
    }

    @Test
    public void testVerwijderOnderzoekZonderWaarde() throws Exception {
        final BrpCharacter titel = new BrpCharacter(null, onderzoek);
        assertNull(titel.verwijderOnderzoek());
    }

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpCharacter.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        final BrpCharacter titel = BrpCharacter.wrap('B', null);
        assertNull(titel.getOnderzoek());
        assertEquals('B', titel.getWaarde().charValue());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        final BrpCharacter titel = BrpCharacter.wrap(null, onderzoek);
        assertNotNull(titel.getOnderzoek());
        assertNull(titel.getWaarde());
    }

}
