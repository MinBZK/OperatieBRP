/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import org.junit.Test;

/**
 */
public class BrpIstHuwelijkOfGpGroepInhoudTest {

    public static BrpIstHuwelijkOfGpGroepInhoud createInhoud() {
        return new BrpIstHuwelijkOfGpGroepInhoud(
                BrpIstStandaardGroepInhoudTestUtil.createInhoud(),
                BrpIstRelatieGroepInhoudTest.createInhoud(),
                new BrpInteger(20010101),
                new BrpGemeenteCode("0042"),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                BrpSoortRelatieCode.HUWELIJK);
    }

    public static BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> createStapel() {
        List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpIstHuwelijkOfGpGroepInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    @Test
    public void createStuff() {
        assertNotNull(BrpIstHuwelijkOfGpGroepInhoudTest.createStapel());
    }

}
