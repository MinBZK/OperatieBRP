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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBooleanTest;
import org.junit.Test;

/**
 */
public class BrpIstGezagsVerhoudingGroepInhoudTest {

    public static BrpIstGezagsVerhoudingGroepInhoud createInhoud() {
        return new BrpIstGezagsVerhoudingGroepInhoud(
                BrpIstStandaardGroepInhoudTestUtil.createInhoud(),
                BrpBooleanTest.BRP_TRUE,
                BrpBooleanTest.BRP_TRUE,
                BrpBooleanTest.BRP_FALSE,
                BrpBooleanTest.BRP_FALSE);
    }

    public static BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> createStapel() {
        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpIstGezagsVerhoudingGroepInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    @Test
    public void createStuff() {
        assertNotNull(BrpIstGezagsVerhoudingGroepInhoudTest.createStapel());
    }

}
