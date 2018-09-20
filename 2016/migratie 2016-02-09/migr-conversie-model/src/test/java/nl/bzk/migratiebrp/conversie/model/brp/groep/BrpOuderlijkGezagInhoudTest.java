/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BrpOuderlijkGezagInhoudTest {

    @Test
    public void testIsLeeg() throws Exception {

    }

    @Test
    public void testGetOuderHeeftGezag() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

    public static BrpOuderlijkGezagInhoud createInhoud(Boolean value) {
        return new BrpOuderlijkGezagInhoud(new BrpBoolean(value));
    }

    public static BrpStapel<BrpOuderlijkGezagInhoud> createStapel(Boolean gezagInhoud) {
        List<BrpGroep<BrpOuderlijkGezagInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpOuderlijkGezagInhoud> groep = new BrpGroep<>(createInhoud(gezagInhoud), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        BrpStapel<BrpOuderlijkGezagInhoud> result = new BrpStapel<>(groepen);
        return result;
    }
}
