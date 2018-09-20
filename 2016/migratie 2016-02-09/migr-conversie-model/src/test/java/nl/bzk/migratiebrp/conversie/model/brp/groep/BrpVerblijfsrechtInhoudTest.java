/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;

import org.junit.Test;

/**
 * Test het contract van BrpVerblijfsrechtInhoud.
 *
 */
public class BrpVerblijfsrechtInhoudTest {

    private final BrpVerblijfsrechtInhoud inhoud1 = new BrpVerblijfsrechtInhoud(
        new BrpVerblijfsrechtCode((short) 12),
        new BrpDatum(20000101, null),
        new BrpDatum(20000802, null),
            new BrpDatum(20000802, null));
    private final BrpVerblijfsrechtInhoud inhoud2GelijkAan1 = new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode((short) 12), new BrpDatum(
        20000101,
        null), new BrpDatum(20000802, null),new BrpDatum(20000802, null));

    public static BrpVerblijfsrechtInhoud createInhoud() {
        return new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode((short) 12), new BrpDatum(20000101, null), new BrpDatum(20000802, null),new BrpDatum(20000802, null));
    }

    public static BrpStapel<BrpVerblijfsrechtInhoud> createStapel() {
        List<BrpGroep<BrpVerblijfsrechtInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpVerblijfsrechtInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2GelijkAan1.hashCode());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2GelijkAan1.toString());
    }
}
