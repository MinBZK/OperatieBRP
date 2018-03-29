/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import org.junit.Before;
import org.junit.Test;

public class BrpOnverwerktDocumentAanwezigIndicatieInhoudTest {

    private BrpOnverwerktDocumentAanwezigIndicatieInhoud inhoud1;
    private BrpOnverwerktDocumentAanwezigIndicatieInhoud inhoud2;

    @Before
    public void setup() {
        inhoud1 = new BrpOnverwerktDocumentAanwezigIndicatieInhoud(new BrpBoolean(true), null, null);
        inhoud2 = new BrpOnverwerktDocumentAanwezigIndicatieInhoud(new BrpBoolean(true), null, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
    }

    @Test
    public void testEqualsObject() {
        assertEquals(inhoud1, inhoud2);
        assertNotSame(inhoud1, new Object());
        assertEquals(inhoud1, inhoud1);
        assertNotSame(inhoud1, null);
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2.toString());
    }

    public static BrpOnverwerktDocumentAanwezigIndicatieInhoud createInhoud() {
        return new BrpOnverwerktDocumentAanwezigIndicatieInhoud(new BrpBoolean(Boolean.TRUE), null, null);
    }

    public static BrpStapel<BrpOnverwerktDocumentAanwezigIndicatieInhoud> createStapel() {
        List<BrpGroep<BrpOnverwerktDocumentAanwezigIndicatieInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpOnverwerktDocumentAanwezigIndicatieInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }
}
