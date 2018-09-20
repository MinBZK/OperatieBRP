/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoudTest;

import org.junit.Test;

public class BrpGroepTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorInhoudNull() {
        new BrpGroep<>(null, null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorHistoryNull() {
        new BrpGroep<>(BrpDocumentInhoudTest.createInhoud(), null, null, null, null);
    }

    @Test
    public void testEquals() {
        BrpGroep groep1 = new BrpGroep<>(BrpDocumentInhoudTest.createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        assertTrue(groep1.equals(returnZelfde(groep1)));
        assertFalse(groep1.equals(returnZelfde(groep1).getHistorie()));
    }

    private BrpGroep returnZelfde(BrpGroep groep) {
        return groep;
    }
}
