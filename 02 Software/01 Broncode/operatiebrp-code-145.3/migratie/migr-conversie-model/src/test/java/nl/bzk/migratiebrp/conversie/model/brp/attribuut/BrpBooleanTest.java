/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Test;

public class BrpBooleanTest {
    public static BrpBoolean BRP_TRUE = new BrpBoolean(Boolean.TRUE);
    public static BrpBoolean BRP_FALSE = new BrpBoolean(Boolean.FALSE);

    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpBoolean.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        final BrpBoolean b = BrpBoolean.wrap(Boolean.FALSE, null);
        assertNull(b.getOnderzoek());
        assertFalse(b.getWaarde().booleanValue());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        final BrpBoolean b = BrpBoolean.wrap(null, onderzoek);
        assertNotNull(b.getOnderzoek());
        assertNull(b.getWaarde());
    }

    @Test
    public void testVerwijderOnderzoekMetWaarde() throws Exception {
        final BrpBoolean b = new BrpBoolean(Boolean.FALSE, onderzoek);
        assertNotNull(b.verwijderOnderzoek());
    }

    @Test
    public void testVerwijderOnderzoekZonderWaarde() throws Exception {
        final BrpBoolean b = new BrpBoolean(null, onderzoek);
        assertNull(b.verwijderOnderzoek());
    }

}
