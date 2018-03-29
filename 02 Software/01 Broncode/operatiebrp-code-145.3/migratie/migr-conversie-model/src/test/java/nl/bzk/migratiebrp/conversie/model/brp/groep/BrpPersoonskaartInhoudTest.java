/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBooleanTest;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import org.junit.Assert;
import org.junit.Test;

public class BrpPersoonskaartInhoudTest {
    @Test
    public void test(){
        final BrpPersoonskaartInhoud inhoud = createInhoud();
        Assert.assertEquals("000200",inhoud.getGemeentePKCode().getWaarde());
    }

    public static BrpPersoonskaartInhoud createInhoud() {
        return new BrpPersoonskaartInhoud(new BrpPartijCode("000200"), BrpBooleanTest.BRP_TRUE);
    }

    public static BrpStapel<BrpPersoonskaartInhoud> createStapel() {
        List<BrpGroep<BrpPersoonskaartInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpPersoonskaartInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

}
